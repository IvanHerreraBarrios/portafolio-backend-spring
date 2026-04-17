package com.example.portafolio_backend.domain.dto.exceptions.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class ErrorResponse {
    private String code;
    private String mesagge;
    private List<String> details;
    private LocalDateTime timeStamp;
}
