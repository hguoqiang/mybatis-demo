package org.hgq.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hgq.io.Resources;
import org.hgq.pojo.Configuration;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-08-11 15:40
 **/
public class XmlConfigBuilder {
    private Configuration configuration;

    public XmlConfigBuilder() {
        configuration = new Configuration();
    }

    //该办法使用dom4j 解析xml
    public Configuration parseConfig(InputStream in) throws Exception {

        Document document = new SAXReader().read(in);

        //拿到根标签<configuration>
        Element rootElement = document.getRootElement();
        // 这个表达式（"//property"）表示任何位置的<property>标签都能获取到
        List<Element> propertys = rootElement.selectNodes("//property");
        Properties prop = new Properties();
        for (Element element : propertys) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            prop.setProperty(name, value);
        }

        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(prop.getProperty("driveClass"));
        dataSource.setJdbcUrl(prop.getProperty("jdbcUrl"));
        dataSource.setUser(prop.getProperty("username"));
        dataSource.setPassword(prop.getProperty("password"));

        configuration.setDataSource(dataSource);

        //mapper.xml解析：拿到路径，加载转成字节流
        List<Element> mappers = rootElement.selectNodes("//mapper");
        for (Element mapper : mappers) {
            String mapperPath = mapper.attributeValue("resource");
            InputStream mapperResource = Resources.getResourceAsStream(mapperPath);
            XmlMapperBuilder xmlMapperBuilder =  new XmlMapperBuilder(configuration);
            xmlMapperBuilder.parse(mapperResource);
        }

        return configuration;

    }
}
