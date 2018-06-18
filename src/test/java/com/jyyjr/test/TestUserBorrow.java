package com.jyyjr.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jyyjr.base.BaseTest;
import com.jyyjr.dao.dxywdao.UserWarningMapper;
import com.jyyjr.dao.ywdao.UserBorrowMapper;
import com.jyyjr.dao.ywdao.UserOverdueMapper;
import com.jyyjr.pojo.UserWarning;
import com.jyyjr.util.ListUtils;

public class TestUserBorrow extends BaseTest{
	
	@Autowired
	private UserBorrowMapper userBorrowMapper;
	@Autowired
	private UserOverdueMapper userOverdueMapper;
	@Autowired
	private UserWarningMapper userWarningMapper;
	
	@Test
	public void test01() {
		//借款次数已经大于1次的老用户
				List<String> borrowCountVids = userBorrowMapper.selectBorrowCount();
				//UserWarning表中的用户
				List<String> userWarnVids = userWarningMapper.selectUserWarningVid();
				//去掉已经存在表中的用户
				borrowCountVids.removeAll(userWarnVids);
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
				System.out.println(borrowCountVids.size());
	}
	
	/*public void test02() {
		List<String> lists = userWarningMapper.selectUserWarningVid();
		System.out.println(lists.size());
	}*/

}
