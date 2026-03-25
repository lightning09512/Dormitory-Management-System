package com.ktx.service.impl;

import com.ktx.config.JpaUtil;
import com.ktx.model.NhanVien;
import com.ktx.repository.NhanVienRepository;
import com.ktx.service.NhanVienService;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

/**
 * Triển khai {@link NhanVienService} sử dụng JPA và BCrypt.
 */
public class NhanVienServiceImpl implements NhanVienService {

    private final NhanVienRepository repo;

    public NhanVienServiceImpl(NhanVienRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<NhanVien> getAll() {
        return repo.findAll();
    }

    @Override
    public void addNhanVien(NhanVien nv) {
        if (nv.getMaNV() == null || nv.getMaNV().trim().isEmpty()) {
            nv.setMaNV("NV" + System.currentTimeMillis() % 100000000L); // Mã tạm
        }
        
        if (repo.findById(nv.getMaNV()).isPresent()) {
            throw new IllegalArgumentException("Mã Nhân viên đã tồn tại: " + nv.getMaNV());
        }
        
        if (repo.findByTaiKhoan(nv.getTaiKhoan()).isPresent()) {
            throw new IllegalArgumentException("Tài khoản đã tồn tại: " + nv.getTaiKhoan());
        }

        // Hash mật khẩu mặc định "123456"
        nv.setMatKhau(BCrypt.hashpw("123456", BCrypt.gensalt()));
        
        repo.save(nv);
    }

    @Override
    public void updateNhanVien(NhanVien nv) {
        NhanVien existing = repo.findById(nv.getMaNV())
                .orElseThrow(() -> new IllegalArgumentException("Nhân viên không tồn tại: " + nv.getMaNV()));
        
        existing.setHoTen(nv.getHoTen());
        existing.setSoDT(nv.getSoDT());
        existing.setVaiTro(nv.getVaiTro());

        repo.update(existing);
    }

    @Override
    public void deleteNhanVien(String maNV) {
        repo.delete(maNV);
    }

    @Override
    public void resetPassword(String maNV) {
        NhanVien nv = repo.findById(maNV)
                .orElseThrow(() -> new IllegalArgumentException("Nhân viên không tồn tại: " + maNV));
        
        nv.setMatKhau(BCrypt.hashpw("123456", BCrypt.gensalt()));
        repo.update(nv);
    }
}
