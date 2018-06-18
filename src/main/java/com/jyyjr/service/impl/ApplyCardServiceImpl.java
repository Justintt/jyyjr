package com.jyyjr.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jyyjr.common.Message;
import com.jyyjr.service.ApplyCardService;
import com.jyyjr.util.TimeUtils;
import com.jyyjr.vo.ApplyCard;


@Service("applyCardService")
public class ApplyCardServiceImpl implements ApplyCardService{
	
	private final Logger logger = LoggerFactory.getLogger(ApplyCardServiceImpl.class);
	
	public Message<Map<String,String>> getApplyCard(String jsonData){
		logger.info(jsonData);
		Map<String,Object> map = null;
		try {
			map = (Map<String, Object>) JSON.parse(jsonData);
			//遍历map
			int count = 0 ;
			String reg = "^[0-9]+(.[0-9]+)?$"; 
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				count ++;
			    //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
			    if(entry.getValue().equals("") || entry.getValue() == null) {
			    	return new Message<>(Message.FAIL,""+entry.getKey()+"参数不能为空");
			    }
			    if(!(String.valueOf(entry.getValue()).matches(reg)) && !entry.getKey().equals("sex")) {
			    	return new Message<>(Message.FAIL,""+entry.getKey()+"非数字");
			    }  
			}
			System.out.println(count);
			if(count<16) {
				return new Message<>(Message.FAIL,"缺参数");
			}
			ApplyCard applyCard = saveApplyCard(jsonData);
			Map<String,String> resultMap = applyResult(applyCard);
			
			return new Message<>(Message.SUCCESS,"成功",resultMap);
		} catch (Exception e) {
			return new Message<>(Message.FAIL,"json格式有误或参数名有误");
		}
		
	}
	
	public ApplyCard saveApplyCard(String jsonData) {
		JSONObject jsonObject = JSONObject.parseObject(jsonData);
		ApplyCard applyCard = jsonObject.toJavaObject(ApplyCard.class);
		
		/*Integer call_count_late_night_3month = jsonObject.getInteger("call_count_late_night_3month");
		//最近3个自然月互通通话的号码数量
		Integer contact_count_mutual_3month = jsonObject.getInteger("contact_count_mutual_3month");
		//最近6个自然月通话时长小于1分钟的通话次数
		Integer call_count_call_time_less1min_6month = jsonObject.getInteger("call_count_call_time_less1min_6month");
		//最近6个自然月通话次数
		Integer call_count_6month = jsonObject.getInteger("call_count_6month");
		//最近6个自然月通话次数大于等于10的号码数量
		Integer contact_count_call_count_over10_6month = jsonObject.getInteger("contact_count_call_count_over10_6month");
		//最近6个自然月通话时长大于等于10分钟的通话次数
		Integer call_count_call_time_over10min_6month = jsonObject.getInteger("call_count_call_time_over10min_6month");
		Integer p_call_count_call_time_less1min_6month = jsonObject.getInteger("p_call_count_call_time_less1min_6month");
		Integer call_count_active = jsonObject.getInteger("call_count_active");
		Integer contact_count_active = jsonObject.getInteger("contact_count_active");
		Integer call_count_late_night = jsonObject.getInteger("call_count_late_night");
		Integer call_count = jsonObject.getInteger("call_count");
		Integer p_count_late_night = jsonObject.getInteger("p_count_late_night");
		//性别
		String sex = jsonObject.getString("sex");
		//年龄
		Integer age = jsonObject.getInteger("age");
		Integer ctime = jsonObject.getInteger("ctime");
		//同盾七天申请数量
		Integer td_7days = jsonObject.getInteger("td_7days");
		Integer mobile_time = jsonObject.getInteger("mobile_time");
		Integer mobile_avgbee = jsonObject.getInteger("mobile_avgbee");*/
		
		return applyCard;
		
		
	}
	
	/**
	 * 计算结果
	 * @param applyCard
	 * @return
	 */
	public Map<String,String> applyResult(ApplyCard applyCard) {
		//最近3个自然月深夜时间的通话次数
		Integer call_count_late_night_3month = applyCard.getCall_count_late_night_3month();
		//最近3个自然月互通通话的号码数量
		Integer contact_count_mutual_3month = applyCard.getContact_count_mutual_3month();
		//最近6个自然月通话时长小于1分钟的通话次数
		Integer call_count_call_time_less1min_6month = applyCard.getCall_count_call_time_less1min_6month();
		//最近6个自然月通话次数
		Integer call_count_6month = applyCard.getCall_count_6month();
		//最近6个自然月通话次数大于等于10的号码数量
		Integer contact_count_call_count_over10_6month = applyCard.getContact_count_call_count_over10_6month();
		//最近6个自然月通话时长大于等于10分钟的通话次数
		Integer call_count_call_time_over10min_6month = applyCard.getCall_count_call_time_over10min_6month();
		//月主叫通话次数
		Integer call_count_active = applyCard.getCall_count_active();
		//月主叫通话的号码数量
		Integer contact_count_active = applyCard.getContact_count_active();
		//月深夜通话次数
		Integer call_count_late_night = applyCard.getCall_count_late_night();
		//月通话次数
		Integer call_count = applyCard.getCall_count();
		//性别
		String sex = applyCard.getSex();
		//年龄
		Integer age = applyCard.getAge();
		//注册时间
		Integer ctime = TimeUtils.getHour(applyCard.getCtime());
		//同盾七天申请数量
		Integer td_7days = applyCard.getTd_7days();
		//入网时长
		Integer mobile_net_age = applyCard.getMobile_net_age();
		//近三个月平均话费
		Double consume_amount_3month = applyCard.getConsume_amount_3month()/3;
		//近6月通话时长大于10分钟的通话次数占比
		Double p_call_count_call_time_less1min_6month = (double)call_count_call_time_less1min_6month/call_count_6month;
		//月深夜通话次占比
		Double p_count_late_night = (double)call_count_late_night/call_count;
		//得分
		int score = 0;
		if(call_count_late_night_3month<=2) {
			score += -1.015470281;
		}else if(call_count_late_night_3month<=9) {
			score += 2.221248233;
		}else if(call_count_late_night_3month<=28) {
			score += 4.351965611;
		}else if(call_count_late_night_3month<=29) {
			score += 1.553524558;
		}else if(call_count_late_night_3month<=71) {
			score += -9.837727445;
		}else {
			score += 2.368740454;
		}
		
		if(contact_count_mutual_3month<=15) {
			score += 8.911560431;
		}else if(contact_count_mutual_3month<=36) {
			score += 5.288296098;
		}else if(contact_count_mutual_3month<=37) {
			score += -0.87095698;
		}else if(contact_count_mutual_3month<=75) {
			score += 3.461827198;
		}else if(contact_count_mutual_3month>75) {
			score += 10.43095777;
		}
		
		if(call_count_call_time_less1min_6month<=529) {
			score += 8.097702474;
		}else if(call_count_call_time_less1min_6month<=940) {
			score += 5.059098663;
		}else if(call_count_call_time_less1min_6month<=1022) {
			score += -0.481080821;
		}else if(call_count_call_time_less1min_6month>1022) {
			score += 3.270483524;
		}
		
		if(call_count_6month<=428) {
			score += 9.691022073;
		}else if(call_count_6month<=1306) {
			score += 5.981689014;
		}else if(call_count_6month<=1576) {
			score += 2.924388953;
		}else if(call_count_6month<=1577) {
			score += 1.553524558;
		}else if(call_count_6month>1577) {
			score += 2.40300794;
		}
		
		if(contact_count_call_count_over10_6month<=23) {
			score += 5.598247826;
		}else if(contact_count_call_count_over10_6month<=24) {
			score += -6.142276175;
		}else if(contact_count_call_count_over10_6month<=38) {
			score += 4.248718435;
		}else if(contact_count_call_count_over10_6month<=57) {
			score += 3.385320124;
		}else if(contact_count_call_count_over10_6month>57) {
			score += 4.952345997;
		}
		
		if(call_count_call_time_over10min_6month<=20) {
			score += 5.718781865;
		}else if(call_count_call_time_over10min_6month<=21) {
			score += -5.597575061;
		}else if(call_count_call_time_over10min_6month>21) {
			score += -3.725893799;
		}
		
		if(call_count_active<=73) {
			score += 4.692697244;
		}else if(call_count_active<=74) {
			score += -5.996863925;
		}else if(call_count_active>74) {
			score += 3.37708329;
		}
		
		if(contact_count_active<=23) {
			score += 5.089844918;
		}else if(contact_count_active>23) {
			score += 0.922179077;
		}
		
		if(call_count_late_night<=0) {
			score += 0.662236;
		}else if(call_count_late_night<=2) {
			score += 3.466169863;
		}else if(call_count_late_night<=5) {
			score += 4.447571515;
		}else if(call_count_late_night<=13) {
			score += -6.707977577;
		}else if(call_count_late_night>13) {
			score += 2.572051279;
		}
		
		if(call_count<=156) {
			score += 5.093765839;
		}else if(call_count<=159) {
			score += -5.993207802;
		}else if(call_count>159) {
			score += 3.116699248;
		}
		
		if(sex.equals("男")) {
			score += 4.65483791;
		}else if(sex.equals("女")) {
			score += -3.58530146;
		}
		
		if(age<=33) {
			score += 4.625204601;
		}else if(age<=35) {
			score += 1.922223691;
		}else if(age<=40) {
			score += 0.971037595;
		}else if(age>40) {
			score += 1.638020068;
		}
		
		if(ctime<=11) {
			score += 4.604446852;
		}else if(ctime<=18) {
			score += 1.773900123;
		}else if(ctime>18) {
			score += 3.640124299;
		}
		
		if(td_7days<=4) {
			score += -7.994936249;
		}else if(td_7days<=11) {
			score += 2.396922385;
		}else if(td_7days<=15) {
			score += 3.927952866;
		}else if(td_7days>15) {
			score += 3.888052386;
		}
		
		if(mobile_net_age<=12) {
			score += 15.64847431;
		}else if(mobile_net_age<=21) {
			score += 9.518902825;
		}else if(mobile_net_age<=31) {
			score += 4.718867474;
		}else if(mobile_net_age<=55) {
			score += 2.995604576;
		}else if(mobile_net_age<=92) {
			score += 10.79946916;
		}else if(mobile_net_age<=128) {
			score += 3.854409556;
		}else if(mobile_net_age>128) {
			score += -1.960286316;
		}
		
		if(consume_amount_3month<=412.96) {
			score += 4.011126061;
		}else {
			score += 0.921239727;
		}
		
		if(p_call_count_call_time_less1min_6month<=0.6337) {
			score += 7.035538314;
		}else if(p_call_count_call_time_less1min_6month<=0.6459) {
			score += -4.322782472;
		}else {
			score += 3.573098103;
		}
		
		if(p_count_late_night<=0.0452) {
			score += 0.875879551;
		}else if(p_count_late_night<=0.1085) {
			score += 9.674089733;
		}else {
			score += 8.562596939;
		}
		
		Map<String,String> map = new HashMap<>();
		String grade = "";
		if(score>=-30 && score<=10) {
			grade = "AAA";
		}else if(score<=50) {
			grade = "AA";
		}else if(score<=60) {
			grade = "A";
		}else if(score<=70) {
			grade = "A-";
		}else if(score<=80) {
			grade = "BBB";
		}else if(score<=85) {
			grade = "BB";
		}else if(score<=90) {
			grade = "B";
		}else if(score<=120) {
			grade = "C";
		}
		
		map.put("score", score+"");
		map.put("grade", grade);
		return map;
		
	}
}
