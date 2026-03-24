package com.ktx.controller;

import com.ktx.model.NhanVien;
import com.ktx.service.AuthService;
import com.ktx.view.DangNhapFrame;

import javax.swing.*;

/**
 * Controller xử lý đăng nhập.
 * Sau khi xác thực thành công, gọi callback để mở MainFrame.
 */
public class AuthController {

    private final DangNhapFrame view;
    private final AuthService   authService;
    private final LoginCallback callback;

    /**
     * @param callback Hàm được gọi sau khi đăng nhập thành công,
     *                 nhận NhanVien đã xác thực.
     */
    public AuthController(DangNhapFrame view,
                          AuthService authService,
                          LoginCallback callback) {
        this.view        = view;
        this.authService = authService;
        this.callback    = callback;
        bindListeners();
    }

    private void bindListeners() {
        view.addDangNhapListener(e -> handleDangNhap());
    }

    private void handleDangNhap() {
        String tk = view.getTaiKhoan();
        String mk = view.getMatKhau();

        if (tk.isEmpty() || mk.isEmpty()) {
            view.setThongBao("Vui lòng nhập đầy đủ tài khoản và mật khẩu.");
            return;
        }

        try {
            NhanVien nv = authService.dangNhap(tk, mk);
            view.clearThongBao();
            view.dispose();
            callback.onLoginSuccess(nv);
        } catch (IllegalArgumentException ex) {
            view.setThongBao("Tài khoản không tồn tại.");
        } catch (SecurityException ex) {
            view.setThongBao("Mật khẩu không đúng.");
        } catch (Exception ex) {
            view.setThongBao("Lỗi hệ thống: " + ex.getMessage());
        }
    }

    /** Callback interface – triển khai inline trong App.java. */
    @FunctionalInterface
    public interface LoginCallback {
        void onLoginSuccess(NhanVien user);
    }
}
