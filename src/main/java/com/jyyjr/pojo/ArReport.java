package com.jyyjr.pojo;

public class ArReport {
    private Integer id;

    private String vid;

    private Integer applydate;

    private String membertype;

    private String creditaddress;

    private Byte loantype;

    private Integer loanmoney;

    private String applyresult;

    private String remark;

    private Integer ctime;

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

    public Integer getApplydate() {
        return applydate;
    }

    public void setApplydate(Integer applydate) {
        this.applydate = applydate;
    }

    public String getMembertype() {
        return membertype;
    }

    public void setMembertype(String membertype) {
        this.membertype = membertype == null ? null : membertype.trim();
    }

    public String getCreditaddress() {
        return creditaddress;
    }

    public void setCreditaddress(String creditaddress) {
        this.creditaddress = creditaddress == null ? null : creditaddress.trim();
    }

    public Byte getLoantype() {
        return loantype;
    }

    public void setLoantype(Byte loantype) {
        this.loantype = loantype;
    }

    public Integer getLoanmoney() {
        return loanmoney;
    }

    public void setLoanmoney(Integer loanmoney) {
        this.loanmoney = loanmoney;
    }

    public String getApplyresult() {
        return applyresult;
    }

    public void setApplyresult(String applyresult) {
        this.applyresult = applyresult == null ? null : applyresult.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getCtime() {
        return ctime;
    }

    public void setCtime(Integer ctime) {
        this.ctime = ctime;
    }
}