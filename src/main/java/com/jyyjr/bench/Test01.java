package com.jyyjr.bench;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jyyjr.common.CloseAll;
import com.jyyjr.util.DBUtil;
import com.jyyjr.util.HttpUtils;
import com.jyyjr.util.TimeUtils;



public class Test01 {
	
	/**
	 * 获取时间
	 * @param vid
	 * @return
	 */
	/*public List<Map<String,Object>> getCtime(){
		Connection conn = null;
		List<Map<String,Object>> lists = new ArrayList<Map<String,Object>>();
		Statement state = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getZDYW();
			state = conn.createStatement();
			String sql = "SELECT vid, ctime, cert_time FROM dxd_user.`user` WHERE cert_status=2";
			rs = state.executeQuery(sql);
			while(rs.next()) {
				Map<String,Object> map = new HashMap<>();
				map.put("vid", rs.getString("vid"));
				map.put("ctime", TimeUtils.dateTime(rs.getLong("ctime")));
				map.put("cert_time", TimeUtils.dateTime(rs.getLong("cert_time")));
				lists.add(map);
			}
		} catch (SQLException e) {
			System.out.println("连接失败");
			e.printStackTrace();
		}finally{
			CloseAll.close(rs, state, conn);
		}
		return lists;
	}*/
	
	
	public String getNameByVid(String vid) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		String name = "";
		try {
			conn = DBUtil.getZDYW();
			state = conn.createStatement();
			String sql = "SELECT vid,truename FROM dxd_user.`user` WHERE vid='"+vid+"'";
			rs = state.executeQuery(sql);
			while(rs.next()) {
				name = rs.getString("truename");
			}
		} catch (SQLException e) {
			System.out.println("连接失败");
			e.printStackTrace();
		}finally{
			CloseAll.close(rs, state, conn);
		}
		return name;
		
	}
	
	public List<String> getUserVid() {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<>();
		try {
			conn = DBUtil.getZDYW();
			state = conn.createStatement();
			String sql2 = "SELECT vid FROM dxd_kefu.tmp_user WHERE `status`=1 ORDER BY vid LIMIT 100";
			rs = state.executeQuery(sql2);
			while(rs.next()) {
				list.add(rs.getString("vid"));
			}
			
		} catch (SQLException e) {
			System.out.println("连接失败");
			e.printStackTrace();
		}finally{
			CloseAll.close(rs, state, conn);
		}
		return list;
	}
	
	/*public static void main(String[] args) throws InterruptedException {
		List<String> lists = new ArrayList<>();
		
		lists.add("00448456000365");
		lists.add("057688215163");
		lists.add("13018888831");
		lists.add("13216998788");
		lists.add("13221615086");
		lists.add("13235767676");
		lists.add("13291616267");
		lists.add("13362632225");
		lists.add("13362633660");
		lists.add("13454671999");
		lists.add("13486881026");
		lists.add("13606687770");
		lists.add("13606689919");
		lists.add("13616695052");
		lists.add("13736599600");
		lists.add("13757631818");
		lists.add("13806595533");
		lists.add("13857667883");
		lists.add("13906579828");
		lists.add("15057608114");
		lists.add("15168621797");
		lists.add("15236800162");
		lists.add("15249991116");
		lists.add("15557635531");
		lists.add("15857631212");
		lists.add("17081748891");
		lists.add("18225488112");
		lists.add("18658304404");
		lists.add("18968607733");
		lists.add("18969681111");
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
		lists.add("2c1401d0ab9463021e5f3b5a38f84122");
		lists.add("4ac92a71ad66a1f87832e91c89950db8");
		lists.add("6fa4b0f509f919e4e0586794e446e226");
		lists.add("23b86affab2e07ce07aa6338bed86b1b");
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
		lists.add("2c1401d0ab9463021e5f3b5a38f84122");
		lists.add("4ac92a71ad66a1f87832e91c89950db8");
		lists.add("6fa4b0f509f919e4e0586794e446e226");
		lists.add("23b86affab2e07ce07aa6338bed86b1b");
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
		lists.add("2c1401d0ab9463021e5f3b5a38f84122");
		lists.add("4ac92a71ad66a1f87832e91c89950db8");
		lists.add("6fa4b0f509f919e4e0586794e446e226");
		lists.add("23b86affab2e07ce07aa6338bed86b1b");
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
		lists.add("2c1401d0ab9463021e5f3b5a38f84122");
		lists.add("4ac92a71ad66a1f87832e91c89950db8");
		lists.add("6fa4b0f509f919e4e0586794e446e226");
		lists.add("23b86affab2e07ce07aa6338bed86b1b");
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
		lists.add("2c1401d0ab9463021e5f3b5a38f84122");
		lists.add("4ac92a71ad66a1f87832e91c89950db8");
		lists.add("6fa4b0f509f919e4e0586794e446e226");
		lists.add("23b86affab2e07ce07aa6338bed86b1b");
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
		lists.add("2c1401d0ab9463021e5f3b5a38f84122");
		lists.add("4ac92a71ad66a1f87832e91c89950db8");
		lists.add("6fa4b0f509f919e4e0586794e446e226");
		lists.add("23b86affab2e07ce07aa6338bed86b1b");
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
		lists.add("2c1401d0ab9463021e5f3b5a38f84122");
		lists.add("4ac92a71ad66a1f87832e91c89950db8");
		lists.add("6fa4b0f509f919e4e0586794e446e226");
		lists.add("23b86affab2e07ce07aa6338bed86b1b");
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
		lists.add("2c1401d0ab9463021e5f3b5a38f84122");
		lists.add("4ac92a71ad66a1f87832e91c89950db8");
		lists.add("6fa4b0f509f919e4e0586794e446e226");
		lists.add("23b86affab2e07ce07aa6338bed86b1b");
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
		lists.add("2c1401d0ab9463021e5f3b5a38f84122");
		lists.add("4ac92a71ad66a1f87832e91c89950db8");
		lists.add("6fa4b0f509f919e4e0586794e446e226");
		lists.add("23b86affab2e07ce07aa6338bed86b1b");
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
		lists.add("2c1401d0ab9463021e5f3b5a38f84122");
		lists.add("4ac92a71ad66a1f87832e91c89950db8");
		lists.add("6fa4b0f509f919e4e0586794e446e226");
		lists.add("23b86affab2e07ce07aa6338bed86b1b");
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
		lists.add("2c1401d0ab9463021e5f3b5a38f84122");
		lists.add("4ac92a71ad66a1f87832e91c89950db8");
		lists.add("6fa4b0f509f919e4e0586794e446e226");
		lists.add("23b86affab2e07ce07aa6338bed86b1b");
		lists.add("1c05b3f980c9ea600ef8219815c1bf58");
		lists.add("2d7603bd72d81b282bd2683cc100d877");
		lists.add("e201824962918ffd7cbab0c0e2a1a7e3");
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
		lists.add("2c1401d0ab9463021e5f3b5a38f84122");
		lists.add("4ac92a71ad66a1f87832e91c89950db8");
		lists.add("6fa4b0f509f919e4e0586794e446e226");
		lists.add("23b86affab2e07ce07aa6338bed86b1b");
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
		lists.add("2c1401d0ab9463021e5f3b5a38f84122");
		lists.add("4ac92a71ad66a1f87832e91c89950db8");
		lists.add("6fa4b0f509f919e4e0586794e446e226");
		lists.add("23b86affab2e07ce07aa6338bed86b1b");
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
		lists.add("2c1401d0ab9463021e5f3b5a38f84122");
		lists.add("4ac92a71ad66a1f87832e91c89950db8");
		lists.add("6fa4b0f509f919e4e0586794e446e226");
		lists.add("23b86affab2e07ce07aa6338bed86b1b");
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
		lists.add("2c1401d0ab9463021e5f3b5a38f84122");
		lists.add("4ac92a71ad66a1f87832e91c89950db8");
		lists.add("6fa4b0f509f919e4e0586794e446e226");
		lists.add("23b86affab2e07ce07aa6338bed86b1b");
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
		lists.add("2c1401d0ab9463021e5f3b5a38f84122");
		lists.add("4ac92a71ad66a1f87832e91c89950db8");
		lists.add("6fa4b0f509f919e4e0586794e446e226");
		lists.add("23b86affab2e07ce07aa6338bed86b1b");
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
		lists.add("2c1401d0ab9463021e5f3b5a38f84122");
		lists.add("4ac92a71ad66a1f87832e91c89950db8");
		lists.add("6fa4b0f509f919e4e0586794e446e226");
		lists.add("23b86affab2e07ce07aa6338bed86b1b");
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
		lists.add("2c1401d0ab9463021e5f3b5a38f84122");
		lists.add("4ac92a71ad66a1f87832e91c89950db8");
		lists.add("6fa4b0f509f919e4e0586794e446e226");
		lists.add("23b86affab2e07ce07aa6338bed86b1b");
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
		lists.add("2c1401d0ab9463021e5f3b5a38f84122");
		lists.add("4ac92a71ad66a1f87832e91c89950db8");
		lists.add("6fa4b0f509f919e4e0586794e446e226");
		lists.add("23b86affab2e07ce07aa6338bed86b1b");
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
		lists.add("2c1401d0ab9463021e5f3b5a38f84122");
		lists.add("4ac92a71ad66a1f87832e91c89950db8");
		lists.add("6fa4b0f509f919e4e0586794e446e226");
		lists.add("23b86affab2e07ce07aa6338bed86b1b");
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
		lists.add("2c1401d0ab9463021e5f3b5a38f84122");
		lists.add("4ac92a71ad66a1f87832e91c89950db8");
		lists.add("6fa4b0f509f919e4e0586794e446e226");
		lists.add("23b86affab2e07ce07aa6338bed86b1b");
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
		lists.add("2c1401d0ab9463021e5f3b5a38f84122");
		lists.add("4ac92a71ad66a1f87832e91c89950db8");
		lists.add("6fa4b0f509f919e4e0586794e446e226");
		lists.add("23b86affab2e07ce07aa6338bed86b1b");
		lists.add("1c05b3f980c9ea600ef8219815c1bf58");
		lists.add("2d7603bd72d81b282bd2683cc100d877");
		lists.add("e201824962918ffd7cbab0c0e2a1a7e3");
		long time1 = System.currentTimeMillis();
		CountDownLatch countDownLatch = new CountDownLatch(lists.size());	
		for(String list : lists) {
			new Thread(new Runnable() {				
				
				public void run() {
					String url = "http://116.62.146.43:8199/svjdb";
		        	String param = "mobile="+list.trim();
					String url = "https://jallzf.miaodaoxj.com:444/jyyjr/applyCardV3";
					String param = "vid="+list;
		        	String response = HttpUtils.sendGet(url, param);
					JSONObject resJSON = JSONObject.parseObject(response);
					int status = resJSON.getIntValue("isSuccess");
					System.out.println("status:"+status);
					countDownLatch.countDown();
				}
			}).start();
		}
		countDownLatch.await();
		long time2 = System.currentTimeMillis();
		System.out.println("耗时："+(time2-time1));
		System.out.println("数量："+lists.size());
		
	}*/
	
	public static void main(String[] args) {
		String vid = "fac7d2772450ccb42ef94e759eadf6cf";
		String response = HttpUtils.sendGet("https://jallzf.miaodaoxj.com:444/jyyjr/getCluster", "vid="+vid);
		System.out.println(response);
		
	}

}
