package com.ktx.repository.impl;

import com.ktx.config.JpaUtil;
import com.ktx.model.HopDong;
import com.ktx.repository.HopDongRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

/**
 * Triển khai {@link HopDongRepository} sử dụng JPA / JPQL.
 */
public class HopDongRepositoryImpl implements HopDongRepository {

    // ----------------------------------------------------------------
    // save
    // ----------------------------------------------------------------
    @Override
    public void save(HopDong hopDong) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(hopDong);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi lưu HopDong: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    // ----------------------------------------------------------------
    // update
    // ----------------------------------------------------------------
    @Override
    public void update(HopDong hopDong) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(hopDong);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi cập nhật HopDong: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    // ----------------------------------------------------------------
    // delete
    // ----------------------------------------------------------------
    @Override
    public void delete(String maHD) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            HopDong hd = em.find(HopDong.class, maHD);
            if (hd != null) {
                em.remove(hd);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi xóa HopDong [" + maHD + "]: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    // ----------------------------------------------------------------
    // findById
    // ----------------------------------------------------------------
    @Override
    public Optional<HopDong> findById(String maHD) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            HopDong hd = em.find(HopDong.class, maHD);
            return Optional.ofNullable(hd);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tìm HopDong [" + maHD + "]: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    // ----------------------------------------------------------------
    // findAll
    // ----------------------------------------------------------------
    @Override
    public List<HopDong> findAll() {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            TypedQuery<HopDong> query = em.createQuery(
                    "SELECT h FROM HopDong h ORDER BY h.ngayLap DESC", HopDong.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy danh sách HopDong: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    // ----------------------------------------------------------------
    // findBySinhVien
    // ----------------------------------------------------------------
    @Override
    public List<HopDong> findBySinhVien(String maSV) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            TypedQuery<HopDong> query = em.createQuery(
                    "SELECT h FROM HopDong h WHERE h.maSV = :maSV ORDER BY h.ngayLap DESC",
                    HopDong.class);
            query.setParameter("maSV", maSV);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tìm HopDong theo SinhVien [" + maSV + "]: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    // ----------------------------------------------------------------
    // findHopDongHieuLuc
    // ----------------------------------------------------------------
    @Override
    public List<HopDong> findHopDongHieuLuc() {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            TypedQuery<HopDong> query = em.createQuery(
                    "SELECT h FROM HopDong h WHERE h.trangThai = 'Đang hiệu lực'",
                    HopDong.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tìm HopDong đang hiệu lực: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
}
