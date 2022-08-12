package com.assessment.ssf.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.assessment.ssf.Model.Article;
import com.assessment.ssf.Repository.NewsRepository;

@Service
public class NewsService {

    private static final String CRYPTO_NEWS_URL = "https://min-api.cryptocompare.com/data/v2/news/?lang=EN";
    private static final String CRYPTO_API_KEY = System.getenv("CRYPTO_API_KEY");

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private NewsRepository repo;


    public List<Article> getArticles(){
        RequestEntity<Void> reqEntity = RequestEntity.get(CRYPTO_NEWS_URL)
                        .header("authorization", "Apikey " + CRYPTO_API_KEY).build();
        ResponseEntity<String> respEntity = restTemplate.exchange(reqEntity, String.class);
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
