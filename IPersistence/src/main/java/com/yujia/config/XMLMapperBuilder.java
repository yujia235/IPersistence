package com.yujia.config;

import com.yujia.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class XMLMapperBuilder {
    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parseConfiguration(InputStream inputstream) {
        try {
            Document read = new SAXReader().read(inputstream);
            // 根节点
            Element rootElement = read.getRootElement();
            String namespace = rootElement.attributeValue("namespace");
            parseElement(namespace, rootElement.selectNodes("//select"));
            parseElement(namespace, rootElement.selectNodes("//insert"));
            parseElement(namespace, rootElement.selectNodes("//delete"));
            parseElement(namespace, rootElement.selectNodes("//update"));
        } catch (DocumentException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void parseElement(String namespace, List<Element> list) throws ClassNotFoundException {
        String id, sql;
        Class<?> paramterClass, resultClass;
        MappedStatement mappedStatement;
        //  select|insert|delete|update语句
        for (Element element : list) {
            id = namespace.concat(".").concat(element.attributeValue("id"));
            sql = element.getTextTrim();
            paramterClass = getClassType(element.attributeValue("parameterType"));
            resultClass = getClassType(element.attributeValue("resultType"));
            mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setSql(sql);
            mappedStatement.setParamterClass(paramterClass);
            mappedStatement.setResultClass(resultClass);
            configuration.getMappedStatementMap().put(id, mappedStatement);
        }
    }

    private Class getClassType(String className) throws ClassNotFoundException {
        return className == null ? null : Class.forName(className);
    }
}
