package com.jyyjr.dao.zxdao;

import java.util.List;

import com.jyyjr.pojo.TdData;

public interface TdDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TdData record);

    int insertSelective(TdData record);

    TdData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TdData record);

    int updateByPrimaryKey(TdData record);
    
    List<TdData> selectTdDataByVid(String vid);
    
    Integer selectTdSevenDayByVid(String vid);
}