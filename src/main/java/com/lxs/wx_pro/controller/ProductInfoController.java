package com.lxs.wx_pro.controller;

import com.lxs.wx_pro.commons.ResultResponse;
import com.lxs.wx_pro.service.ProductInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//@RestController是组合注解
@RestController
@RequestMapping("buyer/product")
@Api(description = "商品信息接口")
public class ProductInfoController {

    @Autowired
    private ProductInfoService productInfoService;

    @RequestMapping("list")
    @ApiOperation(value = "查询商品列表")//使用swagger2的注解对方法接口描述
    public ResultResponse ProductInfoList(){

        return productInfoService.queryList();
    }
}
