package com.example.ec_user.entities.enums;

public enum UserType {

    SELLER(1),
    CLIENT(2),
    SUPPORT(3),
    ADMIN(4);

    private int code;

    private UserType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static UserType valueOf(int code) {
        for (UserType value : UserType.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid user type stats code, try again!");
    }
}
