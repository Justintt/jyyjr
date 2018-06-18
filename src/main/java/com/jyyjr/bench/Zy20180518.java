package com.jyyjr.bench;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jyyjr.common.CloseAll;
import com.jyyjr.util.DBUtil;
import com.jyyjr.util.TimeUtils;

public class Zy20180518 {
	
	public Integer getHuabei(String vid) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		Integer repayCount = null;
		try {
			conn = DBUtil.getZDZX();
			state = conn.createStatement();
			String sql2 = "SELECT consume_quota FROM jyy_fk_db.mh_alipay_assets_info WHERE vid='"+vid+"' ORDER BY id LIMIT 1";
			rs = state.executeQuery(sql2);
			while(rs.next()) {
				repayCount = rs.getInt("consume_quota");
			}
			
		} catch (SQLException e) {
			System.out.println("连接失败");
			e.printStackTrace();
		}finally{
			CloseAll.close(rs, state, conn);
		}
		return repayCount;
	}
	
	public Map<String, Double> getXYK(String vid) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		Map<String, Double> map = new HashMap<>();
		try {
			conn = DBUtil.getZDZX();
			state = conn.createStatement();
			String sql = "SELECT SUM(creditlimit) creditlimit ,SUM(withdrawallimit) withdrawallimit,COUNT(*) num FROM jyy_fk_db.cards_base WHERE vid='"+vid+"'";
			rs = state.executeQuery(sql);
			while(rs.next()) {
				map.put("creditlimit", rs.getDouble("creditlimit"));
				map.put("withdrawallimit", rs.getDouble("withdrawallimit"));
				map.put("num", rs.getDouble("num"));
			}
			
		} catch (SQLException e) {
			System.out.println("连接失败");
			e.printStackTrace();
		}finally{
			CloseAll.close(rs, state, conn);
		}
		return map;
	}
	
	public String getCertTime(String vid) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		String certTime = null;
		try {
			conn = DBUtil.getZDYW();
			state = conn.createStatement();
			String sql2 = "SELECT concat(date_format(LAST_DAY(FROM_UNIXTIME(cert_time)),'%Y-%m-'),'01') cert_time  FROM dxd_user.`user` WHERE vid='"+vid+"'";
			rs = state.executeQuery(sql2);
			while(rs.next()) {
				String certime = rs.getString("cert_time");
				if (certime!=null) {
					certTime = String.valueOf(TimeUtils.timeStamp(certime));
				}
				
			}
			
		} catch (SQLException e) {
			System.out.println("连接失败");
			e.printStackTrace();
		}finally{
			CloseAll.close(rs, state, conn);
		}
		return certTime;
	}
	
	public Map<String, Object> getXYKxf(String vid,String ctime) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		Map<String, Object> map = new HashMap<>();
		try {
			conn = DBUtil.getZDZX();
			state = conn.createStatement();
			String sql = "SELECT statementmonth,SUM(curpaymentbal) curpaymentbal,SUM(creditlimit) creditlimit FROM jyy_fk_db.cards_bills WHERE vid IN "
					+ "(SELECT id FROM jyy_fk_db.cards_base WHERE vid='"+vid+"') AND statementmonth='"+ctime+"'";
			rs = state.executeQuery(sql);
			while(rs.next()) {
				map.put("statementmonth", rs.getString("statementmonth"));
				map.put("curpaymentbal", rs.getDouble("curpaymentbal"));
				map.put("creditlimit", rs.getDouble("creditlimit"));
			}
			
		} catch (SQLException e) {
			System.out.println("连接失败");
			e.printStackTrace();
		}finally{
			CloseAll.close(rs, state, conn);
		}
		return map;
	}
	
	public static void main(String[] args) {
		Zy20180518 zy20180518 = new Zy20180518();
		Common common = new Common();
		List<String> vids = common.getUserVid();
		int index = 831;
		for(String vid : vids) {
			index ++;
			String ctime = zy20180518.getCertTime(vid);
			if (ctime!=null) {
				Map<String, Object> map = zy20180518.getXYKxf(vid, ctime);
				System.out.println(vid+":"+map.get("statementmonth")+":"+map.get("curpaymentbal")+":"+map.get("creditlimit")+":"+index);
			}else {
				System.out.println(vid+":null"+":null"+":null"+":"+index);
			}
			
		}
		
	}
	
	/*public static void main(String[] args) {
		Common common = new Common();
		List<String> vids = common.getUserVid();
		List<List<String>> listVids = common.splitList(vids);
		List<String> vid1s = listVids.get(0);
		List<String> vid2s = listVids.get(1);
		List<String> vid3s = listVids.get(2);
		List<String> vid4s = listVids.get(3);
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				int size = 0;
				for(String vid : vid1s) {
					Integer borrowCount = common.getBorrowCount(vid);
					Double borrowMoney = common.getBorrowMoney(vid);
					Integer borrowCountc = common.getBorrowCountC(vid);
					Integer overdueCount = common.getOverdueCountC(vid);
					Double borrowMoneyc = common.getBorrowMoneyC(vid);
					size++;
					System.out.println(vid+":"+borrowCount+":"+borrowMoney+":"+borrowCountc+":"+overdueCount+":"+borrowMoneyc+":"+size);
				}
				
			}
		});
        Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				int size = 0;
				for(String vid : vid2s) {
					Integer borrowCount = common.getBorrowCount(vid);
					Double borrowMoney = common.getBorrowMoney(vid);
					Integer borrowCountc = common.getBorrowCountC(vid);
					Integer overdueCount = common.getOverdueCountC(vid);
					Double borrowMoneyc = common.getBorrowMoneyC(vid);
					size++;
					System.out.println(vid+":"+borrowCount+":"+borrowMoney+":"+borrowCountc+":"+overdueCount+":"+borrowMoneyc+":"+size);
				}
				
			}
		});
        Thread t3 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				int size = 0;
				for(String vid : vid3s) {
					Integer borrowCount = common.getBorrowCount(vid);
					Double borrowMoney = common.getBorrowMoney(vid);
					Integer borrowCountc = common.getBorrowCountC(vid);
					Integer overdueCount = common.getOverdueCountC(vid);
					Double borrowMoneyc = common.getBorrowMoneyC(vid);
					size++;
					System.out.println(vid+":"+borrowCount+":"+borrowMoney+":"+borrowCountc+":"+overdueCount+":"+borrowMoneyc+":"+size);
				}
				
			}
		});
        Thread t4 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				int size = 0;
				for(String vid : vid4s) {
					Integer borrowCount = common.getBorrowCount(vid);
					Double borrowMoney = common.getBorrowMoney(vid);
					Integer borrowCountc = common.getBorrowCountC(vid);
					Integer overdueCount = common.getOverdueCountC(vid);
					Double borrowMoneyc = common.getBorrowMoneyC(vid);
					size++;
					System.out.println(vid+":"+borrowCount+":"+borrowMoney+":"+borrowCountc+":"+overdueCount+":"+borrowMoneyc+":"+size);
				}
				
			}
		});
        
        t1.start();
        t2.start();
        t3.start();
        t4.start();
	}*/

}
