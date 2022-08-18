package com.devmhk.restaurant.customer.exception;

public class CustomerSignInException extends RuntimeException {
    public CustomerSignInException(String error) {
        super(error);
    }
}
