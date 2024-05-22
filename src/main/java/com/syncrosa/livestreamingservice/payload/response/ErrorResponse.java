package com.syncrosa.livestreamingservice.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse <T>{

    private String apiPath;

    private Integer errorCode;

    private T errorMessage;

    private LocalDateTime errorTime;

}
