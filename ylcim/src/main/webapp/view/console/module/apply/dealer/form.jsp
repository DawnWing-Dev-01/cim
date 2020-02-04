<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div style="overflow: hidden;">
	<div class="phenix-win-head"></div>
	<form method="post">
		<input type="hidden" name="dealerInfo.id">
		<table class="phenix-table">
			<tr>
				<th>经营者名称：</th>
				<td colspan="3">
					<s:if test="viewType!='readOnly'">
						<input type="text" name="dealerInfo.name" class="easyui-validatebox phenix-input" 
							data-options="required:true, validType:'length[1,100]'">
					</s:if>
					<s:else>
						<input type="text" name="dealerInfo.name" class="phenix-no-input" disabled>
					</s:else>
				</td>
			</tr>
			<tr>
				<th>简称：</th>
				<td colspan="3">
					<s:if test="viewType!='readOnly'">
						<input type="text" name="dealerInfo.simpleName" class="phenix-input" 
							style="width: 325px !important;" placeholder="常用名称，可输入多个" readonly>
						<a id="addsimple" href="javascript: void(0);"
							style="line-height: 12px;margin-top: 1px;display: inline-block;cursor: pointer;">
							<i class="icon czs-add"></i>
							<label style="cursor: pointer;">添加</label>
						</a>
					</s:if>
					<s:else>
						<input type="text" name="dealerInfo.simpleName" class="phenix-no-input" disabled>
					</s:else>
				</td>
			</tr>
			<tr>
				<th>法人姓名：</th>
				<td>
					<s:if test="viewType!='readOnly'">
						<input type="text" name="dealerInfo.legalPerson" class="easyui-validatebox phenix-input" 
							data-options="required:true, validType:'length[0,50]'">
					</s:if>
					<s:else>
						<input type="text" name="dealerInfo.legalPerson" class="phenix-no-input" disabled>
					</s:else>
				</td>
				<th>营业执照：</th>
				<td>
					<s:if test="viewType!='readOnly'">
						<input type="text" name="dealerInfo.licenseNo" class="easyui-validatebox phenix-input" 
							data-options="required:true, validType:'length[0,100]'">
					</s:if>
					<s:else>
						<input type="text" name="dealerInfo.licenseNo" class="phenix-no-input" disabled>
					</s:else>
				</td>
			</tr>
			<tr>
				<th>所属行业：</th>
				<td>
					<s:if test="viewType!='readOnly'">
						<input type="text" name="dealerInfo.industryId" style="width: 150px;">
					</s:if>
					<s:else>
						<input type="text" name="dealerInfo.industryName" class="phenix-no-input" disabled>
					</s:else>
				</td>
				<th>联系电话：</th>
				<td>
					<s:if test="viewType!='readOnly'">
						<input type="text" name="dealerInfo.linkTel" 
							class="easyui-validatebox phenix-input" 
							data-options="required:true, validType:['telNum', 'length[1,12]']">
					</s:if>
					<s:else>
						<input type="text" name="dealerInfo.linkTel" class="phenix-no-input" disabled>
					</s:else>
				</td>
				<%-- <th>经营类型：</th>
				<td>
					<s:if test="viewType!='readOnly'">
						<input type="text" name="dealerInfo.dealerTypeId" style="width: 150px;">
					</s:if>
					<s:else>
						<input type="text" name="dealerInfo.dealerTypeName" class="phenix-no-input" disabled>
					</s:else>
				</td> --%>
			</tr>
			<tr>
				<th>管辖单位：</th>
				<td>
					<s:if test="viewType!='readOnly'">
						<input type="text" name="dealerInfo.jurisdiction" style="width: 150px;">
					</s:if>
					<s:else>
						<input type="text" name="dealerInfo.jurisdictionName" class="phenix-no-input" disabled>
					</s:else>
				</td>
				<th>排序号：</th>
				<td>
					<s:if test="viewType!='readOnly'">
						<input type="text" name="dealerInfo.sort" class="easyui-numberspinner" 
							style="width: 150px;">
					</s:if>
					<s:else>
						<input type="text" name="dealerInfo.sort" class="phenix-no-input" disabled>
					</s:else>
				</td>
			</tr>
			<tr>
				<th>注册地址：</th>
				<td colspan="3">
					<s:if test="viewType!='readOnly'">
						<input type="text" name="dealerInfo.registerAddress" class="easyui-validatebox phenix-input" 
							data-options="required:true, validType:'length[0,512]'">
					</s:if>
					<s:else>
						<input type="text" name="dealerInfo.registerAddress" class="phenix-no-input" disabled>
					</s:else>
				</td>
			</tr>
			<tr>
				<th>经营地址：</th>
				<td colspan="3">
					<s:if test="viewType!='readOnly'">
						<input type="text" name="dealerInfo.dealerAddress" class="phenix-input">
					</s:if>
					<s:else>
						<input type="text" name="dealerInfo.dealerAddress" class="phenix-no-input" disabled>
					</s:else>
				</td>
			</tr>
			<tr>
				<th>主营项目：</th>
				<td colspan="3">
					<s:if test="viewType!='readOnly'">
						<textarea name="dealerInfo.mainProject" class="easyui-validatebox phenix-textarea" 
							data-options="required:true, validType:'length[0,512]'" style="width: 99% !important;">
						</textarea>
					</s:if>
					<s:else>
						<textarea name="dealerInfo.mainProject" class="phenix-no-textarea" disabled></textarea>
					</s:else>
				</td>
			</tr>
			<%-- <tr>
				<th>备注：</th>
				<td colspan="3">
					<s:if test="viewType!='readOnly'">
						<textarea name="dealerInfo.remark" class="phenix-textarea" style="width: 99% !important;"></textarea>
					</s:if>
					<s:else>
						<textarea name="dealerInfo.remark" class="phenix-no-textarea" disabled></textarea>
					</s:else>
				</td>
			</tr> --%>
		</table>
	</form>
</div>