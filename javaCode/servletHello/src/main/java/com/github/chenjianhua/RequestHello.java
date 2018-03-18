package com.github.chenjianhua;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RequestHello
 */
public class RequestHello extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestHello() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		request.getRemoteAddr();
		request.getQueryString();
		request.getRequestURI();//请求资源名部分
		request.getRequestURL();//请求时完整URL
		request.getParameter("type");//对应的查询字符串
		
		PrintWriter out = response.getWriter();
		//返回请求的IP地址
		out.println("getRemoteAddr:" + request.getRemoteAddr() + "\n");
		//查询字符串
		out.println("getQueryString:" + request.getQueryString() + "\n");
		//请求资源名部分
		out.println("getRequestURI:" + request.getRequestURI() + "\n");
		//请求时完整URL
		out.println("getRequestURL:" + request.getRequestURL() + "\n");
		//对应的查询字符串
		out.println("getParameter:" + request.getParameter("type") + "\n");

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
