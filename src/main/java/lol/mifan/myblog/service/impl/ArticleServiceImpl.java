package lol.mifan.myblog.service.impl;

import lol.mifan.myblog.controller.UserController;
import lol.mifan.myblog.dao.ArticleDao;
import lol.mifan.myblog.po.Article;
import lol.mifan.myblog.po.Users;
import lol.mifan.myblog.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 米饭 on 2017-06-29.
 */
@Service
public class ArticleServiceImpl extends EntityServiceImpl<Article, Integer> implements ArticleService {


    @Resource
    private ArticleDao articleDao;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Override
    public List<Article> getArticlesByTagId(int tagId, Integer limit, Integer offset) {
        Sort sort = new Sort(Sort.Direction.DESC,"sort");
        sort.and(new Sort(Sort.Direction.DESC,"createTime"));
        Pageable pageable = new PageRequest(offset/limit, limit, sort);
        return articleDao.findAllByTagId(tagId, pageable);
    }

    @Override
    public Object toJsonArray(Collection<Article> articles) {

        List<Object> list = new ArrayList<>(articles.size());
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
