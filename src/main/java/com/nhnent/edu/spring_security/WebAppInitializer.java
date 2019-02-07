package com.nhnent.edu.spring_security;

import com.nhnent.edu.spring_security.config.RootConfig;
import com.nhnent.edu.spring_security.config.WebConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    /*
        <context-param>
            <param-name>contextClass</param-name>
            <param-value>org.springframework.web.context.support.AnnotationConfigWebApplicationContext</param-value>
        </context-param>
        <context-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>com.nhnent.edu.spring_security.config.RootConfig</param-value>
        </context-param>
        <listener>
            <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
        </listener>
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { RootConfig.class };
    }

    /*
       <servlet>
           <servlet-name>appServlet</servlet-name>
           <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
           <init-param>
               <param-name>contextClass</param-name>
               <param-value>org.springframework.web.context.support.AnnotationConfigWebApplicationContext</param-value>
           </init-param>
           <init-param>
               <param-name>contextConfigLocation</param-name>
               <param-value>com.nhnent.edu.spring_security.config.WebConfig</param-value>
           </init-param>
           <load-on-startup>1</load-on-startup>
       </servlet>
    */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { WebConfig.class };
    }


    /*
        <servlet-mapping>
            <servlet-name>appServlet</servlet-name>
            <url-pattern>/</url-pattern>
        </servlet-mapping>
     */
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

}
