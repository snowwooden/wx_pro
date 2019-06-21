package com.lxs.wx_pro.service;

import com.lxs.wx_pro.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    void batchInsert(List<OrderDetail> orderDetail);
}
