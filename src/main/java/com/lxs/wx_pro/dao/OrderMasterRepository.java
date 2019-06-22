package com.lxs.wx_pro.dao;

import com.lxs.wx_pro.entity.OrderDetail;
import com.lxs.wx_pro.entity.OrderMaster;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 订单管理dao接口
 */

public interface OrderMasterRepository extends JpaRepository<OrderMaster,String>, JpaSpecificationExecutor<OrderMaster> {
  /* @Query(value = "select * from OrderMaster  "
           + "where buyerOpenid=?1 limit ?3,?4 ",nativeQuery = true)*/
    List<OrderMaster> findByBuyerOpenid(String openId, Pageable pageable);
    //List<OrderMaster> findAllByBuyerOpenid(String openId, Pageable pageable);
    OrderMaster findOrderMasterByBuyerOpenidAndOrderId(String openId,String orderId);
}
