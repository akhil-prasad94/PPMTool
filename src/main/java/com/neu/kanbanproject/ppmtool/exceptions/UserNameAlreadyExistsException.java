package com.neu.kanbanproject.ppmtool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import sun.security.provider.certpath.OCSPResponse;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNameAlreadyExistsException extends RuntimeException{


    public UserNameAlreadyExistsException(String message) {
        super(message);

    }
}
