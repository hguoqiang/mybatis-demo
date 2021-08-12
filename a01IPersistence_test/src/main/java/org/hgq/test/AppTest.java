package org.hgq.test;

import org.hgq.mapper.UserDao;
import org.hgq.io.Resources;
import org.hgq.domain.User;
import org.hgq.sqlsession.SqlSession;
import org.hgq.sqlsession.SqlSessionFactory;
import org.hgq.sqlsession.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-08-11 14:59
 **/
public class AppTest {
    public static void main(String[] args) throws Exception {
        InputStream in = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);

        SqlSession sqlSession = sqlSessionFactory.openSession();

        User u= new User();
        u.setId(1);
        u.setUsername("lucy");
//        User user = sqlSession.selectOne("user.selectOne", u);
//        System.out.println(user);
//
//        List<User> objects = sqlSession.selectList("user.selectList");
//        for (User object : objects) {
//            System.out.println(object);
//        }

        UserDao userDao = sqlSession.getMapper(UserDao.class);
        List<User> userList = userDao.findAll();
        for (User object : userList) {
            System.out.println(object);
        }
        User result = userDao.findByCondition(u);
        System.out.println(result);
    }



}
