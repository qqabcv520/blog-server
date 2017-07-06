package lol.mifan.myblog.service.impl;

import lol.mifan.myblog.service.EntityService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by 米饭 on 2017-05-26.
 */
public abstract class EntityServiceImpl<T, ID extends Serializable> implements EntityService<T, ID> {

    /**
     * 由业务类实现
     * @return
     */
    public abstract JpaRepository<T, ID> getEntityDao();

    @Transactional
    @Override
    public <S extends T> S save(S entity) {
        return getEntityDao().save(entity);
    }

    @Transactional
    @Override
    public <S extends T> Iterable<S> save(Iterable<S> entities) {
        return getEntityDao().save(entities);
    }

    @Transactional(readOnly=true, isolation=Isolation.READ_COMMITTED)
    @Override
    public T findOne(ID id) {
        return getEntityDao().findOne(id);
    }

    @Transactional(readOnly=true, isolation=Isolation.READ_COMMITTED)
    @Override
    public boolean exists(ID id) {
        return getEntityDao().exists(id);
    }

    @Transactional(readOnly=true, isolation=Isolation.READ_COMMITTED)
    @Override
    public Iterable<T> findAll() {
        return getEntityDao().findAll();
    }

    @Transactional(readOnly=true, isolation=Isolation.READ_COMMITTED)
    @Override
    public Iterable<T> findAll(Iterable<ID> ids) {
        return getEntityDao().findAll(ids);
    }

    @Transactional(readOnly=true, isolation= Isolation.READ_COMMITTED)
    @Override
    public long count() {
        return getEntityDao().count();
    }

    @Transactional
    @Override
    public void delete(ID id) {
        getEntityDao().delete(id);
    }

    @Transactional
    @Override
    public void delete(T entity) {
        getEntityDao().delete(entity);
    }

    @Transactional
    @Override
    public void delete(Iterable<? extends T> entities) {
        getEntityDao().delete(entities);
    }

    @Transactional
    @Override
    public void deleteAll() {
        getEntityDao().deleteAll();
    }


    @Override
    public Object toJsonObj(T entity) {
        return entity;
    }

    @Override
    public Object toJsonArray(Iterable<T> entities) {
        return entities;
    }
}
