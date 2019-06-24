package com.lxs.wx_pro.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lxs.wx_pro.commons.*;
import com.lxs.wx_pro.dao.OrderDetailRepository;
import com.lxs.wx_pro.dao.OrderMasterRepository;
import com.lxs.wx_pro.dtos.OrderDetailDto;
import com.lxs.wx_pro.dtos.OrderListDto;
import com.lxs.wx_pro.dtos.OrderMasterDto;
import com.lxs.wx_pro.entity.OrderDetail;
import com.lxs.wx_pro.entity.OrderMaster;
import com.lxs.wx_pro.entity.ProductInfo;
import com.lxs.wx_pro.param.OneOrderDetail;
import com.lxs.wx_pro.param.OrderList;
import com.lxs.wx_pro.service.OrderDetailService;
import com.lxs.wx_pro.service.OrderMasterService;
import com.lxs.wx_pro.service.ProductInfoService;
import com.lxs.wx_pro.utils.BigDecimalUtil;
import com.lxs.wx_pro.utils.CustomException;
import com.lxs.wx_pro.utils.IDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @Autowired
    OrderDetailRepository orderDetailRepository;


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
            if (productInfoResult.getCode() == ResultEnums.FAIL.getCode()) {
                throw new CustomException(ResultEnums.FAIL.getMsg());
            }
            ProductInfo productInfo = productInfoResult.getData();
            //判断库存
            if (productInfo.getProductStock() < detail.getProductQuantity()) {
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
            productInfo.setProductStock(productInfo.getProductStock() - detail.getProductQuantity());
            productInfoService.updateProductInfo(productInfo);
            //算价格
            price = BigDecimalUtil.add(price, BigDecimalUtil.multi(productInfo.getProductPrice(), detail.getProductQuantity()));
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
        map.put("orderId", orderId);
        return ResultResponse.success(map);
    }

    /**
     * 分页查询处订单列表
     *
     * @param openId
     * @param pageable
     * @return
     */
    @Override
    public List<OrderMaster> findAllByOpenId(String openId, Pageable pageable) {
        //参数判空
        //查询订单
        List<OrderMaster> orderMasters = orderMasterRepository.findByBuyerOpenid(openId, pageable);
        //创建一个集合来装订单和订单项
        //判断订单是否为空，为空则返空
        //根据每一个订单的订单id查询对应的订单详情id
        return orderMasters;
    }
    /**
     * 封装查询的订单列表
     *
     * @param orderList
     * @return
     */
    @Override
    public ResultResponse<OrderListDto> getOrderList(OrderList orderList) {
        //参数校验
        //调用查询订单列表
        Pageable request = new PageRequest(orderList.getPage(), orderList.getSize());
        List<OrderMaster> masters = findAllByOpenId(orderList.getOpenid(), request);
        //
        if (masters.isEmpty()) {
            return ResultResponse.success(Lists.newArrayList());
        }
        //转DTO
        List<OrderListDto> orderListDtos = masters.stream().map(OrderMaster -> OrderListDto.turnToDto(OrderMaster)).collect(Collectors.toList());
        //判空
        //获取所有的orderId
        List<String> orderIds = orderListDtos.stream().map(OrderListDto::getOrderId).collect(Collectors.toList());
       //获取所有的订单详情
        List<OrderDetail> orderDetailByOrOrderIdIn = orderDetailRepository.findOrderDetailByOrOrderIdIn(orderIds);
       //将orderList赋值具体的订单详情列表
        orderDetailByOrOrderIdIn.stream()
                .forEach(OrderDetail -> orderListDtos.stream()
                        .forEach(orderListDto -> {
            if (orderListDto.getOrderId().equals(OrderDetail.getOrderId())){
                orderListDto.getOrderDetailList().add(OrderDetail);
            }
        } ));
        //将订单返回为需要的格式
        return ResultResponse.success(orderListDtos);
    }
    /**
     * 查询一个订单详情
     *
     * @param detail
     * @return
     */
    @Override
    public ResultResponse<OrderListDto> getOrderByOpenIdandOrderId(OneOrderDetail detail) {
        //参数校BeanValidator.check(param);
        //根据信息查询具体的订单
        OrderMaster order = orderMasterRepository.
                findOrderMasterByBuyerOpenidAndOrderId(detail.getOpenid(), detail.getOrderId());
        //判空
        if (order == null) {
            throw new CustomException(OrderEnums.ORDER_NOT_EXITS.getMsg());
        }
        //根据具体订单信息的orderiD去订单项表中查询具体的信息
        List<OrderDetail> details = orderDetailRepository.findOrderDetailByOrderId(order.getOrderId());
        //将信息组装回去
        OrderListDto dto = OrderListDto.turnToDto(order);
        List<OrderDetail> list = dto.getOrderDetailList();
          details.stream().map(orderDetail -> list.add(orderDetail)).collect(Collectors.toList());
        return ResultResponse.success(dto);
    }
    /**
     * 取消一个订单
     *
     * @param detail
     * @return
     */
    @Override
    @Transactional
    public ResultResponse cancelanOrder(OneOrderDetail detail) {
        //参数验证//todo:参数验证未完成
        //更具信息拿到订单信息
        OrderMaster order = orderMasterRepository.
                findOrderMasterByBuyerOpenidAndOrderId(detail.getOpenid(), detail.getOrderId());
        //根据订单的状态判断是否已经取消
        if (order.getOrderStatus() != OrderEnums.NEW.getCode()) {
            return ResultResponse.fail(OrderEnums.FINSH_CANCEL.getMsg());
        }
        //设置订单状态为失效的订单
        order.setOrderStatus(OrderEnums.CANCEL.getCode());
        //保存订单修改信息
        orderMasterRepository.save(order);
        //返回
        return ResultResponse.success();
    }

}
