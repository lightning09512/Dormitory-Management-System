package com.ktx.controller;

import com.ktx.model.NhanVien;
import com.ktx.service.NhanVienService;
import com.ktx.view.QuanLyNhanVienPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class NhanVienController {

    private final QuanLyNhanVienPanel view;
    private final NhanVienService service;
    private List<NhanVien> fullList;

    public NhanVienController(QuanLyNhanVienPanel view, NhanVienService service) {
        this.view = view;
        this.service = service;
        setupListeners();
        loadAll();
    }

    private void setupListeners() {
        view.addLocListener(e -> filterData());
        view.addThemListener(e -> handleThem());
        view.addSuaListener(e -> handleSua());
        view.addXoaListener(e -> handleXoa());
        view.addResetPassListener(e -> handleResetPass());
        view.addLamMoiListener(e -> loadAll());
    }

    public void loadAll() {
        try {
            fullList = service.getAll();
            filterData();
        } catch (Exception e) {
            showError("Lỗi tải danh sách nhân viên: " + e.getMessage());
        }
    }

    private void filterData() {
        if (fullList == null) return;

        String keyword = view.getSearchKeyword().toLowerCase();
        String roleFilter = view.getSelectedVaiTro();

        List<NhanVien> filtered = fullList.stream()
                .filter(nv -> {
                    boolean matchKeyword = keyword.isEmpty() ||
                            nv.getMaNV().toLowerCase().contains(keyword) ||
                            nv.getHoTen().toLowerCase().contains(keyword) ||
                            nv.getTaiKhoan().toLowerCase().contains(keyword);
                    
                    boolean matchRole = roleFilter.equals("Tất cả") || nv.getVaiTro().equalsIgnoreCase(roleFilter);
                    
                    return matchKeyword && matchRole;
                })
                .collect(Collectors.toList());

        view.setTableModel(filtered);
    }

    private void handleThem() {
        NhanVien nv = openDialog(null);
        if (nv != null) {
            try {
                service.addNhanVien(nv);
                showInfo("Thêm nhân viên thành công!\nMật khẩu mặc định: 123456");
                loadAll();
            } catch (Exception e) {
                showError("Lỗi: " + e.getMessage());
            }
        }
    }

    private void handleSua() {
        String maNV = view.getSelectedMaNV();
        if (maNV == null) {
            showError("Vui lòng chọn nhân viên muốn sửa.");
            return;
        }

        NhanVien selected = fullList.stream().filter(n -> n.getMaNV().equals(maNV)).findFirst().orElse(null);
        if (selected == null) return;

        NhanVien updatedVal = openDialog(selected);
        if (updatedVal != null) {
            try {
                selected.setHoTen(updatedVal.getHoTen());
                selected.setSoDT(updatedVal.getSoDT());
                selected.setVaiTro(updatedVal.getVaiTro());
                
                service.updateNhanVien(selected);
                showInfo("Cập nhật thành công!");
                loadAll();
            } catch (Exception e) {
                showError("Lỗi: " + e.getMessage());
            }
        }
    }

    private void handleXoa() {
        String maNV = view.getSelectedMaNV();
        if (maNV == null) {
            showError("Vui lòng chọn nhân viên muốn xóa.");
            return;
        }

        int result = JOptionPane.showConfirmDialog(view, "Bạn có chắc muốn xóa nhân viên " + maNV + "?",
                "Xác nhận Xóa", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (result == JOptionPane.YES_OPTION) {
            try {
                service.deleteNhanVien(maNV);
                showInfo("Xóa thành công!");
                loadAll();
            } catch (Exception e) {
                showError("Lỗi: " + e.getMessage());
            }
        }
    }

    private void handleResetPass() {
        String maNV = view.getSelectedMaNV();
        if (maNV == null) {
            showError("Vui lòng chọn nhân viên muốn reset mật khẩu.");
            return;
        }

        int result = JOptionPane.showConfirmDialog(view, "Reset mật khẩu của nhân viên " + maNV + " về 123456?",
                "Reset Mật khẩu", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (result == JOptionPane.YES_OPTION) {
            try {
                service.resetPassword(maNV);
                showInfo("Đặt lại mật khẩu thành công!\nMật khẩu mới: 123456");
            } catch (Exception e) {
                showError("Lỗi: " + e.getMessage());
            }
        }
    }

    private NhanVien openDialog(NhanVien existing) {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtHoTen = new JTextField(18);
        JTextField txtSoDT = new JTextField(18);
        JTextField txtTaiKhoan = new JTextField(18);
        JComboBox<String> cboRole = new JComboBox<>(new String[]{"Staff", "Manager"});

        if (existing != null) {
            txtHoTen.setText(existing.getHoTen());
            txtSoDT.setText(existing.getSoDT() != null ? existing.getSoDT() : "");
            txtTaiKhoan.setText(existing.getTaiKhoan());
            txtTaiKhoan.setEditable(false); // Sửa không cho đổi tài khoản
            cboRole.setSelectedItem(existing.getVaiTro());
        }

        gbc.gridx = 0; gbc.gridy = 0; p.add(new JLabel("Họ tên:"), gbc);
        gbc.gridx = 1; p.add(txtHoTen, gbc);

        gbc.gridx = 0; gbc.gridy = 1; p.add(new JLabel("Số điện thoại:"), gbc);
        gbc.gridx = 1; p.add(txtSoDT, gbc);

        gbc.gridx = 0; gbc.gridy = 2; p.add(new JLabel("Tài khoản:"), gbc);
        gbc.gridx = 1; p.add(txtTaiKhoan, gbc);

        gbc.gridx = 0; gbc.gridy = 3; p.add(new JLabel("Vai trò:"), gbc);
        gbc.gridx = 1; p.add(cboRole, gbc);

        String title = existing == null ? "Thêm Nhân Viên" : "Sửa Nhân Viên";
        int result = JOptionPane.showConfirmDialog(view, p, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String hoTen = txtHoTen.getText().trim();
            String soDT = txtSoDT.getText().trim();
            String taiKhoan = txtTaiKhoan.getText().trim();
            String vaiTro = (String) cboRole.getSelectedItem();

            if (hoTen.isEmpty() || taiKhoan.isEmpty()) {
                showError("Họ tên và Tài khoản không được để trống.");
                return null;
            }

            NhanVien nv = new NhanVien();
            nv.setHoTen(hoTen);
            nv.setSoDT(soDT.isEmpty() ? null : soDT);
            nv.setTaiKhoan(taiKhoan);
            nv.setVaiTro(vaiTro);
            return nv;
        }
        return null;
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(view, msg, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    private void showInfo(String msg) {
        JOptionPane.showMessageDialog(view, msg, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
}
