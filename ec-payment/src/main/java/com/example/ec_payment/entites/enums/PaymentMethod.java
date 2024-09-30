package com.example.ec_payment.entites.enums;

public enum PaymentMethod {

    CREDITO(1),
    DEBITO(2),
    PIX(3),
    BOLETO(4);

    private int code;

    private PaymentMethod(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static PaymentMethod valueOf(int code) {
        for (PaymentMethod value : PaymentMethod.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid stats code, try again!");
    }
}
