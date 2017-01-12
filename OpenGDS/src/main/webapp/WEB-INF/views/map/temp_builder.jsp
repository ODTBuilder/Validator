<%@ page contentType="text/html; charset=UTF-8"%>
<jsp:include page="/WEB-INF/views/common/common.jsp" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- masking -->
<title>Builder test</title>
<script src="${pageContext.request.contextPath}/resources/js/gitbuilder/commandline.js"></script>
<style>
/* 작은 기기들 (태블릿, 768px 이상) */
@media (min-width: @screen-sm-min) {}

/* 중간 기기들 (데스크탑, 992px 이상) */
@media (min-width: @screen-md-min) {}

/* 큰 기기들 (큰 데스크탑, 1200px 이상) */
@media (min-width: @screen-lg-min) {}
</style>
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
						<li><a href="#" onclick="gitbuilder.ui.NewVectorWindow()" title="Vector"><i class="fa fa-file-o fa-lg" aria-hidden="true"></i> New</a></li>
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
	<!-- 		<div class="row"> -->
	<!-- 			<div class="col-md-2" style="height: 90%; overflow-y: auto; margin: 0;"> -->
	<!-- 				<div class="panel panel-default "> -->
	<!-- 					<div class="panel-heading">Layer</div> -->
	<!-- 					<div class="panel-body"> -->
	<!-- 						<div id="builderLayer"></div> -->
	<!-- 					</div> -->
	<!-- 				</div> -->
	<!-- 			</div> -->
	<!-- 			<div class="col-md-10" style="height: 90%; margin: 0;"> -->
	<!-- 				<div class="panel panel-default"> -->
	<!-- 					<div class="panel-heading">Map</div> -->
	<!-- 					<div class="panel-body"> -->
	<!-- 						<div id="builderMap"></div> -->
	<!-- 					</div> -->
	<!-- 				</div> -->
	<!-- 			</div> -->

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
	<!-- 	<div id="builderLayer" style="float: left; width: 15%; height: 80%; overflow-y: auto; margin: 0;"></div> -->
	<!-- 	<div id="builderMap" style="float: left; width: 80%; height: 80%; margin: 0;"></div> -->
	<!-- 	<div id="autocomplete"> -->
	<!-- 		<div class="input-group"> -->
	<!-- 			<div class="input-group-addon">$</div> -->
	<!-- 			<input type="text" id="commandKeyword" class="form-control"> -->
	<!-- 		</div> -->
	<!-- 	</div> -->
	<!-- 	</div> -->

	<!-- 	<div id="autoComplete" class="form-group hocommand"> -->
	<!-- 		<div class="input-group"> -->
	<!-- 			<span class="input-group-addon">@</span> <input type="text" id="commandKeyword" class="form-control"> -->

	<!-- 		</div> -->
	<!-- 	</div> -->

	<!-- 	</div> -->
	<script type="text/javascript">
		var mapMethod = gitbuilder.method.map;
		var ui = gitbuilder.ui;
		mapMethod.setMapView(new ol.View({
			center : ol.proj.transform([ 71.433333, 51.166667 ], 'EPSG:4326', 'EPSG:3857'),
			zoom : 4,
			minZoom : 2,
			maxZoom : 20,
			projection : 'EPSG:3857'
		}));

		mapMethod.setMapControl([ new ol.control.ScaleLine({
			minWidth : 100,
			className : 'ol-scale-line'
		}), new ol.control.ZoomSlider(), new ol.control.Rotate({
			className : "ol-rotate"
		}) ]);

		mapMethod.setBuilderMap(new ol.Map({
			controls : ol.control.defaults({
				attributionOptions : /** @type {olx.control.AttributionOptions} */
				({
					collapsible : false
				})
			}).extend(mapMethod.getMapControl()),
			target : "builderMap",
			layers : [],
			view : mapMethod.getMapView()
		}));

		ui.LayerList("builderLayer");

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

		var obj2 = {
			layer : bing,
			name : "bing_",
			id : gitbuilder.method.layer.createLayerId(),
			type : "tile",
			cat : 1,
			edit : false,
			attrType : null
		};

		gitbuilder.method.layer.setLayerProperties(obj2);

		gitbuilder.method.layer.addLayerOnList(bing);

		var osm = new ol.layer.Tile({
			source : new ol.source.OSM()
		});

		var obj1 = {
			layer : osm,
			name : "osm_",
			id : gitbuilder.method.layer.createLayerId(),
			type : "tile",
			cat : 1,
			edit : false,
			attrType : null
		};

		gitbuilder.method.layer.setLayerProperties(obj1);

		gitbuilder.method.layer.addLayerOnList(osm);

		gitbuilder.method.layer.updateLayerList();

		function loadTemp() {
			var serverUrl = gitbuilder.method.getContextPath() + '/validator/validate.ajax';

			$.ajax({
				url : serverUrl,
				type : "POST",
				// type : "GET",
				// dataType : "json",
				contentType : "application/json; charset=UTF-8",
				cache : false,
				async : true,
				data : "param : temp",
				beforeSend : function() { // 호출전실행
					loadImageShow();
				},
				traditional : true,
				success : function(data, textStatus, jqXHR) {
					// gitbuilder.variable.errReport
					// console.log(JSON.stringify(data.DetailsReport));
					loadImageHide();
					// console.log(JSON.stringify(data.ErrorLayer));
					var format = new ol.format.GeoJSON().readFeatures(JSON.stringify(data));

					var source = new ol.source.Vector({
						features : format
					});

					var fill = new ol.style.Fill({
						color : gitbuilder.method.layer.convertHex(gitbuilder.method.layer.getRandomColor(), 50)
					});
					var stroke = new ol.style.Stroke({
						color : gitbuilder.method.layer.getRandomColor(),
						width : 1.25
					});
					var text = new ol.style.Text({});
					var styles = new ol.style.Style({
						image : new ol.style.Circle({
							fill : fill,
							stroke : stroke,
							radius : 5
						}),
						fill : fill,
						stroke : stroke,
						text : text
					});

					var layer = gitbuilder.method.layer.createVectorLayer(source, styles);
					var layerId = gitbuilder.method.layer.createLayerId();

					var obj = {
						layer : layer,
						name : "Temp SHP",
						id : "Temp SHP",
						type : "MultiPolygon",
						cat : 1,
						edit : true,
						attrType : null
					};
					gitbuilder.method.layer.setLayerProperties(obj);

					gitbuilder.method.layer.addLayerOnList(layer);
				},
				error : function(jqXHR, textStatus, errorThrown) {
					console.log(textStatus)
				}
			});
		};

		$(function() {
			$('#commandKeyword').commandline({
				map : gitbuilder.variable.map,
				source : gitbuilder.command.defaults(),
				create : function() {
					$('#ui-id-1').css('display', 'inline-block');
				},
				autoFocus : true
			});
		});
	</script>
</body>
</html>