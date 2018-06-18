package com.jyyjr.service;

import java.util.Map;

import com.jyyjr.common.Message;


public interface ApplyCardService {

	Message<Map<String,String>> getApplyCard(String jsonData);
	
	
}
