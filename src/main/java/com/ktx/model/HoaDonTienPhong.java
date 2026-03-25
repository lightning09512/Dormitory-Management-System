package com.ktx.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entity ánh xạ bảng {@code HoaDonTienPhong}.
 */
@Entity
@Table(name = "HoaDonTienPhong")
public class HoaDonTienPhong {

    @Id
    @Column(name = "MaHDTP", length = 15, nullable = false)
    private String maHDTP;

    @Column(name = "MaHD", nullable = false, length = 15)
    private String maHD;

    @Column(name = "GoiThanhToan", nullable = false)
    private int goiThanhToan;

    @Column(name = "NgayLap", nullable = false)
    private LocalDate ngayLap;

    @Column(name = "TongTien", nullable = false, precision = 14, scale = 2)
    private BigDecimal tongTien;

    @Column(name = "TrangThai", nullable = false, length = 50)
    private String trangThai;

    @Column(name = "MaNV", nullable = false, length = 10)
    private String maNV;

    public HoaDonTienPhong() {}

    public HoaDonTienPhong(String maHDTP, String maHD, int goiThanhToan,
                           LocalDate ngayLap, BigDecimal tongTien,
                           String trangThai, String maNV) {
        this.maHDTP = maHDTP;
        this.maHD = maHD;
        this.goiThanhToan = goiThanhToan;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
        this.maNV = maNV;
    }

    public String getMaHDTP() { return maHDTP; }
    public void setMaHDTP(String maHDTP) { this.maHDTP = maHDTP; }

    public String getMaHD() { return maHD; }
    public void setMaHD(String maHD) { this.maHD = maHD; }

    public int getGoiThanhToan() { return goiThanhToan; }
    public void setGoiThanhToan(int goiThanhToan) { this.goiThanhToan = goiThanhToan; }

    public LocalDate getNgayLap() { return ngayLap; }
    public void setNgayLap(LocalDate ngayLap) { this.ngayLap = ngayLap; }

    public BigDecimal getTongTien() { return tongTien; }
    public void setTongTien(BigDecimal tongTien) { this.tongTien = tongTien; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }

    @Override
    public String toString() {
        return "HoaDonTienPhong{" +
                "maHDTP='" + maHDTP + '\'' +
                ", maHD='" + maHD + '\'' +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}
