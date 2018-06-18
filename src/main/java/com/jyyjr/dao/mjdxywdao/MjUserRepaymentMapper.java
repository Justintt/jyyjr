package com.jyyjr.dao.mjdxywdao;
/**
 * @author 作者 jinmin
 * @date 创建时间：2018年6月1日 下午2:38:53
 */

import java.util.List;

import com.jyyjr.vo.LinkedOverdueVo;
import com.jyyjr.vo.UserRepaymentVo;

public interface MjUserRepaymentMapper {
	
	
	
	/**
	 * 查询用户最后一笔订单是否逾期
	 * @author 作者 jinmin
	 * @date 创建时间：2018年6月11日 下午2:25:01
	 * @param vid
	 * @return
	 */
	LinkedOverdueVo selectOverdueByVid(String vid);

}
