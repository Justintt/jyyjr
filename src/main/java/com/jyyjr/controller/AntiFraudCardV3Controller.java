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
import com.jyyjr.service.AntiFraudService;
import com.jyyjr.vo.AntiFraudMsg;

@Controller
public class AntiFraudCardV3Controller {
	
	private final Logger logger = LoggerFactory.getLogger(AntiFraudCardV3Controller.class);
	
	@Autowired
	private AntiFraudService antiFraudService;
	
	/**
	 * 获取反欺诈3.01数据
	 * @param vid
	 * @return
	 */
	@RequestMapping(value="/getAntiFraudMsg" , method = RequestMethod.GET)
	@ResponseBody
	public Message<AntiFraudMsg> getAntiFraudMsg(String vid){
		AntiFraudMsg antiFraudMsg = antiFraudService.getAntiFraudMsg(vid);
		if (antiFraudMsg.getStatus()==1002) {
			return new Message<>(Message.FAIL, antiFraudMsg.getMessage());
		}
		return  new Message<>(Message.SUCCESS, antiFraudMsg.getMessage(),antiFraudMsg);	
	}
	
	/**
	 * 反欺诈接口
	 * @param vid
	 * @return
	 */
	@RequestMapping(value="/getAntiFraudCard" , method = RequestMethod.GET)
	@ResponseBody
	public Message<Map<String, Object>> getAntiFraudCard(String vid){
		logger.info(vid+":反欺诈301接口");
		Message<Map<String, Object>> message = antiFraudService.getAntiFraudCard(vid);
		return 	message;
	}
	
}
