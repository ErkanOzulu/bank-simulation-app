package com.bank.exception;

public class AccountOwnershipException extends RuntimeException {
    public AccountOwnershipException(String message) {
        System.out.println(message);
    }
}
