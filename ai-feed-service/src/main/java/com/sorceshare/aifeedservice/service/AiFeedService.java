package com.sorceshare.aifeedservice.service;

import com.sorceshare.aifeedservice.model.Article;
import reactor.core.publisher.Flux;

public interface AiFeedService {
    boolean insertKafkaDataToDb(Article article);

    Flux<Article> getLatest10DaysRecords();
}
