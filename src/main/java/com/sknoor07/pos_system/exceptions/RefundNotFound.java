package com.sknoor07.pos_system.exceptions;

public class RefundNotFound extends RuntimeException {
    public RefundNotFound(String message) {
        super(message);
    }
}
