package com.ktx.repository.impl;

import com.ktx.config.JpaUtil;
import com.ktx.model.Phong;
import com.ktx.repository.PhongRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Triển khai {@link PhongRepository} sử dụng JPA / JPQL.
 */
public class PhongRepositoryImpl implements PhongRepository {

    // ----------------------------------------------------------------
    // save
    // ----------------------------------------------------------------
    @Override
    public void save(Phong phong) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(phong);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi lưu Phong: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    // ----------------------------------------------------------------
    // update
    // ----------------------------------------------------------------
    @Override
    public void update(Phong phong) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(phong);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi cập nhật Phong: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    // ----------------------------------------------------------------
    // delete
    // ----------------------------------------------------------------
    @Override
    public void delete(String maPhong) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Phong phong = em.find(Phong.class, maPhong);
            if (phong != null) {
                em.remove(phong);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi xóa Phong [" + maPhong + "]: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    // ----------------------------------------------------------------
    // findById
    // ----------------------------------------------------------------
    @Override
    public Optional<Phong> findById(String maPhong) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            Phong phong = em.find(Phong.class, maPhong);
            return Optional.ofNullable(phong);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tìm Phong [" + maPhong + "]: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    // ----------------------------------------------------------------
    // findAll
    // ----------------------------------------------------------------
    @Override
    public List<Phong> findAll() {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            TypedQuery<Phong> query = em.createQuery(
                    "SELECT p FROM Phong p ORDER BY p.maPhong", Phong.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy danh sách Phong: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    // ----------------------------------------------------------------
    // findPhongTrong — JPQL đặc thù theo yêu cầu đề bài
    // ----------------------------------------------------------------
    @Override
    public List<Phong> findPhongTrong() {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            TypedQuery<Phong> query = em.createQuery(
                    "SELECT p FROM Phong p WHERE p.trangThai = 'Còn trống'",
                    Phong.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tìm phòng trống: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
}
