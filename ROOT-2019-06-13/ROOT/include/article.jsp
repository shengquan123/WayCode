<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<article data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-61"
	data-genuitec-path="/zrlog/src/main/webapp/include/article.jsp"
	data-genuitec-lp-enabled="false" data-genuitec-file-id="wc2-111"
	data-genuitec-path="/zrlog/src/main/webapp/include/article.jsp">
	<br /> <br /> <br />
	<h1 class="post-title">${log.title}</h1>
	<div class="meta">
		<!-- <p class="category"><a href="${log.typeUrl}" rel="tag">${log.typeName}</a> </p> -->
		<p class="published">
			<time datetime="${log.releaseTime}">&nbsp;${log.releaseTime.year+1900}-${log.releaseTime.month+1}-${log.releaseTime.date}</time>
		</p>
	</div>
	<div class="bdsharebuttonbox">
		<a href="#" class="bds_more" data-cmd="more"></a><a href="#"
			class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a><a href="#"
			class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a><a href="#"
			class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a><a href="#"
			class="bds_renren" data-cmd="renren" title="分享到人人网"></a><a href="#"
			class="bds_weixin" data-cmd="weixin" title="分享到微信"></a>
	</div>
	<script>
		window._bd_share_config = {
			"common" : {
				"bdSnsKey" : {},
				"bdText" : "",
				"bdMini" : "2",
				"bdPic" : "",
				"bdStyle" : "0",
				"bdSize" : "16"
			},
			"share" : {}
		};
		with (document)
			0[(getElementsByTagName('head')[0] || body)
					.appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='
					+ ~(-new Date() / 36e5)];
	</script>
	<div class="content">${log.content }</div>


</article>