package lol.mifan.myblog.dao;

import lol.mifan.myblog.po.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by 米饭 on 2017-07-01.
 */
public interface ReviewDao extends JpaRepository<Review, Integer> {

    List<Review> findAllByArticle_IdAndDeletedFalse(Integer articleId, Pageable pageable);

    List<Review> findAllByReview_IdAndDeletedFalse(Integer reviewId, Pageable pageable);

}
