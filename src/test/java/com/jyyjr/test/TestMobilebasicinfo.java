package com.jyyjr.test;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jyyjr.base.BaseTest;
import com.jyyjr.dao.zxdao.MobileBasicinfoMapper;

public class TestMobilebasicinfo extends BaseTest{

	@Autowired
	private MobileBasicinfoMapper mobileBasicinfoMapper;
	
	@Test
	public void test01() {
		int net_age = mobileBasicinfoMapper.selectNetAgeByvid("651d486de306129d94f568c913487a3")==null?0:mobileBasicinfoMapper.selectNetAgeByvid("651d486de306129d94f568c913487a3");
		System.out.println("入网时长："+net_age);
	}
}
