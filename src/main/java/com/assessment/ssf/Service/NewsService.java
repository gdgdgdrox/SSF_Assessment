package com.assessment.ssf.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.assessment.ssf.Model.Article;
import com.assessment.ssf.Repository.NewsRepository;

@Service
public class NewsService {

    private final String CRYPTO_NEWS_URL = "https://min-api.cryptocompare.com/data/v2/news/?lang=EN";

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private NewsRepository repo;

    public List<Article> getArticles(){
        ResponseEntity<String> respEntity = restTemplate.getForEntity(CRYPTO_NEWS_URL, String.class);
        String payload = respEntity.getBody();
        Article.createArticleList(payload);
        return Article.listOfArticles;
    }

    public void saveArticles(List<Article> articlesToBeSaved){
        repo.save(articlesToBeSaved);
    }

    public Optional<Article> findArticleById (String id){
        return repo.findArticleById(id);
    }
    


    
}
