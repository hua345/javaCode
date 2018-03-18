package com.github.chenjianhua;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ResponseDownload
 */
public class ResponseDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String IMAGE_PATH = "/download/珠海合影.JPG";
    private static final int BUF_LEN = 2048;
    public ResponseDownload() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		downloadFileByOutputStream(response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	private void downloadFileByOutputStream(HttpServletResponse response) throws FileNotFoundException, IOException{
		//获取要下载的文件的绝对路径
		String realPath = this.getServletContext().getRealPath(IMAGE_PATH);
		//获取要下载的文件名
		String fileName = realPath.substring(realPath.lastIndexOf(File.separator) + 1);
		//设置content-disposition响应
		fileName = URLEncoder.encode(fileName, "UTF-8");
		response.setHeader("content-disposition", "attachment;filename=" + fileName);
		response.setContentType("text/html;charset=utf-8");
		
		//获取要下载的文件输入流
		InputStream in = new FileInputStream(realPath);
		int len = 0;
		//创建数据缓冲区
		byte[] buffer = new byte[BUF_LEN];
		//通过response获取OutputStream流
		OutputStream out = response.getOutputStream();
		//将FileInputStream流写入到buffer缓冲区
		while((len = in.read(buffer)) > 0){
			//使用OutputStream将缓冲区的数据输出到客户端浏览器
			out.write(buffer,0,len);
		}
		in.close();
	}
}
