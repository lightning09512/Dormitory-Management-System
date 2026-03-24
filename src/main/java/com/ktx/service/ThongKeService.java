package com.ktx.service;

import com.ktx.model.dto.DashboardStatsDto;

/**
 * Service interface cho các chức năng thống kê tổng hợp (Dashboard).
 */
public interface ThongKeService {

    /**
     * Lấy số liệu tổng quan cho màn hình Dashboard.
     *
     * @return {@link DashboardStatsDto} chứa: tổng sinh viên,
     *         tổng phòng trống, tổng hợp đồng đang hiệu lực.
     */
    DashboardStatsDto layThongKeDashboard();
}
