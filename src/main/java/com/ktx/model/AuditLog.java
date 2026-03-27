package com.ktx.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity ánh xạ bảng {@code AuditLog}.
 * Dùng để lưu trữ nhật ký hệ thống.
 */
@Entity
@Table(name = "AuditLog")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "MaNV", length = 10, nullable = false)
    private String maNV;

    @Column(name = "HanhDong", length = 100, nullable = false)
    private String hanhDong;

    @Column(name = "ThoiGian", nullable = false)
    private LocalDateTime thoiGian;

    @Column(name = "ChiTiet", columnDefinition = "TEXT")
    private String chiTiet;

    public AuditLog() {}

    public AuditLog(String maNV, String hanhDong, String chiTiet) {
        this.maNV = maNV;
        this.hanhDong = hanhDong;
        this.chiTiet = chiTiet;
        this.thoiGian = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }

    public String getHanhDong() { return hanhDong; }
    public void setHanhDong(String hanhDong) { this.hanhDong = hanhDong; }

    public LocalDateTime getThoiGian() { return thoiGian; }
    public void setThoiGian(LocalDateTime thoiGian) { this.thoiGian = thoiGian; }

    public String getChiTiet() { return chiTiet; }
    public void setChiTiet(String chiTiet) { this.chiTiet = chiTiet; }

    @Override
    public String toString() {
        return "AuditLog{id=" + id + ", maNV='" + maNV + "', hanhDong='" + hanhDong + "'}";
    }
}
