package com.drivertest.controller;

import com.drivertest.enumeration.TopicTypeEnum;
import com.drivertest.service.ReptileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Jaxw on 12/25/2018.
 */
@Component
@Slf4j
public class JobController {

    @Autowired
    ReptileService reptileService;

    @Scheduled(fixedRate=60 * 10 * 1000)
    public void reptileJob() {
        log.info("爬虫定时任务启动，10分钟一次....");
        String url1 = "http://www.jsyst.cn/jkbd/11335/";
        String url4 = "http://kao.jsyst.cn/anquan/";
        reptileService.getTopic(url1, TopicTypeEnum.SUBJECT1.getCode());
        reptileService.getTopic(url4, TopicTypeEnum.SUBJECT4.getCode());
    }

}
