package org.hgq.sqlsession;

import org.hgq.pojo.Configuration;
import org.hgq.pojo.MappedStatement;

import java.util.List;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-08-11 17:22
 **/
public interface Executor {
    <E> List<E> query(Configuration configuration, MappedStatement statement, Object... params) throws Exception;
}
