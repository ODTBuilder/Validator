<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/gitbuilder/gitbuilder_3.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/ol3/ol.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/gitbuilder/command.css">
<%-- <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/dist/css/bootstrap-select.min.css"> --%>

<script src="${pageContext.request.contextPath}/resources/js/jquery/jquery-2.2.2.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/ol3/ol-debug.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/proj4js/dist/proj4-src.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/jsts/jsts.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/jqueryui/jquery-ui.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/jqueryui/jquery-ui.min.css">

<script src="${pageContext.request.contextPath}/resources/js/spectrum/spectrum.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/gitbuilder/command.js"></script>

<%-- 부트스트랩 --%>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/bootstrap/css/bootstrap.min.css">

<!-- 데이터 테이블 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/datatables/js/jquery.dataTables.min.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/datatables/css/jquery.dataTables.min.css"/>

<!-- 폰트어썸 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/fontawesome/css/font-awesome.min.css"/>