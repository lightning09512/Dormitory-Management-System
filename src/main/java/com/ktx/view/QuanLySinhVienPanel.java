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
            "Mã SV", "Họ tên", "Phòng", "Ngày sinh", "Giới tính", "Lớp", "Khoa", "SĐT", "Email"
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
        // ---- Header bar with decorative image ----
        JPanel header = new JPanel(new BorderLayout(16, 0));
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 18, 0));
        
        // Add decorative image on the right
        ImageIcon decorIcon = new ImageIcon("sinh-vien-truong-nao-duoc-thue-ktx-khu-b-dhqg-tphcm-o-tro-hay-o-ktx-tiet-kiem-hon.webp");
        Image scaledDecor = decorIcon.getImage().getScaledInstance(120, 80, Image.SCALE_SMOOTH);
        JLabel decorLabel = new JLabel(new ImageIcon(scaledDecor));
        decorLabel.setBorder(BorderFactory.createLineBorder(UITheme.BORDER_COLOR));
        header.add(UITheme.pageTitle("Quản lý Sinh viên"), BorderLayout.WEST);
        header.add(decorLabel, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // ---- Card chứa toolbar + table ----
        JPanel card = new JPanel(new BorderLayout(0, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(UITheme.BORDER_COLOR));

        JPanel toolbar = new JPanel(new GridBagLayout());
        toolbar.setBackground(Color.WHITE);
        toolbar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, UITheme.BORDER_COLOR),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JLabel searchLabel = new JLabel("Tìm kiếm (MSSV/Tên): ");
        searchLabel.setFont(UITheme.FONT_BOLD);
        searchLabel.setForeground(UITheme.TEXT_SECONDARY);
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.BORDER_COLOR),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        txtSearch.setFont(UITheme.FONT_BODY);
        txtSearch.setPreferredSize(new Dimension(220, 36));
        txtSearch.setMinimumSize(new Dimension(200, 36));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 0, 10);

        gbc.gridx = 0; toolbar.add(searchLabel, gbc);
        gbc.gridx = 1; toolbar.add(txtSearch, gbc);
        gbc.gridx = 2; toolbar.add(btnTim, gbc);

        // Khoảng trống đẩy các nút về bên phải
        gbc.gridx = 3; gbc.weightx = 1.0; toolbar.add(Box.createHorizontalGlue(), gbc);

        gbc.weightx = 0;
        gbc.gridx = 4; toolbar.add(btnThem, gbc);
        gbc.gridx = 5; toolbar.add(btnSua, gbc);
        gbc.gridx = 6; toolbar.add(btnXoa, gbc);
        gbc.gridx = 7; gbc.insets = new Insets(0, 0, 0, 0); toolbar.add(btnLamMoi, gbc);

        card.add(toolbar, BorderLayout.NORTH);

        // Table
        UITheme.styleTable(table);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Tinh chỉnh độ rộng ưu tiên của các cột
        int[] preferredWidths = {90, 170, 110, 100, 80, 100, 140, 110, 200};
        for (int i = 0; i < preferredWidths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(preferredWidths[i]);
            table.getColumnModel().getColumn(i).setMinWidth(preferredWidths[i] - 20);
        }

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
                    sv.getMaSV(), sv.getHoTen(), sv.getPhongHienTai(),
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
