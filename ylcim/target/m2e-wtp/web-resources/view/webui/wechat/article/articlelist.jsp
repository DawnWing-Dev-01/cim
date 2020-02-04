<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix ="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html lang="zh-cn">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no,maximum-scale=1,minimum-scale=1">
		<meta name="author" content="w·xL">
		<title>${columnInfo.name}</title>
		<link rel="stylesheet" href="../static/library/layui/css/layui.css"  media="all">
		<link rel="stylesheet" href="../static/library/icon-library/iconfont/iconfont.css?v=${version}">
		<link rel="stylesheet" href="../static/library/jQuery-WeUI/1.2.0/lib/weui.min.css?v=${version}">
		<link rel="stylesheet" href="../static/library/jQuery-WeUI/1.2.0/css/jquery-weui.css?v=${version}">
		<link rel="stylesheet" href="../static/style/webui/wechat/wechatstyle.css?v=${version}">
		<script src="../static/library/flexible/2.0/index.js?v=${version}"></script>
	</head>

	<body ontouchstart class="article-list">
		<div class="weui-search-bar" id="searchBar">
			<form id="formArticleList" class="weui-search-bar__form" method="post" 
				action="./wechatAction!showArticleListView">
				<input type="hidden" name="articleInfo.columnId" value="${columnInfo.id}"/>
				<input id="page" type="hidden" name="page" value="${page}"/>
				<div class="weui-search-bar__box">
					<i class="weui-icon-search"></i>
					<input type="search" class="weui-search-bar__input"
						id="searchInput" name="keyworks" value="${keyworks}" placeholder="搜索"> 
					<a href="javascript:"
						class="weui-icon-clear" id="searchClear"></a>
				</div>
				<label class="weui-search-bar__label" id="searchText"> 
					<i class="weui-icon-search"></i>
					<span>搜索</span>
				</label>
			</form>
			<a href="javascript:" class="weui-search-bar__cancel-btn" id="searchCancel">取消</a>
		</div>
	
		<div class="weui-panel">
			<s:iterator value="articleListMap.rows" var="article" status="status">
				<div class="weui-panel__bd">
					<div class="weui-media-box weui-media-box_text">
						<h4 class="weui-media-box__title">
							<a href="./wechatAction!showArticleView?columnId=${article.columnId}&articleId=${article.id}">
								<label>${article.name}</label>
							</a>
						</h4>
						<p class="weui-media-box__desc">
							<a href="./wechatAction!showArticleView?columnId=${article.columnId}&articleId=${article.id}">
								<s:if test="#article.summary != null">
									${article.summary}
								</s:if>
								<s:else>
									暂无摘要...
								</s:else>
							</a>
						</p>
						<ul class="weui-media-box__info">
							<li class="weui-media-box__info__meta">来源：${article.articleFrom}</li>
							<li class="weui-media-box__info__meta">
								<s:date name="#article.deliveryDate" format="yyyy-MM-dd"/>
							</li>
							<li class="weui-media-box__info__meta weui-media-box__info__meta_extra">【${article.articleType}】</li>
						</ul>
					</div>
				</div>
			</s:iterator>
			<%-- <s:if test="#parameters.columnId[0]==1">
				<div class="weui-panel__bd">
					<div class="weui-media-box weui-media-box_text">
						<h4 class="weui-media-box__title">
							<a href="./wechatAction!showArticleView?columnId=${columnId}&articleId=123">
								<label>关于消费者权益的20个法律知识</label>
							</a>
						</h4>
						<p class="weui-media-box__desc">
							<a href="./wechatAction!showArticleView?columnId=${columnId}&articleId=123">
								消费者有权要求经营者提供的商品和服务，符合保障人身、财产安全的要求。下面文章中就关于消费者权益的20个法律知识进行详细的介绍，欢迎大家的阅读，希望能带来更好的帮助!
							</a>
						</p>
						<ul class="weui-media-box__info">
							<li class="weui-media-box__info__meta">来源：王久成律师</li>
							<li class="weui-media-box__info__meta">2016-10-19</li>
							<li class="weui-media-box__info__meta weui-media-box__info__meta_extra">【转载】</li>
						</ul>
					</div>
				</div>
				<div class="weui-panel__bd">
					<div class="weui-media-box weui-media-box_text">
						<h4 class="weui-media-box__title">
							<a href="./wechatAction!showArticleView?columnId=${columnId}&articleId=123">
								<label>工商总局关于加强互联网领域消费者权益保护工作的意见</label>
							</a>
						</h4>
						<p class="weui-media-box__desc">
							<a href="./wechatAction!showArticleView?columnId=${columnId}&articleId=123">
								当前，以电子商务为主要内容的互联网经济发展迅猛，成为我国经济增长的强劲动力，对扩大消费、拉动经济增长发挥了不可替代的作用。
							</a>	
						</p>
						<ul class="weui-media-box__info">
							<li class="weui-media-box__info__meta">来源：法律教育网</li>
							<li class="weui-media-box__info__meta">2016-10-19</li>
							<li class="weui-media-box__info__meta weui-media-box__info__meta_extra">【原创】</li>
						</ul>
					</div>
				</div>
			</s:if>
			--%>
		</div>
		<div class="weui-flex xiaofeiyujing">
			<div class="weui-flex__item">
				<div id="footerPage"></div>
			</div>
		</div>
		
		<s:if test="columnInfo.id == '402880e96364331f016364489ad80000'">
			<!-- 放心消费创建栏目背景 -->
			<div class="fxxfcj_bg"></div>
		</s:if>

		<!-- body 最后 js Area-->
		<script src="http://libs.baidu.com/jquery/2.1.1/jquery.min.js?v=${version}"></script>
		<script src="../static/library/jQuery-WeUI/1.2.0/js/jquery-weui.min.js?v=${version}"></script>
	
		<!-- 调用js-sdk 网页开发工具包 -->
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js?v=${version}"></script>
	
		<script type="text/javascript" src="../static/library/fastclick/fastclick.js?v=${version}"></script>
		
		<script type="text/javascript" src="../static/library/layui/layui.all.js?v=${version}" charset="utf-8"></script>
		
		<script type="text/javascript">
			// 总数
			var total = eval('${articleListMap.total}');
			// 当前页
			var curr = eval('${page}');
		</script>
		<script type="text/javascript" src="../static/js/webui/wechat/articlelist.js?v=${version}"></script>
	</body>
</html>