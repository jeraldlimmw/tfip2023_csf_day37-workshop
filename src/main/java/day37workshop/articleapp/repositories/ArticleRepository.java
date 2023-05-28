package day37workshop.articleapp.repositories;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import day37workshop.articleapp.models.Article;

@Repository
public class ArticleRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    /*
     *  db.articles.insert({
     *      _id: articleId,
     *      title: "title",
     *      content: "content",
     *      photoId: "photoId"
     *  })
     */

    public void saveArticle(Article a) {
        Document doc = new Document();
        doc.put("title", a.title());
        doc.put("content", a.content());
        doc.put("photoId", a.photoId());

        mongoTemplate.insert(doc, "articles");
    }

    public Article findArticleById(String id) {
        Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id))
            .fields().exclude("_id");

		return mongoTemplate.findOne(query, Article.class, "articles");
	}
}
