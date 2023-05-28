package day37workshop.articleapp.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import day37workshop.articleapp.models.Photo;
import day37workshop.articleapp.services.ArticleService;

@Controller
@RequestMapping
public class PhotoController {
    
    @Autowired
    private ArticleService svc;

    @GetMapping(path="/article/image/{photoId}")
    @ResponseBody
    public ResponseEntity<byte[]> getPhotos(@PathVariable String photoId) {

        Optional<Photo> op = svc.findPhoto(photoId);

        if (op.isEmpty())
            return ResponseEntity.notFound().build();

        Photo p = op.get();
        return ResponseEntity.status(200)
            .header("Content-Type", p.contentType())
            .body(p.photo());
    }
}
