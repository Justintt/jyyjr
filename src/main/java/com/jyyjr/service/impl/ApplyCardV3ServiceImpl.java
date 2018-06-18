package com.jyyjr.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jyyjr.common.Message;
import com.jyyjr.dao.ywdao.MobileClearYYMapper;
import com.jyyjr.dao.ywdao.UserMapper;
import com.jyyjr.dao.ywdao.UserWokeVerifyMapper;
import com.jyyjr.dao.zxdao.MobileBillMapper;
import com.jyyjr.dao.zxdao.QhUserCreditTrackMapper;
import com.jyyjr.dao.zxdao.QhUserLoanPlatformMapper;
import com.jyyjr.dao.zxdao.QhUserReliability2bMapper;
import com.jyyjr.dao.zxdao.QhUserRiskHintMapper;
import com.jyyjr.dao.zxdao.RiskContactDetailMapper;
import com.jyyjr.dao.zxdao.TdTbBaseMapper;
import com.jyyjr.dao.zxdao.TdTbOrderListMapper;
import com.jyyjr.service.ApplyCardV3Service;
import com.jyyjr.util.TimeUtils;


@Service("applyCardV3Service")
public class ApplyCardV3ServiceImpl implements ApplyCardV3Service{
	
	private final Logger logger = LoggerFactory.getLogger(ApplyCardV3ServiceImpl.class);
	
	@Autowired
	private TdTbOrderListMapper tbOrderListMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private QhUserCreditTrackMapper qhUserCreditTrackMapper;
	@Autowired
	private MobileClearYYMapper mobileClearYYMapper;
	@Autowired
	private MobileBillMapper mobileBillMapper;
	@Autowired
	private QhUserRiskHintMapper qhUserRiskHintMapper;
	@Autowired
	private QhUserLoanPlatformMapper qhUserLoanPlatformMapper;
	@Autowired
	private QhUserReliability2bMapper qhUserReliability2bMapper;
	@Autowired
	private TdTbBaseMapper tdTbBaseMapper;
	@Autowired
	private RiskContactDetailMapper riskContactDetailMapper;
	@Autowired
	private UserWokeVerifyMapper userWokeVerifyMapper;
	
	
	
	/**
	 * 获取维度数据
	 * @param vid
	 * @return
	 */
	public Map<String, Object> ApplyCardV3Msg(String vid) {
		Map<String, Object> map = new HashMap<>();
		//近6个月平均话费消费
		double bin_fee_avg_m6 =  ((double)mobileBillMapper.selectAvgBillfeeByVid(vid)/100);
		//bin_age,bin_is_marry,bin_education,bin_identity_address
		Map<String, Object> userMap = userMapper.selectUserMsgByVid(vid);
		if (userMap==null) {
			//用户不存在
			map.put("status", 1002);
			map.put("message", "用户不存在");
			return map;
		}
		long bin_age = (long) userMap.get("age");
		String bin_is_marry = (String) userMap.get("ismarry");
		if (bin_is_marry!=null) {
			if (bin_is_marry.equals("已婚已孕")) {
				bin_is_marry = "已婚已育";
			}
			if (bin_is_marry.equals("已婚未孕")) {
				bin_is_marry = "已婚未育";
			}
		}else {
			bin_is_marry = "未填";
		}
		
		String bin_education = (String) userMap.get("degree");
		String bin_identity_address = (String) userMap.get("province");
		//通讯录清洗数据
		Map<String, Object> acaMap = mobileClearYYMapper.selectAcaByVid(vid);
		if (acaMap==null) {
			map.put("status", 1002);
			map.put("message", "通话记录未清洗");
			return map;
		}
		long bin_contact_cnt_clr = (long) acaMap.get("bin_contact_cnt_clr");
		int bin_contact_call_cnt_clr = (int) acaMap.get("bin_contact_call_cnt_clr");
		long bin_call_atv_cnt_clr = (long) acaMap.get("bin_call_atv_cnt_clr");
		long bin_call_cnt_clr = (long) acaMap.get("bin_call_cnt_clr");
		BigDecimal call_atv_rate_clr = (BigDecimal) acaMap.get("bin_call_atv_rate_clr");
		double bin_call_atv_rate_clr  = call_atv_rate_clr.doubleValue();
		int bin_call_ap_number_clr = (int) acaMap.get("bin_call_ap_number_clr");
		int bin_home_status = (int) acaMap.get("bin_home_status");
		//前海最近一笔逾期业务发生的灰度分
		Integer qh_risk_hint = qhUserRiskHintMapper.selectRskScoreByVid(vid);
		int bin_qh_risk_hint = qh_risk_hint==null?0:qh_risk_hint;
		//前海基准日期好信度分
		Integer qh_credooScoreM0 = qhUserCreditTrackMapper.selectCedooScoreM0ByVid(vid);
		int bin_qh_credooScoreM0 = qh_credooScoreM0==null?0:qh_credooScoreM0;
		//cnssAmount,p2pAmount,queryAmtM3
		Map<String, Integer> qhMap = qhUserLoanPlatformMapper.selectCpqByVid(vid);
		int bin_qh_cnssAmount = 0;
		int bin_qh_p2pAmount = 0;
		int bin_qh_queryAmtM3 = 0;
		if (qhMap!=null) {
			bin_qh_cnssAmount = qhMap.get("cnssAmount");
			bin_qh_p2pAmount = qhMap.get("p2pAmount");
			bin_qh_queryAmtM3 = qhMap.get("queryAmtM3");
		}
		//payAbilityScore,virAssetScore
		Map<String, Object> qhpvMap = qhUserReliability2bMapper.selectPvByVid(vid);
		int bin_qh_payAbilityScore = 0;
		int bin_qh_virAssetScore = 0;
		if (qhpvMap!=null) {
			bin_qh_payAbilityScore = Integer.parseInt((String) qhpvMap.get("payAbilityScore"));
			bin_qh_virAssetScore = Integer.parseInt((String) qhpvMap.get("virAssetScore"));
			
		}
		//获取淘宝消费总额
		Double tb_total_purchase_money = tbOrderListMapper.getOrderListMoneyCount(vid);
		double bin_tb_consume_money = tb_total_purchase_money==null?0:tb_total_purchase_money;
		//淘宝有效购物账龄
		Map<String, Integer> tbMap = tbOrderListMapper.queryOrderTimeByVid(vid);
		Integer ctime = tdTbBaseMapper.queryCtimeByVid(vid);
		Integer max_time = 0;
		Integer min_time = 0;
		if (tbMap!=null) {
			max_time = tbMap.get("max_time");
			min_time = tbMap.get("min_time");
		}
		double bin_tb_age = 0;
		if (ctime==null) {
			bin_tb_age = (double)(max_time-min_time)/(60*60*24*30);//转换为月
		}else {
			bin_tb_age = (double)(ctime-min_time)/(60*60*24*30);
		}
		//同盾风险联系人个数
		Integer bin_td_risk_cnt = riskContactDetailMapper.selectRiskCountByVid(vid);
		if (bin_td_risk_cnt==null) {
			bin_td_risk_cnt = 0;
		}
		//前海sourceId字段A信贷逾期风险业务发生的次数
		int bin_qh_n_sourceId = qhUserRiskHintMapper.selectNsourceIdByVid(vid);
		//前海最近一笔信贷逾期业务发生时间距离18年4月份现在的月数
		String dataBuildTime = qhUserRiskHintMapper.selectDataBuildTimeByVid(vid);
		long time = System.currentTimeMillis()/1000;
		String nowTime = TimeUtils.dateTime(time, "yyyy-MM-dd");
		int bin_qh_f_date_count = 0;
		if (dataBuildTime!=null) {
			bin_qh_f_date_count = TimeUtils.differenceMonth(nowTime, dataBuildTime);
		}
		//工作认证所在地省市
		Map<String, String> wMap = userWokeVerifyMapper.selectWorkAddressByVid(vid);
		String bin_work_province = "无";
		if (wMap!=null) {
			String work_location = wMap.get("work_location");
			String work_address = wMap.get("work_address");
			if (work_location.equals("0")) {
				bin_work_province = work_address.substring(0, 2);
			}else {
				bin_work_province = work_location.substring(0, 2);
			}
		}
		map.put("status", 1001);
		map.put("message", "正常");
		map.put("bin_fee_avg_m6", bin_fee_avg_m6);
		map.put("bin_age", bin_age);
		map.put("bin_is_marry", bin_is_marry);
		map.put("bin_education", bin_education);
		map.put("bin_identity_address", bin_identity_address);
		map.put("bin_contact_cnt_clr", bin_contact_cnt_clr);
		map.put("bin_contact_call_cnt_clr", bin_contact_call_cnt_clr);
		map.put("bin_call_atv_cnt_clr", bin_call_atv_cnt_clr);
		map.put("bin_call_cnt_clr", bin_call_cnt_clr);
		map.put("bin_call_atv_rate_clr", bin_call_atv_rate_clr);
		map.put("bin_call_ap_number_clr", bin_call_ap_number_clr);
		map.put("bin_home_status", bin_home_status);
		map.put("bin_qh_risk_hint", bin_qh_risk_hint);
		map.put("bin_qh_credooScoreM0", bin_qh_credooScoreM0);
		map.put("bin_qh_cnssAmount", bin_qh_cnssAmount);
		map.put("bin_qh_p2pAmount", bin_qh_p2pAmount);
		map.put("bin_qh_queryAmtM3", bin_qh_queryAmtM3);
		map.put("bin_qh_payAbilityScore", bin_qh_payAbilityScore);
		map.put("bin_qh_virAssetScore", bin_qh_virAssetScore);
		map.put("bin_tb_consume_money", bin_tb_consume_money);
		map.put("bin_tb_age", bin_tb_age);
		map.put("bin_td_risk_cnt", bin_td_risk_cnt);
		map.put("bin_qh_n_sourceId", bin_qh_n_sourceId);
		map.put("bin_qh_f_date_count", bin_qh_f_date_count);
		map.put("bin_work_province", bin_work_province);
		return map;
	}
	
	/**
	 * 计算结果
	 * @param vid
	 * @return
	 */
	public Message<Map<String, Object>> showApplyCardV3(String vid) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> msgMap = ApplyCardV3Msg(vid);
		int status = (int) msgMap.get("status");
		String message = (String) msgMap.get("message");
		if (status==1002) {
			logger.info(vid+":"+message);
			return new Message<>(Message.FAIL, message);
		}
		double bin_fee_avg_m6 = (double) msgMap.get("bin_fee_avg_m6");
		long bin_age = (long) msgMap.get("bin_age");
		String bin_is_marry = (String) msgMap.get("bin_is_marry");
		String bin_education = (String) msgMap.get("bin_education");
		long bin_contact_cnt_clr = (long) msgMap.get("bin_contact_cnt_clr");
		int bin_contact_call_cnt_clr = (int) msgMap.get("bin_contact_call_cnt_clr");
		long bin_call_atv_cnt_clr = (long) msgMap.get("bin_call_atv_cnt_clr");
		long bin_call_cnt_clr = (long) msgMap.get("bin_call_cnt_clr");
		double bin_call_atv_rate_clr = (double) msgMap.get("bin_call_atv_rate_clr");
		int bin_call_ap_number_clr = (int) msgMap.get("bin_call_ap_number_clr");
		int bin_home_status = (int) msgMap.get("bin_home_status");
		int bin_qh_risk_hint = (int) msgMap.get("bin_qh_risk_hint");
		int bin_qh_f_date_count = (int) msgMap.get("bin_qh_f_date_count");
		int bin_qh_credooScoreM0 = (int) msgMap.get("bin_qh_credooScoreM0");
		int bin_qh_cnssAmount = (int) msgMap.get("bin_qh_cnssAmount");
		int bin_qh_p2pAmount = (int) msgMap.get("bin_qh_p2pAmount");
		int bin_qh_queryAmtM3 = (int) msgMap.get("bin_qh_queryAmtM3");
		int bin_qh_payAbilityScore = (int) msgMap.get("bin_qh_payAbilityScore");
		int bin_qh_virAssetScore = (int) msgMap.get("bin_qh_virAssetScore");
		double bin_tb_consume_money = (double) msgMap.get("bin_tb_consume_money");
		double bin_tb_age = (double) msgMap.get("bin_tb_age");
		int bin_td_risk_cnt = (int) msgMap.get("bin_td_risk_cnt");
		int bin_qh_n_sourceId = (int) msgMap.get("bin_qh_n_sourceId");
		String bin_work_province = (String) msgMap.get("bin_work_province");
		String bin_identity_address = (String) msgMap.get("bin_identity_address");
		double score =0;
		if (bin_fee_avg_m6<=139.73) {
			score +=4.47832906177642;
		}else if (bin_fee_avg_m6<=312.45) {
			score +=6.23921552872504;
		}else if (bin_fee_avg_m6>312.45) {
			score +=0.83101734915437;
		}
		
		if (bin_age<=33) {
			score +=4.48170970707816;
		}else if (bin_age<=38) {
			score +=7.49612333021295;
		}else if (bin_age>38) {
			score +=-3.12682611274144;
		}
		
		if (bin_is_marry.equals("离异")) {
			score += 6.24153366775586;
		}else if (bin_is_marry.equals("未婚")) {
			score += 3.25100314926377;
		}else if (bin_is_marry.equals("已婚未育")) {
			score += 0.248231992974538;
		}else if (bin_is_marry.equals("已婚已育")) {
			score += 5.96073683199928;
		}
		
		if (bin_education.equals("本科")) {
			score += 6.38125703251703;
		}else if (bin_education.equals("初中以下")) {
			score += 7.39053325626148;
		}else if (bin_education.equals("大专") || bin_education.equals("专科")) {
			score += -0.98987452305593;
		}else if (bin_education.equals("高中")) {
			score += -2.79121987159078;
		}else if (bin_education.equals("博士") || bin_education.equals("博士研究生") || bin_education.equals("硕士") || bin_education.equals("硕士研究生")) {
			score += 1.44111695439337;
		}else if (bin_education.equals("中专") || bin_education.equals("高中/中专")) {
			score += 4.91312338571094;
		}
		
		if (bin_contact_cnt_clr<=52) {
			score += 2.6052820714276;
		}else if (bin_contact_cnt_clr<=134) {
			score += 2.74951380675182;
		}else if (bin_contact_cnt_clr<=224) {
			score += 2.08481277643728;
		}else if (bin_contact_cnt_clr<=494) {
			score += 15.6634405071734;
		}else if (bin_contact_cnt_clr>494) {
			score += 10.4945764614099;
		}
		
		if (bin_contact_call_cnt_clr<=10) {
			score += 1.13904366210439;
		}else if (bin_contact_call_cnt_clr<=29) {
			score += 0.780705557055386;
		}else if (bin_contact_call_cnt_clr<=63) {
			score += 4.0652659397507;
		}else if (bin_contact_call_cnt_clr<=124) {
			score += 14.9212480612143;
		}else if (bin_contact_call_cnt_clr>124) {
			score += 11.5921440849783;
		}
		
		if (bin_call_atv_cnt_clr<=203) {
			score += 4.02591022401479;
		}else if (bin_call_atv_cnt_clr<=928) {
			score += 6.07986676853428;
		}else if (bin_call_atv_cnt_clr>928) {
			score += 0.984677668030977;
		}
		
		if (bin_call_cnt_clr<=136) {
			score +=1.8664385267203;
		}else if (bin_call_cnt_clr<=472) {
			score +=1.81534115534419;
		}else if (bin_call_cnt_clr<=1811) {
			score +=3.61028844845836;
		}else if (bin_call_cnt_clr>1811) {
			score +=14.9864572572458;
		}
		
		if (bin_call_atv_rate_clr<=0.4112) {
			score += 5.6178625988165;
		}else if (bin_call_atv_rate_clr<=0.5848) {
			score += 4.36021239589819;
		}else if (bin_call_atv_rate_clr<=0.6432) {
			score += 8.75891180627137;
		}else if (bin_call_atv_rate_clr>0.6432) {
			score += -9.62406250379572;
		}
		
		if (bin_call_ap_number_clr<=5) {
			score += 1.88788568623657;
		}else if (bin_call_ap_number_clr<=14) {
			score += 0.507303057905357;
		}else if (bin_call_ap_number_clr<=40) {
			score += 4.35006296348989;
		}else if (bin_call_ap_number_clr<=47) {
			score += 11.6630180385579;
		}else if (bin_call_ap_number_clr<=72) {
			score += 8.67426862978113;
		}else if (bin_call_ap_number_clr>72) {
			score += 11.3022768043007;
		}
		
		if (bin_home_status<=1) {
			score += 2.98637475612639;
		}else if (bin_home_status<=9) {
			score += 4.73933166682969;
		}else if (bin_home_status<=17) {
			score += 8.08314185105542;
		}else if (bin_home_status<=143) {
			score += 8.1746370338738;
		}else if (bin_home_status>143) {
			score += 8.12896920566878;
		}
		
		if (bin_qh_risk_hint<=10) {
			score += 5.0807732971792;
		}else if (bin_qh_risk_hint<=23) {
			score += 9.7332129957799;
		}else if (bin_qh_risk_hint>23) {
			score += 11.2323794527054;
		}
		
		if (bin_qh_f_date_count<=0) {
			score += 5.07390552717068;
		}else if (bin_qh_f_date_count<=11) {
			score += -10.9602838622338;
		}else if (bin_qh_f_date_count<=23) {
			score += 9.29936527282216;
		}else if (bin_qh_f_date_count>23) {
			score += 16.6558039271827;
		}
		
		if (bin_qh_credooScoreM0<=376) {
			score += 6.46232308660594;
		}else if (bin_qh_credooScoreM0<=432) {
			score += -9.89007233095764;
		}else if (bin_qh_credooScoreM0<=521) {
			score += 3.01354613213015;
		}else if (bin_qh_credooScoreM0<=590) {
			score += -4.2262161862309;
		}else if (bin_qh_credooScoreM0<=602) {
			score += 4.12657032792338;
		}else if (bin_qh_credooScoreM0<=621) {
			score += 6.93151872290876;
		}else if (bin_qh_credooScoreM0>621) {
			score += 8.31491862608006;
		}
		
		if (bin_qh_cnssAmount<=0) {
			score += 6.74921157969559;
		}else if (bin_qh_cnssAmount<=1) {
			score += 8.63689255850014;
		}else if (bin_qh_cnssAmount<=2) {
			score += 5.2170251452377;
		}else if (bin_qh_cnssAmount>2) {
			score += -2.02811810784663;
		}
		
		if (bin_qh_p2pAmount<=14) {
			score += 5.18151384308048;
		}else if (bin_qh_p2pAmount<=26) {
			score += 3.11214920728317;
		}else if (bin_qh_p2pAmount>26) {
			score += 1.82155476137571;
		}
		
		if (bin_qh_queryAmtM3<=7) {
			score += 3.05716277106199;
		}else if (bin_qh_queryAmtM3<=11) {
			score += 2.52644822882572;
		}else if (bin_qh_queryAmtM3<=20) {
			score += 2.03798721671174;
		}else if (bin_qh_queryAmtM3>20) {
			score += 19.7602307681954;
		}
		
		if (bin_qh_payAbilityScore<=0) {
			score += 6.59358398507631;
		}else if (bin_qh_payAbilityScore<=41) {
			score += 1.90466340877727;
		}else if (bin_qh_payAbilityScore>41) {
			score += -0.274251830233067;
		}
		
		if (bin_qh_virAssetScore<=20) {
			score += 6.59260032734983;
		}else if (bin_qh_virAssetScore<=33) {
			score += -8.19807120373542;
		}else if (bin_qh_virAssetScore<=41) {
			score += 6.45760540668138;
		}else if (bin_qh_virAssetScore>41) {
			score += 15.7236016294298;
		}
		
		if (bin_tb_consume_money<=136) {
			score += 3.14063909398187;
		}else if (bin_tb_consume_money<=5263) {
			score += 16.0190260832253;
		}else if (bin_tb_consume_money<=9614) {
			score += -7.1334451897679;
		}else if (bin_tb_consume_money>9614) {
			score += 35.7260771436681;
		}
		
		if (bin_tb_age<=0) {
			score += 3.28606916374632;
		}else if (bin_tb_age>0) {
			score += 16.8207345950125;
		}
		
		if (bin_td_risk_cnt<=0) {
			score += 6.06728097084464;
		}else if (bin_td_risk_cnt<=6) {
			score += 5.01970997312839;
		}else if (bin_td_risk_cnt<=8) {
			score += 7.16701050238644;
		}else if (bin_td_risk_cnt>8) {
			score += -6.31439333032158;
		}
		
		if (bin_qh_n_sourceId<=0) {
			score += 11.4865785418068;
		}else if (bin_qh_n_sourceId>0) {
			score += -3.58018763332009;
		}
		
		if (bin_work_province.equals("安徽")) {
			score += 5.9945168524576;
		}else if (bin_work_province.equals("北京")) {
			score += 16.0327697892737;
		}else if (bin_work_province.equals("福建")) {
			score += 7.29054425074438;
		}else if (bin_work_province.equals("甘肃")) {
			score += -0.163937669824636;
		}else if (bin_work_province.equals("广东")) {
			score += 2.06213917918058;
		}else if (bin_work_province.equals("广西")) {
			score += 1.46084783514165;
		}else if (bin_work_province.equals("贵州")) {
			score += 4.96219934088358;
		}else if (bin_work_province.equals("海南")) {
			score += 4.84995508130866;
		}else if (bin_work_province.equals("河北")) {
			score += -5.41198829502509;
		}else if (bin_work_province.equals("河南")) {
			score += 5.44211917059508;
		}else if (bin_work_province.equals("黑龙")) {
			score += 1.00303696032484;
		}else if (bin_work_province.equals("湖北")) {
			score += 0.166142833649496;
		}else if (bin_work_province.equals("湖南")) {
			score += -4.80862484876133;
		}else if (bin_work_province.equals("吉林")) {
			score += 3.11197904905035;
		}else if (bin_work_province.equals("江苏")) {
			score += 21.4025980980897;
		}else if (bin_work_province.equals("江西")) {
			score += -8.6156325401647;
		}else if (bin_work_province.equals("辽宁")) {
			score += -4.5647322431287;
		}else if (bin_work_province.equals("内蒙")) {
			score += 5.16778736408173;
		}else if (bin_work_province.equals("宁夏")) {
			score += 4.73143885846109;
		}else if (bin_work_province.equals("青海")) {
			score += 29.8322969022064;
		}else if (bin_work_province.equals("山东")) {
			score += 3.62720228633367;
		}else if (bin_work_province.equals("山西")) {
			score += -4.09607186373981;
		}else if (bin_work_province.equals("陕西")) {
			score += 1.45306667300601;
		}else if (bin_work_province.equals("上海")) {
			score += 13.2273847841318;
		}else if (bin_work_province.equals("四川")) {
			score += -2.54954454466947;
		}else if (bin_work_province.equals("天津")) {
			score += 8.11347649761049;
		}else if (bin_work_province.equals("无")) {
			score += 0;
		}else if (bin_work_province.equals("西藏")) {
			score += 2.96140593056187;
		}else if (bin_work_province.equals("新疆")) {
			score += 2.96140593056187;
		}else if (bin_work_province.equals("香港")) {
			score += 4.7127465396527;
		}else if (bin_work_province.equals("澳门")) {
			score += 4.7127465396527;
		}else if (bin_work_province.equals("云南")) {
			score += 3.20073398943965;
		}else if (bin_work_province.equals("浙江")) {
			score += 5.71181674296812;
		}else if (bin_work_province.equals("重庆")) {
			score += 1.48249125972992;
		}
		
		if (bin_identity_address.equals("安徽")) {
			score += 6.18237356065085;
		}else if (bin_identity_address.equals("北京")) {
			score += 31.7740780194024;
		}else if (bin_identity_address.equals("福建")) {
			score += 5.08431740064346;
		}else if (bin_identity_address.equals("甘肃")) {
			score += 0.456393127717664;
		}else if (bin_identity_address.equals("广东")) {
			score += 4.75417680573156;
		}else if (bin_identity_address.equals("广西")) {
			score += 1.11422576902569;
		}else if (bin_identity_address.equals("贵州")) {
			score += 3.86056960058848;
		}else if (bin_identity_address.equals("海南")) {
			score += 4.85179599207374;
		}else if (bin_identity_address.equals("河北")) {
			score += 2.96351668712108;
		}else if (bin_identity_address.equals("河南")) {
			score += 5.43229326294489;
		}else if (bin_identity_address.equals("黑龙江")) {
			score += 5.13684119290148;
		}else if (bin_identity_address.equals("湖北")) {
			score += 1.27635902653248;
		}else if (bin_identity_address.equals("湖南")) {
			score += -3.52796411449014;
		}else if (bin_identity_address.equals("吉林")) {
			score += 7.28532589490766;
		}else if (bin_identity_address.equals("江苏")) {
			score += 28.8050974229764;
		}else if (bin_identity_address.equals("江西")) {
			score += -2.90482184962838;
		}else if (bin_identity_address.equals("辽宁")) {
			score += 2.55784790439531;
		}else if (bin_identity_address.equals("内蒙古")) {
			score += 5.04521128195209;
		}else if (bin_identity_address.equals("宁夏")) {
			score += 6.08032247552054;
		}else if (bin_identity_address.equals("青海")) {
			score += 23.5862239486767;
		}else if (bin_identity_address.equals("山东")) {
			score += 3.66485937184105;
		}else if (bin_identity_address.equals("山西")) {
			score += -1.31222426953955;
		}else if (bin_identity_address.equals("陕西")) {
			score += 6.60049799787262;
		}else if (bin_identity_address.equals("上海")) {
			score += 33.0976700236756;
		}else if (bin_identity_address.equals("四川")) {
			score += -1.23888476882457;
		}else if (bin_identity_address.equals("天津")) {
			score += 10.720008272719;
		}else if (bin_identity_address.equals("西藏")) {
			score += 16.6956449996778;
		}else if (bin_identity_address.equals("新疆")) {
			score += 16.6956449996778;
		}else if (bin_identity_address.equals("云南")) {
			score += 7.04031221260724;
		}else if (bin_identity_address.equals("浙江")) {
			score += 15.0434255906075;
		}else if (bin_identity_address.equals("重庆")) {
			score += -1.64424968890886;
		}
		String grade = null;
		if (score>165) {
			grade = "AAA";
		}else if (score>145 && score<=165) {
			grade = "AA";
		}else if (score>130 && score<=145) {
			grade = "A";
		}else if (score>118 && score<=130) {
			grade = "A-";
		}else if (score>105 && score<=118) {
			grade = "BBB";
		}else if (score>95 && score<=105) {
			grade = "BB";
		}else if (score>80 && score<=95) {
			grade = "B";
		}else if (score<=80) {
			grade = "C";
		}
		resultMap.put("score", score);
		resultMap.put("grade", grade);
		logger.info(vid+":计算成功");
		return new Message<>(Message.SUCCESS, message,resultMap);
	}
	

}
