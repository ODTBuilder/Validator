<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<style>
/* http://meyerweb.com/eric/tools/css/reset/ 
   v2.0 | 20110126
   License: none (public domain)
*/
 html, body, div, span, applet, object, iframe, h1, h2, h3, h4, h5, h6, p,
	blockquote, pre, a, abbr, acronym, address, big, cite, code, del, dfn,
	em, img, ins, kbd, q, s, samp, small, strike, strong, sub, sup, tt, var,
	b, u, i, center, dl, dt, dd, ol, ul, li, fieldset, form, label, legend,
	table, caption, tbody, tfoot, thead, tr, th, td, article, aside, canvas,
	details, embed, figure, figcaption, footer, header, hgroup, menu, nav,
	output, ruby, section, summary, time, mark, audio, video {
	margin: 0;
	padding: 0;
	border: 0;
	font-size: 100%;
	font: inherit;
	vertical-align: baseline;
} 
</style>


<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/spectrum/spectrum.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/gitbuilder/gitbuilder_2.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/ol3/ol.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/gitbuilder/hochul.css">
<link rel="stylesheet" href="${ctx}/resources/js/sweetalert/sweetalert.css">
<link rel="stylesheet" href="${ctx}/resources/css/font-awesome.css">
<link rel="stylesheet" href="${ctx}/resources/css/build.css">
<%-- <link rel="stylesheet" href="${ctx}/resources/js/dist/css/bootstrap-select.min.css"> --%>

<script src="${ctx}/resources/js/jquery/jquery-2.2.2.min.js"></script>

<script src="${ctx}/resources/js/ol3/ol-debug.js"></script>

<script src="${ctx}/resources/js/proj4js/dist/proj4-src.js"></script>

<script src="${ctx}/resources/js/jsts/jsts.min.js"></script>

<script src="${ctx}/resources/js/jqueryui/jquery-ui.min.js"></script>
<%-- <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/jqueryui/jquery-ui.min.css"> --%>

<script src="${ctx}/resources/js/spectrum/spectrum.js"></script>

<script src="${ctx}/resources/js/gitbuilder/gitbuilder_2.js"></script>
<script src="${ctx}/resources/js/gitbuilder/hochul.js"></script>
<script src="${ctx}/resources/js/gitbuilder/interaction.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/gitbuilder/commandline.js"></script>
<script src="${ctx}/resources/js/gitbuilder/seulgi.js"></script>


<%-- <script src="${pageContext.request.contextPath}/resources/js/gserver/gserver.js"></script> --%>




<%-- 부트스트랩 --%>
<script src="${ctx}/resources/js/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${ctx}/resources/js/bootstrap/css/bootstrap.min.css">
<!-- 공통함수 -->
<script src="${ctx}/resources/js/common/common.js"></script>
<script src="${ctx}/resources/js/gserver/gserver.js"></script>
<script src="${ctx}/resources/js/geolayer/geolayer.js"></script>
<script src="${ctx}/resources/js/bootstrap/js/bootstrap-modalmanager.js"></script>
<script src="${ctx}/resources/js/sweetalert/sweetalert.js"></script>
<script src="${ctx}/resources/js/sweetalert/sweetalert.min.js"></script>
<%-- <script src="${ctx}/resources/js/dist/js/bootstrap-select.js"></script>
<script src="${ctx}/resources/js/dist/js/bootstrap-select.min.js"></script> --%>
<script type="text/javascript">
var CONTEXT = "${pageContext.request.contextPath}";
</script>

<link rel="stylesheet" href="${ctx}/resources/js/jqgrid/css/jquery-ui.theme.min.css">
<link rel="stylesheet" href="${ctx}/resources/js/jqgrid/css/ui.jqgrid.min.css">
<script src="${ctx}/resources/js/jqgrid/js/i18n/grid.locale-de.min.js"></script>
<script src="${ctx}/resources/js/jqgrid/js/jquery.jqgrid.min.js"></script>

