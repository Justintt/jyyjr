package com.jyyjr.test;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jyyjr.base.BaseTest;
import com.jyyjr.dao.dxywdao.WarningMapper;
import com.jyyjr.dao.ywdao.UserBorrowMapper;
import com.jyyjr.pojo.Warning;

public class TestWraning extends BaseTest{
	
	@Autowired
	private UserBorrowMapper userBorrowMapper;
	@Autowired
	private WarningMapper warningMapper;
	
	/*@Test
	public void test01() {
		//命中Y0001的用户
		String type = "Y0001";
		List<String> Y0001Vids = userBorrowMapper.selectBorrowInUserWarning();
		List<String> statusVids  = warningMapper.selectWarningByType(type);
		//排除掉status=1的已经存在的用户
		Y0001Vids.removeAll(statusVids);
		for(String vid : Y0001Vids) {
			Integer rowCount = warningMapper.selectWarningCount(vid, type);
			if (rowCount>0) {
				int ctime = (int)(System.currentTimeMillis()/1000);
				Warning warning = new Warning();
				warning.setVid(vid);
				warning.setStatus(1);
				warning.setUpdate_time(ctime);
				warning.setType(type);
				warningMapper.updateWarning(warning);
			}else {
				int ctime = (int)(System.currentTimeMillis()/1000);
				Warning warning = new Warning();
				warning.setVid(vid);
				warning.setCtime(ctime);
				warning.setUpdate_time(ctime);
				warning.setType(type);
				warningMapper.insertWarning(warning);
			}
		}
	}*/
	
	@Test
	public void test02() {
		//获取warning表中已还款或已逾期的用户vid
		List<String> repayOrOverdueVids = userBorrowMapper.selectRepayOrOverdueByMarning();
		for(String vid : repayOrOverdueVids) {
			int update_time = (int)(System.currentTimeMillis()/1000);
			Warning warning = new Warning();
			warning.setVid(vid);
			warning.setStatus(2);
			warning.setUpdate_time(update_time);
			warningMapper.updateWarning(warning);
		}
	}
}
