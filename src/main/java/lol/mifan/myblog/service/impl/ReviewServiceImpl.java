package lol.mifan.myblog.service.impl;

import lol.mifan.myblog.dao.ReviewDao;
import lol.mifan.myblog.po.Review;
import lol.mifan.myblog.po.Tag;
import lol.mifan.myblog.po.Users;
import lol.mifan.myblog.service.ReviewService;
import lol.mifan.myblog.service.TagService;
import lol.mifan.myblog.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 米饭 on 2017-07-01.
 */
@Service
public class ReviewServiceImpl extends EntityServiceImpl<Review, Integer> implements ReviewService {

    @Resource
    private ReviewDao reviewDao;
    @Resource
    private UserService userService;


    @Override
    public List<Review> getReviewsByArticleId(int articleId, Integer page, Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        size = size == null ? Integer.MAX_VALUE : size;//默认值无穷大
        page = page == null ? 0 : page;//默认值0
        Pageable pageable = new PageRequest(page, size, sort);

        return reviewDao.findAllByArticle_IdAndDeletedFalse(articleId, pageable);
    }

    @Override
    public List<Review> getReviewsByReviewId(int reviewId, Integer page, Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        size = size == null  ? Integer.MAX_VALUE : size;//默认值无穷大
        page = page == null ? 0 : page;//默认值0
        Pageable pageable = new PageRequest(page, size, sort);

        return reviewDao.findAllByReview_IdAndDeletedFalse(reviewId, pageable);
    }




    @Override
    public Object toJsonObj(Review review, Object childReviewJson) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", review.getId());
        map.put("content", review.getContent());
        map.put("createTime", review.getCreateTime());

        Users user = review.getUsers();
        if(user != null) {
            map.put("user", userService.toJsonObj(user));
        }
        map.put("reviews", childReviewJson);
        return map;
    }

    @Override
    public Object toJsonObj(Review review) {
        return toJsonObj(review, null);
    }

    @Override
    public Object toJsonArray(Iterable<Review> reviews) {
        List<Object> list = new ArrayList<>();
        for (Review review : reviews) {
            list.add(toJsonObj(review));
        }
        return list;
    }


    @Override
    public JpaRepository<Review, Integer> getEntityDao() {
        return reviewDao;
    }


}
