package com.syncrosa.livestreamingservice.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateLiveStreamingRequest {

    @NotBlank
    @Size(min = 2, max = 50)
    private String fullName;

    @NotBlank
    @Size(min = 2, max = 255)
    private String title;

}
