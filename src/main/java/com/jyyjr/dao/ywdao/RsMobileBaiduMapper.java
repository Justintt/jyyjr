package com.jyyjr.dao.ywdao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jyyjr.pojo.RsMobileBaidu;

public interface RsMobileBaiduMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RsMobileBaidu record);

    int insertSelective(RsMobileBaidu record);

    RsMobileBaidu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RsMobileBaidu record);

    int updateByPrimaryKey(RsMobileBaidu record);
    
    /**
     * 查询mobiles中命中催收个数
     * @param mobiles
     * @return
     */
    List<String> selectHitCountByMobiles(@Param("mobiles") List<String> mobiles);
    
    /**
     * 获取催收号码库号码
     * @return
     */
    List<String> selectCuiShouNumber();
}