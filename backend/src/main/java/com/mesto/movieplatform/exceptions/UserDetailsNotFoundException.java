package com.mesto.movieplatform.exceptions;

import java.util.function.Supplier;

public class UserDetailsNotFoundException extends RuntimeException {
    public UserDetailsNotFoundException(String s) {
        super(s);
    }
}
