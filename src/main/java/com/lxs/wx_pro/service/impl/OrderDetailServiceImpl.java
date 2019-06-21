package com.lxs.wx_pro.service.impl;

import com.lxs.wx_pro.commons.impl.AbstractBatchDao;
import com.lxs.wx_pro.entity.OrderDetail;
import com.lxs.wx_pro.service.OrderDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 这是商品项的服务
 */
@Service
public class OrderDetailServiceImpl extends AbstractBatchDao<OrderDetail> implements OrderDetailService {
    @Override
    @Transactional
    public void batchInsert(List<OrderDetail> orderDetail) {
        super.batchOderDtails(orderDetail);
    }
}
