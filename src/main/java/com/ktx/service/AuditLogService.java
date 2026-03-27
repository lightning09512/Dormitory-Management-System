package com.ktx.service;

import com.ktx.model.AuditLog;
import java.util.List;

/**
 * Interface cho {@link AuditLogService}.
 */
public interface AuditLogService {
    /**
     * Ghi lại nhật ký hành động.
     * @param maNV       Mã nhân viên thực hiện
     * @param hanhDong   Tên hành động
     * @param chiTiet    Chi tiết thao tác
     */
    void log(String maNV, String hanhDong, String chiTiet);

    List<AuditLog> layTatCa();
    List<AuditLog> timKiem(String keyword);
    void xoaToanBo();
}
