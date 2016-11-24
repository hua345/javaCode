package com.github.chenjianhua;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;

//maven读取配置文件
//
//test
public class ReadProperties extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String JDBC_FILENAME = "jdbc.properties";
    private static final String WEBINF_NAME = "WEB-INF";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReadProperties() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("content-type","text/html;charset=UTF-8");
		readResources(response);
		readWEBINF(response);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	//maven读取src/main/resources目录下的配置文件
	//编译后resources目录下的文件在target/classes目录中
	private void readResources(HttpServletResponse response) throws IOException {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		
		Properties property = new Properties();
		InputStream inputStream = classloader.getResourceAsStream(JDBC_FILENAME);
		property.load(inputStream);

		String url = property.getProperty("url");
		String username = property.getProperty("username");
		String password = property.getProperty("password");
		PrintWriter out = response.getWriter();
		out.println("maven读取src/main/resources目录下的配置文件");
		out.println(MessageFormat.format("url={0},username={1},password={2}", 
               url, username, password));

	}
	//maven读取WEB-INF目录下配置文件
	private void readWEBINF(HttpServletResponse response) throws IOException {

		Properties property = new Properties();
		InputStream inputStream = this.getServletContext().getResourceAsStream(WEBINF_NAME + File.separator + JDBC_FILENAME);
		property.load(inputStream);

		String url = property.getProperty("url");
		String username = property.getProperty("username");
		String password = property.getProperty("password");
		PrintWriter out = response.getWriter();
		out.println("maven读取WEB-INF目录下配置文件");
		out.println(MessageFormat.format("url={0},username={1},password={2}", 
	               url, username, password));
	}

}
