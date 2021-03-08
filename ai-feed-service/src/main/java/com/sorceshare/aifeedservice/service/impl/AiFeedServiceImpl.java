package com.sorceshare.aifeedservice.service.impl;
import com.sorceshare.aifeedservice.model.Article;
import com.sorceshare.aifeedservice.repository.ArticleRepository;
import com.sorceshare.aifeedservice.service.AiFeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiFeedServiceImpl implements AiFeedService {

    private final ArticleRepository articleRepository;

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

}
