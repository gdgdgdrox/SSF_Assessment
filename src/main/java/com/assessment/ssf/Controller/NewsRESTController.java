package com.assessment.ssf.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.assessment.ssf.Model.Article;
import com.assessment.ssf.Service.NewsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
public class NewsRESTController {

    @Autowired
    private NewsService newsService;

    @GetMapping(path="/news/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> returnArticle(@PathVariable String id){
        Optional<Article> optArticle = newsService.findArticleById(id);
        ObjectMapper mapper = new ObjectMapper();
        if (optArticle.isPresent()){
            Article article = optArticle.get();
            String articleInStringFormat = "";
            try {
                articleInStringFormat = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(article);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            ResponseEntity<String> respEntity = 
                        ResponseEntity.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(articleInStringFormat);
            return respEntity;
        }

        JsonObject jsonErrorObject = Json.createObjectBuilder().add("error", "Cannot find news article " + id).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(jsonErrorObject.toString());
    
    }
    
}
