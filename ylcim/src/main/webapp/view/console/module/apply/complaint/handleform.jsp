<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div style="overflow: hidden;">
	<div class="phenix-win-head"></div>
	<form id="flowLog" method="post">
		<input type="hidden" name="complaintHandleInfo.id">
		<input type="hidden" name="complaintHandleInfo.complaintId">
		<table class="phenix-table">
			<%-- <colgroup>
				<col width="15%">
				<col width="85%">
			</colgroup> --%>
			<tr>
				<th>处理类型：</th>
				<td>
					<input name="complaintHandleInfo.handleType" id="process" type="radio" 
						style="width: 15px;" value="${viewDeftData.handleProcess}" checked/>
					<label for="process">过程记录</label>
					<input name="complaintHandleInfo.handleType" id="finally" type="radio"
						style="width: 15px;" value="${viewDeftData.handleFinally}"/>
					<label for="finally">处罚结果</label>
				</td>
			</tr>
			<tr>
				<th>处理时间：</th>
				<td>
					<input type="text" name="complaintHandleInfo.handleDate" class="phenix-input" 
							style="width: 180px;" />
				</td>
			</tr>
			<tr>
				<th>处理结果：</th>
				<td>
					<textarea name="complaintHandleInfo.handleSay" class="phenix-textarea" 
								style="height: 300px; width: 99.5%;"></textarea>
				</td>
			</tr>
		</table>
	</form>
</div>