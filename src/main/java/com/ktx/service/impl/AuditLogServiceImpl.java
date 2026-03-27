package com.ktx.service.impl;

import com.ktx.model.AuditLog;
import com.ktx.repository.AuditLogRepository;
import com.ktx.service.AuditLogService;
import java.util.List;

public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepo;

    public AuditLogServiceImpl(AuditLogRepository auditLogRepo) {
        this.auditLogRepo = auditLogRepo;
    }

    @Override
    public void log(String maNV, String hanhDong, String chiTiet) {
        // Chạy trong thread riêng để không làm chậm nghiệp vụ chính
        new Thread(() -> {
            try {
                AuditLog logExp = new AuditLog(maNV, hanhDong, chiTiet);
                auditLogRepo.save(logExp);
            } catch (Exception e) {
                // Chỉ log lỗi ra console, không làm hỏng app
                System.err.println("AuditLog Error: " + e.getMessage());
            }
        }).start();
    }

    @Override
    public List<AuditLog> layTatCa() {
        return auditLogRepo.findAll();
    }

    @Override
    public List<AuditLog> timKiem(String keyword) {
        if (keyword == null || keyword.isBlank()) return layTatCa();
        return auditLogRepo.findByKeyword(keyword);
    }

    @Override
    public void xoaToanBo() {
        auditLogRepo.deleteAll();
    }
}
