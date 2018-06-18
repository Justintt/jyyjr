package com.jyyjr.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.jyyjr.base.BaseTest;
import com.jyyjr.dao.csdao.JmVidMapper;

/**
 * @author 作者 jinmin
 * @date 创建时间：2018年6月13日 下午2:30:54
 */
public class TestJmVid extends BaseTest{
	
	@Autowired
	private JmVidMapper jmVidMapper;
	
	@Test
	@Transactional("cs")
	public void test01() {
		List<String> lists = new ArrayList<>();
		lists.add("1c05b3f980c9ea600ef8219815c1bf58");
		lists.add("2d7603bd72d81b282bd2683cc100d877");
		lists.add("e201824962918ffd7cbab0c0e2a1a7e3");
		lists.add("d94da6fe40c6ee96bbc716d7b5059716");
		lists.add("2c1401d0ab9463021e5f3b5a38f84122");
		lists.add("4ac92a71ad66a1f87832e91c89950db8");
		lists.add("6fa4b0f509f919e4e0586794e446e226");
		lists.add("23b86affab2e07ce07aa6338bed86b1b");
		lists.add("1c05b3f980c9ea600ef8219815c1bf58");
		lists.add("2d7603bd72d81b282bd2683cc100d877");
		lists.add("e201824962918ffd7cbab0c0e2a1a7e3");
		lists.add("d94da6fe40c6ee96bbc716d7b5059716");
		lists.add("2c1401d0ab9463021e5f3b5a38f84122");
		lists.add("4ac92a71ad66a1f87832e91c89950db8");
		//lists.add(null);
		lists.add("23b86affab2e07ce07aa6338bed86b1b");
		lists.add("1c05b3f980c9ea600ef8219815c1bf58");
		lists.add("2d7603bd72d81b282bd2683cc100d877");
		lists.add("e201824962918ffd7cbab0c0e2a1a7e3");
		lists.add("d94da6fe40c6ee96bbc716d7b5059716");
		lists.add("2c1401d0ab9463021e5f3b5a38f84122");
		lists.add("4ac92a71ad66a1f87832e91c89950db8");
		lists.add("6fa4b0f509f919e4e0586794e446e226");
		
		for(String vid : lists) {
			int rowCount = jmVidMapper.insertVid(vid);
			System.out.println(vid+":"+rowCount);
		}
	}
}
