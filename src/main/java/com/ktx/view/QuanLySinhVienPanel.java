package com.ktx.view;

import com.ktx.model.SinhVien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Panel Quản lý Sinh viên – bảng + toolbar hiện đại.
 */
public class QuanLySinhVienPanel extends JPanel {

    private static final String[] COLUMNS = {
            "Mã SV", "Họ tên", "Ngày sinh", "Giới tính", "Lớp", "Khoa", "SĐT", "Email"
    };

    private final DefaultTableModel tableModel = new DefaultTableModel(COLUMNS, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable  table     = new JTable(tableModel);
    private final JTextField txtSearch = new JTextField(20);

    private final JButton btnTim    = UITheme.button("Tìm kiếm", UITheme.BLUE);
    private final JButton btnThem   = UITheme.button("Thêm", UITheme.GREEN);
    private final JButton btnSua    = UITheme.button("Sửa"    , UITheme.AMBER);
    private final JButton btnXoa    = UITheme.button("Xóa"    , UITheme.RED);
    private final JButton btnLamMoi = UITheme.button("Làm mới", UITheme.SLATE);

    public QuanLySinhVienPanel() {
        super(new BorderLayout(0, 0));
        setBackground(UITheme.CONTENT_BG);
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        buildUI();
    }

    private void buildUI() {
        // ---- Header bar ----
        JPanel header = new JPanel(new BorderLayout(16, 0));
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 18, 0));
        header.add(UITheme.pageTitle("Quản lý Sinh viên"), BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // ---- Card chứa toolbar + table ----
        JPanel card = new JPanel(new BorderLayout(0, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(UITheme.BORDER_COLOR));

        // Toolbar
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 12));
        toolbar.setBackground(Color.WHITE);
        toolbar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UITheme.BORDER_COLOR));

        JLabel searchLabel = new JLabel("Tìm kiếm (MSSV/Tên): ");
        searchLabel.setFont(UITheme.FONT_BOLD);
        searchLabel.setForeground(UITheme.TEXT_SECONDARY);
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.BORDER_COLOR),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        txtSearch.setFont(UITheme.FONT_BODY);

        toolbar.add(searchLabel);
        toolbar.add(txtSearch);
        toolbar.add(btnTim);
        toolbar.add(new JSeparator(JSeparator.VERTICAL) {{
            setPreferredSize(new Dimension(1, 28));
            setForeground(UITheme.BORDER_COLOR);
        }});
        toolbar.add(btnThem);
        toolbar.add(btnSua);
        toolbar.add(btnXoa);
        toolbar.add(btnLamMoi);
        card.add(toolbar, BorderLayout.NORTH);

        // Table
        UITheme.styleTable(table);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = UITheme.cleanScrollPane(table);
        card.add(scroll, BorderLayout.CENTER);

        add(card, BorderLayout.CENTER);
    }

    // ---- Public API ----
    public void setSinhVienList(List<SinhVien> list) {
        tableModel.setRowCount(0);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (SinhVien sv : list) {
            tableModel.addRow(new Object[]{
                    sv.getMaSV(), sv.getHoTen(),
                    sv.getNgaySinh() != null ? sv.getNgaySinh().format(fmt) : "",
                    sv.getGioiTinh(), sv.getLop(), sv.getKhoa(),
                    sv.getSoDT(), sv.getEmail()
            });
        }
    }

    public String getKeyword() { return txtSearch.getText().trim(); }

    public String getSelectedMaSV() {
        int row = table.getSelectedRow();
        return row < 0 ? null : (String) tableModel.getValueAt(row, 0);
    }

    public void addTimKiemListener(ActionListener l) { btnTim.addActionListener(l); }
    public void addThemListener   (ActionListener l) { btnThem.addActionListener(l); }
    public void addSuaListener    (ActionListener l) { btnSua.addActionListener(l); }
    public void addXoaListener    (ActionListener l) { btnXoa.addActionListener(l); }
    public void addLamMoiListener (ActionListener l) { btnLamMoi.addActionListener(l); }
}
