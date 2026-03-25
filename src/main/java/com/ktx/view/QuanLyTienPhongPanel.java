package com.ktx.view;

import com.ktx.model.HoaDonTienPhong;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.List;

public class QuanLyTienPhongPanel extends JPanel {

    private static final String[] COLUMNS = {
            "Mã HĐTP", "Mã Hợp Đồng", "Gói đóng (Tháng)", "Tổng tiền (VNĐ)", "Ngày lập", "Trạng thái", "Nhân viên"
    };

    private final DefaultTableModel tableModel = new DefaultTableModel(COLUMNS, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable table = new JTable(tableModel);

    // Lọc / Tìm kiếm
    private final JTextField txtTimKiem = UITheme.textField(20, "Mã HĐ hoặc MSSV...");
    private final JComboBox<String> cboTrangThai = new JComboBox<>(new String[]{"Tất cả", "Chưa thanh toán", "Đã thanh toán"});
    private final JButton btnLoc = UITheme.button("Lọc", UITheme.BLUE);

    private final JButton btnLapHD = UITheme.button("Lập hóa đơn", UITheme.GREEN);
    private final JButton btnThanhToan = UITheme.button("Xác nhận Đóng tiền", UITheme.AMBER);
    private final JButton btnLamMoi = UITheme.button("Làm mới", UITheme.SLATE);

    public QuanLyTienPhongPanel() {
        super(new BorderLayout(0, 0));
        setBackground(UITheme.CONTENT_BG);
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        buildUI();
    }

    private void buildUI() {
        JPanel header = new JPanel(new BorderLayout(16, 0));
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 18, 0));
        header.add(UITheme.pageTitle("Thanh Toán Tiền Phòng"), BorderLayout.WEST);
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
        JLabel lblTim = new JLabel("Tìm kiếm (Mã HD/SV):");
        lblTim.setFont(UITheme.FONT_BOLD);
        gbc.gridx = 0; toolbar.add(lblTim, gbc);
        gbc.gridx = 1; toolbar.add(txtTimKiem, gbc);

        JLabel lblStatus = new JLabel("Trạng thái:");
        lblStatus.setFont(UITheme.FONT_BOLD);
        gbc.gridx = 2; toolbar.add(lblStatus, gbc);
        gbc.gridx = 3; toolbar.add(cboTrangThai, gbc);

        gbc.gridx = 4; toolbar.add(btnLoc, gbc);

        // --- Row 2: Action Buttons ---
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 10);
        JPanel btnActions = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        btnActions.setOpaque(false);
        btnActions.add(btnLapHD);
        btnActions.add(btnThanhToan);
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

    public void setTableModel(List<HoaDonTienPhong> list) {
        tableModel.setRowCount(0);
        DecimalFormat df = new DecimalFormat("#,###.##");
        for (HoaDonTienPhong hd : list) {
            tableModel.addRow(new Object[]{
                    hd.getMaHDTP(),
                    hd.getMaHD(),
                    hd.getGoiThanhToan() + " Tháng",
                    df.format(hd.getTongTien()),
                    hd.getNgayLap().toString(),
                    hd.getTrangThai(),
                    hd.getMaNV()
            });
        }
    }

    public String getSelectedMaHDTP() {
        int r = table.getSelectedRow();
        if (r < 0) return null;
        return table.getValueAt(r, 0).toString();
    }

    public String getSearchKeyword() { return txtTimKiem.getText().trim(); }
    public String getSelectedTrangThai() { return (String) cboTrangThai.getSelectedItem(); }

    public void addLocListener(ActionListener l) { btnLoc.addActionListener(l); }
    public void addLapHDListener(ActionListener l) { btnLapHD.addActionListener(l); }
    public void addThanhToanListener(ActionListener l) { btnThanhToan.addActionListener(l); }
    public void addLamMoiListener(ActionListener l) { btnLamMoi.addActionListener(l); }
}
