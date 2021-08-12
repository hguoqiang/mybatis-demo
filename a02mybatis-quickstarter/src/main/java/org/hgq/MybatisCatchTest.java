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
 * @description:
 * mybatis 缓存：
 * @author: huangguoqiang
 * @create: 2021-08-12 20:21
 **/
public class MybatisCatchTest {
    UserMapperAnnotation userMapper;
    OrderMapperAnnotation orderMapper;

    @Before
    public void before() throws IOException {
        InputStream in = Resources.getResourceAsStream("sqlMapConfigAnnotation.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);

        userMapper = sqlSession.getMapper(UserMapperAnnotation.class);
        orderMapper = sqlSession.getMapper(OrderMapperAnnotation.class);


    }
    @Test
    public void firstLevelCatch(){
        //在⼀个sqlSession中，对User表根据id进⾏两次查询，查看他们发出sql语句的情况
        User user1 = userMapper.findUserById(1);
        System.out.println(user1);

        System.out.println("==================================");
        User user2 = userMapper.findUserById(1);
        System.out.println(user2);
        System.out.println(user1==user2);
    }

    @Test
    public void firstLevelCatch2(){
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
        System.out.println(user1==user2);
    }
}
