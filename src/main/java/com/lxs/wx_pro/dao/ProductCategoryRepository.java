package com.lxs.wx_pro.dao;

import com.lxs.wx_pro.entity.OrderMaster;
import com.lxs.wx_pro.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 商品类别的dao接口
 */

public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer>, JpaSpecificationExecutor<ProductCategory> {
}
