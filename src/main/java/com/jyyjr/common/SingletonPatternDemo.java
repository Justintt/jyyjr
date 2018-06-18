package com.jyyjr.common;
/**
 * 单例使用测试
 * @author 作者 jinmin
 * @date 创建时间：2018年6月12日 下午4:56:51
 */
public class SingletonPatternDemo {

	public static void main(String[] args) {
		Singleton singleton = Singleton.getInstance();
		singleton.showMessage();
		
	}
}
