import com.ktx.model.SinhVien;
import com.ktx.service.SinhVienService;
import com.ktx.service.impl.SinhVienServiceImpl;
import com.ktx.repository.impl.SinhVienRepositoryImpl;
import com.ktx.repository.impl.HopDongRepositoryImpl;
import java.time.LocalDate;

public class TestAdd {
    public static void main(String[] args) {
        try {
            SinhVienService service = new SinhVienServiceImpl(new SinhVienRepositoryImpl(), new HopDongRepositoryImpl());
            SinhVien sv = new SinhVien(
                "TEST1234", "Nguyen Test", LocalDate.now(), "Nam", "Lop", "Khoa", "0123456789", null, "test@test.com", "testacc1", "password123"
            );
            service.themSinhVien(sv);
            System.out.println("SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
