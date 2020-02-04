<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div style="overflow: hidden;">
	<div class="phenix-win-head"></div>
	<form method="post">
		<input type="hidden" name="industryInfo.id">
		<table class="phenix-table">
			<tr>
				<th>行业名称：</th>
				<td>
					<s:if test="viewType!='readOnly'">
						<input type="text" name="industryInfo.name" class="easyui-validatebox phenix-input" 
							data-options="required:true, validType:'length[1,100]'">
					</s:if>
					<s:else>
						<input type="text" name="industryInfo.name" class="phenix-no-input" disabled>
					</s:else>
				</td>
			</tr>
			<tr>
				<th>行业编码：</th>
				<td>
					<s:if test="viewType!='readOnly'">
						<input type="text" name="industryInfo.code" class="phenix-input">
					</s:if>
					<s:else>
						<input type="text" name="industryInfo.code" class="phenix-no-input" disabled>
					</s:else>
				</td>
			</tr>
			<tr style="display: none;">
				<th>预警阀值：</th>
				<td>
					<input type="text" name="industryInfo.warningThreshold" class="phenix-no-input" disabled>
				</td>
			</tr>
			<tr>
				<th>排序号：</th>
				<td>
					<s:if test="viewType!='readOnly'">
						<input type="text" name="industryInfo.sort" class="easyui-validatebox easyui-numberspinner" 
							style="width: 150px;" data-options="required:true">
					</s:if>
					<s:else>
						<input type="text" name="industryInfo.sort" class="phenix-no-input" disabled>
					</s:else>
					
				</td>
			</tr>
			<tr>
				<th>备注：</th>
				<td>
					<s:if test="viewType!='readOnly'">
						<textarea name="industryInfo.remark" class="phenix-textarea"></textarea>
					</s:if>
					<s:else>
						<textarea name="industryInfo.remark" class="phenix-no-textarea" disabled></textarea>
					</s:else>
				</td>
			</tr>
		</table>
	</form>
</div>