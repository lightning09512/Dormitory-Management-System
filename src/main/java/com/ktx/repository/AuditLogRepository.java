package com.ktx.repository;

import com.ktx.model.AuditLog;
import java.util.List;

/**
 * Interface Repository cho {@link AuditLog}.
 */
public interface AuditLogRepository {
    void save(AuditLog log);
    List<AuditLog> findAll();
    List<AuditLog> findByMaNV(String maNV);
    List<AuditLog> findByKeyword(String keyword);
    void deleteAll();
}
