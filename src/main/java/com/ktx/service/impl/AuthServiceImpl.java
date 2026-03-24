package com.ktx.service.impl;

import com.ktx.model.NhanVien;
import com.ktx.repository.NhanVienRepository;
import com.ktx.service.AuthService;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Triển khai {@link AuthService} dùng BCrypt để xác thực mật khẩu.
 *
 * <p>Mật khẩu trong DB phải được lưu dạng BCrypt hash
 * (ví dụ: {@code BCrypt.hashpw("password", BCrypt.gensalt())}).</p>
 */
public class AuthServiceImpl implements AuthService {

    private final NhanVienRepository nhanVienRepo;

    public AuthServiceImpl(NhanVienRepository nhanVienRepo) {
        this.nhanVienRepo = nhanVienRepo;
    }

    @Override
    public NhanVien dangNhap(String taiKhoan, String matKhau) {
        NhanVien nv = nhanVienRepo.findByTaiKhoan(taiKhoan)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Tài khoản không tồn tại: " + taiKhoan));

        if (!BCrypt.checkpw(matKhau, nv.getMatKhau())) {
            throw new SecurityException("Mật khẩu không đúng.");
        }
        return nv;
    }
}
