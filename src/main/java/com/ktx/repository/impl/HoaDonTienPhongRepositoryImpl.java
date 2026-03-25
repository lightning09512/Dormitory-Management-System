package com.ktx.repository.impl;

import com.ktx.config.JpaUtil;
import com.ktx.model.HoaDonTienPhong;
import com.ktx.repository.HoaDonTienPhongRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class HoaDonTienPhongRepositoryImpl implements HoaDonTienPhongRepository {

    @Override
    public void save(HoaDonTienPhong hoaDon) {
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
    public void update(HoaDonTienPhong hoaDon) {
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
    public void delete(String maHDTP) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            HoaDonTienPhong hd = em.find(HoaDonTienPhong.class, maHDTP);
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
    public Optional<HoaDonTienPhong> findById(String maHDTP) {
        try (EntityManager em = JpaUtil.getEmf().createEntityManager()) {
            HoaDonTienPhong hd = em.find(HoaDonTienPhong.class, maHDTP);
            return Optional.ofNullable(hd);
        }
    }

    @Override
    public List<HoaDonTienPhong> findAll() {
        try (EntityManager em = JpaUtil.getEmf().createEntityManager()) {
            return em.createQuery("SELECT h FROM HoaDonTienPhong h ORDER BY h.ngayLap DESC", HoaDonTienPhong.class)
                    .getResultList();
        }
    }

    @Override
    public List<HoaDonTienPhong> findByHopDong(String maHD) {
        try (EntityManager em = JpaUtil.getEmf().createEntityManager()) {
            TypedQuery<HoaDonTienPhong> query = em.createQuery(
                    "SELECT h FROM HoaDonTienPhong h WHERE h.maHD = :maHD", HoaDonTienPhong.class);
            query.setParameter("maHD", maHD);
            return query.getResultList();
        }
    }

    @Override
    public List<HoaDonTienPhong> findBySinhVien(String maSV) {
        try (EntityManager em = JpaUtil.getEmf().createEntityManager()) {
            TypedQuery<HoaDonTienPhong> query = em.createQuery(
                    "SELECT h FROM HoaDonTienPhong h WHERE h.maHD IN (SELECT hd.maHD FROM HopDong hd WHERE hd.maSV = :maSV)", 
                    HoaDonTienPhong.class);
            query.setParameter("maSV", maSV);
            return query.getResultList();
        }
    }

    @Override
    public List<HoaDonTienPhong> findByPhong(String maPhong) {
        try (EntityManager em = JpaUtil.getEmf().createEntityManager()) {
            TypedQuery<HoaDonTienPhong> query = em.createQuery(
                    "SELECT h FROM HoaDonTienPhong h WHERE h.maHD IN (SELECT hd.maHD FROM HopDong hd WHERE hd.maPhong = :maPhong)", 
                    HoaDonTienPhong.class);
            query.setParameter("maPhong", maPhong);
            return query.getResultList();
        }
    }
}
