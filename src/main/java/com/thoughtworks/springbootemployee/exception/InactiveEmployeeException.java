package com.thoughtworks.springbootemployee.exception;

public class InactiveEmployeeException extends RuntimeException{
    public InactiveEmployeeException() {
        super("Employee is inactive");
    }
}
