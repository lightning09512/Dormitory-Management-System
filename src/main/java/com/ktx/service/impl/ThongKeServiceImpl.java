package com.ktx.service.impl;

import com.ktx.model.dto.DashboardStatsDto;
import com.ktx.repository.ThongKeRepository;
import com.ktx.service.ThongKeService;

/**
 * Triển khai {@link ThongKeService}.
 * Repository được truyền qua Constructor Injection (DIP).
 */
public class ThongKeServiceImpl implements ThongKeService {

    private final ThongKeRepository thongKeRepo;

    public ThongKeServiceImpl(ThongKeRepository thongKeRepo) {
        this.thongKeRepo = thongKeRepo;
    }

    @Override
    public DashboardStatsDto layThongKeDashboard() {
        return thongKeRepo.getDashboardStats();
    }
}
