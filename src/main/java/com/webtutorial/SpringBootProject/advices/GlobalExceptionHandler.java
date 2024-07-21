package com.webtutorial.SpringBootProject.advices;

import com.webtutorial.SpringBootProject.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFound(ResourceNotFoundException e){

       ApiError apiError = ApiError.builder()
               .status(HttpStatus.NOT_FOUND)
               .message(e.getMessage())
               .build();
       return buildErrorResponseEntity(apiError);
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleInternalServerError(Exception e){

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(e.getMessage())
                .build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationError(MethodArgumentNotValidException e){

      List<String> errors =  e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(ex->ex.getDefaultMessage())
                .collect(Collectors.toList());

    ApiError apiError = ApiError.builder()
            .status(HttpStatus.BAD_REQUEST)
            .message("input validation failed")
            .subErrors(errors)
            .build();

    return buildErrorResponseEntity(apiError);

    }
    private ResponseEntity<ApiResponse<?>> buildErrorResponseEntity(ApiError apiError) {

        return new ResponseEntity<>(new ApiResponse(apiError), apiError.getStatus());
    }
}

