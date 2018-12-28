package com.drivertest.dao;

import com.drivertest.pojo.Topic;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Jaxw on 12/24/2018.
 */
public interface ReptileDao {

    @Select("select * from topic")
    List<Topic> topicList();

    void insertTopicList(List<Topic> list);

//    @Select("select title from topic")
    List<String> uniqueTopicList();

    @Delete("delete t1 from topic t1 where exists (select 1 from (select id, title, image, topic_type from topic t2) t3  where t1.id > t3.id and t1.title = t3.title and t1.image = t3.image and t1.topic_type = t3.topic_type)")
    int duplicateRemoval();
}
