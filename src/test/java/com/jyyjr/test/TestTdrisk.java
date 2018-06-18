package com.jyyjr.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jyyjr.base.BaseTest;
import com.jyyjr.dao.zxdao.TdriskReportMapper;

public class TestTdrisk extends BaseTest{
	
	@Autowired
	private TdriskReportMapper tdriskReportMapper;
	
	@Test
	public void test01() {
		Integer tdScore = tdriskReportMapper.selectTdScoreByVid("a61ce80f9678a000ea36459728a797b");
		System.out.println(tdScore);
	}

}
