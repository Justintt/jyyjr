package com.jyyjr.service.impl;




import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jyyjr.common.Message;
import com.jyyjr.dao.ywdao.AbnormNumberMapper;
import com.jyyjr.dao.ywdao.RsMobileBaiduMapper;
import com.jyyjr.dao.txdao.MobileBookMapper;
import com.jyyjr.dao.txdao.MobileCallrecordMapper;
import com.jyyjr.dao.ywdao.TestMobilebookMapper;
import com.jyyjr.dao.ywdao.TestMobilecallMapper;
import com.jyyjr.dao.ywdao.UserMapper;
import com.jyyjr.dao.zxdao.MobileBasicinfoMapper;
import com.jyyjr.pojo.User;
import com.jyyjr.service.ClusterService;

import com.jyyjr.util.TimeUtils;

import ch.qos.logback.classic.Logger;

@Service("clusterService")
public class ClusterServiceImpl implements ClusterService{
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(ClusterServiceImpl.class);
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private MobileBasicinfoMapper mobileBasicinfoMapper;
	@Autowired
	private MobileBookMapper mobileBookMapper;
	@Autowired
	private MobileCallrecordMapper mobileCallrecordMapper;
	@Autowired
	private TestMobilebookMapper testMobilebookMapper;
	@Autowired
	private TestMobilecallMapper testMobilecallMapper;
	@Autowired
	private RsMobileBaiduMapper rsMobileBaiduMapper;
	@Autowired
	private AbnormNumberMapper abnormNumberMapper;
	
	/**
	 * 聚类数据
	 * @param vid
	 * @return
	 */
	public Map<String, Object> getClusterMsg(String vid){
		Map<String, Object> msgMap = new HashMap<>();
		User user = userMapper.selectUserByVid(vid);
		if (user==null) {
			msgMap.put("status", 1002);
			msgMap.put("message", "用户不存在");
			return msgMap;
		}
		long mymobile = user.getMobile();
		Map<String, Object> mobileMap = getMobileMsg(vid,mymobile);
		int status = (int) mobileMap.get("status");
		String message = (String) mobileMap.get("message");
		if (status==1002) {
			msgMap.put("status", 1002);
			msgMap.put("message", message);
			return msgMap;
		}
		List<String> hitCallOtherNumbers = hitCallOtherNumbers(vid,mymobile);
		Map<String, Object> baiduMap = getBaiduCollection(vid,mymobile,hitCallOtherNumbers);
		 
		msgMap.put("status", 1001);
		msgMap.put("message", "正常");
		msgMap.put("p_c_umb_p_cnt_m3", mobileMap.get("p_c_umb_p_cnt_m3"));
		msgMap.put("c_umb_p_max_days_m3", mobileMap.get("c_umb_p_max_days_m3"));
		msgMap.put("c_umb_cnt_m3", mobileMap.get("c_umb_cnt_m3"));
		msgMap.put("s_3_cnt_m3", mobileMap.get("s_3_cnt_m3"));
		msgMap.put("c_n_rate_m3", mobileMap.get("c_n_rate_m3"));
		 
		msgMap.put("hit_bd_tag_cnt", baiduMap.get("hit_bd_tag_cnt"));
		msgMap.put("hit_number_c_cnt", baiduMap.get("hit_number_c_cnt"));
		msgMap.put("r_number_user_cnt", baiduMap.get("r_number_user_cnt"));
		msgMap.put("like_number_cnt", baiduMap.get("like_number_cnt"));
		msgMap.put("like_number_c_cnt", baiduMap.get("like_number_c_cnt"));
		msgMap.put("mix_counts", baiduMap.get("mix_counts"));
		 
	    return msgMap;
	}
	
	/**
	 * 聚类结果
	 * @param vid
	 * @return
	 */
	public Message<Map<String, Object>> clusterResult(String vid){
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> msgMap = getClusterMsg(vid);
		int status = (int) msgMap.get("status");
		String message = (String) msgMap.get("message");
		if (status==1002) {
			return new Message<>(Message.FAIL, message);
		}
		double p_c_umb_p_cnt_m3 = (double) msgMap.get("p_c_umb_p_cnt_m3");
		double c_umb_cnt_m3 = (int) msgMap.get("c_umb_cnt_m3");
		double c_umb_p_max_days_m3 = (int) msgMap.get("c_umb_p_max_days_m3");
		double hit_bd_tag_cnt = (int) msgMap.get("hit_bd_tag_cnt");
		double hit_number_c_cnt = (int) msgMap.get("hit_number_c_cnt");
		double r_number_user_cnt = (int) msgMap.get("r_number_user_cnt");
		double like_number_cnt = (int) msgMap.get("like_number_cnt");
		double like_number_c_cnt = (int) msgMap.get("like_number_c_cnt");
		double s_3_cnt_m3 = (int) msgMap.get("s_3_cnt_m3");
		double c_n_rate_m3 = (double) msgMap.get("c_n_rate_m3");
		double mix_counts = (int) msgMap.get("mix_counts");
		
		if (p_c_umb_p_cnt_m3>0.7353618) {
			p_c_umb_p_cnt_m3 = 0.7353618;
		}
		if (c_umb_cnt_m3>1236.8) {
			c_umb_cnt_m3 = 1236.8;
		}
		if (c_umb_p_max_days_m3>11.99275) {
			c_umb_p_max_days_m3 = 11.99275;
		}
		if (hit_bd_tag_cnt>1.192155) {
			hit_bd_tag_cnt = 1.192155;
		}
		if (hit_number_c_cnt>5.452982) {
			hit_number_c_cnt = 5.452982;
		}
		if (r_number_user_cnt>51.18251) {
			r_number_user_cnt = 51.18251;
		}
		if (like_number_cnt>23.938) {
			like_number_cnt = 23.938;
		}
		if (like_number_c_cnt>203.7386) {
			like_number_c_cnt = 203.7386;
		}
		if (s_3_cnt_m3>12.47196) {
			s_3_cnt_m3 = 12.47196;
		}
		if (c_n_rate_m3>0.1439374) {
			c_n_rate_m3 = 0.1439374;
		}
		if (mix_counts>79.41939) {
			mix_counts = 79.41939;
		}
		
		
		/*double p_c_umb_p_cnt_m3 = 0.486206897;
		int c_umb_cnt_m3 = 290;
		int c_umb_p_max_days_m3 = 4;
		int hit_bd_tag_cnt = 0;
		int hit_number_c_cnt = 0;
		int r_number_user_cnt = 0;
		int like_number_cnt = 12;
		int like_number_c_cnt = 107;
		int s_3_cnt_m3 = 0;
		double c_n_rate_m3 = 0.0008;
		int mix_counts = 34;*/
		
		//
		double[] type1 = {0.433561556,1138.11361,7.038616134,0.300554759,1.322539958,9.251648534,13.55606252,98.48308916,0.55386289,0.025099903,46.69734525};
		double[] type2 = {0.372507634,528.358537,4.437499747,0.186622981,0.796021223,6.59215793,8.768681061,56.70724842,0.777252772,0.02902842,32.98268993};
		double[] type3 = {0.298246929,195.0721904,2.210844554,0.066228352,0.251084955,2.25585,3.80936681,26.09170677,2.350438183,0.033508503,15.75004612};
		double allCount1 = (p_c_umb_p_cnt_m3-type1[0])*(p_c_umb_p_cnt_m3-type1[0])+(c_umb_cnt_m3-type1[1])*(c_umb_cnt_m3-type1[1])+(c_umb_p_max_days_m3-type1[2])*(c_umb_p_max_days_m3-type1[2])+
				           (hit_bd_tag_cnt-type1[3])*(hit_bd_tag_cnt-type1[3])+(hit_number_c_cnt-type1[4])*(hit_number_c_cnt-type1[4])+(r_number_user_cnt-type1[5])*(r_number_user_cnt-type1[5])+
				           (like_number_cnt-type1[6])*(like_number_cnt-type1[6])+(like_number_c_cnt-type1[7])*(like_number_c_cnt-type1[7])+(s_3_cnt_m3-type1[8])*(s_3_cnt_m3-type1[8])+
				           (c_n_rate_m3-type1[9])*(c_n_rate_m3-type1[9])+(mix_counts-type1[10])*(mix_counts-type1[10]);
		
		double allCount2 = (p_c_umb_p_cnt_m3-type2[0])*(p_c_umb_p_cnt_m3-type2[0])+(c_umb_cnt_m3-type2[1])*(c_umb_cnt_m3-type2[1])+(c_umb_p_max_days_m3-type2[2])*(c_umb_p_max_days_m3-type2[2])+
		                   (hit_bd_tag_cnt-type2[3])*(hit_bd_tag_cnt-type2[3])+(hit_number_c_cnt-type2[4])*(hit_number_c_cnt-type2[4])+(r_number_user_cnt-type2[5])*(r_number_user_cnt-type2[5])+
		                   (like_number_cnt-type2[6])*(like_number_cnt-type2[6])+(like_number_c_cnt-type2[7])*(like_number_c_cnt-type2[7])+(s_3_cnt_m3-type2[8])*(s_3_cnt_m3-type2[8])+
		                   (c_n_rate_m3-type2[9])*(c_n_rate_m3-type2[9])+(mix_counts-type2[10])*(mix_counts-type2[10]);
		
		double allCount3 = (p_c_umb_p_cnt_m3-type3[0])*(p_c_umb_p_cnt_m3-type3[0])+(c_umb_cnt_m3-type3[1])*(c_umb_cnt_m3-type3[1])+(c_umb_p_max_days_m3-type3[2])*(c_umb_p_max_days_m3-type3[2])+
                           (hit_bd_tag_cnt-type3[3])*(hit_bd_tag_cnt-type3[3])+(hit_number_c_cnt-type3[4])*(hit_number_c_cnt-type3[4])+(r_number_user_cnt-type3[5])*(r_number_user_cnt-type3[5])+
                           (like_number_cnt-type3[6])*(like_number_cnt-type3[6])+(like_number_c_cnt-type3[7])*(like_number_c_cnt-type3[7])+(s_3_cnt_m3-type3[8])*(s_3_cnt_m3-type3[8])+
                           (c_n_rate_m3-type3[9])*(c_n_rate_m3-type3[9])+(mix_counts-type3[10])*(mix_counts-type3[10]);
		
		double result1 = Math.sqrt(allCount1);
		double result2 = Math.sqrt(allCount2);
		double result3 = Math.sqrt(allCount3);
		double result = (result1<result2)?result1:result2;
		result = (result<result3)?result:result3;
		if (result==result1) {
			map.put("result1", result1);
			map.put("result2", result2);
			map.put("result3", result3);
			map.put("type", "严重催收");
			return new Message<>(Message.SUCCESS, message,map);
		}else if (result==result2) {
			map.put("result1", result1);
			map.put("result2", result2);
			map.put("result3", result3);
			map.put("type", "轻度催收");
			return new Message<>(Message.SUCCESS, message,map);
		}else {
			map.put("result1", result1);
			map.put("result2", result2);
			map.put("result3", result3);
			map.put("type", "未被催收");
			return new Message<>(Message.SUCCESS, message,map);
		}
	}
	
	/**
	 * 疑似催收号码
	 * @param vid
	 * @param mymobile
	 * @return
	 */
	public List<String> hitCallOtherNumbers(String vid,long mymobile) {
		List<String> callOtherNumbers = mobileCallrecordMapper.selectK0013MobilesByMymobile(mymobile);
		List<String> hitCallOtherNumbers = new ArrayList<String>();//符合条件的号码
		for (String callOtherNumber : callOtherNumbers) {
        	//剔除号码为"未知"的
        	if("未知".equals(callOtherNumber)) {
        		continue ;
        	}
            //查询mymobile和callOtherNumber 的被叫通话时间
            List<Integer> times = mobileCallrecordMapper.selectK0013TimesByMymobileAndCallOtherNumber(mymobile, callOtherNumber);
            for (int i=0;i<times.size()-1;i++) {
                int big = times.get(i+1);
                int small = times.get(i);
                //间隔天数
                int intervalDay = (big-small)/86400;
                if(intervalDay == 0) {
                    //当天通话
                    //间隔3小时以上的被叫通话
                    if(big - small >= 60*60*3) {
                        hitCallOtherNumbers.add(callOtherNumber);
                        break;
                    }
                } else if(intervalDay == 1) {
                    //连续两天
                    hitCallOtherNumbers.add(callOtherNumber);
                    break;
                }
            }
        }
		return hitCallOtherNumbers;
	}
	
	/**
	 * 命中百度催收标签号的次数                   
	 * 百度催收标签号码的被叫通话次数
	 * 百度催收标签号码的关联用户数
	 * 用户疑似催收号码的个数
	 * 用户疑似催收号码总通话数
	 * 用户交集数据counts
	 * @param vid
	 * @return
	 */
	public Map<String, Object> getBaiduCollection(String vid,long mymobile,List<String> hitCallOtherNumbers){
		Map<String, Object> resultMap = new HashMap<>();
		//存在命中号码
        if(hitCallOtherNumbers.size() > 0) {
        	/*CountDownLatch countDownLatch = new CountDownLatch(hitCallOtherNumbers.size());	
            for (String mobile : hitCallOtherNumbers) {
            	System.out.println(mobile);
            	Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						//百度催收爬虫接口
						long time10 = System.currentTimeMillis();
		            	String url = "http://116.62.146.43:8199/savetag";
		            	String param = "mobile="+mobile.trim();
		            	String response = HttpUtils.sendGet(url, param);
		                try {
		                    JSONObject resJSON = JSONObject.parseObject(response);
		                } catch (Exception e) {
		                	e.printStackTrace();
		                }
		                long time11 = System.currentTimeMillis();
		                System.out.println("pa:"+(time11-time10));
		                countDownLatch.countDown();  
					}
				});
            	thread.start();
            }
            try {
    			countDownLatch.await();
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}*/
            //命中百度催收标签号的号码
            List<String> hitNumbers = rsMobileBaiduMapper.selectHitCountByMobiles(hitCallOtherNumbers);
            //命中百度催收标签号的次数
            int hit_bd_tag_cnt = 0;
            //百度催收标签号码的被叫通话次数
            int hit_number_c_cnt = 0;
            //百度催收标签号码的关联用户数
            int r_number_user_cnt = 0;
            if (hitNumbers.size()>0) {
            	hit_bd_tag_cnt = hitNumbers.size();
            	hit_number_c_cnt = mobileCallrecordMapper.selectCuiShouBjCount(mymobile,hitNumbers);
            	//获取催收号码库号码
                //List<String> cuiShouNumbers = rsMobileBaiduMapper.selectCuiShouNumber();
                List<String> callNumbers = mobileCallrecordMapper.selectCuiShouNumberInCall(mymobile,hitNumbers);
                r_number_user_cnt = callNumbers.size();
			}
            //用户疑似催收号码的个数
            int like_number_cnt = hitCallOtherNumbers.size();
            //用户疑似催收号码总通话数
            int like_number_c_cnt = mobileCallrecordMapper.selectYsCuiShouCount(mymobile,hitCallOtherNumbers);
            //用户交集数据counts
            List<String> jiaojiNumbers = abnormNumberMapper.selectMixCounts(hitCallOtherNumbers);
            int mix_counts = jiaojiNumbers.size();
            resultMap.put("hit_bd_tag_cnt", hit_bd_tag_cnt);
            resultMap.put("hit_number_c_cnt", hit_number_c_cnt);
            resultMap.put("r_number_user_cnt", r_number_user_cnt);
            resultMap.put("like_number_cnt", like_number_cnt);
            resultMap.put("like_number_c_cnt", like_number_c_cnt);
            resultMap.put("mix_counts", mix_counts);
            
        }else {
        	resultMap.put("hit_bd_tag_cnt", 0);
            resultMap.put("hit_number_c_cnt", 0);
            resultMap.put("r_number_user_cnt", 0);
            resultMap.put("like_number_cnt", 0);
            resultMap.put("like_number_c_cnt", 0);
            resultMap.put("mix_counts", 0);
        }
		return resultMap;
		
	}
	
	/**
	 * 获取用户通讯录
	 * @param vid
	 * @return
	 */
	public List<String> getMobileBook(String vid){
		List<String> lists = mobileBookMapper.selectMobileByVid(vid);
		List<String> mobs = new ArrayList<>();
		if (lists.size()>0 && lists!=null) {
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
		}
		return mobs;
	}
	
	/**
	 * 近3个月非通讯录号码的被叫数量占比         
	 * 近3个月非通讯录号码总的通话数量        
	 * 近3个月非通讯录号码连续被叫的最大天数
	 * 近3个月连续静默三天的次数
	 * 近3个月夜间通话占比            
	 */
	public Map<String, Object> getMobileMsg(String vid,long mymobile) {
		Map<String, Object> map = new HashMap<>();
		Long ctime = mobileBasicinfoMapper.selectCtimeByVid(vid);
		if (ctime==null) {
			map.put("status", 1002);
			map.put("message", "运营商时间未获取");
			return map;
		}
		//获取通讯录号码
		List<String> mobs = getMobileBook(vid);
		if (mobs==null || mobs.size()==0) {
			map.put("status", 1002);
			map.put("message", "通讯录为空");
			return map;
		}
		//获取所有近3个月的通话次数
		List<Map<String, Object>> allCalllists = mobileCallrecordMapper.selectAllCallCountByMyMobile(mymobile, ctime);
		if (allCalllists==null || allCalllists.size()==0) {
			map.put("status", 1002);
			map.put("message", "近3个月通讯记录为空");
			return map;
		}
		//获取被叫近3个月的通话次数
		List<Map<String, Object>> calllists = mobileCallrecordMapper.selectCallCountByMyMobile(mymobile, ctime);
		//夜间通话情况
		Map<String, Object> nightMap = mobileCallrecordMapper.selectCallNightByMyMobile(mymobile, ctime);
		//获取被叫近3个月的通话次数(不干掉1)
		//List<Map<String, Object>> calllistNo1s = mobileCallrecordMapper.selectCallCountNo1ByMyMobile(mymobile, ctime);
		//获取用户近3个月的通话开始时间
		List<Long> callTimeList = mobileCallrecordMapper.selectCallTimeByMyMobile(mymobile, ctime);
		List<String> timeList = new ArrayList<>();
		for(Long callTime : callTimeList) {
			String str = TimeUtils.dateTime(callTime, "yyyy-MM-dd");
			timeList.add(str);
		}
		
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
				BigDecimal num = (BigDecimal) allCalll.get("counts");
				allCallCount += num.intValue();
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
				BigDecimal num = (BigDecimal) calll.get("counts"); 
				callCount += num.intValue();
			}
		}
		
		//近3个月非通讯录号码的被叫数量占比
		double callZb = (double)callCount/allCallCount;
		List<Integer> dayLists = new ArrayList<>();//连续天数集合
		dayLists.add(0);
		bjMob.add("00000000");
		List<Map<String, Object>> dateMap = mobileCallrecordMapper.selectAllDateCallByMyMobile(mymobile, ctime, bjMob);
		
		Set<String> numberCount = new HashSet<>();
		for(Map<String, Object> date : dateMap) {
			numberCount.add((String)date.get("call_other_number"));
		}
		for(String string : numberCount) {
			List<Long> dateCallList = new ArrayList<>();
			for(Map<String, Object> date : dateMap) {
				String call_other_number = (String) date.get("call_other_number");
				if (call_other_number.equals(string)) {
					BigDecimal num = (BigDecimal)date.get("date_call");
					dateCallList.add((long) num.intValue());
				}
			}
			int dayNum = dayCount(dateCallList);
			dayLists.add(dayNum);
		}
		
		int maxDay = Collections.max(dayLists);
		int jmDay = jmDayCount(callTimeList,ctime);
		double nightCall = 0.0;
		if (nightMap!=null) {
			long count = (long) nightMap.get("counts");
			long all_counts = (long) nightMap.get("all_counts");
			nightCall = (double)count/all_counts;
		}
		
		map.put("status", 1001);
		map.put("message", "正常");
		map.put("c_umb_p_max_days_m3", maxDay);
		map.put("c_umb_cnt_m3", allCallCount);
		map.put("p_c_umb_p_cnt_m3", callZb);
		map.put("s_3_cnt_m3", jmDay);
		map.put("c_n_rate_m3", nightCall);
		return map;
	}
	
	/**
	 * 计算连续天数
	 * @param dateCallList
	 * @return
	 */
	public int dayCount(List<Long> dateCallList) {
		dateCallList.add(0L);
		int count = 0;
		List<Integer> list = new ArrayList<>();
		list.add(1);
		for(int i=0;i<dateCallList.size()-1;i++) {
			if (dateCallList.size()>1) {
				double size =(dateCallList.get(i)-dateCallList.get(i+1))/86400;
				if (size<=1) {
					count ++;
				}else {
					list.add(count);
					count =0;
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
