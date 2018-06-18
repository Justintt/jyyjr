package com.jyyjr.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jyyjr.common.Message;
import com.jyyjr.pojo.MobileBook;
import com.jyyjr.pojo.MobileCallrecord;
import com.jyyjr.pojo.TdData;
import com.jyyjr.pojo.User;
import com.jyyjr.pojo.UserBorrow;
import com.jyyjr.service.MobileBookService;
import com.jyyjr.service.MobileCallrecordService;
import com.jyyjr.service.TdDataService;
import com.jyyjr.service.TestService;
import com.jyyjr.service.UserBorrowService;
import com.jyyjr.service.UserService;

@Controller
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserBorrowService userBorrowService;
	@Autowired
	private TdDataService tdDataService;
	@Autowired
	private MobileBookService mobileBookService;
	@Autowired
	private MobileCallrecordService mobileCallrecordService;
	@Autowired
	private TestService testService;
	
	/**
	 * 查询用户
	 * @param vid
	 * @return
	 */
	@RequestMapping("/getuser")
	@ResponseBody
	public Message<User> Test01(String vid) {
		return userService.selectUserByVid(vid);
	}
	
	/**
	 * 获取用户借款信息
	 * @param vid
	 * @return
	 */
	@RequestMapping("/getUserBorrow")
	@ResponseBody
	public Message<List<UserBorrow>> test02(String vid) {
		return userBorrowService.getUserBorrow(vid);
		
	}
	
	/**
	 * 获取同盾申请次数
	 * @return
	 */
	@RequestMapping("/getTdData")
	@ResponseBody
	public Message<List<TdData>> test03(String vid) {
		//DataSourceContextHolder.setDbType("dataSource_zx");
		return tdDataService.getTdData(vid);
		
	}
	
	/**
	 * 根据vid获取通讯录
	 * @param vid
	 * @return
	 */
	@RequestMapping("/getMobileBook")
	@ResponseBody
	public Message<List<MobileBook>> test04(String vid){
		return mobileBookService.selectMobileBookByVid(vid);
		
	}
	
	@RequestMapping("/getMobileCallrecord")
	@ResponseBody
	public Message<List<MobileCallrecord>> test05(String vid) {
		return mobileCallrecordService.getMobileCallrecord(vid);
	}
	
	@RequestMapping("/getK0032")
	@ResponseBody
	public Message<Map<String, Object>> k0032(String vid) {
		return mobileCallrecordService.checkK0032(vid);
		
	}
	
	/**
	 * 静默天数
	 * @param vid
	 * @return
	 */
	@RequestMapping("/getJmDay")
	@ResponseBody
	public Message<Integer> getJmDay(String vid) {
		return testService.getJmDay(vid);
	}
	
	/**
	 * 测试错误
	 * @author 作者 jinmin
	 * @date 创建时间：2018年6月2日 下午5:43:43
	 * @return
	 */
	@RequestMapping("/testError")
	@ResponseBody
	public Message<Integer> testError() {
		int num = testService.testError();
		Message<Integer> message = new Message<>(Message.SUCCESS, "成功",num);
		return message;
		
	}

	@RequestMapping("/K0039")
	@ResponseBody
	public Message<String> testK0039(String vid) {
		String num = testService.checkK0039(vid);
		Message<String> message = new Message<>(Message.SUCCESS, "成功",num);
		return message;
		
	}
	
	@RequestMapping("/insertVid")
	@ResponseBody
	public Message<Integer> insertVid(){
		Integer status = testService.insertVid();
		Message<Integer> message = new Message<>(Message.SUCCESS, "成功",status);
		return message;
	}
}
