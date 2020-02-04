<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div style="overflow: hidden;">
	<div class="phenix-win-head"></div>
	<form method="post" enctype="multipart/form-data">
		<table class="phenix-table">
			<tr>
				<th>登记日期：</th>
				<td>
					<input type="text" name="startDate" class= "easyui-datebox" 
						style="width: 178px;" placeholder="开始时间"/>
					&nbsp;至&nbsp;
					<input type="text" name="endDate" class= "easyui-datebox" 
						style="width: 178px;" placeholder="结束时间"/>
				</td>
			</tr>
		</table>
	</form>
</div>