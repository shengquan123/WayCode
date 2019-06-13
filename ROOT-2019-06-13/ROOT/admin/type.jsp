<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="include/menu.jsp"/>
<link rel="stylesheet" href="${cacheFile['/assets/css/ui.jqgrid.css']}"  data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-89" data-genuitec-path="/zrlog/src/main/webapp/admin/type.jsp" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc2-90" data-genuitec-path="/zrlog/src/main/webapp/admin/type.jsp"/>

<script src="${cacheFile['/assets/js/date-time/bootstrap-datepicker.min.js']}"></script>
<script src="${cacheFile['/assets/js/jqGrid/jquery.jqGrid.min.js']}"></script>
<script src="${url}/assets/js/jqGrid/i18n/grid.locale-${lang}.js"></script>
<script src="${cacheFile['/admin/js/type_jqgrid.js']}"></script>

<div class="page-header">
	<h3>
		${_res['admin.type.manage']}
	</h3>
</div>
<div class="row">
	<div class="col-xs-12">
		<!-- PAGE CONTENT BEGINS -->

		<table id="grid-table"></table>

		<div id="grid-pager"></div>

		<!-- PAGE CONTENT ENDS -->
	</div><!-- /.col -->
</div>
<jsp:include page="include/footer.jsp"/>