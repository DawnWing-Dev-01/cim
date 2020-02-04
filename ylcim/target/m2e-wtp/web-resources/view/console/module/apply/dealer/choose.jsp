<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border: false,title:'条件筛选',iconCls:'icon-shaixuan',tools:'#searchTotal'" 
			style="height:35%; border-bottom: 1px #C3D9E0 solid; padding: 15px 0px;">
		<form method="post">
			<table class="phenix-table">
				<colgroup>
					<col width="25%">
					<col width="25%">
					<col width="25%">
					<col width="25%">
				</colgroup>
				<tr>
					<th>名称：</th>
					<td>
						<input type="text" id="name" class="phenix-input" style="width: 98% !important;">
					</td>
					<th>简称：</th>
					<td>
						<input type="text" id="simpleName" class="easyui-tooltip phenix-input" 
							title="需维护经营者简称后才可查询" data-options="position:'top'" style="width: 98% !important;">
					</td>
				</tr>
				<tr>
					<th>法人：</th>
					<td>
						<input type="text" id="legalPerson" class="phenix-input" style="width: 98% !important;">
					</td>
					<th>营业执照：</th>
					<td>
						<input type="text" id="licenseNo" class="phenix-input" style="width: 98% !important;">
					</td>
				</tr>
				<tr>
					<th>经营地址：</th>
					<td colspan="3">
						<input type="text" id="dealerAddress" class="phenix-input" style="width: 99.3% !important;">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border: false" style="overflow: hidden;">
		<table id="dealerGrid"></table>
	</div>
	
	<div id="searchTotal">
		<a id="addbtn" href="javascript:void(0);" class="easyui-tooltip czs-add" style="font-size: 16px;"
			title="添加经营者" data-options="position:'top'"></a>
		<a id="searchbtn" href="javascript:void(0);" class="easyui-tooltip searchbox-button" 
			title="点击查询" data-options="position:'top'"></a>
	</div>
	
	
	<!-- <div id="dealerSearchMenu" style="width:120px">
		<div data-options="name:'dealerInfo.simpleName',iconCls:'icon-shaixuan'">简&nbsp;&nbsp;称</div>
		<div data-options="name:'dealerInfo.name',iconCls:'icon-shaixuan'">名&nbsp;&nbsp;称</div>
		<div data-options="name:'dealerInfo.legalPerson',iconCls:'icon-shaixuan'">法&nbsp;&nbsp;人</div>
		<div data-options="name:'dealerInfo.licenseNo',iconCls:'icon-shaixuan'">营业执照</div>
	</div> -->
</div>