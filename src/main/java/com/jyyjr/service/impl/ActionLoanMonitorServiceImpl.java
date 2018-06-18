package com.jyyjr.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.jyyjr.dao.dxywdao.UserWarningMapper;
import com.jyyjr.dao.dxywdao.WarningMapper;
import com.jyyjr.dao.ywdao.UserBorrowMapper;
import com.jyyjr.dao.ywdao.UserOverdueMapper;
import com.jyyjr.dao.ywdao.UserRepaymentMapper;
import com.jyyjr.dao.ywdao.WarningMsgMapper;
import com.jyyjr.pojo.UserWarning;
import com.jyyjr.pojo.Warning;
import com.jyyjr.service.ActionLoanMonitorService;
import com.jyyjr.util.HttpUtils;
import com.jyyjr.util.ListUtils;
import com.jyyjr.vo.UserRepaymentVo;
import com.sun.media.jfxmedia.control.VideoDataBuffer;
/**
 * 贷中监控
 * @author 作者 jinmin
 * @version 创建时间：2018年5月31日 上午10:35:34
 */
@Service("actionLoanMonitorService")
public class ActionLoanMonitorServiceImpl implements ActionLoanMonitorService{
	
	private final Logger logger = LoggerFactory.getLogger(ActionLoanMonitorServiceImpl.class);
	
	private static final String APINAME = "S9992859";
	private static final String PASSWORD = "pwd1524430";
	private static final String URL = "https://kh_bd.253.com/feign/apiMobileTest/findByMobile";
	
	@Autowired
	private UserBorrowMapper userBorrowMapper;
	@Autowired
	private UserOverdueMapper userOverdueMapper;
	@Autowired
	private UserWarningMapper userWarningMapper;
	@Autowired
	private WarningMapper warningMapper;
	@Autowired
	private WarningMsgMapper warningMsgMapper;
	@Autowired
	private UserRepaymentMapper userRepaymentMapper;
	
	/**
	 * 将符合借款次数已经大于1次的老用户，若前3期还款中出现逾期超3天的用户插入use_warning表
	 * @author 作者 jinmin
	 * @version 创建时间：2018年5月31日 上午10:36:56
	 */
	public void firstOverdue() {
		try {
			long sTime = System.currentTimeMillis();
			//借款次数已经大于1次的老用户
			List<String> borrowCountVids = userBorrowMapper.selectBorrowCount();
			//UserWarning表中的用户
			List<String> userWarnVids = userWarningMapper.selectUserWarningVid();
			//去掉已经存在表中的用户
			borrowCountVids.removeAll(userWarnVids);
			List<String> addVids = new ArrayList<>();
			//遍历新增的用户，筛选出  前3期还款中出现逾期超3天前的用户 将他们存入UserWarning表中
			List<List<String>> listVids = ListUtils.splitList(borrowCountVids);
			List<String> vid1s = listVids.get(0);
			List<String> vid2s = listVids.get(1);
			List<String> vid3s = listVids.get(2);
			List<String> vid4s = listVids.get(3);
			CountDownLatch countDownLatch = new CountDownLatch(4);
			Thread thread1 = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						for(String vid : vid1s) {
							String overdueCountVid = userOverdueMapper.selectOverdueCount3(vid);
							if (overdueCountVid!=null) {
								int ctime = (int)(System.currentTimeMillis()/1000);
								UserWarning userWarning = new UserWarning();
								userWarning.setVid(overdueCountVid);
								userWarning.setCtime(ctime);
								userWarning.setUpdate_time(ctime);
								userWarningMapper.insertUserWarning(userWarning);
								addVids.add(overdueCountVid);
							}
						}
					} finally {
						countDownLatch.countDown();
					}
				}
			});
			thread1.start();
			Thread thread2 = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						for(String vid : vid2s) {
							String overdueCountVid = userOverdueMapper.selectOverdueCount3(vid);
							if (overdueCountVid!=null) {
								int ctime = (int)(System.currentTimeMillis()/1000);
								UserWarning userWarning = new UserWarning();
								userWarning.setVid(overdueCountVid);
								userWarning.setCtime(ctime);
								userWarning.setUpdate_time(ctime);
								userWarningMapper.insertUserWarning(userWarning);
								addVids.add(overdueCountVid);
							}
						}
					} finally {
						countDownLatch.countDown();
					}
					
				}
			});
			thread2.start();
			Thread thread3 = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						for(String vid : vid3s) {
							String overdueCountVid = userOverdueMapper.selectOverdueCount3(vid);
							if (overdueCountVid!=null) {
								int ctime = (int)(System.currentTimeMillis()/1000);
								UserWarning userWarning = new UserWarning();
								userWarning.setVid(overdueCountVid);
								userWarning.setCtime(ctime);
								userWarning.setUpdate_time(ctime);
								userWarningMapper.insertUserWarning(userWarning);
								addVids.add(overdueCountVid);
							}
						}
					} finally {
						countDownLatch.countDown();
					}
					
				}
			});
			thread3.start();
			Thread thread4 = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						for(String vid : vid4s) {
							String overdueCountVid = userOverdueMapper.selectOverdueCount3(vid);
							if (overdueCountVid!=null) {
								int ctime = (int)(System.currentTimeMillis()/1000);
								UserWarning userWarning = new UserWarning();
								userWarning.setVid(overdueCountVid);
								userWarning.setCtime(ctime);
								userWarning.setUpdate_time(ctime);
								userWarningMapper.insertUserWarning(userWarning);
								addVids.add(overdueCountVid);
							}
						}
					} finally {
						countDownLatch.countDown();
					}
					
				}
			});
			thread4.start();
			try {
				countDownLatch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			long eTime = System.currentTimeMillis();
			logger.info("查找符合Y0001用户的方法结束，耗时："+(eTime-sTime)+":新增vid："+addVids.toString());
		} catch (Exception e) {
			logger.error("出错啦"+e);
		}
		
	}
	
	/**
	 * 命中Y0001的用户
	 * @author 作者 jinmin
	 * @version 创建时间：2018年5月31日 上午10:37:12
	 */
	public void borrowing() {
		try {
			long sTime = System.currentTimeMillis();
			//命中Y0001的用户
			String type = "Y0001";
			//命中Y0001的用户vid
			List<String> Y0001Vids = userBorrowMapper.selectBorrowInUserWarning();
			List<String> updateVids = new ArrayList<>();
			List<String> addVids = new ArrayList<>();
			for(String vid : Y0001Vids) {
				//命中Y0001的用户是否已经存在（status=2）
				Integer rowCount = warningMapper.selectWarningCount(vid, type);
				if (rowCount>0) {
					updateWarning(vid,type);
					updateVids.add(vid);
				}else {
					insertWarning(vid, type);
					addVids.add(vid);
				}
			}
			long eTime = System.currentTimeMillis();
			logger.info("命中Y0001方法结束,耗时："+(eTime-sTime)+",更新vid:"+updateVids.toString()+",新增vid:"+addVids.toString());
		} catch (Exception e) {
			logger.error("出错啦！"+e);
		}
		
	}
	
	/**
	 * 将已经货款或逾期的用户status改为3
	 * @author 作者 jinmin
	 * @version 创建时间：2018年5月31日 上午10:37:23
	 */
	public void userRepayOrOverdue() {
		try {
			long sTime = System.currentTimeMillis();
			//获取warning表中已还款或已逾期的用户vid
			List<String> repayOrOverdueVids = userBorrowMapper.selectRepayOrOverdueByMarning();
			List<String> updateVids = new ArrayList<>();
			for(String vid : repayOrOverdueVids) {
				int update_time = (int)(System.currentTimeMillis()/1000);
				Warning warning = new Warning();
				warning.setVid(vid);
				warning.setStatus(3);
				warning.setUpdate_time(update_time);
				warningMapper.updateWarningAll(warning);
				updateVids.add(vid);
			}
			long eTime = System.currentTimeMillis();
			logger.info("解除预警方法结束，耗时："+(eTime-sTime)+",更新vid:"+updateVids.toString());
		} catch (Exception e) {
			logger.error("出错啦！"+e);
		}
	}
	
	/**
	 * 检查用户是否再次发起借款（避免遗漏）
	 * @author 作者 jinmin
	 * @date 创建时间：2018年6月1日 下午3:59:34
	 */
	public void userCheckBorrow() {
		try {
			long sTime = System.currentTimeMillis();
			//查询warning中有命中项的所有用户
			List<String> warningUser = warningMapper.selectWarningByStatus();
			List<String> updateVids = new ArrayList<>();
			for(String vid : warningUser) {
				//查询有命中项的用户的最后一笔订单的信息
				UserRepaymentVo userRepaymentVo = userRepaymentMapper.selectLastBorrowMsg(vid);
				String borrow_no = userRepaymentVo.getBorrow_no();
				//查询有命中项的用户的命中项信息
				List<Warning> warnings = warningMapper.selectWarning(vid);
				for(Warning warning : warnings) {
					int status = warning.getStatus();
					String type = warning.getType();
					String wBorrow_no = warning.getBorrow_no();
					if (status==1) {
						if (!borrow_no.equals(wBorrow_no)) {
							//用户最后一笔订单号与命中项的订单不一致（用户已经再一次发起借款，将之前的命中状态解除）
							Warning warning2 = new Warning();
							int update_time = (int)(System.currentTimeMillis()/1000);
							warning2.setVid(vid);
							warning2.setType(type);
							warning2.setStatus(2);
							warning2.setUpdate_time(update_time);
							warningMapper.updateWarning(warning2);
							updateVids.add(vid);
						}
					}
				}
			}
			long eTime = System.currentTimeMillis();
			logger.info("检查是否再次借款发放结束,耗时："+(eTime-sTime)+",更新vid："+updateVids);
		} catch (Exception e) {
			logger.error("出错啦!"+e);
		}
		
	}
	
	/**
	 * 命中Y0003
	 * @author 作者 jinmin
	 * @version 创建时间：2018年5月31日 上午10:37:37
	 */
	public void checkY0003() {
		try {
			long sTime = System.currentTimeMillis();
			String type = "Y0003";
			List<String> userLists = userBorrowMapper.selectBorrowNow();
			List<String> statusVis = warningMapper.selectWarningByType(type);
			List<Map<String, Object>> userAddresssLists = warningMsgMapper.selectAddress();
			List<String> updateVids = new ArrayList<>();
			List<String> addVids = new ArrayList<>();
			userLists.removeAll(statusVis);
			for(String userVid : userLists) {
				List<String> loginAddress = new ArrayList<>();
				List<String> borrowAddress = new ArrayList<>();
				for(Map<String, Object> map : userAddresssLists) {
					String vid = (String) map.get("vid");
					if (userVid.equals(vid)) {
						Long loginTime = (Long) map.get("login_time");
						Long ctime = (Long) map.get("ctime");
						String address = (String) map.get("address");
						if (address==null || loginTime == null) {
							continue;
						}
						int index  = address.indexOf("市")+1;
						if (index==0) {
							index = address.length();
						}
						String address1 = address.substring(0, index);
						if (loginTime>ctime) {
							//借款后的登录地址
							borrowAddress.add(address1);
						}else {
							//借款前的地址
							loginAddress.add(address1);
						}
					}
				}
				int loginSize = loginAddress.size();
				int bSize = borrowAddress.size();
				if (loginSize>0) {
					borrowAddress.removeAll(loginAddress);
					if (borrowAddress.size()>0) {
						//借款后登录地址不在，历史登录地址中
						Integer rowCount = warningMapper.selectWarningCount(userVid, type);
						if (rowCount>0) {
							updateWarning(userVid, type);
							updateVids.add(userVid);
						}else {
							insertWarning(userVid, type);
							addVids.add(userVid);
						}
					}
				}
			}
			long eTime = System.currentTimeMillis();
			logger.info("命中Y0003方法结束,耗时："+(eTime-sTime)+",更新vid:"+updateVids.toString()+",新增vid:"+addVids.toString());
		} catch (Exception e) {
			logger.error("出错啦！"+e);
		}
	}
	
	/**
	 * 命中Y0004
	 * @author 作者 jinmin
	 * @date 创建时间：2018年5月31日 下午2:51:27
	 */
	public void checkY0004() {
		try {
			long sTime = System.currentTimeMillis();
			String type = "Y0004";
			List<Map<String, Object>> mobileMap = warningMsgMapper.selectEmptyMobile();
			List<String> updateVids = new ArrayList<>();
			List<String> addVids = new ArrayList<>();
			for(Map<String, Object> map : mobileMap) {
				String vid = (String) map.get("vid");
				long repayTime = (long) map.get("repay_time");
				long mobile = (long) map.get("mobile");
				int time = (int)(System.currentTimeMillis()/1000);
				if ((time+86400*5)>repayTime) {
					boolean flag = emptyMobile(mobile+"");
					if (flag) {
						//命中Y0004
						Integer rowCount = warningMapper.selectWarningCount(vid, type);
						if (rowCount>0) {
							updateWarning(vid, type);
							updateVids.add(vid);
						}else {
							insertWarning(vid, type);
							addVids.add(vid);
						}
					}
				}
			}
			long eTime = System.currentTimeMillis();
			logger.info("命中Y0004方法结束,耗时："+(eTime-sTime)+",更新vid:"+updateVids.toString()+",新增vid:"+addVids.toString());
		} catch (Exception e) {
			logger.error("出错啦"+e);
		}
		
	}
	
	/**
	 * 命中Y0005
	 * @author 作者 jinmin
	 * @date 创建时间：2018年6月2日 上午10:45:49
	 */
	public void checkY0005() {
		try {
			long sTime = System.currentTimeMillis();
			String type = "Y0005";
			List<String> updateVids = new ArrayList<>();
			List<String> addVids = new ArrayList<>();
			List<UserRepaymentVo> fqMoneys = userRepaymentMapper.selectFqMoney();
			List<String> fqVids = userRepaymentMapper.selectFqMoneyVid();
			for(String vid : fqVids) {
				List<Integer> moneys = new ArrayList<>();
				for(UserRepaymentVo userRepaymentVo : fqMoneys) {
					String repayVid = userRepaymentVo.getVid();
					int repayMoney = userRepaymentVo.getRepay_money();
					if (vid.equals(repayVid)) {
						moneys.add(repayMoney);
					}
				}
				int size = moneys.size();
				int index = 0;
				for(Integer m : moneys) {
					if (m<400) {
						index ++;
					}
				}
				if (size==index) {
					//命中Y0005
					Integer rowCount = warningMapper.selectWarningCount(vid, type);
					if (rowCount>0) {
						updateWarning(vid, type);
						updateVids.add(vid);
					}else {
						insertWarning(vid, type);
						addVids.add(vid);
					}
				}
			}
			long eTime = System.currentTimeMillis();
			logger.info("命中Y0005启动,耗时："+(eTime-sTime)+",更新vid:"+updateVids.toString()+",新增vid:"+addVids.toString());
		} catch (Exception e) {
			logger.error("出错啦"+e);
		}
		
	}
	
	/**
	 * 命中Y0006
	 * @author 作者 jinmin
	 * @date 创建时间：2018年6月2日 下午4:56:42
	 */
	public void checkY0006() {
		try {
			long sTime = System.currentTimeMillis();
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
			long eTime = System.currentTimeMillis();
			logger.info("命中Y0006启动,耗时："+(eTime-sTime)+",更新vid:"+updateVids.toString()+",新增vid:"+addVids.toString());
		} catch (Exception e) {
			logger.info("出错啦"+e);
		}
		
	}
	
	/**
	 * 空号检查
	 * @author 作者 jinmin
	 * @version 创建时间：2018年5月31日 上午10:51:30
	 * @param mobile
	 * @return
	 */
	public boolean emptyMobile(String mobile) {
		try {
			Map<String, String> param = new HashMap<>();
			param.put("apiName", APINAME);
			param.put("password", PASSWORD);
			param.put("mobile", mobile);
			String response = HttpUtils.sendPost(URL, param,null,null);
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
			logger.error("出错"+e);
		}
		logger.info(mobile+":查询失败");
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
