package com.lxs.wx_pro.entity;

import lombok.AllArgsConstructor;
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
@Table(name="seller_info") // 按照了去掉下划线首字母大写的规则 就可以不用指定
@AllArgsConstructor
@NoArgsConstructor
public class SellerInfo  implements Serializable {

    @Id
    @Column(name = "id")
    private String id;

    /** 用户名. */
    @Column(name = "username")
    private String username;

    /** 密码. */
    @Column(name = "password")
    private String password;

    /** 微信openid. */
    @Column(name = "openid")
    private String openid;

    private Date createTime;

    private Date updateTime;


}