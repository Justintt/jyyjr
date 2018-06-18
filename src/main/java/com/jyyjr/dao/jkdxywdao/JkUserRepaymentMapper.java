package com.jyyjr.dao.jkdxywdao;

import com.jyyjr.vo.LinkedOverdueVo;

/**
 * @author 作者 jinmin
 * @date 创建时间：2018年6月1日 下午2:38:53
 */

public interface JkUserRepaymentMapper {
	
	
	/**
	 * 查询用户最后一笔订单是否逾期
	 * @author 作者 jinmin
	 * @date 创建时间：2018年6月11日 下午2:25:01
	 * @param vid
	 * @return
	 */
	LinkedOverdueVo selectOverdueByVid(String vid);

}
