<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="center-tabs" class="easyui-tabs" data-options="fit: true,border: false,pill: true">
	<div id="center-home" data-options="fit: true,title: '系统首页',iconCls:'czs-home'" style="overflow:hidden;">
	    <shiro:hasAnyRoles name="R-01,402880e9622ceebb01622d0b84a40001">
	    	<div class="easyui-layout" data-options="fit: true,border: false" style="overflow: hidden;">   
			    <div data-options="region:'south',title:'',split:true,border: false" 
			    		style="height:50%;border-top: 1px #C3D9E0 solid;overflow: hidden; overflow: hidden;">
			    	<div style="width: 33%;height:100%;float: left;border-right: 1px #C3D9E0 solid;">
			    		<iframe style="border:none; width:99.5%;height:99.5%;" src="./view/console/module/system/portal/cpu.jsp"></iframe>
			    	</div>
			    	<div style="width: 33%;height:100%;float: left;border-right: 1px #C3D9E0 solid;">
			    		<iframe style="border:none; width:99.5%;height:99.5%;" src="./view/console/module/system/portal/memory.jsp"></iframe>
			    	</div>
			    	<div style="width: 33%;height:100%;float: left;">
			    		<iframe style="border:none; width:99.5%;height:99.5%;" src="./view/console/module/system/portal/disk.jsp"></iframe>
			    	</div>
			    </div>   
			    <div data-options="region:'center',border: false">
			    	<div class="easyui-layout" data-options="fit: true,border:false" style="overflow: hidden;">
			    		<div data-options="region:'center',border:false">
			    			<div class="easyui-layout" data-options="fit: true,border: false" style="overflow: hidden;">   
							    <div data-options="region:'west',border: false" style="width:50%;padding-top: 30px;overflow: hidden;">
							    	<!-- 为 ECharts 准备一个具备大小（宽高）的Dom -->
		    						<div id="annual_echarts" style="width: 500px;height:300px;margin: auto;"></div>
							    </div>   
							    <div data-options="region:'center',border: false" style="padding-top: 30px;overflow: hidden;">
							    	<!-- 为 ECharts 准备一个具备大小（宽高）的Dom -->
		    						<div id="industry_echarts" style="width: 500px;height:300px;margin: auto;"></div>
							    </div>   
							</div>
			    		</div>
			    	</div>
			    </div>   
			</div>
	    </shiro:hasAnyRoles>
	    <shiro:lacksRole name="R-01,402880e9622ceebb01622d0b84a40001">
	    	<div class="easyui-layout" data-options="fit: true,border:false" style="overflow: hidden;">
	    		<div data-options="region:'north',split:true,border:false" style="height:50%;">
	    			<div class="easyui-layout" data-options="fit: true" style="overflow: hidden;border-bottom: 1px #C3D9E0 solid;">
	    				<div data-options="region:'west',split:true,collapsible:false,border:false,title:'我的待办'" style="width:50%;border-right: 1px #C3D9E0 solid;">
	    					<table id="complaintGrid"></table>
	    				</div>
	    				<div data-options="region:'center',border:false,title:'预警发布提醒'" style=" border-left: 1px #C3D9E0 solid;">
			    			<table id="warningPublishGrid"></table>
			    		</div>
	    			</div>
	    		</div>
	    		<div data-options="region:'center',border:false,title:'统计分析'">
	    			<div class="easyui-layout" data-options="fit: true,border: false" style="overflow: hidden;">   
					    <div data-options="region:'west',title:'',split:true,border: false" style="width:50%;padding-top: 30px; overflow: hidden;">
					    	<!-- 为 ECharts 准备一个具备大小（宽高）的Dom -->
    						<div id="annual_echarts" style="width: 500px;height:300px;margin: auto;"></div>
					    </div>   
					    <div data-options="region:'center',border: false" style="padding-top: 30px; overflow: hidden;">
					    	<!-- 为 ECharts 准备一个具备大小（宽高）的Dom -->
    						<div id="industry_echarts" style="width: 500px;height:300px;margin: auto;"></div>
					    </div>   
					</div>
	    		</div>
	    	</div>
	    </shiro:lacksRole>
	</div>
</div>