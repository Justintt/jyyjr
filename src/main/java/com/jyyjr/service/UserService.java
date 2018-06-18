package com.jyyjr.service;

import com.jyyjr.common.Message;
import com.jyyjr.pojo.User;

public interface UserService {
	
	Message<User> selectUserByVid(String vid);

}
