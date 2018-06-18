package com.jyyjr.pojo;

public class QhUserRiskHint {
    private Integer id;

    private String vid;

    private String batchno;

    private String databuildtime;

    private Byte datastatus;

    private String ercode;

    private String ermsg;

    private String idno;

    private Boolean idtype;

    private String name;

    private String rskmark;

    private Short rskscore;

    private String seqno;

    private String sourceid;

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

    public String getDatabuildtime() {
        return databuildtime;
    }

    public void setDatabuildtime(String databuildtime) {
        this.databuildtime = databuildtime == null ? null : databuildtime.trim();
    }

    public Byte getDatastatus() {
        return datastatus;
    }

    public void setDatastatus(Byte datastatus) {
        this.datastatus = datastatus;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRskmark() {
        return rskmark;
    }

    public void setRskmark(String rskmark) {
        this.rskmark = rskmark == null ? null : rskmark.trim();
    }

    public Short getRskscore() {
        return rskscore;
    }

    public void setRskscore(Short rskscore) {
        this.rskscore = rskscore;
    }

    public String getSeqno() {
        return seqno;
    }

    public void setSeqno(String seqno) {
        this.seqno = seqno == null ? null : seqno.trim();
    }

    public String getSourceid() {
        return sourceid;
    }

    public void setSourceid(String sourceid) {
        this.sourceid = sourceid == null ? null : sourceid.trim();
    }

    public Integer getCtime() {
        return ctime;
    }

    public void setCtime(Integer ctime) {
        this.ctime = ctime;
    }
}