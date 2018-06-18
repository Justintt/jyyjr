package com.jyyjr.dao.ywdao;

import java.util.List;
import java.util.Map;

import com.jyyjr.pojo.UserWokeVerify;

public interface UserWokeVerifyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserWokeVerify record);

    int insertSelective(UserWokeVerify record);

    UserWokeVerify selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserWokeVerify record);

    int updateByPrimaryKey(UserWokeVerify record);
    
    /**
     * 3、工作认证提交的工作城市
     * @param vid
     * @return
     */
    Map<String, String> selectWorkAddressByVid(String vid);
    
    /**
     * 获取用户工作的公司连表
     * @param vid
     * @return
     */
    String selectWorkCompany(String vid);
    
    /**
     * 同一工作单位的关系用户数
     * @param vid
     * @return
     */
    List<String> selectBinCompanyUsers(String workCompany);
}