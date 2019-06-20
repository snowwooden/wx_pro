package com.lxs.wx_pro.dao;

import com.lxs.wx_pro.entity.OrderDetail;
import com.lxs.wx_pro.entity.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 订单详情的dao接口
 */

public interface OrderDetailRepository  extends JpaRepository<OrderDetail,String>, JpaSpecificationExecutor<OrderDetail> {
}
