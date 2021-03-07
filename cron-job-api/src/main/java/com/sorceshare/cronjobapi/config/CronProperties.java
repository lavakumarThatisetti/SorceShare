package com.sorceshare.cronjobapi.config;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties("init-cronjob")
@Slf4j
public class CronProperties {
    private List<JobRecord> cronJobs;

    @Data
    public static class JobRecord{
        private String jobName;
        public String jobGroup;
        public String cronExpression;
        public String targetUrl;

    }
}
