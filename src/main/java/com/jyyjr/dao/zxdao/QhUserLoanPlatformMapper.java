package com.jyyjr.dao.zxdao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jyyjr.pojo.QhUserLoanPlatform;

public interface QhUserLoanPlatformMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(QhUserLoanPlatform record);

    int insertSelective(QhUserLoanPlatform record);

    QhUserLoanPlatform selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QhUserLoanPlatform record);

    int updateByPrimaryKey(QhUserLoanPlatform record);
    
    /**
     * cnssAmount,p2pAmount,queryAmtM3
     * @param vid
     * @return
     */
    Map<String, Integer> selectCpqByVid(String vid);
    
    /**
     * 前海近3个月业务发生的次数
     * @param vid
     * @param ctime
     * @return
     */
    Integer selectBinQhBusiCntM3(@Param("vid") String vid,@Param("ctime") int ctime);
}