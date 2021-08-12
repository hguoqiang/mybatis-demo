package org.hgq.mapper;

import org.apache.ibatis.annotations.Select;
import org.hgq.pojo.Role;

import java.util.List;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-08-12 19:43
 **/
public interface RoleMapperAnnotation {
    @Select("select * from sys_role r,sys_user_role ur where r.id=ur.roleid and ur.userid=#{uid}")
    List<Role> findByUid(int uid);
}
