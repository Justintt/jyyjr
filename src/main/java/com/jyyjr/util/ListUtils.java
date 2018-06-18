package com.jyyjr.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
	
	/**
	 * 集合均分(默认4份)
	 * @param targe
	 * @return
	 */
	public static List<List<String>> splitList(List<String> targe){
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

}
