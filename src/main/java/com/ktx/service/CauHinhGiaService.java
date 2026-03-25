package com.ktx.service;

import com.ktx.model.CauHinhGia;
import java.math.BigDecimal;
import java.util.List;

public interface CauHinhGiaService {
    List<CauHinhGia> getAll();
    void updateGia(CauHinhGia gia);
    BigDecimal getDonGia(String loaiDichVu);
}
