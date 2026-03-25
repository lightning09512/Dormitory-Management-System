package com.ktx.view;

import com.ktx.model.CauHinhGia;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.List;

public class QuanLyGiaPanel extends JPanel {

    private static final String[] COLUMNS = {
            "Gói Dịch Vụ", "Đơn giá (VNĐ)", "Ngày Áp Dụng"
    };

    private final DefaultTableModel tableModel = new DefaultTableModel(COLUMNS, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable table = new JTable(tableModel);

    private final JButton btnCapNhat = UITheme.button("Cập nhật Giá", UITheme.AMBER);
    private final JButton btnLamMoi = UITheme.button("Làm mới", UITheme.SLATE);

    public QuanLyGiaPanel() {
        super(new BorderLayout(0, 0));
        setBackground(UITheme.CONTENT_BG);
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        buildUI();
    }

    private void buildUI() {
        JPanel header = new JPanel(new BorderLayout(16, 0));
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 18, 0));
        header.add(UITheme.pageTitle("Cấu hình Đơn giá Dịch vụ"), BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        JPanel card = new JPanel(new BorderLayout(0, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(UITheme.BORDER_COLOR));

        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        toolbar.setBackground(Color.WHITE);
        toolbar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UITheme.BORDER_COLOR));
        toolbar.add(btnCapNhat);
        toolbar.add(btnLamMoi);
        card.add(toolbar, BorderLayout.NORTH);

        UITheme.styleTable(table);
        JScrollPane scroll = UITheme.cleanScrollPane(table);
        card.add(scroll, BorderLayout.CENTER);

        add(card, BorderLayout.CENTER);
    }

    public void setTableModel(List<CauHinhGia> list) {
        tableModel.setRowCount(0);
        DecimalFormat df = new DecimalFormat("#,###.##");
        for (CauHinhGia g : list) {
            tableModel.addRow(new Object[]{
                    g.getLoaiDichVu(),
                    df.format(g.getDonGia()),
                    g.getNgayApDung() != null ? g.getNgayApDung().toString() : "Chưa áp dụng"
            });
        }
    }

    public String getSelectedLoaiDichVu() {
        int r = table.getSelectedRow();
        if (r < 0) return null;
        return table.getValueAt(r, 0).toString();
    }

    public void addCapNhatListener(ActionListener l) { btnCapNhat.addActionListener(l); }
    public void addLamMoiListener(ActionListener l) { btnLamMoi.addActionListener(l); }
}
