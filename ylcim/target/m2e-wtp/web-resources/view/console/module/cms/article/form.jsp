<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div style="overflow: hidden;">
	<div class="phenix-win-head"></div>
	<form method="post">
		<input type="hidden" name="articleInfo.id">
		<input type="hidden" name="articleInfo.columnId">
		<table class="phenix-table">
			<colgroup>
				<col width="15%">
				<col width="35%">
				<col width="15%">
				<col width="35%">
			</colgroup>
			<tr>
				<th>文章名称：</th>
				<td colspan="3">
					<s:if test="viewType!='readOnly'">
						<input type="text" name="articleInfo.name" class="easyui-validatebox phenix-input" 
							data-options="required:true, validType:'length[1,100]'" style="width: 99.5% !important;">
					</s:if>
					<s:else>
						<input type="text" name="articleInfo.name" class="phenix-no-input" style="width: 99.5% !important;" disabled>
					</s:else>
				</td>
			</tr>
			<tr>
				<th>文章来源：</th>
				<td>
					<s:if test="viewType!='readOnly'">
						<input type="text" name="articleInfo.articleFrom" class="easyui-validatebox phenix-input" 
							data-options="required:true, validType:'length[1,100]'">
					</s:if>
					<s:else>
						<input type="text" name="articleInfo.articleFrom" class="phenix-no-input" disabled>
					</s:else>
				</td>
				<th>文章类型：</th>
				<td>
					<s:if test="viewType!='readOnly'">
						<select class="easyui-combobox phenix-input" name="articleInfo.articleType" style="width:180px;">   
						    <option value="原创">原创</option>   
						    <option value="转载">转载</option>   
						</select>
					</s:if>
					<s:else>
						<input type="text" name="articleInfo.articleType" class="phenix-no-input" disabled>
					</s:else>
				</td>
			</tr>
			<tr>
				<th>发布者：</th>
				<td>
					<s:if test="viewType!='readOnly'">
						<shiro:authenticated>
							<input type="text" name="articleInfo.author" class="easyui-validatebox phenix-input" 
								data-options="required:true, validType:'length[1,100]'" 
								value="<shiro:principal property="realName"/>">
							<input type="hidden" name="articleInfo.authorId" 
								value="<shiro:principal property="userId" />">
						</shiro:authenticated>
					</s:if>
					<s:else>
						<input type="text" name="articleInfo.author" class="phenix-no-input" disabled>
					</s:else>
				</td>
				<th>发布时间：</th>
				<td>
					<s:if test="viewType!='readOnly'">
						<input type="text" name="articleInfo.deliveryDate" class="phenix-input" 
							style="width: 180px;" />
					</s:if>
					<s:else>
						<input type="text" name="articleInfo.deliveryDate" class="phenix-no-input" disabled>
					</s:else>
				</td>
			</tr>
			<tr id="articleSearchIndex">
				<th>检索关键字（索引）：</th>
				<td colspan="3">
					<s:if test="viewType!='readOnly'">
						<input type="text" name="articleInfo.searchIndex" class="phenix-input" 
							style="width: 304px !important;" placeholder="关键字，可输入多个" readonly>
						<a id="addIndex" href="javascript: void(0);"
							style="line-height: 12px;margin-top: 1px;display: inline-block;cursor: pointer;">
							<i class="icon czs-add"></i>
							<label style="cursor: pointer;">添加</label>
						</a>
					</s:if>
					<s:else>
						<input type="text" name="articleInfo.searchIndex" class="phenix-no-input" disabled>
					</s:else>
				</td>
			</tr>
			<tr>
				<th>内容摘要：</th>
				<td colspan="3">
					<s:if test="viewType!='readOnly'">
						<textarea name="articleInfo.summary" class="phenix-textarea" style="width: 99.5% !important;"></textarea>
					</s:if>
					<s:else>
						<textarea name="articleInfo.summary" class="phenix-no-textarea" style="width: 99.5% !important;" disabled></textarea>
					</s:else>
				</td>
			</tr>
			<tr>
				<th>文章内容：</th>
				<td colspan="3">
					<s:if test="viewType!='readOnly'">
						<textarea name="articleInfo.content" class="phenix-textarea" style="height: 300px; width: 99.5%;"></textarea>
					</s:if>
					<s:else>
						<textarea name="articleInfo.content" class="phenix-no-textarea" style="height: 300px; width: 99.5%;" disabled></textarea>
					</s:else>
				</td>
			</tr>
		</table>
	</form>
</div>