package com.sorceshare.aifeedservice.service.impl;
import com.sorceshare.aifeedservice.model.Article;
import com.sorceshare.aifeedservice.repository.ArticleRepository;
import com.sorceshare.aifeedservice.service.AiFeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiFeedServiceImpl implements AiFeedService {

    private final ArticleRepository articleRepository;
    private static Flux<Article> articlesCache;
    private Flux<Article> articleFlux;

    @PostConstruct
    public void init(){
//        articleFlux = articleRepository.findAll(Sort.by(Sort.Direction.DESC, "articleDate"));
//        articlesCache = articleFlux.cache();
    }

    @Override
    public boolean insertKafkaDataToDb(Article article){
        try {
            articleRepository.save(article).subscribe();
        }catch (Exception e){
            log.info("Error in saving DB");
            return false;
        }
        return true;
    }

    @Override
    public Flux<Article> getLatest10DaysRecords() {
        articleFlux = articleRepository.findAll(Sort.by(Sort.Direction.DESC, "articleDate"));
        return articleFlux.limitRequest(150);
    }

}
