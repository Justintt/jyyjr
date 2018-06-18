package com.jyyjr.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jyyjr.common.Message;
import com.jyyjr.service.RandomForestService;

@Controller
public class RandomForestController {
	@Autowired
	private RandomForestService randomForestService;
	
	@RequestMapping(value="/randomForest")
	@ResponseBody
	public Message<Map<String, Object>> showRandomForest(String vid){
		return randomForestService.randomForestResult(vid);
	}
}
