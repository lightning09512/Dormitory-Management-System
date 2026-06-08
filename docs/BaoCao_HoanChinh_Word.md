# BÁO CÁO MÔN HỌC: PHÂN TÍCH THIẾT KẾ HỆ THỐNG (OOSE)
**ĐỀ TÀI: HỆ THỐNG QUẢN LÝ KÝ TÚC XÁ BẰNG JAVA SWING & HIBERNATE**

---

## 1. KHẢO SÁT HIỆN TRẠNG & CÁC HỆ THỐNG ĐÃ CÓ

### 1.1 Khảo sát hiện trạng
Hiện nay, việc quản lý nội trú tại ký túc xá của các trường đại học/cao đẳng đang phải đối mặt với khối lượng sinh viên lớn và biến động liên tục. Các thủ tục từ đăng ký phòng, thu/trả phí, lập hợp đồng lưu trú cho đến quản lý chỉ số điện, nước thường được thực hiện chủ yếu qua sổ sách hoặc các file Excel rời rạc. Điều này dễ dẫn đến nhầm lẫn, làm mất thời gian tra cứu, khó bảo mật thông tin và khiến việc thống kê doanh thu trở nên thiếu chính xác.

### 1.2 Đánh giá các hệ thống quản lý KTX đã có
**Ưu điểm:**
- Các phần mềm hiện năng trên thị trường (dạng web/app từ công ty thứ 3) cung cấp đa dạng tiện ích và kết nối được với nhiều cổng thanh toán.
- Lưu trữ dữ liệu trên nền tảng đám mây lớn.

**Khuyết điểm:**
- Hệ thống quá cồng kềnh, tính năng dư thừa không phù hợp quy trình riêng lẻ của quy mô nhà trường nhỏ.
- Phí bản quyền lớn, đôi khi đòi hỏi hạ tầng mạng phức tạp.
- Khó tùy biến hoặc tích hợp chung vào các cơ sở dữ liệu sinh viên rời rạc đang có rải rác.

### 1.3 Phạm vi đề tài (Từ ưu khuyết trên)
Từ khảo sát trên, đề tài **Hệ thống Quản lý Ký túc xá** của nhóm hướng tới việc xây dựng một giải pháp **Desktop App (nhỏ gọn, bảo mật vật lý cao, sử dụng nội bộ phòng ban)** nhằm số hóa toàn bộ quy trình cốt lõi tại KTX. 
**Phạm vi hệ thống tập trung vào:** 
- Quản lý Sinh viên & Số lượng phòng, sức chứa, dãy nhà.
- Số hóa thủ tục Lập/Thanh lý Hợp đồng.
- Cập nhật số điện, nước & in hóa đơn minh bạch.
- Thống kê doanh thu theo tháng/năm.

---

## 2. THIẾT KẾ DỮ LIỆU
### 2.1 Thiết kế ERD & Ánh xạ Database <=> Entity Classes (Hibernate)
*(Chèn hình ảnh sơ đồ ERD vào đây)*
Hệ thống sử dụng **Hibernate framework** làm cầu nối ORM (Object-Relational Mapping). Sơ đồ quan hệ thực thể (ERD) được ánh xạ trực tiếp sang các `Entity Class` trong Java như sau:

| Bảng Cơ sở dữ liệu (MySQL) | Thực thể Java (Entity Class) | Các mối quan hệ (Cột / Khóa ngoại) |
| :--- | :--- | :--- |
| `sinh_vien` | `SinhVien` | Ánh xạ `@OneToMany` sang `HopDong` và `DonDangKy`. |
| `phong` | `Phong` | Thuộc về `DayNha` (`@ManyToOne`). Chứa nhiều hợp đồng. |
| `hop_dong` | `HopDong` | Mang khóa ngoại `maSV`, `maPhong`, `maNV` (`@ManyToOne`). |
| `hoa_don` | `HoaDon` | Ánh xạ với `Phong`. Quản lý chỉ số điện nước. |
| `nhan_vien` | `NhanVien` | Bảng định danh với cột phân quyền (Role). |

### 2.2 Các quan hệ dự tuyển (Lược đồ CSDL Relational Schema)
*(Ghi lại danh sách các bảng giống ERD nhưng dưới dạng chữ)*
- SINH_VIEN(**maSV**, hoTen, ngaySinh, queQuan, sdt, gioiTinh)
- DAY_NHA(**maDay**, tenDay, soTang)
- PHONG(**maPhong**, loaiPhong, sucChua, giaPhong, trangThai, *maDay*)
- HOP_DONG(**maHD**, ngayBD, ngayKT, trangThai, tienCoc, *maSV*, *maPhong*, *maNV*)
- HOA_DON(**maHDon**, thang, nam, chiSoDienCu, chiSoDienMoi, ... ,*maPhong*, *maNV*)

### 2.3 Lệnh SQL tạo dữ liệu mẫu
*(Dán script lệnh SQL DDL và INSERT INTO tạo một số dữ liệu ban đầu tại đây)*
> Hệ thống được đính kèm file `create_database.sql` để thầy(cô) tiện dụng khởi tạo môi trường.

---

## 3. THIẾT KẾ CHỨC NĂNG TỪ CÁC USE CASE
*(Chèn hình Tổng quan Use Case toàn hệ thống vào đây)*

Theo yêu cầu môn học, nhóm xin trích chọn **2 Use case chính** và phức tạp nhất để trình bày thiết kế hoạt động: Cụm chức năng **Lập Hợp đồng** và **Lập Hóa đơn thu phí**.

### 3.1. Usecase 1: Lập Hợp đồng thuê phòng
**1. Use case diagram:** Dành riêng cho Lập hợp đồng *(Chèn biểu đồ use case nhỏ)*.
**2. Activity diagram (Quy trình hoạt động):**
- **Bước 1:** Nhân viên ấn chọn chức năng lập hợp đồng.
- **Bước 2:** Hệ thống hiển thị Form. NV điền Mã Phòng, Mã SV, Ngày bắt đầu và Số tiền cọc.
- **Bước 3:** Hệ thống kiểm tra: Phòng còn trống hay không? SV đó đã có hợp đồng thuê nào đang hoạt động chưa?
- **Bước 4:** Nếu hợp lệ -> Gọi Hibernate lưu dữ liệu Hợp đồng xuống SQL -> Chuyển trạng thái sinh viên/phòng sang Đang ở.
- **Bước 5:** Báo hoàn tất giao dịch.
*(Chèn hình ảnh Activity Diagram Lập Hợp đồng)*

**3. Sequence Diagram (Tuần tự):**
*(Chèn hình Sequence Diagram Lập Hợp đồng - có thể hiện View gọi Controller, Controller gọi HopDongService kiểm tra rồi push xuống Database)*

**4. Class Diagram & Object Diagram (Thiết kế lớp):**
*(Chèn hình Class Diagram liên quan đến SinhVien, Phong, HopDong và HopDongService, HopDongController)*

---

### 3.2. Usecase 2: Quản lý Hóa đơn (Chốt điện nước cuối tháng)
**1. Use case diagram:** Hóa đơn *(Chèn hình)*
**2. Activity diagram:**
- **Bước 1:** Chốt số cuối tháng.
- **Bước 2:** Chọn Phòng, hệ thống tự gọi SQL truy vấn "Chỉ số điện/nước mới" của tháng vừa rồi và lấy đó làm chỉ số cũ cho tháng này.
- **Bước 3:** Nhập Số diện mới, Số nước mới. Nhập Phụ phí.
- **Bước 4:** Hệ thống tính toán (Lấy số mới - số cũ) * Đơn giá. Cộng dồn -> Tổng tiền.
- **Bước 5:** Xuất hóa đơn lưu vào Database với trạng thái "Chưa thanh toán".
*(Chèn hình Activity đi kèm tính toán Hóa đơn)*

**3. Sequence Diagram:**
*(Chèn hình Sequence Quản lý Hóa đơn - Service tự động tra cứu Cấu Hình Cước Cơ Bản và trả tổng thành tiền cho View)*

**4. Class Diagram & Object Diagram:**
*(Chèn sơ đồ các Class như HoaDon, HoaDonTienPhong, CauHinhGia, Phong)*

---

## 4. CÔNG NGHỆ CHỌN ĐỂ TRIỂN KHAI VÀ MÔ HÌNH KIẾN TRÚC
Nhóm thi công bằng hệ sinh thái lập trình nguyên bản Java để đảm bảo xử lý logic chặt chẽ:
- **Kiến trúc:** MVC Layer (UI Panel - Controller - Service). **(Chú thích thêm: Do dự án ở quy mô cục bộ, nhằm tối ưu hóa đường đi của dữ liệu, nhóm đã tích hợp EntityManager trực tiếp tại tầng `Service` thay vì qua `DAO`).**
- **Ngôn ngữ & Giao diện:** Java (SE) / Giao diện Java Swing chuẩn MVC.
- **Cơ sở dữ liệu:** MySQL Server (Lưu trữ quan hệ).
- **ORM / Truy xuất dữ liệu:** Hibernate (Framework chuẩn mạnh mẽ nhất của Java giúp ánh xạ thực thể).
- **Trình quản lý đóng gói:** Apache Maven.

---

## 5. HƯỚNG DẪN CÀI ĐẶT
1. Tải và giải nén source code.
2. Mở file thư mục chứa Project vào **IntelliJ IDEA** hoặc **Eclipse** (Dự án load theo định dạng Maven `pom.xml`).
3. Mở phần mềm hệ quản trị CSDL (ví dụ MySQL Workbench hoặc XAMPP) import (chạy) file `.sql` kèm theo của nhóm để tạo database trước.
4. Điều chỉnh file `src/main/resources/META-INF/persistence.xml` (sửa username, password đúng với máy chấm của cá nhân, URL localhost:3306/ktx).
5. Sau khi Build Maven load đủ cấu hình Hibernate, chạy Run file `App.java`.

## 6. HƯỚNG DẪN SỬ DỤNG
- Đăng nhập phần mềm qua tài khoản Admin (admin/123456) hoặc Staff.
- Danh mục dọc bên trái là các nhóm chức năng.
- Ban đầu, sinh viên có thể xem tab "Dãy Nhà", "Phòng" để tạo cơ sở hạ tầng.
- Tới Tab Sinh viên để thêm dữ liệu người trọ.
- Tại Menu Hợp đồng, Chọn thông tin Sinh viên cùng Phòng để thực hiện gắp dữ liệu ký kết.
- Cuối tháng vào tab Hóa đơn, chọn phòng và ghi số điện mới, hệ thống tự khấu trừ lưu lại tiền thu. Mọi hoạt động đều có thông báo qua cửa sổ JOptionePane.
