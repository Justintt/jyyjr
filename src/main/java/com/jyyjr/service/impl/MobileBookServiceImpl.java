package com.jyyjr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jyyjr.common.Message;
import com.jyyjr.dao.txdao.MobileBookMapper;
import com.jyyjr.pojo.MobileBook;
import com.jyyjr.service.MobileBookService;

@Service("mobileBookService")
public class MobileBookServiceImpl implements MobileBookService{
	
	@Autowired
	private MobileBookMapper mobileBookMapper;
	/**
	 * 根据vid查询通讯录
	 * @param vid
	 * @return
	 */
	public Message<List<MobileBook>> selectMobileBookByVid(String vid){
		if (vid==null || vid.equals("")) {
			return new Message<>(Message.FAIL,"vid为空");
		}
		List<MobileBook> list = mobileBookMapper.selectMobileBookByVid(vid);
		if (list.size()>0) {
			return new Message<List<MobileBook>>(Message.SUCCESS, "查询成功",list);
		}
		return new Message<>(Message.FAIL, "没有查询到通讯录");
		
	}

}
