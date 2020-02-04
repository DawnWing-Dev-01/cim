<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div style="overflow: hidden;">
	<div class="phenix-win-head"></div>
	<form method="post">
		<input type="hidden" name="roleInfo.id">
		<table class="phenix-table">
			<tr>
				<th>角色名称：</th>
				<td>
					<input type="text" name="roleInfo.name" class="easyui-validatebox phenix-input" data-options="required:true,validType:'length[1,30]'">
				</td>
			</tr>
			<tr>
				<th>角色编码：</th>
				<td>
					<input type="text" name="roleInfo.code" class="easyui-validatebox phenix-input" data-options="validType:'length[0,50]'">
				</td>
			</tr>
			<tr>
				<th>排序号：</th>
				<td>
					<input type="text" name="roleInfo.sort" class="easyui-validatebox easyui-numberspinner" style="width: 150px;" data-options="required:true">
				</td>
			</tr>
			<tr>
				<th>备注：</th>
				<td>
					<textarea name="roleInfo.remark" class="phenix-textarea"></textarea>
				</td>
			</tr>
		</table>
	</form>
</div>