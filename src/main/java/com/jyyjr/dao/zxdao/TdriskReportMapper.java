package com.jyyjr.dao.zxdao;

import com.jyyjr.pojo.TdriskReport;

public interface TdriskReportMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TdriskReport record);

    int insertSelective(TdriskReport record);

    TdriskReport selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TdriskReport record);

    int updateByPrimaryKey(TdriskReport record);
    
    /**
     * 根据vid获取同盾分
     * @param vid
     * @return
     */
    Integer selectTdScoreByVid(String vid);
}