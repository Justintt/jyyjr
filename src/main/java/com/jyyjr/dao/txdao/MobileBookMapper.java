package com.jyyjr.dao.txdao;

import java.util.List;

import com.jyyjr.pojo.MobileBook;

public interface MobileBookMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MobileBook record);

    int insertSelective(MobileBook record);

    MobileBook selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MobileBook record);

    int updateByPrimaryKey(MobileBook record);
    
    List<MobileBook> selectMobileBookByVid(String vid);
    
    List<String> selectMobileByVid(String vid);
}