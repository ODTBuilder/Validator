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
<script src="${ctx}/resources/js/bootstrap/js/bootstrap-modalmanager.js"></script>
<script type="text/javascript">
var CONTEXT = "${pageContext.request.contextPath}";
</script>

<%-- 부트스트랩 --%>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/bootstrap/css/bootstrap.min.css">

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
/* HTML5 display-role reset for older browsers */
article, aside, details, figcaption, figure, footer, header, hgroup,
	menu, nav, section {
	display: block;
}

body {
	line-height: 1;
}

ol, ul {
	list-style: none;
}

blockquote, q {
	quotes: none;
}

blockquote:before, blockquote:after, q:before, q:after {
	content: '';
	content: none;
}

table {
	border-collapse: collapse;
	border-spacing: 0;
}
</style>

<title>Builder test</title>
</head>
<body>
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
			<button class="btn btn-default" onclick="gitbuilder.ui.NewAddGeoserverWindow()">Add GeoServer</button>
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

	<div>
		<div id="builderLayer" style="float: left; width: 13%; height: 92%; background-color: #f1f1f1;"></div>
		<div id="builderMap" style="float: left; width: 87%; height: 92%; background-color: #cccccc;"></div>
	</div>

	<script type="text/javascript">
		var mapMethod = gitbuilder.method.map;
		var ui = gitbuilder.ui;
		mapMethod.setMapView(new ol.View({
			center : ol.proj.transform([ 71.433333, 51.166667 ], 'EPSG:4326', 'EPSG:3857'),
			zoom : 4,
			minZoom : 2,
			maxZoom : 19,
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

		gitbuilder.method.layer.addLayerOnList(bing)

		gitbuilder.method.layer.updateLayerList();
	</script>

</body>
</html>