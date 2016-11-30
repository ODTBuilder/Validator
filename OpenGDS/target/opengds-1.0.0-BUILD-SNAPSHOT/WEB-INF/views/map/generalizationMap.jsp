<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<jsp:include page="/WEB-INF/views/common/common.jsp" />
<head>
<!-- 공통함수 -->
<%-- 부트스트랩 --%>
<script src="${pageContext.request.contextPath}/resources/js/generalization/generalization.js"></script>
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
<div id="header" style="width: 100%; height: 6%; background-color: #f8f8f8;">
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
</div>
	<div id="wrap">
		<div id="builderHeader navbar navbar-default" style="width: 100%; height: 6%; background-color: #f8f8f8;">
			<div class="container-fluid">
				<div class="navbar-form navbar-left" id="navbarle">
					<button class="btn btn-default" onclick="gitbuilder.ui.NewSHPWindow()">SHP</button>
					<button class="btn btn-default" onclick="gitbuilder.ui.NewGeoserverLayerWindow('G')">GeoServer</button>
					<button class="btn btn-default" onclick="gitbuilder.ui.NewGeneralizationWindow()">G/E</button>
				</div>

				<div class="navbar-form navbar-right" id="navbarri">
					<button class="btn btn-default" onclick="gitbuilder.ui.NewGenLayerManageWindow()">Layer Management</button>
					<button class="btn btn-default" onclick="gitbuilder.ui.NewAddGeoserverWindow()">Server Management</button>
					<button class="btn btn-default">Option</button>
					<button class="btn btn-default" onclick="gitbuilder.ui.NewTopologyTableWindow()">TopologyTable</button>
					<button class="btn btn-default disabled" id="resultbtn" onclick="gitbuilder.ui.NewGeneralizationResultWindow()">Result</button>
					<button class="btn btn-default">Save</button>
				</div>
			</div>
		</div>
		<div id="bodyDiv" style="width: 100%;height: 100%;">
				<div class="cntGeneralWarp">
						<table class="tbDetail" style="border: 1px solid #eee; table-layout:fixed;">
							<thead>
								<tr>
									<th style="padding: 10px 10px 10px 10px; text-align :center" scope="col"
										align="center" colspan="2" >Before Generalization</th>
									<th style="padding: 10px 10px 10px 10px; text-align :center" scope="col"
										align="center" colspan="2">After Generalization</th>
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