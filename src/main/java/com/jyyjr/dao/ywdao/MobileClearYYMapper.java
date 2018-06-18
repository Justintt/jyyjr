package com.jyyjr.dao.ywdao;

import java.util.Map;

import com.jyyjr.pojo.MobileClearYY;

public interface MobileClearYYMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MobileClearYY record);

    int insertSelective(MobileClearYY record);

    MobileClearYY selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MobileClearYY record);

    int updateByPrimaryKey(MobileClearYY record);
    
    Map<String,Object> selectMobileClearYYByVid(String vid);
    
    Map<String, Object> selectAcaByVid(String vid);
    
    /**
     * 清洗后通讯录互通号码数量
     * @param vid
     * @return
     */
    Integer selectBinCallIoCnt(String vid);
}