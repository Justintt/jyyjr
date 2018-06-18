package com.jyyjr.pojo;

public class MobileCallrecord {
    private Integer id;

    private Integer callinfoid;

    private Long mymobile;

    private Integer callCost;

    private String callLandType;

    private Integer callLongDistance;

    private String callTypeName;

    private Long callRoamCost;

    private String callOtherNumber;

    private Integer callStartTime;

    private Integer callDiscount;

    private String callAddress;

    private Integer callTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCallinfoid() {
        return callinfoid;
    }

    public void setCallinfoid(Integer callinfoid) {
        this.callinfoid = callinfoid;
    }

    public Long getMymobile() {
        return mymobile;
    }

    public void setMymobile(Long mymobile) {
        this.mymobile = mymobile;
    }

    public Integer getCallCost() {
        return callCost;
    }

    public void setCallCost(Integer callCost) {
        this.callCost = callCost;
    }

    public String getCallLandType() {
        return callLandType;
    }

    public void setCallLandType(String callLandType) {
        this.callLandType = callLandType == null ? null : callLandType.trim();
    }

    public Integer getCallLongDistance() {
        return callLongDistance;
    }

    public void setCallLongDistance(Integer callLongDistance) {
        this.callLongDistance = callLongDistance;
    }

    public String getCallTypeName() {
        return callTypeName;
    }

    public void setCallTypeName(String callTypeName) {
        this.callTypeName = callTypeName == null ? null : callTypeName.trim();
    }

    public Long getCallRoamCost() {
        return callRoamCost;
    }

    public void setCallRoamCost(Long callRoamCost) {
        this.callRoamCost = callRoamCost;
    }

    public String getCallOtherNumber() {
        return callOtherNumber;
    }

    public void setCallOtherNumber(String callOtherNumber) {
        this.callOtherNumber = callOtherNumber == null ? null : callOtherNumber.trim();
    }

    public Integer getCallStartTime() {
        return callStartTime;
    }

    public void setCallStartTime(Integer callStartTime) {
        this.callStartTime = callStartTime;
    }

    public Integer getCallDiscount() {
        return callDiscount;
    }

    public void setCallDiscount(Integer callDiscount) {
        this.callDiscount = callDiscount;
    }

    public String getCallAddress() {
        return callAddress;
    }

    public void setCallAddress(String callAddress) {
        this.callAddress = callAddress == null ? null : callAddress.trim();
    }

    public Integer getCallTime() {
        return callTime;
    }

    public void setCallTime(Integer callTime) {
        this.callTime = callTime;
    }
}