package com.jyyjr.dao.zxdao;

import com.jyyjr.pojo.TdTbBase;

public interface TdTbBaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TdTbBase record);

    int insertSelective(TdTbBase record);

    TdTbBase selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TdTbBase record);

    int updateByPrimaryKey(TdTbBase record);
    
    /**
	 * 根据vid查询淘宝认证时间
	 * @param vid
	 * @return
	 */
	Integer queryCtimeByVid(String vid);
}