package com.jyyjr.dao.csdao;

import com.jyyjr.pojo.Yy20180521Data;

public interface Yy20180521DataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Yy20180521Data record);

    int insertSelective(Yy20180521Data record);

    Yy20180521Data selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Yy20180521Data record);

    int updateByPrimaryKey(Yy20180521Data record);
}