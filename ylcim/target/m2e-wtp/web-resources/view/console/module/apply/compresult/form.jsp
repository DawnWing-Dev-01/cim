<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div style="overflow: hidden;">
	<div class="phenix-win-head"></div>
	<form method="post">
		<input type="hidden" name="complaintResult.id">
		<input type="hidden" name="complaintResult.originTypeId">
		<fieldset>
			<legend>基本信息</legend>
			<table class="phenix-table">
				<colgroup>
					<col width="25%">
					<col width="25%">
					<col width="25%">
					<col width="25%">
				</colgroup>
				<tr>
					<th>登记编号：</th>
					<td>
						<s:if test="viewType!='readOnly'">
							<input type="text" name="complaintResult.enterCode" class="easyui-validatebox phenix-input"
										data-options="required:true, validType:'length[1,50]'">
						</s:if>
						<s:else>
							<input type="text" name="complaintResult.enterCode" class="phenix-no-input" disabled>
						</s:else>
					</td>
					<th>登记部门：</th>
					<td>
						<s:if test="viewType!='readOnly'">
							<input type="text" name="complaintResult.enterDept" class="easyui-validatebox phenix-input"
										data-options="required:true, validType:'length[1,50]'">
						</s:if>
						<s:else>
							<input type="text" name="complaintResult.enterDept" class="phenix-no-input" disabled>
						</s:else>
					</td>
				</tr>
				<tr>
					<th>提供方姓名：</th>
					<td>
						<s:if test="viewType!='readOnly'">
							<input type="text" name="complaintResult.providerName" class="easyui-validatebox phenix-input"
										data-options="required:true, validType:'length[1,20]'">
						</s:if>
						<s:else>
							<input type="text" name="complaintResult.providerName" class="phenix-no-input" disabled>
						</s:else>
					</td>
					<th>类型：</th>
					<td>
						<s:if test="viewType!='readOnly'">
							<select class="easyui-combobox" name="complaintResult.complaintType" style="width: 170px;">   
							    <option value="投诉" selected>投诉</option>
							    <option value="举报">举报</option>
							    <option value="咨询">咨询</option>
							</select>
						</s:if>
						<s:else>
							<input type="text" name="complaintResult.complaintType" class="phenix-no-input" disabled>
						</s:else>
					</td>
				</tr>
				<tr>
					<th>办理情况：</th>
					<td>
						<s:if test="viewType!='readOnly'">
							<select class="easyui-combobox" name="complaintResult.handleStatus" style="width: 170px;">   
							    <option value="办结已归档" selected>办结已归档</option>
							    <option value="转案件系统">转案件系统</option>
							    <option value="待办理">待办理</option>
							</select>
						</s:if>
						<s:else>
							<input type="text" name="complaintResult.handleStatus" class="phenix-no-input" disabled>
						</s:else>
					</td>
					<th>处理部门：</th>
					<td>
						<s:if test="viewType!='readOnly'">
							<input type="text" name="complaintResult.handleDept" class="easyui-validatebox phenix-input"
										data-options="required:true, validType:'length[1,50]'">
						</s:if>
						<s:else>
							<input type="text" name="complaintResult.handleDept" class="phenix-no-input" disabled>
						</s:else>
					</td>
				</tr>
				<tr>
					<th>登记日期：</th>
					<td>
						<s:if test="viewType!='readOnly'">
							<input type= "text" name="complaintResult.enterDate" class="easyui-datebox" 
								required="required" style="width: 170px;"/>
						</s:if>
						<s:else>
							<input type="text" name="complaintResult.enterDate" class="phenix-no-input" disabled>
						</s:else>
					</td>
					<th>办理期限：</th>
					<td>
						<s:if test="viewType!='readOnly'">
							<input type= "text" name="complaintResult.handleDeadline" class="easyui-datebox" style="width: 170px;"/>
						</s:if>
						<s:else>
							<input type="text" name="complaintResult.handleDeadline" class="phenix-no-input" disabled>
						</s:else>
					</td>
				</tr>
				<tr>
					<th>投诉内容：</th>
					<td colspan="3">
						<s:if test="viewType!='readOnly'">
							<textarea name="complaintResult.complaints" class="easyui-validatebox phenix-textarea" 
								style="height: 200px; width: 99.5%;" data-options="required:true"></textarea>
						</s:if>
						<s:else>
							<textarea name="complaintResult.complaints" class="phenix-no-textarea" 
								style="height: 200px; width: 99.5%;" disabled></textarea>
						</s:else>
					</td>
				</tr>
				<tr>
					<th>初查反馈时间：</th>
					<td>
						<s:if test="viewType!='readOnly'">
							<input type= "text" name="complaintResult.netCreatedate" class="easyui-datebox" style="width: 170px;"/>
						</s:if>
						<s:else>
							<input type="text" name="complaintResult.netCreatedate" class="phenix-no-input" disabled>
						</s:else>
					</td>
					<th>创建人：</th>
					<td>
						<s:if test="viewType!='readOnly'">
							<shiro:authenticated>  
								<input type="text" name="complaintResult.creator" class="phenix-no-input" 
									value="<shiro:principal property="realName"/>" disabled>
							</shiro:authenticated>
						</s:if>
						<s:else>
							<input type="text" name="complaintResult.creator" class="phenix-no-input" disabled>
						</s:else>
					</td>
				</tr>
				<tr>
					<th>详细情况说明：</th>
					<td colspan="3">
						<s:if test="viewType!='readOnly'">
							<textarea name="complaintResult.netRemark" class="phenix-textarea"></textarea>
						</s:if>
						<s:else>
							<textarea name="complaintResult.netRemark" class="phenix-no-textarea" disabled></textarea>
						</s:else>
					</td>
				</tr>
				<tr>
					<th>不受理原因：</th>
					<td colspan="3">
						<s:if test="viewType!='readOnly'">
							<textarea name="complaintResult.netResult" class="phenix-textarea"></textarea>
						</s:if>
						<s:else>
							<textarea name="complaintResult.netResult" class="phenix-no-textarea" disabled></textarea>
						</s:else>
					</td>
				</tr>
			</table>
			<div class="beizhu">
				<p>注：投诉内容应当包括：消费者接受商品或服务的名称、消费日期、消费涉及金额等具体情况。必填字段</p>
			</div>
		</fieldset>
		<fieldset>
			<legend>办结情况</legend>
			<table class="phenix-table">
				<colgroup>
					<col width="25%">
					<col width="25%">
					<col width="25%">
					<col width="25%">
				</colgroup>
				<tr>
					<th>办结日期：</th>
					<td>
						<s:if test="viewType!='readOnly'">
							<input type= "text" name="complaintResult.finishKnotDate" class="easyui-datebox" style="width: 170px;"/>
						</s:if>
						<s:else>
							<input type="text" name="complaintResult.finishKnotDate" class="phenix-no-input" disabled>
						</s:else>
					</td>
					<th>处理人：</th>
					<td>
						<s:if test="viewType!='readOnly'">
							<input type="text" name="complaintResult.handlePeople" class="phenix-input">
						</s:if>
						<s:else>
							<input type="text" name="complaintResult.handlePeople" class="phenix-no-input" disabled>
						</s:else>
					</td>
				</tr>
				<tr>
					<th>办结：</th>
					<td colspan="3">
						<s:if test="viewType!='readOnly'">
							<textarea name="complaintResult.finishKnot" class="phenix-textarea"></textarea>
						</s:if>
						<s:else>
							<textarea name="complaintResult.finishKnot" class="phenix-no-textarea" disabled></textarea>
						</s:else>
					</td>
				</tr>
			</table>
		</fieldset>
		<fieldset>
			<legend>扩展字段</legend>
			<table class="phenix-table">
				<colgroup>
					<col width="25%">
					<col width="25%">
					<col width="25%">
					<col width="25%">
				</colgroup>
				<tr>
					<th>扩展01：</th>
					<td>
						<s:if test="viewType!='readOnly'">
							<input type="text" name="complaintResult.ext01" class="phenix-input">
						</s:if>
						<s:else>
							<input type="text" name="complaintResult.ext01" class="phenix-no-input" disabled>
						</s:else>
					</td>
					<th>扩展02：</th>
					<td>
						<s:if test="viewType!='readOnly'">
							<input type="text" name="complaintResult.ext02" class="phenix-input">
						</s:if>
						<s:else>
							<input type="text" name="complaintResult.ext02" class="phenix-no-input" disabled>
						</s:else>
					</td>
				</tr>
				<tr>
					<th>扩展03：</th>
					<td>
						<s:if test="viewType!='readOnly'">
							<input type="text" name="complaintResult.ext03" class="phenix-input">
						</s:if>
						<s:else>
							<input type="text" name="complaintResult.ext03" class="phenix-no-input" disabled>
						</s:else>
					</td>
					<th>扩展04：</th>
					<td>
						<s:if test="viewType!='readOnly'">
							<input type="text" name="complaintResult.ext04" class="phenix-input">
						</s:if>
						<s:else>
							<input type="text" name="complaintResult.ext04" class="phenix-no-input" disabled>
						</s:else>
					</td>
				</tr>
				<tr>
					<th>扩展05：</th>
					<td colspan="3">
						<s:if test="viewType!='readOnly'">
							<textarea name="complaintResult.ext05" class="phenix-textarea"></textarea>
						</s:if>
						<s:else>
							<textarea name="complaintResult.ext05" class="phenix-no-textarea" disabled></textarea>
						</s:else>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</div>