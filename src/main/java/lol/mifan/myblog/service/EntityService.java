package lol.mifan.myblog.service;

import lol.mifan.myblog.po.Tag;
import lol.mifan.myblog.po.Users;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by 米饭 on 2017-05-26.
 */
public interface EntityService<T, ID extends Serializable> {
    <S extends T> S save(S entity);

    <S extends T> Iterable<S> save(Iterable<S> entities);

    T findOne(ID id);

    boolean exists(ID id);

    Iterable<T> findAll();

    Iterable<T> findAll(Iterable<ID> ids);

    long count();

    void delete(ID id);

    void delete(T entity);

    void delete(Iterable<? extends T> entities);

    void deleteAll();

    /**
     * 转成json对象
     * @param entity 待转换的entity
     * @return 可直接进行josn序列化的对象
     */
    Object toJsonObj(T entity);

    /**
     * 转成json数组
     * @param entities 待转换的entity集合
     * @return 可直接进行josn序列化的对象
     */
    Object toJsonArray(Iterable<T> entities);

}
