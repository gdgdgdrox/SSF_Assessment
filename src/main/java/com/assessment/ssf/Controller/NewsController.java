package com.assessment.ssf.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.assessment.ssf.Model.Article;
import com.assessment.ssf.Service.NewsService;

@Controller
public class NewsController {

    @Autowired
    private NewsService service;

    //Prepping the list of article
    @PostConstruct
    public void getArticles(){
        service.getArticles();
    }

    @GetMapping
    public String getArticlesPage(Model model){
        model.addAttribute("articleList", Article.listOfArticles);
        return "articlesPage";
    }

    @PostMapping("/articles")
    public String returnArticlesPage(@RequestBody String reqBody, Model model){
        List<Article> articleList = Article.listOfArticles;
        List<Article> articlesToBeSaved = new ArrayList<>();
        List<String> idOFArticlesToSave = new ArrayList<>();

        //This is to extract the ID from the Request Body e.g. save=27933788&save=27933787
        String[] terms = reqBody.split("&");
        for (String string : terms){
            //
            String id = string.substring(5);
            idOFArticlesToSave.add(id);
        }
        
        //This is to formulate a list of articles that the user wants to save
        for (String id : idOFArticlesToSave){
            for (int i = 0; i < articleList.size(); i++){
                if (articleList.get(i).getId().equals(id)){
                    articlesToBeSaved.add(articleList.get(i));
                }
        }
    }
        service.saveArticles(articlesToBeSaved);
        model.addAttribute("articleList", articleList);
        return "articlesPage";
    }
}
