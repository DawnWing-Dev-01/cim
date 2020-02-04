<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div style="overflow: hidden;">
	<div class="phenix-win-head"></div>
	<form method="post">
		<input type="hidden" name="workFlowInfo.id">
		<table class="phenix-table">
			<colgroup>
				<col width="15%">
				<col width="35%">
			</colgroup>
			<tr>
				<th>流程节点：</th>
				<td>
					<s:if test="viewType!='readOnly'">
						<input type="text" name="workFlowInfo.flowNodeCode" class="easyui-validatebox phenix-input" 
							data-options="required:true, validType:'length[1,20]'">
					</s:if>
					<s:else>
						<input type="text" name="workFlowInfo.flowNodeCode" class="phenix-no-input" disabled>
					</s:else>
				</td>
			</tr>
			<tr>
				<th>节点名称：</th>
				<td>
					<s:if test="viewType!='readOnly'">
						<input type="text" name="workFlowInfo.flowNodeText" class="phenix-input">
					</s:if>
					<s:else>
						<input type="text" name="workFlowInfo.flowNodeText" class="phenix-no-input" disabled>
					</s:else>
				</td>
			</tr>
			<tr>
				<th>NextNode处理方式：</th>
				<td>
					<input name="workFlowInfo.handleMode" id="role_handle" type="radio" 
						style="width: 15px;" value="${viewDeftData.roleHandle}"
						<s:if test="viewType=='readOnly'">disabled</s:if>/>
					<label for="role_handle">对应角色</label>
					<input name="workFlowInfo.handleMode" id="user_handle" type="radio"
						style="width: 15px;" value="${viewDeftData.userHandle}" 
						<s:if test="viewType=='readOnly'">disabled</s:if>/>
					<label for="user_handle">单一用户</label>
					<input name="workFlowInfo.handleMode" id="not_handle" type="radio"
						style="width: 15px;" value="${viewDeftData.notHandle}" 
						<s:if test="viewType=='readOnly'">disabled</s:if> checked/>
					<label for="not_handle">不处理</label>
				</td>
			</tr>
			<tr>
				<th>NextNode处理主体：</th>
				<td>
					<s:if test="viewType!='readOnly'">
						<input type="text" name="workFlowInfo.handleSubject" class="easyui-validatebox phenix-input" 
							data-options="required:true" style="width: 245px !important;" readonly>
						<input type="hidden" name="workFlowInfo.subjectId">
						<a id="selectSubject" href="javascript: void(0);"
								style="line-height: 12px;margin-top: 1px;display: inline-block;cursor: pointer;">
							<i class="icon czs-hande-vertical"></i>
							<label style="cursor: pointer;">选择</label>
						</a>
					</s:if>
					<s:else>
						<input type="text" name="workFlowInfo.handleSubject" class="phenix-no-input" disabled>
					</s:else>
				</td>
			</tr>
			<tr>
				<th>排序号：</th>
				<td>
					<s:if test="viewType!='readOnly'">
						<input type="text" name="workFlowInfo.sort" class="easyui-validatebox easyui-numberspinner" 
							style="width: 150px;" data-options="required:true">
					</s:if>
					<s:else>
						<input type="text" name="workFlowInfo.sort" class="phenix-no-input" disabled>
					</s:else>
					
				</td>
			</tr>
			<tr>
				<th>备注：</th>
				<td>
					<s:if test="viewType!='readOnly'">
						<textarea name="workFlowInfo.remark" class="phenix-textarea"></textarea>
					</s:if>
					<s:else>
						<textarea name="workFlowInfo.remark" class="phenix-no-textarea" disabled></textarea>
					</s:else>
				</td>
			</tr>
		</table>
	</form>
</div>