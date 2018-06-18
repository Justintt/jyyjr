package com.jyyjr.dao.ywdao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jyyjr.pojo.AbnormNumber;

public interface AbnormNumberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AbnormNumber record);

    int insertSelective(AbnormNumber record);

    AbnormNumber selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AbnormNumber record);

    int updateByPrimaryKey(AbnormNumber record);
    /**
     * 交集数据
     * @param mobiles
     * @return
     */
    List<String> selectMixCounts(@Param("mobiles") List<String> mobiles);
}