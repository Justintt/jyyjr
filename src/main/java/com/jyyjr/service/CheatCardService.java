package com.jyyjr.service;

import java.util.List;
import java.util.Map;

import com.jyyjr.common.Message;


public interface CheatCardService {

	Message<Map<String,String>> getCheatCard(String jsonData);
	
	
}
