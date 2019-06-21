package com.lxs.wx_pro.entity;

import com.lxs.wx_pro.commons.OrderEnums;
import com.lxs.wx_pro.commons.PayEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
@Table(name="order_master")  //按照了去掉下划线首字母大写的规则 就可以不用指定
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderMaster implements Serializable
{
    @Id
    @Column(name = "order_id") //
    private String orderId;

    @Column(name = "buyer_name")//买家名字
    private String buyerName;

    @Column(name = "buyer_phone")//买家电话
    private String buyerPhone;

    @Column(name = "buyer_address")//买家地址
    private String buyerAddress;

    @Column(name = "buyer_openid")//买家微信openid
    private String buyerOpenid;

    @Column(name = "order_amount")// 订单总金额
    private BigDecimal orderAmount;

    @Column(name = "order_status")// 订单状态, 默认为新下单
    private Integer orderStatus= OrderEnums.NEW.getCode();

    @Column(name = "pay_status")// 支付状态, 默认未支付
    private Integer payStatus= PayEnums.WAIT.getCode();

    @Column(name = "create_time")//创建时间
    private Date createTime;
    @Column(name = "update_time") //修改时间
    private Date updateTime;


}