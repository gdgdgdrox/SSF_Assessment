package com.assessment.ssf.Model;

import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Article implements Serializable{
    private String id;
    private String title;
    private String body;
    private Integer publishedOn;
    private String url;
    private String imageUrl;
    private String tags;
    private String categories;
    public static List<Article> listOfArticles = new ArrayList<>();

    public static List<Article> createArticleList(String payload){
        StringReader strReader = new StringReader(payload);
        JsonReader jsonReader = Json.createReader(strReader);
        JsonObject jsonObj = jsonReader.readObject();
        JsonArray jsonArray = jsonObj.getJsonArray("Data").asJsonArray();
        for (int i = 0; i < jsonArray.size(); i++){
            Article article = new Article();
            article.setId(jsonArray.getJsonObject(i).getString("id"));
            article.setPublishedOn(jsonArray.getJsonObject(i).getInt("published_on"));
            article.setTitle(jsonArray.getJsonObject(i).getString("title"));
            article.setImageUrl(jsonArray.getJsonObject(i).getString("imageurl"));
            article.setUrl(jsonArray.getJsonObject(i).getString("url"));
            article.setBody(jsonArray.getJsonObject(i).getString("body"));
            article.setTags(jsonArray.getJsonObject(i).getString("tags"));
            article.setCategories(jsonArray.getJsonObject(i).getString("categories"));
            listOfArticles.add(article);
        }
        return listOfArticles;
    }

}

