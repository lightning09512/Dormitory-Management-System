package com.ktx.controller;

import com.ktx.service.AuditLogService;
import com.ktx.view.NhatKyHeThongPanel;
import javax.swing.*;

/**
 * Controller cho {@link NhatKyHeThongPanel}.
 */
public class AuditLogController {

    private final NhatKyHeThongPanel view;
    private final AuditLogService service;

    public AuditLogController(NhatKyHeThongPanel view, AuditLogService service) {
        this.view = view;
        this.service = service;

        initView();
        bindListeners();
    }

    private void initView() {
        refresh();
    }

    private void bindListeners() {
        view.addRefreshListener(e -> refresh());
        view.addSearchListener(e -> search());
        view.addClearListener(e -> clearAll());
    }

    private void refresh() {
        try {
            view.setLogs(service.layTatCa());
        } catch (Exception ex) {
            showError("Lỗi khi nạp nhật ký: " + ex.getMessage());
        }
    }

    private void search() {
        String keyword = view.getSearchKeyword();
        try {
            view.setLogs(service.timKiem(keyword));
        } catch (Exception ex) {
            showError("Lỗi khi tìm kiếm: " + ex.getMessage());
        }
    }

    private void clearAll() {
        int ok = JOptionPane.showConfirmDialog(view, 
                "Bạn có chắc muốn xóa TOÀN BỘ nhật ký hệ thống?\nThao tác này không thể hoàn tác.",
                "Xác nhận xóa sạch", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (ok == JOptionPane.YES_OPTION) {
            try {
                service.xoaToanBo();
                refresh();
                JOptionPane.showMessageDialog(view, "Đã xóa toàn bộ nhật ký thành công.");
            } catch (Exception ex) {
                showError("Lỗi khi xóa nhật ký: " + ex.getMessage());
            }
        }
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(view, msg, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
