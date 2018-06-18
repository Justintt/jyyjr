package com.jyyjr.bench;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jyyjr.common.CloseAll;
import com.jyyjr.util.DBUtil;

/**
 * 3.22日之后（包含）用户，借款次数、还款次数、逾期次数、最大逾期天数、最后一笔订单状态、工作认证状态、淘宝近六个月购物金额、逻辑回归等级、当前额度
 * @author jinmin
 *
 */
public class Zy20180417 {
	
	/**
	 * vid
	 * @return
	 */
	public List<String> getUserVid() {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<>();
		try {
			conn = DBUtil.getZDYW();
			state = conn.createStatement();
			String sql2 = "SELECT vid FROM dxd_user.`user` WHERE ctime>=1521648000 AND cert_status=2 ORDER BY vid";
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
	
	public String getlogic(String vid) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<>();
		String str = "";
		try {
			conn = DBUtil.getZDYW();
			state = conn.createStatement();
			String sql2 = "SELECT logic_level FROM dxd_user.user_good_bad WHERE vid='"+vid+"'";
			rs = state.executeQuery(sql2);
			while(rs.next()) {
				str=rs.getString("logic_level");
			}
			
		} catch (SQLException e) {
			System.out.println("连接失败");
			e.printStackTrace();
		}finally{
			CloseAll.close(rs, state, conn);
		}
		return str;
	}
	
	/**
	 * 淘宝认证时间
	 * @param vid
	 * @return
	 */
	public int getTbCtime(String vid) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<>();
		int str = -1;
		try {
			conn = DBUtil.getZDZX();
			state = conn.createStatement();
			String sql2 = "SELECT IFNULL(ctime,-1)as ctime FROM jyy_fk_db.td_tb_base WHERE vid='"+vid+"'";
			rs = state.executeQuery(sql2);
			while(rs.next()) {
				str=rs.getInt("ctime");
			}
			
		} catch (SQLException e) {
			System.out.println("连接失败");
			e.printStackTrace();
		}finally{
			CloseAll.close(rs, state, conn);
		}
		return str;
	}
	
	/**
	 * 
	 * @param vid
	 * @return
	 */
	public int getTbmax_time(String vid) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<>();
		int str = -1;
		try {
			conn = DBUtil.getZDZX();
			state = conn.createStatement();
			String sql2 = "SELECT max(order_time) as max_time FROM jyy_fk_db.td_tb_order_list WHERE  order_status like '%成功%' and vid='"+vid+"'";
			rs = state.executeQuery(sql2);
			while(rs.next()) {
				str=rs.getInt("max_time");
			}
			
		} catch (SQLException e) {
			System.out.println("连接失败");
			e.printStackTrace();
		}finally{
			CloseAll.close(rs, state, conn);
		}
		return str;
	}
	
	public int getTbmax_count(String vid,int time) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<>();
		int str = -1;
		try {
			conn = DBUtil.getZDZX();
			state = conn.createStatement();
			String sql2 = "SELECT sum(order_amount) order_amount FROM jyy_fk_db.td_tb_order_list WHERE  order_status like '%成功%' and order_time>=UNIX_TIMESTAMP(DATE_SUB(DATE(FROM_UNIXTIME('"+time+"')),INTERVAL 6 MONTH))  and vid='"+vid+"'";
			rs = state.executeQuery(sql2);
			while(rs.next()) {
				str=rs.getInt("order_amount");
			}
			
		} catch (SQLException e) {
			System.out.println("连接失败");
			e.printStackTrace();
		}finally{
			CloseAll.close(rs, state, conn);
		}
		return str;
	}
	
	public int getTbctime_count(String vid,int time) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<>();
		int str = -1;
		try {
			conn = DBUtil.getZDZX();
			state = conn.createStatement();
			String sql2 = "SELECT sum(order_amount) order_amount FROM jyy_fk_db.td_tb_order_list WHERE  order_status like '%成功%' and order_time>=UNIX_TIMESTAMP(DATE_SUB(DATE(FROM_UNIXTIME('"+time+"')),INTERVAL 6 MONTH))  and vid='"+vid+"'";
			rs = state.executeQuery(sql2);
			while(rs.next()) {
				str=rs.getInt("order_amount");
			}
			
		} catch (SQLException e) {
			System.out.println("连接失败");
			e.printStackTrace();
		}finally{
			CloseAll.close(rs, state, conn);
		}
		return str;
	}
	
	public List<Map<String, Object>> getUserStatus() {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		List<Map<String, Object>> list = new ArrayList<>();
		try {
			conn = DBUtil.getZDYW();
			state = conn.createStatement();
			String sql2 = "SELECT u.vid,b.borrow_no,b.`status` FROM dxd_caiwu.user_borrow b RIGHT JOIN "
					+ "(SELECT vid FROM dxd_user.`user` WHERE ctime>=1521648000 AND cert_status=2 ORDER BY vid) u ON b.vid=u.vid AND b.is_success=2";
			rs = state.executeQuery(sql2);
			while(rs.next()) {
				Map<String, Object> map = new HashMap<>();
				map.put("vid", rs.getString("vid"));
				map.put("borrow_no", rs.getString("borrow_no"));
				map.put("status", rs.getInt("status"));
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
	
	
	/**
	 * 获取3月22日后的用户的额度，借款次数
	 * @return
	 */
	public List<Map<String, Object>> getUserMsg() {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		List<Map<String, Object>> list = new ArrayList<>();
		try {
			conn = DBUtil.getZDYW();
			state = conn.createStatement();
			String sql2 = "SELECT u.vid,u.quota,COUNT(b.vid) borrowCount FROM dxd_caiwu.user_borrow b RIGHT JOIN "
					+ "(SELECT vid,quota FROM dxd_user.`user` WHERE ctime>=1521648000 AND cert_status=2 ORDER BY vid) u "
					+ "ON b.vid=u.vid AND b.is_success=2 GROUP BY u.vid";
			rs = state.executeQuery(sql2);
			while(rs.next()) {
				Map<String, Object> map = new HashMap<>();
				map.put("vid", rs.getString("vid"));
				map.put("quota", rs.getInt("quota"));
				map.put("borrowCount", rs.getInt("borrowCount"));
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
	
	public List<Map<String, Object>> getReapy() {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		List<Map<String, Object>> list = new ArrayList<>();
		try {
			conn = DBUtil.getZDYW();
			state = conn.createStatement();
			String sql2 = "SELECT u.vid,COUNT(b.vid) repayCount FROM dxd_caiwu.user_repayment b RIGHT JOIN "
					+ "(SELECT vid FROM dxd_user.`user` WHERE ctime>=1521648000 AND cert_status=2 ORDER BY vid) u ON b.vid=u.vid AND b.status=3 "
					+ "GROUP BY u.vid";
			rs = state.executeQuery(sql2);
			while(rs.next()) {
				Map<String, Object> map = new HashMap<>();
				map.put("vid", rs.getString("vid"));
				map.put("repayCount", rs.getInt("repayCount"));
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
	
	/*public static void main(String[] args) {
		Zy20180417 zy20180417  = new Zy20180417();
		List<Map<String, Object>> list = zy20180417.getUserStatus();
		List<String> vids = ExcelUtils.readExcel();
		
		List<Map<String, Object>> lists = new ArrayList<>();
		int size = 0;
		for(String vid :vids) {
			Map<String, Object> mapStatus = new HashMap<>();
			int status = 0;
			for(Map<String, Object> map : list) {
				if (vid.equals((String)map.get("vid"))) {
					status = (int) map.get("status");
				}
			}
			mapStatus.put("vid", vid);
			mapStatus.put("status", status);
			lists.add(mapStatus);
		}
		for(Map<String, Object> map2 : lists) {
			System.out.println(map2.get("vid")+":"+map2.get("status"));
		}
		System.out.println(lists.size());
		
	}*/
	
	/*public static void main(String[] args) {
		Zy20180417 zy20180417  = new Zy20180417();
		//List<String> vids = ExcelUtils.readExcel();
		int size = 0;
		for(String vid : vids) {
			size++;
			int ctime = zy20180417.getTbCtime(vid);
			int max_time = zy20180417.getTbmax_time(vid);
			if (ctime==-1) {
				if(max_time!=0) {
					System.out.println(vid+":"+(double)zy20180417.getTbmax_count(vid, max_time)/100);
				}else {
					System.out.println(vid+":"+"null");
				}
				
			}
			if (ctime!=-1) {
				if (max_time!=0) {
					System.out.println(vid+":"+(double)zy20180417.getTbmax_count(vid, ctime)/100);
				}else {
					System.out.println(vid+":"+"null");
				}
				
			}
			
			//System.out.println(vid+":"+ctime+":"+max_time);
		}
		System.out.println(size);
	}*/

}
