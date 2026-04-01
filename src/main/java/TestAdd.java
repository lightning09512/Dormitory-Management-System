import com.ktx.model.SinhVien;
import com.ktx.service.SinhVienService;
import com.ktx.service.impl.SinhVienServiceImpl;
import com.ktx.repository.impl.SinhVienRepositoryImpl;
import com.ktx.repository.impl.HopDongRepositoryImpl;
import java.time.LocalDate;

public class TestAdd {
    public static void main(String[] args) {
        try {
            com.ktx.repository.AuditLogRepository auditRepo = new com.ktx.repository.impl.AuditLogRepositoryImpl();
            com.ktx.service.AuditLogService auditService = new com.ktx.service.impl.AuditLogServiceImpl(auditRepo);
            SinhVienService service = new SinhVienServiceImpl(new SinhVienRepositoryImpl(), new HopDongRepositoryImpl(), auditService);
            String uniqueId = "T" + (System.currentTimeMillis() % 1000000);
            SinhVien sv = new SinhVien(
                uniqueId, "Nguyen Test", LocalDate.of(2005, 1, 1), "Nam", "Lop", "Khoa", "0123456789", null, "test" + uniqueId + "@test.com", "acc" + uniqueId, "password123"
            );
            service.themSinhVien(sv);
            System.out.println("SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
