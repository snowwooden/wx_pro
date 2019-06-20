package com.lxs.wx_pro.dao;

import com.lxs.wx_pro.entity.ProductCategory;
import com.lxs.wx_pro.entity.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 卖家信息接口
 */

public interface SellerInfoRepository extends JpaRepository<SellerInfo,String>, JpaSpecificationExecutor<SellerInfo> {
}
