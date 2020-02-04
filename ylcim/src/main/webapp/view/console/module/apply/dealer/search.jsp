<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div style="overflow: hidden;">
	<div class="phenix-win-head"></div>
	<form method="post">
		<table class="phenix-table">
			<!-- <tr>
				<th>名称：</th>
				<td>
					<input type="text" name="dealerInfo.name" class="phenix-input">
				</td>
				<th>简称：</th>
				<td>
					<input type="text" name="dealerInfo.name" class="phenix-input">
				</td>
			</tr> -->
			<tr>
				<th>创建日期：</th>
				<td>
					<input type="text" name="dealerInfo.startDate" class= "easyui-datebox" 
						style="width: 178px;" placeholder="开始时间"/>
					&nbsp;至&nbsp;
					<input type="text" name="dealerInfo.endDate" class= "easyui-datebox" 
						style="width: 178px;" placeholder="结束时间"/>
				</td>
			</tr>
			<tr>
				<th>二维码生成状态：</th>
				<td>
					<input name="dealerInfo.qrcode" id="generated" type="radio" 
						style="width: 15px;" value="not null" checked/>
					<label for="generated">已生成</label>
					<input name="dealerInfo.qrcode" id="notGenerated" type="radio"
						style="width: 15px;" value="null"/>
					<label for="notGenerated">未生成</label>
					<input name="dealerInfo.qrcode" id="nullSearch" type="radio"
						style="width: 15px;" value=""/>
					<label for="nullSearch">不过滤</label>
				</td>
			</tr>
		</table>
	</form>
</div>