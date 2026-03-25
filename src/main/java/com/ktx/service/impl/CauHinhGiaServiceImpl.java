package com.ktx.service.impl;

import com.ktx.model.CauHinhGia;
import com.ktx.repository.CauHinhGiaRepository;
import com.ktx.service.CauHinhGiaService;
import java.math.BigDecimal;
import java.util.List;

public class CauHinhGiaServiceImpl implements CauHinhGiaService {

    private final CauHinhGiaRepository repo;

    public CauHinhGiaServiceImpl(CauHinhGiaRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<CauHinhGia> getAll() {
        return repo.findAll();
    }

    @Override
    public void updateGia(CauHinhGia gia) {
        repo.update(gia);
    }

    @Override
    public BigDecimal getDonGia(String loaiDichVu) {
        return repo.findByLoai(loaiDichVu)
                .map(CauHinhGia::getDonGia)
                .orElse(getDefault(loaiDichVu));
    }

    private BigDecimal getDefault(String loai) {
        if ("Điện".equalsIgnoreCase(loai)) return new BigDecimal("3500");
        if ("Nước".equalsIgnoreCase(loai)) return new BigDecimal("20000");
        return BigDecimal.ZERO;
    }
}
