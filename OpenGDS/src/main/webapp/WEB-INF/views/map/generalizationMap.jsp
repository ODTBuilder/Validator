<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>




<script src="${pageContext.request.contextPath}/resources/js/jquery/jquery-2.2.2.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/ol3/ol-debug.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/ol3/ol.css">

<script src="${pageContext.request.contextPath}/resources/js/jqueryui/jquery-ui.min.js"></script>
<%-- <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/jqueryui/jquery-ui.min.css"> --%>

<script src="${pageContext.request.contextPath}/resources/js/spectrum/spectrum.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/spectrum/spectrum.css">

<script src="${pageContext.request.contextPath}/resources/js/gitbuilder/gitbuilder_2.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/gitbuilder/gitbuilder_2.css">

<%-- <script src="${pageContext.request.contextPath}/resources/js/gserver/gserver.js"></script> --%>


<!-- 공통함수 -->
<script src="${ctx}/resources/js/common/common.js"></script>
<script src="${ctx}/resources/js/generalization/generalization.js"></script>
<script type="text/javascript">
var CONTEXT = "${pageContext.request.contextPath}";
</script>

<%-- 부트스트랩 --%>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/bootstrap/css/bootstrap.min.css">
<style type="text/css">
.cntGeneralWarp {width: 97.5%;margin:0 auto;padding:40px 0 100px 0;font-family:'NanumGothic';}
.tbDetail{border-collapse:collapse; width:100%; text-align: center;}
.tbDetail thead th{padding: 10px 10px 10px 10px; background: #eee; border: 1px solid #ddd; font-size: 16px; font-family: 'NanumGothic';}
.tbDetail tbody td{padding: 10px 10px 10px 10px; border:1px solid #eee; font-size: 13px;}
.tbDetail .alignL {text-align: center;}
#bfLayer,#afLayer {
	width: 100%;
/* 	height: 650px; */
}
</style>




<title>generalization map</title>
</head>
<body>
	<div id="wrap">
		<div id="builderInfo" style="width: 100%; height: 3%; background-color: #e3e3e3;">
			<div class="btn-group" role="group" style="float: right;">
	  			<button type="button" class="btn btn-default" onclick="gitbuilder.ui.showQAPage()">검수</button>
	  			<button type="button" class="btn btn-default" onclick="gitbuilder.ui.showGenPage()">일반화</button>
			</div>
		</div>
		<div id="builderHeader" style="width: 100%; height: 5%; background-color: #e9e9e9">

		<div class="col-md-8" role="toolbar" aria-label="...">
			<button class="btn btn-default" onclick="gitbuilder.ui.NewSHPWindow()">SHP</button>
			<button class="btn btn-default" onclick="gitbuilder.ui.NewVectorWindow()">Vector</button>
			<button class="btn btn-default">GeoServer</button>
			<button class="btn btn-default">Editing</button>
			<button class="btn btn-default">Validation</button>
			<button class="btn btn-default" onclick="gitbuilder.ui.NewGeoserverWindow()">Add GeoServer</button>
			<button class="btn btn-default">Error Report</button>
			<button class="btn btn-default">Save</button>
		</div>

		<div class="col-md-4" role="toolbar" aria-label="...">
			<div class="col-md-10">
				<input class="form-control" type="text">
			</div>
			<div class="col-md-2">
				<button class="btn btn-default">Search</button>
			</div>
		</div>

	</div>
		<div id="bodyDiv" style="width: 100%;height: 100%;">
				<div class="cntGeneralWarp">
						<table class="tbDetail" style="border: 1px solid #eee; table-layout:fixed;">
							<thead>
								<tr>
									<th style="padding: 10px 10px 10px 10px;" scope="col"
										align="center" colspan="2">일반화전</th>
									<th style="padding: 10px 10px 10px 10px;" scope="col"
										align="center" colspan="2">일반화후</th>
								</tr>
							</thead>
							<tbody id="mapBody">
								<tr>
									<td colspan="2"><div id="bfLayer"></div></td>
									<td colspan="2"><div id="afLayer" ></div></td>
								</tr>
								<!-- <tr>
									<td><div id="expandBL">
											<input type="button" class="bigButton" value="확대"
												style="width: 100px; margin-right: 7px;"
												onclick="showExpendBL()"
										</div></td>
									<td><div id="expandAL">
											<input type="button" class="bigButton" value="확대"
												style="width: 100px; margin-right: 7px;"
												onclick="showExpendAL()"
										</div></td>
								</tr> -->
							</tbody>
							<!-- <thead>								
								<tr>
									<th scope="col" align="center" colspan="4">일반화 결과</th>
								</tr>
							</thead>
							<colgroup>
								<col width="25%" />
								<col width="25%" />
								<col width="25%" />
								<col width="25%" />
							</colgroup>
							<thead>
								<tr>
									<th>구 분</th>
									<th>대상타입</th>
									<th>일반화 전</th>
									<th>일반화 후</th>
								</tr>
							</thead> -->
						</table>
				</div>
		</div>
	</div>
</body>

<script type="text/javascript">

</script>
</html>