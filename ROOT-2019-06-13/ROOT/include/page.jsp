<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp"></jsp:include>
<c:choose>
	<c:when test="${empty requestScope.data}">
		<c:set var="pageLevel" value="1" scope="request" />
		<jsp:include page="404.jsp"></jsp:include>
	</c:when>
	<c:otherwise>
		<c:if test="${not empty tipsType}">
			<h2 class="category-title">
				${tipsType}目录：${tipsName}<br /> 以下是与${tipsType} “${tipsName}” 相关联的文章
			</h2>
		</c:if>

		<div class="intro-header7"></div>
		<br />
		<c:if test="${not empty requestScope.data}">
			<c:forEach var="log" items="${requestScope.data.rows}">
				<article class="entry">
					<div class="col-md-2">
						<img src="${log.titleImg}" alt="" class="img-responsive"
							style="margin-left: -20px;" />
					</div>
					<div class="col-md-9">
						<h3 class="post-title" style="margin-left: -20px;">
							<a rel="bookmark" href="${log.url}">${log.title}</a>
						</h3>
						<div class="content" style="margin-left: -20px;">
							<p>${log.digest}....<a href="${log.url}">[详细]</a>
							</p>
						</div>
					</div>
					<div class="meta">
						<p class="category">
							<time datetime="${log.releaseTime}">&nbsp;${log.releaseTime.year+1900}-${log.releaseTime.month+1}-${log.releaseTime.date}</time>
						</p>
						<div style="float: right;">
							<div class="bdsharebuttonbox">
								<a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博1111"></a><a
									href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a>
							</div>
							<script>
								window._bd_share_config = {
									"common" : {
										"bdSnsKey" : {},
										"bdText" : "",
										"bdMini" : "1",
										"bdMiniList" : [ "tsina", "weixin" ],
										"bdPic" : "",
										"bdStyle" : "0",
										"bdSize" : "32"
									},
									"share" : {},
									"image" : {
										"viewList" : [ "tsina", "weixin" ],
										"viewText" : "分享到：",
										"viewSize" : "16"
									},
									"selectShare" : {
										"bdContainerClass" : null,
										"bdSelectMiniList" : [ "tsina",
												"weixin" ]
									}
								};
								with (document)
									0[(getElementsByTagName('head')[0] || body)
											.appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='
											+ ~(-new Date() / 36e5)];
							</script>
						</div>
					</div>
				</article>


			</c:forEach>
		</c:if>
	</c:otherwise>
</c:choose>
<jsp:include page="pager.jsp"></jsp:include>
<jsp:include page="footer.jsp"></jsp:include>
