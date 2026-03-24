package com.ktx.service.impl;

import com.ktx.model.HoaDon;
import com.ktx.model.Phong;
import com.ktx.repository.HoaDonRepository;
import com.ktx.repository.PhongRepository;
import com.ktx.repository.impl.HoaDonRepositoryImpl;
import com.ktx.repository.impl.PhongRepositoryImpl;
import com.ktx.service.HoaDonService;

import java.math.BigDecimal;
import java.util.List;

public class HoaDonServiceImpl implements HoaDonService {

    private final HoaDonRepository hoaDonRepo;
    private final PhongRepository phongRepo;

    // Hardcode giá điện nước tạm thời. Thực tế lấy từ CauHinhGia
    private static final BigDecimal GIA_DIEN = new BigDecimal("3500");
    private static final BigDecimal GIA_NUOC = new BigDecimal("20000");

    public HoaDonServiceImpl() {
        this.hoaDonRepo = new HoaDonRepositoryImpl();
        this.phongRepo = new PhongRepositoryImpl();
    }

    @Override
    public List<HoaDon> layTatCaHoaDon() {
        return hoaDonRepo.findAll();
    }

    @Override
    public List<HoaDon> locHoaDonTheoThangNam(int thang, int nam) {
        return hoaDonRepo.findTheoThangNam(thang, nam);
    }

    @Override
    public void taoHoaDon(int thang, int nam, String maPhong, String maNV, 
                          BigDecimal chiSoDienCu, BigDecimal chiSoDienMoi, 
                          BigDecimal chiSoNuocCu, BigDecimal chiSoNuocMoi) throws Exception {
                          
        if (chiSoDienMoi.compareTo(chiSoDienCu) < 0) {
            throw new Exception("Chỉ số điện mới không được nhỏ hơn chỉ số cũ.");
        }
        if (chiSoNuocMoi.compareTo(chiSoNuocCu) < 0) {
            throw new Exception("Chỉ số nước mới không được nhỏ hơn chỉ số cũ.");
        }

        // 1. Kiểm tra phòng tồn tại
        Phong phong = phongRepo.findById(maPhong)
                .orElseThrow(() -> new Exception("Phòng " + maPhong + " không tồn tại."));

        // 2. Kiểm tra phòng đã có hóa đơn trong tháng này chưa
        if (hoaDonRepo.existsByPhongAndThangNam(maPhong, thang, nam)) {
            throw new Exception("Phòng " + maPhong + " đã có hóa đơn trong tháng " + thang + "/" + nam + ".");
        }

        // 3. Tính tiền
        BigDecimal dienTieuThu = chiSoDienMoi.subtract(chiSoDienCu);
        BigDecimal tienDien = dienTieuThu.multiply(GIA_DIEN);

        BigDecimal nuocTieuThu = chiSoNuocMoi.subtract(chiSoNuocCu);
        BigDecimal tienNuoc = nuocTieuThu.multiply(GIA_NUOC);

        BigDecimal giaPhong = phong.getGiaPhong();
        BigDecimal phuPhi = BigDecimal.ZERO; // Có thể mở rộng nhập thêm sau

        BigDecimal tongTien = tienDien.add(tienNuoc).add(giaPhong).add(phuPhi);

        // 4. Sinh mã hóa đơn: HD[MM][YY][MaPhong] (Tối đa 15 ký tự nếu MaPhong ~ 8)
        String mm = String.format("%02d", thang);
        String yy = String.valueOf(nam).substring(2);
        String maHDon = "HD" + mm + yy + maPhong.replace("-", "");
        if (maHDon.length() > 15) {
            maHDon = maHDon.substring(0, 15);
        }

        // 5. Lưu xuống DB
        HoaDon hd = new HoaDon(
            maHDon, thang, nam,
            chiSoDienCu, chiSoDienMoi,
            chiSoNuocCu, chiSoNuocMoi,
            phuPhi, tongTien,
            "Chưa thanh toán", maPhong, maNV
        );

        hoaDonRepo.save(hd);
    }

    @Override
    public void thanhToanHoaDon(String maHDon) throws Exception {
        HoaDon hd = hoaDonRepo.findById(maHDon)
                .orElseThrow(() -> new Exception("Hóa đơn không tồn tại."));
                
        if ("Đã thanh toán".equals(hd.getTrangThaiThanhToan())) {
            throw new Exception("Hóa đơn này đã được thanh toán rồi.");
        }
        
        hd.setTrangThaiThanhToan("Đã thanh toán");
        hoaDonRepo.update(hd);
    }

    @Override
    public void xoaHoaDon(String maHDon) throws Exception {
        HoaDon hd = hoaDonRepo.findById(maHDon)
                .orElseThrow(() -> new Exception("Hóa đơn không tồn tại."));
        if ("Đã thanh toán".equals(hd.getTrangThaiThanhToan())) {
            throw new Exception("Không thể xóa hóa đơn đã thanh toán. Vui lòng liên hệ quản lý.");
        }
        hoaDonRepo.delete(maHDon);
    }
}
