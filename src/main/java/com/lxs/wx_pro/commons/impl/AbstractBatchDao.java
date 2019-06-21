package com.lxs.wx_pro.commons.impl;

import com.lxs.wx_pro.commons.BatchDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * 批量插入的实现
 * @param <T>
 */
public class AbstractBatchDao<T> implements BatchDao<T> {
    @PersistenceContext
    protected EntityManager em;

    @Override
    @Transactional
    public void batchOderDtails(List<T> t) {
        long length = t.size();
        for (int i = 0; i <length; i++) {
            em.persist(t.get(i));
            if (i % 100 == 0||i==length-1) {//每100条执行一次写入数据库操作
                em.flush();
                em.clear();
            }
        }
    }


}
