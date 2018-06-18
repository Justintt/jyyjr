package com.jyyjr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jyyjr.common.Message;
import com.jyyjr.dao.ywdao.UserMapper;
import com.jyyjr.dao.zxdao.TdDataMapper;
import com.jyyjr.pojo.TdData;
import com.jyyjr.pojo.User;
import com.jyyjr.service.TdDataService;


@Service("tdDataService")
public class TdDataServiceImpl implements TdDataService{
	
	@Autowired
	private TdDataMapper tdDataMapper;
	@Autowired
	private UserMapper userMapper;
	
	
	public Message<List<TdData>> getTdData(String vid){
		if (vid.equals("") || vid==null) {
			return new Message<>(Message.FAIL, "vid为空");
		}
		List<TdData> list = tdDataMapper.selectTdDataByVid(vid);
		if (list.size()>0 && list !=null) {
			return new Message<>(Message.SUCCESS, "查询成功",list);
		}
		return new Message<>(Message.FAIL, "没有同盾数据",list);	
	}
	
	
	public String getUsername(String vid) {
		User user = userMapper.selectUserByVid(vid);
		return user.getTruename();
	}

}
