package com.ktx.model;

import jakarta.persistence.*;

/**
 * Entity ánh xạ bảng {@code DayNha}.
 */
@Entity
@Table(name = "DayNha")
public class DayNha {

    @Id
    @Column(name = "MaDay", length = 10, nullable = false)
    private String maDay;

    @Column(name = "TenDay", nullable = false, length = 100)
    private String tenDay;

    @Column(name = "LoaiDay", length = 50)
    private String loaiDay;

    @Column(name = "SoTang")
    private Integer soTang;

    public DayNha() {}

    public DayNha(String maDay, String tenDay, String loaiDay, Integer soTang) {
        this.maDay = maDay; this.tenDay = tenDay;
        this.loaiDay = loaiDay; this.soTang = soTang;
    }

    public String getMaDay()             { return maDay; }
    public void   setMaDay(String v)     { this.maDay = v; }
    public String getTenDay()            { return tenDay; }
    public void   setTenDay(String v)    { this.tenDay = v; }
    public String getLoaiDay()           { return loaiDay; }
    public void setLoaiDay(String v)     { this.loaiDay = v; }
    public Integer getSoTang()           { return soTang; }
    public void setSoTang(Integer v)     { this.soTang = v; }

    @Override
    public String toString() {
        return "DayNha{maDay='" + maDay + "', tenDay='" + tenDay + "'}";
    }
}
