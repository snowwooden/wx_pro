package com.lxs.wx_pro.controller;

import com.google.common.collect.Maps;
import com.lxs.wx_pro.commons.ResultResponse;
import com.lxs.wx_pro.dtos.OrderMasterDto;
import com.lxs.wx_pro.param.OneOrderDetail;
import com.lxs.wx_pro.param.OrderList;
import com.lxs.wx_pro.service.OrderMasterService;
import com.lxs.wx_pro.utils.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("buyer/order")
@Api(value = "订单相关接口",description = "完成订单的增删改查")
public class OrderMasterController {
    @Autowired
    private OrderMasterService orderMasterService;
    @PostMapping("create")
    @ApiOperation(value = "创建订单接口", httpMethod = "POST", response = ResultResponse.class)
    public ResultResponse create(
            @Valid @ApiParam(name="订单对象",value = "传入json格式",required = true)
                    OrderMasterDto orderMasterDto, BindingResult bindingResult){
        Map<String,String> map = Maps.newHashMap();
        //判断是否有参数校验问题
        if(bindingResult.hasErrors()){
            List<String> errList = bindingResult.getFieldErrors().stream().map(err -> err.getDefaultMessage()).collect(Collectors.toList());
            map.put("参数校验错误", JsonUtil.object2string(errList));
            //将参数校验的错误信息返回给前台
            return  ResultResponse.fail(map);
        }
        return orderMasterService.insertOrder(orderMasterDto);
    }
    @PostMapping("list")
    @ApiOperation(value = "查询订单列表", httpMethod = "POST", response = ResultResponse.class)
    public ResultResponse orderList(OrderList list){
       return orderMasterService.getOrderList(list);
    }

    @PostMapping("detail")
    @ApiOperation(value = "查询一个订单的详情", httpMethod = "POST", response = ResultResponse.class)
    public ResultResponse getOneOrderDetail(OneOrderDetail detail){
       return orderMasterService.getOrderByOpenIdandOrderId(detail);
    }@PostMapping("cancel")
    @ApiOperation(value = "查询一个订单的详情", httpMethod = "POST", response = ResultResponse.class)
    public ResultResponse cancelOneOrder(OneOrderDetail detail){
       return orderMasterService.cancelanOrder(detail);
    }
}
