package com.github.springbootjunittest.springboot.servlet;

import com.github.springbootjunittest.springboot.aop.DemoAnnotationService;
import com.github.springbootjunittest.springboot.aop.DemoMethodService;
import com.github.springbootjunittest.springboot.aop.MyAnnotation;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author chenjianhua
 * @date 2021/4/1
 */
@WebServlet(urlPatterns = "/helloAnnotationServlet")
public class HelloAnnotationServlet extends HttpServlet {
    @Resource
    private ServletAopAdapterService servletAopAdapterService;

    @Override
    @MyAnnotation(name = "注解式拦截的add操作")
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        servletAopAdapterService.handleRequest();
        String name = req.getParameter("name");
        PrintWriter writer = resp.getWriter();
        writer.write("[helloAnnotationServlet] welcome " + name);
        writer.flush();
        writer.close();
    }
}