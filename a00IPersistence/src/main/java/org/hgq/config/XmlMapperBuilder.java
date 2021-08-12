package org.hgq.config;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hgq.pojo.Configuration;
import org.hgq.pojo.MappedStatement;

import java.io.InputStream;
import java.util.List;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-08-11 16:22
 **/
public class XmlMapperBuilder {
    private Configuration configuration;

    public XmlMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(InputStream inputStream) throws Exception {
        Document document = new SAXReader().read(inputStream);
        Element rootElement = document.getRootElement();
        String namespace = rootElement.attributeValue("namespace");

        List<Element> selectList = rootElement.selectNodes("//select");

        for (Element element : selectList) {
            String id = element.attributeValue("id");
            String resultType = element.attributeValue("resultType");
            String parameterType = element.attributeValue("parameterType");
            String sqlText = element.getTextTrim();
            MappedStatement statement = new MappedStatement();
            statement.setId(id);
            statement.setResultType(resultType);
            statement.setParameterType(parameterType);
            statement.setSql(sqlText);

            String key = namespace + "." + id;
            configuration.getMappedStatementMap().put(key, statement);
        }

    }
}
