<%@ page contentType="text/html; charset=UTF-8"%>
<jsp:include page="/WEB-INF/views/common/common2.jsp" />
<html>
<head>

<!-- masking -->
<title>Builder test</title>
<script src="${pageContext.request.contextPath}/resources/js/gitbuilder/layerlist.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/gitbuilder/layerSelectable.js"></script>
</head>
<body>

	<div id="header">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
	</div>

	<div id="builderHeader navbar navbar-default" style="width: 100%; height: 6%; background-color: #f8f8f8;">

		<div class="row" style="margin-left: 0px !important; margin-right: 0px !important;">

			<div class="col-md-10">
				<button class="btn btn-default" onclick="gitbuilder.ui.NewSHPWindow()">SHP</button>
				<button class="btn btn-default" onclick="gitbuilder.ui.NewVectorWindow()">Vector</button>
				<button class="btn btn-default" onclick="gitbuilder.ui.NewGeoserverLayerWindow('Q')">GeoServer</button>
				<button class="btn btn-default" onclick="gitbuilder.ui.OpenEditingWindow()">Edit</button>
				<button class="btn btn-default" onclick="gitbuilder.ui.ConvertCoordinates()">CRS</button>
				<button class="btn btn-default" onclick="gitbuilder.ui.NavigatorWindow()">Error Navigator</button>
				<button class="btn btn-default" onclick="gitbuilder.ui.OutputOperationWindow()">Calculation</button>
				<button class="btn btn-default" onclick="gitbuilder.ui.ValidationOptionWindow()">Validation</button>
				<button class="btn btn-default" onclick="gitbuilder.ui.NewAddGeoserverWindow()">Server Management</button>
				<button class="btn btn-default" onclick="gitbuilder.ui.OutputErrorReportWindow()">Error Report</button>
				<button class="btn btn-default">Save</button>
				<button class="btn btn-default" onclick="loadTemp()">Temp</button>
				<!-- 				<button class="btn btn-default" onclick="gitbuilder.method.edit.EditToolTip()">EditToolTip</button> -->
			</div>

			<div class="col-md-2">
				<div class="input-group">
					<input type="text" class="form-control" placeholder="Search for..."> <span class="input-group-btn">
						<button class="btn btn-default" type="button">Search</button>
					</span>
				</div>
			</div>

		</div>

	</div>

	<div>
		<div id="builderLayer" style="float: left; width: 13%; height: 88%; background-color: #f8f8f8;">
			<ul id="selectable">
			</ul>
			<ul id="selectable1">
			</ul>
		</div>
		<div id="builderMap" style="float: left; width: 87%; height: 88%; background-color: #e9e9e9;"></div>
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
			$("#selectable").layerlistable({
				map : map,
				stopSelect : function(event, layer) {
					// 		    		console.log(event);
					console.log(layer.selectedLayers);
				}
			});
		});
		
	</script>

</body>
</html>