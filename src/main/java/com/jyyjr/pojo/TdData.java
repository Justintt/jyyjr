package com.jyyjr.pojo;

public class TdData {
    private Integer id;

    private String vid;

    private String reportId;

    private Integer sevenDay;

    private Integer oneMonth;

    private Integer threeMonth;

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

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId == null ? null : reportId.trim();
    }

    public Integer getSevenDay() {
        return sevenDay;
    }

    public void setSevenDay(Integer sevenDay) {
        this.sevenDay = sevenDay;
    }

    public Integer getOneMonth() {
        return oneMonth;
    }

    public void setOneMonth(Integer oneMonth) {
        this.oneMonth = oneMonth;
    }

    public Integer getThreeMonth() {
        return threeMonth;
    }

    public void setThreeMonth(Integer threeMonth) {
        this.threeMonth = threeMonth;
    }
}