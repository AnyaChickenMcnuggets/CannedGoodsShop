package com.example.finalfullstack.enums;

public enum Status {
    Оформлен("Оформлен"), Оплачен("Оплачен"), Доставка("Доставка"), Получен("Получен"),Отменен("Отменен");
    private final String displayValue;

    Status(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
