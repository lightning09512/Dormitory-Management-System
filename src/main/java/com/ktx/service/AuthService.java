package com.ktx.service;

import com.ktx.model.NhanVien;

/**
 * Service xác thực đăng nhập.
 */
public interface AuthService {

    /**
     * Xác thực tài khoản nhân viên.
     *
     * @param taiKhoan tên đăng nhập
     * @param matKhau  mật khẩu (plaintext – sẽ được đối chiếu với hash bcrypt trong DB)
     * @return         {@link NhanVien} đã xác thực
     * @throws IllegalArgumentException nếu tài khoản không tồn tại
     * @throws SecurityException        nếu mật khẩu sai
     */
    NhanVien dangNhap(String taiKhoan, String matKhau);
}
