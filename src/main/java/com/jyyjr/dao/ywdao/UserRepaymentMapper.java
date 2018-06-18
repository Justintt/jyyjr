package com.jyyjr.dao.ywdao;
/**
 * @author 作者 jinmin
 * @date 创建时间：2018年6月1日 下午2:38:53
 */

import java.util.List;

import com.jyyjr.vo.LinkedOverdueVo;
import com.jyyjr.vo.UserRepaymentVo;

public interface UserRepaymentMapper {
	
	/**
	 * 用户最后还款时间的订单信息
	 * @author 作者 jinmin
	 * @date 创建时间：2018年6月1日 下午2:42:02
	 * @param vid
	 * @return
	 */
	UserRepaymentVo selectLastBorrowMsg(String vid);
	
	/**
	 * 借贷中的分期订单的还款金额
	 * @author 作者 jinmin
	 * @date 创建时间：2018年6月2日 上午10:42:35
	 * @return
	 */
	List<UserRepaymentVo> selectFqMoney();
	
	/**
	 * 借贷中的分期订单的vid
	 * @author 作者 jinmin
	 * @date 创建时间：2018年6月2日 上午10:53:59
	 * @return
	 */
	List<String> selectFqMoneyVid();
	
	/**
	 * 借款次数在1次以上的用户，若用户上次还款时间在还款日当晚七点后才采取主动还款方式的用户
	 * @author 作者 jinmin
	 * @date 创建时间：2018年6月2日 下午4:57:57
	 * @return
	 */
	List<String> selectLastRepay();
	
	/**
	 * 查询用户最后一笔订单是否逾期或黑名单
	 * @author 作者 jinmin
	 * @date 创建时间：2018年6月11日 下午2:25:01
	 * @param vid
	 * @return
	 */
	LinkedOverdueVo selectOverdueByVid(String vid);

}
