package com.jyyjr.dao.ywdao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jyyjr.pojo.MobileCallrecord;
import com.jyyjr.pojo.TestMobilecall;

public interface TestMobilecallMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TestMobilecall record);

    int insertSelective(TestMobilecall record);

    TestMobilecall selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TestMobilecall record);

    int updateByPrimaryKey(TestMobilecall record);
    
    List<Map<String, Object>> selectCallCountByMyMobile(@Param("mymobile") long mymobile,@Param("ctime") long ctime);
    
    List<Map<String, Object>> selectAllCallCountByMyMobile(@Param("mymobile") long mymobile,@Param("ctime") long ctime);
    
    List<MobileCallrecord> selectMobileCallrecordByVid(long mymobile);
    
    List<Map<String, Object>> selectDateCallByMyMobile(@Param("mymobile") long mymobile,@Param("ctime") long ctime,@Param("mobile") String mobile);
    
    List<Map<String, Object>> selectCallCountNo1ByMyMobile(@Param("mymobile") long mymobile,@Param("ctime") long ctime);
    
    List<Map<String, Object>> selectAllDateCallByMyMobile(@Param("mymobile") long mymobile,@Param("ctime") long ctime,@Param("mobile") String mobile);
    
    /**
     * K0032：用户近通话记录前三位密度过少
     * @param mymobile
     * @param week_time
     * @param one_month_time
     * @param three_month_time
     * @return
     */
    List<Map<String, Object>> selectCallTop3ByMobile(
    		@Param("mymobile")long mymobile,
    		@Param("week_time") long week_time,
    		@Param("one_month_time") long one_month_time,
    		@Param("three_month_time") long three_month_time);
    
    List<Long> selectCallTimeByMyMobile(@Param("mymobile") long mymobile,@Param("ctime") long ctime);
    /**
     * 夜间通话情况
     * @param mymobile
     * @param ctime
     * @return
     */
    Map<String, Object> selectCallNightByMyMobile(@Param("mymobile") long mymobile,@Param("ctime") long ctime);
    
    /**
     * 查询mymobile被叫次数大于2次 并且无主叫号码
     * @param mymobile
     * @return
     */
    List<String> selectK0013MobilesByMymobile(long mymobile);
    
    /**
     * 查询mymobile和callOtherNumber 的被叫通话时间
     * @param mymobile
     * @param callOtherNumber
     * @return
     */
    List<Integer> selectK0013TimesByMymobileAndCallOtherNumber(@Param("mymobile")long mymobile, @Param("callOtherNumber") String callOtherNumber);
    
    /**
     * 催收号码被叫次数
     * @param mobiles
     * @return
     */
    int selectCuiShouBjCount(@Param("mymobile") long mymobile,@Param("mobiles") List<String> mobiles);
    
    /**
     * 有通话记录的号码
     * @param mobiles
     * @return
     */
    List<String> selectCuiShouNumberInCall(@Param("mymobile") long mymobile,@Param("mobiles") List<String> mobiles);
    
    /**
     * 疑似催收号码的通话数量
     * @param mobiles
     * @return
     */
    int selectYsCuiShouCount(@Param("mymobile") long mymobile,@Param("mobiles") List<String> mobiles);
    
    /**
     * 近1月最大连续通话活跃天数
     * @param mymobile
     * @param ctime
     * @return
     */
    List<Long> selectBinCallAMaxDaysM1(@Param("mymobile") long mymobile,@Param("ctime") long ctime);
    
    /**
     * 通话时长极短（低于30秒）的通话次数占比
     * @param mymobile
     * @return
     */
    Double selectBinCall30sRate(long mymobile);
    
    /**
     * 夜间（23点-5点）通话次数占比
     * @param mymobile
     * @return
     */
    Double selectBinCallNightRate(long mymobile);
    
    /**
     * 获取用户通讯记录里面的手机号码
     * @param mymobile
     * @return
     */
    List<String> selectNumber11ByMyMobile(long mymobile);
}