package com.jyyjr.dao.ywdao;

import java.util.List;

public interface UserOverdueMapper {
	
	/**
	 * 前3期还款中出现逾期超3天
	 * @param vid
	 * @return
	 */
	String selectOverdueCount3(String vid);

}
