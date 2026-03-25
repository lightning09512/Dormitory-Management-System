import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class TestDB {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/QuanLyKTX", "root", "123456");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("DESCRIBE TrangThietBi");
            while (rs.next()) {
                System.out.println(rs.getString("Field") + " - " + rs.getString("Type"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
