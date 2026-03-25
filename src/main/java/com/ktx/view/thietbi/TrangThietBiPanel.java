package com.ktx.view.thietbi;

import com.ktx.view.UITheme;
import com.ktx.model.enums.TinhTrangTB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

/**
 * Panel Quản lý Trang Thiết Bị – Giao diện hiện đại đồng bộ.
 */
public class TrangThietBiPanel extends JPanel {

    private JTextField txtMaTB;
    private JTextField txtTenTB;
    private JTextField txtGiaTri;
    private JTextField txtMaPhong;
    private JComboBox<TinhTrangTB> cbTinhTrang;

    private JButton btnThem;
    private JButton btnCapNhat;
    private JButton btnXoa;
    private JButton btnLamMoi;
    private JButton btnTimKiem;

    private JTable tableThietBi;
    private DefaultTableModel tableModel;

    public TrangThietBiPanel() {
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        setBackground(UITheme.CONTENT_BG);
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        // Header Title
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 18, 0));
        header.add(UITheme.pageTitle("Quản lý Trang Thiết Bị"), BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // Main content card
        JPanel card = new JPanel(new BorderLayout(0, 24));
        card.setBackground(Color.WHITE);
        card.putClientProperty("FlatLaf.style", "arc: 16");
        card.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        // 1. Form Panel
        JPanel formPanel = new JPanel(new GridLayout(3, 4, 20, 16));
        formPanel.setBackground(Color.WHITE);

        formPanel.add(label("Mã Thiết Bị:"));
        txtMaTB = UITheme.textField(15, "VD: QUAT_01");
        formPanel.add(txtMaTB);

        formPanel.add(label("Tên Thiết Bị:"));
        txtTenTB = UITheme.textField(15, "VD: Quạt trần");
        formPanel.add(txtTenTB);

        formPanel.add(label("Tình trạng:"));
        cbTinhTrang = new JComboBox<>(TinhTrangTB.values());
        cbTinhTrang.setFont(UITheme.FONT_BODY);
        cbTinhTrang.setPreferredSize(new Dimension(160, 42));
        formPanel.add(cbTinhTrang);

        formPanel.add(label("Giá trị (VNĐ):"));
        txtGiaTri = UITheme.textField(15, "Nhập số tiền...");
        formPanel.add(txtGiaTri);

        formPanel.add(label("Mã Phòng:"));
        txtMaPhong = UITheme.textField(15, "Nhập số phòng...");
        formPanel.add(txtMaPhong);

        card.add(formPanel, BorderLayout.NORTH);

        // 2. Buttons Toolbar
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        btnPanel.setBackground(Color.WHITE);
        
        btnThem = UITheme.button("Thêm", UITheme.INDIGO);
        btnCapNhat = UITheme.button("Cập nhật", UITheme.AMBER);
        btnXoa = UITheme.button("Xóa", UITheme.RED);
        btnLamMoi = UITheme.button("Làm mới", UITheme.SLATE);
        btnTimKiem = UITheme.button("Tìm theo Phòng", UITheme.BLUE);

        btnPanel.add(btnThem);
        btnPanel.add(btnCapNhat);
        btnPanel.add(btnXoa);
        btnPanel.add(btnLamMoi);
        btnPanel.add(btnTimKiem);

        JPanel btnWrapper = new JPanel(new BorderLayout());
        btnWrapper.setBackground(Color.WHITE);
        btnWrapper.add(btnPanel, BorderLayout.WEST);
        card.add(btnWrapper, BorderLayout.CENTER);

        // 3. Table List
        tableModel = new DefaultTableModel(new Object[]{"Mã TB", "Tên TB", "Tình trạng", "Giá trị", "Mã Phòng"}, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        tableThietBi = new JTable(tableModel);
        UITheme.styleTable(tableThietBi);
        
        // Custom renderer for status and currency
        tableThietBi.getColumnModel().getColumn(2).setCellRenderer(new StatusCellRenderer());
        tableThietBi.getColumnModel().getColumn(3).setCellRenderer(new CurrencyCellRenderer());

        JScrollPane scrollPane = UITheme.cleanScrollPane(tableThietBi);
        scrollPane.setPreferredSize(new Dimension(0, 350));
        card.add(scrollPane, BorderLayout.SOUTH);

        add(card, BorderLayout.CENTER);
    }

    private JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setFont(UITheme.FONT_BOLD);
        l.setForeground(UITheme.TEXT_SECONDARY);
        return l;
    }

    // --- Inner Renderers ---
    private static class StatusCellRenderer extends javax.swing.table.DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable t, Object v, boolean s, boolean f, int r, int c) {
            Component comp = super.getTableCellRendererComponent(t, v, s, f, r, c);
            if (v == null) return comp;
            String status = v.toString();
            if ("Tốt".equalsIgnoreCase(status)) {
                setForeground(new Color(34, 197, 94)); // Green
            } else if ("Hỏng".equalsIgnoreCase(status)) {
                setForeground(new Color(239, 68, 68)); // Red
            } else if ("Đang sửa".equalsIgnoreCase(status)) {
                setForeground(new Color(245, 158, 11)); // Amber
            } else {
                setForeground(UITheme.TEXT_PRIMARY);
            }
            setFont(UITheme.FONT_BOLD);
            setHorizontalAlignment(JLabel.CENTER);
            return comp;
        }
    }

    private static class CurrencyCellRenderer extends javax.swing.table.DefaultTableCellRenderer {
        private final java.text.DecimalFormat df = new java.text.DecimalFormat("#,### VNĐ");
        @Override
        public Component getTableCellRendererComponent(JTable t, Object v, boolean s, boolean f, int r, int c) {
            if (v != null && v instanceof String) {
                try {
                    double val = Double.parseDouble((String) v);
                    v = df.format(val);
                } catch (Exception e) {}
            }
            Component comp = super.getTableCellRendererComponent(t, v, s, f, r, c);
            setHorizontalAlignment(JLabel.RIGHT);
            return comp;
        }
    }

    // Getters for form inputs
    public String getMaTB() { return txtMaTB.getText().trim(); }
    public String getTenTB() { return txtTenTB.getText().trim(); }
    public TinhTrangTB getTinhTrang() { return (TinhTrangTB) cbTinhTrang.getSelectedItem(); }
    public String getGiaTri() { return txtGiaTri.getText().trim(); }
    public String getMaPhong() { return txtMaPhong.getText().trim(); }

    public void setMaTB(String maTB) { txtMaTB.setText(maTB); }
    public void setTenTB(String tenTB) { txtTenTB.setText(tenTB); }
    public void setTinhTrang(TinhTrangTB tinhTrang) { cbTinhTrang.setSelectedItem(tinhTrang); }
    public void setGiaTri(String giaTri) { txtGiaTri.setText(giaTri); }
    public void setMaPhong(String maPhong) { txtMaPhong.setText(maPhong); }

    public DefaultTableModel getTableModel() { return tableModel; }
    public JTable getTableThietBi() { return tableThietBi; }

    public void addThemListener(ActionListener listener) { btnThem.addActionListener(listener); }
    public void addCapNhatListener(ActionListener listener) { btnCapNhat.addActionListener(listener); }
    public void addXoaListener(ActionListener listener) { btnXoa.addActionListener(listener); }
    public void addLamMoiListener(ActionListener listener) { btnLamMoi.addActionListener(listener); }
    public void addTimKiemListener(ActionListener listener) { btnTimKiem.addActionListener(listener); }
    public void addTableMouseListener(MouseAdapter listener) { tableThietBi.addMouseListener(listener); }
    
    public void clearForm() {
        txtMaTB.setText("");
        txtTenTB.setText("");
        if (cbTinhTrang.getItemCount() > 0) cbTinhTrang.setSelectedIndex(0);
        txtGiaTri.setText("");
        txtMaPhong.setText("");
        txtMaTB.requestFocus();
    }
}
