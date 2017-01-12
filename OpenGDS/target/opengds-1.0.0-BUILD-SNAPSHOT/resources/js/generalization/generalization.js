$(document).ready(function() {
    setGenBodyDivSize();
    setSeparateMap();
    gitbuilder.ui.NewGenLayerManageWindow('A');
//    $('#genLayerManageWindow').modal('hide');
    gitbuilder.ui.NewGeneralizationWindow('A');
    gitbuilder.ui.NewGeneralizationResultWindow('A');
    gitbuilder.ui.NewTopologyTableWindow('A');
//    $('#generalizationWindow').modal('hide');
});

var map1;
var map2;
var view;




function setGenBodyDivSize(){
    var bodyDiv = document.getElementById('bodyDiv');
    
    var width;
    var height;
    
    width = bodyDiv.offsetWidth;
    height = bodyDiv.offsetHeight;
    
    document.getElementById('bfLayer').style.height = height/100*76;
    document.getElementById('afLayer').style.height = height/100*76;
}


/**
 * 검수전, 검수후 맵에 대한 정보를 입력한다.
 * @author seulgi.lee
 * @date 2016. 02.
 * @param trgt1 - 검수전 지도 div ID
 * @param trgt2 - 검수후 지도 div ID
 * @returns 
 */
function setSeparateMap() {
	var tile = new ol.layer.Tile({
	    source : new ol.source.BingMaps({
        		key : 'AtZS5HHiM9JIaF1cUX-x-zQT_97S8YkWkjxDowNNUGD1D8jWUtgVmdaxsiitNQbo',
        		imagerySet : "Road"
	          })
	});

	view = new ol.View({
		center : [ 0, 0 ],
		zoom : 1,
		projection : "EPSG:3857"
	});

	map1 = new ol.Map({
		target : "bfLayer",
		layers : [ tile ],
		view : view
	});

	map2 = new ol.Map({
		target : "afLayer",
		layers : [ tile ],
		view : map1.getView()
	});
}

var layerAddList;
var chklayer;
function geoGenLayerAdd() {
    layerAddList = new Array();
    chklayer = new Array();
    $(":checkbox[name='chklayer']:checked").each(function(){
	var checkboxValue = $(this).attr('id');
	chklayer.push(checkboxValue.substring(14));
    });

    if (chklayer.length > 1){
	alertPopup('Warning', 'Please select only one layer.');
    }
    else if(chklayer.length ==0){
	alertPopup('Warning', 'Please select layer.');
    }
    else {
	var layerList = getLayerList();
	for ( var obj in chklayer) {
	    var index = chklayer[obj];
	    layerAddList.push(layerList[index]);
	}
	var geomType = layerAddList[0].geomType;
	$('#genOption').empty();
	if (geomType == 'MultiLineString' || geomType == "LineString") {
	    for(var obj in layerAddList) {
		$('#genLayerManageTableBody')
			.append(
				"<tr>"
					+ "<td style='vertical-align: middle;'><div class='checkbox checkbox-primary checkbox-single' style='margin-top: 0px;margin-bottom: 0px;'><input type='checkbox' name='chklayer' id='singleCheckbox"
					+ obj
					+ "'><label></label></div></td>"
					+ "<td style='vertical-align: middle;'>"
					+ layerAddList[obj].geomType
					+ "</td>"
					+ "<td style='vertical-align: middle;'>"
					+ layerAddList[obj].lName
					+ "</td>"
					+ "<td><button type='button' class='btn btn-primary' id='detaillayerbtn"
					+ obj + "'>Detail Info</button></td>"
					+ "</tr>");

		if (layerAddList[obj].geomType == 'MultiPolygon' || layerAddList[obj].geomType == 'Polygon') {
		    $('#genOption')
			    .append(
				    "<h3>"
					    + layerAddList[obj].lName
					    + "</h3>"
					    + "<div class='form-group'>"
					    + "<label class='col-sm-2 control-label'>Elimination(area)</label>"
					    + "<div class='col-sm-10'>"
					    + "<input type='text' class='form-control' id='elival"
					    + obj
					    + "' placeholder='Area Value'>"
					    + "</div>" + "</div>");
		} else if (layerAddList[obj].geomType == 'MultiLineString' || layerAddList[obj].geomType == 'LineString') {
		    $('#genOption')
			    .append(
				    "<h3>"+ layerAddList[obj].lName+ "</h3>"
					    + "<div class='form-group'>"
					    + "<label class='col-sm-3 control-label'>Simplification(Distance)</label>"
					    + "<div class='col-sm-9'>"
					    + "<input type='text' class='form-control' id='simdistance"+ obj+ "' placeholder='km'>"
					    + "</div>"
					    + "</div>"
					    + "<div class='form-group'>"
					    + "<label class='col-sm-3 control-label'>Elimination(Length)</label>"
					    + "<div class='col-sm-9'>"
					    + "<input type='text' class='form-control' id='elival"+ obj + "' placeholder='km'>"
					    + "</div>" + "</div>");
		} else {
		    $('#genOption').append(
			    "<h3>" + layerAddList[obj].lName + "</h3>"
				    + "<h2>해당 레이어는 일반화 할 수 없습니다.</h2>");
		}
	    }
	    $('#geoserverLayerWindow').modal('hide');

	    
	    //기존 레이어 지우기
	    gitbuilder.method.layer.allRemoveImageLayer(map1);
	    gitbuilder.method.layer.allRemoveImageLayer(map2);
	    
	    
	    for ( var obj in layerAddList) {
		var layer = layerAddList[obj];

		var url = CONTEXT + "/geoserver/proxy.ajax";
		var curl = createURL(layer);
		var params = {
		    url : curl
		};
		sendJsonRequest(url, params, proxyDoneCallback);
	    }
	    // $('#genLayerManageTableBody > tr').remove();
	} else {
	    alertPopup('Warning','Current generalizations only support LineString and MultiLineString.');
	}
    }
}


var attInfo;
//var maxFeature;
var layerName;

/**
* 프록시 서버를 통해 전달받은 레이어를 표시한다.
* @author seulgi.lee
* @date 2016. 02.
* @param result - 레이어 정보
* @returns 
*/

var addGeojsonList= new Array();

function proxyDoneCallback(result){
//	loadImageHide();
    	addGeojsonList= new Array();
	var format = new ol.format.GeoJSON().readFeatures(result,{
	    dataProjection : 'EPSG:4326',
	    featureProjection : 'EPSG:3857'
	});
	
	var source = new ol.source.Vector({
		features : format
	});
	addGeojsonList.push(new ol.format.GeoJSON().writeFeatures(source.getFeatures()));
	var style = new ol.style.Style({
		fill : new ol.style.Fill({
			color : 'rgba(0, 0, 0, 0)'
		}),
		stroke : new ol.style.Stroke({
			color : 'rgba(0, 108, 255, 1)',
			width : 2
		}),
		image : new ol.style.Circle({
			radius : 7,
			fill : new ol.style.Fill({
				color : 'rgba(0, 0, 0, 0)',
			})
		})
	});
	
	var layer =  new ol.layer.Image({
            source: new ol.source.ImageVector({
                source: source,
                style: style
              })
            });
	
	layer.setZIndex(2);
	map1.addLayer(layer);
	
	view.fit(source.getExtent(), map1.getSize());
}

/**
* 레이어 정보를 통해 요청 주소를 생성한다.
* @author seulgi.lee
* @date 2016. 02.
* @param result - 레이어 정보
* @returns 
*/
function createURL(layerVO){
	var url = "";
	url = "http://175.116.181.39:9990/geoserver/wfs?service=WFS&version=1.1.0&request=GetFeature&typeName="+layerVO.lName+"&outputFormat=application/json&srsname=EPSG:4326";
//	url = "http://175.116.181.39:9990/geoserver/wfs?service=WFS&version=1.1.0&request=GetFeature&typename=clip_water_pipe&outputFormat=application/json&srsname=EPSG:3857";
	return url;
}

/**
* 속성정보를 반환한다.
* @author seulgi.lee
* @date 2016. 02.
* @param result - 레이어 정보
* @returns 
*/
function getAttInfo(){
	return attInfo;
} 





function generalizationRun() {
    var layerInfos = new Array();
    var validCase = {
	    elival0 : {type : "text", name : "Elimination Option", required : true},
	    simdistance0 : {type : "text", name : "Simplification Option", required : true}
    };
    if(validation(validCase)){
        for ( var obj in chklayer) {
        	var layerJson = {};
        	var index = chklayer[obj];
        	var layerInfo = layerList[index];
        	var geojson = addGeojsonList[obj];
        	
        	var elival = $('#elival' + obj + '').val();
        	var simval = $('#simdistance' + obj + '').val();
        	if(isNumber(elival) && isNumber(simval) && elival!=0 && simval!=0){
        	    layerJson = {
            		geojson : geojson,
            		type : layerInfo.geomType,
            		layerName : layerInfo.lName,
            		option : {
            		    elival : elival,
            		    simval : simval
            		}
            	    };
            	var url = CONTEXT +"/generalization/generalization.ajax";
            	var params ={
            			json : layerJson
            	};
            	sendObjectRequest(url, params, generalizationRunCallback);
        	}
        	else
        	    alertPopup('Warning','Please enter a number excluding 0');
        }
    }
}

function generalizationRunCallback(result){
    
    //결과레이어 초기화
    gitbuilder.method.layer.allRemoveImageLayer(map2);
    
    var format = new ol.format.GeoJSON().readFeatures(result.geojson);
	var source = new ol.source.Vector({
		features : format
	});

	var style = new ol.style.Style({
		fill : new ol.style.Fill({
			color : 'rgba(0, 0, 0, 0)'
		}),
		stroke : new ol.style.Stroke({
			color : 'rgba(0, 108, 255, 1)',
			width : 2
		}),
		image : new ol.style.Circle({
			radius : 7,
			fill : new ol.style.Fill({
				color : 'rgba(0, 0, 0, 0)',
			})
		})
	});
	
	var layer =  new ol.layer.Image({
        source: new ol.source.ImageVector({
            source: source,
            style: style
          })
        });
	
	layer.setZIndex(2);
	map2.addLayer(layer);
	
	
	$('#resultbtn').attr('class','btn btn-primary');
	var layerName = result.layerName;
	var report = result.report;
	var afResultNum = report.afResultNum;
	var preResultNum = report.preResultNum;
//	 $('#genResult').remove();
	$('#genResult').empty();
	 $('#genResult').append(
		 "<h3>" +layerName+"</h3>"+
		 '<div class="tbList">'+
			'<table class="table table-hover table-bordered text-center">'+
			'<colgroup><col width="30%"/><col width="35%"/><col width="35%"/>'+
			'<thead>'+
			'<tr style="background-color: #e6e6e6;">'+
			'<th style="text-align: center;"><strong>Type</strong></th>'+
			'<th style="text-align: center;"><strong>Before</strong></th>'+
			'<th style="text-align: center;"><strong>After</strong></th>'+
			'</tr>'+
			'</thead>'+
			'<tbody>'+
			 "<tr>" +
		    		"<td style='vertical-align: middle;'>Number of Objects</td>"+
		    		"<td style='vertical-align: middle;'>" + preResultNum.ENTITYNUM + "</td>" +
		    		"<td style='vertical-align: middle;'>" + afResultNum.ENTITYNUM + "</td>" +
		    	"</tr>" +
		    	 "<tr>" +
		    		"<td style='vertical-align: middle;'>Number of Points</td>"+
		    		"<td style='vertical-align: middle;'>" + preResultNum.POINTNUM + "</td>" +
		    		"<td style='vertical-align: middle;'>" + afResultNum.POINTNUM + "</td>" +
		    	"</tr>" +
			'</tbody>'+
			'</table>'+
			'</div>'
	 );
	 
	 
	 
	 gitbuilder.variable.topologyTable = result.topologyTable;
	 $('#topologyTable').DataTable().destroy();
	 $('#topologyTable').empty();
	 $('#topologyTable').DataTable({
		"autoWidth" : false,
		data : gitbuilder.variable.topologyTable,
		"columnDefs" : [ {
			"title" : "Object ID",
			"width" : "15%",
			"targets" : 0
		}, {
			"title" : "First Point",
			"width" : "40%",
			"targets" : 1
		}, {
			"title" : "Last Point",
			"width" : "40%",
			"targets" : 2
		}, {
			"title" : "Length(km)/area(m2)",
			"width" : "10%",
			"targets" : 3
		} ],
		"columns" : [ {
			"data" : "objID"
		}, {
			"data" : "firstObjs"
		}, {
			"data" : "lastObjs"
		}, {
			"data" : "alValue"
		} ]
	});
	 
	 /*$('#topologyTableBody > tr').remove();
	 for(var obj in topologyTable){
	     $('#topologyTableBody')
		.append(
				"<tr>"
						+ "<td style='vertical-align: middle;'>" + topologyTable[obj].objID + "</td>"
						+ "<td style='vertical-align: middle;'>" + firstObjs + "</td>"
						+ "<td style='vertical-align: middle;'>" + lastObjs + "</td>"
						+ "<td style='vertical-align: middle;'>" + topologyTable[obj].alValue + "</td>"
				+ "</tr>"				
		);
	 }*/
	 
	
	view.fit(source.getExtent(), map2.getSize());
}









////////////////////////////////
// gitbuilder_2 추후 적용
// ol.layer.Tile, ol.layer.Image, ol.layer.Vector
gitbuilder.method.layer.allRemoveVectorLayer = function allRemoveVectorLayer(olMap) {
    var layers = olMap.getLayers();
    
    var removeLayers = new Array();
    
    
    for(var layer in layers.getArray()){
	if(layer instanceof ol.layer.Vector){
	    removeLayers.push(layer);
	}
    }
    
    for(var layer in removeLayers){
	olMap.removeLayer(layer);
    }
}

gitbuilder.method.layer.allRemoveImageLayer = function allRemoveImageLayer(olMap) {
    var layerCollection = olMap.getLayers();
    var layers = layerCollection.getArray();
    
    var removeLayers = new Array();
    
    
    for(var index in layers){
	if(layers[index] instanceof ol.layer.Image){
	    removeLayers.push(layers[index]);
	}
    }
    
    for(var index in removeLayers){
	olMap.removeLayer(removeLayers[index]);
    }
}

gitbuilder.method.layer.allRemoveTileLayer = function allRemoveTileLayer(olMap) {
    var layers = olMap.getLayers();
    
    var removeLayers = new Array();
    
    
    for(var layer in layers.getArray()){
	if(layer instanceof ol.layer.Tile){
	    removeLayers.push(layer);
	}
    }
    
    for(var layer in removeLayers){
	olMap.removeLayer(layer);
    }
}

gitbuilder.method.layer.refreshMap = function refreshMap() {
    gitbuilder.method.layer.allRemoveImageLayer(map1);
    gitbuilder.method.layer.allRemoveImageLayer(map2);
}
























