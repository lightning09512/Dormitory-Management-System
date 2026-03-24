package com.ktx.repository.impl;

import com.ktx.config.JpaUtil;
import com.ktx.model.HoaDon;
import com.ktx.repository.HoaDonRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class HoaDonRepositoryImpl implements HoaDonRepository {

    @Override
    public void save(HoaDon hoaDon) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(hoaDon);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(HoaDon hoaDon) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(hoaDon);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(String maHDon) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            HoaDon hd = em.find(HoaDon.class, maHDon);
            if (hd != null) {
                em.remove(hd);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<HoaDon> findAll() {
        try (EntityManager em = JpaUtil.getEmf().createEntityManager()) {
            return em.createQuery("SELECT h FROM HoaDon h ORDER BY h.nam DESC, h.thang DESC", HoaDon.class).getResultList();
        }
    }

    @Override
    public List<HoaDon> findTheoThangNam(int thang, int nam) {
        try (EntityManager em = JpaUtil.getEmf().createEntityManager()) {
            TypedQuery<HoaDon> query = em.createQuery(
                "SELECT h FROM HoaDon h WHERE h.thang = :thang AND h.nam = :nam ORDER BY h.maPhong", HoaDon.class);
            query.setParameter("thang", thang);
            query.setParameter("nam", nam);
            return query.getResultList();
        }
    }

    @Override
    public Optional<HoaDon> findById(String maHDon) {
        try (EntityManager em = JpaUtil.getEmf().createEntityManager()) {
            HoaDon hd = em.find(HoaDon.class, maHDon);
            return Optional.ofNullable(hd);
        }
    }

    @Override
    public boolean existsByPhongAndThangNam(String maPhong, int thang, int nam) {
        try (EntityManager em = JpaUtil.getEmf().createEntityManager()) {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(h) FROM HoaDon h WHERE h.maPhong = :maPhong AND h.thang = :thang AND h.nam = :nam", Long.class);
            query.setParameter("maPhong", maPhong);
            query.setParameter("thang", thang);
            query.setParameter("nam", nam);
            return query.getSingleResult() > 0;
        }
    }
}
