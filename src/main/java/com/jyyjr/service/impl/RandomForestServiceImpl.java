package com.jyyjr.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.dmg.pmml.FieldName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jyyjr.common.Message;
import com.jyyjr.common.ModelInvoker;
import com.jyyjr.dao.ywdao.MobileClearYYMapper;
import com.jyyjr.dao.ywdao.UserMapper;
import com.jyyjr.dao.zxdao.TdDataMapper;
import com.jyyjr.dao.zxdao.TdriskReportMapper;
import com.jyyjr.service.RandomForestService;


@Service("randomForestService")
public class RandomForestServiceImpl implements RandomForestService{
	
	private final Logger logger = LoggerFactory.getLogger(RandomForestServiceImpl.class);
	
	@Autowired
	private TdriskReportMapper tdriskReportMapper;
	@Autowired
	private MobileClearYYMapper mobileClearYYMapper;
	@Autowired
	private TdDataMapper tdDataMapper;
	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 获取用户特征维度
	 * @param vid
	 * @return
	 */
	public Map<String, Object> selectUserMsg(String vid) {
		Integer tdScore = tdriskReportMapper.selectTdScoreByVid(vid);
		Map<String, Object> mapClear = mobileClearYYMapper.selectMobileClearYYByVid(vid);
		Integer tdSevenDay = tdDataMapper.selectTdSevenDayByVid(vid);
		Map<String, Object> mapUser = userMapper.selectAgeAndSexByVid(vid);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (mapUser==null) {
			resultMap.put("status", 1002);
			resultMap.put("message", "用户不存在");
			logger.info("vid:"+vid+",用户不存在");
			return resultMap;
		}
		if (mapClear==null) {
			resultMap.put("status", 1002);
			resultMap.put("message", "通讯录未清洗");
			logger.info("vid:"+vid+",用户通讯录未清洗");
			return resultMap;
		}
		/*if (tdScore==null) {
			resultMap.put("status", 1002);
			resultMap.put("message", "没有同盾分");
			return resultMap;
		}
		if (tdSevenDay==null) {
			resultMap.put("status", 1002);
			resultMap.put("message", "没有同盾七天申请数量");
			return resultMap;
		}*/
		resultMap.put("status", 1001);
		resultMap.put("message", "正常");
		resultMap.put("td_score", tdScore==null?0:tdScore);
		resultMap.put("other_count", mapClear.get("other_count"));
		resultMap.put("unex_mobile__count", mapClear.get("other_count-ex_mobile_count"));
		resultMap.put("apply_one_mbook_call", mapClear.get("apply_one_mbook_call"));
		resultMap.put("apply_two_to_three_mbook_call", mapClear.get("apply_two_to_three_mbook_call"));
		resultMap.put("not_in_mobile_all_one_month_rate", mapClear.get("not_in_mobile_all_one_month_rate"));
		resultMap.put("ex_mobile_count", mapClear.get("ex_mobile_count"));
		resultMap.put("unex_mobile_call_count", mapClear.get("unex_mobile_call_count"));
		resultMap.put("frist_seven_day", tdSevenDay==null?0:tdSevenDay);
		resultMap.put("age", mapUser.get("age"));
		resultMap.put("sex", mapUser.get("sex").equals(1)?"men":"women");
		//打印日志
		/*logger.info("vid:"+vid+"{"+"status:"+resultMap.get("status")+",message:"+resultMap.get("message")+",td_score:"+resultMap.get("td_score")+
				",other_count:"+resultMap.get("other_count")+",unex_mobile__count:"+resultMap.get("unex_mobile__count")+",apply_one_mbook_call:"+
				resultMap.get("apply_one_mbook_call")+",apply_two_to_three_mbook_call:"+resultMap.get("apply_two_to_three_mbook_call")+
				",not_in_mobile_all_one_month_rate:"+resultMap.get("not_in_mobile_all_one_month_rate")+",ex_mobile_count:"+resultMap.get("ex_mobile_count")+
				",unex_mobile_call_count:"+resultMap.get("unex_mobile_call_count")+",frist_seven_day:"+resultMap.get("frist_seven_day")+
				",age:"+resultMap.get("age")+",sex:"+resultMap.get("sex")+"}");*/
		return resultMap;
	}
	
	public Map<FieldName,Object> randomForestParams(Map<String, Object> map) {
		Map<FieldName,Object> resultMap = new HashMap<FieldName, Object>();
		resultMap.put(new FieldName("td_score"), map.get("td_score"));
		resultMap.put(new FieldName("other_count"), map.get("other_count"));
		resultMap.put(new FieldName("unex_mobile__count"), map.get("unex_mobile__count"));
		resultMap.put(new FieldName("apply_one_mbook_call"), map.get("apply_one_mbook_call"));
		resultMap.put(new FieldName("apply_two_to_three_mbook_call"), map.get("apply_two_to_three_mbook_call"));
		resultMap.put(new FieldName("not_in_mobile_all_one_month_rate"), map.get("not_in_mobile_all_one_month_rate"));
		resultMap.put(new FieldName("ex_mobile_count"), map.get("ex_mobile_count"));
		resultMap.put(new FieldName("unex_mobile_call_count"), map.get("unex_mobile_call_count"));
		resultMap.put(new FieldName("frist_seven_day"), map.get("frist_seven_day"));
		resultMap.put(new FieldName("age"), map.get("age"));
		resultMap.put(new FieldName("sex"), map.get("sex"));
		return resultMap;
	}
	
	/**
	 * 随机深林结果
	 * @param vid
	 * @return
	 */
	public Message<Map<String, Object>> randomForestResult(String vid) {
		long sTime = System.currentTimeMillis();
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> userMsgMap = selectUserMsg(vid);
		if (userMsgMap.get("status").equals(1002)) {
			return new Message<>(Message.FAIL, userMsgMap.get("message").toString());
		}
		Map<FieldName,Object> paramsMap = randomForestParams(userMsgMap);
		Map<FieldName, ?> result = null;
		ModelInvoker modelInvoker = ModelInvoker.getInstance();
		result = modelInvoker.invoke(paramsMap);
		
    	Set<FieldName> keySet = result.keySet();  //获取结果的keySet
    	for(FieldName fn : keySet){
    		resultMap.put(fn.toString(), result.get(fn));
    	}
    	resultMap.put("vid", vid);
    	long eTime = System.currentTimeMillis();
    	logger.info("总耗时："+(eTime-sTime));
		return new Message<Map<String, Object>>(Message.SUCCESS, "成功",resultMap);
	}
	
}
