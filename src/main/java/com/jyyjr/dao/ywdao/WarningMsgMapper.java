package com.jyyjr.dao.ywdao;

import java.util.List;
import java.util.Map;

/**
 * 贷中监控相关数据
 * @author 作者 jinmin
 * @date 创建时间：2018年5月31日 下午1:56:16
 */
public interface WarningMsgMapper {
	
	/**
	 * 获取正在借贷中的用户的
	 * 登录地址 与 居住地址、工作地址
	 * @author 作者 jinmin
	 * @date 创建时间：2018年5月31日 下午1:55:54
	 * @return
	 */
	List<Map<String, Object>> selectAddress();
	
	/**
	 * 查询正在借贷中的用户的还款时间和电话号码
	 * @author 作者 jinmin
	 * @date 创建时间：2018年5月31日 下午1:55:41
	 * @return
	 */
	List<Map<String, Object>> selectEmptyMobile();

}
