package com.jyyjr.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jyyjr.common.Message;
import com.jyyjr.service.LinkedService;

/**
 * @author 作者 jinmin
 * @date 创建时间：2018年6月11日 下午3:15:53
 */
@Controller
public class LinkedController {
	
	@Autowired
	private LinkedService linkedService;
	
	@RequestMapping(value="/getLinkedOverdue" , method = RequestMethod.GET)
	@ResponseBody
	public Message<Map<String, Object>> getLinkedOverdue(String mobile) {
		Map<String, Object> map = linkedService.linkedOverdue(mobile);
		int status = (int) map.get("status");
		if (status==1001) {
			return new Message<Map<String, Object>>(Message.SUCCESS, "成功",map);
		}
		return new Message<Map<String, Object>>(Message.FAIL, "失败",map);
	}

}
