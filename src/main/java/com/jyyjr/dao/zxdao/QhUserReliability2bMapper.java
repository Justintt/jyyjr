package com.jyyjr.dao.zxdao;

import java.util.Map;

import com.jyyjr.pojo.QhUserReliability2b;

public interface QhUserReliability2bMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(QhUserReliability2b record);

    int insertSelective(QhUserReliability2b record);

    QhUserReliability2b selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QhUserReliability2b record);

    int updateByPrimaryKey(QhUserReliability2b record);
    /**
     * payAbilityScore,virAssetScore
     * @param vid
     * @return
     */
    Map<String, Object> selectPvByVid(String vid);
}