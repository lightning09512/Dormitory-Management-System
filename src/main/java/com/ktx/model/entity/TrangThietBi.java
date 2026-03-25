package com.ktx.model.entity;

import com.ktx.model.Phong;
import com.ktx.model.enums.TinhTrangTB;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "TrangThietBi")
public class TrangThietBi {

    @Id
    @Column(name = "MaTB")
    private String maTB;

    @Column(name = "TenTB")
    private String tenTB;

    @Convert(converter = com.ktx.model.converter.TinhTrangTBConverter.class)
    @Column(name = "TinhTrang")
    private TinhTrangTB tinhTrang;

    @Column(name = "GiaTri")
    private BigDecimal giaTri;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaPhong")
    private Phong phong;

    public TrangThietBi() {
    }

    public TrangThietBi(String maTB, String tenTB, TinhTrangTB tinhTrang, BigDecimal giaTri, Phong phong) {
        this.maTB = maTB;
        this.tenTB = tenTB;
        this.tinhTrang = tinhTrang;
        this.giaTri = giaTri;
        this.phong = phong;
    }

    public String getMaTB() {
        return maTB;
    }

    public void setMaTB(String maTB) {
        this.maTB = maTB;
    }

    public String getTenTB() {
        return tenTB;
    }

    public void setTenTB(String tenTB) {
        this.tenTB = tenTB;
    }

    public TinhTrangTB getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(TinhTrangTB tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public BigDecimal getGiaTri() {
        return giaTri;
    }

    public void setGiaTri(BigDecimal giaTri) {
        this.giaTri = giaTri;
    }

    public Phong getPhong() {
        return phong;
    }

    public void setPhong(Phong phong) {
        this.phong = phong;
    }
}
