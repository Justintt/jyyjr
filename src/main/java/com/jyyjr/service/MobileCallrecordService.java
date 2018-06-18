package com.jyyjr.service;

import java.util.List;
import java.util.Map;

import com.jyyjr.common.Message;
import com.jyyjr.pojo.MobileCallrecord;

public interface MobileCallrecordService {
	
	Message<List<MobileCallrecord>> getMobileCallrecord(String vid);
	
	/**
	 * 检查k0032
	 * @param vid
	 * @return
	 */
	Message<Map<String, Object>> checkK0032(String vid);

}
