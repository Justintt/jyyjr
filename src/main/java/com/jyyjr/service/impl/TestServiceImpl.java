package com.jyyjr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.jyyjr.common.Message;
import com.jyyjr.dao.csdao.JmVidMapper;
import com.jyyjr.dao.csdao.Yy20180521DataMapper;
import com.jyyjr.dao.txdao.MobileCallrecordMapper;
import com.jyyjr.dao.ywdao.UserMapper;
import com.jyyjr.dao.zxdao.MobileBasicinfoMapper;
import com.jyyjr.pojo.User;
import com.jyyjr.pojo.Yy20180521Data;
import com.jyyjr.service.TestService;
import com.jyyjr.util.HttpUtils;
import com.jyyjr.util.TimeUtils;

@Service("testService")
public class TestServiceImpl implements TestService{
	
	private static final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);
	
	@Autowired
	private MobileBasicinfoMapper mobileBasicinfoMapper;
	@Autowired
	private MobileCallrecordMapper mobileCallrecordMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private Yy20180521DataMapper yy20180521DataMapper;
	@Autowired
	private JmVidMapper jmVidMapper;
	
	/**
	 * 测试事务
	 */
	@Transactional("cs")
	public Integer insertVid() {
		List<String> lists = new ArrayList<>();
		lists.add("1c05b3f980c9ea600ef8219815c1bf58");
		lists.add("2d7603bd72d81b282bd2683cc100d877");
		lists.add("e201824962918ffd7cbab0c0e2a1a7e3");
		lists.add("d94da6fe40c6ee96bbc716d7b5059716");
		lists.add("2c1401d0ab9463021e5f3b5a38f84122");
		lists.add("4ac92a71ad66a1f87832e91c89950db8");
		lists.add("6fa4b0f509f919e4e0586794e446e226");
		lists.add("23b86affab2e07ce07aa6338bed86b1b");
		lists.add("1c05b3f980c9ea600ef8219815c1bf58");
		lists.add("2d7603bd72d81b282bd2683cc100d877");
		lists.add("e201824962918ffd7cbab0c0e2a1a7e3");
		lists.add("d94da6fe40c6ee96bbc716d7b5059716");
		lists.add(null);
		lists.add("2c1401d0ab9463021e5f3b5a38f84122");
		lists.add("4ac92a71ad66a1f87832e91c89950db8");
		lists.add("23b86affab2e07ce07aa6338bed86b1b");
		lists.add("1c05b3f980c9ea600ef8219815c1bf58");
		lists.add("2d7603bd72d81b282bd2683cc100d877");
		lists.add("e201824962918ffd7cbab0c0e2a1a7e3");
		lists.add("d94da6fe40c6ee96bbc716d7b5059716");
		lists.add("2c1401d0ab9463021e5f3b5a38f84122");
		lists.add("4ac92a71ad66a1f87832e91c89950db8");
		lists.add("6fa4b0f509f919e4e0586794e446e226");
		
		for(String vid : lists) {
			int rowCount = jmVidMapper.insertVid(vid);
		}
		return 1001;
		
	}
	
	/**
	 * 近3个月连续静默3天的次数
	 * @param vid
	 * @param mymobile
	 */
	public int getBinCallAMaxDaysM1(String vid) {
		Long ctime = mobileBasicinfoMapper.selectCtimeByVid(vid);
		int bin_call_scilence_3d_m3 = 0;
		if (ctime==null) {
			return bin_call_scilence_3d_m3;
		}
		User user = userMapper.selectUserByVid(vid);
		long mymobile = user.getMobile();
		//获取用户近3个月的通话开始时间
		List<Long> callTimeList = mobileCallrecordMapper.selectCallTimeByMyMobile(mymobile, ctime);
		//近3个月连续静默3天的次数
		bin_call_scilence_3d_m3 = jmDayCount(callTimeList,ctime);
		return bin_call_scilence_3d_m3;
	}
	/**
	 * 连续三天静默次数
	 * @param callTimeList
	 * @return
	 */
	public int jmDayCount(List<Long> callTimeList,Long ctime) {
		long fastTime = TimeUtils.zeroPoint(ctime);
		long lastTime = TimeUtils.reduceDate(fastTime, 3);
		callTimeList.add(0,fastTime);
		callTimeList.add(lastTime);
		int count = 0;
		for(int i=0;i<callTimeList.size()-1;i++) {
			if (callTimeList.size()>1) {
				double size =(callTimeList.get(i)-callTimeList.get(i+1))/86400;
				if (size>3) {
					int num = (int)(size-1)/3;
					count +=num;
				}
			}
		}
		return count;
	}
	
	/**
	 * 放回
	 * @param vid
	 * @return
	 */
	public Message<Integer> getJmDay(String vid) {
		int dayCount = getBinCallAMaxDaysM1(vid);
		Yy20180521Data yy20180521Data = new Yy20180521Data();
		yy20180521Data.setVid(vid);
		yy20180521Data.setBinCallScilence3dM3(dayCount);
		yy20180521DataMapper.updateByPrimaryKeySelective(yy20180521Data);
		return new Message<>(Message.SUCCESS, "正常",dayCount);
		
	}
	
	/**
	 * 错误测试
	 * @author 作者 jinmin
	 * @date 创建时间：2018年6月2日 下午5:38:01
	 * @return
	 */
	public int testError() {
		try {
			int a = 332;
			int b = 0;
			int c = a/b;
			return c;
		} catch (Exception e) {
			logger.error("出错啦"+e);
		}
		return 2;
	}
	
	public String checkK0039(String vid) {
    	User userBean = userMapper.selectUserByVid(vid);
    	String name = userBean.getTruename();
    	String mobile = String.valueOf(userBean.getMobile());
    	try {
    		String response = HttpUtils.sendGet("http://116.62.146.43:8199/getinfo/svmffw", "name="+name+"&mobile="+mobile);
        	logger.info(vid+"：米房数据:"+response+":name,"+name+",mobile:"+mobile);
    		JSONObject jsonObject = JSONObject.parseObject(response);
    		int status = jsonObject.getIntValue("status");
    		if (status!=1001) {
    			logger.info(vid+":"+"米房接口失败");
				return "失败";
			}
    		JSONObject dataJson = JSONObject.parseObject(jsonObject.getString("data"));
    		int code = dataJson.getIntValue("code");
    		if (code == 1) {
    			logger.info(vid+"：命中K0039,米房接口");
				return "失败";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(vid+":米房接口出错"+e);
			logger.info(vid+":米房接口出错");
		}
    	logger.info(vid+":K0039通过");
    	return "成功";
    }

}
