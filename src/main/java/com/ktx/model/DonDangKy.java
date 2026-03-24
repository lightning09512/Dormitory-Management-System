package com.ktx.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Entity ánh xạ bảng {@code DonDangKy}.
 */
@Entity
@Table(name = "DonDangKy")
public class DonDangKy {

    @Id
    @Column(name = "MaDon", length = 15, nullable = false)
    private String maDon;

    @Column(name = "NgayGui", nullable = false)
    private LocalDate ngayGui;

    @Column(name = "HocKy", nullable = false)
    private int hocKy;

    @Column(name = "NamHoc", nullable = false, length = 9)
    private String namHoc;

    @Column(name = "TrangThai", nullable = false)
    private String trangThai;

    @Column(name = "MaSV", nullable = false, length = 10)
    private String maSV;

    @Column(name = "MaPhong", nullable = false, length = 10)
    private String maPhong;

    public DonDangKy() {}

    public DonDangKy(String maDon, LocalDate ngayGui, int hocKy,
                     String namHoc, String trangThai, String maSV, String maPhong) {
        this.maDon = maDon; this.ngayGui = ngayGui; this.hocKy = hocKy;
        this.namHoc = namHoc; this.trangThai = trangThai;
        this.maSV = maSV; this.maPhong = maPhong;
    }

    public String getMaDon()               { return maDon; }
    public void   setMaDon(String v)       { this.maDon = v; }
    public LocalDate getNgayGui()          { return ngayGui; }
    public void setNgayGui(LocalDate v)    { this.ngayGui = v; }
    public int  getHocKy()                 { return hocKy; }
    public void setHocKy(int v)            { this.hocKy = v; }
    public String getNamHoc()              { return namHoc; }
    public void   setNamHoc(String v)      { this.namHoc = v; }
    public String getTrangThai()           { return trangThai; }
    public void   setTrangThai(String v)   { this.trangThai = v; }
    public String getMaSV()                { return maSV; }
    public void   setMaSV(String v)        { this.maSV = v; }
    public String getMaPhong()             { return maPhong; }
    public void   setMaPhong(String v)     { this.maPhong = v; }

    @Override
    public String toString() {
        return "DonDangKy{maDon='" + maDon + "', trangThai='" + trangThai + "'}";
    }
}
