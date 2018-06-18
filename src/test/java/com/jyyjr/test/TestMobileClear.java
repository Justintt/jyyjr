package com.jyyjr.test;

import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jyyjr.base.BaseTest;
import com.jyyjr.dao.ywdao.MobileClearYYMapper;
import com.jyyjr.dao.zxdao.QhUserLoanPlatformMapper;

public class TestMobileClear extends BaseTest{

	@Autowired
	private MobileClearYYMapper mobileClearYYMapper;
	@Autowired
	private QhUserLoanPlatformMapper qhUserLoanPlatformMapper;
	
	/*@Test
	public void Test01() {
		Map<String, Object> map = mobileClearYYMapper.selectMobileClearYYByVid("6fc9136c8984be8e26c1699d00a7f77");
		System.out.println(map);
		if (map==null) {
			System.out.println("kong");
		}
		for (Map.Entry<String, Object> entry : map.entrySet()) { 
			  System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
		}
	}*/
	
	@Test
	public void test02() {
		Map<String, Integer> qhMap = qhUserLoanPlatformMapper.selectCpqByVid("6844a5f933c1fb893bb4dcdb22049c6");
		if (qhMap==null) {
			System.out.println("kong");
		}else {
			Integer bin_qh_cnssAmount = qhMap.get("cnssAmount");
			Integer bin_qh_p2pAmount = qhMap.get("p2pAmount");
			Integer bin_qh_queryAmtM3 = qhMap.get("queryAmtM3");
			System.out.println(bin_qh_cnssAmount);
		}
		
	}
}
