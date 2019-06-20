package com.lxs.wx_pro.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Entity
@Data
@DynamicUpdate
@Table(name="product_category") //按照了去掉下划线首字母大写的规则 就可以不用指定
public class ProductCategory {
    @Id
    @Column(name = "category_id")
    private Integer categoryId;
    @Column(name = "category_name")
    private String categoryName;
    @Column(name = "category_type")
    private Integer categoryType;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
}

