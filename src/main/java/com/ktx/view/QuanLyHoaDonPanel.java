package com.ktx.view;

import com.ktx.model.HoaDon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.List;

public class QuanLyHoaDonPanel extends JPanel {

    private static final String[] COLUMNS = {
            "Mã HĐ", "Tháng", "Năm", "Phòng", "Điện (Cũ-Mới)", "Nước (Cũ-Mới)", "Tổng tiền (VNĐ)", "Trạng thái", "Nhân viên"
    };

    private final DefaultTableModel tableModel = new DefaultTableModel(COLUMNS, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable table = new JTable(tableModel);

    // Lọc theo Tháng Nam
    private final JComboBox<Integer> cboThang = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});
    private final JSpinner spinNam = new JSpinner(new SpinnerNumberModel(2026, 2020, 2050, 1));
    private final JButton btnLoc = UITheme.button("Lọc", UITheme.BLUE);

    private final JButton btnLapHD = UITheme.button("Lập hóa đơn", UITheme.GREEN);
    private final JButton btnThanhToan = UITheme.button("Thanh toán", UITheme.AMBER);
    private final JButton btnXoa = UITheme.button("Xóa", UITheme.RED);
    private final JButton btnLamMoi = UITheme.button("Làm mới", UITheme.SLATE);

    public QuanLyHoaDonPanel() {
        super(new BorderLayout(0, 0));
        setBackground(UITheme.CONTENT_BG);
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        buildUI();
    }

    private void buildUI() {
        JPanel header = new JPanel(new BorderLayout(16, 0));
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 18, 0));
        header.add(UITheme.pageTitle("Hóa đơn điện nước"), BorderLayout.WEST);
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

        // --- Row 1: Filter ---
        gbc.gridy = 0;
        JLabel lblThang = new JLabel("Tháng:");
        lblThang.setFont(UITheme.FONT_BOLD);
        gbc.gridx = 0; toolbar.add(lblThang, gbc);
        cboThang.setPreferredSize(new Dimension(80, 42));
        gbc.gridx = 1; toolbar.add(cboThang, gbc);

        JLabel lblNam = new JLabel("Năm:");
        lblNam.setFont(UITheme.FONT_BOLD);
        gbc.gridx = 2; toolbar.add(lblNam, gbc);
        spinNam.setPreferredSize(new Dimension(100, 42));
        gbc.gridx = 3; toolbar.add(spinNam, gbc);

        gbc.gridx = 4; toolbar.add(btnLoc, gbc);

        // --- Row 2: Actions ---
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 10);
        JPanel btnActions = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        btnActions.setOpaque(false);
        btnActions.add(btnLapHD);
        btnActions.add(btnThanhToan);
        btnActions.add(btnXoa);
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

    public void setTableModel(List<HoaDon> list) {
        tableModel.setRowCount(0);
        DecimalFormat df = new DecimalFormat("#,###.##");
        for (HoaDon hd : list) {
            tableModel.addRow(new Object[]{
                    hd.getMaHDon(),
                    hd.getThang(),
                    hd.getNam(),
                    hd.getMaPhong(),
                    hd.getChiSoDienCu() + " - " + hd.getChiSoDienMoi(),
                    hd.getChiSoNuocCu() + " - " + hd.getChiSoNuocMoi(),
                    df.format(hd.getTongTien()),
                    hd.getTrangThaiThanhToan(),
                    hd.getMaNV()
            });
        }
    }

    public String getSelectedMaHDon() {
        int r = table.getSelectedRow();
        if (r < 0) return null;
        return table.getValueAt(r, 0).toString();
    }

    public int getSelectedThang() { return (Integer) cboThang.getSelectedItem(); }
    public int getSelectedNam() { return (Integer) spinNam.getValue(); }

    public void addLocListener(ActionListener l) { btnLoc.addActionListener(l); }
    public void addLapHDListener(ActionListener l) { btnLapHD.addActionListener(l); }
    public void addThanhToanListener(ActionListener l) { btnThanhToan.addActionListener(l); }
    public void addXoaListener(ActionListener l) { btnXoa.addActionListener(l); }
    public void addLamMoiListener(ActionListener l) { btnLamMoi.addActionListener(l); }
}
