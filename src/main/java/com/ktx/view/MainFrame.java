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
        JPanel sidebar = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                g2.setColor(UITheme.SIDEBAR_BG);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(260, 0));
        sidebar.setOpaque(false);

        // Brand / Logo
        JPanel brand = new JPanel();
        brand.setOpaque(false);
        brand.setLayout(new BoxLayout(brand, BoxLayout.Y_AXIS));
        brand.setBorder(BorderFactory.createEmptyBorder(32, 24, 24, 24));
        JLabel logo = new JLabel("KTX Manager");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);
        brand.add(logo);
        JLabel role = new JLabel(user.getVaiTro());
        role.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        role.setForeground(UITheme.SIDEBAR_TEXT);
        role.setAlignmentX(Component.LEFT_ALIGNMENT);
        brand.add(Box.createVerticalStrut(6));
        brand.add(role);
        sidebar.add(brand);

        // Separator
        sidebar.add(createSidebarSep());

        // Nav items
        boolean isManager = "Manager".equalsIgnoreCase(user.getVaiTro());
        if (isManager) {
            sidebar.add(navButton("Dashboard / Thống kê", CARD_DASHBOARD));
        }
        sidebar.add(navButton("Quản lý Sinh viên", CARD_SINH_VIEN));
        sidebar.add(navButton("Quản lý Phòng",     CARD_PHONG));
        sidebar.add(navButton("Lập Hợp Đồng",      CARD_HOP_DONG));

        sidebar.add(Box.createVerticalGlue());
        sidebar.add(createSidebarSep());

        // Logout
        btnDangXuat.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnDangXuat.setForeground(new Color(252, 165, 165)); // red-300
        btnDangXuat.setBackground(UITheme.SIDEBAR_BG);
        btnDangXuat.setOpaque(true);
        btnDangXuat.setContentAreaFilled(true);
        btnDangXuat.setBorderPainted(false);
        btnDangXuat.setFocusPainted(false);
        btnDangXuat.setHorizontalAlignment(SwingConstants.LEFT);
        btnDangXuat.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(4, 0, 4, 0),
                BorderFactory.createEmptyBorder(12, 24, 12, 24)
        ));
        btnDangXuat.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
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
        b.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        b.setForeground(UITheme.SIDEBAR_TEXT);
        b.setBackground(UITheme.SIDEBAR_BG);
        b.setOpaque(true);
        b.setContentAreaFilled(true);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(4, 0, 4, 0),
                BorderFactory.createEmptyBorder(12, 24, 12, 24)
        ));
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        b.setAlignmentX(Component.LEFT_ALIGNMENT);

        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                if (b != activeSidebar) {
                    b.setBackground(new Color(50, 60, 80));
                    b.setForeground(new Color(220, 230, 240));
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
        setSize(1100, 700);
        setMinimumSize(new Dimension(900, 600));
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
            activeSidebar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }
        source.setBackground(new Color(99, 102, 241));
        source.setForeground(Color.WHITE);
        source.setFont(new Font("Segoe UI", Font.BOLD, 14));
        activeSidebar = source;
        cardLayout.show(contentPane, cardName);
    }

    public void addDangXuatListener(ActionListener l) {
        btnDangXuat.addActionListener(l);
    }
}
