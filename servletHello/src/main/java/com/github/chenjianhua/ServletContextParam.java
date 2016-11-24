package com.github.chenjianhua;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;

//在web.xml文件中使用<context-param>标签配置WEB应用的初始化参数
//<context-param>
//  <param-name>url</param-name>
//  <param-value>jdbc:mysql://localhost:3306/test</param-value>
//</context-param>
public class ServletContextParam extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletContextParam() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletContext context = this.getServletContext();
		String mysqlUrl = context.getInitParameter("mysqlurl");
		response.getWriter().append("mysqlUrl:" + mysqlUrl);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
