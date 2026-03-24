package com.ktx.repository;

import com.ktx.model.dto.DashboardStatsDto;

/**
 * Repository interface cho các truy vấn thống kê tổng hợp.
 * Không ánh xạ trực tiếp một entity duy nhất.
 */
public interface ThongKeRepository {

    /**
     * Trả về đối tượng {@link DashboardStatsDto} chứa:
     * <ul>
     *   <li>Tổng số sinh viên trong hệ thống</li>
     *   <li>Tổng số phòng còn trống</li>
     *   <li>Tổng số hợp đồng đang hiệu lực</li>
     * </ul>
     *
     * @return {@link DashboardStatsDto}
     */
    DashboardStatsDto getDashboardStats();
}
