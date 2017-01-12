<%@ page contentType="text/html; charset=UTF-8"%>
<jsp:include page="/WEB-INF/views/common/common2.jsp" />
<html>
<head>

<!-- masking -->
<title>Builder test</title>
<script src="${pageContext.request.contextPath}/resources/js/gitbuilder/newvector.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/gitbuilder/layerlist.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/gitbuilder/commandline.js"></script>
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
<!-- 					<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#builderLayer"> -->
<!-- 						<span class="sr-only">Toggle navigation</span>LAYER -->
<!-- 					</button> -->
				</div>
				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse" style="padding: 0;" id="builderNav">
					<ul class="nav navbar-nav">
						<li><a href="#" id="newVector" title="Vector"><i class="fa fa-file-o fa-lg" aria-hidden="true"></i> New</a></li>
						<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false" title="New Layer"><i class="fa fa-folder-open-o fa-lg" aria-hidden="true"></i> Open</a>
							<ul class="dropdown-menu" role="menu">
								<!-- 							<li><a href="#" onclick="gitbuilder.ui.NewVectorWindow()"><h4>Vector</h4></a></li> -->
								<li><a href="#" onclick="gitbuilder.ui.NewSHPWindow()">SHP</a></li>
								<li><a href="#" onclick="gitbuilder.ui.NewGeoserverLayerWindow('Q')">GeoServer</a></li>
							</ul></li>

						<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false" title="Save"><i class="fa fa-floppy-o fa-lg" aria-hidden="true"></i> Save</a>
							<ul class="dropdown-menu" role="menu">
								<li><a href="#">as a SHP</a></li>
								<li><a href="#">to Server</a></li>
							</ul></li>

						<li><a href="#" onclick="gitbuilder.ui.EditingWindow()" title="Edit"><i class="fa fa-pencil-square-o fa-lg" aria-hidden="true"></i> Edit</a></li>
						<li><a href="#" onclick="gitbuilder.ui.ValidationOptionWindow()" title="Validation"><i class="fa fa-search fa-lg" aria-hidden="true"></i> Validation</a></li>

						<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false" title="Validation Result"><i class="fa fa-list-alt fa-lg" aria-hidden="true"></i> Result</a>
							<ul class="dropdown-menu" role="menu">
								<li><a href="#" onclick="gitbuilder.ui.NavigatorWindow()">Error Navigator</a></li>
								<li><a href="#" onclick="gitbuilder.ui.OutputErrorReportWindow()">Error Report</a></li>
							</ul></li>

						<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false" title="ToolBox"><i class="fa fa-calculator fa-lg" aria-hidden="true"></i> ToolBox</a>
							<ul class="dropdown-menu" role="menu">
								<li><a href="#" onclick="gitbuilder.ui.ConvertCoordinates()">CRS Transformation</a></li>
								<li><a href="#" onclick="gitbuilder.ui.OutputOperationWindow()">Spatial Operation</a></li>
							</ul></li>

						<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false" title="History"><i class="fa fa-history fa-lg" aria-hidden="true"></i> History</a>
							<ul class="dropdown-menu" role="menu">
								<li><a href="#">History</a></li>
								<li role="presentation" class="divider"></li>
								<li><a href="#">Download History</a></li>
								<li><a href="#">Upload History</a></li>
							</ul></li>

						<li><a href="#" onclick="gitbuilder.ui.NewAddGeoserverWindow()" title="Server Management"><i class="fa fa-server fa-lg" aria-hidden="true"></i> Server</a></li>
						<li><a href="#" onclick="gitbuilder.ui.aboutBuilder()" title="Information"><i class="fa fa-info-circle fa-lg" aria-hidden="true"></i> Information</a></li>
					</ul>
				</div>
				<!-- /.navbar-collapse -->
			</div>
			<!-- /.container-fluid -->

		</nav>
	</div>


	<div class="container-fluid" style="padding: 0; margin: 0; overflow: hidden; background-color: #f8f8f8;">
		<div id="builderLayer" style="width: 15%; min-width:150px; height: 89.5%; float: left; border: 1px solid #e7e7e7; background-color: #f8f8f8;"></div>
		<div id="builderMap" style="width: 85%; height: 89.5%; float: left; border: 1px solid #e7e7e7; background-color: #f8f8f8; padding: 8px;"></div>
		<div id="commandWindow">
			<div id="consoleWindow">
				<kbd>console</kbd>
				<div style="width:100%; height:90%; position:relative; overflow:auto">
					<table></table>
				</div>
			</div>
			<div id="commandHistory">
				<kbd>history</kbd>
				<div style="width:100%; height:90%; position:relative; overflow:auto">
					<table></table>
				</div>
			</div>
			<div class="input-group">
				<div class="input-group-addon">$</div>
				<input type="text" id="commandKeyword" class="form-control">
			</div>
		</div>
	</div>

	<script type="text/javascript">
		var bing = new ol.layer.Tile({
			preload : Infinity,
			source : new ol.source.BingMaps({
				key : 'AtZS5HHiM9JIaF1cUX-x-zQT_97S8YkWkjxDowNNUGD1D8jWUtgVmdaxsiitNQbo',
				imagerySet : "AerialWithLabels"
			// use maxZoom 19 to see stretched tiles instead of the BingMaps
			// "no photos at this zoom level" tiles
			// maxZoom: 19
			})
		});

		bing.set("name", "bing");
		bing.setZIndex(1);
		
		var osm = new ol.layer.Tile({
			source : new ol.source.OSM()
		});

		osm.set("name", "osm");
		osm.setZIndex(2);

		var map = new ol.Map({
			target : 'builderMap',
			layers : [ bing, osm ],
			view : new ol.View({
				center : ol.proj.fromLonLat([ 37.41, 8.82 ]),
				zoom : 4
			})
		});
		
		$(function() {
			$("#builderLayer").layerlistable({
				map : map,
				stopSelect : function(event, layer) {
					// 		    		console.log(event);
					console.log(layer.selectedLayers);
				}
			});
			
			$("#newVector").newvector({
				map: map	
			});
			
			$('#commandKeyword').commandline({
				map : map,
				source : gitbuilder.command.defaults(),
				create : function() {
					$('#ui-id-1').css('display', 'inline-block');
				},
				autoFocus : true
			});
		});
		
		$(document).on('keydown', function (e) {
			
			// command 입력창 숨김, 나타내기
			if (e.which === 192) {
				
				e.stopPropagation();
				e.preventDefault();
				
				if ($('#commandWindow').css('display') === 'none') {
					$('#commandWindow').show('slow');
					$('#commandKeyword').focus();
					
				} else {
					//입력창을 숨겼을때는 입력창을 비활성화 시킴
					$('#commandKeyword').blur();
					$('#commandWindow').hide('slow');
				}
			}
		});
	</script>

</body>
</html>