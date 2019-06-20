package com.lxs.wx_pro.dao;

import com.lxs.wx_pro.entity.OrderDetail;
import com.lxs.wx_pro.entity.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 订单管理dao接口
 */

public interface OrderMasterRepository extends JpaRepository<OrderMaster,String>, JpaSpecificationExecutor<OrderMaster> {
}
