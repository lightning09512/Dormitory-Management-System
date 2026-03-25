package com.ktx.view;

import com.ktx.model.NhanVien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class QuanLyNhanVienPanel extends JPanel {

    private static final String[] COLUMNS = {
            "Mã NV", "Họ Tên", "Vai Trò", "Số ĐT", "Tài Khoản"
    };

    private final DefaultTableModel tableModel = new DefaultTableModel(COLUMNS, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable table = new JTable(tableModel);

    // Lọc / Tìm kiếm
    private final JTextField txtTimKiem = UITheme.textField(20, "Tên hoặc Mã NV...");
    private final JComboBox<String> cboVaiTro = new JComboBox<>(new String[]{"Tất cả", "Staff", "Manager"});
    private final JButton btnLoc = UITheme.button("Lọc", UITheme.BLUE);

    private final JButton btnThem = UITheme.button("Thêm Nhân viên", UITheme.GREEN);
    private final JButton btnSua = UITheme.button("Sửa", UITheme.AMBER);
    private final JButton btnXoa = UITheme.button("Xóa", UITheme.RED);
    private final JButton btnResetPass = UITheme.button("Reset Mật khẩu", UITheme.INDIGO);
    private final JButton btnLamMoi = UITheme.button("Làm mới", UITheme.SLATE);

    public QuanLyNhanVienPanel() {
        super(new BorderLayout(0, 0));
        setBackground(UITheme.CONTENT_BG);
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        buildUI();
    }

    private void buildUI() {
        JPanel header = new JPanel(new BorderLayout(16, 0));
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 18, 0));
        header.add(UITheme.pageTitle("Quản lý Nhân viên"), BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        JPanel card = new JPanel(new BorderLayout(0, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(UITheme.BORDER_COLOR));

        JPanel toolbar = new JPanel(new GridBagLayout());
        toolbar.setBackground(Color.WHITE);
        toolbar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, UITheme.BORDER_COLOR),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;

        // --- Row 1: Filter/Search ---
        gbc.gridy = 0;
        JLabel lblTim = new JLabel("Tìm kiếm (Tên/Mã):");
        lblTim.setFont(UITheme.FONT_BOLD);
        gbc.gridx = 0; toolbar.add(lblTim, gbc);
        gbc.gridx = 1; toolbar.add(txtTimKiem, gbc);

        JLabel lblRole = new JLabel("Vai trò:");
        lblRole.setFont(UITheme.FONT_BOLD);
        gbc.gridx = 2; toolbar.add(lblRole, gbc);
        gbc.gridx = 3; toolbar.add(cboVaiTro, gbc);

        gbc.gridx = 4; toolbar.add(btnLoc, gbc);

        // --- Row 2: Action Buttons ---
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 10);
        JPanel btnActions = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        btnActions.setOpaque(false);
        btnActions.add(btnThem);
        btnActions.add(btnSua);
        btnActions.add(btnXoa);
        btnActions.add(btnResetPass);
        btnActions.add(btnLamMoi);
        
        gbc.gridx = 0;
        gbc.gridwidth = 5;
        toolbar.add(btnActions, gbc);

        card.add(toolbar, BorderLayout.NORTH);

        UITheme.styleTable(table);
        JScrollPane scroll = UITheme.cleanScrollPane(table);
        card.add(scroll, BorderLayout.CENTER);

        add(card, BorderLayout.CENTER);
    }

    public void setTableModel(List<NhanVien> list) {
        tableModel.setRowCount(0);
        for (NhanVien nv : list) {
            tableModel.addRow(new Object[]{
                    nv.getMaNV(),
                    nv.getHoTen(),
                    nv.getVaiTro(),
                    nv.getSoDT(),
                    nv.getTaiKhoan()
            });
        }
    }

    public String getSelectedMaNV() {
        int r = table.getSelectedRow();
        if (r < 0) return null;
        return table.getValueAt(r, 0).toString();
    }

    public String getSearchKeyword() { return txtTimKiem.getText().trim(); }
    public String getSelectedVaiTro() { return (String) cboVaiTro.getSelectedItem(); }

    public void addLocListener(ActionListener l) { btnLoc.addActionListener(l); }
    public void addThemListener(ActionListener l) { btnThem.addActionListener(l); }
    public void addSuaListener(ActionListener l) { btnSua.addActionListener(l); }
    public void addXoaListener(ActionListener l) { btnXoa.addActionListener(l); }
    public void addResetPassListener(ActionListener l) { btnResetPass.addActionListener(l); }
    public void addLamMoiListener(ActionListener l) { btnLamMoi.addActionListener(l); }
}
