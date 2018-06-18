package com.jyyjr.pojo;

public class QhUserLoanPlatform {
    private Integer id;

    private String vid;

    private String batchno;

    private Short amount;

    private Short bnkamount;

    private String busidate;

    private Short cnssamount;

    private String ercode;

    private String ermsg;

    private String idno;

    private Boolean idtype;

    private String industry;

    private String name;

    private Short p2pamount;

    private Short queryamtm3;

    private Short queryamtm6;

    private Boolean reasoncode;

    private String seqno;

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

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno == null ? null : batchno.trim();
    }

    public Short getAmount() {
        return amount;
    }

    public void setAmount(Short amount) {
        this.amount = amount;
    }

    public Short getBnkamount() {
        return bnkamount;
    }

    public void setBnkamount(Short bnkamount) {
        this.bnkamount = bnkamount;
    }

    public String getBusidate() {
        return busidate;
    }

    public void setBusidate(String busidate) {
        this.busidate = busidate == null ? null : busidate.trim();
    }

    public Short getCnssamount() {
        return cnssamount;
    }

    public void setCnssamount(Short cnssamount) {
        this.cnssamount = cnssamount;
    }

    public String getErcode() {
        return ercode;
    }

    public void setErcode(String ercode) {
        this.ercode = ercode == null ? null : ercode.trim();
    }

    public String getErmsg() {
        return ermsg;
    }

    public void setErmsg(String ermsg) {
        this.ermsg = ermsg == null ? null : ermsg.trim();
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno == null ? null : idno.trim();
    }

    public Boolean getIdtype() {
        return idtype;
    }

    public void setIdtype(Boolean idtype) {
        this.idtype = idtype;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry == null ? null : industry.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Short getP2pamount() {
        return p2pamount;
    }

    public void setP2pamount(Short p2pamount) {
        this.p2pamount = p2pamount;
    }

    public Short getQueryamtm3() {
        return queryamtm3;
    }

    public void setQueryamtm3(Short queryamtm3) {
        this.queryamtm3 = queryamtm3;
    }

    public Short getQueryamtm6() {
        return queryamtm6;
    }

    public void setQueryamtm6(Short queryamtm6) {
        this.queryamtm6 = queryamtm6;
    }

    public Boolean getReasoncode() {
        return reasoncode;
    }

    public void setReasoncode(Boolean reasoncode) {
        this.reasoncode = reasoncode;
    }

    public String getSeqno() {
        return seqno;
    }

    public void setSeqno(String seqno) {
        this.seqno = seqno == null ? null : seqno.trim();
    }

    public Integer getCtime() {
        return ctime;
    }

    public void setCtime(Integer ctime) {
        this.ctime = ctime;
    }
}