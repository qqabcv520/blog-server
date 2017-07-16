package lol.mifan.myblog.service;

import lol.mifan.myblog.po.Article;
import lol.mifan.myblog.service.impl.EntityServiceImpl;

import java.util.Collection;
import java.util.List;

/**
 * Created by 米饭 on 2017-06-29.
 */
public interface ArticleService extends EntityService<Article, Integer> {
    /**
     * 根据tag主键获取文章集合
     * @param tagId tag主键
     * @param page
     * @param size
     * @return 文章集合
     */
    List<Article> getArticlesByTagId(int tagId, Integer page, Integer size);


    Article get(int articleId);


    List<Article> getList(Integer page, Integer size);
}
