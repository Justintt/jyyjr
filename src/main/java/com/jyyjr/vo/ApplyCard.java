package com.jyyjr.vo;

public class ApplyCard {
	
	//最近3个自然月深夜时间的通话次数
	private Integer call_count_late_night_3month;
	//最近3个自然月互通通话的号码数量
	private Integer contact_count_mutual_3month;
	//最近6个自然月通话时长小于1分钟的通话次数（被除数）
	private Integer call_count_call_time_less1min_6month;
	//最近6个自然月通话次数（除数）
	private Integer call_count_6month;
	//最近6个自然月通话次数大于等于10的号码数量
	private Integer contact_count_call_count_over10_6month;
	//最近6个自然月通话时长大于等于10分钟的通话次数
	private Integer call_count_call_time_over10min_6month;
	//月主叫通话次数
	private Integer call_count_active;
	//月主叫通话的号码数量
	private Integer contact_count_active;
	//月深夜通话次数（被除数）
	private Integer call_count_late_night;
	//月通话次数（除数）
	private Integer call_count;
	//性别
	private String sex;
	//年龄
	private Integer age;
	//注册时间
	private Integer ctime;
	//同盾七天申请数量
	private Integer td_7days;
	//入网时长
	private Integer mobile_net_age;
	//近三个月话费
	private Double consume_amount_3month;
	
	
	public Integer getCall_count_late_night_3month() {
		return call_count_late_night_3month;
	}
	public void setCall_count_late_night_3month(Integer call_count_late_night_3month) {
		this.call_count_late_night_3month = call_count_late_night_3month;
	}
	public Integer getContact_count_mutual_3month() {
		return contact_count_mutual_3month;
	}
	public void setContact_count_mutual_3month(Integer contact_count_mutual_3month) {
		this.contact_count_mutual_3month = contact_count_mutual_3month;
	}
	public Integer getCall_count_call_time_less1min_6month() {
		return call_count_call_time_less1min_6month;
	}
	public void setCall_count_call_time_less1min_6month(Integer call_count_call_time_less1min_6month) {
		this.call_count_call_time_less1min_6month = call_count_call_time_less1min_6month;
	}
	public Integer getCall_count_6month() {
		return call_count_6month;
	}
	public void setCall_count_6month(Integer call_count_6month) {
		this.call_count_6month = call_count_6month;
	}
	public Integer getContact_count_call_count_over10_6month() {
		return contact_count_call_count_over10_6month;
	}
	public void setContact_count_call_count_over10_6month(Integer contact_count_call_count_over10_6month) {
		this.contact_count_call_count_over10_6month = contact_count_call_count_over10_6month;
	}
	public Integer getCall_count_call_time_over10min_6month() {
		return call_count_call_time_over10min_6month;
	}
	public void setCall_count_call_time_over10min_6month(Integer call_count_call_time_over10min_6month) {
		this.call_count_call_time_over10min_6month = call_count_call_time_over10min_6month;
	}
	public Integer getCall_count_active() {
		return call_count_active;
	}
	public void setCall_count_active(Integer call_count_active) {
		this.call_count_active = call_count_active;
	}
	public Integer getContact_count_active() {
		return contact_count_active;
	}
	public void setContact_count_active(Integer contact_count_active) {
		this.contact_count_active = contact_count_active;
	}
	public Integer getCall_count_late_night() {
		return call_count_late_night;
	}
	public void setCall_count_late_night(Integer call_count_late_night) {
		this.call_count_late_night = call_count_late_night;
	}
	public Integer getCall_count() {
		return call_count;
	}
	public void setCall_count(Integer call_count) {
		this.call_count = call_count;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer getCtime() {
		return ctime;
	}
	public void setCtime(Integer ctime) {
		this.ctime = ctime;
	}
	public Integer getTd_7days() {
		return td_7days;
	}
	public void setTd_7days(Integer td_7days) {
		this.td_7days = td_7days;
	}
	public Integer getMobile_net_age() {
		return mobile_net_age;
	}
	public void setMobile_net_age(Integer mobile_net_age) {
		this.mobile_net_age = mobile_net_age;
	}
	public Double getConsume_amount_3month() {
		return consume_amount_3month;
	}
	public void setConsume_amount_3month(Double consume_amount_3month) {
		this.consume_amount_3month = consume_amount_3month;
	}
	
	
	
	

}
