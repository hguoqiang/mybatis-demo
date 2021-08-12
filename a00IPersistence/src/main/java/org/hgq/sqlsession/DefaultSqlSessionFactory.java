package org.hgq.sqlsession;

import org.hgq.pojo.Configuration;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-08-11 16:33
 **/
public class DefaultSqlSessionFactory  implements SqlSessionFactory{

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {

        SqlSession sqlSession = new DefaultSqlSession(configuration);

        return sqlSession;
    }
}
