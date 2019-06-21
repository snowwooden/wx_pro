package com.lxs.wx_pro.commons;

import java.util.List;

/**
 * 批量插入
 * @param <T>
 */
public interface BatchDao<T> {
    void batchOderDtails(List<T> t);
}
