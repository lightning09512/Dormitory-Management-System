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

            // 4. Thống kê doanh thu theo tháng (Năm hiện tại)
            int currentYear = java.time.LocalDate.now().getYear();
            java.util.List<Object[]> revenueResult = em.createQuery(
                    "SELECT h.thang, h.nam, SUM(h.tongTien) " +
                    "FROM HoaDon h " +
                    "WHERE h.nam = :year AND h.trangThaiThanhToan = 'Đã thanh toán' " +
                    "GROUP BY h.thang, h.nam " +
                    "ORDER BY h.thang ASC", Object[].class)
                    .setParameter("year", currentYear)
                    .getResultList();

            java.util.Map<String, java.math.BigDecimal> doanhThuMap = new java.util.LinkedHashMap<>();
            // Khởi tạo 12 tháng với 0 để biểu đồ hiển thị đầy đủ
            for (int i = 1; i <= 12; i++) {
                doanhThuMap.put(String.format("%02d/%d", i, currentYear), java.math.BigDecimal.ZERO);
            }

            for (Object[] row : revenueResult) {
                int thang = (int) row[0];
                int nam = (int) row[1];
                java.math.BigDecimal total = (java.math.BigDecimal) row[2];
                String key = String.format("%02d/%d", thang, nam);
                doanhThuMap.put(key, total);
            }

            // 5. Tổng doanh thu Điện Nước tích lũy
            java.math.BigDecimal dtDienNuoc = (java.math.BigDecimal) em.createQuery(
                    "SELECT SUM(h.tongTien) FROM HoaDon h WHERE h.trangThaiThanhToan = 'Đã thanh toán'")
                    .getSingleResult();
            if (dtDienNuoc == null) dtDienNuoc = java.math.BigDecimal.ZERO;

            // 6. Tổng doanh thu Tiền Phòng tích lũy
            java.math.BigDecimal dtTienPhong = java.math.BigDecimal.ZERO;
            try {
                dtTienPhong = (java.math.BigDecimal) em.createQuery(
                        "SELECT SUM(h.tongTien) FROM HoaDonTienPhong h WHERE h.trangThai = 'Đã thanh toán'")
                        .getSingleResult();
                if (dtTienPhong == null) dtTienPhong = java.math.BigDecimal.ZERO;
            } catch (Exception e) {
                // Đề phòng bảng chưa có dữ liệu hoặc lỗi
            }

            return new DashboardStatsDto(tongSinhVien, tongPhongTrong, tongHopDongHieuLuc, 
                    doanhThuMap, dtDienNuoc, dtTienPhong);

        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy thống kê Dashboard: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
}
