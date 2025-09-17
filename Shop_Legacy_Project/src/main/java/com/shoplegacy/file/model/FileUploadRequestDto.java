package com.shoplegacy.file.model;

import java.time.LocalDate;

public class FileUploadRequestDto {

   private Integer cartNo;
   private String userId;
   private Long prodNo;
   private String prodName;
   private String ordNo;
   private LocalDate crtDt; // 또는 LocalDateTimeprivate String updDt;
    private String fileUrl;
    private String fileName;

    private String callbackUrl;

    public Integer getCartNo() {
        return cartNo;
    }

    public void setCartNo(Integer cartNo) {
        this.cartNo = cartNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getProdNo() {
        return prodNo;
    }

    public void setProdNo(Long prodNo) {
        this.prodNo = prodNo;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getOrdNo() {
        return ordNo;
    }

    public void setOrdNo(String ordNo) {
        this.ordNo = ordNo;
    }

    public LocalDate getCrtDt() {
        return crtDt;
    }

    public void setCrtDt(LocalDate crtDt) {
        this.crtDt = crtDt;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
}
