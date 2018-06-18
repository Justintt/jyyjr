package com.jyyjr.dao.zxdao;

import com.jyyjr.pojo.MobileBasicinfo;

public interface MobileBasicinfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MobileBasicinfo record);

    int insertSelective(MobileBasicinfo record);

    MobileBasicinfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MobileBasicinfo record);

    int updateByPrimaryKey(MobileBasicinfo record);
    
    Integer selectNetAgeByvid(String vid);
    
    Long selectCtimeByVid(String vid);
}