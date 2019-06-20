package com.lxs.wx_pro.service;

import com.lxs.wx_pro.commons.ResultResponse;
import com.lxs.wx_pro.dtos.ProductCategoryDto;

import java.util.List;

public interface ProductCategoryService {

    ResultResponse<List<ProductCategoryDto>> findAll();
}
