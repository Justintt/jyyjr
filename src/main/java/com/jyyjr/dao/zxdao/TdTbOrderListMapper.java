package com.jyyjr.dao.zxdao;

import java.util.Map;

import com.jyyjr.pojo.TdTbOrderList;

public interface TdTbOrderListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TdTbOrderList record);

    int insertSelective(TdTbOrderList record);

    TdTbOrderList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TdTbOrderList record);

    int updateByPrimaryKey(TdTbOrderList record);
    
    /**
     * 获取淘宝消费总额
     * @param vid
     * @return
     */
    Double getOrderListMoneyCount(String vid);
    
    /**
     * 根据vid查询用户的最早和最晚订单时间
     * @param vid
     * @return
     */
    Map<String, Integer> queryOrderTimeByVid(String vid);
}