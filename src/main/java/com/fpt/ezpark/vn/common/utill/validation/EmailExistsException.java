package com.fpt.ezpark.vn.common.utill.validation;

public class EmailExistsException extends Throwable{

    public EmailExistsException(final String message) {
        super(message);
    }
}
