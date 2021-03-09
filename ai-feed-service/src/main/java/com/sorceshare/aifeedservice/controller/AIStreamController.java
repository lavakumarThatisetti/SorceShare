package com.sorceshare.aifeedservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sorceshare.aifeedservice.kafkalistener.KafkaService;
import com.sorceshare.aifeedservice.model.Article;
import com.sorceshare.aifeedservice.repository.ArticleRepository;
import com.sorceshare.aifeedservice.service.AiFeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/aiStream")
public class AIStreamController {


    private final ArticleRepository articleRepository;


    private final KafkaService kafkaService;

    private final AiFeedService aiFeedService;


    @GetMapping(value = "/initialLoadArticles",produces = "application/stream+json")
    public Flux<Article> getArticles(){

        return aiFeedService.getLatest10DaysRecords();
    }

    @GetMapping(value = "/getStreamArticles",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<Article>> streamArticles(){

        return kafkaService.getEventPublisher()
                .doOnNext(stringServerSentEvent -> log.info(stringServerSentEvent.data()))
                .map(stringServerSentEvent -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    Article article;
                    try {
                        article = objectMapper.readValue(Objects.requireNonNull(stringServerSentEvent.data()), Article.class);
                        aiFeedService.insertKafkaDataToDb(article);
                    } catch (Exception ex) {
                        return null;
                    }
                    return ServerSentEvent.builder(article).build();
                })
                .filter(Objects::nonNull)
                .checkpoint("Messages are done Consumed");
    }
}
