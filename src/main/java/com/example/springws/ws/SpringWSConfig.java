package com.example.springws.ws;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;


@Configuration
public class SpringWSConfig {
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> servletRegistrationBean(ApplicationContext applicationContext){
        MessageDispatcherServlet messageDispatcherServlet= new MessageDispatcherServlet();
        messageDispatcherServlet.setApplicationContext(applicationContext);
        messageDispatcherServlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(messageDispatcherServlet,"/ws/*");
    }
    @Bean
    public XsdSchema xmlSchemaDescrition ( ){
        SimpleXsdSchema simpleXsdSchema=new SimpleXsdSchema();
        simpleXsdSchema.setXsd(new ClassPathResource("xsd/data.xsd"));
        return simpleXsdSchema;
    }
    @Bean("banque")
    public DefaultWsdl11Definition defaultWsdl11Definition  ( ){
        DefaultWsdl11Definition defaultWsdl11Definition=new DefaultWsdl11Definition();
        defaultWsdl11Definition.setPortTypeName("BanqueService");
        defaultWsdl11Definition.setSchema(xmlSchemaDescrition());
        defaultWsdl11Definition.setLocationUri("/ws");
        defaultWsdl11Definition.setTargetNamespace(BanqueSaopEndPoint.NAME_SPACE);
        return defaultWsdl11Definition;
    }


}
