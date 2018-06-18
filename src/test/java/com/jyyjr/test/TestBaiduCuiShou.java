package com.jyyjr.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.jyyjr.base.BaseTest;
import com.jyyjr.dao.ywdao.AbnormNumberMapper;
import com.jyyjr.dao.ywdao.RsMobileBaiduMapper;
import com.jyyjr.dao.ywdao.TestMobilebookMapper;
import com.jyyjr.dao.ywdao.TestMobilecallMapper;
import com.jyyjr.dao.ywdao.UserMapper;
import com.jyyjr.pojo.User;
import com.jyyjr.util.HttpUtils;

public class TestBaiduCuiShou extends BaseTest{
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private TestMobilebookMapper testMobilebookMapper;
	@Autowired
	private TestMobilecallMapper testMobilecallMapper;
	@Autowired
	private RsMobileBaiduMapper rsMobileBaiduMapper;
	@Autowired
	private AbnormNumberMapper abnormNumberMapper;
	
	@Test
	public void test01() {
		User user = userMapper.selectUserByVid("e9d265470895bdb2bddaec85c495f795");
		long mymobile = user.getMobile();
		List<String> callOtherNumbers = testMobilecallMapper.selectK0013MobilesByMymobile(mymobile);
		List<String> hitCallOtherNumbers = new ArrayList<String>();//符合条件的号码
		for (String callOtherNumber : callOtherNumbers) {
        	//剔除号码为"未知"的
        	if("未知".equals(callOtherNumber)) {
        		continue ;
        	}
            //查询mymobile和callOtherNumber 的被叫通话时间
            List<Integer> times = testMobilecallMapper.selectK0013TimesByMymobileAndCallOtherNumber(mymobile, callOtherNumber);
            for (int i=0;i<times.size()-1;i++) {
                int big = times.get(i+1);
                int small = times.get(i);
                //间隔天数
                int intervalDay = (big-small)/86400;
                if(intervalDay == 0) {
                    //当天通话
                    //间隔3小时以上的被叫通话
                    if(big - small >= 60*60*3) {
                        hitCallOtherNumbers.add(callOtherNumber);
                        break;
                    }
                } else if(intervalDay == 1) {
                    //连续两天
                    hitCallOtherNumbers.add(callOtherNumber);
                    break;
                }
            }
        }
		//存在命中号码
        if(hitCallOtherNumbers.size() > 0) {
            for (String mobile : hitCallOtherNumbers) {
            	//百度催收爬虫接口
            	String url = "http://116.62.146.43:8199/savetag";
            	String param = "mobile="+mobile.trim();
            	String response = HttpUtils.sendGet(url, param);
                try {
                    JSONObject resJSON = JSONObject.parseObject(response);
                    if (resJSON.getInteger("status") == 1) {
                    	
                    }
                } catch (Exception e) {
                	
                }
            }
            //命中百度催收标签号的号码
            List<String> hitNumbers = rsMobileBaiduMapper.selectHitCountByMobiles(hitCallOtherNumbers);
            hitNumbers.add("");
            //命中百度催收标签号的次数
            int hit_bd_tag_cnt = hitNumbers.size()-1;
            //百度催收标签号码的被叫通话次数
            int hit_number_c_cnt = testMobilecallMapper.selectCuiShouBjCount(mymobile,hitNumbers);
            //获取催收号码库号码
            //List<String> cuiShouNumbers = rsMobileBaiduMapper.selectCuiShouNumber();
            List<String> callNumbers = testMobilecallMapper.selectCuiShouNumberInCall(mymobile,hitNumbers);
            //百度催收标签号码的关联用户数
            int r_number_user_cnt = callNumbers.size();
            //用户疑似催收号码的个数
            int like_number_cnt = hitCallOtherNumbers.size();
            //用户疑似催收号码总通话数
            int like_number_c_cnt = testMobilecallMapper.selectYsCuiShouCount(mymobile,hitCallOtherNumbers);
            
            List<String> jiaojiNumbers = abnormNumberMapper.selectMixCounts(hitCallOtherNumbers);
            int mix_counts = jiaojiNumbers.size();
            System.out.println(hit_bd_tag_cnt+":"+hit_number_c_cnt+":"+r_number_user_cnt+":"+like_number_cnt+":"+like_number_c_cnt+":"+mix_counts);
            System.out.println(hitCallOtherNumbers.size());
            
        }else {
        	System.out.println("空");
        	int hit_bd_tag_cnt = 0;
        	int hit_number_c_cnt = 0;
        	int r_number_user_cnt = 0;
        	
        }
	}

}
