package com.jyyjr.dao.zxdao;

import java.util.Map;

import com.jyyjr.pojo.BehaviorAnalysis;

public interface BehaviorAnalysisMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BehaviorAnalysis record);

    int insertSelective(BehaviorAnalysis record);

    BehaviorAnalysis selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BehaviorAnalysis record);

    int updateByPrimaryKey(BehaviorAnalysis record);
    
    /**
     * 近6个月催收号码联系情况（魔盒）
     * 近6个月小贷号码联系情况（魔盒）
     * @param vid
     * @return
     */
    Map<String, String> selectBaMsgByVid(String vid);
}