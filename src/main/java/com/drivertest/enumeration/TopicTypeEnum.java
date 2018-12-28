package com.drivertest.enumeration;

/**
 * Created by Jaxw on 12/26/2018.
 */
public enum TopicTypeEnum {

    SUBJECT1(1),
    SUBJECT4(4);

    private Integer code;

    TopicTypeEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
