<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="include/menu.jsp"/>
	<div class="page-header" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-84" data-genuitec-path="/zrlog/src/main/webapp/admin/page.jsp" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc2-85" data-genuitec-path="/zrlog/src/main/webapp/admin/page.jsp">
		<h1>
			${mTitle}
			<small>
				<i class="fa fa-double-angle-right"></i>
				${sTitle}
			</small>
		</h1>
	</div>
	<div class="row">
		<div class="col-xs-12">
<jsp:include page="${param.include }"/>
		</div>
	</div>

<jsp:include page="include/footer.jsp"/>
