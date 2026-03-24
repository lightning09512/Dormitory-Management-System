package com.ktx.model;

import jakarta.persistence.*;

/**
 * Entity ánh xạ bảng {@code NhanVien}.
 */
@Entity
@Table(name = "NhanVien")
public class NhanVien {

    @Id
    @Column(name = "MaNV", length = 10, nullable = false)
    private String maNV;

    @Column(name = "HoTen", nullable = false, length = 100)
    private String hoTen;

    @Column(name = "VaiTro", nullable = false)
    private String vaiTro;   // 'Staff' | 'Manager'

    @Column(name = "SoDT", length = 15)
    private String soDT;

    @Column(name = "TaiKhoan", nullable = false, unique = true, length = 50)
    private String taiKhoan;

    @Column(name = "MatKhau", nullable = false, length = 255)
    private String matKhau;

    public NhanVien() {}

    public NhanVien(String maNV, String hoTen, String vaiTro,
                    String soDT, String taiKhoan, String matKhau) {
        this.maNV = maNV; this.hoTen = hoTen; this.vaiTro = vaiTro;
        this.soDT = soDT; this.taiKhoan = taiKhoan; this.matKhau = matKhau;
    }

    public String getMaNV()              { return maNV; }
    public void   setMaNV(String v)      { this.maNV = v; }
    public String getHoTen()             { return hoTen; }
    public void   setHoTen(String v)     { this.hoTen = v; }
    public String getVaiTro()            { return vaiTro; }
    public void   setVaiTro(String v)    { this.vaiTro = v; }
    public String getSoDT()              { return soDT; }
    public void   setSoDT(String v)      { this.soDT = v; }
    public String getTaiKhoan()          { return taiKhoan; }
    public void   setTaiKhoan(String v)  { this.taiKhoan = v; }
    public String getMatKhau()           { return matKhau; }
    public void   setMatKhau(String v)   { this.matKhau = v; }

    @Override
    public String toString() {
        return "NhanVien{maNV='" + maNV + "', hoTen='" + hoTen + "', vaiTro='" + vaiTro + "'}";
    }
}
