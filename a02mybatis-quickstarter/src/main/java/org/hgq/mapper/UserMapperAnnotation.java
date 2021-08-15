package org.hgq.mapper;

import org.apache.ibatis.annotations.*;
import org.hgq.pojo.User;

import java.util.List;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-08-12 16:56
 **/
//开启二级缓存
//@CacheNamespace(implementation = RedisCache.class)
public interface UserMapperAnnotation {


    //查询所有用户、同时查询每个用户关联的订单信息
    @Select("select * from user")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "orderList", column = "id", javaType = List.class,
                    many = @Many(select = "org.hgq.mapper.OrderMapperAnnotation.findByUid"))
    })
    List<User> findAllUserAndOrder();

    //查询所有用户、同时查询每个用户关联的角色信息
    @Select("select * from user")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "roleList", column = "id", javaType = List.class,
                    many = @Many(select = "org.hgq.mapper.RoleMapperAnnotation.findByUid"))
    })
    List<User> findAllUserAndRole();


    //添加用户
    @Insert("insert into user values(#{id},#{username},#{password},#{birthday})")
    void addUser(User user);

    //更新用户
    @Update("update user set username = #{username} where id = #{id}")
    void updateUser(User user);

    //查询用户
    @Select("select * from user")
    List<User> selectUser();

    //删除用户
    @Delete("delete from user where id = #{id}")
    void deleteUser(Integer id);


    //根据id查询用户
    //@Options(useCache = false)
    @Select({"select * from user where id = #{id}"})
    User findUserById(Integer id);


}
