package org.hgq.sqlsession;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-08-11 15:39
 **/
public interface SqlSessionFactory {

    SqlSession openSession();
}
