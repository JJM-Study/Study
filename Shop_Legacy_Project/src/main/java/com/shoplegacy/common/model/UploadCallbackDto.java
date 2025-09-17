package com.shoplegacy.common.model;

public class UploadCallbackDto {
    private Integer resultCode;
    private String errMsg;
    private String ordnum;


    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getOrdnum() {
        return ordnum;
    }

    public void setOrdnum(String ordnum) {
        this.ordnum = ordnum;
    }


}

