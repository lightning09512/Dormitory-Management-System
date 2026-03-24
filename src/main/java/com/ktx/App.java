package com.ktx;

import com.ktx.config.JpaUtil;
import com.ktx.controller.*;
import com.ktx.model.NhanVien;
import com.ktx.repository.*;
import com.ktx.repository.impl.*;
import com.ktx.service.*;
import com.ktx.service.impl.*;
import com.ktx.view.*;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

/**
 * Điểm khởi động của ứng dụng Quản lý Ký túc xá.
 *
 * <p>Thứ tự khởi tạo:</p>
 * <ol>
 *   <li>Repository → Service (DI thuần)</li>
 *   <li>Hiển thị màn hình đăng nhập</li>
 *   <li>Sau đăng nhập thành công → tạo MainFrame + tất cả Panel + Controller</li>
 *   <li>Đăng ký shutdown hook để đóng JPA khi thoát</li>
 * </ol>
 */
public class App {

    public static void main(String[] args) {
        // ---- FlatLaf Modern L&F ----
        FlatLightLaf.setup();
        UIManager.put("Button.arc", 8);
        UIManager.put("Component.arc", 8);
        UIManager.put("TextComponent.arc", 6);
        UIManager.put("ScrollBar.width", 8);
        UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 13));

        // Đóng JPA khi JVM tắt
        Runtime.getRuntime().addShutdownHook(new Thread(JpaUtil::shutdown));

        SwingUtilities.invokeLater(App::startLogin);
    }

    // ----------------------------------------------------------------
    // Bước 1: Khởi động màn hình đăng nhập
    // ----------------------------------------------------------------
    private static void startLogin() {
        // --- Repositories ---
        NhanVienRepository nhanVienRepo = new NhanVienRepositoryImpl();

        // --- Services ---
        AuthService authService = new AuthServiceImpl(nhanVienRepo);

        // --- View ---
        DangNhapFrame loginFrame = new DangNhapFrame();

        // --- Controller (callback mở MainFrame sau login) ---
        new AuthController(loginFrame, authService,
                user -> SwingUtilities.invokeLater(() -> startMain(user)));

        loginFrame.setVisible(true);
    }

    // ----------------------------------------------------------------
    // Bước 2: Khởi động MainFrame sau khi đăng nhập thành công
    // ----------------------------------------------------------------
    private static void startMain(NhanVien user) {
        // ---- Repositories ----
        NhanVienRepository nhanVienRepo = new NhanVienRepositoryImpl();
        SinhVienRepository sinhVienRepo = new SinhVienRepositoryImpl();
        PhongRepository    phongRepo    = new PhongRepositoryImpl();
        HopDongRepository  hopDongRepo  = new HopDongRepositoryImpl();
        ThongKeRepository  thongKeRepo  = new ThongKeRepositoryImpl();
        DayNhaRepository   dayNhaRepo   = new DayNhaRepositoryImpl();

        // ---- Services ----
        SinhVienService sinhVienSvc = new SinhVienServiceImpl(sinhVienRepo, hopDongRepo);
        PhongService    phongSvc    = new PhongServiceImpl(phongRepo);
        HopDongService  hopDongSvc  = new HopDongServiceImpl(hopDongRepo, sinhVienRepo, phongRepo);
        ThongKeService  thongKeSvc  = new ThongKeServiceImpl(thongKeRepo);
        HoaDonService   hoaDonSvc   = new HoaDonServiceImpl();

        // ---- Views ----
        DashboardPanel        dashPanel = new DashboardPanel();
        QuanLySinhVienPanel   svPanel   = new QuanLySinhVienPanel();
        QuanLyPhongPanel      phongPanel= new QuanLyPhongPanel();
        LapHopDongPanel       hdPanel   = new LapHopDongPanel();
        QuanLyHoaDonPanel     hdnPanel  = new QuanLyHoaDonPanel();

        // ---- Controllers ----
        DashboardController  dashCtrl = new DashboardController(dashPanel, thongKeSvc);
        new SinhVienController(svPanel,   sinhVienSvc);
        new PhongController   (phongPanel, phongSvc, dayNhaRepo);
        new HopDongController (hdPanel, hopDongSvc, sinhVienSvc, phongSvc, user);
        new HoaDonController  (hdnPanel, hoaDonSvc, user.getMaNV());

        // ---- MainFrame ----
        MainFrame frame = new MainFrame(user);
        frame.setPanel(dashPanel,  MainFrame.CARD_DASHBOARD);
        frame.setPanel(svPanel,    MainFrame.CARD_SINH_VIEN);
        frame.setPanel(phongPanel, MainFrame.CARD_PHONG);
        frame.setPanel(hdPanel,    MainFrame.CARD_HOP_DONG);
        frame.setPanel(hdnPanel,   MainFrame.CARD_HOA_DON);

        // Refresh dashboard mỗi khi chuyển sang tab (dùng addDangXuatListener tác động phụ)
        // Đăng xuất: đóng MainFrame và quay lại màn hình login
        frame.addDangXuatListener(e -> {
            int ok = JOptionPane.showConfirmDialog(frame, "Bạn có chắc muốn đăng xuất?",
                    "Đăng xuất", JOptionPane.YES_NO_OPTION);
            if (ok == JOptionPane.YES_OPTION) {
                frame.dispose();
                SwingUtilities.invokeLater(App::startLogin);
            }
        });

        frame.setVisible(true);
    }
}
