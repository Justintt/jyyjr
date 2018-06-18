package com.jyyjr.service;

import java.util.List;

import com.jyyjr.common.Message;
import com.jyyjr.pojo.UserBorrow;

public interface UserBorrowService {
	
	Message<List<UserBorrow>> getUserBorrow(String vid);

}
