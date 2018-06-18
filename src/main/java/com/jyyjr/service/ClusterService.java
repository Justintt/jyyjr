package com.jyyjr.service;

import java.util.List;
import java.util.Map;

import com.jyyjr.common.Message;

public interface ClusterService {
	
	
	
	List<String> getMobileBook(String vid);
	
	/**
	 * 聚类数据
	 * @param vid
	 * @return
	 */
	Map<String, Object> getClusterMsg(String vid);
	
	/**
	 * 聚类结果
	 * @param vid
	 * @return
	 */
	Message<Map<String, Object>> clusterResult(String vid);

}
