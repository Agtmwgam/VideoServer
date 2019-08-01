<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>抽奖页面</title>
</head>
<body>
	一等奖
	<br /> 二等奖


	<button id="delUserButton" onclick="goLuck()">抽奖</button>
</body>
</html>

<script type="text/javascript">
	function goLuck() {

		$.ajax({
			type : 'POST',
			url : '/user/delUser',
			async : false,
			dataType : 'json',
			data : JSON.stringify(users),
			/* data : {
				"id" : id
			} , */
			contentType : 'application/json;charset=utf-8',
			success : function(data) {
				alert("OK+++");
				/* console.log(data) */
			},
			error : function(data) {
				alert("error");
			}
		});
		window.location.reload();
	}
</script>