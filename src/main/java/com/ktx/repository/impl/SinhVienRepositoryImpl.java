package com.ktx.repository.impl;

import com.ktx.config.JpaUtil;
import com.ktx.model.SinhVien;
import com.ktx.repository.SinhVienRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

/**
 * Triển khai {@link SinhVienRepository} sử dụng JPA / JPQL.
 */
public class SinhVienRepositoryImpl implements SinhVienRepository {

    // ----------------------------------------------------------------
    // save
    // ----------------------------------------------------------------
    @Override
    public void save(SinhVien sinhVien) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(sinhVien);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi lưu SinhVien: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    // ----------------------------------------------------------------
    // update
    // ----------------------------------------------------------------
    @Override
    public void update(SinhVien sinhVien) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(sinhVien);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi cập nhật SinhVien: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    // ----------------------------------------------------------------
    // delete
    // ----------------------------------------------------------------
    @Override
    public void delete(String maSV) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            SinhVien sv = em.find(SinhVien.class, maSV);
            if (sv != null) {
                em.remove(sv);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi xóa SinhVien [" + maSV + "]: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    // ----------------------------------------------------------------
    // findById
    // ----------------------------------------------------------------
    @Override
    public Optional<SinhVien> findById(String maSV) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            SinhVien sv = em.find(SinhVien.class, maSV);
            return Optional.ofNullable(sv);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tìm SinhVien [" + maSV + "]: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    // ----------------------------------------------------------------
    // findAll
    // ----------------------------------------------------------------
    @Override
    public List<SinhVien> findAll() {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            TypedQuery<SinhVien> query = em.createQuery(
                    "SELECT s FROM SinhVien s ORDER BY s.hoTen", SinhVien.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy danh sách SinhVien: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    // ----------------------------------------------------------------
    // findByHoTen — tìm kiếm tương đối (LIKE) không phân biệt hoa thường
    // ----------------------------------------------------------------
    @Override
    public List<SinhVien> findByHoTen(String keyword) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            TypedQuery<SinhVien> query = em.createQuery(
                    "SELECT s FROM SinhVien s WHERE LOWER(s.hoTen) LIKE LOWER(:kw)",
                    SinhVien.class);
            query.setParameter("kw", "%" + keyword + "%");
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tìm SinhVien theo tên: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    // ----------------------------------------------------------------
    // findByKeyword — tìm kiếm theo MSSV hoặc tên (tương đối, không phân biệt hoa thường)
    // ----------------------------------------------------------------
    @Override
    public List<SinhVien> findByKeyword(String keyword) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            TypedQuery<SinhVien> query = em.createQuery(
                    "SELECT s FROM SinhVien s WHERE " +
                    "LOWER(s.maSV) LIKE LOWER(:kw) OR " +
                    "LOWER(s.hoTen) LIKE LOWER(:kw) " +
                    "ORDER BY s.maSV, s.hoTen", SinhVien.class);
            query.setParameter("kw", "%" + keyword + "%");
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tìm SinhVien theo MSSV hoặc tên: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
}
