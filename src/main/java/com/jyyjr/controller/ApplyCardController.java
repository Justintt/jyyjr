package com.jyyjr.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jyyjr.common.Message;
import com.jyyjr.service.ApplyCardService;

@Controller
@RequestMapping(value = "/xycsf")
public class ApplyCardController {
	
	private final Logger logger = LoggerFactory.getLogger(ApplyCardController.class);
	
	@Autowired
	private ApplyCardService applyCardService;

	/**
	 * 申请评分卡接口
	 * @param jsonData
	 * @return
	 */
	@RequestMapping(value = "/getApplyCard",method = RequestMethod.POST )
	@ResponseBody
	public Message<Map<String,String>> getApplyCard(@RequestBody String jsonData){
		logger.info("信用查申请评分卡");
		return applyCardService.getApplyCard(jsonData);
	}
	
	/**
	 * 测试接口
	 * @param jsonData
	 * @return
	 */
	@RequestMapping(value = "/getjson",method = RequestMethod.POST )
	@ResponseBody
	public Message<Map<String,String>> test(@RequestBody String jsonData){
		System.out.println(jsonData);
		@SuppressWarnings("unchecked")
		Map<String,String> map = (Map<String, String>) JSON.parse(jsonData);
		return new Message<>(Message.SUCCESS,"成功",map);
	}
}
