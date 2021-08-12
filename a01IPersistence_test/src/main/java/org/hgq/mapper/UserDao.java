package org.hgq.mapper;

import org.hgq.domain.User;

import java.util.List;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-08-12 11:31
 **/
public interface UserDao {
    //查询所有
    List<User> findAll() throws Exception;

    //查询单个
    User findByCondition(User user) throws Exception;


}
