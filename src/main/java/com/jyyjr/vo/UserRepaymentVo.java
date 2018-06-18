package com.jyyjr.vo;
/**
 * @author 作者 jinmin
 * @date 创建时间：2018年6月1日 下午2:44:55
 */
public class UserRepaymentVo {
	
	private String vid;
	private Integer repay_time;
	private String borrow_no;
	private String repayment_no;
	private Integer repay_money;
	public String getVid() {
		return vid;
	}
	public void setVid(String vid) {
		this.vid = vid;
	}
	public Integer getRepay_time() {
		return repay_time;
	}
	public void setRepay_time(Integer repay_time) {
		this.repay_time = repay_time;
	}
	public String getBorrow_no() {
		return borrow_no;
	}
	public void setBorrow_no(String borrow_no) {
		this.borrow_no = borrow_no;
	}
	public String getRepayment_no() {
		return repayment_no;
	}
	public void setRepayment_no(String repayment_no) {
		this.repayment_no = repayment_no;
	}
	public Integer getRepay_money() {
		return repay_money;
	}
	public void setRepay_money(Integer repay_money) {
		this.repay_money = repay_money;
	}
	

}
