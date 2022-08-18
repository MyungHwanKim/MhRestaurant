package com.devmhk.restaurant.customer.exception;

public class CustomerNotEmailAuthException extends RuntimeException {
    public CustomerNotEmailAuthException(String error) {
        super(error);
    }
}
