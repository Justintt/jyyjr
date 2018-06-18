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

import com.jyyjr.common.Message;
import com.jyyjr.service.CheatCardService;


@Controller
@RequestMapping(value = "/xycsf")
public class CheatCardController {
	
	private final Logger logger = LoggerFactory.getLogger(CheatCardController.class);
	
	@Autowired
	private CheatCardService cheatCardService;
	
	/**
	 * 反欺诈评分卡接口
	 * @param jsonData
	 * @return
	 */
	@RequestMapping(value = "/getCheatCard", method = RequestMethod.POST )
	@ResponseBody
	public Message<Map<String,String>> getCheatCard(@RequestBody String jsonData){
		logger.info("信用查反欺诈评分卡接口");
		return cheatCardService.getCheatCard(jsonData);
	}

}
