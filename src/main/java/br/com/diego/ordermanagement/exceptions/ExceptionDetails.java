package br.com.diego.ordermanagement.exceptions;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ExceptionDetails {

    private String title;
    private int status;
    private String details;
    private String message;
    private LocalDateTime date;
}
