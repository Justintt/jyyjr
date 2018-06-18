package com.jyyjr.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jyyjr.base.BaseTest;
import com.jyyjr.dao.ywdao.TestMobilebookMapper;

public class TestMobileBook extends BaseTest{
	
	@Autowired
	private TestMobilebookMapper testMobilebookMapper;
	
	@Test
	public void test01() {
		List<String> lists = testMobilebookMapper.selectMobileByVid("6b84134c92e2a7723d7b0aa39ae09219");
		List<String> mobs = new ArrayList<>();
		for(String list : lists) {
			if (list.contains(",") || list.contains(";") || list.contains("#")) {
				if (list.contains(",")) {
					String[] str = list.split(",");
					for(int i=0;i<str.length;i++) {
						mobs.add(str[i]);
					}
				}
				if (list.contains(";")) {
					String[] str = list.split(";");
					for(int i=0;i<str.length;i++) {
						mobs.add(str[i]);
					}
				}
				if (list.contains("#")) {
					String[] str = list.split("#");
					for(int i=0;i<str.length;i++) {
						mobs.add(str[i]);
					}
				}
				
			}else {
				mobs.add(list);
			}
		}
		for(String mob : mobs) {
			System.out.println(mob);
		}
		System.out.println(mobs.size());
	}

}
