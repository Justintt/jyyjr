package com.jyyjr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jyyjr.common.Message;
import com.jyyjr.dao.ywdao.UserBorrowMapper;
import com.jyyjr.pojo.UserBorrow;
import com.jyyjr.service.UserBorrowService;

@Service("userBorrowService")
public class UserBorrowServiceImpl implements UserBorrowService{

	@Autowired
	private UserBorrowMapper userBorrowMapper;
	
	
	public Message<List<UserBorrow>> getUserBorrow(String vid){
		if(vid==null || vid.equals("")) {
			return new Message<>(Message.FAIL,"vid为空");
		}
		List<UserBorrow> list = userBorrowMapper.selectUserBorrowByVid(vid);
		if(list.size()>0 && list!=null) {
			return new Message<>(Message.SUCCESS,"查询成功",list);
		}
		return new Message<>(Message.FAIL,"用户没有借款记录");
		
	}
}
