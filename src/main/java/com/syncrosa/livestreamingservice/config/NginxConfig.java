package com.syncrosa.livestreamingservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "nginx")
public class NginxConfig {
    private String hlsUrl;

}
