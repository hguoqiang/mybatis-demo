package org.hgq.sqlsession;

import java.util.List;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-08-11 16:35
 **/
public interface SqlSession {

    //查询所有
    <E> List<E> selectList(String statmentId, Object... params);

    //查询单个
    <E> E selectOne(String statmentId, Object... params);

    <E> E getMapper(Class<?> classType);
}
