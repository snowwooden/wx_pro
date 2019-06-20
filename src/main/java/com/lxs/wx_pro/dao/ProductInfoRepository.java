package com.lxs.wx_pro.dao;

import com.lxs.wx_pro.entity.ProductCategory;
import com.lxs.wx_pro.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 商品信息dao接口
 */

public interface ProductInfoRepository extends JpaRepository<ProductInfo,String>,
        JpaSpecificationExecutor<ProductInfo> {
    //根据类目编号 查询正常上架的商品
    List<ProductInfo> findByProductStatusAndCategoryTypeIn(Integer status, List<Integer> categoryList);
}
