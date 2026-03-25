package com.ktx.controller;

import com.ktx.model.SinhVien;
import com.ktx.service.SinhVienService;
import com.ktx.view.QuanLySinhVienPanel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Controller quản lý sinh viên.
 */
public class SinhVienController {

    private final QuanLySinhVienPanel view;
    private final SinhVienService     service;
    
    private int currentPage = 1;
    private final int pageSize = 20;

    public SinhVienController(QuanLySinhVienPanel view, SinhVienService service) {
        this.view    = view;
        this.service = service;

        view.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                loadAll();
            }
        });

        loadAll();
        bindListeners();
    }

    private void bindListeners() {
        view.addTimKiemListener(e -> handleTimKiem());
        view.addThemListener   (e -> handleThem());
        view.addSuaListener    (e -> handleSua());
        view.addXoaListener    (e -> handleXoa());
        view.addLamMoiListener (e -> { currentPage = 1; loadAll(); });
        
        view.addPrevPageListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                loadAll();
            }
        });
        view.addNextPageListener(e -> {
            currentPage++;
            loadAll();
        });
    }

    // ----------------------------------------------------------------
    // Handlers
    // ----------------------------------------------------------------
    private void handleTimKiem() {
        currentPage = 1; // Reset về trang 1 khi tìm kiếm mới
        loadAll();
    }

    private void handleThem() {
        SinhVienFormDialog dialog = new SinhVienFormDialog(null, "Thêm sinh viên mới", null);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            try {
                service.themSinhVien(dialog.getSinhVien());
                showInfo("Thêm sinh viên thành công!");
                loadAll();
            } catch (Exception ex) {
                showError("Lỗi thêm sinh viên:\n" + ex.getMessage());
            }
        }
    }

    private void handleSua() {
        String maSV = view.getSelectedMaSV();
        if (maSV == null) { showError("Vui lòng chọn sinh viên cần sửa."); return; }
        try {
            SinhVien existing = service.timTheoMa(maSV);
            SinhVienFormDialog dialog = new SinhVienFormDialog(null, "Sửa thông tin sinh viên", existing);
            dialog.setVisible(true);
            if (dialog.isConfirmed()) {
                service.capNhatSinhVien(dialog.getSinhVien());
                showInfo("Cập nhật thành công!");
                loadAll();
            }
        } catch (Exception ex) {
            showError("Lỗi:\n" + ex.getMessage());
        }
    }

    private void handleXoa() {
        String maSV = view.getSelectedMaSV();
        if (maSV == null) { showError("Vui lòng chọn sinh viên cần xóa."); return; }
        int confirm = JOptionPane.showConfirmDialog(view,
                "Xác nhận xóa sinh viên " + maSV + "?",
                "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm != JOptionPane.YES_OPTION) return;
        try {
            service.xoaSinhVien(maSV);
            showInfo("Xóa thành công!");
            loadAll();
        } catch (Exception ex) {
            showError("Lỗi xóa sinh viên:\n" + ex.getMessage());
        }
    }

    private void loadAll() {
        try {
            String kw = view.getKeyword();
            long totalCount;
            List<SinhVien> list;

            if (kw.isEmpty()) {
                totalCount = service.demTongSo();
                list = service.layDanhSachPhanTrang(currentPage, pageSize);
            } else {
                totalCount = service.demTheoKeyword(kw);
                list = service.timTheoKeywordPhanTrang(kw, currentPage, pageSize);
            }

            int totalPages = (int) Math.ceil((double) totalCount / pageSize);
            view.setSinhVienList(list);
            view.setPaginationInfo(currentPage, totalPages, totalCount);
        } catch (Exception ex) {
            showError("Lỗi tải dữ liệu:\n" + ex.getMessage());
        }
    }

    private void showInfo(String msg)  { JOptionPane.showMessageDialog(view, msg, "Thành công", JOptionPane.INFORMATION_MESSAGE); }
    private void showError(String msg) { JOptionPane.showMessageDialog(view, msg, "Lỗi", JOptionPane.ERROR_MESSAGE); }

    // ----------------------------------------------------------------
    // Inner dialog – Form thêm/sửa sinh viên
    // ----------------------------------------------------------------
    private static class SinhVienFormDialog extends JDialog {
        private boolean confirmed = false;
        private final JTextField txtMaSV    = new JTextField(12);
        private final JTextField txtHoTen   = new JTextField(20);
        private final JTextField txtNgaySinh= new JTextField(12); // yyyy-MM-dd
        private final JComboBox<String> cbGioiTinh = new JComboBox<>(new String[]{"Nam","Nữ","Khác"});
        private final JTextField txtLop     = new JTextField(12);
        private final JTextField txtKhoa    = new JTextField(20);
        private final JTextField txtSoDT    = new JTextField(12);
        private final JTextField txtEmail   = new JTextField(20);
        private final JTextField txtTaiKhoan= new JTextField(20);
        private final JPasswordField txtMK  = new JPasswordField(20);

        SinhVienFormDialog(JFrame parent, String title, SinhVien sv) {
            super(parent, title, true);
            if (sv != null) prefill(sv);
            buildUI();
            pack();
            setLocationRelativeTo(parent);
        }

        private void prefill(SinhVien sv) {
            txtMaSV.setText(sv.getMaSV()); txtMaSV.setEditable(false);
            txtHoTen.setText(sv.getHoTen());
            if (sv.getNgaySinh() != null) txtNgaySinh.setText(sv.getNgaySinh().toString());
            cbGioiTinh.setSelectedItem(sv.getGioiTinh());
            txtLop.setText(sv.getLop()); txtKhoa.setText(sv.getKhoa());
            txtSoDT.setText(sv.getSoDT()); txtEmail.setText(sv.getEmail());
            txtTaiKhoan.setText(sv.getTaiKhoan());
        }

        private void buildUI() {
            JPanel p = new JPanel(new GridBagLayout());
            p.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
            GridBagConstraints lc = new GridBagConstraints();
            lc.anchor = GridBagConstraints.WEST; lc.insets = new Insets(4,4,4,8);
            GridBagConstraints fc = new GridBagConstraints();
            fc.gridx = 1; fc.fill = GridBagConstraints.HORIZONTAL;
            fc.weightx = 1; fc.insets = new Insets(4,0,4,4);

            String[] labels = {"Mã SV:","Họ tên:","Ngày sinh\n(yyyy-MM-dd):","Giới tính:","Lớp:","Khoa:","SĐT:","Email:","Tài khoản:","Mật khẩu:"};
            JComponent[] fields = {txtMaSV, txtHoTen, txtNgaySinh, cbGioiTinh, txtLop, txtKhoa, txtSoDT, txtEmail, txtTaiKhoan, txtMK};
            for (int i = 0; i < labels.length; i++) {
                lc.gridy = i; fc.gridy = i;
                p.add(new JLabel(labels[i]), lc);
                p.add(fields[i], fc);
            }

            JButton btnOK = new JButton("✅ Lưu");
            btnOK.addActionListener(e -> { confirmed = true; dispose(); });
            JButton btnCancel = new JButton("Hủy");
            btnCancel.addActionListener(e -> dispose());
            JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            btns.add(btnCancel); btns.add(btnOK);

            setLayout(new BorderLayout());
            add(p, BorderLayout.CENTER);
            add(btns, BorderLayout.SOUTH);
        }

        boolean isConfirmed() { return confirmed; }

        SinhVien getSinhVien() {
            LocalDate ngaySinh = null;
            try { if (!txtNgaySinh.getText().isBlank()) ngaySinh = LocalDate.parse(txtNgaySinh.getText().trim()); }
            catch (Exception ignored) {}
            String matKhau = new String(txtMK.getPassword());
            return new SinhVien(
                txtMaSV.getText().trim(),
                txtHoTen.getText().trim(),
                ngaySinh,
                (String) cbGioiTinh.getSelectedItem(),
                txtLop.getText().trim(),
                txtKhoa.getText().trim(),
                txtSoDT.getText().trim(),
                null,
                txtEmail.getText().trim(),
                txtTaiKhoan.getText().trim(),
                matKhau.isEmpty() ? null : matKhau
            );
        }
    }
}
