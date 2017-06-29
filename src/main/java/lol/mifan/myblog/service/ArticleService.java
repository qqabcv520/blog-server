package lol.mifan.myblog.service;

import lol.mifan.myblog.po.Article;
import lol.mifan.myblog.service.impl.EntityServiceImpl;

import java.util.Collection;
import java.util.List;

/**
 * Created by 米饭 on 2017-06-29.
 */
public interface ArticleService extends EntityService<Article, Integer> {
    List<Article> getArticlesByTagId(int tagId, Integer limit, Integer offset);
    Object toJsonArray(Collection<Article> articles);
}
