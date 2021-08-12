package org.hgq.mapper;

import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.hgq.pojo.Order;
import org.hgq.pojo.User;

import java.util.List;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-08-12 19:24
 **/
public interface OrderMapperAnnotation {

    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "ordertime", property = "ordertime"),
            @Result(column = "total", property = "total"),
            @Result(column = "uid", property = "user", javaType = User.class,
                    one = @One(select = "org.hgq.mapper.UserMapperAnnotation.findUserById"))
    })
    @Select(" select * from orders ")
    List<Order> findAll();


    @Select("select * from orders where uid=#{uid}")
    List<Order> findByUid(int uid);
}
