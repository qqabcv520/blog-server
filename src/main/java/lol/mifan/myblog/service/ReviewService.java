package lol.mifan.myblog.service;

import lol.mifan.myblog.po.Review;
import lol.mifan.myblog.service.impl.EntityServiceImpl;

import java.util.List;

/**
 * Created by 米饭 on 2017-07-01.
 */
public interface ReviewService extends EntityService<Review, Integer> {
    List<Review> getReviewsByArticleId(int AarticleId, Integer offset, Integer limit);

    List<Review> getReviewsByReviewId(int reviewId, Integer offset, Integer limit);


    /**
     * 包含子评论一起转换成json
     * @param review
     * @param childReviewJson 被转换为json的评论下的子评论
     * @return
     */
    Object toJsonObj(Review review, Object childReviewJson);
}
