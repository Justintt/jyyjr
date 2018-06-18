package com.jyyjr.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jyyjr.common.Message;
import com.jyyjr.service.ClusterService;
import com.jyyjr.service.impl.ClusterServiceImpl;

/**
 * 聚类接口
 * @author 作者 jinmin
 * @date 创建时间：2018年6月4日 下午12:06:07
 */
@Controller
public class ClusterController {
	
	private final Logger logger =  LoggerFactory.getLogger(ClusterController.class);

	@Autowired
	private ClusterService clusterService;
	
	@RequestMapping(value = "/getClusterMsg",method = RequestMethod.GET )
	@ResponseBody
	public Message<Map<String, Object>> test(String vid) {
		Map<String, Object> map = clusterService.getClusterMsg(vid);
		return new Message<>(Message.SUCCESS, "成功",map);
	}
	
	@RequestMapping(value = "/bbb",method = RequestMethod.GET )
	@ResponseBody
	public Message<List<String>> test02(String vid) {
		List<String> list = clusterService.getMobileBook(vid);
		return new Message<>(Message.SUCCESS, "成功",list);
	}
	
	/**
	 * 聚类接口
	 * @param vid
	 * @return
	 */
	@RequestMapping(value = "/getCluster",method = RequestMethod.GET )
	@ResponseBody
	public Message<Map<String, Object>> getCluster(String vid) {
		long sTime = System.currentTimeMillis();
		Message<Map<String, Object>> map = clusterService.clusterResult(vid);
		long eTime = System.currentTimeMillis();
		logger.info(vid+"：耗时-->"+(eTime-sTime));
		return map;
	}
	
}
