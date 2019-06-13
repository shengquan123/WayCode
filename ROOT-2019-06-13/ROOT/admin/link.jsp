<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="include/menu.jsp"/>
<link rel="stylesheet" href="${cacheFile['/assets/css/ui.jqgrid.css']}"  data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-64" data-genuitec-path="/zrlog/src/main/webapp/admin/link.jsp" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc2-65" data-genuitec-path="/zrlog/src/main/webapp/admin/link.jsp"/>

<script src="${cacheFile['/assets/js/date-time/bootstrap-datepicker.min.js']}"></script>
<script src="${cacheFile['/assets/js/jqGrid/jquery.jqGrid.min.js']}"></script>
<script src="${url}/assets/js/jqGrid/i18n/grid.locale-${lang}.js"></script>
<script src="${cacheFile['/admin/js/link_jqgrid.js']}"></script>
<div class="page-header">
	<h3>
		${_res['admin.link.manage']}
	</h3>
</div><!-- /.page-header -->
<div class="row">
	<div class="col-xs-12">
		<!-- PAGE CONTENT BEGINS -->

		<table id="grid-table"></table>

		<div id="grid-pager"></div>

		<!-- PAGE CONTENT ENDS -->
	</div><!-- /.col -->
</div>
<jsp:include page="include/footer.jsp"/>
