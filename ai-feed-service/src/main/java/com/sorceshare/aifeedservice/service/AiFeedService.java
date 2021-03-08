package com.sorceshare.aifeedservice.service;

import com.sorceshare.aifeedservice.model.Article;

public interface AiFeedService {
    boolean insertKafkaDataToDb(Article article);
}
