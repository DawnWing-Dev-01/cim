<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div style="overflow: hidden;">
	<div class="phenix-win-head"></div>
	<form method="post">
		<input type="hidden" name="userInfo.id">
		<table class="phenix-table">
			<tr id="orgInfo">
				<th>所属组织：</th>
				<td colspan="3">
					<s:if test="viewType!='readOnly'">
						<input type="text" name="userInfo.orgId" style="width: 389px;">
					</s:if>
					<s:else>
						<input type="text" name="userInfo.orgId" class="phenix-no-input" style="width: 389px;" disabled>
					</s:else>
				</td>
			</tr>
			<tr>
				<th>用户姓名：</th>
				<td colspan="3">
					<s:if test="viewType!='readOnly'">
						<input type="text" name="userInfo.name" class="easyui-validatebox phenix-input" 
							data-options="required:true,validType:'length[1,30]'"/>
					</s:if>
					<s:else>
						<input type="text" name="userInfo.name" class="phenix-no-input" disabled>
					</s:else>
				</td>
			</tr>
			<tr>
				<th>登录账号：</th>
				<td>
					<s:if test="viewType!='readOnly'">
						<input type="text" name="userInfo.username" class="easyui-validatebox phenix-input" 
							data-options="required:true,validType:'length[1,30]'"/>
					</s:if>
					<s:else>
						<input type="text" name="userInfo.username" class="phenix-no-input" disabled>
					</s:else>
				</td>
				<th>生日：</th>
				<td>
					<s:if test="viewType!='readOnly'">
						<input type="text" name="userInfo.birthday" class="easyui-datebox phenix-input" 
							style="width: 126px;" />
					</s:if>
					<s:else>
						<input type="text" name="userInfo.birthday" class="phenix-no-input" disabled>
					</s:else>
				</td>
			</tr>
			<tr>
				<th>电话：</th>
				<td>
					<s:if test="viewType!='readOnly'">
						<input type="text" name="userInfo.iphone" class="phenix-input"/>
					</s:if>
					<s:else>
						<input type="text" name="userInfo.iphone" class="phenix-no-input" disabled>
					</s:else>
				</td>
				<th>手机：</th>
				<td>
					<s:if test="viewType!='readOnly'">
						<input type="text" name="userInfo.mobile" class="phenix-input"/>
					</s:if>
					<s:else>
						<input type="text" name="userInfo.mobile" class="phenix-no-input" disabled>
					</s:else>
				</td>
			</tr>
			<tr>
				<th>排序号：</th>
				<td>
					<s:if test="viewType!='readOnly'">
						<input type="text" name="userInfo.sort" class="easyui-validatebox easyui-numberspinner" 
							style="width: 127px;" data-options="required:true">
					</s:if>
					<s:else>
						<input type="text" name="userInfo.sort" class="phenix-no-input" disabled>
					</s:else>
				</td>
				<th>性别：</th>
				<td>
					<s:if test="viewType!='readOnly'">
						<input name="userInfo.gender" id="GENDER_MALE" type="radio" style="width: 15px;" 
							value="${viewDeftData.genderMale}" checked/>
						<label for="GENDER_MALE">男</label>
						<input name="userInfo.gender" id="GENDER_FEMALE" type="radio" style="width: 15px;" 
							value="${viewDeftData.genderFemale}"/>
						<label for="GENDER_FEMALE">女</label>
					</s:if>
					<s:else>
						<input name="userInfo.gender" id="GENDER_MALE" type="radio" style="width: 15px;" 
							value="${viewDeftData.genderMale}" disabled/>
						<label for="GENDER_MALE">男</label>
						<input name="userInfo.gender" id="GENDER_FEMALE" type="radio" style="width: 15px;" 
							value="${viewDeftData.genderFemale}" disabled/>
						<label for="GENDER_FEMALE">女</label>
					</s:else>
				</td>
			</tr>
			<tr>
				<th>微信账号：</th>
				<td colspan="3">
					<s:if test="viewType!='readOnly'">
						<input type="text" name="userInfo.wechatOpenId" class="phenix-input" 
							style="width: 245px !important;" readonly>
						<a id="chooseOpenId" href="javascript: void(0);"
								style="line-height: 12px;margin-top: 1px;display: inline-block;cursor: pointer;">
							<i class="icon czs-hande-vertical"></i>
							<label style="cursor: pointer;">选择</label>
						</a>
					</s:if>
					<s:else>
						<input type="text" name="userInfo.wechatOpenId" class="phenix-no-input" disabled>
					</s:else>
				</td>
			</tr>
			<tr>
				<th>家庭住址：</th>
				<td colspan="3">
					<s:if test="viewType!='readOnly'">
						<input type="text" name="userInfo.address" class="phenix-input"/>
					</s:if>
					<s:else>
						<input type="text" name="userInfo.address" class="phenix-no-input" disabled>
					</s:else>
				</td>
			</tr>
			<tr>
				<th>备注：</th>
				<td colspan="3">
					<s:if test="viewType!='readOnly'">
						<textarea name="userInfo.remark" class="phenix-textarea"></textarea>
					</s:if>
					<s:else>
						<textarea name="userInfo.remark" class="phenix-no-textarea" disabled></textarea>
					</s:else>
				</td>
			</tr>
		</table>
	</form>
</div>