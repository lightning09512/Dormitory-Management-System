package com.ktx.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entity ánh xạ bảng {@code CauHinhGia}.
 * MaGia: AUTO_INCREMENT → @GeneratedValue(IDENTITY)
 */
@Entity
@Table(name = "CauHinhGia")
public class CauHinhGia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaGia")
    private int maGia;

    @Column(name = "LoaiDichVu", nullable = false)
    private String loaiDichVu;

    @Column(name = "DonGia", nullable = false, precision = 12, scale = 2)
    private BigDecimal donGia;

    @Column(name = "NgayApDung", nullable = false)
    private LocalDate ngayApDung;

    public CauHinhGia() {}

    public CauHinhGia(int maGia, String loaiDichVu, BigDecimal donGia, LocalDate ngayApDung) {
        this.maGia = maGia; this.loaiDichVu = loaiDichVu;
        this.donGia = donGia; this.ngayApDung = ngayApDung;
    }

    public int  getMaGia()              { return maGia; }
    public void setMaGia(int v)         { this.maGia = v; }
    public String getLoaiDichVu()       { return loaiDichVu; }
    public void setLoaiDichVu(String v) { this.loaiDichVu = v; }
    public BigDecimal getDonGia()       { return donGia; }
    public void setDonGia(BigDecimal v) { this.donGia = v; }
    public LocalDate getNgayApDung()    { return ngayApDung; }
    public void setNgayApDung(LocalDate v){ this.ngayApDung = v; }

    @Override
    public String toString() {
        return "CauHinhGia{maGia=" + maGia + ", loaiDichVu='" + loaiDichVu + "'}";
    }
}
