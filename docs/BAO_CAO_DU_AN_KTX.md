# Hệ thống quản lý Ký túc xá (Dormitory Management System)
**Công nghệ:** Java Swing MVC + Hibernate/JPA + MySQL

---

## 1. Mục tiêu dự án
Xây dựng một ứng dụng desktop quản lý ký túc xá cho trường đại học hoặc cao đẳng, hỗ trợ:
- Quản lý cơ sở vật chất: Dãy nhà, Phòng.
- Quản lý lưu trú: Sinh viên, Đơn đăng ký, Hợp đồng thuê phòng.
- Quản lý tài chính: Hóa đơn tiền phòng, Hóa đơn điện nước.
- Theo dõi hệ thống: Ghi chép lịch sử thao tác (Audit Log), Thống kê doanh thu.

Dự án được phát triển theo mô hình:
- **Java Swing** cho giao diện người dùng.
- **MVC** cho tổ chức mã nguồn.
- **Hibernate/JPA** để ánh xạ đối tượng – quan hệ (ORM).
- **MySQL** để lưu trữ dữ liệu.
- **Maven** để quản lý thư viện.
- Áp dụng các nguyên tắc **SOLID**.

## 2. Quy mô phù hợp
Dự án phù hợp cho nhóm sinh viên (2-4 người) với thời lượng hoàn thành từ 3-4 tuần.

## 3. Yêu cầu công nghệ
### 3.1. Công nghệ bắt buộc
- Java 17 hoặc Java 21
- Java Swing
- Maven
- MySQL 8.x
- Hibernate ORM / JPA
- Môi trường phát triển: IntelliJ IDEA, Eclipse...

### 3.2. Thư viện đề xuất
- `hibernate-core`, `jakarta.persistence-api`
- `mysql-connector-j`
- `lombok`
- Thư viện xuất file: `openpdf` (hoặc `itext`), `apache-poi` (nếu có xuất Excel).
- Thư viện log: `slf4j` / `logback-classic`.

## 4. Bài toán nghiệp vụ
Ban quản lý KTX cần một phần mềm giúp nhân viên:
1. Quản lý trạng thái và sức chứa của các phòng trong từng dãy nhà.
2. Nắm bắt thông tin sinh viên đang lưu trú.
3. Duyệt đơn đăng ký trực tuyến (hoặc offline) của sinh viên.
4. Lập hợp đồng lưu trú khi sinh viên được nhận phòng.
5. Ghi nhận chỉ số điện nước hàng tháng và phát hành hóa đơn.
6. Thu tiền phòng theo học kỳ/năm.
7. Thanh lý hợp đồng khi sinh viên chuyển đi.
8. Thống kê tình trạng trống của phòng và doanh thu KTX hàng tháng.

## 5. Chức năng chính
### 5.1. Quản lý Cơ sở vật chất (Dãy nhà, Phòng)
- Thêm/sửa/xóa dãy nhà, phòng.
- Theo dõi sức chứa phòng (loại giường tầng, số lượng tối đa).
- Tình trạng: Trống, Đang ở, Đầy.

### 5.2. Quản lý Sinh viên
- Thêm/sửa/xóa hồ sơ sinh viên (Mã SV, Họ tên, Quê quán, Lớp...).
- Tìm kiếm sinh viên theo tên hoặc mã.

### 5.3. Quản lý Đơn đăng ký và Hợp đồng
- Lập đơn đăng ký ở KTX.
- Chuyển đơn thành Hợp đồng (Ghi nhận phòng ở, ngày bắt đầu, ngày kết thúc).
- Chức năng **Thanh lý hợp đồng**: Tính toán công nợ và trả lại trạng thái cho phòng.

### 5.4. Quản lý Hóa đơn
- **Hóa đơn điện nước**: Nhập chỉ số cũ, chỉ số mới, tính ra thành tiền.
- **Hóa đơn tiền phòng**: Thu theo hợp đồng, có lịch sử thu tiền.
- Đánh dấu trạng thái: Đã thanh toán / Chưa thanh toán.

### 5.5. Thống kê, Báo cáo
- Tỷ lệ lấp đầy phòng.
- Thống kê doanh thu điện nước, tiền phòng.
- Báo cáo sinh viên nợ phí.

### 5.6. Quản trị hệ thống & Audit Log
- Quản lý tài khoản đăng nhập và cấp quyền (Admin / Staff).
- Audit Log (Lưu vết sử dụng): Lưu lại mọi hoạt động Insert/Update/Delete của nhân viên.

## 6. Yêu cầu phi chức năng
- Phải tách riêng UI, Nghiệp vụ, và Thao tác dữ liệu (DAO).
- Giao diện dễ sử dụng, rõ ràng. Khuyến khích sử dụng FlatLaf cho giao diện hiện đại.
- Không ghép mọi code SQL/Nghiệp vụ vào file UI JFrame.
- Có validate dữ liệu linh hoạt.

## 7. Kiến trúc hệ thống và MVC
- **View (`view`)**: Các lớp JFrame, JPanel, JDialog.
- **Controller (`controller`)**: Điều phối luồng xử lý và kết nối View với Service.
- **Service (`service`)**: Chứa Business Logic, kiểm tra luật nghiệp vụ (ví dụ: Phòng này đã đầy chưa?).
- **Repository (`dao`/`repository`)**: Làm việc với EntityManager của Hibernate để thao tác DB.
- **Entity (`model`)**: Các thực thể mô phỏng bảng trong MySQL.

## 8. Cấu trúc Package tham khảo (Maven)
```text
src/main/java
└── com.ktx
    ├── config        (Cấu hình Hibernate: HibernateUtil)
    ├── model         (Các entity: SinhVien, Phong, HopDong...)
    ├── dao           (Các interface và class DAO: SinhVienDao, PhongDao...)
    ├── service       (Lớp nghiệp vụ: HopDongService, SinhVienService...)
    ├── controller    (Lớp điều khiển: HopDongController...)
    ├── view          (Giao diện: MainFrame, LoginFrame, HopDongPanel...)
    ├── util          (Tiện ích: DateUtil, PdfUtil...)
    └── App.java      (Lớp khởi chạy)
```

## 9. Thiết kế Dữ liệu cơ bản
1. **DayNha**: id, maDay, tenDay.
2. **Phong**: id, maPhong, loaiPhong, soGiuong, dayNha_id.
3. **SinhVien**: id, maSV, hoTen, sdt,...
4. **HopDong**: id, sinhVien_id, phong_id, ngayBatDau, ngayKetThuc, trangThai ("ĐANG_THUÊ", "THANH_LÝ").
5. **HoaDonDienNuoc**: id, phong_id, chiSoCu, chiSoMoi, thanhTien, trangThai...
6. **HoaDonTienPhong**: id, hopDong_id, soTien, ngayThu...
7. **NhanVien** (Tài khoản): id, username, password, chucVu.
8. **AuditLog**: id, tableName, action, recordId, userId, timestamp...

## 10. Yêu cầu nguyên lý SOLID
Quá trình xây dựng yêu cầu phân tách trách nhiệm thông qua giao diện (Interface).
- **S (Single Responsibility)**: `PhongController` chỉ xử lý sự kiện UI, không viết lệnh insert CSDL trực tiếp, mà gọi `PhongService`.
- **D (Dependency Inversion)**: `Controller` nên phụ thuộc vào bản vẽ (Interface) của Service thay vì lớp hiện thực trực tiếp để dễ dàng mở rộng và test.

## 11. Các Diagram bắt buộc (Tối thiểu cần có)
- **Use Case Diagram**: Phân chia Actor `Admin` và `Nhân Viên`. Các Use case quản lý phòng, đăng ký, lập hợp đồng, trả phòng.
- **Class Diagram**: Minh họa các lớp theo cấu trúc Model-View-Controller và Service/Repository.
- **Sequence Diagram**: Mô tả một tiến trình đầy đủ, vd: `Thêm sinh viên`, `Lập hợp đồng và tính phòng`, `Thanh toán hóa đơn`.
- **Activity Diagram**: Lược đồ quy trình hoạt động: Vd luồng duyệt đăng ký nhận phòng tới in hợp đồng, hoặc luồng lập hóa đơn hàng tháng.
