package com.kjvargas.admuserswithhibernet.Entitys;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
public class ApiError {
    private HttpStatus status;
    private List<String> errors;

    public static ApiError fromMessage(HttpStatus status, String message) {
        return new ApiError(status, Collections.singletonList(message));
    }
}