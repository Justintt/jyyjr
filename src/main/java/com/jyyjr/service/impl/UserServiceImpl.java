package com.jyyjr.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jyyjr.common.Message;
import com.jyyjr.dao.ywdao.UserMapper;
import com.jyyjr.pojo.User;
import com.jyyjr.service.UserService;



@Service("userService")
public class UserServiceImpl implements UserService{
	
	private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserMapper userMapper;
	
	public Message<User> selectUserByVid(String vid){
		try {
			User user = userMapper.selectUserByVid(vid);
			if(user !=null) {
				logger.info(vid+":"+user.getTruename());
				return new Message<>(Message.SUCCESS, "查询成功",user);
			}
			return new Message<>(Message.SUCCESS, "查询失败");
		} catch (Exception e) {
			logger.error(vid+":发生异常"+e);
			return new Message<>(Message.SUCCESS, "查询失败");
		}
		
	}
}
