package com.jyyjr.bench;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.jyyjr.common.CloseAll;
import com.jyyjr.util.DBUtil;
import com.jyyjr.util.HttpUtils;

public class Yy20180429 {
	
	private static final String URL = "https://jallzf.miaodaoxj.com:444/jyyjr/randomForest";

	public List<Map<String, Object>> getBorrow() {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		List<Map<String, Object>> list = new ArrayList<>();
		try {
			conn = DBUtil.getZDYW();
			state = conn.createStatement();
			String sql2 = "SELECT r.vid,b.money,FROM_UNIXTIME(b.get_time) get_time,FROM_UNIXTIME(r.repay_time) repay_time,r.borrow_no,r.`status` FROM dxd_caiwu.user_repayment r INNER JOIN (SELECT vid,get_time,money,borrow_no,COUNT(*) nun FROM dxd_caiwu.user_borrow WHERE is_success=2 GROUP BY vid ) b ON b.borrow_no=r.borrow_no AND is_overdue=1 AND b.nun=1 AND r.`status`=1";
			rs = state.executeQuery(sql2);
			while(rs.next()) {
				Map<String, Object> map = new HashMap<>();
				map.put("vid", rs.getString("vid"));
				map.put("money", rs.getInt("money"));
				list.add(map);
			}
			
		} catch (SQLException e) {
			System.out.println("连接失败");
			e.printStackTrace();
		}finally{
			CloseAll.close(rs, state, conn);
		}
		return list;
	}
	
	public static void main(String[] args) {
		Yy20180429 yy20180429 = new Yy20180429();
		List<Map<String, Object>> lists = yy20180429.getBorrow();
		int index = 1;
		for(Map<String, Object> list : lists) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String vid= (String) list.get("vid");
			int money = (Integer) list.get("money");
			String param = "vid="+vid;
			String resutl = HttpUtils.sendGet(URL, param);
			JSONObject jsonObject = JSONObject.parseObject(resutl);
			int status = jsonObject.getIntValue("isSuccess");
			if (status==1) {
				JSONObject json =  (JSONObject) jsonObject.get("data");
				String probability_bad = json.getString("Probability_bad");
				String probability_good = json.getString("Probability_good");
				String predicted_ifgood = json.getString("Predicted_ifgood");
				System.out.println(vid+":"+money+":"+probability_bad+":"+probability_good+":"+predicted_ifgood+":"+index++);
			}else {
				System.out.println(vid+":"+money+":"+index++);
			}
			
		}
	}
}
