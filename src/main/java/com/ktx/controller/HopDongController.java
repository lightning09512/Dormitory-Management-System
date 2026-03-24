package com.ktx.controller;

import com.ktx.model.HopDong;
import com.ktx.model.NhanVien;
import com.ktx.model.Phong;
import com.ktx.model.SinhVien;
import com.ktx.service.HopDongService;
import com.ktx.service.PhongService;
import com.ktx.service.SinhVienService;
import com.ktx.view.LapHopDongPanel;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Controller cho màn hình "Lập Hợp Đồng / Xếp Phòng".
 *
 * <p>Nhận {@link LapHopDongPanel} (View) và các Service qua Constructor.
 * Gắn listener vào View, điều phối gọi Service, cập nhật lại View.</p>
 *
 * <p>Tuân thủ MVC: Controller là đầu mối duy nhất kết nối View ↔ Service.</p>
 */
public class HopDongController {

    // ----------------------------------------------------------------
    // Dependencies
    // ----------------------------------------------------------------
    private final LapHopDongPanel view;
    private final HopDongService  hopDongService;
    private final SinhVienService sinhVienService;
    private final PhongService    phongService;
    private final NhanVien        currentUser;  // Nhân viên đang đăng nhập

    // ----------------------------------------------------------------
    // Constructor Injection
    // ----------------------------------------------------------------
    /**
     * @param view            Panel View đã khởi tạo
     * @param hopDongService  Service hợp đồng
     * @param sinhVienService Service sinh viên (để nạp ComboBox)
     * @param phongService    Service phòng (để nạp ComboBox)
     * @param currentUser     Nhân viên đang đăng nhập (dùng làm staff khi lập HĐ)
     */
    public HopDongController(LapHopDongPanel view,
                             HopDongService hopDongService,
                             SinhVienService sinhVienService,
                             PhongService phongService,
                             NhanVien currentUser) {
        this.view            = view;
        this.hopDongService  = hopDongService;
        this.sinhVienService = sinhVienService;
        this.phongService    = phongService;
        this.currentUser     = currentUser;

        initView();
        bindListeners();
    }

    // ----------------------------------------------------------------
    // Init
    // ----------------------------------------------------------------

    /**
     * Nạp dữ liệu ban đầu vào View khi màn hình được mở.
     */
    private void initView() {
        loadComboBoxes();
        loadTable();
    }

    // ----------------------------------------------------------------
    // Bind Listeners – Controller gắn xử lý vào các nút của View
    // ----------------------------------------------------------------
    private void bindListeners() {

        // ---- Nút LẬP HỢP ĐỒNG -----------------------------------------
        view.addLapHopDongListener(e -> handleLapHopDong());
        view.addChamDutListener   (e -> handleThanhLy());
        view.addLamMoiListener    (e -> handleLamMoi());
    }

    // ================================================================
    // Event Handlers
    // ================================================================

    /**
     * Xử lý sự kiện "Lập hợp đồng":
     * <ol>
     *   <li>Đọc dữ liệu từ View và validate cơ bản.</li>
     *   <li>Gọi {@link HopDongService#taoHopDongLuuTru}.</li>
     *   <li>Cập nhật lại JTable và ComboBox phòng.</li>
     *   <li>Hiển thị thông báo thành công / thất bại.</li>
     * </ol>
     */
    private void handleLapHopDong() {
        SinhVien sv    = view.getSelectedSinhVien();
        Phong    phong = view.getSelectedPhong();

        // Validate – đảm bảo người dùng đã chọn
        if (sv == null) {
            showError("Vui lòng chọn sinh viên.");
            return;
        }
        if (phong == null) {
            showError("Vui lòng chọn phòng.");
            return;
        }

        LocalDate batDau   = view.getNgayBatDau();
        LocalDate ketThuc  = view.getNgayKetThuc();

        if (!ketThuc.isAfter(batDau)) {
            showError("Ngày kết thúc phải sau ngày bắt đầu.");
            return;
        }

        try {
            HopDong hopDong = hopDongService.taoHopDongLuuTru(
                    sv.getMaSV(),
                    phong.getMaPhong(),
                    batDau,
                    ketThuc,
                    currentUser);

            showInfo("Lập hợp đồng thành công!\nMã hợp đồng: " + hopDong.getMaHD());
            refreshView();

        } catch (IllegalArgumentException ex) {
            showError("Dữ liệu không hợp lệ:\n" + ex.getMessage());
        } catch (IllegalStateException ex) {
            showError("Không thể lập hợp đồng:\n" + ex.getMessage());
        } catch (Exception ex) {
            showError("Lỗi hệ thống:\n" + ex.getMessage());
        }
    }

    /**
     * Xử lý sự kiện "Thanh lý hợp đồng":
     * Đọc mã HĐ từ dòng đang chọn trong JTable, gọi Service thanh lý,
     * cập nhật lại View.
     */
    private void handleThanhLy() {
        String maHD = view.getSelectedMaHopDong();
        if (maHD == null) {
            showError("Vui lòng chọn hợp đồng cần thanh lý trong bảng.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                view,
                "Xác nhận thanh lý hợp đồng " + maHD + "?\nThao tác này không thể hoàn tác.",
                "Xác nhận thanh lý",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            hopDongService.thanhLyHopDong(maHD);
            showInfo("Thanh lý hợp đồng " + maHD + " thành công.");
            refreshView();

        } catch (IllegalArgumentException ex) {
            showError("Không tìm thấy hợp đồng:\n" + ex.getMessage());
        } catch (IllegalStateException ex) {
            showError("Không thể thanh lý:\n" + ex.getMessage());
        } catch (Exception ex) {
            showError("Lỗi hệ thống:\n" + ex.getMessage());
        }
    }

    /** Làm mới toàn bộ dữ liệu trên View. */
    private void handleLamMoi() {
        refreshView();
    }

    // ================================================================
    // Helpers
    // ================================================================

    /** Nạp lại cả bảng và ComboBox sau mỗi thao tác thay đổi dữ liệu. */
    private void refreshView() {
        loadComboBoxes();
        loadTable();
    }

    private void loadComboBoxes() {
        try {
            List<SinhVien> svList    = sinhVienService.layTatCa();
            List<Phong>    phongList = phongService.layPhongConTrong();
            view.setSinhVienList(svList);
            view.setPhongList(phongList);
        } catch (Exception ex) {
            showError("Không thể tải dữ liệu ComboBox:\n" + ex.getMessage());
        }
    }

    private void loadTable() {
        try {
            List<HopDong> hopDongs = hopDongService.layTatCa();
            view.setHopDongList(hopDongs);
        } catch (Exception ex) {
            showError("Không thể tải danh sách hợp đồng:\n" + ex.getMessage());
        }
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(view, message,
                "Thành công", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(view, message,
                "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
