package com.ktx.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Entity ánh xạ bảng {@code HoaDon}.
 * Cột Nam kiểu YEAR trong MySQL → int trong Java.
 */
@Entity
@Table(name = "HoaDon")
public class HoaDon {

    @Id
    @Column(name = "MaHDon", length = 15, nullable = false)
    private String maHDon;

    @Column(name = "Thang", nullable = false)
    private int thang;

    @Column(name = "Nam", nullable = false)
    private int nam;

    @Column(name = "ChiSoDienCu", nullable = false, precision = 10, scale = 2)
    private BigDecimal chiSoDienCu;

    @Column(name = "ChiSoDienMoi", nullable = false, precision = 10, scale = 2)
    private BigDecimal chiSoDienMoi;

    @Column(name = "ChiSoNuocCu", nullable = false, precision = 10, scale = 2)
    private BigDecimal chiSoNuocCu;

    @Column(name = "ChiSoNuocMoi", nullable = false, precision = 10, scale = 2)
    private BigDecimal chiSoNuocMoi;

    @Column(name = "PhuPhi", nullable = false, precision = 12, scale = 2)
    private BigDecimal phuPhi;

    @Column(name = "TongTien", nullable = false, precision = 14, scale = 2)
    private BigDecimal tongTien;

    @Column(name = "TrangThaiThanhToan", nullable = false)
    private String trangThaiThanhToan;

    @Column(name = "MaPhong", nullable = false, length = 10)
    private String maPhong;

    @Column(name = "MaNV", nullable = false, length = 10)
    private String maNV;

    public HoaDon() {}

    public HoaDon(String maHDon, int thang, int nam,
                  BigDecimal chiSoDienCu, BigDecimal chiSoDienMoi,
                  BigDecimal chiSoNuocCu, BigDecimal chiSoNuocMoi,
                  BigDecimal phuPhi, BigDecimal tongTien,
                  String trangThaiThanhToan, String maPhong, String maNV) {
        this.maHDon = maHDon; this.thang = thang; this.nam = nam;
        this.chiSoDienCu = chiSoDienCu; this.chiSoDienMoi = chiSoDienMoi;
        this.chiSoNuocCu = chiSoNuocCu; this.chiSoNuocMoi = chiSoNuocMoi;
        this.phuPhi = phuPhi; this.tongTien = tongTien;
        this.trangThaiThanhToan = trangThaiThanhToan;
        this.maPhong = maPhong; this.maNV = maNV;
    }

    public String getMaHDon()                 { return maHDon; }
    public void   setMaHDon(String v)         { this.maHDon = v; }
    public int    getThang()                  { return thang; }
    public void   setThang(int v)             { this.thang = v; }
    public int    getNam()                    { return nam; }
    public void   setNam(int v)               { this.nam = v; }
    public BigDecimal getChiSoDienCu()        { return chiSoDienCu; }
    public void setChiSoDienCu(BigDecimal v)  { this.chiSoDienCu = v; }
    public BigDecimal getChiSoDienMoi()       { return chiSoDienMoi; }
    public void setChiSoDienMoi(BigDecimal v) { this.chiSoDienMoi = v; }
    public BigDecimal getChiSoNuocCu()        { return chiSoNuocCu; }
    public void setChiSoNuocCu(BigDecimal v)  { this.chiSoNuocCu = v; }
    public BigDecimal getChiSoNuocMoi()       { return chiSoNuocMoi; }
    public void setChiSoNuocMoi(BigDecimal v) { this.chiSoNuocMoi = v; }
    public BigDecimal getPhuPhi()             { return phuPhi; }
    public void setPhuPhi(BigDecimal v)       { this.phuPhi = v; }
    public BigDecimal getTongTien()           { return tongTien; }
    public void setTongTien(BigDecimal v)     { this.tongTien = v; }
    public String getTrangThaiThanhToan()              { return trangThaiThanhToan; }
    public void   setTrangThaiThanhToan(String v)      { this.trangThaiThanhToan = v; }
    public String getMaPhong()                { return maPhong; }
    public void   setMaPhong(String v)        { this.maPhong = v; }
    public String getMaNV()                   { return maNV; }
    public void   setMaNV(String v)           { this.maNV = v; }

    @Override
    public String toString() {
        return "HoaDon{maHDon='" + maHDon + "', thang=" + thang + ", nam=" + nam + "}";
    }
}
