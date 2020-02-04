<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE HTML>
<html lang="zh_cn">
	<head>
		<meta charset="utf-8" />
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>经营者信用公示平台【admin登录】</title>
		<link rel="shortcut icon" type="image/x-icon" href="./static/images/favicon.ico" />
		<link rel="stylesheet" href="./static/style/css/base.css" />
		<link rel="stylesheet" href="./static/style/css/login.css" />
		<script type="text/javascript" src="./static/library/jQuery/1.11.3/jquery-1.11.3.min.js?v=${version}"></script>
		<script type="text/javascript" src="./static/library/layer/layer.js?v=${version}"></script>
		<script type="text/javascript" src="./static/js/comm/unfishing.js?v=${version}"></script>
		<script type="text/javascript">var showCaptcha = '${viewDeftData.showCaptcha}';</script>
		<script type="text/javascript" src="./static/js/webui/console/login.js?v=${version}"></script>
	</head>
	<body>
		<div class="superloginB">
			<div class="loginMain">
				<div class="tabwrap">
					<form action="./loginAction!uniLogin?v=${random}" method="post" onsubmit="return beforeLogin()">
						<table class="_diy_table">
							<tr>
								<td class="title">用户名：</td>
								<td>
									<input type="text" name="userInfo.username" id="username" class="form-control txt" autoComplete="off" placeholder="请输入账号"/>
								</td>
							</tr>
							<tr>
								<td class="title">密　码：</td>
								<td>
									<input type="password" name="userInfo.password" id="pwd" class="form-control txt" autoComplete="off" placeholder="请输入密码"/>
								</td>
							</tr>
							<tr>
								<td class="title">验证码：</td>
								<td>
									<input type="text" name="object" id="verifiedCodeVal" class="form-control txt txt2" autoComplete="off" placeholder="请输入验证码"/>
									<span class="yzm">
										<img id="verifiedCode" alt="验证码" title="看不清？单击刷新"/>
									</span>
								</td>
							</tr>
							<tr class="errortd">
								<td>&nbsp;</td>
								<td>
									<c:if test="${loginError != null}">
										<i class="ico-error"></i>
										<span class="errorword">${loginError}</span>
									</c:if>
								</td>
							</tr>
							<tr>
								<td colspan="2" class="formbtn">
									<input type="submit" class="loginbtn" value="登录"/>
									<input type="reset" class="resetbtn" value="重置"/>
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
		<div class="footer">${viewDeftData.copyright}</div>
	</body>
</html>
