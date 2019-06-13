<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="include/menu.jsp"/>
<div class="row" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-82" data-genuitec-path="/zrlog/src/main/webapp/admin/message.jsp" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc2-83" data-genuitec-path="/zrlog/src/main/webapp/admin/message.jsp">
<div class="col-xs-12">
<div class="alert alert-block alert-success">
	<p>
		${message}
	</p>
	<p>
		<a href="javascript:history.go(-1);"><button class="btn btn-sm btn-success">${_res.goBack}</button></a>
	</p>
</div>
</div>
</div>
<jsp:include page="include/footer.jsp" />