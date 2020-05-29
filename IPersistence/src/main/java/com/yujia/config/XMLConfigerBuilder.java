package com.yujia.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.yujia.io.Resources;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class XMLConfigerBuilder {

    private Configuration configuration;

    public XMLConfigerBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parseConfiguration(InputStream inputstream) throws Exception {
        Document read = new SAXReader().read(inputstream);
        // 根节点
        Element rootElement = read.getRootElement();

        // 数据源配置
        Properties properties = new Properties();
        ((List<Element>) rootElement.selectNodes("//property")).stream().forEach((property) -> {
            properties.put(property.attributeValue("name"), property.attributeValue("value"));
        });

        // 连接池设置
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        configuration.setDataSource(dataSource);
        dataSource.setDriverClass(properties.getProperty("driverClass"));
        dataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        dataSource.setUser(properties.getProperty("username"));
        dataSource.setPassword(properties.getProperty("password"));

        // mapperXml
        List<Element> list = rootElement.selectNodes("//mapper");
        list.stream().forEach(element -> new XMLMapperBuilder(configuration).parseConfiguration(Resources.load(element.attributeValue("resource"))));
    }
}
