package com.jyyjr.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.jyyjr.base.BaseTest;
import com.jyyjr.dao.dxywdao.WarningMapper;
import com.jyyjr.dao.ywdao.UserBorrowMapper;
import com.jyyjr.dao.ywdao.UserRepaymentMapper;
import com.jyyjr.dao.ywdao.WarningMsgMapper;
import com.jyyjr.pojo.Warning;
import com.jyyjr.util.HttpUtils;
import com.jyyjr.vo.UserRepaymentVo;
import com.sun.corba.se.impl.encoding.CodeSetConversion.CTBConverter;

public class TestWarningMsg extends BaseTest{
	
	@Autowired
	private WarningMsgMapper warningMsgMapper;
	@Autowired
	private UserBorrowMapper userBorrowMapper;
	@Autowired
	private WarningMapper warningMapper;
	@Autowired
	private UserRepaymentMapper userRepaymentMapper;
	
	@Test
	public void test01() {
		
		String type = "Y0006";
		List<String> updateVids = new ArrayList<>();
		List<String> addVids = new ArrayList<>();
		List<String> vids = userRepaymentMapper.selectLastRepay();
		for(String vid : vids) {
			//命中Y0006
			Integer rowCount = warningMapper.selectWarningCount(vid, type);
			if (rowCount>0) {
				updateWarning(vid, type);
				updateVids.add(vid);
			}else {
				insertWarning(vid, type);
				addVids.add(vid);
			}
		}
		System.out.println(addVids.toString());
	}
	
	public boolean emptyMobile(String mobile) {
		try {
			Map<String, String> param = new HashMap<>();
			param.put("apiName", "S9992859");
			param.put("password", "pwd1524430");
			param.put("mobile", mobile);
			String response = HttpUtils.sendPost("https://kh_bd.253.com/feign/apiMobileTest/findByMobile", param,null,null);
			JSONObject jsonObject = JSONObject.parseObject(response);
			String resultMsg = jsonObject.getString("resultMsg");
			if (resultMsg.equals("成功")) {
				String resultObjStr = jsonObject.getString("resultObj");
				JSONObject resultObj = JSONObject.parseObject(resultObjStr);
				int status = resultObj.getIntValue("status");
				if (status==0) {
					return true;
				}else {
					return false;
				}
			}
			
		} catch (Exception e) {
			
		}
		return false;
	}
	
		
	/**
	 * 添加warning
	 * @author 作者 jinmin
	 * @version 创建时间：2018年5月31日 上午10:38:17
	 * @param vid
	 * @param type
	 */
	public void insertWarning(String vid,String type) {
		UserRepaymentVo userRepaymentVo = userRepaymentMapper.selectLastBorrowMsg(vid);
		int repay_time = userRepaymentVo.getRepay_time();
		String borrow_no = userRepaymentVo.getBorrow_no();
		String repayment_no = userRepaymentVo.getRepayment_no();
		int ctime = (int)(System.currentTimeMillis()/1000);
		Warning warning = new Warning();
		warning.setVid(vid);
		warning.setCtime(ctime);
		warning.setUpdate_time(ctime);
		warning.setType(type);
		warning.setRepay_time(repay_time);
		warning.setBorrow_no(borrow_no);
		warning.setRepayment_no(repayment_no);
		warningMapper.insertWarning(warning);
	}
	
	/**
	 * 更新warning
	 * @author 作者 jinmin
	 * @version 创建时间：2018年5月31日 上午10:38:30
	 * @param vid
	 * @param type
	 */
	public void updateWarning(String vid,String type) {
		UserRepaymentVo userRepaymentVo = userRepaymentMapper.selectLastBorrowMsg(vid);
		int repay_time = userRepaymentVo.getRepay_time();
		String borrow_no = userRepaymentVo.getBorrow_no();
		String repayment_no = userRepaymentVo.getRepayment_no();
		int ctime = (int)(System.currentTimeMillis()/1000);
		Warning warning = new Warning();
		warning.setVid(vid);
		warning.setStatus(1);
		warning.setHandle_status(1);
		warning.setUpdate_time(ctime);
		warning.setType(type);
		warning.setRepay_time(repay_time);
		warning.setBorrow_no(borrow_no);
		warning.setRepayment_no(repayment_no);
		warningMapper.updateWarning(warning);
    }

}
