package com.drivertest.service;

import com.drivertest.dao.ReptileDao;
import com.drivertest.enumeration.TitleTypeEnum;
import com.drivertest.enumeration.TopicTypeEnum;
import com.drivertest.pojo.Topic;
import com.drivertest.util.ImageUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jaxw on 12/21/2018.
 */
@Service
@Slf4j
public class ReptileService {

    @Autowired
    private ReptileDao reptileDao;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${imgBasePath}")
    private String imgBasePath;

    private static String unicodeToCn(String unicode) {
        /** 以 \ u 分割，因为java注释也能识别unicode，因此中间加了一个空格*/
        String[] strs = unicode.split("\\\\u");
        String returnStr = "";
        // 由于unicode字符串以 \ u 开头，因此分割出的第一个字符是""。
        for (int i = 1; i < strs.length; i++) {
            returnStr += (char) Integer.valueOf(strs[i], 16).intValue();
        }
        return returnStr;
    }

    public static void main(String[] args) {
        String str = "<p>\\u4f60</p><span>\\u6211</span><p>\\u4f60</p><span>\\u6211</span>";
        String str1 = str;
        String pattern = "\\\\\\w*";
        Pattern pt = Pattern.compile(pattern);
        Matcher m = pt.matcher(str);
        while (m.find()) {
            String s = m.group();
            str = str.replace(s, ReptileService.unicodeToCn(s));
        }
        log.info("转换前：{}", str1);
        log.info("转换后：{}", str);
    }

    @Transactional
    public void getTopic(String url, Integer topicType) {
        try {
            Document doc = Jsoup.connect(url).get();
            // 内容
            Element content = doc.getElementsByClass("testInfo").first();
            Elements tests = content.getElementsByClass("test");
            List<Topic> list = new ArrayList();
            List<String> uniqueTopicList = this.uniqueTopicList();
            tests.forEach(o -> {
                Topic topic = new Topic();
                // 题目
                String title = o.getElementsByTag("h5").first().text();
                title = title.substring(title.indexOf("、") + 1).trim();
                // 第一个table的class为outer_table既单选题，panduan既判断题
                String titleType = o.getElementsByTag("table").first().className();
                List options = new ArrayList(4);
                if (titleType.equals("outer_table")) {
                    // 单选题选项
                    Elements optionsElm = o.getElementsByTag("p");
                    for (int i = 0; i < 4; i++) {
                        options.add((optionsElm.get(i).text().trim()));
                    }
                } else if (titleType.equals("panduan")) {
                    // 判断题选项
                    options.add("对");
                    options.add("错");
                }
                // 获取答案和解释
                String url1 = o.getElementsByAttributeValue("type", "hidden").last().val();
                String[] answerAndExplain = this.getAnswerAndExplain(url1);
                topic.setTitle(title);
                topic.setOptions(options.toString().replace("[", "").replace("]", ""));
                topic.setAnswer(answerAndExplain[0]);
                topic.setExplanation(answerAndExplain[1]);
                topic.setTitleType(titleType.equals("outer_table")? TitleTypeEnum.SINGLE_CHOICE.getCode():TitleTypeEnum.T_OR_F.getCode());
                topic.setTopicType(topicType);
                StringBuilder uniqueTopic = new StringBuilder();
                uniqueTopic.append(topic.getTitle()).append(topic.getOptions()).append(topic.getAnswer()).append(topic.getTopicType());
                // 若数据存在，则continue
                if (uniqueTopicList.contains(uniqueTopic.toString().trim())) {
                    return;
                }
                // 图片
                String imgUrl = "";
                String imgName = null;
                Elements imgElements = o.getElementsByClass("warp_img");
                if (imgElements.size() > 0) {
                    imgUrl = imgElements.first().child(0).attr("src");
                    imgName = UUID.randomUUID() + imgUrl.substring(imgUrl.lastIndexOf("."), imgUrl.length());
                    ImageUtils.saveImage(imgUrl, imgBasePath, imgName);
                    topic.setImage(imgName);
                }
                list.add(topic);
            });
//            redisTemplate.delete("uniqueTopicList");
//            redisTemplate.opsForValue().set("uniqueTopicList", uniqueTopicList);
            if (list.size() != 0) {
                reptileDao.insertTopicList(list);
            }
            log.info("本次插入 {} 条 {} 数据", list.size(), topicType == TopicTypeEnum.SUBJECT1.getCode()? "科目一": "科目四");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getAnswerAndExplain(String url) {
        String[] resultArr = new String[2];
        try {
            Document doc = Jsoup.connect(url).get();
            Element content = doc.getElementsByClass("vehiclesIn3").first();
            Elements preAnswers = content.getElementsByAttributeValue("size", "6").attr("color", "red");
            String answer = preAnswers.tagName("b").first().text();
            String explain = preAnswers.first().parent().nextElementSibling().nextElementSibling().tagName("b").text().substring(3);
            resultArr[0] = answer;
            resultArr[1] = explain.substring(0, explain.indexOf("更多试题详细分析"));
            return resultArr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> uniqueTopicList() {
        return reptileDao.uniqueTopicList();
    }

}
