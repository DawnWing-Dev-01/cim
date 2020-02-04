<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix ="s" uri="/struts-tags"%>
<div class="tas-header">
	<div class="logo">
		<%-- <label>杨凌示范区工商行政管理局</label>
		<small>经营者信用公示平台</small> --%>
	</div>
	<div class="funArea">
		<div class="operating">
			<a class="opt-item" href="javascript:void(0);" title="换肤" onclick="changeThemes();">
				<i class="iconfont icon-theme"></i>
			</a>
			<shiro:authenticated>
				<a class="opt-item" href="javascript:void(0);" title="用户信息" onclick="showUserInfo();">
					<i class="iconfont icon-yonghuziliao"></i>
				</a>
			</shiro:authenticated>
			<a class="opt-item logout" href="javascript:void(0);" title="注销登录" onclick="logOut();">
				<i class="iconfont icon-tuichu1"></i>
			</a>
		</div>
    	<shiro:authenticated>  
			<div class="welcome">
				<label>欢迎您，【&nbsp;<shiro:principal property="realName"/>&nbsp;】</label>
				<span id="header-date"></span>
				<script type="text/javascript">
					var onlineId = '<shiro:principal property="userId" />';
				</script>
			</div>
		</shiro:authenticated>
	</div>
</div>