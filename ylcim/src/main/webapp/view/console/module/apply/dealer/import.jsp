<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div style="overflow: hidden;">
	<div class="phenix-win-head"></div>
	<form method="post" enctype="multipart/form-data">
		<table class="phenix-table">
			<tr>
				<th>导入模板：</th>
				<td align="center">
					<a href="./static/other/template/template_import_dealer.xls">点击下载</a>
				</td>
			</tr>
			<tr align="center">
				<th>上传文件：</th>
				<td>
					<input type="file" name="file"/>
				</td>
			</tr>
		</table>
		<fieldset style="width: 85%;">
			<legend>导入说明&步骤</legend>
			<p>1、下载模板，查看需要导入的列；</p>
			<p>2、在模板后追加数据&nbsp;/&nbsp;根据列顺序从其他系统导入；</p>
			<p style="color: red;">重要：导入之前需维护好基础数据（管辖单位、行业），否则会导入失败；</p>
		</fieldset>
	</form>
</div>