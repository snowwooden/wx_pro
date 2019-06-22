package com.lxs.wx_pro;

import com.google.gson.Gson;
import com.lxs.wx_pro.commons.ResultResponse;
import com.lxs.wx_pro.dao.OrderDetailRepository;
import com.lxs.wx_pro.dao.OrderMasterRepository;
import com.lxs.wx_pro.dao.TestDao;
import com.lxs.wx_pro.dtos.OrderListDto;
import com.lxs.wx_pro.entity.OrderDetail;
import com.lxs.wx_pro.entity.OrderMaster;
import com.lxs.wx_pro.entity.SellerInfo;
import com.lxs.wx_pro.param.OneOrderDetail;
import com.lxs.wx_pro.param.OrderList;
import com.lxs.wx_pro.service.OrderMasterService;
import com.lxs.wx_pro.service.impl.OrderMasterServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WxProApplicationTests {
    @Autowired
private TestDao testDao;
    @Autowired
    private OrderMasterRepository repository;
    @Autowired
    private OrderDetailRepository repositorys;
    @Autowired
    private OrderMasterServiceImpl masterService;
    @Test
    public void contextLoads() {
        List<SellerInfo> all = testDao.findAll();
        all.forEach(System.out::println);
    }
    @Test
    public void queryAllByOpenid() {

        ResultResponse<OrderListDto> oXDaO1RMGiRJACn5Bsp0nkHEqQ_w = masterService.getOrderList(new OrderList("oXDaO1RMGiRJACn5Bsp0nkHEqQ_w", 1, 10));
        Gson gson = new Gson();
        String s = gson.toJson(oXDaO1RMGiRJACn5Bsp0nkHEqQ_w);
        System.out.println(s);

    }

    }



