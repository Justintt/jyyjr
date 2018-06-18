package com.jyyjr.bench;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.jyyjr.common.CloseAll;
import com.jyyjr.util.DBUtil;

public class Zy20180413 {
	
	/**
	 * 注册时的同盾分
	 * @param vid
	 * @return
	 */
	public String getTdScore(String vid) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		String name = "null";
		try {
			conn = DBUtil.getZDZX();
			state = conn.createStatement();
			String sql = "SELECT final_score FROM jyy_fk_db.tdrisk_report WHERE vid='"+vid+"' LIMIT 1";
			rs = state.executeQuery(sql);
			while(rs.next()) {
				name = rs.getString("final_score");
			}
		} catch (SQLException e) {
			System.out.println("连接失败");
			e.printStackTrace();
		}finally{
			CloseAll.close(rs, state, conn);
		}
		return name;
		
	}
	
	/**
	 * 匹配同盾分
	 * @param vid
	 * @return
	 */
	public String getTd_score(String vid,String ctime) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		String score = "null";
		try {
			conn = DBUtil.getZDZX();
			state = conn.createStatement();
			String sql2 = "SELECT IFNULL(final_score,-1) final_score FROM jyy_fk_db.borrow_check_td  WHERE vid='"+vid+"' AND success=1 AND "
					+ "FROM_UNIXTIME(LEFT(apply_time,10))>=timestampadd(HOUR,-2,FROM_UNIXTIME('"+ctime+"')) AND "
							+ "FROM_UNIXTIME(LEFT(apply_time,10))<=FROM_UNIXTIME('"+ctime+"') LIMIT 1";
			rs = state.executeQuery(sql2);
			while(rs.next()) {
				score = rs.getString("final_score");
			}
			
		} catch (SQLException e) {
			System.out.println("连接失败");
			e.printStackTrace();
		}finally{
			CloseAll.close(rs, state, conn);
		}
		return score;
	}
	
	/**
	 * ok
	 * 17、用户最后一次借款的时间（小时）
	 * @param vid
	 * @return
	 */
	public int[] getLast_time(String vid) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		int[] str = new int[2];
		try {
			conn = DBUtil.getZDYW();
			state = conn.createStatement();
			String sql = "SELECT get_time,HOUR(FROM_UNIXTIME(get_time)) h FROM dxd_caiwu.user_borrow WHERE vid='"+vid+"' AND is_success=2 ORDER BY get_time DESC LIMIT 1";
			rs = state.executeQuery(sql);
			while(rs.next()) {
				str[0] = rs.getInt("get_time");
				str[1] = rs.getInt("h");
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
	public String getLast_td(String vid) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		String result = "null";
		try {
			conn = DBUtil.getZDZX();
			state = conn.createStatement();
			String sql = "SELECT final_score FROM jyy_fk_db.borrow_check_td WHERE vid='"+vid+"' ORDER BY report_id DESC LIMIT 1";
			rs = state.executeQuery(sql);
			while(rs.next()) {
				result = rs.getString("final_score");
			}
		} catch (SQLException e) {
			System.out.println("连接失败");
			e.printStackTrace();
		}finally{
			CloseAll.close(rs, state, conn);
		}
		return result;
	}
	
	/**
	 * ok
	 * 16、用户最后一次借款时的同盾分
	 * @param vid
	 * @return
	 */
	public String getLast_tdscore(String vid) {
		String score = "null";
		int[] arr = getLast_time(vid);
		String ctime = arr[0]+"";
		score = getTd_score(vid, ctime);
		if(score==null) {
			score = getLast_td(vid);
		}
		return score;
	}
	
	/**
	 * 同盾七天
	 * @param vid
	 * @return
	 */
	public String[] getTd(String vid) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		String[] str = new String[3];
		str[0] = "null";
		str[1] = "null";
		str[2] = "null";
		try {
			conn = DBUtil.getZDZX();
			state = conn.createStatement();
			String sql = "SELECT seven_day,one_month,three_month FROM jyy_fk_db.get_td_data WHERE vid='"+vid+"' ORDER BY report_id DESC LIMIT 1";
			rs = state.executeQuery(sql);
			while(rs.next()) {
				str[0] = rs.getString("seven_day");
				str[1] = rs.getString("one_month");
				str[2] = rs.getString("three_month");
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
	 * 风险联系人个数
	 * @param vid
	 * @return
	 */
	public String getfxlxr(String vid) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		String result = "null";
		try {
			conn = DBUtil.getZDZX();
			state = conn.createStatement();
			String sql = "SELECT COUNT(vid) num FROM jyy_fk_db.risk_contact_detail WHERE vid='"+vid+"'";
			rs = state.executeQuery(sql);
			while(rs.next()) {
				result = rs.getString("num");
			}
		} catch (SQLException e) {
			System.out.println("连接失败");
			e.printStackTrace();
		}finally{
			CloseAll.close(rs, state, conn);
		}
		return result;
	}
	
	/**
	 * ok
	 * 12、前海qh_user_risk表风险评分：rskScore
	 * @param vid
	 * @return
	 */
	public String getQh_user_risk(String vid) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		String lists = "null";
		try {
			conn = DBUtil.getZDZX();
			state = conn.createStatement();
			String sql = "SELECT rskScore FROM jyy_fk_db.qh_user_risk WHERE vid='"+vid+"' AND "
					+ "erCode='E000000' ORDER BY UNIX_TIMESTAMP(iUpdateDate) DESC LIMIT 1";
			rs = state.executeQuery(sql);
			while(rs.next()) {
				lists = rs.getString("rskScore");
			}
		} catch (SQLException e) {
			System.out.println("连接失败");
			e.printStackTrace();
		}finally{
			CloseAll.close(rs, state, conn);
		}
		return lists;
	}
	
	/**
	 * ok
	 * 13、前海qh_user_risk_hint表最近一笔业务发生的灰度分：rskScore_hint
	 * @param vid
	 * @return
	 */
	public String getQh_user_risk_hint(String vid) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		String lists = "null";
		try {
			conn = DBUtil.getZDZX();
			state = conn.createStatement();
			String sql = "SELECT rskScore FROM jyy_fk_db.qh_user_risk_hint WHERE vid='"+vid+"' "
					+ "AND erCode='E000000' ORDER BY UNIX_TIMESTAMP(dataBuildTime) DESC LIMIT 1";
			rs = state.executeQuery(sql);
			while(rs.next()) {
				lists = rs.getString("rskScore");
			}
		} catch (SQLException e) {
			System.out.println("连接失败");
			e.printStackTrace();
		}finally{
			CloseAll.close(rs, state, conn);
		}
		return lists;
	}
	
	/*public static void main(String[] args) {
		Zy20180413 z = new Zy20180413();
		List<String> vids = ExcelUtils.readExcel();
		int size = 0;
		for(String vid : vids) {
			String firstTd = z.getTdScore(vid);
			String lastTd = z.getLast_tdscore(vid);
			String[] td = z.getTd(vid);
			String fxlxr = z.getfxlxr(vid);
			String qhfx = z.getQh_user_risk(vid);
			String qhhd = z.getQh_user_risk_hint(vid);
			System.out.println(vid+":"+firstTd+":"+lastTd+":"+td[0]+":"+td[1]+":"+td[2]+":"+fxlxr+":"+qhfx+":"+qhhd);
			size++;
		}
		System.out.println(size);
	}*/

}
