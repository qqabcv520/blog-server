package lol.mifan.myblog.controller;

import com.sun.org.apache.xpath.internal.operations.String;
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
    public Object getList(Integer page, Integer size) throws HttpException {
        List<Article> articles = articleService.getList(page, size);
        return articleService.toJsonArray(articles);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id")int id) throws HttpException {
//        articleService.get(id);
        //todo

    }

    @GetMapping(value = "/{articleId}/reviews")
    public Object getReviews(@PathVariable("articleId")int articleId, Integer page, Integer size,
                             @RequestParam(defaultValue = "5") int reviewSize) throws HttpException {
        List<Review> reviewList = reviewService.getReviewsByArticleId(articleId, page, size);
        List<Object> jsonArray = new ArrayList<>();
        for (Review review : reviewList) {
            List<Review> list = reviewService.getReviewsByReviewId(review.getId(), 0, reviewSize);
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
