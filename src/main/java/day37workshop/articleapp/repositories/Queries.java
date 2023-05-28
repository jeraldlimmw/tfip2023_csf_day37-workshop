package day37workshop.articleapp.repositories;

public class Queries {
    
    public static final String SQL_SAVE_PHOTO = """
            insert into photos(photo_id, photo, content_type) values (?, ?, ?)
            """;

    public static final String SQL_FIND_PHOTO_BY_ID = """
            select * from photos where photo_id = ?
            """;
}
