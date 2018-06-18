package com.jyyjr.dao.zxdao;

import com.jyyjr.pojo.QhUserRiskHint;

public interface QhUserRiskHintMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(QhUserRiskHint record);

    int insertSelective(QhUserRiskHint record);

    QhUserRiskHint selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QhUserRiskHint record);

    int updateByPrimaryKey(QhUserRiskHint record);
    
    /**
     * 查询最近一笔业务的灰度分
     * @param vid
     * @return
     */
    Integer selectRskScoreByVid(String vid);
    
    /**
     * 前海sourceId字段A信贷逾期风险业务发生的次数
     * @param vid
     * @return
     */
    Integer selectNsourceIdByVid(String vid);
    
    /**
     * 前海最近一笔信贷逾期业务发生时间距离
     * @param vid
     * @return
     */
    String selectDataBuildTimeByVid(String vid);
}