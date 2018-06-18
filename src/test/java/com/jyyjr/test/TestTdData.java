package com.jyyjr.test;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jyyjr.base.BaseTest;
import com.jyyjr.dao.zxdao.TdDataMapper;
import com.jyyjr.pojo.TdData;

public class TestTdData extends BaseTest{
	
	@Autowired
	private TdDataMapper tdDataMapper;
	
	@Test
	public void test01() {
		List<TdData> list = tdDataMapper.selectTdDataByVid("bae8f60eee9f515a7f0d26b4ad1f9be4");
		System.out.println(list.get(0).getVid());
	}
	
	@Test
	public void test02() {
		Integer tdSevenDay = tdDataMapper.selectTdSevenDayByVid("bae8f60eee9f515a7f0d26b4ad1f9be4");
		System.out.println(tdSevenDay);
	}

}
