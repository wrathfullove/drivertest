package com.drivertest.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Jaxw on 12/24/2018.
 */
@Data
public class Topic implements Serializable {
    private static final long serialVersionUID = -5236897242970622217L;
    private Long id;
    private String title;
    private String options;
    private String image;
    private Integer titleType;
    private Integer topicType;
    private String answer;
    private String explanation;
}
