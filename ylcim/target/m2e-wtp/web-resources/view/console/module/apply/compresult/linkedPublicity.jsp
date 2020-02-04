<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div style="overflow: hidden;">
	<div class="phenix-win-head"></div>
	<form method="post">
		<input type="hidden" name="complaintResult.id">
		<table class="phenix-table">
			<tr>
				<th>是否公示：</th>
				<td>
					<input name="complaintResult.isPublicity" id="isPublicity_TRUE" type="radio" 
						style="width: 15px;" value="1" checked/>
					<label for="isPublicity_TRUE">是</label>
					<input name="complaintInfo.isPublicity" id="isPublicity_FALSE" type="radio"
						style="width: 15px;" value="0"/>
					<label for="isPublicity_FALSE">否</label>
				</td>
			</tr>
			<tr>
				<th>关联经营者：</th>
				<td>
					<input type="text" name="complaintResult.dealerName" class="easyui-validatebox phenix-input" 
						data-options="required:true, validType:'length[1,100]'" style="width: 250px !important;"
						placeholder="请选择经营者" readonly>
					<input type="hidden" name="complaintResult.dealerId">
					<a id="selectDealer" href="javascript: void(0);" 
							style="line-height: 12px;margin-top: 1px;display: inline-block;cursor: pointer;">
						<i class="icon czs-hande-vertical"></i>
						<label style="cursor: pointer;">经营者</label>
					</a>
				</td>
			</tr>
		</table>
	</form>
</div>