package com.jyyjr.test;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jyyjr.base.BaseTest;
import com.jyyjr.dao.ywdao.UserWokeVerifyMapper;

public class TestUserWork extends BaseTest{
	
	@Autowired
	private UserWokeVerifyMapper userWokeVerifyMapper;
	
	@Test
	public void test01() {
		String workCompany = userWokeVerifyMapper.selectWorkCompany("6844a5f933c1fb893bb4dcdb22049c6e");
		List<String> companyUsers = userWokeVerifyMapper.selectBinCompanyUsers(workCompany);
		int bin_company_users = 0;
		if(companyUsers.size()>0) {
			bin_company_users = companyUsers.size();
		}
		System.out.println("工作用户："+bin_company_users);
	}

}
