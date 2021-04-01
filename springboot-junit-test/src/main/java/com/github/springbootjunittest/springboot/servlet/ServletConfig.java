package com.github.springbootjunittest.springboot.servlet;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * @author chenjianhua
 * @date 2021/4/1
 */
@Configuration
public class ServletConfig implements ServletContextInitializer {
    @Resource
    private HelloRegistrationBeanServlet helloRegistrationBeanServlet;

    @Resource
    private HelloRegistrationBeanServlet2 helloRegistrationBeanServlet2;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        ServletRegistration servletRegistration = servletContext.addServlet("helloContextServlet", HelloContextServlet.class);
        servletRegistration.addMapping("/helloContextServlet");
    }

    @Bean
    public ServletRegistrationBean servletBean() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.addUrlMappings("/helloRegistrationBeanServlet");
        registrationBean.setServlet(helloRegistrationBeanServlet);
        return registrationBean;
    }
    @Bean
    public ServletRegistrationBean servletBean2() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.addUrlMappings("/helloRegistrationBeanServlet2");
        registrationBean.setServlet(helloRegistrationBeanServlet2);
        return registrationBean;
    }
}
