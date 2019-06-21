package com.lxs.wx_pro.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lxs.wx_pro.commons.*;
import com.lxs.wx_pro.dao.OrderMasterRepository;
import com.lxs.wx_pro.dtos.OrderDetailDto;
import com.lxs.wx_pro.dtos.OrderMasterDto;
import com.lxs.wx_pro.entity.OrderDetail;
import com.lxs.wx_pro.entity.OrderMaster;
import com.lxs.wx_pro.entity.ProductInfo;
import com.lxs.wx_pro.service.OrderDetailService;
import com.lxs.wx_pro.service.OrderMasterService;
import com.lxs.wx_pro.service.ProductInfoService;
import com.lxs.wx_pro.utils.BigDecimalUtil;
import com.lxs.wx_pro.utils.CustomException;
import com.lxs.wx_pro.utils.IDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.awt.color.CMMException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 关于订单的服务
 */
@Service
public class OrderMasterServiceImpl implements OrderMasterService {
    //注入内个商品信息的服务
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private OrderDetailService orderDetailService;
    //注入订单的ipa
    @Autowired
    private OrderMasterRepository orderMasterRepository;


    @Override
    @Transactional
    public ResultResponse<OrderMaster> insertOrder(OrderMasterDto orderMasterDto) {
        //1.更具id查询封装的order对象那个
        List<OrderDetailDto> items = orderMasterDto.getItems();
        //3.*定义要封装的订单项和金额
        ArrayList<OrderDetail> list = Lists.newArrayList();
        BigDecimal price = new BigDecimal("0");
        //3.再从封装对象中取出具体的数据订单
        //创建一个订单号
        String orderId = IDUtils.createIdbyUUID();
        for (OrderDetailDto detail : items) {
            ResultResponse<ProductInfo> productInfoResult = productInfoService.queryById(detail.getProductId());
        //2.更具封装对象的code判断是否成功
            if (productInfoResult.getCode()== ResultEnums.FAIL.getCode()){
                throw  new  CustomException(ResultEnums.FAIL.getMsg());
            }
            ProductInfo productInfo = productInfoResult.getData();
           //判断库存
            if (productInfo.getProductStock()<detail.getProductQuantity()){
               throw new CustomException(ProductEnums.PRODUCT_NOT_ENOUGH.getMsg());
            }
            //开始将这些订单项的内容装进去
            OrderDetail orderDetail = OrderDetail.
                    builder()
                    .detailId(IDUtils.createIdbyUUID())
                    .productIcon(productInfo.getProductIcon())
                    .productId(detail.getProductId())
                    .productName(productInfo.getProductName())
                    .productPrice(productInfo.getProductPrice())
                    .productQquantity(detail.getProductQuantity())
                    .orderId(orderId)
                    .build();
            //把组装好多订单项装进要存进数据库的list
            list.add(orderDetail);
            //减库存
            productInfo.setProductStock(productInfo.getProductStock()-detail.getProductQuantity());
            productInfoService.updateProductInfo(productInfo);
            //算价格
            price= BigDecimalUtil.add(price,BigDecimalUtil.multi(productInfo.getProductPrice(),detail.getProductQuantity()));
            //4.去除订单中的订单项
        //5.对这些订单想坐处理
        }
           //组装订单
        OrderMaster orderMaster = OrderMaster.builder().buyerAddress(orderMasterDto.getAddress()).buyerName(orderMasterDto.getName())
                .buyerOpenid(orderMasterDto.getOpenid()).orderStatus(OrderEnums.NEW.getCode())
                .payStatus(PayEnums.WAIT.getCode()).buyerPhone(orderMasterDto.getPhone())
                .orderId(orderId).orderAmount(price).build();
         //批量提交订单项
        orderDetailService.batchInsert(list);
        //订单提交
        orderMasterRepository.save(orderMaster);
        //7.返回前台需要的订单id
        HashMap<String, String> map = Maps.newHashMap();
        map.put("orderId",orderId);
        return ResultResponse.success(map);
    }
}
