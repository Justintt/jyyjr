package com.jyyjr.dao.zxdao;

import com.jyyjr.pojo.RiskContactDetail;

public interface RiskContactDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RiskContactDetail record);

    int insertSelective(RiskContactDetail record);

    RiskContactDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RiskContactDetail record);

    int updateByPrimaryKey(RiskContactDetail record);
    
    /**
     * 同盾风险联系人个数
     * @param vid
     * @return
     */
    Integer selectRiskCountByVid(String vid);
}