package com.drivertest.enumeration;


/**
 * Created by Jaxw on 12/26/2018.
 */
public enum TitleTypeEnum {

    SINGLE_CHOICE(1),
    T_OR_F(2);

    private Integer code;

    TitleTypeEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
