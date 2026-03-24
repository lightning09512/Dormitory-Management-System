package com.ktx.repository;

import com.ktx.config.JpaUtil;
import com.ktx.model.SinhVien;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for SinhVienRepository
 */
class SinhVienRepositoryTest {

    private SinhVienRepository repository;

    @BeforeEach
    void setUp() {
        // Initialize repository for testing
        repository = new SinhVienRepositoryImpl();
    }

    @AfterEach
    void tearDown() {
        // Clean up test data if needed
        if (JpaUtil.getEmf() != null) {
            JpaUtil.getEmf().close();
        }
    }

    @Test
    void testSaveAndFindById_Success() {
        // Given
        SinhVien sinhVien = new SinhVien(
            "TEST001", "Test Student", LocalDate.of(2000, 1, 1),
            "Nam", "TEST", "Test Class", "0123456789",
            "Test City", "test@test.com", "testuser", "hashedpass"
        );

        // When
        repository.save(sinhVien);
        Optional<SinhVien> found = repository.findById("TEST001");

        // Then
        assertTrue(found.isPresent());
        SinhVien result = found.get();
        assertEquals("TEST001", result.getMaSV());
        assertEquals("Test Student", result.getHoTen());
        assertEquals("Nam", result.getGioiTinh());
    }

    @Test
    void testFindById_NotFound() {
        // When
        Optional<SinhVien> result = repository.findById("NONEXISTENT");

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    void testFindAll_Success() {
        // Given
        SinhVien sv1 = new SinhVien("TEST001", "Student 1", LocalDate.of(2000, 1, 1),
                "Nam", "TEST", "Test Class", "0123456789",
                "Test City", "test1@test.com", "testuser1", "hashedpass1");
        
        SinhVien sv2 = new SinhVien("TEST002", "Student 2", LocalDate.of(2000, 2, 2),
                "Nữ", "TEST", "Test Class", "0123456788",
                "Test City", "test2@test.com", "testuser2", "hashedpass2");

        // When
        repository.save(sv1);
        repository.save(sv2);
        List<SinhVien> result = repository.findAll();

        // Then
        assertNotNull(result);
        assertTrue(result.size() >= 2); // May contain existing data
    }

    @Test
    void testFindByHoTen_Success() {
        // Given
        SinhVien sinhVien = new SinhVien("TEST001", "Nguyễn Văn A", LocalDate.of(2000, 1, 1),
                "Nam", "TEST", "Test Class", "0123456789",
                "Test City", "test@test.com", "testuser", "hashedpass");

        // When
        repository.save(sinhVien);
        List<SinhVien> result = repository.findByHoTen("Nguyễn");

        // Then
        assertNotNull(result);
        assertTrue(result.stream().anyMatch(sv -> sv.getHoTen().contains("Nguyễn")));
    }

    @Test
    void testFindByKeyword_Success() {
        // Given
        SinhVien sinhVien = new SinhVien("TEST001", "Test Student", LocalDate.of(2000, 1, 1),
                "Nam", "TEST", "Test Class", "0123456789",
                "Test City", "test@test.com", "testuser", "hashedpass");

        // When
        repository.save(sinhVien);
        
        // Test search by MSSV
        List<SinhVien> resultByMaSV = repository.findByKeyword("TEST");
        assertTrue(resultByMaSV.stream().anyMatch(sv -> sv.getMaSV().contains("TEST")));
        
        // Test search by name
        List<SinhVien> resultByName = repository.findByKeyword("Test");
        assertTrue(resultByName.stream().anyMatch(sv -> sv.getHoTen().contains("Test")));
    }
}
