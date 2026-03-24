package com.ktx.service.impl;

import com.ktx.model.SinhVien;
import com.ktx.repository.SinhVienRepository;
import com.ktx.service.SinhVienService;
import com.ktx.util.LoggerUtil;
import com.ktx.util.ValidationUtil;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * Triển khai {@link SinhVienService}.
 * Repository được truyền qua Constructor Injection (DIP).
 */
public class SinhVienServiceImpl implements SinhVienService {

    private final SinhVienRepository sinhVienRepo;
    private static final Logger LOGGER = LoggerUtil.getLogger(SinhVienServiceImpl.class);

    public SinhVienServiceImpl(SinhVienRepository sinhVienRepo) {
        this.sinhVienRepo = sinhVienRepo;
    }

    @Override
    public void themSinhVien(SinhVien sinhVien) {
        LoggerUtil.logEntry(LOGGER, "themSinhVien", sinhVien);
        
        // Validate input
        String validationError = ValidationUtil.validate(sinhVien);
        if (validationError != null) {
            LoggerUtil.logValidationError(LOGGER, "themSinhVien", validationError);
            throw new IllegalArgumentException("Dữ liệu không hợp lệ:\n" + validationError);
        }
        
        // Check duplicate
        if (sinhVienRepo.findById(sinhVien.getMaSV()).isPresent()) {
            LoggerUtil.logError(LOGGER, "themSinhVien", 
                new IllegalArgumentException("Mã sinh viên đã tồn tại: " + sinhVien.getMaSV()));
            throw new IllegalArgumentException("Mã sinh viên đã tồn tại: " + sinhVien.getMaSV());
        }
        
        try {
            sinhVienRepo.save(sinhVien);
            LoggerUtil.logExit(LOGGER, "themSinhVien", "Success");
        } catch (Exception e) {
            LoggerUtil.logError(LOGGER, "themSinhVien", e);
            throw e;
        }
    }

    @Override
    public void capNhatSinhVien(SinhVien sinhVien) {
        timTheoMa(sinhVien.getMaSV()); // ném exception nếu không tồn tại
        sinhVienRepo.update(sinhVien);
    }

    @Override
    public void xoaSinhVien(String maSV) {
        timTheoMa(maSV);
        sinhVienRepo.delete(maSV);
    }

    @Override
    public SinhVien timTheoMa(String maSV) {
        return sinhVienRepo.findById(maSV)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Không tìm thấy sinh viên: " + maSV));
    }

    @Override
    public List<SinhVien> layTatCa() {
        return sinhVienRepo.findAll();
    }

    @Override
    public List<SinhVien> timTheoTen(String keyword) {
        return sinhVienRepo.findByHoTen(keyword);
    }
    
    @Override
    public List<SinhVien> timTheoKeyword(String keyword) {
        return sinhVienRepo.findByKeyword(keyword);
    }
}
