package com.ktx.controller;

import com.ktx.model.HoaDon;
import com.ktx.model.Phong;
import com.ktx.repository.PhongRepository;
import com.ktx.repository.impl.PhongRepositoryImpl;
import com.ktx.service.HoaDonService;
import com.ktx.view.QuanLyHoaDonPanel;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class HoaDonController {

    private final QuanLyHoaDonPanel view;
    private final HoaDonService service;
    private final String maNVDangNhap;
    private final String vaiTroNguoiDung;

    public HoaDonController(QuanLyHoaDonPanel view, HoaDonService service, String maNV, String vaiTro) {
        this.view = view;
        this.service = service;
        this.maNVDangNhap = maNV;
        this.vaiTroNguoiDung = vaiTro;
        bindListeners();
        loadAll();
    }

    private void bindListeners() {
        view.addLocListener(e -> loc() );
        view.addLamMoiListener(e -> loadAll() );
        view.addThanhToanListener(e -> thanhToan() );
        view.addXoaListener(e -> xoa() );
        view.addLapHDListener(e -> lapMoi() );
    }

    private void loadAll() {
        try {
            List<HoaDon> list = service.layTatCaHoaDon();
            view.setTableModel(list);
        } catch (Exception ex) {
            showError("Lỗi tải danh sách: " + ex.getMessage());
        }
    }

    private void loc() {
        int thang = view.getSelectedThang();
        int nam = view.getSelectedNam();
        try {
            List<HoaDon> list = service.locHoaDonTheoThangNam(thang, nam);
            view.setTableModel(list);
        } catch (Exception ex) {
            showError("Lỗi tải danh sách: " + ex.getMessage());
        }
    }

    private void thanhToan() {
        String maHD = view.getSelectedMaHDon();
        if (maHD == null) {
            showError("Vui lòng chọn 1 hóa đơn để thanh toán.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "Xác nhận thanh toán hóa đơn " + maHD + "?", "Thanh toán", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                service.thanhToanHoaDon(maHD);
                showInfo("Đã thanh toán hóa đơn thành công!");
                loadAll();
            } catch (Exception ex) {
                showError(ex.getMessage());
            }
        }
    }

    private void xoa() {
        String maHD = view.getSelectedMaHDon();
        if (maHD == null) {
            showError("Vui lòng chọn 1 hóa đơn để xóa.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc muốn xóa hóa đơn " + maHD + " không?", "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                service.xoaHoaDon(maHD, vaiTroNguoiDung);
                showInfo("Đã xóa hóa đơn!");
                loadAll();
            } catch (Exception ex) {
                showError(ex.getMessage());
            }
        }
    }

    private void lapMoi() {
        // Mở Dialog nhập Mã Phòng, Tháng, Năm, Chỉ số điện (Cũ/Mới), Chỉ số nước (Cũ/Mới)
        JPanel p = new JPanel(new GridLayout(7, 2, 10, 10));
        
        JComboBox<String> cboPhong = new JComboBox<>();
        try {
            PhongRepository phongRepo = new PhongRepositoryImpl();
            for (Phong ph : phongRepo.findAll()) {
                cboPhong.addItem(ph.getMaPhong());
            }
        } catch (Exception e) {}
        
        JComboBox<Integer> cboThang = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});
        cboThang.setSelectedItem(LocalDate.now().getMonthValue());
        
        JSpinner spinNam = new JSpinner(new SpinnerNumberModel(LocalDate.now().getYear(), 2020, 2050, 1));
        
        JTextField txtDienCu = new JTextField("0");
        JTextField txtDienMoi = new JTextField();
        JTextField txtNuocCu = new JTextField("0");
        JTextField txtNuocMoi = new JTextField();

        // Thêm listener để tự động lấy chỉ số cũ khi chọn phòng
        cboPhong.addActionListener(al -> {
            String selected = (String) cboPhong.getSelectedItem();
            if (selected != null) {
                com.ktx.model.HoaDon latest = service.layHoaDonGanNhatTheoPhong(selected);
                if (latest != null) {
                    txtDienCu.setText(latest.getChiSoDienMoi().toString());
                    txtNuocCu.setText(latest.getChiSoNuocMoi().toString());
                } else {
                    txtDienCu.setText("0");
                    txtNuocCu.setText("0");
                }
            }
        });
        
        // Kích hoạt load lần đầu nếu có dữ liệu
        if (cboPhong.getItemCount() > 0) {
            cboPhong.setSelectedIndex(0);
            // Kích hoạt thủ công vì addActionListener có thể không chạy khi setSelectedIndex(0) nếu nó đã là 0
            String selected = (String) cboPhong.getSelectedItem();
            com.ktx.model.HoaDon latest = service.layHoaDonGanNhatTheoPhong(selected);
            if (latest != null) {
                txtDienCu.setText(latest.getChiSoDienMoi().toString());
                txtNuocCu.setText(latest.getChiSoNuocMoi().toString());
            }
        }

        p.add(new JLabel("Chọn phòng:")); p.add(cboPhong);
        p.add(new JLabel("Tháng:")); p.add(cboThang);
        p.add(new JLabel("Năm:")); p.add(spinNam);
        p.add(new JLabel("Chỉ số Điện cũ (tháng trước):")); p.add(txtDienCu);
        p.add(new JLabel("Chỉ số Điện mới:")); p.add(txtDienMoi);
        p.add(new JLabel("Chỉ số Nước cũ (tháng trước):")); p.add(txtNuocCu);
        p.add(new JLabel("Chỉ số Nước mới:")); p.add(txtNuocMoi);

        int result = JOptionPane.showConfirmDialog(view, p, "Lập Hóa Đơn Điện Nước", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                String maPhong = (String) cboPhong.getSelectedItem();
                int thang = (Integer) cboThang.getSelectedItem();
                int nam = (Integer) spinNam.getValue();
                
                BigDecimal dCu = new BigDecimal(txtDienCu.getText().trim());
                BigDecimal dMoi = new BigDecimal(txtDienMoi.getText().trim());
                BigDecimal nCu = new BigDecimal(txtNuocCu.getText().trim());
                BigDecimal nMoi = new BigDecimal(txtNuocMoi.getText().trim());
                
                service.taoHoaDon(thang, nam, maPhong, maNVDangNhap, dCu, dMoi, nCu, nMoi);
                showInfo("Lập hóa đơn thành công!");
                loadAll();
            } catch (NumberFormatException nfe) {
                showError("Vui lòng nhập đúng định dạng số cho chỉ số điện nước.");
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
