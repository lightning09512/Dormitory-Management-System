package com.ktx.repository.impl;

import com.ktx.config.JpaUtil;
import com.ktx.model.AuditLog;
import com.ktx.repository.AuditLogRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class AuditLogRepositoryImpl implements AuditLogRepository {

    @Override
    public void save(AuditLog log) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(log);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<AuditLog> findAll() {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            return em.createQuery("SELECT a FROM AuditLog a ORDER BY a.thoiGian DESC", AuditLog.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<AuditLog> findByMaNV(String maNV) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            return em.createQuery("SELECT a FROM AuditLog a WHERE a.maNV = :maNV ORDER BY a.thoiGian DESC", AuditLog.class)
                    .setParameter("maNV", maNV)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<AuditLog> findByKeyword(String keyword) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            return em.createQuery("SELECT a FROM AuditLog a WHERE a.maNV LIKE :k OR a.hanhDong LIKE :k OR a.chiTiet LIKE :k ORDER BY a.thoiGian DESC", AuditLog.class)
                    .setParameter("k", "%" + keyword + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteAll() {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createQuery("DELETE FROM AuditLog").executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
