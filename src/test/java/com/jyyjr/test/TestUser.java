package com.jyyjr.test;




import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jyyjr.base.BaseTest;
import com.jyyjr.dao.ywdao.UserMapper;
import com.jyyjr.pojo.User;

public class TestUser extends BaseTest{
	
	@Autowired
	private UserMapper userMapper;
	
	
	/*@Test
	public void test01() {
		User user = userMapper.selectUserByVid("d3c947898f49846e35f3f60783541d81");
		System.out.println(user.getTruename());
	}
	
	@Test
	public void test02() {
		Map<String, Object> map = userMapper.selectBorrow("1c05b3f980c9ea600ef8219815c1bf58");
		for (Map.Entry<String, Object> entry : map.entrySet()) { 
			  System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
		 }
	}
	
	@Test
	public void test03() {
		Map<String, Object> mapUser = userMapper.selectAgeAndSexByVid("bb9243f18b81a22d6a0cdcc70ca54f3d");
		for(Map.Entry<String, Object> entry : mapUser.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
	}*/
	
	@Test
	public void test04() {
		String province = userMapper.selectProvinceByVid("774604d038b484e5bb524c549fa5bd39");
		if (province.equals("浙江")) {
			System.out.println("浙江地区："+province);
		}else {
			System.out.println("地区:"+province);
		}
	}
}
