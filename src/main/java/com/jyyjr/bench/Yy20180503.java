package com.jyyjr.bench;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.jyyjr.common.CloseAll;
import com.jyyjr.util.DBUtil;
import com.jyyjr.util.HttpUtils;

public class Yy20180503 {
	
	/**
	 * 用户vid
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
			String sql2 = "SELECT vid FROM dxd_fk.20180521_yy_data";
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
	
	
	
	public static void main(String[] args) {
		Common common = new Common();
		Yy20180503 yy20180503 = new Yy20180503();
		List<String> vids  =  yy20180503.getUserVid();
		List<List<String>> listVids = common.splitList(vids);
		List<String> vid1s = listVids.get(0);
		List<String> vid2s = listVids.get(1);
		List<String> vid3s = listVids.get(2);
		List<String> vid4s = listVids.get(3);
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(String vid : vid1s) {
					try {
						String resposen = HttpUtils.sendGet("http://116.62.146.43:8080/jyyjr/test/getJmDay", "vid="+vid);
						JSONObject jsonObject = JSONObject.parseObject(resposen);
						int isSuccess = jsonObject.getIntValue("isSuccess");
						String message = jsonObject.getString("message");
						System.out.println(vid+":"+isSuccess+":"+message);
					} catch (Exception e) {
						System.out.println(vid+":出错");
					}
					
				}
				
			}
		});
		t1.start();
        Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(String vid : vid2s) {
					try {
						String resposen = HttpUtils.sendGet("http://116.62.146.43:8080/jyyjr/test/getJmDay", "vid="+vid);
						JSONObject jsonObject = JSONObject.parseObject(resposen);
						int isSuccess = jsonObject.getIntValue("isSuccess");
						String message = jsonObject.getString("message");
						System.out.println(vid+":"+isSuccess+":"+message);
					} catch (Exception e) {
						System.out.println(vid+":出错");
					}
					
				}
				
			}
		});
		t2.start();
		Thread t3 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(String vid : vid3s) {
					try {
						String resposen = HttpUtils.sendGet("http://116.62.146.43:8080/jyyjr/test/getJmDay", "vid="+vid);
						JSONObject jsonObject = JSONObject.parseObject(resposen);
						int isSuccess = jsonObject.getIntValue("isSuccess");
						String message = jsonObject.getString("message");
						System.out.println(vid+":"+isSuccess+":"+message);
					} catch (Exception e) {
						System.out.println(vid+":出错");
					}
					
				}
				
			}
		});
		t3.start();
		Thread t4 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(String vid : vid4s) {
					try {
						String resposen = HttpUtils.sendGet("http://116.62.146.43:8080/jyyjr/test/getJmDay", "vid="+vid);
						JSONObject jsonObject = JSONObject.parseObject(resposen);
						int isSuccess = jsonObject.getIntValue("isSuccess");
						String message = jsonObject.getString("message");
						System.out.println(vid+":"+isSuccess+":"+message);
					} catch (Exception e) {
						System.out.println(vid+":出错");
					}
					
				}
				
			}
		});
		t4.start();
	}

}
