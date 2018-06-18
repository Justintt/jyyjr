package com.jyyjr.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jyyjr.base.BaseTest;
import com.jyyjr.dao.zxdao.TdTbOrderListMapper;

public class TestTdOrderList extends BaseTest{
	
	@Autowired
	private TdTbOrderListMapper tbOrderListMapper;
	
	@Test
	public void test01() {
		double moneyAll = tbOrderListMapper.getOrderListMoneyCount("66440286ef8bccf7ae4a4ccd0d3b671e")==null?0:tbOrderListMapper.getOrderListMoneyCount("66440286ef8bccf7ae4a4ccd0d3b671e");
		System.out.println("淘宝金额："+moneyAll);
	}

}
