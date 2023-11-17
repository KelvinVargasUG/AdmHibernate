package com.kjvargas.admuserswithhibernet.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
public class ApiResponse {
    private HttpStatus status;
    private List<String> errors;

    public static ApiResponse fromMessage(HttpStatus status, String message) {
        return new ApiResponse(status, Collections.singletonList(message));
    }
}