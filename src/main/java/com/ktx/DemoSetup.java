package com.ktx;

import com.ktx.model.NhanVien;
import com.ktx.repository.NhanVienRepository;
import com.ktx.repository.impl.NhanVienRepositoryImpl;
import org.mindrot.jbcrypt.BCrypt;

public class DemoSetup {
    public static void main(String[] args) {
        NhanVienRepository repo = new NhanVienRepositoryImpl();
        
        // Kiểm tra xem đã có admin chưa
        if (repo.findByTaiKhoan("admin").isPresent()) {
            System.out.println("Tài khoản admin đã tồn tại!");
            return;
        }

        // Tạo nhân viên Admin mặc định
        NhanVien admin = new NhanVien();
        admin.setMaNV("NV001");
        admin.setHoTen("Quản trị viên hệ thống");
        admin.setVaiTro("Manager");
        admin.setSoDT("0123456789");
        admin.setTaiKhoan("admin");
        
        // Hash mật khẩu "admin123"
        String hashedPw = BCrypt.hashpw("admin123", BCrypt.gensalt());
        admin.setMatKhau(hashedPw);

        repo.save(admin);
        
        System.out.println("Đã tạo thành công tài khoản mặc định!");
        System.out.println("Tài khoản: admin");
        System.out.println("Mật khẩu: admin123");
        
        // Buộc dừng (do JPA EntityManagerFactory có thể giữ thread)
        System.exit(0);
    }
}
