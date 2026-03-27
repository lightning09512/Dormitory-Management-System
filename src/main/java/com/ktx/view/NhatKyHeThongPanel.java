package com.ktx.view;

import com.ktx.model.AuditLog;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Giao diện xem Nhật ký hệ thống (Audit Logs).
 */
public class NhatKyHeThongPanel extends JPanel {

    private final JTable table;
    private final DefaultTableModel model;
    private final JTextField txtSearch = UITheme.textField(20);
    private final JButton btnSearch = UITheme.button("Tìm kiếm", UITheme.BLUE);
    private final JButton btnRefresh = UITheme.button("Làm mới", UITheme.SLATE);
    private final JButton btnClear = UITheme.button("Xóa tất cả nhật ký", new Color(220, 38, 38));

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public NhatKyHeThongPanel() {
        super(new BorderLayout(0, 0));
        
        // Initialize final fields
        String[] columns = {"ID", "Thời gian", "Nhân viên", "Hành động", "Chi tiết"};
        model = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);

        setBackground(UITheme.CONTENT_BG);
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        buildUI();
    }

    private void buildUI() {
        // --- Header ---
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 18, 0));
        header.add(UITheme.pageTitle("Nhật ký hệ thống (Audit Logs)"), BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // --- Card containing Toolbar & Table ---
        JPanel card = new JPanel(new BorderLayout(0, 0));
        card.setBackground(Color.WHITE);
        card.putClientProperty("FlatLaf.style", "arc: 16");
        card.setBorder(BorderFactory.createLineBorder(UITheme.BORDER_COLOR));

        // --- Toolbar ---
        JPanel toolbar = new JPanel(new GridBagLayout());
        toolbar.setBackground(Color.WHITE);
        toolbar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, UITheme.BORDER_COLOR),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 0, 12);

        JLabel searchLabel = new JLabel("Tìm kiếm:");
        searchLabel.setFont(UITheme.FONT_BOLD);
        searchLabel.setForeground(UITheme.TEXT_SECONDARY);

        txtSearch.putClientProperty("JTextField.placeholderText", "Tìm theo Nhân viên, Hành động...");
        txtSearch.setPreferredSize(new Dimension(300, 42));

        gbc.gridx = 0; toolbar.add(searchLabel, gbc);
        gbc.gridx = 1; toolbar.add(txtSearch, gbc);
        gbc.gridx = 2; toolbar.add(btnSearch, gbc);
        gbc.gridx = 3; toolbar.add(btnRefresh, gbc);
        
        gbc.gridx = 4;
        gbc.weightx = 1.0; // Push following components to the right
        toolbar.add(new Box.Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(32767, 32767)), gbc);
        
        gbc.gridx = 5;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, 0, 0);
        toolbar.add(btnClear, gbc);

        card.add(toolbar, BorderLayout.NORTH);

        UITheme.styleTable(table);
        
        // Căn chỉnh độ rộng cột
        table.getColumnModel().getColumn(0).setMaxWidth(60);
        table.getColumnModel().getColumn(1).setPreferredWidth(180);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        table.getColumnModel().getColumn(4).setPreferredWidth(400);

        JScrollPane scrollPane = UITheme.cleanScrollPane(table);
        card.add(scrollPane, BorderLayout.CENTER);

        add(card, BorderLayout.CENTER);
    }

    public void setLogs(List<AuditLog> logs) {
        model.setRowCount(0);
        if (logs != null) {
            for (AuditLog log : logs) {
                model.addRow(new Object[]{
                    log.getId(),
                    log.getThoiGian() != null ? log.getThoiGian().format(formatter) : "",
                    log.getMaNV(),
                    log.getHanhDong(),
                    log.getChiTiet()
                });
            }
        }
    }

    public String getSearchKeyword() { return txtSearch.getText().trim(); }

    public void addSearchListener(ActionListener l) { btnSearch.addActionListener(l); }
    public void addRefreshListener(ActionListener l) { btnRefresh.addActionListener(l); }
    public void addClearListener(ActionListener l) { btnClear.addActionListener(l); }
}
