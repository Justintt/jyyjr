package com.jyyjr.service;

import java.util.Map;

import com.jyyjr.common.Message;

public interface RandomForestService {
	
	Map<String, Object> selectUserMsg(String vid);
	
	/**
	 * 随机深林结果
	 * @param vid
	 * @return
	 */
	Message<Map<String, Object>> randomForestResult(String vid);

}
