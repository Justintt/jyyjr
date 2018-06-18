package com.jyyjr.bench;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jyyjr.common.CloseAll;
import com.jyyjr.util.DBUtil;

public class Zy20180424 {
	
	/**
	 * 3月20号通过用户vid
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
			String sql2 = "SELECT vid FROM dxd_urge.user_overdue WHERE overdue_day>7 GROUP BY vid ORDER BY vid";
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
	
	/**
	 * 集合均分
	 * @param targe
	 * @return
	 */
	public List<List<String>> splitList(List<String> targe){
		List<List<String>> listArr = new ArrayList<List<String>>();  
		//4租每组多少个数据  
		int size = 0;
		if(targe.size()%4==0){
		size = targe.size()/4;
		}else{
		    size = targe.size()/4+1;
	    }
	    for(int i=0;i<4;i++) {  
	        List<String>  sub = new ArrayList<String>();  
	        //把指定索引数据放入到list中  
	        for(int j=i*size;j<=size*(i+1)-1;j++) {  
	            if(j<=targe.size()-1) {  
	                sub.add(targe.get(j));  
	            }  
	        }  
	        listArr.add(sub);  
	    }  
	    return listArr;  
	}
	
	/**
	 * 获取3月20日后的用户的额度，借款次数
	 * @return
	 */
	public Map<String, Object> getUserMsg(String vid) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		Map<String, Object> map = new HashMap<>();
		try {
			conn = DBUtil.getZDYW();
			state = conn.createStatement();
			String sql2 = "SELECT u.quota,u.check_type,COUNT(b.vid) borrowCount FROM dxd_caiwu.user_borrow b RIGHT JOIN "
					+ "(SELECT vid,quota,check_type FROM dxd_user.`user` WHERE vid='"+vid+"') u ON b.vid=u.vid AND b.is_success=2 GROUP BY u.vid";
			rs = state.executeQuery(sql2);
			while(rs.next()) {
				map.put("quota", rs.getInt("quota"));
				map.put("check_type", rs.getInt("check_type"));
				map.put("borrowCount", rs.getInt("borrowCount"));
			}
			
		} catch (SQLException e) {
			System.out.println("连接失败");
			e.printStackTrace();
		}finally{
			CloseAll.close(rs, state, conn);
		}
		return map;
	}
	
	/**
	 * 还款次数
	 * @param vid
	 * @return
	 */
	public Integer getReapy(String vid) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		Integer repayCount = null;
		try {
			conn = DBUtil.getZDYW();
			state = conn.createStatement();
			String sql2 = "SELECT COUNT(vid) repayCount FROM dxd_caiwu.user_repayment WHERE vid='"+vid+"' AND status=3";
			rs = state.executeQuery(sql2);
			while(rs.next()) {
				repayCount = rs.getInt("repayCount");
			}
			
		} catch (SQLException e) {
			System.out.println("连接失败");
			e.printStackTrace();
		}finally{
			CloseAll.close(rs, state, conn);
		}
		return repayCount;
	}
	
	/**
	 * 最大逾期天数
	 * @param vid
	 * @return
	 */
	public Integer getMaxOverdue(String vid) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		Integer maxOverdue = null;
		try {
			conn = DBUtil.getZDYW();
			state = conn.createStatement();
			String sql2 = "SELECT MAX(overdue_day) maxOverdue FROM dxd_urge.user_overdue WHERE vid='"+vid+"'";
			rs = state.executeQuery(sql2);
			while(rs.next()) {
				maxOverdue = rs.getInt("maxOverdue");
			}
			
		} catch (SQLException e) {
			System.out.println("连接失败");
			e.printStackTrace();
		}finally{
			CloseAll.close(rs, state, conn);
		}
		return maxOverdue;
	}
	
	/**
	 * 最后一比还款方式
	 * @param vid
	 * @return
	 */
	public Integer getRepayType(String vid) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		Integer type = null;
		try {
			conn = DBUtil.getZDYW();
			state = conn.createStatement();
			String sql2 = "SELECT type FROM dxd_caiwu.user_repayment WHERE vid='"+vid+"' ORDER BY id DESC LIMIT 1";
			rs = state.executeQuery(sql2);
			while(rs.next()) {
				type = rs.getInt("type");
			}
			
		} catch (SQLException e) {
			System.out.println("连接失败");
			e.printStackTrace();
		}finally{
			CloseAll.close(rs, state, conn);
		}
		return type;
	}
	
	/**
	 * 最后一笔订单的状态
	 * @param vid
	 * @return
	 */
	public String getListBorrowStatus(String vid) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		String listBorrowStatus = null;
		try {
			conn = DBUtil.getZDYW();
			state = conn.createStatement();
			String sql2 = "SELECT `status` FROM dxd_caiwu.user_borrow WHERE vid='"+vid+"' AND is_success=2 ORDER BY borrow_no DESC LIMIT 1";
			rs = state.executeQuery(sql2);
			while(rs.next()) {
				listBorrowStatus = rs.getString("status");
			}
			
		} catch (SQLException e) {
			System.out.println("连接失败");
			e.printStackTrace();
		}finally{
			CloseAll.close(rs, state, conn);
		}
		return listBorrowStatus;
	}
	
	/**
	 * 逻辑回归等级
	 * @param vid
	 * @return
	 */
	public String getlogic(String vid) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<>();
		String str = null;
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
	 * 命中项
	 * @return
	 */
	public Map<String, Object> getExamie(String vid) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		List<Map<String, Object>> lists = new ArrayList<>();
		Map<String, Object> resultMap = new HashMap<>();
		try {
			conn = DBUtil.getDQZX();
			state = conn.createStatement();
			String sql2 = "SELECT number FROM user_examine WHERE vid='"+vid+"' AND (`status` IN (1,2) OR checktype=2)";
			rs = state.executeQuery(sql2);
			while(rs.next()) {
				Map<String, Object> map = new HashMap<>();
				map.put("number", rs.getString("number"));
				lists.add(map);
			}
			
		} catch (SQLException e) {
			System.out.println("连接失败");
			e.printStackTrace();
		}finally{
			CloseAll.close(rs, state, conn);
		}
		StringBuffer number = new StringBuffer();
		
		if (lists.size()>0 && lists!=null) {
			for(Map<String,Object> m : lists) {
				number.append(m.get("number"));
				number.append("-");
				
			}
		}
		String numberStr = number.toString();
		if (numberStr.length()>0) {
			numberStr = numberStr.substring(0, numberStr.length()-1);
		}
		
		resultMap.put("number", numberStr);
		
		return resultMap;
	}
	
	/**
	 * 逾期次数、逾期天数
	 * @param vid
	 * @return
	 */
	public List<Integer> getBorrowCount(String vid) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		List<Integer> lists = new ArrayList<>();
		try {
			conn = DBUtil.getZDYW();
			state = conn.createStatement();
			String sql2 = "SELECT vid,o.overdue_day FROM dxd_caiwu.user_repayment r INNER JOIN (SELECT repayment_no,overdue_day FROM dxd_urge.user_overdue) o "
					+ "ON r.repayment_no=o.repayment_no WHERE r.vid='"+vid+"' AND r.is_overdue=2";
			rs = state.executeQuery(sql2);
			while(rs.next()) {
				Integer overdueDay = rs.getInt("overdue_day");
				lists.add(overdueDay);
			}
			
		} catch (SQLException e) {
			System.out.println("连接失败");
			e.printStackTrace();
		}finally{
			CloseAll.close(rs, state, conn);
		}
		return lists;
	}
	
	
	
	public static void main(String[] args) {
		Zy20180424 zy20180424 = new Zy20180424();
		List<String> vids = zy20180424.getUserVid();
		List<List<String>> listVids = zy20180424.splitList(vids);
		List<String> vid1s = listVids.get(0);
		List<String> vid2s = listVids.get(1);
		List<String> vid3s = listVids.get(2);
		List<String> vid4s = listVids.get(3);
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				int index = 0;
				for(String vid : vid1s) {
					index++;
					Map<String, Object> map = zy20180424.getUserMsg(vid);
					List<Integer> overdueCount = zy20180424.getBorrowCount(vid);
					Integer getReapy = zy20180424.getReapy(vid);
					int overdueNum = overdueCount.size();
					Integer type = zy20180424.getRepayType(vid);
					System.out.println(vid+":"+map.get("borrowCount")+":"+getReapy+":"+map.get("quota")+":"+overdueNum+":"+type+":"+index);
				}
			}
		});
        Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				int index = 0;
				for(String vid : vid2s) {
					index++;
					Map<String, Object> map = zy20180424.getUserMsg(vid);
					List<Integer> overdueCount = zy20180424.getBorrowCount(vid);
					Integer getReapy = zy20180424.getReapy(vid);
					int overdueNum = overdueCount.size();
					Integer type = zy20180424.getRepayType(vid);
					System.out.println(vid+":"+map.get("borrowCount")+":"+getReapy+":"+map.get("quota")+":"+overdueNum+":"+type+":"+index);
				}
			}
		});
        Thread t3 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				int index = 0;
				for(String vid : vid3s) {
					index++;
					Map<String, Object> map = zy20180424.getUserMsg(vid);
					List<Integer> overdueCount = zy20180424.getBorrowCount(vid);
					Integer getReapy = zy20180424.getReapy(vid);
					int overdueNum = overdueCount.size();
					Integer type = zy20180424.getRepayType(vid);
					System.out.println(vid+":"+map.get("borrowCount")+":"+getReapy+":"+map.get("quota")+":"+overdueNum+":"+type+":"+index);
				}
			}
		});
        Thread t4 = new Thread(new Runnable() {
			@Override
			public void run() {
				int index = 0;
				for(String vid : vid4s) {
					index++;
					Map<String, Object> map = zy20180424.getUserMsg(vid);
					List<Integer> overdueCount = zy20180424.getBorrowCount(vid);
					Integer getReapy = zy20180424.getReapy(vid);
					int overdueNum = overdueCount.size();
					Integer type = zy20180424.getRepayType(vid);
					System.out.println(vid+":"+map.get("borrowCount")+":"+getReapy+":"+map.get("quota")+":"+overdueNum+":"+type+":"+index);
				}
			}
		});
		t1.start();
		t2.start();
		t3.start();
		t4.start();
	}
	/*public static void main(String[] args) {
		Zy20180424 zy20180424 = new Zy20180424();
		Map<String, Object> map = zy20180424.getExamie("7d713acfac40ab7f97f103b62381b218");
		System.err.println(map.get("number")+":"+map.get("status"));
	}*/

}
