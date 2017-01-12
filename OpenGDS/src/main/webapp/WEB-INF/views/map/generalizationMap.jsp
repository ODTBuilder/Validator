<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<jsp:include page="/WEB-INF/views/common/common.jsp" />
<head>
<!-- 공통함수 -->
<%-- 부트스트랩 --%>
<script src="${pageContext.request.contextPath}/resources/js/generalization/generalization.js"></script>
<style type="text/css">
.cntGeneralWarp {
	width: 97.5%;
	height: 100%;
	margin: 0 auto;
	padding: 40px 0 40px 0;
	font-family: 'NanumGothic';
}

.tbDetail {
	border-collapse: collapse;
	width: 100%;
	text-align: center;
}

.tbDetail thead th {
	padding: 10px 10px 10px 10px;
	background: #eee;
	border: 1px solid #ddd;
	font-size: 16px;
	font-family: 'NanumGothic';
}

.tbDetail tbody td {
	padding: 10px 10px 10px 10px;
	border: 1px solid #eee;
	font-size: 13px;
}

.tbDetail .alignL {
	text-align: center;
}

#bfLayer, #afLayer {
	width: 100%;
	/* 	height: 650px; */
}
</style>



<title>generalization map</title>
</head>
<body>
	<div id="builderInfo" class="container-fluid">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
	</div>
	<div id="builderHeader" class="container-fluid">
		<nav class="row navbar navbar-default" style="margin-bottom: 0;">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#builderNav">
						<span class="sr-only">Toggle navigation</span>MENU
					</button>
				</div>
				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse" style="padding: 0;" id="builderNav">
					<ul class="nav navbar-nav">
<!-- 						<li><a href="#" onclick="gitbuilder.ui.NewVectorWindow()" title="Vector"><i class="fa fa-file-o fa-lg" aria-hidden="true"></i> New</a></li> -->
						<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false" title="New Layer"><i class="fa fa-folder-open-o fa-lg" aria-hidden="true"></i> Open</a>
							<ul class="dropdown-menu" role="menu">
								<!-- 							<li><a href="#" onclick="gitbuilder.ui.NewVectorWindow()"><h4>Vector</h4></a></li> -->
								<li><a href="#" onclick="gitbuilder.ui.NewSHPWindow()">SHP</a></li>
								<li><a href="#" onclick="gitbuilder.ui.NewGeoserverLayerWindow('G')">GeoServer</a></li>
							</ul></li>

						<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false" title="Save"><i class="fa fa-floppy-o fa-lg" aria-hidden="true"></i> Save</a>
							<ul class="dropdown-menu" role="menu">
								<li><a href="#">as a SHP</a></li>
								<li><a href="#">to Server</a></li>
							</ul></li>

<!-- 						<li><a href="#" onclick="gitbuilder.ui.EditingWindow()" title="Edit"><i class="fa fa-pencil-square-o fa-lg" aria-hidden="true"></i> Edit</a></li> -->
						<li><a href="#" onclick="gitbuilder.ui.NewGeneralizationWindow()" title="Generalization"><i class="fa fa-search fa-lg" aria-hidden="true"></i> Generalization</a></li>

						<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false" title="Result"><i class="fa fa-list-alt fa-lg" aria-hidden="true"></i> Result</a>
							<ul class="dropdown-menu" role="menu">
								<li><a href="#" onclick="gitbuilder.ui.NewGeneralizationResultWindow()">Execution Result</a></li>
								<li><a href="#" onclick="gitbuilder.ui.NewTopologyTableWindow()">Topology Table</a></li>
							</ul></li>

						<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false" title="Management"><i class="fa fa-server fa-lg" aria-hidden="true"></i> Management</a>
							<ul class="dropdown-menu" role="menu">
								<li><a href="#" onclick="gitbuilder.ui.NewGenLayerManageWindow()">Layer Management</a></li>
						<li><a href="#" onclick="gitbuilder.ui.NewAddGeoserverWindow()">Server Management</a></li>
							</ul></li>

<!-- 						<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false" title="History"><i class="fa fa-history fa-lg" aria-hidden="true"></i> History</a> -->
<!-- 							<ul class="dropdown-menu" role="menu"> -->
<!-- 								<li><a href="#">History</a></li> -->
<!-- 								<li role="presentation" class="divider"></li> -->
<!-- 								<li><a href="#">Download History</a></li> -->
<!-- 								<li><a href="#">Upload History</a></li> -->
<!-- 							</ul></li> -->

<!-- 						<li><a href="#" onclick="gitbuilder.ui.NewAddGeoserverWindow()" title="Server Management"><i class="fa fa-server fa-lg" aria-hidden="true"></i> Server</a></li> -->
						<li><a href="#" onclick="" title="Information"><i class="fa fa-info-circle fa-lg" aria-hidden="true"></i> Refresh</a></li>
					</ul>
				</div>
				<!-- /.navbar-collapse -->
			</div>
			<!-- /.container-fluid -->

		</nav>
	</div>
	<div id="wrap" style="height: 88%;">
<!-- 		<div id="builderHeader" class="navbar navbar-default" style="width: 100%; height: 6%; background-color: #f8f8f8;"> -->
<!-- 			<div class="col-md-9"> -->
<!-- 				<div class="btn-group" role="group"> -->
<!-- 					<button type="button" class="btn btn-default dropdown-toggle btn-lg" data-toggle="dropdown" aria-expanded="false"> -->
<!-- 						New Layer <span class="caret"></span> -->
<!-- 					</button> -->
<!-- 					<ul class="dropdown-menu" role="menu"> -->
<!-- 						<li><a href="#" onclick="gitbuilder.ui.NewSHPWindow()"><h4>SHP</h4></a></li> -->
<!-- 						<li><a href="#" onclick="gitbuilder.ui.NewGeoserverLayerWindow('G')"><h4>GeoServer</h4></a></li> -->
<!-- 					</ul> -->
<!-- 				</div> -->

<!-- 				<button class="btn btn-default dropdown-toggle btn-lg" onclick="gitbuilder.ui.NewGeneralizationWindow()">E / G</button> -->
<!-- 				<button class="btn btn-default dropdown-toggle btn-lg" onclick="gitbuilder.ui.showGenPage()">Refresh</button> -->
<!-- 			</div> -->






<!-- 			<div class="col-md-3 text-right"> -->
<!-- 				<div class="btn-group" role="group"> -->
<!-- 					<button type="button" class="btn btn-default dropdown-toggle btn-lg" data-toggle="dropdown" aria-expanded="false"> -->
<!-- 						Management<span class="caret"></span> -->
<!-- 					</button> -->
<!-- 					<ul class="dropdown-menu" role="menu"> -->
<!-- 						<li><a href="#" onclick="gitbuilder.ui.NewGenLayerManageWindow()"><h4>Layer Management</h4></a></li> -->
<!-- 						<li><a href="#" onclick="gitbuilder.ui.NewAddGeoserverWindow()"><h4>Server Management</h4></a></li> -->
<!-- 						<li><a href="#"><h4>Option</h4></a></li> -->
<!-- 					</ul> -->
<!-- 				</div> -->
<!-- 				<div class="btn-group" role="group"> -->
<!-- 					<button type="button" class="btn btn-default dropdown-toggle btn-lg" data-toggle="dropdown" aria-expanded="false"> -->
<!-- 						Result<span class="caret"></span> -->
<!-- 					</button> -->
<!-- 					<ul class="dropdown-menu" role="menu"> -->
<!-- 						<li><a href="#" onclick="gitbuilder.ui.NewGeneralizationResultWindow()"><h4>Execution Result</h4></a></li> -->
<!-- 						<li><a href="#" onclick="gitbuilder.ui.NewTopologyTableWindow()"><h4>TopologyTable</h4></a></li> -->
<!-- 					</ul> -->
<!-- 				</div> -->
<!-- 				<button class="btn btn-default dropdown-toggle btn-lg">Save</button> -->
<!-- 			</div> -->
<!-- 		</div> -->
		<div id="bodyDiv" style="width: 100%; height: 100%;">
			<div class="cntGeneralWarp">
				<table class="tbDetail" style="border: 1px solid #eee; table-layout: fixed; height: 100%;">
					<thead>
						<tr>
							<th style="padding: 10px 10px 10px 10px; text-align: center" scope="col" align="center" colspan="2"><h4>Before Generalization</h4></th>
							<th style="padding: 10px 10px 10px 10px; text-align: center" scope="col" align="center" colspan="2"><h4>After Generalization</h4></th>
						</tr>
					</thead>
					<tbody id="mapBody">
						<tr>
							<td colspan="2"><div id="bfLayer"></div></td>
							<td colspan="2"><div id="afLayer"></div></td>
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