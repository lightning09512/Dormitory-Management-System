package com.ktx.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

/**
 * Entity ánh xạ bảng {@code SinhVien}.
 */
@Entity
@Table(name = "SinhVien")
public class SinhVien {

    @Id
    @Column(name = "MaSV", length = 10, nullable = false)
    @NotBlank(message = "Mã sinh viên không được rỗng")
    @Size(min = 5, max = 10, message = "Mã sinh viên phải từ 5-10 ký tự")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "Mã sinh viên chỉ chứa chữ hoa và số")
    private String maSV;

    @Column(name = "HoTen", nullable = false, length = 100)
    @NotBlank(message = "Họ tên không được rỗng")
    @Size(max = 100, message = "Họ tên tối đa 100 ký tự")
    private String hoTen;

    @Column(name = "NgaySinh")
    @Past(message = "Ngày sinh phải là ngày trong quá khứ")
    private LocalDate ngaySinh;

    @Column(name = "GioiTinh")
    @Pattern(regexp = "^(Nam|Nữ|Khác)$", message = "Giới tính phải là Nam, Nữ hoặc Khác")
    private String gioiTinh;

    @Column(name = "Lop", length = 20)
    @Size(max = 20, message = "Lớp tối đa 20 ký tự")
    private String lop;

    @Column(name = "Khoa", length = 100)
    @Size(max = 100, message = "Khoa tối đa 100 ký tự")
    private String khoa;

    @Column(name = "SoDT", length = 15)
    @Pattern(regexp = "^[0-9]+$", message = "Số điện thoại chỉ chứa số")
    @Size(min = 10, max = 15, message = "Số điện thoại từ 10-15 số")
    private String soDT;

    @Column(name = "QueQuan", length = 200)
    @Size(max = 200, message = "Quê quán tối đa 200 ký tự")
    private String queQuan;

    @Column(name = "Email", unique = true, length = 100)
    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được rỗng")
    private String email;

    @Column(name = "TaiKhoan", nullable = false, unique = true, length = 50)
    @NotBlank(message = "Tài khoản không được rỗng")
    @Size(min = 3, max = 50, message = "Tài khoản từ 3-50 ký tự")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Tài khoản chỉ chứa chữ, số và dấu gạch dưới")
    private String taiKhoan;

    @Column(name = "MatKhau", nullable = false, length = 255)
    @NotBlank(message = "Mật khẩu không được rỗng")
    @Size(min = 8, message = "Mật khẩu tối thiểu 8 ký tự")
    private String matKhau;

    public SinhVien() {}

    public SinhVien(String maSV, String hoTen, LocalDate ngaySinh,
                    String gioiTinh, String lop, String khoa,
                    String soDT, String queQuan, String email,
                    String taiKhoan, String matKhau) {
        this.maSV = maSV; this.hoTen = hoTen; this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh; this.lop = lop; this.khoa = khoa;
        this.soDT = soDT; this.queQuan = queQuan; this.email = email;
        this.taiKhoan = taiKhoan; this.matKhau = matKhau;
    }

    public String getMaSV()              { return maSV; }
    public void   setMaSV(String v)      { this.maSV = v; }
    public String getHoTen()             { return hoTen; }
    public void   setHoTen(String v)     { this.hoTen = v; }
    public LocalDate getNgaySinh()       { return ngaySinh; }
    public void setNgaySinh(LocalDate v) { this.ngaySinh = v; }
    public String getGioiTinh()          { return gioiTinh; }
    public void setGioiTinh(String v)    { this.gioiTinh = v; }
    public String getLop()               { return lop; }
    public void   setLop(String v)       { this.lop = v; }
    public String getKhoa()              { return khoa; }
    public void   setKhoa(String v)      { this.khoa = v; }
    public String getSoDT()              { return soDT; }
    public void   setSoDT(String v)      { this.soDT = v; }
    public String getQueQuan()           { return queQuan; }
    public void setQueQuan(String v)     { this.queQuan = v; }
    public String getEmail()             { return email; }
    public void   setEmail(String v)     { this.email = v; }
    public String getTaiKhoan()          { return taiKhoan; }
    public void setTaiKhoan(String v)    { this.taiKhoan = v; }
    public String getMatKhau()           { return matKhau; }
    public void setMatKhau(String v)     { this.matKhau = v; }

    @Override
    public String toString() {
        return "SinhVien{maSV='" + maSV + "', hoTen='" + hoTen + "'}";
    }
}
