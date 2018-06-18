package com.jyyjr.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jyyjr.base.BaseTest;
import com.jyyjr.dao.ywdao.TestMobilebookMapper;
import com.jyyjr.dao.ywdao.TestMobilecallMapper;
import com.jyyjr.dao.ywdao.UserMapper;
import com.jyyjr.dao.zxdao.MobileBasicinfoMapper;
import com.jyyjr.util.TimeUtils;

public class TestMobileCallRecord extends BaseTest{

	@Autowired
	private TestMobilecallMapper testMobilecallMapper;
	@Autowired
	private TestMobilebookMapper testMobilebookMapper;
	@Autowired
	private MobileBasicinfoMapper mobileBasicinfoMapper;
	@Autowired
	private UserMapper userMapper;
	
	/*@Test
	public void test01() {
		List<String> lists = testMobilebookMapper.selectMobileByVid("1c05b3f980c9ea600ef8219815c1bf58");
		List<String> mobs = new ArrayList<>();
		for(String list : lists) {
			if (list.contains(",") || list.contains(";") || list.contains("#")) {
				if (list.contains(",")) {
					String[] str = list.split(",");
					for(int i=0;i<str.length;i++) {
						mobs.add(str[i]);
					}
				}
				if (list.contains(";")) {
					String[] str = list.split(";");
					for(int i=0;i<str.length;i++) {
						mobs.add(str[i]);
					}
				}
				if (list.contains("#")) {
					String[] str = list.split("#");
					for(int i=0;i<str.length;i++) {
						mobs.add(str[i]);
					}
				}
				
			}else {
				mobs.add(list);
			}
		}
		long mymobile = 15658700036L;
		Long ctime = 1497584354L;
		//获取所有近3个月的通话次数
		List<Map<String, Object>> allCalllists = testMobilecallMapper.selectAllCallCountByMyMobile(mymobile, ctime);
		//获取被叫近3个月的通话次数
		List<Map<String, Object>> calllists = testMobilecallMapper.selectCallCountByMyMobile(mymobile, ctime);
		
		//统计非通讯录号码近3个月的通话次数
		int allCallCount = 0;
		for(Map<String, Object> allCalll : allCalllists) {
			String callMob = (String) allCalll.get("call_other_number");
			boolean flag = false;
			for(String mob : mobs) {
				if (callMob.equals(mob)) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				long num = (long) allCalll.get("counts"); 
				allCallCount += num;
			}
		}
		//统计非通讯号码被叫近3个月的通话次数
		List<String> bjMob = new ArrayList<>();//非通讯录号码
		int callCount = 0;
		for(Map<String, Object> calll : calllists) {
			String callMob = (String) calll.get("call_other_number");
			boolean flag = false;
			for(String mob : mobs) {
				if (callMob.equals(mob)) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				bjMob.add(callMob);
				long num = (long) calll.get("counts"); 
				callCount += num;
			}
		}
		//近3个月非通讯录号码的被叫数量占比
		double callZb = (double)callCount/allCallCount;
		List<Integer> dayLists = new ArrayList<>();//连续天数集合
		for(String bj : bjMob) {
			String mobile = bj;
			List<Map<String, Object>> dateMap = testMobilecallMapper.selectDateCallByMyMobile(mymobile, ctime, mobile);
			List<Long> dateCallList = new ArrayList<>(); 
			for(Map<String, Object> date : dateMap) {
				long dateCall = (long) date.get("date_call");
				dateCallList.add(dateCall);
			}
			int count = 0;
			for(int i=0;i<dateCallList.size()-1;i++) {
				if (dateCallList.size()>1) {
					double size =(dateCallList.get(i)-dateCallList.get(i+1))/86400;
					if (size<=1) {
						count += 1;
					}
				}
			}
			int dayNum = count;
			dayLists.add(dayNum);
		}
		int maxDay = Collections.max(dayLists);
		System.out.println("allCallCount:"+allCallCount+",callCount:"+callCount+",callZb:"+callZb+",maxDay:"+maxDay);
	}*/
	
	/*@Test
	public void test02() {
		long mymobile = 15658700036L;
		Long ctime = 1492337750L;
		//String[] mobile = "'13017859912','057189852304'";
		List<String> mobile = new ArrayList<>();
		mobile.add("13017859912");
		mobile.add("057189852304");
		List<Map<String, Object>> dateMap = testMobilecallMapper.selectAllDateCallByMyMobile(mymobile, ctime, mobile);
		for(Map<String, Object> date : dateMap) {
			System.out.println(date.get("call_other_number")+":"+date.get("date_call"));
		}
	}*/
	
	/*@Test
	public void test03() {
		Long ctime = mobileBasicinfoMapper.selectCtimeByVid("6844a5f933c1fb893bb4dcdb22049c6e");
		long mymobile = 13784826669L;
		List<Long> dateCallList = testMobilecallMapper.selectBinCallAMaxDaysM1(mymobile, ctime);
		int bin_call_a_max_days_m1 = dayCount(dateCallList);
		//获取用户近3个月的通话开始时间
		List<Long> callTimeList = testMobilecallMapper.selectCallTimeByMyMobile(mymobile, ctime);
		//近3个月连续静默3天的次数
		int bin_call_scilence_3d_m3 = jmDayCount(callTimeList,ctime);
		System.out.println("最大连续："+bin_call_a_max_days_m1);
		System.out.println("最大静默："+bin_call_scilence_3d_m3);
		
	}*/
	
	@Test
	public void test04() {
		long mymobile = 15906455556L;
		List<String> mobiles = testMobilecallMapper.selectNumber11ByMyMobile(mymobile);
		List<String> jc_overdue_users = userMapper.selectBinJcOverdueUsers(mobiles);
		int bin_jc_overdue_users = jc_overdue_users.size();
		for(String vid : jc_overdue_users) {
			System.out.println(vid);
		}
		System.out.println(bin_jc_overdue_users);
	}
	/**
	 * 计算连续天数
	 * @param dateCallList
	 * @return
	 */
	public int dayCount(List<Long> dateCallList) {
		dateCallList.add(0L);
		int count = 1;
		List<Integer> list = new ArrayList<>();
		list.add(1);
		for(int i=0;i<dateCallList.size()-1;i++) {
			if (dateCallList.size()>1) {
				double size =(dateCallList.get(i)-dateCallList.get(i+1))/86400;
				if (size<=1) {
					count ++;
				}else {
					list.add(count);
					count =1;
				}
			}
		}
		int maxDay = Collections.max(list);
		return maxDay;
	}
	/**
	 * 连续三天静默次数
	 * @param callTimeList
	 * @return
	 */
	public int jmDayCount(List<Long> callTimeList,Long ctime) {
		long fastTime = TimeUtils.zeroPoint(ctime);
		long lastTime = TimeUtils.reduceDate(fastTime, 3);
		callTimeList.add(0,fastTime);
		callTimeList.add(lastTime);
		int count = 0;
		for(int i=0;i<callTimeList.size()-1;i++) {
			if (callTimeList.size()>1) {
				double size =(callTimeList.get(i)-callTimeList.get(i+1))/86400;
				if (size>3) {
					int num = (int)(size-1)/3;
					count +=num;
				}
			}
		}
		return count;
	}
	
}
