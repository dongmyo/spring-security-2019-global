package com.nhnent.edu.spring_security.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;

import javax.sql.DataSource;

/*
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- Root Context: defines shared resources visible to all other web components -->
    <import resource="security.xml" />

</beans>
 */
@Configuration
@ComponentScan(basePackages = "com.nhnent.edu.spring_security", excludeFilters = @ComponentScan.Filter(Controller.class))
@Import(SecurityConfig.class)
public class RootConfig {
    // TODO : #2 database config
    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:~/springsecurity;INIT=RUNSCRIPT FROM 'classpath:/script/schema.sql'");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        return dataSource;
    }

}
