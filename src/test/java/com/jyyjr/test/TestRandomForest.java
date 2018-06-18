package com.jyyjr.test;

import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jyyjr.base.BaseTest;
import com.jyyjr.service.RandomForestService;

public class TestRandomForest extends BaseTest{
	
	@Autowired
	private RandomForestService randomForestService;
	
	@Test
	public void test01() {
		Map<String, Object> map = randomForestService.selectUserMsg("1c05b3f980c9ea600ef8219815c1bf58");
		if (map.get("status").equals(1001)) {
			System.out.println("成功");
		}
		System.out.println(map.get("status"));
	}

}
