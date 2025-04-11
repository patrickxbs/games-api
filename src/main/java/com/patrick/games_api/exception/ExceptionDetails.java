package com.patrick.games_api.exception;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class ExceptionDetails {

    private String title;
    private String message;
    private String status;
    private int statusCode;
    private LocalDateTime timestamp;

}
