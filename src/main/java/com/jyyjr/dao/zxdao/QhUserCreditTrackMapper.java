package com.jyyjr.dao.zxdao;

import com.jyyjr.pojo.QhUserCreditTrack;


public interface QhUserCreditTrackMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(QhUserCreditTrack record);

    int insertSelective(QhUserCreditTrack record);

    QhUserCreditTrack selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QhUserCreditTrack record);

    int updateByPrimaryKey(QhUserCreditTrack record);
    
    Integer selectCedooScoreM0ByVid(String vid);
    
}