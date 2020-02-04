<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div style="overflow: hidden;">
	<div class="phenix-win-head"></div>
	<form method="post">
		<input type="hidden" name="constant.id">
		<table class="phenix-table">
			<tr>
				<th>类型Key：</th>
				<td>
					<s:if test="viewType!='readOnly'">
						<input type="text" name="constant.scKey" class="easyui-validatebox phenix-input" 
							data-options="required:true, validType:'length[1,100]'">
					</s:if>
					<s:else>
						<input type="text" name="constant.scKey" class="phenix-no-input" disabled>
					</s:else>
				</td>
			</tr>
			<tr>
				<th>类型Value：</th>
				<td>
					<s:if test="viewType!='readOnly'">
						<input type="text" name="constant.scVal" class="easyui-validatebox phenix-input" 
							data-options="required:true, validType:'length[1,512]'">
					</s:if>
					<s:else>
						<input type="text" name="constant.scVal" class="phenix-no-input" disabled>
					</s:else>
				</td>
			</tr>
			<tr>
				<th>排序号：</th>
				<td>
					<s:if test="viewType!='readOnly'">
						<input type="text" name="constant.sort" class="easyui-validatebox easyui-numberspinner" 
							style="width: 150px;" data-options="required:true">
					</s:if>
					<s:else>
						<input type="text" name="constant.sort" class="phenix-no-input" disabled>
					</s:else>
					
				</td>
			</tr>
			<tr>
				<th>备注：</th>
				<td>
					<s:if test="viewType!='readOnly'">
						<textarea name="constant.remark" class="phenix-textarea"></textarea>
					</s:if>
					<s:else>
						<textarea name="constant.remark" class="phenix-no-textarea" disabled></textarea>
					</s:else>
				</td>
			</tr>
		</table>
	</form>
</div>