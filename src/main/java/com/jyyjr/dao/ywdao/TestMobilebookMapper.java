package com.jyyjr.dao.ywdao;

import java.util.List;

import com.jyyjr.pojo.MobileBook;
import com.jyyjr.pojo.TestMobilebook;

public interface TestMobilebookMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TestMobilebook record);

    int insertSelective(TestMobilebook record);

    TestMobilebook selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TestMobilebook record);

    int updateByPrimaryKey(TestMobilebook record);
    
    List<MobileBook> selectMobileBookByVid(String vid);
    
    List<String> selectMobileByVid(String vid);
}