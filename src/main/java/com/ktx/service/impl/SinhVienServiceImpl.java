package com.ktx.service.impl;

import com.ktx.model.SinhVien;
import com.ktx.model.HopDong;
import com.ktx.repository.SinhVienRepository;
import com.ktx.repository.HopDongRepository;
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
    private final HopDongRepository hopDongRepo;
    private final com.ktx.service.AuditLogService auditLogService;
    private static final Logger LOGGER = LoggerUtil.getLogger(SinhVienServiceImpl.class);

    public SinhVienServiceImpl(SinhVienRepository sinhVienRepo, HopDongRepository hopDongRepo, com.ktx.service.AuditLogService auditLogService) {
        this.sinhVienRepo = sinhVienRepo;
        this.hopDongRepo = hopDongRepo;
        this.auditLogService = auditLogService;
    }

    private SinhVien attachPhongHienTai(SinhVien sv) {
        if (sv == null) return null;
        sv.setPhongHienTai("Chưa xếp phòng");
        if (hopDongRepo != null) {
            List<HopDong> listHD = hopDongRepo.findBySinhVien(sv.getMaSV());
            for (HopDong hd : listHD) {
                if ("Đang hiệu lực".equals(hd.getTrangThai())) {
                    sv.setPhongHienTai(hd.getMaPhong());
                    break;
                }
            }
        }
        return sv;
    }

    private List<SinhVien> attachPhongHienTaiList(List<SinhVien> list) {
        if (list != null) {
            for (SinhVien sv : list) attachPhongHienTai(sv);
        }
        return list;
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
            if (auditLogService != null) {
                auditLogService.log("SYSTEM", "THÊM_SINH_VIÊN", "Thêm mới sinh viên: " + sinhVien.getMaSV() + " - " + sinhVien.getHoTen());
            }
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
        if (auditLogService != null) {
            auditLogService.log("SYSTEM", "CẬP_NHẬT_SINH_VIÊN", "Cập nhật thông tin sinh viên mã: " + sinhVien.getMaSV());
        }
    }

    @Override
    public void xoaSinhVien(String maSV) {
        timTheoMa(maSV);
        sinhVienRepo.delete(maSV);
        if (auditLogService != null) {
            auditLogService.log("SYSTEM", "XÓA_SINH_VIÊN", "Xóa sinh viên mã: " + maSV);
        }
    }

    @Override
    public SinhVien timTheoMa(String maSV) {
        SinhVien sv = sinhVienRepo.findById(maSV)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Không tìm thấy sinh viên: " + maSV));
        return attachPhongHienTai(sv);
    }

    @Override
    public List<SinhVien> layTatCa() {
        return attachPhongHienTaiList(sinhVienRepo.findAll());
    }

    @Override
    public List<SinhVien> timTheoTen(String keyword) {
        return attachPhongHienTaiList(sinhVienRepo.findByHoTen(keyword));
    }
    
    @Override
    public List<SinhVien> timTheoKeyword(String keyword) {
        return attachPhongHienTaiList(sinhVienRepo.findByKeyword(keyword));
    }

    @Override
    public List<SinhVien> layDanhSachPhanTrang(int page, int size) {
        return attachPhongHienTaiList(sinhVienRepo.findAll(page, size));
    }

    @Override
    public long demTongSo() {
        return sinhVienRepo.count();
    }

    @Override
    public List<SinhVien> timTheoKeywordPhanTrang(String keyword, int page, int size) {
        return attachPhongHienTaiList(sinhVienRepo.findByKeyword(keyword, page, size));
    }

    @Override
    public long demTheoKeyword(String keyword) {
        return sinhVienRepo.countByKeyword(keyword);
    }
}
