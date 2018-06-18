package com.jyyjr.common;
/**
 * 单例（测试）
 * @author 作者 jinmin
 * @date 创建时间：2018年6月12日 下午4:54:42
 */
public class Singleton {
	
	private static Singleton instance = new Singleton();
	
    private Singleton (){}
    
    public static Singleton getInstance() {  
    	return instance;  
    }
    
    public void showMessage(){
        System.out.println("Hello World!");
    }
}
