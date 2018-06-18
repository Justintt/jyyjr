package com.jyyjr.dao.mjdxywdao;
/**
 * @author 作者 jinmin
 * @date 创建时间：2018年6月12日 下午2:21:46
 */
public interface MjUserBlackListMapper {
	
	/**
	 * 查询用户是否在黑名单
	 * @author 作者 jinmin
	 * @date 创建时间：2018年6月12日 下午2:22:34
	 * @param vid
	 * @return
	 */
	Integer selectUserBlackStatus(String vid);

}
