package com.jyyjr.dao.dxywdao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jyyjr.pojo.Warning;

public interface WarningMapper {
	
	/**
	 * 插入预警用户
	 * @param warning
	 * @return
	 */
	int insertWarning(Warning warning);
	
	/**
	 * 查询warning中命中相应规则的用户是否已经存在
	 * @param vid
	 * @return
	 */
	Integer selectWarningCount(@Param("vid") String vid,@Param("type") String type);
	
	/**
	 * 查询相应的规则下，status=1的用户
	 * @param type
	 * @return
	 */
	List<String> selectWarningByType(String type);
	
	/**
	 * 查询相status=1的用户
	 * @param type
	 * @return
	 */
	List<String> selectWarningByStatus();
	
	/**
	 * 更新
	 * @param warning
	 * @return
	 */
	int updateWarning(Warning warning);
	
	/**
	 * 更新全部
	 * @param warning
	 * @return
	 */
	int updateWarningAll(Warning warning);
	
	/**
	 * 根据vid查询用户命中信息
	 * @author 作者 jinmin
	 * @date 创建时间：2018年6月1日 下午4:15:50
	 * @param vid
	 * @return
	 */
	List<Warning> selectWarning(String vid);

}
