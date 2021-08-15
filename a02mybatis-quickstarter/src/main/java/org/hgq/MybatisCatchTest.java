package org.hgq;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.hgq.mapper.OrderMapperAnnotation;
import org.hgq.mapper.UserMapperAnnotation;
import org.hgq.pojo.User;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @description: mybatis 缓存：
 * @author: huangguoqiang
 * @create: 2021-08-12 20:21
 **/
public class MybatisCatchTest {
    UserMapperAnnotation userMapper;
    OrderMapperAnnotation orderMapper;
    SqlSessionFactory sqlSessionFactory;

    @Before
    public void before() throws IOException {
        InputStream in = Resources.getResourceAsStream("sqlMapConfigAnnotation.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);

        userMapper = sqlSession.getMapper(UserMapperAnnotation.class);
        orderMapper = sqlSession.getMapper(OrderMapperAnnotation.class);


    }

    @Test
    public void firstLevelCatch() {
        //在⼀个sqlSession中，对User表根据id进⾏两次查询，查看他们发出sql语句的情况
        User user1 = userMapper.findUserById(1);
        System.out.println(user1);

        System.out.println("==================================");
        User user2 = userMapper.findUserById(1);
        System.out.println(user2);
        System.out.println(user1 == user2);
    }

    @Test
    public void firstLevelCatch2() {
        //同样是对user表进⾏两次查询，只不过两次查询之间进⾏了⼀次update操作
        User user1 = userMapper.findUserById(1);
        System.out.println(user1);

        System.out.println("==================================");
        User user = new User();
        user.setId(1);
        user.setUsername("tom");
        userMapper.updateUser(user);

        System.out.println("==================================");
        User user2 = userMapper.findUserById(1);
        System.out.println(user2);
        System.out.println(user1 == user2);
    }

    @Test
    public void secondLevelCatch() {
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        SqlSession sqlSession3 = sqlSessionFactory.openSession();
        UserMapperAnnotation mapper1 = sqlSession1.getMapper(UserMapperAnnotation.class);
        UserMapperAnnotation mapper2 = sqlSession2.getMapper(UserMapperAnnotation.class);
        UserMapperAnnotation mapper3 = sqlSession3.getMapper(UserMapperAnnotation.class);


        User user1 = mapper1.findUserById(1);
        System.out.println(user1);
        sqlSession1.close();//清空一级缓存
        System.out.println("==================================");

     /*   User user = new User();
        user.setId(1);
        user.setUsername("tomm");
        mapper3.updateUser(user);
        sqlSession3.commit();*/

        System.out.println("==================================");
        User user2 = mapper2.findUserById(1);
        System.out.println(user2);
        System.out.println(user1 == user2);
    }
}
