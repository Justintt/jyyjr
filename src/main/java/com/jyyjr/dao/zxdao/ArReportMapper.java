package com.jyyjr.dao.zxdao;

import com.jyyjr.pojo.ArReport;

public interface ArReportMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArReport record);

    int insertSelective(ArReport record);

    ArReport selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArReport record);

    int updateByPrimaryKey(ArReport record);
    
    /**
     * 安融申请总次数
     * @param vid
     * @return
     */
    Integer selectBinArApplyAllCnt(String vid);
    
    /**
     * 安融申请失败次数
     * @param vid
     * @return
     */
    Integer selectBinPArApplyNotpass(String vid);
}