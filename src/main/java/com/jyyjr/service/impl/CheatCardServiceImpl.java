package com.jyyjr.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.jyyjr.common.Message;
import com.jyyjr.service.CheatCardService;
import com.jyyjr.util.HttpUtils;
import com.jyyjr.vo.CheatCard;


@Service("cheatCardService")
public class CheatCardServiceImpl implements CheatCardService{
	
	private static final Logger logger = LoggerFactory.getLogger(CheatCardServiceImpl.class);
	
	public Message<Map<String,String>> getCheatCard(String jsonData){
		JSONObject jsonDataStr = JSONObject.parseObject(jsonData);
		String province = jsonDataStr.getString("province")==null?"":jsonDataStr.getString("province");
		String ismarry = jsonDataStr.getString("ismarry")==null?"":jsonDataStr.getString("ismarry");
		String age = jsonDataStr.getString("age")==null?"0":jsonDataStr.getString("age");
		//调用接口得到清洗后的通讯录数据
		String jsonStr = HttpUtils.postBody("http://fk.jyyjrfw.com/Interface/credit", jsonData);
		JSONObject jsonObject = JSONObject.parseObject(jsonStr);
		String status = jsonObject.getString("status");
		
		if(status.equals("1001")) {
			JSONObject resultJson = JSONObject.parseObject(jsonObject.getString("result"));
			CheatCard cheatCard  = JSONObject.toJavaObject(resultJson, CheatCard.class);
			cheatCard.setProvince(province);
			cheatCard.setIsmarry(ismarry);
			cheatCard.setAge(Integer.parseInt(age));
			Map<String,String> map = new HashMap<>();
			String score = getScore(cheatCard);
			map.put("score", score);
			return new Message<>(Message.SUCCESS,"成功",map);
		}else {
			return new Message<>(Message.FAIL,"缺少参数");
		}
	}
	
	
	
	/**
	 * 计算反欺诈得分
	 * @param cheatCard
	 * @return
	 */
	public String getScore(CheatCard cheatCard) {
		double score =0.0;
		Integer apply_one_mbook_call = cheatCard.getBin_apply_one_mbook_call();
		Integer ex_two_to_three_month_action_count = cheatCard.getBin_ex_two_to_three_month_action_count();
		Integer unex_mobile_count = cheatCard.getBin_unex_mobile_count();
		Integer ex_two_to_three_month_passive_count = cheatCard.getBin_ex_two_to_three_month_passive_count();
		Integer ex_one_month_action_count = cheatCard.getBin_ex_one_month_action_count();
		Double not_in_mobile_all_two_to_three_rate = cheatCard.getBin_not_in_mobile_all_two_to_three_rate();
		Integer cl_apply_one_mbook_call_three = cheatCard.getBin_cl_apply_one_mbook_call_three();
		Double clex_one_month_bjavg_call_time = cheatCard.getBin_clex_one_month_bjavg_call_time();
		Integer ex_mobile_count = cheatCard.getBin_ex_mobile_count();
		Double unex_mobile_call_count_p = cheatCard.getBin_unex_mobile_call_count_p();
		Double not_in_mobile_zj_two_to_three_rate = cheatCard.getBin_not_in_mobile_zj_two_to_three_rate();
		Integer clex_two_to_three_month_number_count = cheatCard.getBin_clex_two_to_three_month_number_count();
		Integer clex_two_to_three_month_passive_count = cheatCard.getBin_clex_two_to_three_month_passive_count();
		Integer clex_one_month_passive_count = cheatCard.getBin_clex_one_month_passive_count();
		Integer clex_one_month_action_count = cheatCard.getBin_clex_one_month_action_count();
		Integer unex_mobile_call_count = cheatCard.getBin_unex_mobile_call_count();
		Integer apply_two_to_three_mbook_call = cheatCard.getBin_apply_two_to_three_mbook_call();
		Double not_in_mobile_zj_one_month_rate = cheatCard.getBin_not_in_mobile_zj_one_month_rate();
		Integer clex_mobile_in_one_month_call_count = cheatCard.getBin_clex_mobile_in_one_month_call_count();
		String province = cheatCard.getProvince();
		String ismarry = cheatCard.getIsmarry();
		Integer age = cheatCard.getAge();
		
		//用户申请日前1个月(30天），未清洗的通讯录号码的通话个数
		if(apply_one_mbook_call<=3) {
			score += 10.9233369130831;
		}else if(apply_one_mbook_call<=7) {
			score += 10.2332834179654;
		}else if(apply_one_mbook_call<=9) {
			score += 5.33396064528705;
		}else if(apply_one_mbook_call<=19) {
			score += 3.04292460248682;
		}else if(apply_one_mbook_call<=27) {
			score += 1.41367999950473;
		}else if(apply_one_mbook_call<=37) {
			score += -1.36577103862521;
		}else if(apply_one_mbook_call<=55) {
			score += -0.155350058363171;
		}else if(apply_one_mbook_call>55) {
			score += -2.75763824735258;
		}
		//user表的用户city-地区
		if(province.contains("安徽")) {
			score += 0.417448239930454;
		}else if(province.contains("北京")) {
			score += -16.3963322754879;
		}else if(province.contains("福建")) {
			score += 3.19941522315882;
		}else if(province.contains("甘肃")) {
			score += 3.37781511364057;
		}else if(province.contains("广东")) {
			score += 3.71238405108259;
		}else if(province.contains("广西")) {
			score += 8.53567123076658;
		}else if(province.contains("贵州")) {
			score += 4.53662483286658;
		}else if(province.contains("海南")) {
			score += -0.819072168638801;
		}else if(province.contains("河北")) {
			score += 4.48623661723984;
		}else if(province.contains("河南")) {
			score += 1.98974511458993;
		}else if(province.contains("黑龙江")) {
			score += 1.59381677587266;
		}else if(province.contains("湖北")) {
			score += 3.98063041852921;
		}else if(province.contains("湖南")) {
			score += 4.05189952987373;
		}else if(province.contains("吉林")) {
			score += 1.7174723072458;
		}else if(province.contains("江苏")) {
			score += -0.623311487167244;
		}else if(province.contains("江西")) {
			score += 1.56619957669091;
		}else if(province.contains("辽宁")) {
			score += 2.05934191368413;
		}else if(province.contains("内蒙古")) {
			score += 3.23117903985147;
		}else if(province.contains("宁夏")) {
			score += 5.12777768078143;
		}else if(province.contains("青海")) {
			score += 0.595399763174381;
		}else if(province.contains("山东")) {
			score += 2.35248386238761;
		}else if(province.contains("山西")) {
			score += 3.66800997393886;
		}else if(province.contains("陕西")) {
			score += 3.59580680463789;
		}else if(province.contains("上海")) {
			score += -17.0184335446578;
		}else if(province.contains("四川")) {
			score += 8.73647236858321;
		}else if(province.contains("天津")) {
			score += -2.84690782630757;
		}else if(province.contains("新疆")) {
			score += -4.99241378371276;
		}else if(province.contains("云南")) {
			score += 3.37098462560414;
		}else if(province.contains("浙江")) {
			score += 0.645919902357051;
		}else if(province.contains("重庆")) {
			score += 6.7672169680365;
		}
		//通话记录中主叫异常号码的个数（[2,3]个月）
		if(ex_two_to_three_month_action_count<=6) {
			score += 1.16451066675469;
		}else if(ex_two_to_three_month_action_count<=14) {
			score += 2.6196066073327;
		}else if(ex_two_to_three_month_action_count>14) {
			score += 6.85620729240123;
		}
		//清洗后的通讯录正常号码个数
		if(unex_mobile_count<=50) {
			score += 8.22351664452826;
		}else if(unex_mobile_count<=67) {
			score += 6.49077407798419;
		}else if(unex_mobile_count<=150) {
			score += 3.54763622024486;
		}else if(unex_mobile_count<=492) {
			score += 1.62826810459928;
		}else if(unex_mobile_count>492) {
			score += -5.03233654549751;
		}
		//通话记录中被叫异常号码的个数（[2,3]个月）
		if(ex_two_to_three_month_passive_count<=1) {
			score += 4.87445507282819;
		}else if(ex_two_to_three_month_passive_count>1) {
			score += 0.754223168667389;
		}
		//异常号码主叫总数量(申请日前一个月)
		if(ex_one_month_action_count==0) {
			score += 0.599916995794704;
		}else if(ex_one_month_action_count<=5) {
			score += 2.02159097571896;
		}else if(ex_one_month_action_count>5) {
			score += 5.02167367326473;
		}
		//不在通讯录的主叫+被叫号码个数（2个月[2,3])/未清洗的主叫通话记录号码总个数
		if(not_in_mobile_all_two_to_three_rate<=0.1697) {
			score += 4.43157699954147;
		}else if(not_in_mobile_all_two_to_three_rate<=0.2188) {
			score += 2.81259481696398;
		}else if(not_in_mobile_all_two_to_three_rate<=0.3528) {
			score += 0.447951335984975;
		}else if(not_in_mobile_all_two_to_three_rate<=0.3956) {
			score += 2.84811257063469;
		}else if(not_in_mobile_all_two_to_three_rate<=0.4726) {
			score += 6.49018412923178;
		}else if(not_in_mobile_all_two_to_three_rate<=1) {
			score += 14.8403709070762;
		}
		//用户申请日前1个月，清洗后的通话记录中连续3天无活跃的次数
		if(cl_apply_one_mbook_call_three==0) {
			score += 1.78039947697769;
		}else if(cl_apply_one_mbook_call_three>0) {
			score += 10.589137289201;
		}
		//清洗异常码号后的长近1个月被叫平均通话时长
		if(clex_one_month_bjavg_call_time<=82.6186) {
			score += 2.18421336194371;
		}else if(clex_one_month_bjavg_call_time<=135.6814) {
			score += 4.61810025637826;
		}else if(clex_one_month_bjavg_call_time>135.6814) {
			score += 8.50375373053807;
		}
		//通讯录异常字段个数
		if(ex_mobile_count<=1) {
			score += 4.70203462640226;
		}else if(ex_mobile_count<3) {
			score += 2.91870706402691;
		}else if(ex_mobile_count>3) {
			score += -2.13690377640008;
		}
		//清洗后的通讯录互通号码个数占比
		if(unex_mobile_call_count_p<=0.3384) {
			score += 2.25261805918119;
		}else if(unex_mobile_call_count_p<=1) {
			score += 6.9506118883325;
		}
		//user表的用户自填的婚姻状况ismarry
		if(ismarry.equals("离异")) {
			score += 0.25767314905073;
		}else if(ismarry.equals("未婚")) {
			score += 4.15121521515081;
		}else if(ismarry.equals("已婚未育")) {
			score += 3.82311670090121;
		}else if(ismarry.equals("已婚未孕")) {
			score += 0.575352375424839;
		}else if(ismarry.equals("已婚已育")) {
			score += 3.11860369115726;
		}else if(ismarry.equals("已婚已孕")) {
			score += -0.069858069066282;
		}else {
			score += -0.1974673460153;
		}
		//年龄
		if(age<=26) {
			score += 4.18241618163408;
		}else if(age<=34) {
			score += 3.19323590131921;
		}else if(age<=40) {
			score += -0.313375779816685;
		}else if(age>40) {
			score += 0.297929813877031;
		}
		//不在通讯录的主叫号码个数（2个月[2,3])/未清洗的主叫通话记录号码总个数
		if(not_in_mobile_zj_two_to_three_rate<=0.2882) {
			score += 1.15844055960694;
		}else if(not_in_mobile_zj_two_to_three_rate<=0.4178) {
			score += 6.34650485042985;
		}else if(not_in_mobile_zj_two_to_three_rate<=1) {
			score += 11.6782674069332;
		}
		//清洗后的通话记录中近[2,3]个月的通话数量
		if(clex_two_to_three_month_number_count<=53) {
			score += 5.74340372719128;
		}else if(clex_two_to_three_month_number_count<=88) {
			score += 4.39358628489527;
		}else if(clex_two_to_three_month_number_count<=212) {
			score += 1.14445056582516;
		}else if(clex_two_to_three_month_number_count>212) {
			score += 1.29297315510172;
		}
		//清洗后的通话记录中近[2,3]个月的被叫数量
		if(clex_two_to_three_month_passive_count<=25) {
			score += 5.08917442469514;
		}else if(clex_two_to_three_month_passive_count<=64) {
			score += 10.0411124305589;
		}else if(clex_two_to_three_month_passive_count<=237) {
			score += 3.81192484466961;
		}else if(clex_two_to_three_month_passive_count<=509) {
			score += 2.49324037415042;
		}else if(clex_two_to_three_month_passive_count>509) {
			score += -0.444090107224336;
		}
		//清洗后的通话记录中近1个月的被叫数量
		if(clex_one_month_passive_count==0) {
			score += 2.1941020187527;
		}else if(clex_one_month_passive_count<=33) {
			score += 14.7130376202766;
		}else if(clex_one_month_passive_count<=53) {
			score += 7.04839255516918;
		}else if(clex_one_month_passive_count<=99) {
			score += 3.14581516778323;
		}else if(clex_one_month_passive_count<=403) {
			score += 1.57289589305117;
		}else if(clex_one_month_passive_count>403) {
			score += -5.58065234300803;
		}
		//清洗后的通话记录中近1个月的主叫数量
		if(clex_one_month_action_count==0) {
			score += 1.89600032905925;
		}else if(clex_one_month_action_count<=17) {
			score += 10.1351473219082;
		}else if(clex_one_month_action_count<=53) {
			score += 5.57407739742737;
		}else if(clex_one_month_action_count<=166) {
			score += 2.84633966896224;
		}else if(clex_one_month_action_count<=346) {
			score += 1.01227923788438;
		}else if(clex_one_month_action_count>346) {
			score += -2.79678269136496;
		}
		//清洗后的通讯录中互通号码的个数
		if(unex_mobile_call_count<=5) {
			score += 8.48298780504906;
		}else if(unex_mobile_call_count<=12) {
			score += 7.7615048212506;
		}else if(unex_mobile_call_count<=18) {
			score += 4.88659748351327;
		}else if(unex_mobile_call_count<=30) {
			score += 2.67782817568309;
		}else if(unex_mobile_call_count<=47) {
			score += 0.488047572829954;
		}else if(unex_mobile_call_count<=68) {
			score += -2.86813393255926;
		}else if(unex_mobile_call_count>68) {
			score += -1.05366258062885;
		}
		//用户申请日前2-3个月（60天），未清洗的通讯录号码的通话个数
		if(apply_two_to_three_mbook_call<=5) {
			score += 10.373839261027;
		}else if(apply_two_to_three_mbook_call<=14) {
			score += 7.67464984440054;
		}else if(apply_two_to_three_mbook_call<=20) {
			score += 4.2685817247238;
		}else if(apply_two_to_three_mbook_call<=38) {
			score += 2.65784382071223;
		}else if(apply_two_to_three_mbook_call<=71) {
			score += -1.16510689174897;
		}else if(apply_two_to_three_mbook_call>71) {
			score += -7.00555924051078;
		}
		//不在通讯录的主叫号码个数（1个月)/未清洗的主叫通话记录号码总个数
		if(not_in_mobile_zj_one_month_rate<=0.1784) {
			score += 1.91938506316078;
		}else if(not_in_mobile_zj_one_month_rate<=1) {
			score += 6.02965962975435;
		}
		//清洗后的通讯录正常号码近一个月通话个数
		if(clex_mobile_in_one_month_call_count<=14) {
			score += 10.1327510629446;
		}else if(clex_mobile_in_one_month_call_count<=43) {
			score += 7.7084044573902;
		}else if(clex_mobile_in_one_month_call_count<=135) {
			score += 3.28087301593707;
		}else if(clex_mobile_in_one_month_call_count<=451) {
			score += 2.17270295547008;
		}else if(clex_mobile_in_one_month_call_count>451) {
			score += -3.6440243558321;
		}
		return score+"";
	}
	
	
	
	
}
