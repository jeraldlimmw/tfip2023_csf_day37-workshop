package day37workshop.articleapp.services;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import day37workshop.articleapp.models.Article;
import day37workshop.articleapp.models.Photo;
import day37workshop.articleapp.repositories.ArticleRepository;
import day37workshop.articleapp.repositories.PhotoRepository;

@Service
public class ArticleService {
    
    @Autowired
    private ArticleRepository articleRepo;

    @Autowired
    private PhotoRepository photoRepo;

    public String uploadPhoto(MultipartFile p) {
        String photoId = UUID.randomUUID().toString().substring(0, 8);

        boolean result;
        try {
            result = photoRepo.savePhoto(photoId, p.getBytes(), p.getContentType());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        if (!result) 
            return null;
        return photoId;
    }

    public void uploadArticle(Article a) {
        articleRepo.saveArticle(a);
    }

    public Article findArticle(String articleId) {
        return articleRepo.findArticleById(articleId);
    }

    public Optional<Photo> findPhoto(String photoId) {
        return photoRepo.findPhotoById(photoId);
    }
}
