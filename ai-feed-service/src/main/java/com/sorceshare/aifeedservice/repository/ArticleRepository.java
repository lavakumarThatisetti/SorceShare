package com.sorceshare.aifeedservice.repository;

import com.sorceshare.aifeedservice.model.Article;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends ReactiveMongoRepository<Article,String> {

}
