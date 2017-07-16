package lol.mifan.myblog.service.impl;

import lol.mifan.myblog.controller.UserController;
import lol.mifan.myblog.dao.ArticleDao;
import lol.mifan.myblog.po.Article;
import lol.mifan.myblog.po.Tag;
import lol.mifan.myblog.po.Users;
import lol.mifan.myblog.service.ArticleService;
import lol.mifan.myblog.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by 米饭 on 2017-06-29.
 */
@Service
public class ArticleServiceImpl extends EntityServiceImpl<Article, Integer> implements ArticleService {


    @Resource
    private ArticleDao articleDao;


    @Resource
    private TagService tagService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);



    @Override
    public Article get(int articleId) {
        return articleDao.findByIdAndDeletedFalse(articleId);
    }

    @Override
    public List<Article> getList(Integer page, Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"sort");
        sort.and(new Sort(Sort.Direction.DESC,"createTime"));
        size = size==null ? Integer.MAX_VALUE : size;//默认值无穷大
        page = page==null ? 0 : page;//默认值0
        Pageable pageable = new PageRequest(page, size, sort);
        return articleDao.findAllByDeletedFalse(pageable);
    }

    @Override
    public List<Article> getArticlesByTagId(int tagId, Integer page, Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"sort");
        sort.and(new Sort(Sort.Direction.DESC,"createTime"));
        size = size==null ? Integer.MAX_VALUE : size;//默认值无穷大
        page = page==null ? 0 : page;//默认值0
        Pageable pageable = new PageRequest(page, size, sort);
        return articleDao.findAllByTagId(tagId, pageable);
    }

    @Override
    public Object toJsonObj(Article article) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", article.getId());
        hashMap.put("clickCnt", article.getClickCnt());
        hashMap.put("createTime", article.getCreateTime());
        hashMap.put("lastEditTime", article.getLastEditTime());
        hashMap.put("outline", article.getOutline());
        hashMap.put("sort", article.getSort());
        hashMap.put("title", article.getTitle());
        hashMap.put("titleImg", article.getTitleImg());
        hashMap.put("reprintedFrom", article.getReprintedFrom());
        hashMap.put("content", article.getContent());

        hashMap.put("tags", tagService.toJsonArray(article.getTags()));

        Users user = article.getUsers();
        if(user != null) {
            hashMap.put("user", user.getUsername());
        }
        return hashMap;
    }

    @Override
    public Object toJsonArray(Iterable<Article> articles) {

        List<Object> list = new ArrayList<>();
        for(Article article : articles) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("id", article.getId());
            hashMap.put("clickCnt", article.getClickCnt());
            hashMap.put("createTime", article.getCreateTime());
            hashMap.put("lastEditTime", article.getLastEditTime());
            hashMap.put("outline", article.getOutline());
            hashMap.put("sort", article.getSort());
            hashMap.put("title", article.getTitle());
            hashMap.put("titleImg", article.getTitleImg());
            hashMap.put("reprintedFrom", article.getReprintedFrom());

            hashMap.put("tags", tagService.toJsonArray(article.getTags()));

            Users user = article.getUsers();
            if(user != null) {
                hashMap.put("user", user.getUsername());
            }
            list.add(hashMap);
        }

        return list;
    }


    @Override
    public JpaRepository<Article, Integer> getEntityDao() {
        return articleDao;
    }

}
