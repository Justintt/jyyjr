package com.jyyjr.pojo;

import java.math.BigDecimal;

public class UserBorrow {
    private Integer id;

    private String vid;

    private BigDecimal money;

    private String playAccount;

    private Integer getTime;

    private Byte isSuccess;

    private String borrowNo;

    private Integer ctime;

    private Float fee;

    private Byte status;

    private Byte rateId;

    private Byte checkstatus;

    private Byte isBespeak;

    private Boolean borrowType;

    private Integer couponId;

    private Float balanceFee;

    private Byte isBalance;

    private BigDecimal couponMoney;

    private BigDecimal refundMoney;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid == null ? null : vid.trim();
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getPlayAccount() {
        return playAccount;
    }

    public void setPlayAccount(String playAccount) {
        this.playAccount = playAccount == null ? null : playAccount.trim();
    }

    public Integer getGetTime() {
        return getTime;
    }

    public void setGetTime(Integer getTime) {
        this.getTime = getTime;
    }

    public Byte getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Byte isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getBorrowNo() {
        return borrowNo;
    }

    public void setBorrowNo(String borrowNo) {
        this.borrowNo = borrowNo == null ? null : borrowNo.trim();
    }

    public Integer getCtime() {
        return ctime;
    }

    public void setCtime(Integer ctime) {
        this.ctime = ctime;
    }

    public Float getFee() {
        return fee;
    }

    public void setFee(Float fee) {
        this.fee = fee;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getRateId() {
        return rateId;
    }

    public void setRateId(Byte rateId) {
        this.rateId = rateId;
    }

    public Byte getCheckstatus() {
        return checkstatus;
    }

    public void setCheckstatus(Byte checkstatus) {
        this.checkstatus = checkstatus;
    }

    public Byte getIsBespeak() {
        return isBespeak;
    }

    public void setIsBespeak(Byte isBespeak) {
        this.isBespeak = isBespeak;
    }

    public Boolean getBorrowType() {
        return borrowType;
    }

    public void setBorrowType(Boolean borrowType) {
        this.borrowType = borrowType;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public Float getBalanceFee() {
        return balanceFee;
    }

    public void setBalanceFee(Float balanceFee) {
        this.balanceFee = balanceFee;
    }

    public Byte getIsBalance() {
        return isBalance;
    }

    public void setIsBalance(Byte isBalance) {
        this.isBalance = isBalance;
    }

    public BigDecimal getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(BigDecimal couponMoney) {
        this.couponMoney = couponMoney;
    }

    public BigDecimal getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(BigDecimal refundMoney) {
        this.refundMoney = refundMoney;
    }
}