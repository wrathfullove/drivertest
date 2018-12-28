package com.drivertest.runner;

import com.drivertest.service.ReptileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component//被spring容器管理
@Order(1)//如果多个自定义ApplicationRunner，用来标明执行顺序
public class MyApplicationRunner implements ApplicationRunner {
    @Resource
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    ReptileService reptileService;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        System.out.println("-------------->" + "项目启动，now=" + new Date());
        cacheTitleList();
    }

    public void cacheTitleList(){
//        redisTemplate.delete("uniqueTopicList");
//        List<String> uniqueTopicList = reptileService.uniqueTopicList();
//        if (uniqueTopicList == null || uniqueTopicList.size() < 0) {
//            uniqueTopicList = new ArrayList<>();
//        }
//        redisTemplate.opsForValue().set("uniqueTopicList", uniqueTopicList);
    }
}