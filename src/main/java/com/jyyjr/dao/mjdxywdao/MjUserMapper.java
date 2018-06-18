package com.jyyjr.dao.mjdxywdao;

public interface MjUserMapper {
	/**
     * 根据电话号码查询vid
     * @author 作者 jinmin
     * @date 创建时间：2018年6月11日 下午1:47:59
     * @param mobile
     * @return
     */
    String selectVidByMobile(String mobile);
}