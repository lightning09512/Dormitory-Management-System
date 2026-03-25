package com.ktx.controller;

import com.ktx.model.HoaDonTienPhong;
import com.ktx.model.HopDong;
import com.ktx.repository.HopDongRepository;
import com.ktx.service.HoaDonTienPhongService;
import com.ktx.view.QuanLyTienPhongPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class HoaDonTienPhongController {

    private final QuanLyTienPhongPanel view;
    private final HoaDonTienPhongService service;
    private final HopDongRepository hopDongRepo;
    private final String maNVDangNhap;

    public HoaDonTienPhongController(QuanLyTienPhongPanel view, 
                                     HoaDonTienPhongService service, 
                                     HopDongRepository hopDongRepo,
                                     String maNV) {
        this.view = view;
        this.service = service;
        this.hopDongRepo = hopDongRepo;
        this.maNVDangNhap = maNV;
        bindListeners();
        loadAll();
    }

    private void bindListeners() {
        view.addLocListener(e -> loc());
        view.addLamMoiListener(e -> loadAll());
        view.addThanhToanListener(e -> thanhToan());
        view.addLapHDListener(e -> lapMoi());
    }

    private void loadAll() {
        try {
            List<HoaDonTienPhong> list = service.layTatCa();
            view.setTableModel(list);
        } catch (Exception ex) {
            showError("Lỗi tải danh sách: " + ex.getMessage());
        }
    }

    private void loc() {
        String keyword = view.getSearchKeyword().toLowerCase();
        String trangThai = view.getSelectedTrangThai();

        try {
            List<HoaDonTienPhong> list = service.layTatCa().stream()
                .filter(h -> {
                    boolean matchKeyword = keyword.isEmpty() 
                        || h.getMaHDTP().toLowerCase().contains(keyword)
                        || h.getMaHD().toLowerCase().contains(keyword);
                    
                    boolean matchStatus = "Tất cả".equals(trangThai) 
                        || h.getTrangThai().equals(trangThai);

                    return matchKeyword && matchStatus;
                })
                .collect(Collectors.toList());

            view.setTableModel(list);
        } catch (Exception ex) {
            showError("Lỗi lọc danh sách: " + ex.getMessage());
        }
    }

    private void thanhToan() {
        String maHDTP = view.getSelectedMaHDTP();
        if (maHDTP == null) {
            showError("Vui lòng chọn 1 hóa đơn để thanh toán.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, 
            "Xác nhận sinh viên đã đóng tiền cho hóa đơn " + maHDTP + "?", 
            "Xác nhận Thanh toán", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                service.xacNhanThanhToan(maHDTP);
                showInfo("Xác nhận thanh toán thành công!");
                loadAll();
            } catch (Exception ex) {
                showError(ex.getMessage());
            }
        }
    }

    private void lapMoi() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        List<HopDong> listHD;
        try {
            listHD = hopDongRepo.findHopDongHieuLuc();
        } catch (Exception e) {
            showError("Lỗi tải danh sách hợp đồng: " + e.getMessage());
            return;
        }

        if (listHD.isEmpty()) {
            showInfo("Không có Hợp đồng nào đang hiệu lực để lập hóa đơn.");
            return;
        }

        // Components
        JTextField txtSearch = new JTextField(15);
        JComboBox<String> cboHopDong = new JComboBox<>();
        JTextField txtGoi = new JTextField();
        txtGoi.setEditable(false);
        txtGoi.setBackground(new Color(245, 247, 250));

        // Model cho ComboBox
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        cboHopDong.setModel(model);

        // Hàm cập nhật danh sách Combo dựa trên tìm kiếm
        Runnable updateCombo = () -> {
            String keyword = txtSearch.getText().toLowerCase();
            model.removeAllElements();
            for (HopDong hd : listHD) {
                String itemText = hd.getMaHD() + " (SV: " + hd.getMaSV() + ")";
                if (itemText.toLowerCase().contains(keyword)) {
                    model.addElement(itemText);
                }
            }
        };

        // Bỏ listener Document để filter
        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateCombo.run(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateCombo.run(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateCombo.run(); }
        });

        // Khởi tạo danh sách ban đầu
        updateCombo.run();

        // Listener cập nhật Gói tự động khi chọn hợp đồng
        cboHopDong.addActionListener(e -> {
            String selectedItem = (String) cboHopDong.getSelectedItem();
            if (selectedItem != null) {
                String maHD = selectedItem.split(" ")[0];
                listHD.stream()
                    .filter(hd -> hd.getMaHD().equals(maHD))
                    .findFirst()
                    .ifPresent(hd -> {
                        if (hd.getNgayBatDau() != null && hd.getNgayKetThuc() != null) {
                            long months = java.time.temporal.ChronoUnit.MONTHS.between(hd.getNgayBatDau(), hd.getNgayKetThuc());
                            txtGoi.setText(months + " Tháng");
                        } else {
                            txtGoi.setText("Không xác định");
                        }
                    });
            } else {
                txtGoi.setText("");
            }
        });

        // Kích hoạt listener ban đầu
        if (cboHopDong.getItemCount() > 0) {
            cboHopDong.setSelectedIndex(0);
        }

        // Layout
        gbc.gridx = 0; gbc.gridy = 0; p.add(new JLabel("Tìm kiếm (Mã HĐ/SV):"), gbc);
        gbc.gridx = 1; p.add(txtSearch, gbc);

        gbc.gridx = 0; gbc.gridy = 1; p.add(new JLabel("Chọn Hợp đồng:"), gbc);
        gbc.gridx = 1; p.add(cboHopDong, gbc);

        gbc.gridx = 0; gbc.gridy = 2; p.add(new JLabel("Gói đóng:"), gbc);
        gbc.gridx = 1; p.add(txtGoi, gbc);

        gbc.gridx = 0; gbc.gridy = 3; p.add(new JLabel("Mã Nhân viên lập:"), gbc);
        gbc.gridx = 1; p.add(new JLabel(maNVDangNhap), gbc);

        int result = JOptionPane.showConfirmDialog(view, p, 
            "Lập Hóa Đơn Tiền Phòng", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                String selectedItem = (String) cboHopDong.getSelectedItem();
                if (selectedItem == null) throw new IllegalArgumentException("Chưa chọn hợp đồng.");
                
                String maHD = selectedItem.split(" ")[0];
                String goiText = txtGoi.getText().replace(" Tháng", "");
                int goi = Integer.parseInt(goiText);

                service.lapHoaDonTienPhong(maHD, goi, maNVDangNhap);
                showInfo("Lập hóa đơn thành công!");
                loadAll();
            } catch (NumberFormatException nfe) {
                showError("Gói thanh toán không hợp lệ.");
            } catch (Exception ex) {
                showError(ex.getMessage());
            }
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(view, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(view, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
}
