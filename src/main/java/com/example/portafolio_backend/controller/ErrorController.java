package com.example.portafolio_backend.controller;

import com.example.portafolio_backend.domain.dto.exceptions.BrandNotFoundEsception;
import com.example.portafolio_backend.domain.dto.exceptions.CakeNotFoundException;
import com.example.portafolio_backend.domain.dto.exceptions.CustomerNotFoundException;
import com.example.portafolio_backend.domain.dto.exceptions.OrderNotFoundException;
import com.example.portafolio_backend.domain.dto.exceptions.utils.ErrorResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Collectors;

import static com.example.portafolio_backend.domain.dto.exceptions.utils.ErrorCatalog.*;

@RestControllerAdvice
public class ErrorController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CustomerNotFoundException.class)
    public ErrorResponse handlerCustomerNotFoundException(){
        return ErrorResponse.builder()
                .code(CUSTOMER_NOT_FOUND.getCode())
                .mesagge(CUSTOMER_NOT_FOUND.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CakeNotFoundException.class)
    public ErrorResponse handlerCakeNotFoundException(){
        return ErrorResponse.builder()
                .code(CAKE_NOT_FOUND.getCode())
                .mesagge(CAKE_NOT_FOUND.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BrandNotFoundEsception.class)
    public ErrorResponse handlerBrandNotFoundException(){
        return ErrorResponse.builder()
                .code(BRAND_NOT_FOUND.getCode())
                .mesagge(BRAND_NOT_FOUND.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(OrderNotFoundException.class)
    public ErrorResponse handlerOrderNotFoundException(){
        return ErrorResponse.builder()
                .code(ORDER_NOT_FOUND.getCode())
                .mesagge(ORDER_NOT_FOUND.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
    }




    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMehodArgumentNotValidException(MethodArgumentNotValidException exception){
        BindingResult result = exception.getBindingResult();

        return ErrorResponse.builder()
                .code(INVALID_DATA.getCode())
                .mesagge(INVALID_DATA.getMessage())
                .details(result.getFieldErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.toList()))
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleGenericError(Exception exception) {
        return ErrorResponse.builder()
                .code(GENERAL_ERROR.getCode())
                .mesagge(GENERAL_ERROR.getMessage())
                .details(Collections.singletonList(exception.getMessage()))
                .timeStamp(LocalDateTime.now())
                .build();
    }
}
