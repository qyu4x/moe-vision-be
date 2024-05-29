package com.syncrosa.livestreamingservice.service.impl;

import com.syncrosa.livestreamingservice.config.NginxConfig;
import com.syncrosa.livestreamingservice.entity.Stream;
import com.syncrosa.livestreamingservice.helper.TokenHelper;
import com.syncrosa.livestreamingservice.payload.request.CreateLiveStreamingRequest;
import com.syncrosa.livestreamingservice.payload.response.CreateLiveStreamingResponse;
import com.syncrosa.livestreamingservice.payload.response.LiveStreamingResponse;
import com.syncrosa.livestreamingservice.repository.StreamRepository;
import com.syncrosa.livestreamingservice.service.IStreamService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StreamServiceImpl implements IStreamService {


    private final NginxConfig nginxConfig;

    private final StreamRepository streamRepository;

    private CreateLiveStreamingResponse toCreateLiveStreamingResponse(Stream stream) {
        return CreateLiveStreamingResponse.builder()
                .id(stream.getId())
                .title(stream.getTitle())
                .fullName(stream.getFullName())
                .token(stream.getToken())
                .isTokenActive(stream.getIsTokenActive())
                .isStreamActive(stream.getIsStreamActive())
                .expiredAt(stream.getExpiredAt().toEpochMilli())
                .createdAt(Objects.isNull(stream.getCreatedAt()) ? 0L : stream.getCreatedAt().toEpochMilli())
                .updatedAt(Objects.isNull(stream.getUpdatedAt()) ? 0L : stream.getUpdatedAt().toEpochMilli())
                .build();
    }

    private LiveStreamingResponse toLiveStreamingResponse(Stream stream) {
        return LiveStreamingResponse.builder()
                .id(stream.getId())
                .title(stream.getTitle())
                .fullName(stream.getFullName())
                .token(stream.getToken())
                .hlsURL(String.format(nginxConfig.getHlsUrl(), stream.getToken()))
                .isStreamActive(stream.getIsStreamActive())
                .expiredAt(stream.getExpiredAt().toEpochMilli())
                .createdAt(Objects.isNull(stream.getCreatedAt()) ? 0L : stream.getCreatedAt().toEpochMilli())
                .updatedAt(Objects.isNull(stream.getUpdatedAt()) ? 0L : stream.getUpdatedAt().toEpochMilli())
                .build();
    }

    @Override
    @Transactional
    public CreateLiveStreamingResponse create(CreateLiveStreamingRequest createLiveStreamingRequest) {
        Stream stream = new Stream();
        stream.setId(UUID.randomUUID().toString());
        stream.setTitle(createLiveStreamingRequest.getTitle());
        stream.setFullName(createLiveStreamingRequest.getFullName());
        stream.setToken(TokenHelper.generateRandomToken(25));
        stream.setIsStreamActive(false);
        stream.setIsTokenActive(true);
        stream.setExpiredAt(Instant.now().plusSeconds(24 * 3600));

        streamRepository.save(stream);

        return this.toCreateLiveStreamingResponse(stream);
    }

    @Override
    public LiveStreamingResponse get(String id) {
        Stream stream = streamRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stream not found"));

        return this.toLiveStreamingResponse(stream);
    }

    @Override
    public List<LiveStreamingResponse> getAll() {
        return streamRepository.findAllByIsStreamActive(true)
                .stream().map(this::toLiveStreamingResponse).toList();
    }

    @Override
    @Transactional
    public void endStream(String id) {
        Stream stream = streamRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stream not found"));
        stream.setIsStreamActive(false);

        streamRepository.save(stream);
    }

    @Override
    @Transactional
    public Boolean endStreamRtmp(String token) {
        Stream stream = streamRepository.findFirstByToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stream not found"));
        stream.setIsStreamActive(false);

        streamRepository.save(stream);

        return true;
    }

    @Override
    @Transactional
    public Boolean validateToken(String token) {
        Stream stream = streamRepository.findFirstByToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Token is wrong"));

        if (Instant.now().toEpochMilli() > stream.getExpiredAt().toEpochMilli()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Token is wrong");
        }

        stream.setIsStreamActive(true);

        return true;
    }
}
