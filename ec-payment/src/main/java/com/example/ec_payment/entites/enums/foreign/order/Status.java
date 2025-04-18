package com.example.ec_payment.entites.enums.foreign.order;

public enum Status {

    PENDENTE(1),
    ENVIADO(2),
    CONCLUÍDO(3),
    ERROR_CODE(4);

    private int code;

    private Status(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static Status valueOf(int code) {
        for (Status value : Status.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid stats code, try again!");
    }
}
