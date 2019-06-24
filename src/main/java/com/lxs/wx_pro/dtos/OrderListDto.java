package com.lxs.wx_pro.dtos;

import com.google.common.collect.Lists;
import com.lxs.wx_pro.commons.OrderEnums;
import com.lxs.wx_pro.commons.PayEnums;
import com.lxs.wx_pro.entity.OrderDetail;
import com.lxs.wx_pro.entity.OrderMaster;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@ApiModel("订单列表参数")
public class OrderListDto {
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    private Integer orderStatus= OrderEnums.NEW.getCode();

    private Integer payStatus= PayEnums.WAIT.getCode();

    private Date createTime;

    private Date updateTime;

    private List<OrderDetail> orderDetailList = Lists.newArrayList();

    public static OrderListDto turnToDto(OrderMaster orderMaster) {
        OrderListDto dto = new OrderListDto();
        BeanUtils.copyProperties(orderMaster, dto);
        return dto;
    }
}
