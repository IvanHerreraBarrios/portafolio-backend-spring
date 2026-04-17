package com.example.portafolio_backend.domain.dto.exceptions.utils;

import lombok.Getter;

@Getter
public enum ErrorCatalog {
    CUSTOMER_NOT_FOUND("ERROR_CUSTOMER_01", "Customer not found"),
    CAKE_NOT_FOUND("ERROR_CAKE_01", "Cake not found"),
    BRAND_NOT_FOUND("ERROR_BRAND_01", "Brand not found"),
    ORDER_NOT_FOUND("ERROR_ORDER_01", "Order not found"),
    INVALID_DATA("ERROR_INVALID_DATA", "Invalid data"),
    GENERAL_ERROR("GENERAL_ERROR", "Internal server error")

    ;



    private final String code;
    private final String message;

    ErrorCatalog(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
