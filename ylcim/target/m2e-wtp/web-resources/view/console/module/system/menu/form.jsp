<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div style="overflow: hidden;">
	<div class="phenix-win-head"></div>
	<form method="post">
		<input type="hidden" name="menuInfo.id">
		<input type="hidden" name="menuInfo.fatherId">
		<table class="phenix-table">
			<tr style="display: none;">
				<th>父菜单：</th>
				<td colspan="3">
					<input type="text" id="fatherName" class="phenix-no-input" disabled>
				</td>
			</tr>
			<tr>
				<th>菜单名称：</th>
				<td colspan="3">
					<input type="text" name="menuInfo.name" class="easyui-validatebox phenix-input" 
						data-options="required:true,validType:'length[1,30]', tipPosition:'top'">
				</td>
			</tr>
			<tr>
				<th>链接地址：</th>
				<td colspan="3">
					<input type="text" name="menuInfo.action" class="easyui-validatebox phenix-input" 
						data-options="validType:'length[0,100]'">
				</td>
			</tr>
			<tr>
				<th>类型：</th>
				<td colspan="3">
					<input name="menuInfo.type" id="MENU_NAV" type="radio" style="width: 15px;" 
						value="${viewDeftData.navMenu}" checked>
					<label for="MENU_NAV">导航栏目</label>
					<input name="menuInfo.type" id="MENU_OPT" type="radio" style="width: 15px;" 
						value="${viewDeftData.optMenu}">
					<label for="MENU_OPT">操作菜单</label>
				</td>
			</tr>
			<tr>
				<th>排序号：</th>
				<td>
					<input type="text" name="menuInfo.sort" class="easyui-validatebox easyui-numberspinner" 
						style="width: 93px;" data-options="required:true">
				</td>
				<th>图标：</th>
				<td style="text-align: center;">
					<i class="easyui-tooltip item-icon czs-tag-l" data-old="czs-tag-l" title="更换图标"></i>
					<input type="hidden" name="menuInfo.icon" value="czs-tag-l"/>
				</td>
			</tr>
			<tr>
				<th>备注：</th>
				<td colspan="3">
					<textarea name="menuInfo.remark" class="phenix-textarea"></textarea>
				</td>
			</tr>
		</table>
	</form>
</div>