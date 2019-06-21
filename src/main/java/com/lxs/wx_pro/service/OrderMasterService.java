package com.lxs.wx_pro.service;

import com.lxs.wx_pro.commons.ResultResponse;
import com.lxs.wx_pro.dtos.OrderMasterDto;
import com.lxs.wx_pro.entity.OrderMaster;

public interface OrderMasterService {
    ResultResponse<OrderMaster> insertOrder(OrderMasterDto orderMasterDto);
}
