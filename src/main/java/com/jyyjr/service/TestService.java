package com.jyyjr.service;

import com.jyyjr.common.Message;

public interface TestService {
	
	/**
	 * 放回
	 * @param vid
	 * @return
	 */
	Message<Integer> getJmDay(String vid);
	
	/**
	 * 错误测试
	 * @author 作者 jinmin
	 * @date 创建时间：2018年6月2日 下午5:38:49
	 * @return
	 */
	public int testError();
	
	String checkK0039(String vid);
	
	Integer insertVid();

}
