package org.hgq;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.hgq.mapper.OrderMapperAnnotation;
import org.hgq.mapper.UserMapperAnnotation;
import org.hgq.pojo.Order;
import org.hgq.pojo.User;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-08-12 16:59
 **/
public class MybatisTestAnnotation {

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
    public void addUser() {

        User user = new User();
        user.setId(3);
        user.setUsername("zhangs");
        user.setPassword("zhangs12");
        user.setBirthday("20180606");
        userMapper.addUser(user);

    }

    @Test
    public void testUpdate() throws IOException {
        User user = new User();
        user.setId(3);
        user.setUsername("测试数据修改");
        user.setPassword("abc");
        user.setBirthday("20180606");
        userMapper.updateUser(user);
    }

    @Test
    public void testDelete() throws IOException {
        userMapper.deleteUser(16);
    }

    @Test
    public void testFindById() throws IOException {
        User user = userMapper.findUserById(3);
        System.out.println(user);
    }

//    @Test
//    public void testFindAll() throws IOException {
//        List<User> all = userMapper.findAll();
//        for (User user : all) {
//            System.out.println(user);
//        }
//    }

    @Test
    public void testSelectOrderAndUser() {
        List<Order> all = orderMapper.findAll();
        for (Order order : all) {
            System.out.println(order);
        }
    }

    @Test
    public void testSelectUserAndOrder() {
        List<User> all = userMapper.findAllUserAndOrder();
        for (User user : all) {
            System.out.println(user);
//            System.out.println(user.getUsername());
//            List<Order> orderList = user.getOrderList();
//            for(Order order : orderList){
//                System.out.println(order);
//            }
            System.out.println("-----------------------------");
        }
    }

    @Test
    public void testFindAllUserAndRole() {
        List<User> all = userMapper.findAllUserAndRole();
        for (User user : all) {
            System.out.println(user);
//            System.out.println(user.getUsername());
//            List<Role> roleList = user.getRoleList();
//            for(Role role : roleList){
//                System.out.println(role);
//            }
            System.out.println("----------------------------------");
        }
    }
}
