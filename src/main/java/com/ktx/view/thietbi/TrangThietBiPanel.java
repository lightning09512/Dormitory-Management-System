package com.ktx.view.thietbi;

import com.ktx.model.enums.TinhTrangTB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

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
        // Top Panel: Form & Buttons
        JPanel topPanel = new JPanel(new BorderLayout());

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(3, 4, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông tin Trang Thiết Bị"));

        formPanel.add(new JLabel("Mã TB:"));
        txtMaTB = new JTextField();
        formPanel.add(txtMaTB);

        formPanel.add(new JLabel("Tên TB:"));
        txtTenTB = new JTextField();
        formPanel.add(txtTenTB);

        formPanel.add(new JLabel("Tình trạng:"));
        cbTinhTrang = new JComboBox<>(TinhTrangTB.values());
        formPanel.add(cbTinhTrang);

        formPanel.add(new JLabel("Giá trị:"));
        txtGiaTri = new JTextField();
        formPanel.add(txtGiaTri);

        formPanel.add(new JLabel("Mã Phòng:"));
        txtMaPhong = new JTextField();
        formPanel.add(txtMaPhong);

        topPanel.add(formPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnThem = new JButton("Thêm");
        btnCapNhat = new JButton("Cập nhật");
        btnXoa = new JButton("Xóa");
        btnLamMoi = new JButton("Làm mới");
        btnTimKiem = new JButton("Tìm theo Phòng");

        btnPanel.add(btnThem);
        btnPanel.add(btnCapNhat);
        btnPanel.add(btnXoa);
        btnPanel.add(btnLamMoi);
        btnPanel.add(btnTimKiem);

        topPanel.add(btnPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        // Center Panel: Table
        tableModel = new DefaultTableModel(new Object[]{"Mã TB", "Tên TB", "Tình trạng", "Giá trị", "Mã Phòng"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableThietBi = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableThietBi);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách Trang Thiết Bị"));

        add(scrollPane, BorderLayout.CENTER);
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

    // Table Models
    public DefaultTableModel getTableModel() { return tableModel; }
    public JTable getTableThietBi() { return tableThietBi; }

    // Listeners Registration
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
