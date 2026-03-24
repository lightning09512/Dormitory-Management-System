package com.ktx.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConfig – Quản lý kết nối cơ sở dữ liệu MySQL.
 *
 * <p>Sử dụng Singleton để đảm bảo chỉ tồn tại một Connection
 * trong suốt vòng đời ứng dụng desktop (Java Swing).
 * Gọi {@link #getConnection()} để lấy kết nối;
 * gọi {@link #closeConnection()} khi thoát ứng dụng.
 */
public class DBConfig {

    // ----------------------------------------------------------------
    // Thông tin kết nối – chỉnh sửa cho phù hợp môi trường thực tế
    // ----------------------------------------------------------------
    private static final String HOST     = "localhost";
    private static final String PORT     = "3306";
    private static final String DATABASE = "QuanLyKTX";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";          // Đổi thành mật khẩu thực

    private static final String URL =
            "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE
            + "?useUnicode=true"
            + "&characterEncoding=UTF-8"
            + "&serverTimezone=Asia/Ho_Chi_Minh"
            + "&useSSL=false"
            + "&allowPublicKeyRetrieval=true";

    // ----------------------------------------------------------------
    // Singleton – Connection duy nhất
    // ----------------------------------------------------------------
    private static Connection connection = null;

    /** Ngăn khởi tạo từ bên ngoài. */
    private DBConfig() {}

    /**
     * Trả về Connection đang mở. Nếu chưa có (hoặc đã bị đóng),
     * sẽ tạo kết nối mới trước khi trả về.
     *
     * @return {@link Connection} tới MySQL
     * @throws SQLException nếu không thể kết nối
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("[DBConfig] Kết nối CSDL thành công.");
            } catch (ClassNotFoundException e) {
                throw new SQLException(
                        "Không tìm thấy MySQL JDBC Driver. "
                        + "Hãy thêm mysql-connector-j vào classpath.", e);
            }
        }
        return connection;
    }

    /**
     * Đóng Connection hiện tại (gọi khi thoát ứng dụng).
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    System.out.println("[DBConfig] Đã đóng kết nối CSDL.");
                }
            } catch (SQLException e) {
                System.err.println("[DBConfig] Lỗi khi đóng kết nối: " + e.getMessage());
            } finally {
                connection = null;
            }
        }
    }
}
