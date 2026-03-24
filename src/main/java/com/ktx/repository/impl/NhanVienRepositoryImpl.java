package com.ktx.repository.impl;

import com.ktx.config.JpaUtil;
import com.ktx.model.NhanVien;
import com.ktx.repository.NhanVienRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

/**
 * Triển khai {@link NhanVienRepository} sử dụng JPA / JPQL.
 */
public class NhanVienRepositoryImpl implements NhanVienRepository {

    @Override
    public void save(NhanVien nv) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin(); em.persist(nv); tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi lưu NhanVien: " + e.getMessage(), e);
        } finally { em.close(); }
    }

    @Override
    public void update(NhanVien nv) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin(); em.merge(nv); tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi cập nhật NhanVien: " + e.getMessage(), e);
        } finally { em.close(); }
    }

    @Override
    public void delete(String maNV) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            NhanVien nv = em.find(NhanVien.class, maNV);
            if (nv != null) em.remove(nv);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi xóa NhanVien: " + e.getMessage(), e);
        } finally { em.close(); }
    }

    @Override
    public Optional<NhanVien> findById(String maNV) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            return Optional.ofNullable(em.find(NhanVien.class, maNV));
        } finally { em.close(); }
    }

    @Override
    public Optional<NhanVien> findByTaiKhoan(String taiKhoan) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            TypedQuery<NhanVien> q = em.createQuery(
                    "SELECT n FROM NhanVien n WHERE n.taiKhoan = :tk", NhanVien.class);
            q.setParameter("tk", taiKhoan);
            List<NhanVien> list = q.getResultList();
            return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
        } finally { em.close(); }
    }

    @Override
    public List<NhanVien> findAll() {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            return em.createQuery("SELECT n FROM NhanVien n ORDER BY n.hoTen", NhanVien.class)
                     .getResultList();
        } finally { em.close(); }
    }
}
