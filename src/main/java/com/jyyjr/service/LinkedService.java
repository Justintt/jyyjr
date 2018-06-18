package com.jyyjr.service;

import java.util.Map;

/**
 * @author 作者 jinmin
 * @date 创建时间：2018年6月11日 下午2:41:04
 */
public interface LinkedService {

	/**
	 * 多平台逾期判断
	 * @author 作者 jinmin
	 * @date 创建时间：2018年6月11日 下午3:14:44
	 * @param mobile
	 * @return
	 */
	Map<String, Object> linkedOverdue(String mobile);
}
