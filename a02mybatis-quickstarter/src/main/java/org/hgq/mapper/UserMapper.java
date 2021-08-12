package org.hgq.mapper;

import org.hgq.pojo.User;

import java.util.List;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-08-12 11:31
 **/
public interface UserMapper {
    //查询所有
    List<User> findAll() throws Exception;

    //查询单个
    User findByCondition(User user) throws Exception;

    List<User> find(User user) throws Exception;

    List<User> findByIds(List<Integer> list) throws Exception;

    List<User> findAllContainsOrder() throws Exception;


    List<User> findAllUserAndRole();
}
