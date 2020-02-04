<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div style="overflow: hidden;">
	<div class="phenix-win-head"></div>
	<form method="post">
		<input type="hidden" name="warningInfo.id">
		<input type="hidden" name="warningInfo.industryId">
		<table class="phenix-table">
			<colgroup>
				<col width="15%">
				<col width="35%">
				<col width="15%">
				<col width="35%">
			</colgroup>
			<tr>
				<th>预警标题：</th>
				<td colspan="3">
					<s:if test="viewType!='readOnly'">
						<input type="text" name="warningInfo.ewTitle" class="easyui-validatebox phenix-input" 
							data-options="required:true, validType:'length[1,100]'">
					</s:if>
					<s:else>
						<input type="text" name="warningInfo.ewTitle" class="phenix-no-input" disabled>
					</s:else>
				</td>
			</tr>
			<tr>
				<th>发布单位：</th>
				<td>
					<s:if test="viewType!='readOnly'">
						<input type="text" name="warningInfo.ewAuthor" class="phenix-input" 
							value="${viewDeftData.ewAuthor}">
					</s:if>
					<s:else>
						<input type="text" name="warningInfo.ewAuthor" class="phenix-no-input" disabled>
					</s:else>
				</td>
				<th>预警时间：</th>
				<td>
					<s:if test="viewType!='readOnly'">
						<input type="text" name="warningInfo.ewDate" class="phenix-input" style="width: 180px;">
					</s:if>
					<s:else>
						<input type="text" name="warningInfo.ewDate" class="phenix-no-input" disabled>
					</s:else>
				</td>
			</tr>
			<tr>
				<th>开始显示：</th>
				<td>
					<s:if test="viewType!='readOnly'">
						<input type="text" name="warningInfo.starShowDate" class="easyui-datebox phenix-input"
							data-options="required:true" style="width: 180px;">
					</s:if>
					<s:else>
						<input type="text" name="warningInfo.starShowDate" class="phenix-no-input" disabled>
					</s:else>
				</td>
				<th>结束显示：</th>
				<td>
					<s:if test="viewType!='readOnly'">
						<input type="text" name="warningInfo.endShowDate" class="easyui-datebox phenix-input" 
							data-options="required:true" style="width: 180px;">
					</s:if>
					<s:else>
						<input type="text" name="warningInfo.endShowDate" class="phenix-no-input" disabled>
					</s:else>
				</td>
			</tr>
			<%-- <tr>
				<th>备注：</th>
				<td colspan="3">
					<s:if test="viewType!='readOnly'">
						<textarea name="warningInfo.remark" class="phenix-textarea"></textarea>
					</s:if>
					<s:else>
						<textarea name="warningInfo.remark" class="phenix-no-textarea" disabled></textarea>
					</s:else>
				</td>
			</tr> --%>
			<tr>
				<th>预警内容：</th>
				<td colspan="3">
					<s:if test="viewType!='readOnly'">
						<textarea name="warningInfo.ewContent" class="phenix-textarea" 
							style="height: 300px; width: 99.5%;"></textarea>
					</s:if>
					<s:else>
						<textarea name="warningInfo.ewContent" class="phenix-no-textarea" 
							style="height: 300px; width: 99.5%;" disabled></textarea>
					</s:else>
				</td>
			</tr>
		</table>
	</form>
</div>