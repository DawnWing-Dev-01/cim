<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="easyui-tabs" data-options="fit:true,border:false,pill:true">
	<div title="投诉信息" style="display:none;">
		<div style="overflow: hidden;">
			<div class="phenix-win-head"></div>
			<form method="post">
				<input type="hidden" name="complaintInfo.id">
				<input type="hidden" name="complaintInfo.dealerId">
				<input type="hidden" name="complaintInfo.complaintSource" value="${viewDeftData.complaintSource}">
				<shiro:authenticated>
					<input type="hidden" name="complaintInfo.reporterId" value="<shiro:principal property="userId" />">
				</shiro:authenticated>
				<fieldset>
					<legend>登记方</legend>
					<table class="phenix-table">
						<colgroup>
							<col width="25%">
							<col width="25%">
							<col width="25%">
							<col width="25%">
						</colgroup>
						<tr>
							<th>登记单位：</th>
							<td>
								<s:if test="viewType!='readOnly'">
									<input type="text" name="complaintInfo.registerUnit" class="easyui-validatebox phenix-input"
										data-options="required:true, validType:'length[1,100]'" 
										value="${viewDeftData.registerUnit}">
								</s:if>
								<s:else>
									<input type="text" name="complaintInfo.registerUnit" class="phenix-no-input" disabled>
								</s:else>
							</td>
							<th>编号：</th>
							<td>
								<s:if test="viewType!='readOnly'">
									<input type="text" name="complaintInfo.complaintCode" class="phenix-no-input" 
										placeholder="系统自动生成" disabled>
								</s:if>
								<s:else>
									<input type="text" name="complaintInfo.complaintCode" class="phenix-no-input" disabled>
								</s:else>
							</td>
						</tr>
					</table>
				</fieldset>
				<fieldset>
					<legend>投诉人</legend>
					<table class="phenix-table">
						<colgroup>
							<col width="25%">
							<col width="25%">
							<col width="25%">
							<col width="25%">
						</colgroup>
						<tr>
							<th>姓名：</th>
							<td>
								<s:if test="viewType!='readOnly'">
									<input type="text" name="complaintInfo.informerName" class="easyui-validatebox phenix-input"
										data-options="required:true, validType:'length[1,100]'">
								</s:if>
								<s:else>
									<input type="text" name="complaintInfo.informerName" class="phenix-no-input" disabled>
								</s:else>
							</td>
							<th>性别：</th>
							<td>
								<input name="complaintInfo.informerGender" id="GENDER_MALE" type="radio" 
									style="width: 15px;" value="${viewDeftData.genderMale}"
									<s:if test="viewType=='readOnly'">disabled</s:if> checked/>
								<label for="GENDER_MALE">男</label>
								<input name="complaintInfo.informerGender" id="GENDER_FEMALE" type="radio"
									style="width: 15px;" value="${viewDeftData.genderFemale}" 
									<s:if test="viewType=='readOnly'">disabled</s:if>/>
								<label for="GENDER_FEMALE">女</label>
							</td>
						</tr>
						<tr>
							<th>年龄：</th>
							<td>
								<s:if test="viewType!='readOnly'">
									<input type="text" name="complaintInfo.informerAge" class="easyui-numberbox phenix-input"
										data-options="min:1,max:100,precision:0" style="width: 171px;">
								</s:if>
								<s:else>
									<input type="text" name="complaintInfo.informerAge" class="phenix-no-input" disabled>
								</s:else>
							</td>
							<th>联系电话：</th>
							<td>
								<s:if test="viewType!='readOnly'">
									<input type="text" name="complaintInfo.informeriPhone" 
										class="easyui-validatebox phenix-input" 
										data-options="required:true, validType:['telNum', 'length[1,12]']">
								</s:if>
								<s:else>
									<input type="text" name="complaintInfo.informeriPhone" class="phenix-no-input" disabled>
								</s:else>
							</td>
						</tr>
						<tr>
							<th>住址：</th>
							<td colspan="3">
								<s:if test="viewType!='readOnly'">
									<input type="text" name="complaintInfo.informerAddress" class="phenix-input">
								</s:if>
								<s:else>
									<input type="text" name="complaintInfo.informerAddress" class="phenix-no-input" disabled>
								</s:else>
							</td>
						</tr>
					</table>
				</fieldset>
				<fieldset>
					<legend>被投诉人</legend>
					<table class="phenix-table">
						<tr>
							<th>名称：</th>
							<td colspan="3">
								<s:if test="viewType!='readOnly'">
									<input type="text" name="complaintInfo.dealerName" class="easyui-validatebox phenix-input" 
										data-options="required:true, validType:'length[1,100]'" style="width: 425px !important;"
										placeholder="请选择经营者" readonly>
									<a id="selectDealer" href="javascript: void(0);" 
											style="line-height: 12px;margin-top: 1px;display: inline-block;cursor: pointer;">
										<i class="icon czs-hande-vertical"></i>
										<label style="cursor: pointer;">经营者</label>
									</a>
								</s:if>
								<s:else>
									<input type="text" name="complaintInfo.dealerName" class="phenix-no-input" disabled>
								</s:else>
							</td>
						</tr>
						<tr>
							<th>联系人：</th>
							<td>
								<s:if test="viewType!='readOnly'">
									<input type="text" name="complaintInfo.dealerLinkman" class="easyui-validatebox phenix-input" 
										data-options="required:true, validType:'length[1,100]'">
								</s:if>
								<s:else>
									<input type="text" name="complaintInfo.dealerLinkman" class="phenix-no-input" disabled>
								</s:else>
							</td>
							<th>联系电话：</th>
							<td>
								<s:if test="viewType!='readOnly'">
									<input type="text" name="complaintInfo.dealeriPhone" class="easyui-validatebox phenix-input" 
										data-options="required:true, validType:['telNum', 'length[1,12]']">
								</s:if>
								<s:else>
									<input type="text" name="complaintInfo.dealeriPhone" class="phenix-no-input" disabled>
								</s:else>
							</td>
						</tr>
						<tr>
							<th>管辖单位：</th>
							<td>
								<s:if test="viewType!='readOnly'">
									<input type="text" name="complaintInfo.dealerJurisdiction" class="phenix-input" readonly>
								</s:if>
								<s:else>
									<input type="text" name="complaintInfo.dealerJurisdiction" class="phenix-no-input" disabled>
								</s:else>
							</td>
							<th></th>
							<td></td>
						</tr>
						<tr>
							<th>经营地址：</th>
							<td colspan="3">
								<s:if test="viewType!='readOnly'">
									<input type="text" name="complaintInfo.dealerAddress" class="easyui-validatebox phenix-input" 
										data-options="required:true, validType:'length[1,512]'">
								</s:if>
								<s:else>
									<input type="text" name="complaintInfo.dealerAddress" class="phenix-no-input" disabled>
								</s:else>
							</td>
						</tr>
					</table>
				</fieldset>
				<fieldset>
					<legend>投诉事实、理由及请求</legend>
					<table class="phenix-table">
						<tr>
							<td colspan="4">
								<s:if test="viewType!='readOnly'">
									<textarea name="complaintInfo.complaintReason" class="phenix-textarea" 
										style="height: 300px; width: 99.5%;"></textarea>
								</s:if>
								<s:else>
									<textarea name="complaintInfo.complaintReason" class="phenix-textarea" 
										style="height: 300px; width: 99.5%;" disabled></textarea>
								</s:else>
							</td>
						</tr>
					</table>
					<div class="beizhu">
						<p>注：投诉事实应当包括：消费者接受商品或服务的名称、消费日期、消费涉及金额等具体情况。</p>
					</div>
				</fieldset>
			</form>
		</div>
	</div>
	<%-- <div title="投诉照片" style="display:none;">
		<!-- <div class="uploadItem">
			<div class="layui-upload-drag" id="uploadDemo">
				<i class="layui-icon"></i>
				<p>点击上传，或将文件拖拽到此处</p>
			</div>
			<label id="realBtn" class="btn btn-info">
	     		<input class="layui-upload-file" type="file" accept="undefined"
					name="file">
	     	</label>
		</div> -->
		<div class="complaint-img-container">
			<s:iterator value="viewDeftData.complaintPhotoList" var="complaintPhoto">
				<div class="img-item">
					<!-- <i class="item-icon czs-image-l showimg"></i> -->
					<img class="showimg" alt="照片" 
						src="./wechat/attachAction!showImage?object=${complaintPhoto.imgPath}">
				</div>
			</s:iterator>
			<s:if test="viewDeftData.complaintPhotoList.size == 0">
				<s:iterator value="new int[4]" status="i">
					<div class="img-item">
						<i class="item-icon czs-image-l showimg"></i>
						<label></label>
						<img class="showimg" alt="照片" 
							src="./wechat/attachAction!showImage?object=${complaintPhoto.imgPath}">
					</div>
				</s:iterator>
			</s:if>
		</div>
	</div> --%>
	<div title="投诉照片" style="display:none;">
		<div class="complaint-img-container">
			<s:iterator value="viewDeftData.complaintPhotoList" var="complaintPhoto">
				<div class="img-item">
					<!-- <i class="item-icon czs-image-l showimg"></i> -->
					<img class="showimg" alt="照片" 
						src="./wechat/attachAction!showImage?object=${complaintPhoto.imgPath}">
				</div>
			</s:iterator>
			<s:set var="photosize" value="viewDeftData.complaintPhotoList.size"></s:set>
			<s:if test="#photosize > 0 && #photosize < 4">
				<s:iterator begin="1" end="4 - #photosize" step="1">
					<div class="img-item">
						<i class="item-icon czs-image-l showimg"></i>
					</div>
				</s:iterator>
			</s:if>
			<s:if test="viewDeftData.complaintPhotoList.size == 0">
				<s:iterator value="new int[4]" status="i">
					<div class="img-item">
						<i class="item-icon czs-image-l showimg"></i>
					</div>
				</s:iterator>
			</s:if>
		</div>
	</div>
	<s:if test="viewType=='readOnly'">
		<div title="进度&日志" style="display:none;">
			<div class="easyui-layout" data-options="fit:true">
				<div data-options="region:'center',border: false" style="/* overflow: hidden; */ background: #FFF;">
					<ul class="layui-timeline" style="width: 80%; margin: 0px auto; margin-top: 30px;">
						<s:iterator value="workflowLogList" var="flowLog">
							<li class="layui-timeline-item"><i class="layui-icon layui-timeline-axis">&#xe63f;</i>
								<div class="layui-timeline-content layui-text">
									<h3 class="layui-timeline-title"><s:date name="#flowLog.handleDate" format="yyyy年MM月dd日  hh:MM:ss"></s:date></h3>
									<p>
										<label>${flowLog.handleUserName}</label>
										<s:if test="#flowLog.handleResult == 1">
											<span>【${flowLog.flowNodeText}】</span>
										</s:if>
										<s:elseif test="#flowLog.handleResult == 0">
											<span>【驳回】</span>
										</s:elseif>
										<s:else>
											<span>【终止】</span>
										</s:else>
										<label>了失信行为单</label>
									</p>
									<ul>
										<li>
											<label>审核结果：</label>
											<s:if test="#flowLog.handleResult == 1">
												<span>同意</span>
											</s:if>
											<s:elseif test="#flowLog.handleResult == 0">
												<span>驳回</span>
											</s:elseif>
											<s:else>
												<span>终止</span>
											</s:else>
										</li>
										<li>
											<label>审核意见：</label>
											<s:if test="#flowLog.handleSay!=null && #flowLog.handleSay!=''">
												<span>${flowLog.handleSay}</span>
											</s:if>
											<s:else>
												<span>默认同意</span>
											</s:else>
										</li>
										<s:if test="#flowLog.flowNodeId == '402880e962851e0c0162853295140005' && #flowLog.handleResult != 0">
											<li>
												<a id="showHandleGrid" href="javascript:void(0);">查看处理记录</a>
											</li>
										</s:if>
									</ul>
								</div>
							</li>
						</s:iterator>
						<li class="layui-timeline-item"><i class="layui-icon layui-timeline-axis">&#xe63f;</i>
							<div class="layui-timeline-content layui-text">
								<div class="layui-timeline-title">起草</div>
							</div>
						</li>
					</ul>
				</div>
				<div data-options="region:'west',border: false" style="overflow: hidden; width: 50%; background: #FFF;">
					<ul id="workflowSchedule" class="layui-timeline" style="width: 50%; margin: 0px auto; margin-top: 30px;">
						<li class="layui-timeline-item">
							<i class="layui-icon layui-timeline-axis" style="color: red;"></i>
							<div class="layui-timeline-content layui-text">
								<div class="layui-timeline-title">开始</div>
							</div>
						</li>
						<s:set var="runIndex" value="-1"></s:set>
						<s:iterator value="workflowList" var="workflow" status="status">
							<s:if test="workflowExample.flowNodeCode == #workflow.flowNodeCode">
								<s:set var="runIndex" value="#status.index + 1"></s:set>
							</s:if>
							
							<li class="layui-timeline-item">
								<s:if test="#status.index != #runIndex">
									<i class="layui-icon layui-timeline-axis" 
										<s:if test="#runIndex == -1 || #status.index < #runIndex">style="color: red;"</s:if>></i>
								</s:if>
								<s:else>
									<i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop layui-timeline-axis"></i>
								</s:else>
								<div class="layui-timeline-content layui-text">
									<div class="layui-timeline-title">
										<span>${workflow.flowNodeText}【${workflow.flowNodeCode}】</span>
										<s:if test="#status.index == #runIndex">
											<label class="during">进行中<span class="dotting"></span></label>
										</s:if>
									</div>
								</div>
							</li>
						</s:iterator>
						<li class="layui-timeline-item">
							<i class="layui-timeline-axis czs-circle-o" 
								style="font-size: 18px;<s:if test="workflowExample.status != 1"> color: red;</s:if>"></i>
							<div class="layui-timeline-content layui-text">
								<div class="layui-timeline-title">结束</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</s:if>
</div>