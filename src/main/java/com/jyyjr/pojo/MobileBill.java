package com.jyyjr.pojo;

public class MobileBill {
    private Integer id;

    private String vid;

    private Long mobile;

    private Integer billDiscount;

    private Integer billFee;

    private String billCycle;

    private Integer paidAmount;

    private Integer unpaidAmount;

    private Integer breachAmount;

    private Integer billTotal;

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

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public Integer getBillDiscount() {
        return billDiscount;
    }

    public void setBillDiscount(Integer billDiscount) {
        this.billDiscount = billDiscount;
    }

    public Integer getBillFee() {
        return billFee;
    }

    public void setBillFee(Integer billFee) {
        this.billFee = billFee;
    }

    public String getBillCycle() {
        return billCycle;
    }

    public void setBillCycle(String billCycle) {
        this.billCycle = billCycle == null ? null : billCycle.trim();
    }

    public Integer getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Integer paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Integer getUnpaidAmount() {
        return unpaidAmount;
    }

    public void setUnpaidAmount(Integer unpaidAmount) {
        this.unpaidAmount = unpaidAmount;
    }

    public Integer getBreachAmount() {
        return breachAmount;
    }

    public void setBreachAmount(Integer breachAmount) {
        this.breachAmount = breachAmount;
    }

    public Integer getBillTotal() {
        return billTotal;
    }

    public void setBillTotal(Integer billTotal) {
        this.billTotal = billTotal;
    }
}