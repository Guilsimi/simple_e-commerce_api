package com.example.ec_payment.entites.enums;

public enum PaymentStats {

    PENDENTE(1),
    APROVADO(2),
    RECUSADO(3);

    private int code;

    private PaymentStats(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static PaymentStats valueOf(int code) {
        for (PaymentStats value : PaymentStats.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid stats code, try again!");
    }
}
