package lol.mifan.myblog.dao;

import lol.mifan.myblog.po.Article;
import lol.mifan.myblog.po.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by 米饭 on 2017-06-29.
 */
public interface ArticleDao extends JpaRepository<Article, Integer> {

    @Query("SELECT a FROM Article AS a INNER JOIN a.tags AS t WHERE t.id = ?1 AND a.deleted = false")
    List<Article> findAllByTagId(Integer tagId, Pageable pageable);

    Article findByIdAndDeletedFalse(Integer tagId);

    List<Article> findAllByDeletedFalse(Pageable pageable);
}
