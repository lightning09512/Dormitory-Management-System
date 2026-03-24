package com.ktx.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Entity ánh xạ bảng {@code Phong}.
 */
@Entity
@Table(name = "Phong")
public class Phong {

    @Id
    @Column(name = "MaPhong", length = 10, nullable = false)
    private String maPhong;

    @Column(name = "TenPhong", nullable = false, length = 20)
    private String tenPhong;

    @Column(name = "LoaiPhong", length = 50)
    private String loaiPhong;

    @Column(name = "SucChua", nullable = false)
    private int sucChua;

    @Column(name = "SoNguoiHienTai", nullable = false)
    private int soNguoiHienTai;

    @Column(name = "GiaPhong", nullable = false, precision = 12, scale = 2)
    private BigDecimal giaPhong;

    @Column(name = "TrangThai", nullable = false)
    private String trangThai;

    @Column(name = "MaDay", nullable = false, length = 10)
    private String maDay;

    public Phong() {}

    public Phong(String maPhong, String tenPhong, String loaiPhong,
                 int sucChua, int soNguoiHienTai,
                 BigDecimal giaPhong, String trangThai, String maDay) {
        this.maPhong = maPhong; this.tenPhong = tenPhong; this.loaiPhong = loaiPhong;
        this.sucChua = sucChua; this.soNguoiHienTai = soNguoiHienTai;
        this.giaPhong = giaPhong; this.trangThai = trangThai; this.maDay = maDay;
    }

    public String getMaPhong()                 { return maPhong; }
    public void   setMaPhong(String v)         { this.maPhong = v; }
    public String getTenPhong()                { return tenPhong; }
    public void   setTenPhong(String v)        { this.tenPhong = v; }
    public String getLoaiPhong()               { return loaiPhong; }
    public void   setLoaiPhong(String v)       { this.loaiPhong = v; }
    public int    getSucChua()                 { return sucChua; }
    public void   setSucChua(int v)            { this.sucChua = v; }
    public int    getSoNguoiHienTai()          { return soNguoiHienTai; }
    public void   setSoNguoiHienTai(int v)     { this.soNguoiHienTai = v; }
    public BigDecimal getGiaPhong()            { return giaPhong; }
    public void       setGiaPhong(BigDecimal v){ this.giaPhong = v; }
    public String getTrangThai()               { return trangThai; }
    public void   setTrangThai(String v)       { this.trangThai = v; }
    public String getMaDay()                   { return maDay; }
    public void   setMaDay(String v)           { this.maDay = v; }

    @Override
    public String toString() {
        return "Phong{maPhong='" + maPhong + "', tenPhong='" + tenPhong
                + "', trangThai='" + trangThai + "'}";
    }
}
