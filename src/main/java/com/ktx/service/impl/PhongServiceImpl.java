package com.ktx.service.impl;

import com.ktx.model.Phong;
import com.ktx.repository.PhongRepository;
import com.ktx.service.PhongService;

import java.util.List;

/**
 * Triển khai {@link PhongService}.
 * Repository được truyền qua Constructor Injection (DIP).
 */
public class PhongServiceImpl implements PhongService {

    private final PhongRepository phongRepo;

    public PhongServiceImpl(PhongRepository phongRepo) {
        this.phongRepo = phongRepo;
    }

    @Override
    public void themPhong(Phong phong) {
        if (phongRepo.findById(phong.getMaPhong()).isPresent()) {
            throw new IllegalArgumentException("Mã phòng đã tồn tại: " + phong.getMaPhong());
        }
        phongRepo.save(phong);
    }

    @Override
    public void capNhatPhong(Phong phong) {
        timTheoMa(phong.getMaPhong());
        phongRepo.update(phong);
    }

    @Override
    public void xoaPhong(String maPhong) {
        timTheoMa(maPhong);
        phongRepo.delete(maPhong);
    }

    @Override
    public Phong timTheoMa(String maPhong) {
        return phongRepo.findById(maPhong)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Không tìm thấy phòng: " + maPhong));
    }

    @Override
    public List<Phong> layTatCa() {
        return phongRepo.findAll();
    }

    @Override
    public List<Phong> layPhongConTrong() {
        return phongRepo.findPhongTrong();
    }
}
