<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*,java.text.*"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- <meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge"> -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<style type="text/css">
/* 设置表格文字左右和上下居中对齐 */
#userTable td {
	vertical-align: middle;
	text-align: center;
}
</style>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改用户</title>
<!-- <script type="text/javascript" src="/jq/jquery-1.8.3.min.js"></script> -->
<script src="${pageContext.request.contextPath}/jq/jquery-1.8.3.min.js"></script>
<link rel="stylesheet" href="../css/popWindow.css">
</head>

<body>
	<%-- <a href="${pageContext.request.contextPath}/WEB-INF/JSP/welcome.jsp">创建用户</a> --%>
	<%-- 	<iframe src="${pageContext.request.contextPath}/WEB-INF/JSP/welcome.jsp">这是A</iframe> --%>
	<div class="checkbox">
		<a href="javascript:0;" class="cd-popup-trigger0">创建用户</a>
	</div>
	<table id="userTable" align="center" border="1" cellspacing="0"
		cellpadding="0" width="600">
		<thead>
			<td>序号</td>
			<td>用户ID</td>
			<td>姓名</td>
			<td>年龄</td>
			<td>性别</td>
			<td>地址</td>
			<td>操作</td>
		</thead>
		<c:forEach var="user" items="${userList}" varStatus="status">
			<tr>
				<td id="index">${status.index+1}</td>
				<td id="userId">${user.id}</td>
				<td id="userName">${user.name}</td>
				<td id="userAge">${user.age}</td>
				<td id="userSex">${user.sex}</td>
				<td id="userAddress">${user.address}</td>
				<td>
					<button id="delUserButton" onclick="delUser('${user.id}')">删除用户</button>
					<!-- 					<button id="delUserButton" onclick="delUser()">删除用户</button> -->
					<button id="updateUserButton" onclick="testAjax()">修改用户信息</button>
					<button>修改密码</button>
					<button>恢复初始密码</button>
				</td>
			</tr>
		</c:forEach>
	</table>

	<!-- 弹窗Start -->
	<!-- <div class="checkbox">
		<a href="javascript:0;" class="cd-popup-trigger0">创建用户</a>
	</div> -->
	<!--弹框-->
	<%-- <form id="itemForm" action="${pageContext.request.contextPath }/items/editItemsSubmit.action" method="post" > --%>
	<form id="userForm" action="${pageContext.request.contextPath }/user/createUser" method="post" >   <!-- target="_blank" -->
		<div class="cd-popup">
			<div class="cd-popup-container">
				<h2 align="center">创建用户</h2>
				<div class="registerForm">
					<table id="creatUser">
						<tr>
							<td width="100px"><lable for="userName">用户名</lable></td>
							<td class="inputInformation">*<input type="text"
								name="userName" id="userName" placeholder="用户名"  value="${user.name}"/></td>
						</tr>
						<tr>
							<td width="100px">密码</td>
							<td class="inputInformation">*<input type="password"
								name="password" id="password" placeholder="密码" value="${user.password}"/></td>
						</tr>
						<tr>
							<td width="100px">确认密码</td>
							<td class="inputInformation">*<input type="password"
								name="password2" id="password2" placeholder="确认密码"  /></td>
						</tr>
						<tr>
							<td width="100px">邮箱</td>
							<td class="inputInformation"><input type="text"
								name="EmailAddress" id="EmailAddress" placeholder="邮箱用于找回密码"  value="${user.email}"/></td>
						</tr>
						<tr>
							<td width="100px">手机</td>
							<td class="inputInformation"><input type="text"
								name="phoneNumer" id="phoneNO" placeholder="手机号用于找回密码" value="${user.phoneNO}"/></td>
						</tr>
					</table>
				</div>
				<a href="#0" class="cd-popup-close">close</a> <br /> 
				<input class="login-button" type="submit" name="sub" value="确认" />
					
					
					<%-- <a href="#0" class="cd-popup-close">close</a> <br /> <input
					class="login-button" type="submit" name="sub" value="确认"
					onclick="submitUser()/>  --%>
				<%-- '${#userName}','${#password}','${#password2}','${#EmailAddress}','${#phoneNO}' --%>
			</div>
		</div>
	</form>
	<!-- 弹窗End -->

</body>
</html>
<script type="text/javascript">
	/* $("#delUserButton").click(function() {
		 users = {
			"id" : $("#userId").html(),
			"name" : $("#userName").html(),
			"age" : $("#userAge").html(),
			"sex" : $("#userSex").html(),
			"address" : $("#userAddress").html(),
		};  */
		
		function submitUser(userName, password, password2, EmailAddress, phoneNO) {
			var users = {
				"name" : username,
				"password" : password,
				"EmailAddress" : EmailAddress,
				"phoneNO" : phoneNO
			//由于后端只根据id删除对象,所以其他属性暂时不传
			}

			$.ajax({
				type : 'POST',
				url : '/user/createUser',
				async : false,
				dataType : 'json',
				data : JSON.stringify(users),
				contentType : 'application/json;charset=utf-8 ',
				success : function(data) {
					alert("OK+++");
				},
				error : function(data) {
					alert("error");
				}
			});
			window.location.reload();
		}

	function delUser(id) {
		var users = {
			"id" : id
		//由于后端只根据id删除对象,所以其他属性暂时不传
		}

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
	//)
	
	/*弹框JS内容*/
			jQuery(document).ready(function($) {
				//打开窗口
				$('.cd-popup-trigger0').on('click', function(event) {
					event.preventDefault();
					$('.cd-popup').addClass('is-visible');
					//$(".dialog-addquxiao").hide()
				});
				//关闭窗口
				$('.cd-popup').on('click', function(event) {
					if($(event.target).is('.cd-popup-close') || $(event.target).is('.cd-popup')) {
						event.preventDefault();
						$(this).removeClass('is-visible');
					}
				});
				//ESC关闭
				$(document).keyup(function(event) {
					if(event.which == '27') {
						$('.cd-popup').removeClass('is-visible');
					}
				});

			});
	
	
</script>