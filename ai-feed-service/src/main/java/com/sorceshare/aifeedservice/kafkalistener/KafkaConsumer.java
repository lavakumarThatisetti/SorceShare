package com.sorceshare.aifeedservice.kafkalistener;
import com.sorceshare.aifeedservice.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ArticleRepository articleRepository;

//    @KafkaListener(topics = "article_topic_test", groupId = "article_json",
//            containerFactory = "userKafkaListenerFactory")
//    public void consumeArticle(Article article) {
//        System.out.println("Consumed JSON Message: " + article);
//        System.out.println("ArticleSummary "+article.getArticleSummary());
//        System.out.println(article.getArticleLabels());
//        saveDataToDb(article);
//    }

}
