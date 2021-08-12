package org.hgq.sqlsession;

import org.hgq.pojo.Configuration;
import org.hgq.pojo.MappedStatement;
import org.hgq.utils.GenericTokenParser;
import org.hgq.utils.ParameterMapping;
import org.hgq.utils.ParameterMappingTokenHandler;

import javax.sql.DataSource;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-08-11 17:22
 **/
public class SimpleExecutor implements Executor {

    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        //操作jdbc
        //1、获取连接
        DataSource dataSource = configuration.getDataSource();

        Connection connection = dataSource.getConnection();

        //2、回去sql语句
        String sql = mappedStatement.getSql();

        //3、转换SQL语句，对 #{id} 进行
        BoundSql bondSql = getBondSql(sql);

        //4、获取预处理对象
        PreparedStatement preparedStatement = connection.prepareStatement(bondSql.getSql());
        //5、设置参数
        String parameterType = mappedStatement.getParameterType();
        Class<?> parameterClassType = getClassType(parameterType);
        List<ParameterMapping> parameterMappings = bondSql.getParameterMappings();
        for (int i = 0; i < parameterMappings.size(); i++) {
            ParameterMapping parameterMapping = parameterMappings.get(i);
            String content = parameterMapping.getContent();
            //根据反射获取实体参数里面具体的属性值
            Field field = parameterClassType.getDeclaredField(content);
            field.setAccessible(true);
            Object value = field.get(params[0]);
            preparedStatement.setObject(i + 1, value);
        }
        //6、执行sql
        ResultSet resultSet = preparedStatement.executeQuery();
        //7、封装结果集
        String resultType = mappedStatement.getResultType();
        Class<?> resultClassType = getClassType(resultType);


        List<Object> resultList = new ArrayList<>();
        while (resultSet.next()) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            Object result = resultClassType.newInstance();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                String columnName = metaData.getColumnName(i);
                Object columnValue = resultSet.getObject(columnName);

                //使用反射或者内省根据数据库表和尸体的对应关系完成封装
                //内省库中的一个类，对resultClassType中的columnName生成读写方法
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultClassType);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                //把值封装的 result 对象中
                writeMethod.invoke(result,columnValue);
            }
            resultList.add(result);

        }

        return (List<E>) resultList;
    }

    private Class<?> getClassType(String parameterType) throws ClassNotFoundException {
        if (parameterType != null && !"".equals(parameterType.trim())) {
            return Class.forName(parameterType);
        }
        return null;
    }

    /**
     * 完成 对 #{} 的解析工作：
     * 1、将 #{} 使用 ? 代替
     * 2、把 #{} 里面的值解析出来进行存储
     *
     * @param sqlText
     * @return
     */
    private BoundSql getBondSql(String sqlText) {
        //标记处理类，配合标记解析器完成对占位符的解析处理工作
        ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser tokenParser = new GenericTokenParser("#{", "}", tokenHandler);

        //解析后的sql
        String sql = tokenParser.parse(sqlText);

        //#{}里面的内容
        List<ParameterMapping> parameterMappings = tokenHandler.getParameterMappings();
        return new BoundSql(sql, parameterMappings);
    }
}
