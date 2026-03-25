package com.ktx.service.impl;

import com.ktx.model.entity.TrangThietBi;
import com.ktx.repository.PhongRepository;
import com.ktx.repository.TrangThietBiRepository;
import com.ktx.service.TrangThietBiService;

import java.util.List;

public class TrangThietBiServiceImpl implements TrangThietBiService {

    private final TrangThietBiRepository trangThietBiRepository;
    private final PhongRepository phongRepository;

    public TrangThietBiServiceImpl(TrangThietBiRepository trangThietBiRepository, PhongRepository phongRepository) {
        this.trangThietBiRepository = trangThietBiRepository;
        this.phongRepository = phongRepository;
    }

    @Override
    public void themThietBi(TrangThietBi trangThietBi) throws Exception {
        validate(trangThietBi);
        trangThietBiRepository.save(trangThietBi);
    }

    @Override
    public void capNhatThietBi(TrangThietBi trangThietBi) throws Exception {
        validate(trangThietBi);
        trangThietBiRepository.update(trangThietBi);
    }

    @Override
    public void xoaThietBi(String maTB) throws Exception {
        if (maTB == null || maTB.trim().isEmpty()) {
            throw new Exception("Mã thiết bị không được để trống");
        }
        trangThietBiRepository.delete(maTB);
    }

    @Override
    public List<TrangThietBi> layTatCaThietBi() {
        return trangThietBiRepository.findAll();
    }

    @Override
    public List<TrangThietBi> timKiemTheoPhong(String maPhong) {
        return trangThietBiRepository.findByMaPhong(maPhong);
    }

    private void validate(TrangThietBi trangThietBi) throws Exception {
        if (trangThietBi.getMaTB() == null || trangThietBi.getMaTB().trim().isEmpty()) {
            throw new Exception("Mã thiết bị không được để trống");
        }
        if (trangThietBi.getPhong() == null || trangThietBi.getPhong().getMaPhong() == null || trangThietBi.getPhong().getMaPhong().trim().isEmpty()) {
            throw new Exception("Thông tin phòng không hợp lệ");
        }
        
        if (phongRepository.findById(trangThietBi.getPhong().getMaPhong()).isEmpty()) {
            throw new Exception("Phòng không tồn tại");
        }
    }
}
