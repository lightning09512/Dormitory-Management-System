package com.ktx.controller;

import com.ktx.service.ThongKeService;
import com.ktx.view.DashboardPanel;

/**
 * Controller cho màn hình Dashboard.
 */
public class DashboardController {

    private final DashboardPanel  view;
    private final ThongKeService  thongKeService;

    public DashboardController(DashboardPanel view, ThongKeService thongKeService) {
        this.view           = view;
        this.thongKeService = thongKeService;
        loadStats();
    }

    /** Nạp số liệu mới nhất từ DB vào View. */
    public void loadStats() {
        try {
            view.setStats(thongKeService.layThongKeDashboard());
        } catch (Exception ex) {
            System.err.println("[DashboardController] Lỗi tải thống kê: " + ex.getMessage());
        }
    }
}
