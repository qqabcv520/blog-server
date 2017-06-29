package lol.mifan.myblog.service.impl;

import lol.mifan.myblog.dao.TagDao;
import lol.mifan.myblog.po.Tag;
import lol.mifan.myblog.service.TagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 米饭 on 2017-05-22.
 */
@Service
public class TagServiceImpl extends EntityServiceImpl<Tag, Integer> implements TagService {

    @Resource
    private TagDao tagDao;

    @Override
    public Tag get(Integer id) {
        return tagDao.findByIdAndDeletedFalse(id);
    }


    @Override
    public List<Tag> getList(Integer offset, Integer limit, String query) {
        Pageable pageable = new PageRequest(offset/limit, limit, Sort.Direction.ASC,"name");
        if(StringUtils.isEmpty(query)) {
            return tagDao.findAllByDeletedFalse(pageable);
        }
        return tagDao.findAllByNameContainingAndDeletedFalse(query, pageable);
    }


    @Override
    public Object toJsonObj(Tag tag) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", tag.getId());
        map.put("name", tag.getName());
        return map;
    }

    @Override
    public JpaRepository<Tag, Integer> getEntityDao() {
        return tagDao;
    }
}
