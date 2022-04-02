package com.drone.dispatch.exceptions;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FieldError {

    private String field;
    private String errorCode;

}