package com.jyyjr.dao.zxdao;

import com.jyyjr.pojo.MobileBill;

public interface MobileBillMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MobileBill record);

    int insertSelective(MobileBill record);

    MobileBill selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MobileBill record);

    int updateByPrimaryKey(MobileBill record);
    
    /**
     * 获取近6个月话费消费总额
     * @param vid
     * @return
     */
    Integer selectAvgBillfeeByVid(String vid);
}