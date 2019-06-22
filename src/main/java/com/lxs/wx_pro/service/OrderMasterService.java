package com.lxs.wx_pro.service;

import com.google.common.collect.Lists;
import com.lxs.wx_pro.commons.ResultResponse;
import com.lxs.wx_pro.dtos.OrderListDto;
import com.lxs.wx_pro.dtos.OrderMasterDto;
import com.lxs.wx_pro.entity.OrderMaster;
import com.lxs.wx_pro.param.OneOrderDetail;
import com.lxs.wx_pro.param.OrderList;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public interface OrderMasterService {
    /**
     * 添加订单的方法
     * @param orderMasterDto
     * @return
     */
    ResultResponse<OrderMaster> insertOrder(OrderMasterDto orderMasterDto);
   //按微信id分页查询订单列表信息
    List<OrderMaster> findAllByOpenId(String openId, Pageable pageable);
    //按规则将订单列表显示到
    ResultResponse<OrderListDto> getOrderList(OrderList orderList);
    //按微信id和订单id查询具体的订单信息
    ResultResponse<OrderListDto> getOrderByOpenIdandOrderId(OneOrderDetail detail);
    //取消订单操作
    ResultResponse cancelanOrder(OneOrderDetail detail);
}
