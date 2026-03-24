package com.ktx.view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Bộ công cụ giao diện dùng chung toàn ứng dụng.
 * Định nghĩa bảng màu, font chữ và helper tạo component đã được style sẵn.
 */
public final class UITheme {

    private UITheme() {}

    // ================================================================
    // Colour palette - Modern enhanced theme
    // ================================================================
    public static final Color SIDEBAR_BG      = new Color(15, 23, 42);   // slate-900
    public static final Color SIDEBAR_HOVER   = new Color(30, 41, 59);   // slate-800
    public static final Color SIDEBAR_ACTIVE  = new Color(99, 102, 241); // indigo-500
    public static final Color SIDEBAR_TEXT    = new Color(203, 213, 225); // slate-300 (lighter for better contrast)
    public static final Color SIDEBAR_TEXT_ACTIVE = Color.WHITE;

    public static final Color CONTENT_BG  = new Color(248, 250, 252);   // slate-50 (lighter background)
    public static final Color CARD_BG     = Color.WHITE;

    public static final Color BLUE    = new Color(59, 130, 246);
    public static final Color INDIGO  = new Color(99, 102, 241);
    public static final Color GREEN   = new Color(34, 197, 94);
    public static final Color AMBER   = new Color(245, 158, 11);
    public static final Color RED     = new Color(239, 68, 68);
    public static final Color SLATE   = new Color(100, 116, 139);

    public static final Color TEXT_PRIMARY   = new Color(15, 23, 42);
    public static final Color TEXT_SECONDARY = new Color(100, 116, 139);
    public static final Color BORDER_COLOR   = new Color(226, 232, 240);

    public static final Color TABLE_STRIPE   = new Color(248, 250, 252);
    public static final Color TABLE_SELECT   = new Color(224, 231, 255);
    public static final Color TABLE_HEADER_BG= new Color(30, 41, 59);

    // ================================================================
    // Fonts - Enhanced typography
    // ================================================================
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font FONT_H2    = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FONT_BODY  = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_BOLD  = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_MONO  = new Font("Consolas", Font.PLAIN, 13);

    // ================================================================
    // Border helpers - Enhanced with rounded corners
    // ================================================================
    public static Border cardBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    public static Border roundedBorder(int radius) {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(radius, radius, radius, radius));
    }

    public static Border emptyBorder(int v, int h) {
        return BorderFactory.createEmptyBorder(v, h, v, h);
    }

    // ================================================================
    // Component helpers
    // ================================================================
    /** Nút màu phẳng với hover effect và thiết kế hiện đại. */
    public static JButton button(String text, Color bg) {
        JButton b = new JButton(text);
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setFont(FONT_BOLD);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        b.setOpaque(true);
        b.addMouseListener(new MouseAdapter() {
            final Color base = bg;
            @Override public void mouseEntered(MouseEvent e) { 
                b.setBackground(base.brighter()); 
            }
            @Override public void mouseExited (MouseEvent e) { 
                b.setBackground(base); 
            }
        });
        return b;
    }

    /** Label tiêu đề trang. */
    public static JLabel pageTitle(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_TITLE);
        l.setForeground(TEXT_PRIMARY);
        return l;
    }

    /** TextField đã style với thiết kế hiện đại. */
    public static JTextField textField(int cols) {
        JTextField tf = new JTextField(cols);
        tf.setFont(FONT_BODY);
        tf.setForeground(TEXT_PRIMARY);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        return tf;
    }

    /** JScrollPane sạch, không viền. */
    public static JScrollPane cleanScrollPane(JTable table) {
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        sp.getViewport().setBackground(CARD_BG);
        return sp;
    }

    /** Áp dụng style hiện đại cho JTable. */
    public static void styleTable(JTable table) {
        table.setFont(FONT_BODY);
        table.setRowHeight(36);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.setGridColor(BORDER_COLOR);
        table.setBackground(CARD_BG);
        table.setForeground(TEXT_PRIMARY);
        table.setSelectionBackground(TABLE_SELECT);
        table.setSelectionForeground(TEXT_PRIMARY);
        table.setFillsViewportHeight(true);
        table.setIntercellSpacing(new Dimension(0, 1));

        // Alternating rows
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object v, boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                if (!sel) setBackground(row % 2 == 0 ? CARD_BG : TABLE_STRIPE);
                setForeground(TEXT_PRIMARY);
                setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
                return this;
            }
        });

        // Header
        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_BOLD);
        header.setBackground(TABLE_HEADER_BG);
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);
        header.setPreferredSize(new Dimension(0, 42));
        ((DefaultTableCellRenderer) header.getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.LEFT);
    }

    /** Panel card trắng có shadow nhẹ và thiết kế hiện đại. */
    public static JPanel card() {
        JPanel p = new JPanel();
        p.setBackground(CARD_BG);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)));
        return p;
    }

    /** Separator ngang mỏng. */
    public static JSeparator separator() {
        JSeparator s = new JSeparator();
        s.setForeground(BORDER_COLOR);
        s.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        return s;
    }
}
