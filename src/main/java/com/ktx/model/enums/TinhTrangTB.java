package com.ktx.model.enums;

public enum TinhTrangTB {
    TOT("Tốt"),
    CAN_BAO_TRI("Cần bảo trì"),
    HONG("Hỏng");

    private final String description;

    TinhTrangTB(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
