-- Script tạo bảng HoaDonTienPhong cho Database QuanLyKTX
-- Hãy chạy Script này trong MySQL Workbench hoặc Command Line trước khi chạy ứng dụng.

USE QuanLyKTX;

CREATE TABLE IF NOT EXISTS HoaDonTienPhong (
    MaHDTP VARCHAR(15) PRIMARY KEY,
    MaHD VARCHAR(15) NOT NULL,
    GoiThanhToan INT NOT NULL,
    NgayLap DATE NOT NULL,
    TongTien DECIMAL(14,2) NOT NULL,
    TrangThai VARCHAR(50) NOT NULL,
    MaNV VARCHAR(10) NOT NULL,
    INDEX (MaHD),
    INDEX (MaNV)
);

-- Bạn có thể thêm khóa ngoại nếu muốn:
-- ALTER TABLE HoaDonTienPhong ADD CONSTRAINT FK_HDTP_HopDong FOREIGN KEY (MaHD) REFERENCES HopDong(MaHD);
