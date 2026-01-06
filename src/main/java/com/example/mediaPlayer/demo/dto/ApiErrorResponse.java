package com.example.mediaPlayer.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorResponse {

    private int status;
    private String message;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    public ApiErrorResponse(boolean b, String message, String requestURI, LocalDateTime now) {
    }
}
