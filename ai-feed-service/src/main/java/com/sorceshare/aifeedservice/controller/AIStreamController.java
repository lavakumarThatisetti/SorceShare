//package com.sorceshare.aifeedservice.controller;
//
//import com.sorceshare.aifeedservice.model.Article;
//import com.sorceshare.aifeedservice.repository.ArticleRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import reactor.core.publisher.Flux;
//
//@RestController
//@RequestMapping("/api/aiStream")
//public class AIStreamController {
//
//
//    private ArticleRepository articleRepository;
//
//    @Autowired
//    public AIStreamController(ArticleRepository articleRepository){
//        this.articleRepository = articleRepository;
//    }
//
//
//    @GetMapping(value = "/getArticles",consumes = "application/stream+json")
//    public Flux<Article> streamArticles(){
//            return articleRepository.findAll();
//    }
//}
