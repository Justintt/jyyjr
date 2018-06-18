package com.jyyjr.dao.dxywdao;

import java.util.List;

import com.jyyjr.pojo.UserWarning;

public interface UserWarningMapper {
	
	/**
	 * 根据vid插入用户
	 * @param vid
	 * @return
	 */
	int insertUserWarning(UserWarning userWarning);
	
	/**
	 * 查询UserWarning表所有vid
	 * @return
	 */
	List<String> selectUserWarningVid();

}
