package com.lxs.wx_pro.utils;

import com.lxs.wx_pro.commons.ResultEnums;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private int  code;

    public CustomException(int code,String message){
        super(message);
        this.code = code;
    }
    public CustomException(String message){
        this(ResultEnums.FAIL.getCode(),message);
    }

}
