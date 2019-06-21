package com.lxs.wx_pro.service.impl;

import com.lxs.wx_pro.commons.ResultResponse;
import com.lxs.wx_pro.dao.ProductCategoryRepository;
import com.lxs.wx_pro.dtos.ProductCategoryDto;
import com.lxs.wx_pro.entity.ProductCategory;
import com.lxs.wx_pro.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public ResultResponse<List<ProductCategoryDto>> findAll() {
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        //利用流转换为dto集合
        return ResultResponse.success(productCategoryList.stream().map(productCategory ->
                ProductCategoryDto.build(productCategory)
        ).collect(Collectors.toList()));

    }
}
