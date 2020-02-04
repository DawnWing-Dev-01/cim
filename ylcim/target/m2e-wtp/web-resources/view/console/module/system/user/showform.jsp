<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="easyui-tabs" data-options="fit:true,border:false,pill:true">
	<div title="基本信息" data-options="iconCls:'czs-right-clipboard-l'" style="display:none;">
		<div style="overflow: hidden;">
			<div class="phenix-win-head"></div>
			<form method="post">
				<input type="hidden" name="userInfo.id">
				<input type="hidden" name="userInfo.orgId">
				<table class="phenix-table">
					<tr>
						<th>用户姓名：</th>
						<td colspan="3">
							<input type="text" name="userInfo.name" class="easyui-validatebox phenix-input" 
									data-options="required:true,validType:'length[1,30]'"/>
						</td>
					</tr>
					<tr>
						<th>登录账号：</th>
						<td>
							<input type="text" name="userInfo.username" class="easyui-validatebox phenix-input" 
									data-options="required:true,validType:'length[1,30]'"/>
						</td>
						<th>生日：</th>
						<td>
							<input type="text" name="userInfo.birthday" class="easyui-datebox phenix-input" 
									style="width: 126px;" />
						</td>
					</tr>
					<tr>
						<th>电话：</th>
						<td>
							<input type="text" name="userInfo.iphone" class="phenix-input"/>
						</td>
						<th>手机：</th>
						<td>
							<input type="text" name="userInfo.mobile" class="phenix-input"/>
						</td>
					</tr>
					<tr>
						<th>排序号：</th>
						<td>
							<input type="text" name="userInfo.sort" class="easyui-validatebox easyui-numberspinner" 
									style="width: 127px;" data-options="required:true">
						</td>
						<th>性别：</th>
						<td>
							<input name="userInfo.gender" id="GENDER_MALE" type="radio" style="width: 15px;" 
								value="${viewDeftData.genderMale}" checked/>
							<label for="GENDER_MALE">男</label>
							<input name="userInfo.gender" id="GENDER_FEMALE" type="radio" style="width: 15px;" 
								value="${viewDeftData.genderFemale}"/>
							<label for="GENDER_FEMALE">女</label>
						</td>
					</tr>
					<tr>
						<th>微信账号：</th>
						<td colspan="3">
							<s:if test="viewType!='readOnly'">
								<input type="text" name="userInfo.wechatOpenId" class="easyui-validatebox phenix-input" 
									data-options="required:true" style="width: 245px !important;" readonly>
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
							<input type="text" name="userInfo.address" class="phenix-input"/>
						</td>
					</tr>
					<tr>
						<th>备注：</th>
						<td colspan="3">
							<textarea name="userInfo.remark" class="phenix-textarea"></textarea>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div title="修改密码" data-options="iconCls:'icon-quanxian2'" style="overflow:auto;display:none;">
		<div style="overflow: hidden;">
			<div class="phenix-win-head"></div>
			<form id="updatePwd" method="post">
				<input type="hidden" name="userInfo.id">
				<table class="phenix-table" style="width: 80%;">
					<tr>
						<th>原密码：</th>
						<td>
							<input type="password" name="userInfo.password" class="easyui-validatebox phenix-input" 
									data-options="required:true,validType:'length[1,30]'"/>
						</td>
					</tr>
					<tr>
						<th>新密码：</th>
						<td>
							<input type="password" id="object" name="object" class="easyui-validatebox phenix-input" 
									data-options="required:true,validType:'length[1,30]'"/>
						</td>
					</tr>
					<tr>
						<th>确认密码：</th>
						<td>
							<input type="password" id="verifyPwd" name="verifyPwd" class="easyui-validatebox phenix-input" 
								required="required" validType="equals['#object']" />
						</td>
					</tr>
					<tfoot>
						<tr>
							<td colspan="2">
								<div class="updatePwdBtn">
									<a id="submit" href="javascript: void(0);" class="easyui-linkbutton" 
										data-options="iconCls:'icon-complete', plain:true">提交</a>
								</div>
							</td>
						</tr>
					</tfoot>
				</table>
			</form>
		</div>
	</div>
</div>