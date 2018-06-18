package com.jyyjr.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jyyjr.base.BaseTest;
import com.jyyjr.dao.zxdao.QhUserCreditTrackMapper;

public class TestQhUserCreditTrack extends BaseTest{

	@Autowired
	private QhUserCreditTrackMapper qhUserCreditTrackMapper;
	
	@Test
	public void test01() {
		Integer qh_cred_score = qhUserCreditTrackMapper.selectCedooScoreM0ByVid("e201824962918ffd7cbab0c0e2a1a7e3");
		int bin_qh_cred_score = qh_cred_score==null?0:qh_cred_score;
		System.out.println("前海好信度分:"+bin_qh_cred_score);
	}
	
}
