package com.miaoshaproject.error;

public interface CommonError {
    public Integer getErrCode();
    public String getErrMsg();
    public CommonError setErrMsg(String errMsg);
}
