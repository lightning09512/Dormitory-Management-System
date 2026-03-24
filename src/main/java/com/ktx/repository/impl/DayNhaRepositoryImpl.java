package com.ktx.repository.impl;

import com.ktx.config.JpaUtil;
import com.ktx.model.DayNha;
import com.ktx.repository.DayNhaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public class DayNhaRepositoryImpl implements DayNhaRepository {

    @Override
    public List<DayNha> findAll() {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            return em.createQuery("SELECT d FROM DayNha d ORDER BY d.maDay", DayNha.class)
                     .getResultList();
        } finally { em.close(); }
    }

    @Override
    public Optional<DayNha> findById(String maDay) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            return Optional.ofNullable(em.find(DayNha.class, maDay));
        } finally { em.close(); }
    }

    @Override
    public void save(DayNha d) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try { tx.begin(); em.persist(d); tx.commit(); }
        catch (Exception e) { if (tx.isActive()) tx.rollback(); throw new RuntimeException(e); }
        finally { em.close(); }
    }

    @Override
    public void update(DayNha d) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try { tx.begin(); em.merge(d); tx.commit(); }
        catch (Exception e) { if (tx.isActive()) tx.rollback(); throw new RuntimeException(e); }
        finally { em.close(); }
    }

    @Override
    public void delete(String maDay) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            DayNha d = em.find(DayNha.class, maDay);
            if (d != null) em.remove(d);
            tx.commit();
        } catch (Exception e) { if (tx.isActive()) tx.rollback(); throw new RuntimeException(e); }
        finally { em.close(); }
    }
}
