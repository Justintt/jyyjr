package com.jyyjr.dao.ywdao;

import java.util.List;

import com.jyyjr.pojo.UserBorrow;

public interface UserBorrowMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserBorrow record);

    int insertSelective(UserBorrow record);

    UserBorrow selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserBorrow record);

    int updateByPrimaryKey(UserBorrow record);
    
    List<UserBorrow> selectUserBorrowByVid(String vid);
    
    /**
     * 借款次数已经大于1次的老用户
     * @return
     */
    List<String> selectBorrowCount();
    
    /**
     * 查询Y0001命中用户
     * @return
     */
    List<String> selectBorrowInUserWarning();
    
    /**
     * 获取正在借贷中的用户
     * @return
     */
    List<String> selectBorrowNow();
    
    /**
     * 查询marning中status=1的用户中的还款或逾期用户
     * @return
     */
    List<String> selectRepayOrOverdueByMarning();
}