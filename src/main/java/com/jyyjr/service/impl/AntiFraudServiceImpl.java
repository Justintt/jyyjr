package com.jyyjr.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.jyyjr.common.Message;
import com.jyyjr.dao.txdao.MobileCallrecordMapper;
import com.jyyjr.dao.ywdao.MobileClearYYMapper;
import com.jyyjr.dao.ywdao.TestMobilecallMapper;
import com.jyyjr.dao.ywdao.UserMapper;
import com.jyyjr.dao.ywdao.UserWokeVerifyMapper;
import com.jyyjr.dao.zxdao.AnRiskParamMapper;
import com.jyyjr.dao.zxdao.ArReportMapper;
import com.jyyjr.dao.zxdao.BehaviorAnalysisMapper;
import com.jyyjr.dao.zxdao.MobileBasicinfoMapper;
import com.jyyjr.dao.zxdao.QhUserLoanPlatformMapper;
import com.jyyjr.dao.zxdao.QhUserRiskHintMapper;
import com.jyyjr.dao.zxdao.TdDataMapper;
import com.jyyjr.pojo.User;
import com.jyyjr.service.AntiFraudService;
import com.jyyjr.util.HttpUtils;
import com.jyyjr.util.TimeUtils;
import com.jyyjr.vo.AntiFraudMsg;


@Service("antiFraudService")
public class AntiFraudServiceImpl implements AntiFraudService{
	
	private final Logger logger =  LoggerFactory.getLogger(AntiFraudServiceImpl.class);
	
	@Autowired
	private ArReportMapper arReportMapper;
	@Autowired
	private TdDataMapper tdDataMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private QhUserLoanPlatformMapper qhUserLoanPlatformMapper;
	@Autowired
	private MobileBasicinfoMapper mobileBasicinfoMapper;
	@Autowired
	private TestMobilecallMapper testMobilecallMapper;
	@Autowired
	private MobileCallrecordMapper mobileCallrecordMapper;
	@Autowired
	private MobileClearYYMapper mobileClearYYMapper;
	@Autowired
	private UserWokeVerifyMapper userWokeVerifyMapper;
	@Autowired
	private QhUserRiskHintMapper qhUserRiskHintMapper;
	@Autowired
	private AnRiskParamMapper anRiskParamMapper;
	@Autowired
	private BehaviorAnalysisMapper behaviorAnalysisMapper;
	
	/**
	 * 获取反欺诈3.01数据
	 * @param vid
	 * @return
	 */
	public AntiFraudMsg getAntiFraudMsg(String vid) {
		AntiFraudMsg antiFraudMsg = new AntiFraudMsg();
		antiFraudMsg.setStatus(1001);
		antiFraudMsg.setMessage("正常");
		User user = userMapper.selectUserByVid(vid);
        if (user==null) {
        	antiFraudMsg.setStatus(1002);
    		antiFraudMsg.setMessage("用户不存在或vid有误");
    		return antiFraudMsg;
		}
		Integer  ctime = user.getCtime();
		long mymobile = user.getMobile();
		
		//安融申请总次数
		/*Integer ar_apply_all_cnt = arReportMapper.selectBinArApplyAllCnt(vid);
		int bin_ar_apply_all_cnt = ar_apply_all_cnt==null?0:ar_apply_all_cnt;*/
		//最新同盾7天申请次数bin_td_7days
		/*Integer td_7days = tdDataMapper.selectTdSevenDayByVid(vid);
		int bin_td_7days = td_7days==null?0:td_7days;*/
		//安融申请未通过次数占比
		/*Integer ar_apply_notpass = arReportMapper.selectBinPArApplyNotpass(vid);
		int p_ar_apply_notpass = ar_apply_notpass==null?0:ar_apply_notpass;
		double bin_p_ar_apply_notpass = 0.0;
		if (bin_ar_apply_all_cnt != 0 && p_ar_apply_notpass != 0) {
			bin_p_ar_apply_notpass = (double)p_ar_apply_notpass/bin_ar_apply_all_cnt;
		}*/
		//前海近3个月业务发生的次数
		/*Integer qh_busi_cnt_m3 = qhUserLoanPlatformMapper.selectBinQhBusiCntM3(vid, ctime);
		int bin_qh_busi_cnt_m3 = qh_busi_cnt_m3==null?0:qh_busi_cnt_m3;
		//命中p2p或者小贷机构数（前海）& 近三个月小贷机构查询次数（前海）
		Map<String, Integer> qhP2pM3Map = qhUserLoanPlatformMapper.selectCpqByVid(vid);
		int bin_qh_p2p_Amount = 0;
		int bin_qh_query_m3 = 0;
		if (qhP2pM3Map!=null) {
			Integer p2pAmount = qhP2pM3Map.get("p2pAmount");
			Integer queryAmtM3 = qhP2pM3Map.get("queryAmtM3");
			bin_qh_p2p_Amount = p2pAmount==null?0:p2pAmount;
			bin_qh_query_m3 = queryAmtM3==null?0:queryAmtM3;
		}*/
		//近1月最大连续通话活跃天数
		int[] callArr = getBinCallAMaxDaysM1(vid,mymobile);
		int bin_call_a_max_days_m1 = callArr[0];//1
		int bin_call_scilence_3d_m3 = callArr[1];//2
		//清洗后通讯录互通号码数量
		Integer call_io_cnt = mobileClearYYMapper.selectBinCallIoCnt(vid);
		int bin_call_io_cnt = call_io_cnt==null?0:call_io_cnt;//3
		//通话时长极短（低于30秒）的通话次数占比
		Double call_30s_rate = mobileCallrecordMapper.selectBinCall30sRate(mymobile);
		double bin_call_30s_rate = call_30s_rate==null?0:call_30s_rate;//4
		//夜间（23点-5点）通话次数占比
		Double call_night_rate = mobileCallrecordMapper.selectBinCallNightRate(mymobile);
		double bin_call_night_rate = call_night_rate==null?0:call_night_rate;//5
		//清洗后的通讯录有实际通话的数量（剔除只被叫或主叫1次的号码）
		int bin_mobile_cnt_clr = getBinMobileCntClr(vid);//6
		//同一工作单位的关系用户数
		int bin_company_users = getBinCompanyUsers(vid);//8
		//交叉联系人逾期未还人数&用户通话记录中的联系人在本平台的借款逾期人数
		int[] jcArr = getBinJcOverdueUsers(vid);
		int bin_jc_overdue_no_pay_users = jcArr[0];//7
		int bin_jc_overdue_users = jcArr[1];//9
		//前海A信贷逾期风险发生的次数
		/*Integer qh_Aloan_cnt = qhUserRiskHintMapper.selectNsourceIdByVid(vid);
		int bin_qh_Aloan_cnt = qh_Aloan_cnt==null?0:qh_Aloan_cnt;*/
		//是否存在逾期信息（安融）& 是否存在单笔合同2次或以上逾期信息& 是否存在2笔或以上不同合同逾期信息
		/*Map<String, String> anMap = anRiskParamMapper.selectD123ByVid(vid);
		String bin_ar_d001 = null;
		String bin_ar_d002 = null;
		String bin_ar_d003 = null;
		if (anMap!=null) {
			bin_ar_d001 =  anMap.get("d001");
			bin_ar_d002 =  anMap.get("d002");
			bin_ar_d003 =  anMap.get("d003");
		}*/
		//近6个月催收号码联系情况（魔盒）& 近6个月小贷号码联系情况（魔盒）
		/*Map<String, String> mhMap = behaviorAnalysisMapper.selectBaMsgByVid(vid);
		String bin_collection_contact_analysis_m6 = null;
		String bin_loan_contact_analysis_m6 = null;
		if (mhMap!=null) {
			bin_collection_contact_analysis_m6 = mhMap.get("collection_contact_analysis_6month");
			bin_loan_contact_analysis_m6 = mhMap.get("loan_contact_analysis_6month");
		}*/
		
		
		
		antiFraudMsg.setBin_call_a_max_days_m1(bin_call_a_max_days_m1);
		antiFraudMsg.setBin_call_scilence_3d_m3(bin_call_scilence_3d_m3);
		antiFraudMsg.setBin_call_io_cnt(bin_call_io_cnt);
		antiFraudMsg.setBin_call_30s_rate(bin_call_30s_rate);
		antiFraudMsg.setBin_call_night_rate(bin_call_night_rate);
		antiFraudMsg.setBin_mobile_cnt_clr(bin_mobile_cnt_clr);
		antiFraudMsg.setBin_company_users(bin_company_users);
		antiFraudMsg.setBin_jc_overdue_no_pay_users(bin_jc_overdue_no_pay_users);
		antiFraudMsg.setBin_jc_overdue_users(bin_jc_overdue_users);
		
		
		return antiFraudMsg;
	}
	
	/**
	 * 反欺诈3.01结果
	 * @param vid
	 * @return
	 */
	public Message<Map<String, Object>> getAntiFraudCard(String vid) {
		Map<String, Object> resultMap = new HashMap<>();
		AntiFraudMsg antiFraudMsg = getAntiFraudMsg(vid);
		int status = antiFraudMsg.getStatus();
		String message = antiFraudMsg.getMessage();
		if (status==1002) {
			return new Message<>(Message.FAIL, message);
		}
		/*int bin_ar_apply_all_cnt = antiFraudMsg.getBin_ar_apply_all_cnt();
		int bin_td_7days = antiFraudMsg.getBin_td_7days();
		double bin_p_ar_apply_notpass = antiFraudMsg.getBin_p_ar_apply_notpass();
		int bin_qh_busi_cnt_m3 = antiFraudMsg.getBin_qh_busi_cnt_m3();
		int bin_qh_p2p_Amount = antiFraudMsg.getBin_qh_p2p_Amount();
		int bin_qh_query_m3 = antiFraudMsg.getBin_qh_query_m3();*/
		int bin_call_a_max_days_m1 = antiFraudMsg.getBin_call_a_max_days_m1();//
		int bin_call_scilence_3d_m3 = antiFraudMsg.getBin_call_scilence_3d_m3();//
		int bin_call_io_cnt = antiFraudMsg.getBin_call_io_cnt();//
		double bin_call_30s_rate = antiFraudMsg.getBin_call_30s_rate();//
		double bin_call_night_rate = antiFraudMsg.getBin_call_night_rate();//
		int bin_mobile_cnt_clr = antiFraudMsg.getBin_mobile_cnt_clr();//
		int bin_company_users = antiFraudMsg.getBin_company_users();//
		int bin_jc_overdue_no_pay_users = antiFraudMsg.getBin_jc_overdue_no_pay_users();//
		int bin_jc_overdue_users = antiFraudMsg.getBin_jc_overdue_users();//
		/*int bin_qh_Aloan_cnt = antiFraudMsg.getBin_qh_Aloan_cnt();
		String bin_ar_d001 = antiFraudMsg.getBin_ar_d001();
		String bin_ar_d002 = antiFraudMsg.getBin_ar_d002();
		String bin_ar_d003 = antiFraudMsg.getBin_ar_d003();
		String bin_collection_contact_analysis_m6 = antiFraudMsg.getBin_collection_contact_analysis_m6();
		String bin_loan_contact_analysis_m6 = antiFraudMsg.getBin_loan_contact_analysis_m6();*/
		
		double score = 0.0;
		/*if (bin_ar_apply_all_cnt<=0) {
			score += 4.26318536938903;
		}else if (bin_ar_apply_all_cnt<=1) {
			score += 3.51496706353077;
		}else if (bin_ar_apply_all_cnt<=2) {
			score += 6.45567527811398;
		}else if (bin_ar_apply_all_cnt<=5) {
			score += 8.52194436229806;
		}else if (bin_ar_apply_all_cnt<=14) {
			score += 10.8529197444848;
		}else if (bin_ar_apply_all_cnt>14) {
			score += 4.24943910354053;
		}*/
		
		/*if (bin_td_7days<=4) {
			score += -0.387956053457367;
		}else if (bin_td_7days<=11) {
			score += 5.97506322242284;
		}else if (bin_td_7days<=15) {
			score += 8.47125426520359;
		}else if (bin_td_7days>15) {
			score += 10.8465993385732;
		}*/
		
		/*if (bin_p_ar_apply_notpass<=0.4075) {
			score += 4.1553712686937;
		}else if (bin_p_ar_apply_notpass<=0.5358) {
			score += 5.11444740326733;
		}else if (bin_p_ar_apply_notpass<=0.8056) {
			score += 8.74594250134854;
		}else if (bin_p_ar_apply_notpass<=0.96) {
			score += 11.3266106021287;
		}else if (bin_p_ar_apply_notpass>0.96) {
			score += 7.78314757781541;
		}*/
		
		/*if (bin_qh_busi_cnt_m3<=7) {
			score += 6.90915882181211;
		}else if (bin_qh_busi_cnt_m3<=14) {
			score += 9.16737085889186;
		}else if (bin_qh_busi_cnt_m3>14) {
			score += 2.52142083661238;
		}*/
		
		/*if (bin_qh_p2p_Amount<=11) {
			score += 5.97933403323158;
		}else if (bin_qh_p2p_Amount<=14) {
			score += 6.46285042830923;
		}else if (bin_qh_p2p_Amount<=26) {
			score += 8.37359823902943;
		}else if (bin_qh_p2p_Amount>26) {
			score += 5.44320307567144;
		}*/
		
		/*if (bin_qh_query_m3<=8) {
			score += 9.40447763813548;
		}else if (bin_qh_query_m3<=11) {
			score += 8.31695126261764;
		}else if (bin_qh_query_m3<=20) {
			score += 6.09871371680225;
		}else if (bin_qh_query_m3>20) {
			score += 3.7840514823743;
		}*/
		
		if (bin_call_a_max_days_m1<=2) {
			score += 13.6322049874241;
		}else if (bin_call_a_max_days_m1<=5) {
			score += 15.1625382097034;
		}else if (bin_call_a_max_days_m1<=19) {
			score += 8.26613936124297;
		}else if (bin_call_a_max_days_m1>19) {
			score += 5.12898593195069;
		}
		
		if (bin_call_scilence_3d_m3<=0) {
			score += 5.29861416099491;
		}else if (bin_call_scilence_3d_m3>0) {
			score += 14.3352386260147;
		}
		
		if (bin_call_io_cnt<=5) {
			score += 12.795443846341;
		}else if (bin_call_io_cnt<=13) {
			score += 11.4619977874503;
		}else if (bin_call_io_cnt<=22) {
			score += 7.23145069513105;
		}else if (bin_call_io_cnt<=47) {
			score += 6.02706250840661;
		}else if (bin_call_io_cnt<=68) {
			score += 2.92681715689054;
		}else if (bin_call_io_cnt>68) {
			score += -5.36048411768378;
		}
		
		if (bin_call_30s_rate<=0.2535) {
			score += 10.1815911150287;
		}else if (bin_call_30s_rate<=0.3334) {
			score += 9.3894589931473;
		}else if (bin_call_30s_rate<=0.4798) {
			score += 6.83101313319821;
		}else if (bin_call_30s_rate<=0.5267) {
			score += 4.72746542058994;
		}else if (bin_call_30s_rate>0.5267) {
			score += 5.61425825575908;
		}
		
		if (bin_call_night_rate<=0.0094) {
			score += 1.80472165327764;
		}else if (bin_call_night_rate<=0.0125) {
			score += 0.157951569542345;
		}else if (bin_call_night_rate<=0.028) {
			score += 5.39612660825694;
		}else if (bin_call_night_rate<=0.0517) {
			score += 7.35299764834869;
		}else if (bin_call_night_rate<=0.105) {
			score += 11.7648922080363;
		}else if (bin_call_night_rate>0.105) {
			score += 23.8728073176781;
		}
		
		if (bin_mobile_cnt_clr<=7) {
			score += 14.031671945597;
		}else if (bin_mobile_cnt_clr<=13) {
			score += 13.6519289150549;
		}else if (bin_mobile_cnt_clr<=24) {
			score += 8.07502522466547;
		}else if (bin_mobile_cnt_clr<=52) {
			score += 6.48920098802295;
		}else if (bin_mobile_cnt_clr<=101) {
			score += 3.00478359597218;
		}else if (bin_mobile_cnt_clr>101) {
			score += -7.12024569847382;
		}
		
		if (bin_company_users<=0) {
			score += 6.02175777040617;
		}else if (bin_company_users>0) {
			score += 13.1976604341557;
		}
		
		if (bin_jc_overdue_no_pay_users<=0) {
			score += 6.31100991452062;
		}else if (bin_jc_overdue_no_pay_users<=2) {
			score += 9.13546570824802;
		}else if (bin_jc_overdue_no_pay_users>2) {
			score += 10.3131817480471;
		}
		
		if (bin_jc_overdue_users<=2) {
			score += 7.03684513045809;
		}else if (bin_jc_overdue_users>2) {
			score += 9.25331326505843;
		}
		
		/*if (bin_qh_Aloan_cnt<=0) {
			score += 6.89083855766176;
		}else if (bin_qh_Aloan_cnt<=1) {
			score += 11.536254035198;
		}else if (bin_qh_Aloan_cnt<=3) {
			score += 8.15598042267258;
		}else if (bin_qh_Aloan_cnt>3) {
			score += 5.53652872702175;
		}*/
		
		/*if (bin_ar_d001==null) {
			score += 0;
		}else if (bin_ar_d001.equals("0")) {
			score += 0.638892774270533;
		}else if (bin_ar_d001.equals("1")) {
			score += -13.6892362785567;
		}*/
		
		/*if (bin_ar_d002==null) {
			score += 0;
		}else if (bin_ar_d002.equals("0")) {
			score += -0.969977013525473;
		}else if (bin_ar_d002.equals("1")) {
			score += -21.3438612588597;
		}*/
		
		/*if (bin_ar_d003==null) {
			score += 0;
		}else if (bin_ar_d003.equals("0")) {
			score += -0.208639599374713;
		}else if (bin_ar_d003.equals("1")) {
			score += -18.2366139104196;
		}*/
		
		/*if (bin_collection_contact_analysis_m6==null) {
			score += 0;
		}else if (bin_collection_contact_analysis_m6.equals("从未通话")) {
			score += 7.72172220207589;
		}else if (bin_collection_contact_analysis_m6.equals("偶尔通话")) {
			score += 9.42072015012674;
		}else if (bin_collection_contact_analysis_m6.equals("频繁通话")) {
			score += 8.86116978191109;
		}*/
		
		/*if (bin_loan_contact_analysis_m6==null) {
			score += 0;
		}else if (bin_loan_contact_analysis_m6.equals("从未通话")) {
			score += 7.23440293772476;
		}else if (bin_loan_contact_analysis_m6.equals("偶尔通话")) {
			score += 5.61170578660783;
		}else if (bin_loan_contact_analysis_m6.equals("频繁通话")) {
			score += 8.27121919928435;
		}*/
		
		String grade = null;
		if(score>20 && score<=53) {
			grade = "AAA";
		}else if (score>53 && score<=60) {
			grade = "AA";
		}else if (score>60 && score<=68) {
			grade = "A";
		}else if (score>68 && score<=75) {
			grade = "BBB";
		}else if (score>75 && score<=82) {
			grade = "BB";
		}else if (score>82 && score<=92) {
			grade = "B";
		}else if (score>92) {
			grade = "C";
		}
		
		resultMap.put("vid", vid);
		resultMap.put("score", score);
		resultMap.put("grade", grade);
		
		return new Message<>(Message.SUCCESS, message,resultMap);
	}
	
	/**
	 * 近1月最大连续通话活跃天数
	 * 近3个月连续静默3天的次数
	 * @param vid
	 * @param mymobile
	 */
	public int[] getBinCallAMaxDaysM1(String vid,long mymobile) {
		Long ctime = mobileBasicinfoMapper.selectCtimeByVid(vid);
		List<Long> dateCallList = mobileCallrecordMapper.selectBinCallAMaxDaysM1(mymobile, ctime);
		//近1月最大连续通话活跃天数
		int bin_call_a_max_days_m1 = dayCount(dateCallList);
		//获取用户近3个月的通话开始时间
		List<Long> callTimeList = mobileCallrecordMapper.selectCallTimeByMyMobile(mymobile, ctime);
		//近3个月连续静默3天的次数
		int bin_call_scilence_3d_m3 = jmDayCount(callTimeList,ctime);
		int[] arr = new int[2];
		arr[0] = bin_call_a_max_days_m1;
		arr[1] = bin_call_scilence_3d_m3;
		return arr;
	}
	
	/**
	 * 清洗后的通讯录有实际通话的数量（剔除只被叫或主叫1次的号码）
	 */
	public int getBinMobileCntClr(String vid) {
		int bin_mobile_cnt_clr = 0;
		try {
			String response = HttpUtils.sendGet("http://fk.jyyjrfw.com/Interface/get_inbook_gt_onecall_user_count", "vid="+vid);
			JSONObject jsonObject = JSONObject.parseObject(response);
			String status = jsonObject.getString("status");
			if (status.equals("1001")) {
				JSONObject resultJson = jsonObject.getJSONObject("result");
				bin_mobile_cnt_clr = resultJson.getInteger("get_inbook_gt_onecall_user_count");
			}
		} catch (Exception e) {
			logger.error(vid+"：清洗后的通讯录有实际通话的数量接口出错");
		}
		return bin_mobile_cnt_clr;
	}
	
	/**
	 * 用户通话记录中的联系人在本平台的借款逾期人数
	 * @param vid
	 * @param mymobile
	 * @return
	 */
	public int[] getBinJcOverdueUsers(String vid) {
		int bin_jc_overdue_no_pay_users = 0;
		int bin_jc_overdue_users = 0;
		try {
			String response = HttpUtils.sendGet("http://fk.jyyjrfw.com/zjdata/find_jx", "vid="+vid);
			JSONObject jsonObject = JSONObject.parseObject(response);
			String status = jsonObject.getString("status");
			if (status.equals("1001")) {
				JSONObject resultJson = jsonObject.getJSONObject("result");
				bin_jc_overdue_no_pay_users = resultJson.getInteger("c_o_user_norepay_count");
				bin_jc_overdue_users = resultJson.getInteger("c_o_user_count");
			}
		} catch (Exception e) {
			logger.error(vid+"：交叉接口出错");
		}
		int[] arr = new int[2];
		arr[0] = bin_jc_overdue_no_pay_users;
		arr[1] = bin_jc_overdue_users;
		
		/*List<String> callOtherNumbers = mobileCallrecordMapper.selectNumber11ByMyMobile(mymobile);
		List<String> jc_overdue_users = userMapper.selectBinJcOverdueUsers(callOtherNumbers);
		int bin_jc_overdue_users = jc_overdue_users.size();*/
		return arr;
	}
	
	/**
	 * 同一工作单位的关系用户数
	 * @param vid
	 * @return
	 */
	public int getBinCompanyUsers(String vid) {
		String workCompany = userWokeVerifyMapper.selectWorkCompany(vid);
		List<String> companyUsers = userWokeVerifyMapper.selectBinCompanyUsers(workCompany);
		int bin_company_users = 0;
		if(companyUsers.size()>0) {
			bin_company_users = companyUsers.size();
		}
		return bin_company_users;
	}
	
	/**
	 * 计算连续天数
	 * @param dateCallList
	 * @return
	 */
	public int dayCount(List<Long> dateCallList) {
		dateCallList.add(0L);
		int count = 1;
		List<Integer> list = new ArrayList<>();
		list.add(1);
		for(int i=0;i<dateCallList.size()-1;i++) {
			if (dateCallList.size()>1) {
				double size =(dateCallList.get(i)-dateCallList.get(i+1))/86400;
				if (size<=1) {
					count ++;
				}else {
					list.add(count);
					count = 1;
				}
			}
		}
		int maxDay = Collections.max(list);
		return maxDay;
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
}
