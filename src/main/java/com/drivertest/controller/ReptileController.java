package com.drivertest.controller;

import com.drivertest.service.ReptileService;
import io.swagger.annotations.Api;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Jaxw on 12/21/2018.
 */
@RestController
@RequestMapping("api")
@Api("爬取信息api")
@Slf4j
public class ReptileController {
    @Autowired
    private ReptileService reptileService;
    @GetMapping("/test")
    public void getTopic(String url, Integer topicType) {
//        reptileService.titleList();
        reptileService.getTopic(url, topicType);
    }
}
