package com.lxs.wx_pro;

import com.lxs.wx_pro.dao.TestDao;
import com.lxs.wx_pro.entity.SellerInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WxProApplicationTests {
    @Autowired
private TestDao testDao;
    @Test
    public void contextLoads() {
        List<SellerInfo> all = testDao.findAll();
        all.forEach(System.out::println);
    }

}
