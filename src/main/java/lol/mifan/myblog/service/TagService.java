package lol.mifan.myblog.service;

import lol.mifan.myblog.po.Tag;

import java.util.List;

/**
 * Created by 米饭 on 2017-05-22.
 */
public interface TagService extends EntityService<Tag, Integer> {

    Tag get(Integer id);

    /**
     *
     * @param offset 偏移量
     * @param limit 个数
     * @param query tag名字模糊搜索
     * @return
     */
    List<Tag> getList(Integer offset, Integer limit, String query);

    Object toJsonObj(Tag tag);
}
