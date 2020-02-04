<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%-- <div id="showQrCode_container" style="overflow: hidden; background: #FFFFFF;">
	<div class="qrcode-title" style="padding: 25px 0px;text-align: center;background: #F1F1F1;">
		<i class="item-icon icon-jingying" style="font-size: 40px;"></i>
		<h1 id="showTitle"></h1>
	</div>
	<div class="qrcode-img" style="text-align: center;padding: 49px 0px;">
		<img id="showQrCode" alt="QrCode" style="width: 60%;border: 1px #EEE solid;">
		<div class="showfooter" style="margin-top: 10px;">
			<small style="font-size: 12px;">请使用微信扫描二维码查询<br>"经营者详细信息"</small>
		</div>
	</div>
</div>
 --%>
<img id="showQrCode" src="${viewDeftData.showQrCodeUrl}" style="width: 100%; padding: 0px; margin: 0px;"/>
<iframe id="viewPrint" style="display: none;"></iframe>