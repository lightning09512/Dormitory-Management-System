package com.ktx.repository.impl;

import com.ktx.config.JpaUtil;
import com.ktx.model.entity.TrangThietBi;
import com.ktx.repository.TrangThietBiRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class TrangThietBiRepositoryImpl implements TrangThietBiRepository {

    @Override
    public void save(TrangThietBi trangThietBi) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            if (trangThietBi.getPhong() != null && trangThietBi.getPhong().getMaPhong() != null) {
                trangThietBi.setPhong(em.getReference(com.ktx.model.Phong.class, trangThietBi.getPhong().getMaPhong()));
            }
            em.persist(trangThietBi);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(TrangThietBi trangThietBi) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            if (trangThietBi.getPhong() != null && trangThietBi.getPhong().getMaPhong() != null) {
                trangThietBi.setPhong(em.getReference(com.ktx.model.Phong.class, trangThietBi.getPhong().getMaPhong()));
            }
            em.merge(trangThietBi);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(String maTB) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            TrangThietBi tb = em.find(TrangThietBi.class, maTB);
            if (tb != null) {
                em.remove(tb);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<TrangThietBi> findAll() {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            return em.createQuery("SELECT t FROM TrangThietBi t LEFT JOIN FETCH t.phong", TrangThietBi.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<TrangThietBi> findByMaPhong(String maPhong) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            TypedQuery<TrangThietBi> query = em.createQuery(
                "SELECT t FROM TrangThietBi t LEFT JOIN FETCH t.phong WHERE t.phong.maPhong = :maPhong",
                TrangThietBi.class
            );
            query.setParameter("maPhong", maPhong);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
