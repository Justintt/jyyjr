package com.jyyjr.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jyyjr.common.Message;
import com.jyyjr.dao.csdao.Yy20180521DataMapper;
import com.jyyjr.dao.txdao.MobileCallrecordMapper;
import com.jyyjr.dao.ywdao.UserMapper;
import com.jyyjr.dao.zxdao.MobileBasicinfoMapper;
import com.jyyjr.pojo.MobileCallrecord;
import com.jyyjr.pojo.User;
import com.jyyjr.pojo.Yy20180521Data;
import com.jyyjr.service.MobileCallrecordService;
import com.sun.org.apache.xml.internal.serializer.ElemDesc;

@Service("mobileCallrecordService")
public class MobileCallrecordServiceImpl implements MobileCallrecordService{

	@Autowired
	private MobileCallrecordMapper mobileCallrecordMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private MobileBasicinfoMapper mobileBasicinfoMapper;
	@Autowired
	private Yy20180521DataMapper yy20180521DataMapper;
	
	public Message<List<MobileCallrecord>> getMobileCallrecord(String vid) {
		User user = userMapper.selectUserByVid(vid);
		long mymobile = user.getMobile();
		List<MobileCallrecord> list = mobileCallrecordMapper.selectMobileCallrecordByVid(mymobile);
		if (list.size()>0) {
			return new Message<>(Message.SUCCESS, "查询成功",list);
		}
		return new Message<>(Message.FAIL, "无数据",list);
	}
	
	
	/**
	 * 检查k0032
	 * @param vid
	 * @return
	 */
	public Message<Map<String, Object>> checkK0032(String vid) {
		Map<String, Object> resultMap = new HashMap<>();
		Yy20180521Data yy20180521Data = new Yy20180521Data();
		int status = 0;
		int age = userMapper.selectUserByVid(vid).getAge();
		Long w = 0L;
		Long m = 0L;
		Long m3 = 0L;
		double avgw = 0.0;
		double avgm = 0.0;
		double avg3m = 0.0;
    	//获取运营商拉取时间
    	Long ctime = mobileBasicinfoMapper.selectCtimeByVid(vid);
    	if (ctime==null) {
    		yy20180521Data.setVid(vid);
    		yy20180521Data.setIsCallDensity(status);
    		yy20180521Data.setAge(age);
    		yy20180521Data.setIsCallDensityAvgw(avgw+"");
    		yy20180521Data.setIsCallDensityAvgm(avgm+"");
    		yy20180521Data.setIsCallDensityAvg3m(avg3m+"");
    		yy20180521DataMapper.updateByPrimaryKeySelective(yy20180521Data);
			return new Message<>(Message.FAIL, "运营商时间没有拉取");
		}
    	long week_time = ctime-60*60*24*7;
    	long one_month_time = ctime-60*60*24*30;
    	long three_month_time = ctime-60*60*24*90;
    	User userBean = userMapper.selectUserByVid(vid);
    	long mymobile = userBean.getMobile();
    	List<Map<String, Object>> top3List = mobileCallrecordMapper.selectCallTop3ByMobile(mymobile, week_time, one_month_time, three_month_time);
    	if (top3List.size()>0) {
    		for(Map<String, Object> map : top3List) {
        		Long call_count_week = (Long) map.get("call_count_week");
        		Long call_count_one_month = (Long) map.get("call_count_one_month");
        		Long call_count_three_month = (Long) map.get("call_count_three_month");
        		w += call_count_week;
        		m += call_count_one_month;
        		m3 += call_count_three_month;
        		if (call_count_week < 1 && call_count_one_month<10 && call_count_three_month<40) {
        			status = 1;
    			}else if (call_count_week < 10 && call_count_one_month<10 && call_count_three_month<10) {
    				status = 1;
				}
        	}
    		avgw = (double)w/3;
    		avgm = (double)m/3;
    		avg3m = (double)m3/3;
    		
		}else {
			yy20180521Data.setVid(vid);
			yy20180521Data.setIsCallDensity(status);
    		yy20180521Data.setAge(age);
    		yy20180521Data.setIsCallDensityAvgw(avgw+"");
    		yy20180521Data.setIsCallDensityAvgm(avgm+"");
    		yy20180521Data.setIsCallDensityAvg3m(avg3m+"");
    		yy20180521DataMapper.updateByPrimaryKeySelective(yy20180521Data);
			return new Message<>(Message.FAIL, "用户通讯记录有问题");
		}
    	yy20180521Data.setVid(vid);
    	yy20180521Data.setIsCallDensity(status);
		yy20180521Data.setAge(age);
		yy20180521Data.setIsCallDensityAvgw(avgw+"");
		yy20180521Data.setIsCallDensityAvgm(avgm+"");
		yy20180521Data.setIsCallDensityAvg3m(avg3m+"");
		yy20180521DataMapper.updateByPrimaryKeySelective(yy20180521Data);
    	return new Message<>(Message.SUCCESS, "成功");
    }
}
