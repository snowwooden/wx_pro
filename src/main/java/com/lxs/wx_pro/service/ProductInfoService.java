package com.lxs.wx_pro.service;

import com.lxs.wx_pro.commons.ResultResponse;
import com.lxs.wx_pro.entity.ProductInfo;

import java.util.List;

public interface ProductInfoService {
    //查询全部商品的信息
    ResultResponse queryList();
    //更具商品iD去查询对应商品的详情
    ResultResponse<ProductInfo> queryById(String id);
    //更新一个商品信息的方法
    void updateProductInfo(ProductInfo productInfo);
}
