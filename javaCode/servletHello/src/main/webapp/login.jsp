<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Servlet Hello</title>
</head>
<body>
    <form action="servlethello?logintype=login" method="post">
    <div>
	    <span>Name:</span>
	    <input type="text" name="name" /> 
    </div>  
     <div>
	    <span>Password:</span>
	    <input type="password" name="password" /> 
    </div> 
	<div>
	    <input type="submit" value="登录" />  
	</div>
    </form>  
</body>
</html>
