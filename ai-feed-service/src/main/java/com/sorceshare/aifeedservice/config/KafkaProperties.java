package com.sorceshare.aifeedservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "kafka")
@Data
public class KafkaProperties {
    private List<String> brokers;
    private String topic;
    private String groupId;
}
