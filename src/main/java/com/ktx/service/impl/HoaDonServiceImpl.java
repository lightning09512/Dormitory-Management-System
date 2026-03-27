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
    private final com.ktx.service.CauHinhGiaService giaSvc;
    private final com.ktx.service.AuditLogService auditLogService;

    public HoaDonServiceImpl(com.ktx.service.CauHinhGiaService giaSvc, com.ktx.service.AuditLogService auditLogService) {
        this.hoaDonRepo = new com.ktx.repository.impl.HoaDonRepositoryImpl();
        this.phongRepo = new com.ktx.repository.impl.PhongRepositoryImpl();
        this.giaSvc = giaSvc;
        this.auditLogService = auditLogService;
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
        BigDecimal giaDien = giaSvc.getDonGia("Điện");
        BigDecimal giaNuoc = giaSvc.getDonGia("Nước");

        BigDecimal dienTieuThu = chiSoDienMoi.subtract(chiSoDienCu);
        BigDecimal tienDien = dienTieuThu.multiply(giaDien);

        BigDecimal nuocTieuThu = chiSoNuocMoi.subtract(chiSoNuocCu);
        BigDecimal tienNuoc = nuocTieuThu.multiply(giaNuoc);

        BigDecimal phuPhi = BigDecimal.ZERO; // Có thể mở rộng nhập thêm sau
        BigDecimal tongTien = tienDien.add(tienNuoc).add(phuPhi);

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

        if (auditLogService != null) {
            auditLogService.log(maNV, "LẬP_HÓA_ĐƠN", "Lập hóa đơn [" + maHDon + "] cho phòng [" + maPhong + "]");
        }
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

        if (auditLogService != null) {
            auditLogService.log("SYSTEM", "THANH_TOÁN_HÓA_ĐƠN", "Thanh toán hóa đơn mã: " + maHDon);
        }
    }

    @Override
    public void xoaHoaDon(String maHDon, String vaiTroNguoiXoa) throws Exception {
        HoaDon hd = hoaDonRepo.findById(maHDon)
                .orElseThrow(() -> new Exception("Hóa đơn không tồn tại."));
        
        // Chỉ cho phép xóa hóa đơn đã thanh toán nếu là Manager
        if ("Đã thanh toán".equals(hd.getTrangThaiThanhToan()) && !"Manager".equalsIgnoreCase(vaiTroNguoiXoa)) {
            throw new Exception("Không thể xóa hóa đơn đã thanh toán. Vui lòng liên hệ quản lý.");
        }
        
        hoaDonRepo.delete(maHDon);

        if (auditLogService != null) {
            auditLogService.log("SYSTEM", "XÓA_HÓA_ĐƠN", "Xóa hóa đơn mã: " + maHDon + " bởi " + vaiTroNguoiXoa);
        }
    }
    @Override
    public HoaDon layHoaDonGanNhatTheoPhong(String maPhong) {
        return hoaDonRepo.findLatestByPhong(maPhong).orElse(null);
    }
}
