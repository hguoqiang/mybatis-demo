package org.hgq.sqlsession;

import org.hgq.pojo.Configuration;

import java.lang.reflect.*;
import java.util.List;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-08-11 16:36
 **/
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statmentId, Object... params) {
        //在这里完成对 SimpleExecutor 的 query方法调用
        Executor executor = new SimpleExecutor();
        List<E> list = null;
        try {
            list = executor.query(configuration, configuration.getMappedStatementMap().get(statmentId), params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public <E> E selectOne(String statmentId, Object... params) {
        List<Object> objects = selectList(statmentId, params);
        if (objects == null || objects.size() == 0) {
            return null;
        }
        if (objects.size() == 1) {
            return (E) objects.get(0);
        } else {
            throw new RuntimeException("expect one,but found more record");
        }
    }

    /**
     * 为 dao 生成代理对象
     *
     * @param classType
     * @param <E>
     * @return
     */
    @Override
    public <E> E getMapper(Class<?> classType) {
        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{classType}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //proxy：当前代理对象的引用
                //method：当前被调用方法的引用
                //args：当前被调用方法传递的参数

                //根据不同情况调用 selectOne 或者 selectList
                //准备参数
                // 1、statementId，sql语句的唯一标识，组成规则：namespace.id，所以修改UserMapper.xml文件，
                // 使namespace的值根接口的全限定名相同，使 select 标签的 id 值跟接口中的方法名相同
                String statementId = classType.getName() + "." + method.getName();
                //String className = method.getDeclaringClass().getName();

                //判断调用哪个方法，
                // 获取被调用方法的返回值
                Type genericReturnType = method.getGenericReturnType();
                //判断是否进行了 泛型类型 参数化 ，就是返回值类型是否是有泛型，如果有泛型，认为返回值是集合，没有泛型，是单一实体
                if (genericReturnType instanceof ParameterizedType) {
                   return selectList(statementId, args);
                }else {
                    return  selectOne(statementId, args);
                }
            }
        });
        return (E) proxyInstance;
    }
}
