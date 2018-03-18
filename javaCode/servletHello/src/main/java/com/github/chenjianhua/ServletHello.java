package com.github.chenjianhua;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletHello extends HttpServlet {
	public ServletHello(){
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		doPost(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		String loginType = request.getParameter("logintype");
		if("login_input".equals(loginType)){
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}else if("login".equals(loginType)){
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			
			String responseStr = "name:"+name + ",password:" + password;
			System.out.println(responseStr);
			
			PrintWriter out = response.getWriter();
			try{
				out.println(responseStr);
			}finally{
				out.close();
			}
		}else{
			PrintWriter out = response.getWriter();
			out.println(this.getServletContext().getRealPath("login.jsp"));
			out.println("Hello world");
			out.close();
		}
	}
}
