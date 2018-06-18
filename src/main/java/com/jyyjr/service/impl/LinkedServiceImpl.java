package com.jyyjr.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jyyjr.dao.jkdxywdao.JkUserBlackListMapper;
import com.jyyjr.dao.jkdxywdao.JkUserMapper;
import com.jyyjr.dao.jkdxywdao.JkUserRepaymentMapper;
import com.jyyjr.dao.mjdxywdao.MjUserBlackListMapper;
import com.jyyjr.dao.mjdxywdao.MjUserMapper;
import com.jyyjr.dao.mjdxywdao.MjUserRepaymentMapper;
import com.jyyjr.dao.radxywdao.RaUserBlackListMapper;
import com.jyyjr.dao.radxywdao.RaUserMapper;
import com.jyyjr.dao.radxywdao.RaUserRepaymentMapper;
import com.jyyjr.dao.ywdao.UserBlackListMapper;
import com.jyyjr.dao.ywdao.UserMapper;
import com.jyyjr.dao.ywdao.UserRepaymentMapper;
import com.jyyjr.service.LinkedService;
import com.jyyjr.vo.LinkedOverdueVo;

/**
 * @author 作者 jinmin
 * @date 创建时间：2018年6月11日 下午2:41:40
 */
@Service("linkedService")
public class LinkedServiceImpl implements LinkedService {
	
	private final Logger logger = LoggerFactory.getLogger(LinkedServiceImpl.class);
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RaUserMapper raUserMapper;
	@Autowired
	private JkUserMapper jkUserMapper;
	@Autowired
	private MjUserMapper mjUserMapper;
	@Autowired
	private UserRepaymentMapper userRepaymentMapper;
	@Autowired
	private RaUserRepaymentMapper raUserRepaymentMapper;
	@Autowired
	private JkUserRepaymentMapper jkUserRepaymentMapper;
	@Autowired
	private MjUserRepaymentMapper mjUserRepaymentMapper;
	@Autowired
	private UserBlackListMapper userBlackListMapper;
	@Autowired
	private RaUserBlackListMapper raUserBlackListMapper;
	@Autowired
	private JkUserBlackListMapper jkUserBlackListMapper;
	@Autowired
	private MjUserBlackListMapper mjUserBlackListMapper;
	
	/**
	 * 多平台逾期判断
	 * @author 作者 jinmin
	 * @date 创建时间：2018年6月11日 下午3:14:44
	 * @param mobile
	 * @return
	 */
	public Map<String, Object> linkedOverdue(String mobile) {
		Map<String, Object> resultMap = new HashMap<>();
		List<String> overdueMsg = new ArrayList<>();
		if (mobile==null || mobile.isEmpty()) {
			resultMap.put("status", 1002);
			resultMap.put("message", "参数不能为空");
		}
		String gxVid = userMapper.selectVidByMobile(mobile);
		String raVid = raUserMapper.selectVidByMobile(mobile);
		String jkVid = jkUserMapper.selectVidByMobile(mobile);
		String mjVid = mjUserMapper.selectVidByMobile(mobile);
		
		if (gxVid!=null) {
			LinkedOverdueVo linkedOverdueVo = userRepaymentMapper.selectOverdueByVid(gxVid);
			Integer blackStatus = userBlackListMapper.selectUserBlackStatus(gxVid);
			if (linkedOverdueVo!=null) {
				Integer overdueDay = linkedOverdueVo.getOverdue_day();
				Integer status = linkedOverdueVo.getStatus();
				if (overdueDay!=null) {
					if (overdueDay>3 && status==1) {//逾期超三天未还
						//逾期
						overdueMsg.add("共享现金逾期超三天未还");
					}
				}
			}
			if (blackStatus!=null) {
				if (blackStatus==1) {
					//黑名单
					overdueMsg.add("共享现金黑名单用户");
				}
			}
		}
        if (raVid!=null) {
        	LinkedOverdueVo linkedOverdueVo = raUserRepaymentMapper.selectOverdueByVid(raVid);
        	Integer blackStatus = userBlackListMapper.selectUserBlackStatus(raVid);
			if (linkedOverdueVo!=null) {
				Integer overdueDay = linkedOverdueVo.getOverdue_day();
				Integer status = linkedOverdueVo.getStatus();
				if (overdueDay!=null) {
					if (overdueDay>3 && status==1) {//逾期超三天未还
						//逾期
						overdueMsg.add("趣消费逾期超三天未还");
					}
				}
			}
			if (blackStatus!=null) {
				if (blackStatus==1) {
					//黑名单
					overdueMsg.add("趣消费黑名单用户");
				}
			}
		}
        if (jkVid!=null) {
        	LinkedOverdueVo linkedOverdueVo = jkUserRepaymentMapper.selectOverdueByVid(jkVid);
        	Integer blackStatus = userBlackListMapper.selectUserBlackStatus(jkVid);
			if (linkedOverdueVo!=null) {
				Integer overdueDay = linkedOverdueVo.getOverdue_day();
				Integer status = linkedOverdueVo.getStatus();
				if (overdueDay!=null) {
					if (overdueDay>3 && status==1) {//逾期超三天未还
						//逾期
						overdueMsg.add("旅游白卡逾期超三天未还");
					}
				}
			}
			if (blackStatus!=null) {
				if (blackStatus==1) {
					//黑名单
					overdueMsg.add("旅游白卡黑名单用户");
				}
			}
		}
        if (mjVid!=null) {
        	LinkedOverdueVo linkedOverdueVo = mjUserRepaymentMapper.selectOverdueByVid(mjVid);
        	Integer blackStatus = userBlackListMapper.selectUserBlackStatus(mjVid);
			if (linkedOverdueVo!=null) {
				Integer overdueDay = linkedOverdueVo.getOverdue_day();
				Integer status = linkedOverdueVo.getStatus();
				if (overdueDay!=null) {
					if (overdueDay>3 && status==1) {//逾期超三天未还
						//逾期
						overdueMsg.add("其他平台逾期超三天未还");
					}
				}
			}
			if (blackStatus!=null) {
				if (blackStatus==1) {
					//黑名单
					overdueMsg.add("其他平台黑名单用户");
				}
			}
		}
        if (overdueMsg.size()>0) {
        	resultMap.put("isOverdue", true);
		}else {
			resultMap.put("isOverdue", false);
		}
        resultMap.put("status", 1001);
        resultMap.put("overdueMsg", overdueMsg);
		return resultMap; 
	}

}
