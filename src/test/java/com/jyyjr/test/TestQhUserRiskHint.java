package com.jyyjr.test;

import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jyyjr.base.BaseTest;
import com.jyyjr.dao.zxdao.QhUserReliability2bMapper;
import com.jyyjr.dao.zxdao.QhUserRiskHintMapper;
import com.jyyjr.util.TimeUtils;

public class TestQhUserRiskHint extends BaseTest{
	
	@Autowired
	private QhUserRiskHintMapper qhUserRiskHintMapper;
	@Autowired
	private QhUserReliability2bMapper qhUserReliability2bMapper;
	
	/*@Test
	public void Test01() {
		String dataBuildTime = qhUserRiskHintMapper.selectDataBuildTimeByVid("3daad20fbbaa11e77e076f7eb9fde3bc");
		int bin_qh_f_date_count = 0;
		if (dataBuildTime!=null) {
			bin_qh_f_date_count = TimeUtils.differenceMonth("2018-3-31", dataBuildTime);
		}
		System.out.println(bin_qh_f_date_count);
	}*/
	
	@Test
	public void test02() {
		Map<String, Object> qhpvMap = qhUserReliability2bMapper.selectPvByVid("6844a5f933c1fb893bb4dcdb22049c6e");
		int bin_qh_payAbilityScore = 0;
		int bin_qh_virAssetScore = 0;
		if (qhpvMap!=null) {
			bin_qh_payAbilityScore = Integer.parseInt((String) qhpvMap.get("payAbilityScore"));
			bin_qh_virAssetScore = Integer.parseInt((String) qhpvMap.get("virAssetScore"));
			
		}
		
		System.out.println(bin_qh_payAbilityScore+":"+bin_qh_virAssetScore);
	}
	
	


}
