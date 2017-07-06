package lol.mifan.myblog.dao;

import lol.mifan.myblog.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by 米饭 on 2017-05-22.
 */
public interface TagDao extends JpaRepository<Tag, Integer> {


     List<Tag> findAllByNameContaining(String name, Pageable pageable);
}
