package com.syncrosa.livestreamingservice.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LiveStreamingResponse {

    private String id;

    private String title;

    private String fullName;

    private String token;

    private Boolean isStreamActive;

    private String hlsURL;

    private Long expiredAt;

    private Long createdAt;

    private Long updatedAt;

}
