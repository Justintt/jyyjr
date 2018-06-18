package com.jyyjr.service;

import java.util.Map;

import com.jyyjr.common.Message;
import com.jyyjr.vo.AntiFraudMsg;

public interface AntiFraudService {
	
	/**
	 * 获取反欺诈3.01数据
	 * @param vid
	 * @return
	 */
	AntiFraudMsg getAntiFraudMsg(String vid);
	
	/**
	 * 反欺诈3.01结果
	 * @param vid
	 * @return
	 */
	Message<Map<String, Object>> getAntiFraudCard(String vid);

}
