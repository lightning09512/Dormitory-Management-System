package com.ktx.service;

import com.ktx.model.SinhVien;
import com.ktx.repository.SinhVienRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for SinhVienService
 */
@ExtendWith(MockitoExtension.class)
class SinhVienServiceTest {

    @Mock
    private SinhVienRepository sinhVienRepo;

    private SinhVienService sinhVienService;

    private SinhVien testSinhVien;

    @BeforeEach
    void setUp() {
        sinhVienService = new SinhVienServiceImpl(sinhVienRepo);
        
        testSinhVien = new SinhVien(
            "20210001", "Nguyễn Văn A", LocalDate.of(2000, 1, 1),
            "Nam", "CNTT", "Công nghệ thông tin", "0123456789",
            "Hà Nội", "test@example.com", "testuser", "hashedpassword"
        );
    }

    @Test
    void testThemSinhVien_Success() {
        // Given
        when(sinhVienRepo.findById("20210001")).thenReturn(Optional.empty());

        // When
        sinhVienService.themSinhVien(testSinhVien);

        // Then
        verify(sinhVienRepo, times(1)).save(testSinhVien);
    }

    @Test
    void testThemSinhVien_DuplicateMaSV_ThrowsException() {
        // Given
        when(sinhVienRepo.findById("20210001")).thenReturn(Optional.of(testSinhVien));

        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> sinhVienService.themSinhVien(testSinhVien)
        );
        
        assertEquals("Mã sinh viên đã tồn tại: 20210001", exception.getMessage());
        verify(sinhVienRepo, never()).save(any());
    }

    @Test
    void testTimTheoMa_Success() {
        // Given
        when(sinhVienRepo.findById("20210001")).thenReturn(Optional.of(testSinhVien));

        // When
        SinhVien result = sinhVienService.timTheoMa("20210001");

        // Then
        assertNotNull(result);
        assertEquals("20210001", result.getMaSV());
        assertEquals("Nguyễn Văn A", result.getHoTen());
    }

    @Test
    void testTimTheoMa_NotFound_ThrowsException() {
        // Given
        when(sinhVienRepo.findById("99999999")).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> sinhVienService.timTheoMa("99999999")
        );
        
        assertEquals("Không tìm thấy sinh viên: 99999999", exception.getMessage());
    }

    @Test
    void testLayTatCa_Success() {
        // Given
        List<SinhVien> expectedList = Arrays.asList(testSinhVien);
        when(sinhVienRepo.findAll()).thenReturn(expectedList);

        // When
        List<SinhVien> result = sinhVienService.layTatCa();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testSinhVien, result.get(0));
    }

    @Test
    void testTimTheoTen_Success() {
        // Given
        List<SinhVien> expectedList = Arrays.asList(testSinhVien);
        when(sinhVienRepo.findByHoTen("Nguyễn")).thenReturn(expectedList);

        // When
        List<SinhVien> result = sinhVienService.timTheoTen("Nguyễn");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testSinhVien, result.get(0));
    }

    @Test
    void testTimTheoKeyword_Success() {
        // Given
        List<SinhVien> expectedList = Arrays.asList(testSinhVien);
        when(sinhVienRepo.findByKeyword("20210001")).thenReturn(expectedList);

        // When
        List<SinhVien> result = sinhVienService.timTheoKeyword("20210001");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testSinhVien, result.get(0));
    }
}
