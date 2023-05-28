package day37workshop.articleapp.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import day37workshop.articleapp.models.Photo;
import static day37workshop.articleapp.repositories.Queries.*;

@Repository
public class PhotoRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean savePhoto(String photoId, byte[] photo, String contentType) { 
        return jdbcTemplate.update(SQL_SAVE_PHOTO, photoId, photo, contentType) > 0;
    }

    public Optional<Photo> findPhotoById(String photoId) {
        Optional<Photo> opt = jdbcTemplate.query(SQL_FIND_PHOTO_BY_ID
            , rs -> {
                if (!rs.next())
                    return Optional.empty();
                Photo p = new Photo(photoId, rs.getBytes("photo"), rs.getString("content_type"));
                    return Optional.of(p);
                },
            photoId
        );
        return opt;
    }
}
