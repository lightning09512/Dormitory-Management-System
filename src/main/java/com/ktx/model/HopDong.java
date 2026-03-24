package com.ktx.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entity ánh xạ bảng {@code HopDong}.
 */
@Entity
@Table(name = "HopDong")
public class HopDong {

    @Id
    @Column(name = "MaHD", length = 15, nullable = false)
    private String maHD;

    @Column(name = "NgayLap", nullable = false)
    private LocalDate ngayLap;

    @Column(name = "NgayBatDau", nullable = false)
    private LocalDate ngayBatDau;

    @Column(name = "NgayKetThuc", nullable = false)
    private LocalDate ngayKetThuc;

    @Column(name = "TienCoc", nullable = false, precision = 12, scale = 2)
    private BigDecimal tienCoc;

    @Column(name = "TrangThai", nullable = false)
    private String trangThai;

    @Column(name = "MaSV", nullable = false, length = 10)
    private String maSV;

    @Column(name = "MaPhong", nullable = false, length = 10)
    private String maPhong;

    @Column(name = "MaNV", nullable = false, length = 10)
    private String maNV;

    public HopDong() {}

    public HopDong(String maHD, LocalDate ngayLap, LocalDate ngayBatDau,
                   LocalDate ngayKetThuc, BigDecimal tienCoc, String trangThai,
                   String maSV, String maPhong, String maNV) {
        this.maHD = maHD; this.ngayLap = ngayLap; this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc; this.tienCoc = tienCoc; this.trangThai = trangThai;
        this.maSV = maSV; this.maPhong = maPhong; this.maNV = maNV;
    }

    public String getMaHD()                    { return maHD; }
    public void   setMaHD(String v)            { this.maHD = v; }
    public LocalDate getNgayLap()              { return ngayLap; }
    public void setNgayLap(LocalDate v)        { this.ngayLap = v; }
    public LocalDate getNgayBatDau()           { return ngayBatDau; }
    public void setNgayBatDau(LocalDate v)     { this.ngayBatDau = v; }
    public LocalDate getNgayKetThuc()          { return ngayKetThuc; }
    public void setNgayKetThuc(LocalDate v)    { this.ngayKetThuc = v; }
    public BigDecimal getTienCoc()             { return tienCoc; }
    public void setTienCoc(BigDecimal v)       { this.tienCoc = v; }
    public String getTrangThai()               { return trangThai; }
    public void   setTrangThai(String v)       { this.trangThai = v; }
    public String getMaSV()                    { return maSV; }
    public void   setMaSV(String v)            { this.maSV = v; }
    public String getMaPhong()                 { return maPhong; }
    public void   setMaPhong(String v)         { this.maPhong = v; }
    public String getMaNV()                    { return maNV; }
    public void   setMaNV(String v)            { this.maNV = v; }

    @Override
    public String toString() {
        return "HopDong{maHD='" + maHD + "', trangThai='" + trangThai + "'}";
    }
}
