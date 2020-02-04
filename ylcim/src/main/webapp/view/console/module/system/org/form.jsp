<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div style="overflow: hidden;">
	<div class="phenix-win-head"></div>
	<form method="post">
		<input type="hidden" name="orgInfo.id">
		<input type="hidden" name="orgInfo.fatherId">
		<table class="phenix-table">
			<tr style="display: none;">
				<th>归属机构：</th>
				<td colspan="3">
					<input type="text" id="fatherName" class="phenix-no-input" disabled>
				</td>
			</tr>
			<tr>
				<th>机构名称：</th>
				<td colspan="3">
					<input type="text" name="orgInfo.name" class="easyui-validatebox phenix-input" 
						data-options="required:true,validType:'length[1,30]'">
				</td>
			</tr>
			<tr>
				<th>机构编码：</th>
				<td colspan="3">
					<input type="text" name="orgInfo.code" class="easyui-validatebox phenix-input" 
						data-options="validType:'length[0,50]'">
				</td>
			</tr>
			<tr>
				<th>排序号：</th>
				<td>
					<input type="text" name="orgInfo.sort" class="easyui-validatebox easyui-numberspinner" 
						style="width: 93px;" data-options="required:true">
				</td>
				<th>图标：</th>
				<td style="text-align: center;">
					<i class="easyui-tooltip item-icon czs-control-rank" data-old="czs-control-rank" title="更换图标"></i>
					<input type="hidden" name="orgInfo.icon" value="czs-control-rank"/>
				</td>
			</tr>
			<tr>
				<th>备注：</th>
				<td colspan="3">
					<textarea name="orgInfo.remark" class="phenix-textarea"></textarea>
				</td>
			</tr>
		</table>
	</form>
</div>