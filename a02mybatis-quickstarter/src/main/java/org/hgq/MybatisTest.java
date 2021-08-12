package org.hgq;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.hgq.mapper.OrderMapper;
import org.hgq.mapper.UserMapper;
import org.hgq.pojo.Order;
import org.hgq.pojo.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-08-12 14:29
 **/
public class MybatisTest {
    @Test
    public void test() throws IOException {
        InputStream in = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Object> objects = sqlSession.selectList("userMapper.findAll");
        for (Object object : objects) {
            System.out.println("所以数据：" + object);
        }
    }

    @Test
    public void test2() throws IOException {
        InputStream in = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        //设置为 true ，当前事务自动提交
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        User user = new User();
        user.setId(3);
        user.setUsername("zhangsan");
        user.setPassword("zhangsan123");
        user.setBirthday("20180906");
        sqlSession.insert("userMapper.saveUser", user);
    }

    @Test
    public void test3() throws IOException {
        InputStream in = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(3);
        user.setUsername("zhangss");
        user.setPassword("zhangss123");
        sqlSession.update("userMapper.updateUser", user);
        sqlSession.commit();
    }

    @Test
    public void test4() throws IOException {
        InputStream in = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        sqlSession.delete("userMapper.deleteUser", 3);
        sqlSession.commit();
    }


    @Test
    public void test5() throws Exception {
        InputStream in = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> all = mapper.findAll();
        for (User user : all) {
            System.out.println(user);
        }
    }

    @Test
    public void test6() throws Exception {
        InputStream in = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User u = new User();
        u.setId(1);
        List<User> all = mapper.find(u);
        for (User user : all) {
            System.out.println(user);
        }
    }

    @Test
    public void test7() throws Exception {
        InputStream in = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        List<User> all = mapper.findByIds(Arrays.asList(1,2));
        for (User user : all) {
            System.out.println(user);
        }
    }

    @Test
    public void test8() throws Exception {
        InputStream in = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);

        List<Order> all = mapper.findAll();
        for (Order o : all) {
            System.out.println(o);
        }
    }

    @Test
    public void test9() throws Exception {
        InputStream in = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        List<User> all = mapper.findAllContainsOrder();
        for (User o : all) {
            System.out.println(o);
        }
    }
}
