package com.jyyjr.service;

import java.util.Map;

import com.jyyjr.common.Message;

public interface ApplyCardV3Service {
	
	/**
	 * 获取维度数据
	 * @param vid
	 * @return
	 */
	Map<String, Object> ApplyCardV3Msg(String vid);
	
	/**
	 * 计算结果
	 * @param vid
	 * @return
	 */
	Message<Map<String, Object>> showApplyCardV3(String vid);

}
