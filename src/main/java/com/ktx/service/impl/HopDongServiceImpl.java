package com.ktx.service.impl;

import com.ktx.model.HopDong;
import com.ktx.model.NhanVien;
import com.ktx.model.Phong;
import com.ktx.model.SinhVien;
import com.ktx.repository.HopDongRepository;
import com.ktx.repository.PhongRepository;
import com.ktx.repository.SinhVienRepository;
import com.ktx.service.HopDongService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Triển khai {@link HopDongService} – chứa toàn bộ business logic
 * liên quan đến hợp đồng lưu trú.
 *
 * <p>Nhận các Repository qua <b>Constructor Injection</b> (DIP).</p>
 */
public class HopDongServiceImpl implements HopDongService {

    // ----------------------------------------------------------------
    // Dependencies – truyền qua Constructor (Dependency Inversion)
    // ----------------------------------------------------------------
    private final HopDongRepository  hopDongRepo;
    private final SinhVienRepository sinhVienRepo;
    private final PhongRepository    phongRepo;

    /**
     * Constructor Injection.
     *
     * @param hopDongRepo  Repository hợp đồng
     * @param sinhVienRepo Repository sinh viên
     * @param phongRepo    Repository phòng
     */
    public HopDongServiceImpl(HopDongRepository hopDongRepo,
                              SinhVienRepository sinhVienRepo,
                              PhongRepository phongRepo) {
        this.hopDongRepo  = hopDongRepo;
        this.sinhVienRepo = sinhVienRepo;
        this.phongRepo    = phongRepo;
    }

    // ================================================================
    // TẠO HỢP ĐỒNG LƯU TRÚ – hàm nghiệp vụ cốt lõi
    // ================================================================

    /**
     * Quy trình tạo hợp đồng lưu trú:
     * <ol>
     *   <li>Kiểm tra sinh viên tồn tại trong hệ thống.</li>
     *   <li>Kiểm tra phòng tồn tại và còn chỗ.</li>
     *   <li>Tạo entity {@link HopDong} với mã sinh tự động.</li>
     *   <li>Tăng sĩ số phòng; nếu vừa đầy → đổi trạng thái 'Đang sử dụng'.</li>
     *   <li>Lưu HopDong mới và cập nhật Phong xuống DB.</li>
     * </ol>
     */
    @Override
    public HopDong taoHopDongLuuTru(String maSV, String maPhong,
                                     LocalDate ngayBatDau, LocalDate ngayKetThuc,
                                     NhanVien staff) {

        // ---- Bước 1: Kiểm tra sinh viên tồn tại -------------------
        SinhVien sinhVien = sinhVienRepo.findById(maSV)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Sinh viên không tồn tại: " + maSV));

        // Kiểm tra hợp đồng đang hiệu lực của sinh viên
        List<HopDong> listHD = hopDongRepo.findBySinhVien(maSV);
        for (HopDong hd : listHD) {
            if ("Đang hiệu lực".equals(hd.getTrangThai())) {
                throw new IllegalStateException("Sinh viên này đang có hợp đồng phòng " + hd.getMaPhong() + " đang hiệu lực. Không thể xếp thêm phòng!");
            }
        }

        // ---- Bước 2: Kiểm tra phòng và sức chứa ------------------
        Phong phong = phongRepo.findById(maPhong)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Phòng không tồn tại: " + maPhong));

        if (phong.getSoNguoiHienTai() >= phong.getSucChua()) {
            throw new IllegalStateException(
                    "Phòng " + maPhong + " đã hết chỗ! "
                    + "(Sức chứa: " + phong.getSucChua()
                    + " | Hiện tại: " + phong.getSoNguoiHienTai() + ")");
        }

        if ("Bảo trì".equals(phong.getTrangThai())) {
            throw new IllegalStateException(
                    "Phòng " + maPhong + " đang trong trạng thái bảo trì, "
                    + "không thể lập hợp đồng.");
        }

        // ---- Bước 3: Tạo entity HopDong mới ----------------------
        String maHD = sinhMaHopDong();
        HopDong hopDong = new HopDong(
                maHD,
                LocalDate.now(),        // NgayLap = hôm nay
                ngayBatDau,
                ngayKetThuc,
                java.math.BigDecimal.ZERO,   // TienCoc mặc định, cập nhật sau nếu cần
                "Đang hiệu lực",
                maSV,
                maPhong,
                staff.getMaNV()
        );

        // ---- Bước 4: Tăng sĩ số phòng & cập nhật trạng thái -----
        int soNguoiMoi = phong.getSoNguoiHienTai() + 1;
        phong.setSoNguoiHienTai(soNguoiMoi);

        if (soNguoiMoi >= phong.getSucChua()) {
            // Phòng vừa đầy → chuyển sang 'Đang sử dụng'
            phong.setTrangThai("Đang sử dụng");
        } else {
            // Phòng vẫn còn chỗ, đảm bảo trạng thái đúng
            phong.setTrangThai("Đang sử dụng");
        }

        // ---- Bước 5: Lưu xuống DB --------------------------------
        hopDongRepo.save(hopDong);
        phongRepo.update(phong);

        return hopDong;
    }

    // ================================================================
    // THANH LÝ HỢP ĐỒNG – trả phòng
    // ================================================================

    /**
     * Quy trình thanh lý hợp đồng:
     * <ol>
     *   <li>Kiểm tra hợp đồng tồn tại.</li>
     *   <li>Chỉ thanh lý được khi trạng thái là 'Đang hiệu lực'.</li>
     *   <li>Đổi trạng thái hợp đồng → 'Đã thanh lý'.</li>
     *   <li>Giảm sĩ số phòng xuống 1; nếu phòng chưa đầy → đổi trạng thái phòng → 'Còn trống'.</li>
     *   <li>Cập nhật cả hai xuống DB.</li>
     * </ol>
     */
    @Override
    public void thanhLyHopDong(String maHD) {

        // ---- Kiểm tra hợp đồng tồn tại ---------------------------
        HopDong hopDong = hopDongRepo.findById(maHD)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Hợp đồng không tồn tại: " + maHD));

        // ---- Chỉ thanh lý khi đang hiệu lực ----------------------
        if (!"Đang hiệu lực".equals(hopDong.getTrangThai())) {
            throw new IllegalStateException(
                    "Hợp đồng " + maHD + " không ở trạng thái 'Đang hiệu lực'. "
                    + "Trạng thái hiện tại: " + hopDong.getTrangThai());
        }

        // ---- Cập nhật trạng thái hợp đồng ------------------------
        hopDong.setTrangThai("Đã thanh lý");

        // ---- Cập nhật sĩ số và trạng thái phòng ------------------
        Phong phong = phongRepo.findById(hopDong.getMaPhong())
                .orElseThrow(() -> new IllegalStateException(
                        "Không tìm thấy phòng liên kết: " + hopDong.getMaPhong()));

        int soNguoiMoi = Math.max(0, phong.getSoNguoiHienTai() - 1);
        phong.setSoNguoiHienTai(soNguoiMoi);

        if (soNguoiMoi < phong.getSucChua()) {
            // Phòng còn chỗ → cập nhật về 'Còn trống'
            phong.setTrangThai("Còn trống");
        }
        // Nếu soNguoiMoi == sucChua vẫn giữ 'Đang sử dụng' (trường hợp hiếm)

        // ---- Lưu xuống DB ----------------------------------------
        hopDongRepo.update(hopDong);
        phongRepo.update(phong);
    }

    // ================================================================
    // CÁC HÀM TIỆN ÍCH KHÁC
    // ================================================================

    @Override
    public List<HopDong> layTatCa() {
        return hopDongRepo.findAll();
    }

    @Override
    public List<HopDong> layHopDongHieuLuc() {
        return hopDongRepo.findHopDongHieuLuc();
    }

    @Override
    public List<HopDong> layTheoDongSinhVien(String maSV) {
        return hopDongRepo.findBySinhVien(maSV);
    }

    // ================================================================
    // HELPER – Sinh mã hợp đồng tự động
    // ================================================================

    private String sinhMaHopDong() {
        String ngay = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        String rand = String.valueOf((int)(Math.random() * 90000) + 10000);
        return "HD-" + ngay + "-" + rand;
    }
}
