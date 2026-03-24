package com.ktx.view;

import com.ktx.model.Phong;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Panel Quản lý Phòng – toolbar + bảng hiện đại.
 */
public class QuanLyPhongPanel extends JPanel {

    private static final String[] COLUMNS = {
            "Mã phòng", "Tên phòng", "Loại", "Sức chứa", "Hiện tại", "Giá phòng", "Trạng thái", "Dãy nhà"
    };

    private final DefaultTableModel tableModel = new DefaultTableModel(COLUMNS, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable table = new JTable(tableModel);

    private final JComboBox<String> cbFilter = new JComboBox<>(
            new String[]{"Tất cả", "Còn trống", "Đang sử dụng", "Bảo trì"});

    private final JButton btnLoc    = UITheme.button("Lọc",       UITheme.BLUE);
    private final JButton btnThem   = UITheme.button("Thêm phòng", UITheme.GREEN);
    private final JButton btnSua    = UITheme.button("Sửa",       UITheme.AMBER);
    private final JButton btnXoa    = UITheme.button("Xóa",       UITheme.RED);
    private final JButton btnLamMoi = UITheme.button("Làm mới",   UITheme.SLATE);

    public QuanLyPhongPanel() {
        super(new BorderLayout(0, 0));
        setBackground(UITheme.CONTENT_BG);
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        buildUI();
    }

    private void buildUI() {
        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 18, 0));
        header.add(UITheme.pageTitle("Quản lý Phòng"), BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // Card
        JPanel card = new JPanel(new BorderLayout(0, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(UITheme.BORDER_COLOR));

        // Toolbar
        JPanel toolbar = new JPanel(new GridBagLayout());
        toolbar.setBackground(Color.WHITE);
        toolbar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, UITheme.BORDER_COLOR),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JLabel filterLabel = new JLabel("Lọc theo TT: ");
        filterLabel.setFont(UITheme.FONT_BOLD);
        filterLabel.setForeground(UITheme.TEXT_SECONDARY);
        cbFilter.setFont(UITheme.FONT_BODY);
        cbFilter.setPreferredSize(new Dimension(150, 34));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 0, 10);

        gbc.gridx = 0; toolbar.add(filterLabel, gbc);
        gbc.gridx = 1; toolbar.add(cbFilter, gbc);
        gbc.gridx = 2; toolbar.add(btnLoc, gbc);

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
        card.add(UITheme.cleanScrollPane(table), BorderLayout.CENTER);

        add(card, BorderLayout.CENTER);
    }

    // ---- Public API ----
    public void setPhongList(List<Phong> list) {
        tableModel.setRowCount(0);
        for (Phong p : list) {
            tableModel.addRow(new Object[]{
                    p.getMaPhong(), p.getTenPhong(), p.getLoaiPhong(),
                    p.getSucChua(), p.getSoNguoiHienTai(),
                    p.getGiaPhong() != null ? String.format("%,.0f ₫", p.getGiaPhong()) : "",
                    p.getTrangThai(), p.getMaDay()
            });
        }
    }

    public String getSelectedFilter() { return (String) cbFilter.getSelectedItem(); }

    public String getSelectedMaPhong() {
        int row = table.getSelectedRow();
        return row < 0 ? null : (String) tableModel.getValueAt(row, 0);
    }

    public void addLocListener   (ActionListener l) { btnLoc.addActionListener(l); }
    public void addThemListener  (ActionListener l) { btnThem.addActionListener(l); }
    public void addSuaListener   (ActionListener l) { btnSua.addActionListener(l); }
    public void addXoaListener    (ActionListener l) { btnXoa.addActionListener(l); }
    public void addLamMoiListener(ActionListener l) { btnLamMoi.addActionListener(l); }
}
