# KỊCH BẢN BÁO CÁO ĐỒ ÁN MÔN OOSE (CÔNG NGHỆ PHẦN MỀM HƯỚNG ĐỐI TƯỢNG)
**Đề tài:** Hệ thống Quản lý Ký Túc Xá (KTX)

File này được soạn cực kỳ chi tiết, dành riêng cho bạn chưa nắm vững kiến thức kỹ thuật lập trình. 
Hãy đọc đi đọc lại phần "Giải thích", sau đó **học thuộc lòng phần "Kịch bản nói"** để tự tin báo cáo nhé!

---

## PHẦN 1: TÌM HIỂU CẤU TRÚC THƯ MỤC & FILE TRONG PROJECT
*(Thầy cô báo cáo Môn OOSE rất hay bắt sinh viên mở project lên và hỏi: "Thế thư mục Controller chứa gì? File Service dùng để làm gì?". Bạn hãy đọc kỹ để hiểu bản chất)*

Trong Project của nhóm, mã nguồn (Code) được đặt trong thư mục `src/main/java/com/ktx/` và chia làm **5 Tầng (Layer) chính**. Sự phân chia này tuân thủ theo mô hình **MVC (Model - View - Controller)** kết hợp **kiến trúc đa tầng (Layered Architecture)**. Việc này đặc biệt quan trọng trong Lập trình Hướng Đối Tượng vì nó giúp code gọn gàng, chia để trị và dễ bảo trì.

1.  **Tầng `model` (Chữ M trong MVC):**
    *   **Nó là gì:** Chứa các đối tượng (Class) phản ánh các "thực thể" trong thế giới thực. Ví dụ: `SinhVien.java` (Sinh viên), `NhanVien.java` (Nhân viên), `HoaDonTienPhong.java` (Hóa đơn).
    *   **Thầy hỏi:** *"Tầng này làm nhiệm vụ gì?"*
    *   **Bạn đáp:** *"Dạ thưa thầy, tầng Model chứa các Entity (Thực thể). Mỗi Class ở tầng này sẽ được ánh xạ trực tiếp thành một Bảng (Table) dưới Cơ sở dữ liệu thông qua công nghệ Hibernate ORM ạ."*
2.  **Tầng `view` (Chữ V trong MVC):**
    *   **Nó là gì:** Chứa các cửa sổ, hộp thoại, giao diện đồ họa. Nhóm dùng **Java Swing** để vẽ ra các giao diện như Ô nhập chữ, Bảng dữ liệu, Nút bấm. Ví dụ: `NhatKyHeThongPanel.java`. 
    *   Khác với Web dùng HTML, ở Java Desktop người ta dùng Swing. Nơi này **TỐI KỴ** việc viết các thuật toán tính toán phức tạp. Nó chỉ để "Hiển thị".
3.  **Tầng `controller` (Chữ C trong MVC):**
    *   **Nó là gì:** Kẻ đứng giữa View và Service. Khi người dùng bấm nút [Thêm Sinh Viên] trên màn hình (ở View), lệnh sẽ được truyền xuống `Controller`. Controller sẽ vơ lấy các dữ liệu mà người dùng vừa nhập (Tên, Mã SV), đóng gói lại và gọi thằng `Service` ra để xử lý.
    *   Ví dụ: `NhanVienController.java` hay `AuditLogController.java`.
4.  **Tầng `service` (Quan Trọng Nhất - Nơi chứa "Não" của phần mềm):**
    *   **Nó là gì:** Chứa 100% các thuật toán và logic nghiệp vụ. Ví dụ: Hàm tính tiền phòng `HoaDonTienPhongServiceImpl.java` (lấy số ngày x đơn giá gói thanh toán), hoặc logic kiểm tra xem ngày sinh của sinh viên có hợp lệ không (phải nhỏ hơn ngày hiện tại).
    *   **Thầy hỏi:** *"Tại sao không dồn code tính toán vào Controller luôn mà phải đẻ ra tầng Service?"*
    *   **Bạn đáp:** *"Dạ vì nguyên lý Single Responsibility (Đơn trách nhiệm) ạ. Controller chỉ lo tương tác với Giao diện, còn Service chuyên xử lý logic. Nhờ vậy nếu sau này nhóm muốn làm một cái Web thay cho cái App Desktop này, nhóm chỉ việc vứt cái View và Controller đi làm lại, còn phần Service (Logic lõi) vẫn tái sử dụng được 100% ạ."*
5.  **Tầng `repository`:**
    *   **Nó là gì:** Khi `service` tính toán xong xuôi và bảo: *"Dữ liệu đúng rồi, đem lưu xuống Database đi"*, lệnh sẽ được truyền tới Repository. Tầng này chứa các đoạn code để "nói chuyện" với Cơ sở dữ liệu (SQL Server/MySQL) để Thêm, Sửa, Xóa, Lấy dữ liệu lên.

**Các thư mục khác quan trọng của môn OOSE:**
*   **Thư mục `PlantUML/` hoặc `docs/diagrams/`:** Chứa tài sản quý giá nhất của môn này, đó là các file biểu đồ UML (Use Case, Activity Diagram, Sequence Diagram). Nó chứng minh nhóm có làm khâu "Phân tích thiết kế" trước khi bắt tay vào code.

> **TÓM LẠI LUỒNG DỮ LIỆU ĐỂ GIẢI THÍCH CHO THẦY:** 
> Giao diện (View) -> Nhận lệnh (Controller) -> Xử lý nghiệp vụ, kiểm tra ràng buộc (Service) -> Lưu vào CSDL (Repository) dựa trên khuôn mẫu thực thể (Model).

---

## PHẦN 2: KỊCH BẢN NÓI TRƯỚC LỚP (HỌC THUỘC LÒNG TỪ ĐÂY)

*(Lưu ý: Bạn hãy tập thao tác: Cầm chuột mở ứng dụng lên -> Miệng đọc lời thoại - Tay bấm chuột tương ứng. Lặp đi lặp lại thật nhiều lần ở nhà cho khớp).*

### BƯỚC 1: MỞ ĐẦU & ĐẶT VẤN ĐỀ
**(Mở app lên màn hình đăng nhập, hoặc bật slide trang chủ)**

"Dạ em xin chào Thầy và các bạn. Hôm nay nhóm em xin trình bày đồ án kết thúc môn Công nghệ Phần mềm Hướng đối tượng (OOSE) với đề tài: **Phần mềm Quản lý Ký Túc Xá**. 
Thưa thầy, thực trạng việc quản lý hàng ngàn sinh viên, sắp xếp phòng ở, và làm hóa đơn tiền phòng thủ công bằng sổ sách hiện nay tốn rất nhiều thời gian và dễ xảy ra sai sót, thất thoát dữ liệu. Vì vậy, hệ thống của chúng em được làm ra nhằm giải quyết câu chuyện **số hóa toàn bộ quy trình vận hành** trong một khu Ký túc xá tĩnh."

### BƯỚC 2: PHÂN TÍCH THIẾT KẾ CẤU TRÚC (CỰC KỲ QUAN TRỌNG ĐỂ ĂN ĐIỂM)
**(Bấm slide hoặc chuyển tab code sang phần thư mục/UML)**

"Dựa trên phương pháp hệ thống Hướng đối tượng, tụi em đã nhận diện các **Tác nhân (Actor)** chính là Quản trị viên và Nhân viên. Tương ứng với đó là các **Use Case** quản lý cốt lõi.

Đặc biệt, về mặt kiến trúc phần mềm, thay vì code lộn xộn trong một file, nhóm em đã áp dụng **mô hình MVC kết hợp với kiến trúc 3 tầng (Layered Architecture)**. 
- Thứ nhất, cấu trúc chia rõ **Tầng View** dùng Java Swing để hiển thị.
- Thứ hai, **Tầng Controller & Service** sẽ đảm nhận 100% luồng Logic nghiệp vụ kiểm soát. 
- Thứ ba, **Tầng Repository** kết nối CSDL mạnh mẽ bằng cơ chế mapping tự động thông qua **Hibernate ORM** để ánh xạ thẳng các Object (Đối tượng Model) xuống Database mà không cần viết các câu lệnh SQL INSERT phức tạp.
Cấu trúc này đáp ứng hoàn hảo yêu cầu tính Đóng gói và Dễ bảo trì của lập trình OOP."

### BƯỚC 3: DEMO CÁC CHỨC NĂNG (VỪA NÓI VỪA THAO TÁC CHUỘT)
**(Chuyển qua màn hình Dashboard Thống Kê)**

"Em xin Demo ứng dụng thực tế. Đầu tiên, ngay khi đăng nhập, hệ thống sẽ mở ra màn hình **Dashboard Thống Kê**. Ở đây, tầng Service sẽ query số liệu trực tiếp từ Database lên để đưa cho Admin cái nhìn tổng quan nhất về: Tổng số phòng trống, số lượng sinh viên đang ở và biểu đồ doanh thu ngay lập tức."

**(Chuyển qua màn hình Quản Lý Phòng / Sinh Viên)**

"Qua khu vực **Quản Lý Sinh Viên**, người dùng có thể Thêm, Sửa, Xóa, Tìm kiếm. Đặc biệt, nhóm em đã lập trình các ràng buộc xử lý rất chặt chẽ theo biểu đồ Activity thay vì chỉ Thêm/Xóa thông thờng. 
*Ví dụ:* Khi thêm 1 sinh viên mới, hàm kiểm tra ở lớp `Service` sẽ chạy. Nếu ngày sinh không hợp lý hoặc người dùng cố tình nhập trùng mã sinh viên, hệ thống sẽ ném ra ngoại lệ (Exception) và Controller sẽ chặn ngay lại, bung hộp thoại cảnh báo để bảo vệ tính toàn vẹn của Dữ liệu."

**(Chuyển qua màn hình Quản Lý Phí/Hóa Đơn)**

"Một chức năng lõi nữa là xử lý **Hóa Đơn Tiền Phòng**. Tại phần này, nhân viên chỉ cần chọn sinh viên và gói thanh toán, code ở tầng Service sẽ dựa vào Đối tượng Sinh Viên đó để tự động tính tổng tiền một cách chính xác tuyệt đối."

**(Chuyển qua màn hình Nhật Ký Hệ Thống)**

"Cuối cùng là phần Bảo mật. Phần mềm có module **Nhật Ký (Audit Log)**. Mọi thao tác như 'Thêm sinh viên', 'Xóa hóa đơn' của bất kỳ nhân viên nào cũng đều được bắt và lưu vết lại (lưu thời gian, người thao tác, hành động) vào Database. Nhờ vậy không nhân viên nào có thể thực hiện thao tác sai trái mà trốn tránh được ạ."

### BƯỚC 4: LỜI KẾT
**(Trở lại màn hình chính của App hoặc bật Slide Cảm ơn)**

"Dạ thông qua đồ án này, nhóm không chỉ học được cách code Java Swing, kết nối Database, mà quan trọng nhất là biết cách chuyển từ bản vẽ UML (thiết kế) sang code thực tế theo đúng nguyên lý Hướng đối tượng. Tuy phần mềm vẫn còn một số điểm chưa hoàn hảo, nhưng nhóm rất mong tiếp nhận sự góp ý của Thầy để hệ thống tốt hơn.
**Dạ bài trình bày của nhóm em đến đây là kết thúc, em xin trân trọng cảm ơn Thầy ạ!**"

---

## DỰ PHÒNG: CÁC CÂU HỎI "XOÁY" CỦA GIẢNG VIÊN & CÁCH TRẢ LỜI "CHỐNG BÍ"

**1. Thầy hỏi: "Em xử lý lúc báo lỗi thêm trùng Sinh viên / Lỗi ngày sinh như thế nào?"**
*   *Cách trả lời:* "Dạ, trước khi gọi hàm Lưu vào Database, ở trong tầng Service em có viết các điều kiện `if`. Nếu phát hiện trùng (hoặc ngày sinh sai), em dùng lệnh `throw new IllegalArgumentException(...)` để ném ra một cục lỗi. Xong ở bên ngoài giao diện Controller nó sẽ dùng khóa `try-catch` để vớ lấy cái lỗi đó và gọi `JOptionPane.showMessageDialog` bung thông báo đỏ lên màn hình ạ."

**2. Thầy hỏi: Câu lệnh SQL em viết chỗ nào mở thầy xem? Vì sao ít thấy SQL vậy?**
*   *Cách trả lời:* "Chỗ này là môn Hướng đối tượng nên thay vì em ngồi viết mấy câu lệnh chuỗi SQL (Insert into...) dài dòng dễ sai chính tả, thì nhóm đã nhúng thư viện có tên là **Hibernate ORM**.  Nó sẽ giúp em lấy nguyên cái Đối Tượng `SinhVien` trong RAM và tự động biến thành dòng dữ liệu lưu xuống DB cho nên source code của em ở tầng Repository thao tác rất ngắn gọn và hiện đại ạ."

**Chúc bạn ôn tập tốt và có một buổi báo cáo thật hoành tráng!**
