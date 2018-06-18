package com.jyyjr.bench;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jyyjr.common.CloseAll;
import com.jyyjr.util.DBUtil;

public class Common {
	
	/**
	 * 集合均分(默认4份)
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
	 * 获取指定vid
	 * @return
	 */
	public List<String> getUserVid() {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<>();
		try {
			conn = DBUtil.getDXFK();
			state = conn.createStatement();
			String sql = "SELECT vid FROM dxd_data.jmvid LIMIT 831,10000";
			rs = state.executeQuery(sql);
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
	 * 获取指定时间段内的借款次数
	 * @param vid
	 * @return
	 */
	public Integer getBorrowCount(String vid) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		Integer count = null;
		try {
			conn = DBUtil.getZDYW();
			state = conn.createStatement();
			String sql = "SELECT COUNT(*) num FROM dxd_caiwu.user_borrow WHERE is_success=2 AND vid='"+vid+"' AND get_time>=1518537600 AND get_time<1519833600";
			rs = state.executeQuery(sql);
			while(rs.next()) {
				count = rs.getInt("num");
			}
			
		} catch (SQLException e) {
			System.out.println("连接失败");
			e.printStackTrace();
		}finally{
			CloseAll.close(rs, state, conn);
		}
		return count;
	}
	
	/**
	 * 获取指定时间段内的借款总金额
	 * @param vid
	 * @return
	 */
	public Double getBorrowMoney(String vid) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		Double count = null;
		try {
			conn = DBUtil.getZDYW();
			state = conn.createStatement();
			String sql = "SELECT SUM(money) num FROM dxd_caiwu.user_borrow WHERE is_success=2 AND vid='"+vid+"' AND get_time>=1518537600 AND get_time<1519833600";
			rs = state.executeQuery(sql);
			while(rs.next()) {
				count = rs.getDouble("num");
			}
			
		} catch (SQLException e) {
			System.out.println("连接失败");
			e.printStackTrace();
		}finally{
			CloseAll.close(rs, state, conn);
		}
		return count;
	}
	
	/**
	 * 获取指定时间段内的借款次数
	 * @param vid
	 * @return
	 */
	public Integer getBorrowCountC(String vid) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		Integer count = null;
		try {
			conn = DBUtil.getZDYW();
			state = conn.createStatement();
			String sql = "SELECT COUNT(*) num FROM dxd_caiwu.user_borrow WHERE is_success=2 AND vid='"+vid+"' AND get_time>=1519833600 AND get_time<1526400000";
			rs = state.executeQuery(sql);
			while(rs.next()) {
				count = rs.getInt("num");
			}
			
		} catch (SQLException e) {
			System.out.println("连接失败");
			e.printStackTrace();
		}finally{
			CloseAll.close(rs, state, conn);
		}
		return count;
	}
	
	/**
	 * 获取指定时间段内的借款总金额
	 * @param vid
	 * @return
	 */
	public Double getBorrowMoneyC(String vid) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		Double count = null;
		try {
			conn = DBUtil.getZDYW();
			state = conn.createStatement();
			String sql = "SELECT SUM(money) num FROM dxd_caiwu.user_borrow WHERE is_success=2 AND vid='"+vid+"' AND get_time>=1519833600 AND get_time<1526400000";
			rs = state.executeQuery(sql);
			while(rs.next()) {
				count = rs.getDouble("num");
			}
			
		} catch (SQLException e) {
			System.out.println("连接失败");
			e.printStackTrace();
		}finally{
			CloseAll.close(rs, state, conn);
		}
		return count;
	}
	
	/**
	 * 获取用户指定时间段内的逾期次数
	 * @param vid
	 * @return
	 */
	public Integer getOverdueCountC(String vid) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		Integer count = null;
		try {
			conn = DBUtil.getZDYW();
			state = conn.createStatement();
			String sql = "SELECT COUNT(*) num FROM dxd_urge.user_overdue WHERE vid='"+vid+"' AND should_time>=1519833600 AND should_time<1526400000";
			rs = state.executeQuery(sql);
			while(rs.next()) {
				count = rs.getInt("num");
			}
		} catch (SQLException e) {
			System.out.println("连接失败");
			e.printStackTrace();
		}finally{
			CloseAll.close(rs, state, conn);
		}
		return count;
	}
	

}
