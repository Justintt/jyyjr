package com.jyyjr.pojo;

public class RsMobileBaidu {
    private Integer id;

    private String number;

    private String rsContent;

    private Integer ctime;

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

    public String getRsContent() {
        return rsContent;
    }

    public void setRsContent(String rsContent) {
        this.rsContent = rsContent == null ? null : rsContent.trim();
    }

    public Integer getCtime() {
        return ctime;
    }

    public void setCtime(Integer ctime) {
        this.ctime = ctime;
    }
}