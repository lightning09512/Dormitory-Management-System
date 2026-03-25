package com.ktx.controller;

import com.ktx.model.Phong;
import com.ktx.model.entity.TrangThietBi;
import com.ktx.model.enums.TinhTrangTB;
import com.ktx.service.TrangThietBiService;
import com.ktx.view.thietbi.TrangThietBiPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.List;

public class TrangThietBiController {

    private final TrangThietBiPanel view;
    private final TrangThietBiService service;

    public TrangThietBiController(TrangThietBiPanel view, TrangThietBiService service) {
        this.view = view;
        this.service = service;

        initView();
        initController();
    }

    private void initView() {
        loadDataToTable(service.layTatCaThietBi());
    }

    private void initController() {
        view.addThemListener(e -> handleThem());
        view.addCapNhatListener(e -> handleCapNhat());
        view.addXoaListener(e -> handleXoa());
        view.addLamMoiListener(e -> handleLamMoi());
        view.addTimKiemListener(e -> handleTimKiem());

        view.addTableMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.getTableThietBi().getSelectedRow();
                if (row >= 0) {
                    fillFormFromTable(row);
                }
            }
        });
    }

    private void handleThem() {
        try {
            TrangThietBi tb = getTrangThietBiFromForm();
            service.themThietBi(tb);
            JOptionPane.showMessageDialog(view, "Thêm thiết bị thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            handleLamMoi();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Giá trị phải là một số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleCapNhat() {
        try {
            TrangThietBi tb = getTrangThietBiFromForm();
            service.capNhatThietBi(tb);
            JOptionPane.showMessageDialog(view, "Cập nhật thiết bị thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            handleLamMoi();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Giá trị phải là một số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleXoa() {
        String maTB = view.getMaTB();
        if (maTB.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn thiết bị hoặc nhập mã thiết bị để xóa!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa thiết bị này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                service.xoaThietBi(maTB);
                JOptionPane.showMessageDialog(view, "Xóa thiết bị thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                handleLamMoi();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleLamMoi() {
        view.clearForm();
        loadDataToTable(service.layTatCaThietBi());
    }

    private void handleTimKiem() {
        String maPhong = view.getMaPhong();
        if (maPhong.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập mã phòng để tìm kiếm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<TrangThietBi> list = service.timKiemTheoPhong(maPhong);
        loadDataToTable(list);
    }

    private void loadDataToTable(List<TrangThietBi> list) {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);
        for (TrangThietBi tb : list) {
            model.addRow(new Object[]{
                    tb.getMaTB(),
                    tb.getTenTB(),
                    tb.getTinhTrang(),
                    tb.getGiaTri(),
                    tb.getPhong() != null ? tb.getPhong().getMaPhong() : ""
            });
        }
    }

    private void fillFormFromTable(int row) {
        DefaultTableModel model = view.getTableModel();
        view.setMaTB(model.getValueAt(row, 0) != null ? model.getValueAt(row, 0).toString() : "");
        view.setTenTB(model.getValueAt(row, 1) != null ? model.getValueAt(row, 1).toString() : "");
        
        Object tinhTrangObj = model.getValueAt(row, 2);
        if (tinhTrangObj instanceof TinhTrangTB) {
            view.setTinhTrang((TinhTrangTB) tinhTrangObj);
        } else if (tinhTrangObj != null) {
            try {
                view.setTinhTrang(TinhTrangTB.valueOf(tinhTrangObj.toString()));
            } catch (IllegalArgumentException e) {
                view.setTinhTrang(TinhTrangTB.TOT);
            }
        }
        
        view.setGiaTri(model.getValueAt(row, 3) != null ? model.getValueAt(row, 3).toString() : "");
        view.setMaPhong(model.getValueAt(row, 4) != null ? model.getValueAt(row, 4).toString() : "");
    }

    private TrangThietBi getTrangThietBiFromForm() {
        TrangThietBi tb = new TrangThietBi();
        tb.setMaTB(view.getMaTB());
        tb.setTenTB(view.getTenTB());
        tb.setTinhTrang(view.getTinhTrang());
        
        String giaTriStr = view.getGiaTri();
        if (!giaTriStr.isEmpty()) {
            tb.setGiaTri(new BigDecimal(giaTriStr));
        } else {
            tb.setGiaTri(BigDecimal.ZERO);
        }

        Phong phong = new Phong();
        phong.setMaPhong(view.getMaPhong());
        tb.setPhong(phong);

        return tb;
    }
}
