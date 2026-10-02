package com.app.backend.dto.response;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude
public class ApiResponse<T> {
    private boolean success;
    private String msg;
    private T data;

    @Builder.Default
    private LocalDateTime timestamp=LocalDateTime.now();

    public static <T> ApiResponse<T> success(String msg,T data){
        return ApiResponse.<T>builder()
                .success(true)
                .msg(msg)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> success(String msg){
        return success(msg,null);
    }

    public static <T> ApiResponse<T> error(String msg){
        return ApiResponse.<T>builder()
                .success(false)
                .msg(msg)
                .timestamp(LocalDateTime.now())
                .build();
    }


}
