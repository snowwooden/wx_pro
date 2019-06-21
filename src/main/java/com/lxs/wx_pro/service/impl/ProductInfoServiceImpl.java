package com.lxs.wx_pro.service.impl;

import com.lxs.wx_pro.commons.ResultEnums;
import com.lxs.wx_pro.commons.ResultResponse;
import com.lxs.wx_pro.dao.ProductInfoRepository;
import com.lxs.wx_pro.dtos.ProductCategoryDto;
import com.lxs.wx_pro.dtos.ProductInfoDto;
import com.lxs.wx_pro.entity.ProductInfo;
import com.lxs.wx_pro.service.ProductCategoryService;
import com.lxs.wx_pro.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public ResultResponse queryList() {
        ResultResponse<List<ProductCategoryDto>> categoryServiceResult = productCategoryService.findAll();
        List<ProductCategoryDto> categorydtoList = categoryServiceResult.getData();
        if (CollectionUtils.isEmpty(categorydtoList)) {
            return categoryServiceResult;//如果分类列表为空 直接返回了
        }
        //获得类目编号集合
        List<Integer> categoryTypeList = categorydtoList.stream().map(categorydto -> categorydto.getCategoryType()).collect(Collectors.toList());
        //查询商品列表  这里商品上下架可以用枚举 方便扩展
        List<ProductInfo> productInfoList = productInfoRepository.findByProductStatusAndCategoryTypeIn(ResultEnums.PRODUCT_UP.getCode(), categoryTypeList);
        //多线程遍历 取出每个商品类目编号对应的 商品列表 设置进入类目中
        List<ProductCategoryDto> finalResultList = categorydtoList.parallelStream().map(categorydto -> {
            categorydto.setProductInfoDtoList(productInfoList.stream().
                    filter(productInfo -> productInfo.getCategoryType() == categorydto.getCategoryType()).map(productInfo ->
                    ProductInfoDto.build(productInfo)).collect(Collectors.toList()));
            return categorydto;
        }).collect(Collectors.toList());
        return ResultResponse.success(finalResultList);
    }

    /**
     * 按照商品id查询商品信息
     *
     * @param id
     * @return
     */
    @Override
    public ResultResponse<ProductInfo> queryById(String id) {
        //对id坐判空
        if (StringUtils.isEmpty(id)) {
            //返回空id的错误信息，通过定义好的枚举
            return ResultResponse.fail(ResultEnums.PARAM_ERROR.getMsg() + "::" + id);
        }
        //查询出这个商品
        Optional<ProductInfo> byId = productInfoRepository.findById(id);
        //判断商品是否存在
        if (!byId.isPresent()) {
            return ResultResponse.fail(id + ":" + ResultEnums.NOT_EXITS.getMsg());
        }
        //判断商品是否已经下架
        if (byId.get().getProductStatus() == ResultEnums.PRODUCT_DOWN.getCode()) {
            return ResultResponse.fail(ResultEnums.PRODUCT_DOWN.getMsg() + "::" + id);
        }

        return ResultResponse.success(byId.get());
    }

    /**
     * 更新一个商品
     *
     * @param productInfo
     */
    @Override
    @Transactional
    public void updateProductInfo(ProductInfo productInfo) {
        productInfoRepository.save(productInfo);
        //调用save方法对商品进行保存
    }
}
