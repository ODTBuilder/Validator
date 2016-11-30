<%@ page contentType="text/html; charset=UTF-8"%>
<jsp:include page="/WEB-INF/views/common/common.jsp" />
<html>
<head>

<!-- masking -->
<title>Builder test</title>

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
				<button class="btn btn-default" onclick="gitbuilder.ui.EditingWindow()">Edit</button>
				<button class="btn btn-default" onclick="gitbuilder.ui.ConvertCoordinates()">CRS</button>
				<button class="btn btn-default" onclick="gitbuilder.ui.NavigatorWindow()">Error Navigator</button>
				<button class="btn btn-default" onclick="gitbuilder.ui.OutputOperationWindow()">Calculation</button>
				<button class="btn btn-default" onclick="gitbuilder.ui.ValidationOptionWindow()">Q/A</button>
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
		<div id="builderLayer" style="float: left; width: 13%; height: 88%; background-color: #f8f8f8;"></div>
		<div id="builderMap" style="float: left; width: 87%; height: 88%; background-color: #e9e9e9;"></div>
		<form action="javascript:gitbuilder.command.CheckCommand()">
			<div id='autocomplete'>
				<input id="commandKeyword" class="form-control">
			</div>
		</form>
	</div>
	
	<div id="autoComplete" class="form-group hocommand">
		<div class="input-group">
			<span class="input-group-addon">@</span>
			<input type="text" id="commandKeyword" class="form-control">
			
		</div>
	</div>
	
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
		
		function loadTemp (){
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
	</script>

</body>
</html>