package com.ktx.repository.impl;

import com.ktx.config.JpaUtil;
import com.ktx.model.CauHinhGia;
import com.ktx.repository.CauHinhGiaRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class CauHinhGiaRepositoryImpl implements CauHinhGiaRepository {

    @Override
    public List<CauHinhGia> findAll() {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            return em.createQuery("SELECT g FROM CauHinhGia g", CauHinhGia.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void update(CauHinhGia gia) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(gia);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<CauHinhGia> findByLoai(String loaiDichVu) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            List<CauHinhGia> list = em.createQuery(
                    "SELECT g FROM CauHinhGia g WHERE g.loaiDichVu = :loai " +
                    "ORDER BY g.ngayApDung DESC", CauHinhGia.class)
                    .setParameter("loai", loaiDichVu)
                    .setMaxResults(1)
                    .getResultList();
            return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
        } finally {
            em.close();
        }
    }
}
