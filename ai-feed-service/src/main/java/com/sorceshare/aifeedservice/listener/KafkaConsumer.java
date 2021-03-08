package com.sorceshare.aifeedservice.listener;

import com.sorceshare.aifeedservice.model.KafkaArticle;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    @KafkaListener(topics = "TestTopic", groupId = "group_id")
    public void consume(String message) {
        System.out.println("Consumed message: " + message);
    }

    @KafkaListener(topics = "article_topic_test", groupId = "group_json",
            containerFactory = "userKafkaListenerFactory")
    public void consumeJson(KafkaArticle article) {
        System.out.println("Consumed JSON Message: " + article);
        System.out.println(article.getArticleSummary());
        System.out.println(article.getArticleLabels());
    }
}
