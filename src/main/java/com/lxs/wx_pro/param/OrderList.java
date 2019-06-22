package com.lxs.wx_pro.param;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("订单参数实体类")
@AllArgsConstructor
@NoArgsConstructor
public class OrderList {
    @NotNull(message = "微信号不能为空")
    private String openid;

    private Integer page=0;

    private Integer size=1;

}
