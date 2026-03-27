package com.ktx.view;

import com.ktx.model.NhanVien;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Khung ứng dụng chính.
 * - Sidebar tối bên trái với nav buttons phẳng
 * - CardLayout ở center để chuyển giữa các màn hình
 */
public class MainFrame extends JFrame {

    public static final String CARD_DASHBOARD  = "dashboard";
    public static final String CARD_SINH_VIEN  = "sinhvien";
    public static final String CARD_PHONG      = "phong";
    public static final String CARD_HOP_DONG   = "hopdong";
    public static final String CARD_HOA_DON    = "hoadon";
    public static final String CARD_THIET_BI   = "thietbi";
    public static final String CARD_TIEN_PHONG = "tienphong";
    public static final String CARD_NHAN_VIEN  = "nhanvien";
    public static final String CARD_GIA        = "gia";
    public static final String CARD_AUDIT_LOG  = "auditlog";

    private final CardLayout  cardLayout  = new CardLayout();
    private final JPanel      contentPane = new JPanel(cardLayout);
    private final JButton     btnDangXuat = new JButton("Đăng xuất");

    // Nav buttons
    private JButton activeSidebar = null;

    public MainFrame(NhanVien user) {
        super("Hệ thống Quản lý Ký túc xá – " + user.getHoTen()
              + " [" + user.getVaiTro() + "]");
        buildUI(user);
        configFrame();
    }

    private void buildUI(NhanVien user) {
        setLayout(new BorderLayout());

        // ---- Sidebar ----
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(300, 0)); // Even wider sidebar
        sidebar.setBackground(UITheme.SIDEBAR_BG);
        sidebar.putClientProperty("FlatLaf.style", "arc: 0"); // No rounding on sidebar itself

        // Brand / Logo with Cover Image
        JPanel brand = new JPanel();
        brand.setOpaque(false);
        brand.setLayout(new BoxLayout(brand, BoxLayout.Y_AXIS));
        brand.setBorder(BorderFactory.createEmptyBorder(0, 0, 24, 0));
        
        // Cover Image
        ImageIcon coverIcon = new ImageIcon("sinh-vien-truong-nao-duoc-thue-ktx-khu-b-dhqg-tphcm-o-tro-hay-o-ktx-tiet-kiem-hon.webp");
        Image scaledCover = coverIcon.getImage().getScaledInstance(240, 120, Image.SCALE_SMOOTH);
        JLabel coverLabel = new JLabel(new ImageIcon(scaledCover));
        coverLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        coverLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        brand.add(coverLabel);
        
        // Logo Text
        JPanel logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        logoPanel.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));
        
        // --- Thêm Logo Ảnh ---
        try {
            ImageIcon scIcon = new ImageIcon("d:/hoc/OOSE/KTX/images.png");
            Image scaled = scIcon.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
            JLabel lblImage = new JLabel(new ImageIcon(scaled));
            lblImage.setAlignmentX(Component.LEFT_ALIGNMENT);
            logoPanel.add(lblImage);
            logoPanel.add(Box.createVerticalStrut(12)); // Cách chữ một khoảng
        } catch (Exception e) {}

        JLabel logo = new JLabel("KTX Manager");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);
        logoPanel.add(logo);
        
        JLabel role = new JLabel(user.getVaiTro());
        role.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        role.setForeground(UITheme.SIDEBAR_TEXT);
        role.setAlignmentX(Component.LEFT_ALIGNMENT);
        logoPanel.add(Box.createVerticalStrut(8));
        logoPanel.add(role);
        
        brand.add(logoPanel);
        sidebar.add(brand);

        // Separator
        sidebar.add(createSidebarSep());

        // Nav items
        boolean isManager = "Manager".equalsIgnoreCase(user.getVaiTro());

        sidebar.add(navButton("Dashboard / Thống kê", CARD_DASHBOARD));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(navButton("Quản lý Sinh viên",     CARD_SINH_VIEN));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(navButton("Quản lý Phòng",         CARD_PHONG));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(navButton("Lập Hợp Đồng",          CARD_HOP_DONG));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(navButton("Tiền Phòng",            CARD_TIEN_PHONG));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(navButton("Hóa đơn điện nước",     CARD_HOA_DON));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(navButton("Trang Thiết Bị",        CARD_THIET_BI));

        if (isManager) {
            sidebar.add(createSidebarSep());
            sidebar.add(Box.createVerticalStrut(4));
            sidebar.add(navButton("Quản lý Nhân viên", CARD_NHAN_VIEN));
            sidebar.add(Box.createVerticalStrut(4));
            sidebar.add(navButton("Cấu hình Giá",      CARD_GIA));
            sidebar.add(Box.createVerticalStrut(4));
            sidebar.add(navButton("Nhật ký hệ thống",  CARD_AUDIT_LOG));
        }

        sidebar.add(Box.createVerticalGlue());
        sidebar.add(createSidebarSep());

        // Logout
        btnDangXuat.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnDangXuat.setForeground(new Color(252, 165, 165)); // red-300
        btnDangXuat.setBackground(UITheme.SIDEBAR_BG);
        btnDangXuat.setOpaque(true);
        btnDangXuat.setContentAreaFilled(true);
        btnDangXuat.setBorderPainted(false);
        btnDangXuat.setFocusPainted(false);
        btnDangXuat.setHorizontalAlignment(SwingConstants.LEFT);
        btnDangXuat.putClientProperty("FlatLaf.style", "margin: 14,24,14,24; arc: 8");
        btnDangXuat.setMaximumSize(new Dimension(Integer.MAX_VALUE, 64));
        btnDangXuat.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnDangXuat.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { 
                btnDangXuat.setBackground(new Color(180, 30, 30));
                btnDangXuat.setForeground(new Color(255, 200, 200));
            }
            @Override public void mouseExited (MouseEvent e) { 
                btnDangXuat.setBackground(UITheme.SIDEBAR_BG);
                btnDangXuat.setForeground(new Color(252, 165, 165));
            }
        });
        sidebar.add(btnDangXuat);
        sidebar.add(Box.createVerticalStrut(8));

        // ---- Content area ----
        contentPane.setBackground(UITheme.CONTENT_BG);

        add(sidebar,      BorderLayout.WEST);
        add(contentPane,  BorderLayout.CENTER);
    }

    private JButton navButton(String label, String card) {
        JButton b = new JButton(label);
        b.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        b.setForeground(UITheme.SIDEBAR_TEXT);
        b.setBackground(UITheme.SIDEBAR_BG);
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.putClientProperty("JButton.buttonType", "toolBarButton");
        b.putClientProperty("FlatLaf.style", "margin: 24,24,24,24; arc: 12");
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 84));
        b.setAlignmentX(Component.LEFT_ALIGNMENT);

        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                if (b != activeSidebar) {
                    b.setBackground(UITheme.SIDEBAR_HOVER);
                    b.setForeground(Color.WHITE);
                }
            }
            @Override public void mouseExited(MouseEvent e) {
                if (b != activeSidebar) {
                    b.setBackground(UITheme.SIDEBAR_BG);
                    b.setForeground(UITheme.SIDEBAR_TEXT);
                }
            }
        });
        b.addActionListener(e -> showCard(card, b));
        return b;
    }

    private Component createSidebarSep() {
        JPanel sep = new JPanel();
        sep.setOpaque(false);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setBackground(UITheme.SIDEBAR_HOVER);
        sep.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UITheme.SIDEBAR_HOVER));
        return sep;
    }

    private void configFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 750);
        setMinimumSize(new Dimension(1000, 650));
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Tự động phóng to toàn màn hình
        setLocationRelativeTo(null);
    }

    // ----------------------------------------------------------------
    // Public API
    // ----------------------------------------------------------------
    public void setPanel(JPanel panel, String cardName) {
        contentPane.add(panel, cardName);
    }

    public void showCard(String cardName) {
        cardLayout.show(contentPane, cardName);
    }

    private void showCard(String cardName, JButton source) {
        if (activeSidebar != null) {
            activeSidebar.setBackground(UITheme.SIDEBAR_BG);
            activeSidebar.setForeground(UITheme.SIDEBAR_TEXT);
            activeSidebar.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        }
        source.setBackground(UITheme.SIDEBAR_ACTIVE);
        source.setForeground(Color.WHITE);
        source.setFont(new Font("Segoe UI", Font.BOLD, 20)); // Even larger bold font
        activeSidebar = source;
        cardLayout.show(contentPane, cardName);
    }

    public void addDangXuatListener(ActionListener l) {
        btnDangXuat.addActionListener(l);
    }
}
