package com.assessment.ssf.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.assessment.ssf.Model.Article;

@Repository
public class NewsRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void save(List<Article> articlesToBeSaved){
        for (Article article : articlesToBeSaved){
            redisTemplate.opsForValue().set(article.getId(), article);
        }
    }

    public Optional<Article> findArticleById(String id){
        Article article = (Article)redisTemplate.opsForValue().get(id);
        if (null!=article){
            return Optional.of(article);
        }
        return Optional.empty();
    }
}
