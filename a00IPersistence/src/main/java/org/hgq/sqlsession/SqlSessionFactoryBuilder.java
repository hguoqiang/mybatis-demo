package org.hgq.sqlsession;

import org.hgq.config.XmlConfigBuilder;
import org.hgq.pojo.Configuration;

import java.io.InputStream;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-08-11 15:37
 **/
public class SqlSessionFactoryBuilder {
    public SqlSessionFactory build(InputStream in) throws Exception {
        //使用dom4j解析xml,封装到Configuration
        XmlConfigBuilder xmlConfigBuilder = new XmlConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfig(in);

        //创建SqlSessionFactory

        DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

        return sqlSessionFactory;
    }
}
