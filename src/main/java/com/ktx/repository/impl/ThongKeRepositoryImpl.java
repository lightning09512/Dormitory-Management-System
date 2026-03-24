package com.ktx.repository.impl;

import com.ktx.config.JpaUtil;
import com.ktx.model.dto.DashboardStatsDto;
import com.ktx.repository.ThongKeRepository;

import jakarta.persistence.EntityManager;

/**
 * Triển khai {@link ThongKeRepository} – tổng hợp số liệu qua 3 truy vấn JPQL COUNT.
 *
 * <p>Các truy vấn được thực hiện trong một EntityManager duy nhất để tối ưu
 * số lần mở kết nối. Vì đây là truy vấn SELECT đọc thuần, không cần transaction.</p>
 */
public class ThongKeRepositoryImpl implements ThongKeRepository {

    @Override
    public DashboardStatsDto getDashboardStats() {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            // 1. Tổng số sinh viên
            long tongSinhVien = (long) em
                    .createQuery("SELECT COUNT(s) FROM SinhVien s")
                    .getSingleResult();

            // 2. Tổng số phòng còn trống
            long tongPhongTrong = (long) em
                    .createQuery("SELECT COUNT(p) FROM Phong p WHERE p.trangThai = 'Còn trống'")
                    .getSingleResult();

            // 3. Tổng số hợp đồng đang hiệu lực
            long tongHopDongHieuLuc = (long) em
                    .createQuery("SELECT COUNT(h) FROM HopDong h WHERE h.trangThai = 'Đang hiệu lực'")
                    .getSingleResult();

            return new DashboardStatsDto(tongSinhVien, tongPhongTrong, tongHopDongHieuLuc);

        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy thống kê Dashboard: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
}
