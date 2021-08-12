package org.hgq.mapper;

import org.hgq.io.Resources;
import org.hgq.domain.User;
import org.hgq.sqlsession.SqlSession;
import org.hgq.sqlsession.SqlSessionFactory;
import org.hgq.sqlsession.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

/**
 * @description:
 * 自定义持久层框架问题：
 * 1、存在代码重复，加载配置文件、创建SqlSessionFactory、生成SqlSession
 * 2、硬编码问题，statmentId
 * 解决：不需要 UserDaoImpl，使用代理模式生成UserDao的实现类
 * @author: huangguoqiang
 * @create: 2021-08-12 11:33
 **/
public class UserDaoImpl implements UserDao {
    @Override
    public List<User> findAll() throws Exception {
        InputStream in = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        List<User> objects = sqlSession.selectList("user.selectList");
        for (User object : objects) {
            System.out.println(object);
        }

        return objects;
    }

    @Override
    public User findByCondition(User user) throws Exception {
        InputStream in = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        User result = sqlSession.selectOne("user.selectOne", user);
        System.out.println(result);
        return user;
    }
}
