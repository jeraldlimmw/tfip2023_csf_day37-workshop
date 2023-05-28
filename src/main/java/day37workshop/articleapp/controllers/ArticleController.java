package day37workshop.articleapp.controllers;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import day37workshop.articleapp.models.Article;
import day37workshop.articleapp.services.ArticleService;

@Controller
@RequestMapping
public class ArticleController {
    
    @Autowired 
    private ArticleService svc;

    @PostMapping(path="/upload", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public ModelAndView uploadArticle(@RequestPart String title
            , @RequestPart String content, @RequestPart MultipartFile photo
            , Model model) {
        
        String photoId = svc.uploadPhoto(photo);
        System.out.println(">>>> photoId: " + photoId);
        
        ModelAndView mv = new ModelAndView();
        Article article = new Article(title, content, photoId);
        System.out.println(article);

        try {
            svc.uploadArticle(article);
        } catch (Exception ex) {
            mv.setStatus(HttpStatusCode.valueOf(500));
            mv.addObject("error", ex.getMessage());
            mv.setViewName("error");
        }
        
        mv.setStatus(HttpStatusCode.valueOf(200));
        mv.addObject("title", title);
        mv.addObject("content", content);
        mv.addObject("photo", "/article/image/%s".formatted(photoId));
        mv.setViewName("article");

        return mv;
    }

    @GetMapping(path="/article/{articleId}")
    @ResponseBody
    public ModelAndView getArticle(@PathVariable String articleId) {

        ModelAndView mv = new ModelAndView();

        Article a = svc.findArticle(articleId);

        if(Objects.isNull(a)) {
            mv.setStatus(HttpStatus.NOT_FOUND);
            mv.setViewName("error");
            return mv;
        }
            
        mv.setStatus(HttpStatus.OK);
        mv.addObject("title", a.title());
        mv.addObject("content", a.content());
        mv.addObject("photo", "/article/image/%s".formatted(a.photoId()));
        mv.setViewName("article");

        return mv;
    }
}
