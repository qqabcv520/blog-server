package lol.mifan.myblog.service;

import lol.mifan.myblog.po.Tag;

import java.util.Collection;
import java.util.List;

/**
 * Created by 米饭 on 2017-05-22.
 */
public interface TagService extends EntityService<Tag, Integer> {

    /**
     * 根据主键获取tag
     * @param id 主键
     * @return tag
     */
    Tag get(Integer id);

    /**
     * 获取tag集合
     * @param offset 偏移量
     * @param limit 个数
     * @param query tag名字模糊搜索
     * @return tag集合
     */
    List<Tag> getList(Integer offset, Integer limit, String query);


}
