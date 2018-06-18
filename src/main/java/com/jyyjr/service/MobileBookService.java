package com.jyyjr.service;

import java.util.List;

import com.jyyjr.common.Message;
import com.jyyjr.pojo.MobileBook;

public interface MobileBookService {
	/**
	 * 根据vid查询通讯录
	 * @param vid
	 * @return
	 */
	Message<List<MobileBook>> selectMobileBookByVid(String vid);
}
