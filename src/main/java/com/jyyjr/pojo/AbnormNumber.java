package com.jyyjr.pojo;

public class AbnormNumber {
    private Integer id;

    private String number;

    private Integer ctime;

    private String fromUserVid;

    private String fromUserMobile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public Integer getCtime() {
        return ctime;
    }

    public void setCtime(Integer ctime) {
        this.ctime = ctime;
    }

    public String getFromUserVid() {
        return fromUserVid;
    }

    public void setFromUserVid(String fromUserVid) {
        this.fromUserVid = fromUserVid == null ? null : fromUserVid.trim();
    }

    public String getFromUserMobile() {
        return fromUserMobile;
    }

    public void setFromUserMobile(String fromUserMobile) {
        this.fromUserMobile = fromUserMobile == null ? null : fromUserMobile.trim();
    }
}