package com.jyyjr.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	public static final String DRIVER = "com.mysql.jdbc.Driver";
	public static final String YW_URL= "jdbc:mysql://rr-bp11xm82364b0h6ad.mysql.rds.aliyuncs.com:3306/?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&useSSL=true";
    public static final String CC_URL= "jdbc:mysql://rm-bp181r7060lye463oo.mysql.rds.aliyuncs.com:3306/?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&useSSL=true";
    public static final String DXYW_URL= "jdbc:mysql://rm-bp12eu605xewc7q0y.mysql.rds.aliyuncs.com:3306/?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&useSSL=true";
    public static final String ZX_URL= "jdbc:mysql://rr-bp1az8l9ljej4424go.mysql.rds.aliyuncs.com:3306/?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&useSSL=true";
    public static final String ZX_TXJL= "jdbc:mysql://drdsne00r16rw2h5.drds.aliyuncs.com:3306/zhengxin?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&useSSL=true";
    public static final String DQ_URL ="jdbc:mysql://rm-bp16m09s898zq6mh3o.mysql.rds.aliyuncs.com:3306/jyy_fk_db?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&useSSL=true";
    public static final String TX_USERNAME = "zhengxin_ro";
	public static final String TX_PASSWORD = "Jyyjr_0571";
    public static final String USERNAME = "dxd_fk";
    public static final String PASSWORD = "jyyjr@0577";
    public static final String ZX_PASSWORD = "dxd_fk@0577";
    public static final String DQ_USERNAME = "dxd_java";
    public static final String DQ_PASSWORD = "dxd_java0571";

    /**
     * 只读业务库连接
     * @return
     */
    public static Connection getZDYW(){
        Connection conn = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(YW_URL,USERNAME,PASSWORD);

        } catch (ClassNotFoundException e) {
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        }catch (SQLException e) {
            System.out.println("连接失败!");
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 读写分控数据库连接
     * @return
     */
    public static Connection getDXFK(){
        Connection conn = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(CC_URL,USERNAME,PASSWORD);

        } catch (ClassNotFoundException e) {
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        }catch (SQLException e) {
            System.out.println("连接失败!");
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 读写业务数据库连接
     * @return
     */
    public static Connection getDXYW(){
        Connection conn = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DXYW_URL,USERNAME,PASSWORD);

        } catch (ClassNotFoundException e) {
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        }catch (SQLException e) {
            System.out.println("连接失败!");
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 只读征信数据库连接
     * @return
     */
    public static Connection getZDZX(){
        Connection conn = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(ZX_URL,USERNAME,ZX_PASSWORD);

        } catch (ClassNotFoundException e) {
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        }catch (SQLException e) {
            System.out.println("连接失败!");
            e.printStackTrace();
        }
        return conn;
    }
    
    /**
     * 通讯记录
     * @return
     */
    public static Connection getTXJL(){
        Connection conn = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(ZX_TXJL,TX_USERNAME,TX_PASSWORD);

        } catch (ClassNotFoundException e) {
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        }catch (SQLException e) {
            System.out.println("连接失败!");
            e.printStackTrace();
        }
        return conn;
    }
    
    /**
     * 代前
     * @return
     */
    public static Connection getDQZX(){
        Connection conn = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DQ_URL,DQ_USERNAME,DQ_PASSWORD);

        } catch (ClassNotFoundException e) {
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        }catch (SQLException e) {
            System.out.println("连接失败!");
            e.printStackTrace();
        }
        return conn;
    }




    /*public static void main(String[] args){
        Connection conn = DBUtil.getDQZX();
        System.out.println(conn);
    }*/
}
