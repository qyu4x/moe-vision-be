package com.syncrosa.livestreamingservice.controller;

import com.syncrosa.livestreamingservice.payload.request.CreateLiveStreamingRequest;
import com.syncrosa.livestreamingservice.payload.response.CreateLiveStreamingResponse;
import com.syncrosa.livestreamingservice.payload.response.LiveStreamingResponse;
import com.syncrosa.livestreamingservice.payload.response.WebResponse;
import com.syncrosa.livestreamingservice.service.IStreamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class StreamController {

    private final IStreamService iStreamService;

    @PostMapping(
            path = "/api/v1/lives",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    private ResponseEntity<WebResponse<CreateLiveStreamingResponse>> create(
            @RequestBody @Valid CreateLiveStreamingRequest request) {

        CreateLiveStreamingResponse createLiveStreamingResponse = iStreamService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(WebResponse.<CreateLiveStreamingResponse>builder()
                        .data(createLiveStreamingResponse)
                        .build());
    }

    @GetMapping(
            path = "/api/v1/lives/{streamId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    private ResponseEntity<WebResponse<LiveStreamingResponse>> get(@PathVariable("streamId") String streamId) {

        LiveStreamingResponse liveStreamingResponse = iStreamService.get(streamId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(WebResponse.<LiveStreamingResponse>builder()
                        .data(liveStreamingResponse)
                        .build());
    }

    @GetMapping(
            path = "/api/v1/lives",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    private ResponseEntity<WebResponse<List<LiveStreamingResponse>>> getAll() {

        List<LiveStreamingResponse> liveStreamingResponses = iStreamService.getAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(WebResponse.<List<LiveStreamingResponse>>builder()
                        .data(liveStreamingResponses)
                        .build());
    }

    @PatchMapping(
            path = "/api/v1/lives/{streamId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    private ResponseEntity<WebResponse<Boolean>> endStream(@PathVariable("streamId") String streamId) {

        iStreamService.endStream(streamId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(WebResponse.<Boolean>builder()
                        .data(true)
                        .build());
    }


    @PostMapping(
            path = "/api/v1/lives/callback",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    private ResponseEntity<WebResponse<Boolean>> endStreamRtmp(@RequestParam("token") String token) {

        iStreamService.endStreamRtmp(token);
        return ResponseEntity.status(HttpStatus.OK)
                .body(WebResponse.<Boolean>builder()
                        .data(true)
                        .build());
    }


    @PostMapping(
            path = "/api/v1/lives"
    )
    private ResponseEntity<Boolean> validateToken(@RequestParam("token") String token) {

        Boolean validateTokenResponse = iStreamService.validateToken(token);
        return ResponseEntity.status(HttpStatus.OK)
                .body(validateTokenResponse);

    }

}
