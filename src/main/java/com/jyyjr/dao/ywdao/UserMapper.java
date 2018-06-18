package com.jyyjr.dao.ywdao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jyyjr.pojo.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    User selectUserByVid(String vid);
    
    Map<String, Object> selectBorrow(String vid);
    
    Map<String, Object> selectAgeAndSexByVid(String vid);
    
    String selectProvinceByVid(String vid);
    
    /**
     * bin_age,bin_is_marry,bin_education,bin_identity_address
     * @param vid
     * @return
     */
    Map<String, Object> selectUserMsgByVid(String vid);
    
    /**
     * 用户通话记录中的联系人在本平台的借款逾期人数
     * @param mobiles
     * @return
     */
    List<String> selectBinJcOverdueUsers(@Param("mobiles") List<String> mobiles);
    
    /**
     * 根据电话号码查询vid
     * @author 作者 jinmin
     * @date 创建时间：2018年6月11日 下午1:47:59
     * @param mobile
     * @return
     */
    String selectVidByMobile(String mobile);
}