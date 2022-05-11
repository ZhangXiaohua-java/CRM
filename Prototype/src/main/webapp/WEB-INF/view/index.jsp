<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/"+request.getContextPath();
%>
<base href="${basePath}">
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
	<script type="text/javascript">
		document.location.href ="/settings/qx/user/login";
	</script>
</body>
</html>