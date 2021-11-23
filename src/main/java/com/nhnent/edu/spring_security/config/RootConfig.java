package com.nhnent.edu.spring_security.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;

// TODO : #1-1 root context configuration
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
}
