package com.ktx.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * JpaUtil – Nắm giữ {@link EntityManagerFactory} chia sẻ toàn ứng dụng.
 *
 * <p>persistence-unit name phải khớp với tên khai báo trong
 * {@code src/main/resources/META-INF/persistence.xml} ("ktxPU").</p>
 *
 * <p>Gọi {@link #shutdown()} khi thoát ứng dụng (ví dụ trong
 * {@code WindowListener#windowClosing}) để giải phóng tài nguyên.</p>
 *
 * <pre>
 * // Cách dùng trong Repository:
 * EntityManager em = JpaUtil.getEmf().createEntityManager();
 * try {
 *     ...
 * } finally {
 *     em.close();
 * }
 * </pre>
 */
public class JpaUtil {

    private static final String PERSISTENCE_UNIT = "ktxPU";

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);

    /** Ngăn khởi tạo từ bên ngoài. */
    private JpaUtil() {}

    /**
     * Trả về {@link EntityManagerFactory} duy nhất của ứng dụng.
     *
     * @return EntityManagerFactory đã được khởi tạo
     */
    public static EntityManagerFactory getEmf() {
        return emf;
    }

    /**
     * Đóng {@link EntityManagerFactory} – gọi khi tắt ứng dụng.
     */
    public static void shutdown() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            System.out.println("[JpaUtil] EntityManagerFactory đã đóng.");
        }
    }
}
