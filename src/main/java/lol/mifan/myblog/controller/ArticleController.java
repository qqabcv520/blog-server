package lol.mifan.myblog.controller;

import lol.mifan.myblog.exception.HttpException;
import lol.mifan.myblog.po.Article;
import lol.mifan.myblog.po.Review;
import lol.mifan.myblog.service.ArticleService;
import lol.mifan.myblog.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 米饭 on 2017-06-30.
 */
@RestController
@RequestMapping(value = "/articles", produces="application/json;charset=UTF-8")
public class ArticleController {


    @Resource
    private ArticleService articleService;

    @Resource
    private ReviewService reviewService;

    @GetMapping(value = "/{id}")
    public Object get(@PathVariable("id")int id) throws HttpException {
        Article article = articleService.get(id);

        return articleService.toJsonObj(article);
    }


    @GetMapping
    public Object getList(Integer limit, Integer offset) throws HttpException {
        List<Article> articles = articleService.getList(limit, offset);
        return articleService.toJsonArray(articles);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id")int id) throws HttpException {
//        articleService.get(id);
        //todo

    }

    @GetMapping(value = "/{id}/reviews")
    public Object getReviews(@PathVariable("id")int id, Integer offset, Integer limit) throws HttpException {
        List<Review> reviewList = reviewService.getReviewsByArticleId(id, offset, limit);
        List<Object> jsonArray = new ArrayList<>();
        for (Review review : reviewList) {
            List<Review> list = reviewService.getReviewsByReviewId(review.getId(), offset, limit);
            Object jsonObj = reviewService.toJsonObj(review, reviewService.toJsonArray(list));
            jsonArray.add(jsonObj);
        }

        return jsonArray;
    }

    @PostMapping(value = "/{id}/reviews")
    public Map<String, Object> postReview(@PathVariable("id")int id, Review review) throws HttpException {
        return null;
    }
}
