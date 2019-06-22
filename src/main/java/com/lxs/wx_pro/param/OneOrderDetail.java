package com.lxs.wx_pro.param;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Data
@ApiModel("订单详情类")
@AllArgsConstructor
@NoArgsConstructor
public class OneOrderDetail {
    @NotBlank(message = "用户微信号不能为空")
    private String openid;
    @NotBlank(message = "订单号不能为空")
    private String orderId;
}
