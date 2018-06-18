package com.jyyjr.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jyyjr.common.Message;
import com.jyyjr.service.ApplyCardV3Service;

@Controller
public class ApplyCardV3Controller {
	
	private final Logger logger = LoggerFactory.getLogger(ApplyCardV3Controller.class);
	
	@Autowired
	private ApplyCardV3Service applyCardV3Service;
	
	/**
	 * 申请评分卡申请评分卡3.0.1版维度
	 * @param vid
	 * @return
	 */
	@RequestMapping(value = "/applyCardV3Msg",method = RequestMethod.GET )
	@ResponseBody
	public Map<String, Object> getApplyCardV3Msg(String vid){
		return applyCardV3Service.ApplyCardV3Msg(vid);
	}
	
	/**
	 * 申请评分卡申请评分卡3.0.1版
	 * @param vid
	 * @return
	 */
	@RequestMapping(value = "/applyCardV3",method = RequestMethod.GET )
	@ResponseBody
	public Message<Map<String, Object>> getApplyCardV3(String vid){
		logger.info(vid+":申请评分卡申请评分卡3.0.1版");
		Message<Map<String, Object>> message = applyCardV3Service.showApplyCardV3(vid);
		return message;
	}

}
