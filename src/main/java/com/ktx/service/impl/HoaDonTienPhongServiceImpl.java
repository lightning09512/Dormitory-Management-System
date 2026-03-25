package com.ktx.service.impl;

import com.ktx.model.HoaDonTienPhong;
import com.ktx.model.HopDong;
import com.ktx.model.Phong;
import com.ktx.repository.HoaDonTienPhongRepository;
import com.ktx.repository.HopDongRepository;
import com.ktx.repository.PhongRepository;
import com.ktx.service.HoaDonTienPhongService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class HoaDonTienPhongServiceImpl implements HoaDonTienPhongService {

    private final HoaDonTienPhongRepository hoadonRepo;
    private final HopDongRepository hopDongRepo;
    private final PhongRepository phongRepo;

    public HoaDonTienPhongServiceImpl(HoaDonTienPhongRepository hoadonRepo, 
                                     HopDongRepository hopDongRepo, 
                                     PhongRepository phongRepo) {
        this.hoadonRepo = hoadonRepo;
        this.hopDongRepo = hopDongRepo;
        this.phongRepo = phongRepo;
    }

    @Override
    public HoaDonTienPhong lapHoaDonTienPhong(String maHD, int goiThanhToan, String maNV) {
        // 1. Kiểm tra hợp đồng
        HopDong hd = hopDongRepo.findById(maHD)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hợp đồng: " + maHD));

        // 2. Lấy phòng
        Phong phong = phongRepo.findById(hd.getMaPhong())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phòng: " + hd.getMaPhong()));

        // 3. Tính tiền
        BigDecimal giaPhong = phong.getGiaPhong();
        BigDecimal tongTien = giaPhong.multiply(new BigDecimal(goiThanhToan));

        // 4. Tạo mã hóa đơn (HDTP-MaHD-Goi-Timestamp)
        String maHDTP = "TP" + System.currentTimeMillis() % 100000000L; 
        // Đảm bảo không quá dài cho cột VARCHAR(15)

        HoaDonTienPhong hdtp = new HoaDonTienPhong(
                maHDTP,
                maHD,
                goiThanhToan,
                LocalDate.now(),
                tongTien,
                "Chưa thanh toán",
                maNV
        );

        hoadonRepo.save(hdtp);
        return hdtp;
    }

    @Override
    public void xacNhanThanhToan(String maHDTP) {
        HoaDonTienPhong hdtp = hoadonRepo.findById(maHDTP)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hóa đơn: " + maHDTP));

        hdtp.setTrangThai("Đã thanh toán");
        hoadonRepo.update(hdtp);
    }

    @Override
    public List<HoaDonTienPhong> layTatCa() {
        return hoadonRepo.findAll();
    }

    @Override
    public List<HoaDonTienPhong> timTheoSinhVien(String maSV) {
        return hoadonRepo.findBySinhVien(maSV);
    }

    @Override
    public List<HoaDonTienPhong> timTheoPhong(String maPhong) {
        return hoadonRepo.findByPhong(maPhong);
    }
}
