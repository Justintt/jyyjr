package com.jyyjr.dao.zxdao;

import java.util.Map;

import com.jyyjr.pojo.AnRiskParam;

public interface AnRiskParamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AnRiskParam record);

    int insertSelective(AnRiskParam record);

    AnRiskParam selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AnRiskParam record);

    int updateByPrimaryKey(AnRiskParam record);
    
    /**
     * 是否存在逾期信息（安融）
     * 是否存在单笔合同2次或以上逾期信息
     * 是否存在2笔或以上不同合同逾期信息
     * @param vid
     * @return
     */
    Map<String, String> selectD123ByVid(String vid);
}