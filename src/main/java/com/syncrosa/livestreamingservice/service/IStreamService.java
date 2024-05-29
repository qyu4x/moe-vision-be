package com.syncrosa.livestreamingservice.service;

import com.syncrosa.livestreamingservice.payload.request.CreateLiveStreamingRequest;
import com.syncrosa.livestreamingservice.payload.response.CreateLiveStreamingResponse;
import com.syncrosa.livestreamingservice.payload.response.LiveStreamingResponse;

import java.util.List;

public interface IStreamService {

    CreateLiveStreamingResponse create(CreateLiveStreamingRequest createLiveStreamingRequest);

    LiveStreamingResponse get(String id);

    List<LiveStreamingResponse> getAll();

    void endStream(String id);

    Boolean endStreamRtmp(String token);

    Boolean validateToken(String token);

}
