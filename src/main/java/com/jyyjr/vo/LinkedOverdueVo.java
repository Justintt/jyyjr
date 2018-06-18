package com.jyyjr.vo;
/**
 * @author 作者 jinmin
 * @date 创建时间：2018年6月12日 下午1:55:00
 */
public class LinkedOverdueVo {
	
	private String vid;
	private String repayment_no;
	private Integer is_overdue;
	private Integer status;
	private Integer overdue_day;
	
	
	public String getVid() {
		return vid;
	}
	public void setVid(String vid) {
		this.vid = vid;
	}
	public String getRepayment_no() {
		return repayment_no;
	}
	public void setRepayment_no(String repayment_no) {
		this.repayment_no = repayment_no;
	}
	public Integer getIs_overdue() {
		return is_overdue;
	}
	public void setIs_overdue(Integer is_overdue) {
		this.is_overdue = is_overdue;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getOverdue_day() {
		return overdue_day;
	}
	public void setOverdue_day(Integer overdue_day) {
		this.overdue_day = overdue_day;
	}
	
	
	

}
