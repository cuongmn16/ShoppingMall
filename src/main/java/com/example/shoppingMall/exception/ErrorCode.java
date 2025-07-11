package com.example.shoppingMall.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, " Incorrect password", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1009, "Email existed", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_FOUND(1010, "Permission not found", HttpStatus.BAD_REQUEST),
    ERROR_SQL(1011, "SQL error", HttpStatus.INTERNAL_SERVER_ERROR),
    SHOPCATEGORY_NOT_FOUND(1012, "Shop category not found", HttpStatus.NOT_FOUND),
    PRODUCT_NOT_FOUND(1013, "Product not found", HttpStatus.NOT_FOUND),
    PRODUCT_IMAGE_NOT_FOUND(1019, "Product image not found", HttpStatus.NOT_FOUND),
    SELLER_NOT_FOUND(1014, "Seller not found", HttpStatus.NOT_FOUND),
    INVALID_OR_MISSING_TOKEN(1015, "Invalid or missing token", HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED(1016, "Token expired", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN(1017, "Invalid token", HttpStatus.UNAUTHORIZED),
    ORDER_ITEM_NOT_FOUND(1018, "Order item not found", HttpStatus.NOT_FOUND),
    ORDER_NOT_FOUND(1020,"Order not found",HttpStatus.NOT_FOUND),
    VARIATION_NOT_FOUND(1021, "Variation not found", HttpStatus.NOT_FOUND),
    ORDER_CAN_NOT_FOUND(1022, "Order can not found", HttpStatus.NOT_FOUND)
    ;


    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }
}
