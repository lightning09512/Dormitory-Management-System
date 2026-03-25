package com.ktx.controller;

import com.ktx.model.CauHinhGia;
import com.ktx.service.CauHinhGiaService;
import com.ktx.view.QuanLyGiaPanel;

import javax.swing.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CauHinhGiaController {

    private final QuanLyGiaPanel view;
    private final CauHinhGiaService service;
    private List<CauHinhGia> list;

    public CauHinhGiaController(QuanLyGiaPanel view, CauHinhGiaService service) {
        this.view = view;
        this.service = service;
        setupListeners();
        loadAll();
    }

    private void setupListeners() {
        view.addCapNhatListener(e -> handleCapNhat());
        view.addLamMoiListener(e -> loadAll());
    }

    public void loadAll() {
        try {
            list = service.getAll();
            view.setTableModel(list);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi tải đơn giá: " + e.getMessage());
        }
    }

    private void handleCapNhat() {
        String loai = view.getSelectedLoaiDichVu();
        if (loai == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn loại dịch vụ muốn cập nhật.");
            return;
        }

        CauHinhGia g = list.stream().filter(item -> item.getLoaiDichVu().equals(loai)).findFirst().orElse(null);
        if (g == null) return;

        String input = JOptionPane.showInputDialog(view, "Nhập đơn giá mới cho " + loai + " (VNĐ):", g.getDonGia());
        if (input != null && !input.trim().isEmpty()) {
            try {
                BigDecimal value = new BigDecimal(input.trim());
                g.setDonGia(value);
                g.setNgayApDung(LocalDate.now());

                service.updateGia(g);
                JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
                loadAll();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(view, "Đơn giá không hợp lệ. Vui lòng nhập số.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(view, "Lỗi: " + e.getMessage());
            }
        }
    }
}
