package com.webtutorial.SpringBootProject.advices;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data

public class ApiResponse <T>{

    private T data;
    private ApiError apiError;


    @JsonFormat(pattern = "hh-mm-ss dd-MM-yyyy")
    private LocalDateTime timeStamp;

    public ApiResponse() {

        this.timeStamp = LocalDateTime.now();
    }
    public ApiResponse(T data) {
        this();
        this.data = data;
    }



    public ApiResponse(ApiError apiError) {
        this();
        this.apiError = apiError;
    }
}
