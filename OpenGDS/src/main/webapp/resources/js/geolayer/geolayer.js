$(document).ready(function() {
});

//레이어 전체선택
$(document).on("change","#allLChkbox",function () {
    $(".chklayer").prop('checked', $(this).prop('checked'));
});



function loadingLayerServerAjax(){
	var url = CONTEXT + "/geoserver/loadingServerAjax.ajax";
	var params = {};
	sendJsonRequest(url, params, loadingLayerServerAjaxCallback);
}

function loadingLayerServerAjaxCallback(result) {
	var geoserverList = result.serverList;
	$('#seserverlayer > option').remove();
		$('#seserverlayer').append("<option id='defaultserver' value='Please select your server.'>Please select your server.</option>");
		for ( var obj in geoserverList) {
			$('#seserverlayer').append("<option value='" + geoserverList[obj].url + "'>" + geoserverList[obj].serverName + "</option>");
		}
}

function loadLayerAjax() {
    	var serverName = $("#seserverlayer option:selected").val();
	var url = CONTEXT + "/geolayer/loadLayerAjax.ajax";
	var params = {
		serverName : serverName
	};
	$('#layerloadimage').show();
	sendObjectRequest(url, params, loadLayerAjaxCallback);
}

var layerList;
function loadLayerAjaxCallback(result) {
    	$('#layerloadimage').hide();
	layerList = result.layerList;
	$('#addGeoLayerTableBody > tr').remove();
	for ( var obj in layerList) {
		$('#addGeoLayerTableBody')
				.append(
						"<tr>"
								+ "<td style='vertical-align: middle;'><div class='checkbox checkbox-primary checkbox-single' style='margin-top: 0px;margin-bottom: 0px;'><input class='chklayer' type='checkbox' name='chklayer' id='singleCheckbox"
								+ obj + "'><label></label></div></td>" + "<td style='vertical-align: middle;'>" + layerList[obj].geomType + "</td>"
								+ "<td style='vertical-align: middle;'>" + layerList[obj].lName + "</td>"
								+ "<td><button type='button' class='btn btn-primary' id='detaillayerbtn" + obj + "'>Detail Info</button></td>" + "</tr>");
	}
}


var chklayer;
function geoLayerAdd() {
    	var chkUrl = CONTEXT + "/geoserver/serverChkAjax.ajax";
	var serverURL = $("#seserverlayer option:selected").val();
	var params = {
		serverURL : serverURL
	};
	
	chklayer= new Array();
	sendJsonRequest(chkUrl, params, serverChkAjaxCallback);

        
	function serverChkAjaxCallback(result) {
	var flag = result.flag;
	var dtGeoserver = result.DTGeoserver;
	if (!flag) {
	    alertPopup("Warning", "The server information that is incorrect.");
	} 
	else {
	    
	    $(":checkbox[name='chklayer']:checked").each(function() {
		var checkboxValue = $(this).attr('id');
		chklayer.push(checkboxValue.substring(14));
	    });

	    if (chklayer.length != 0) {
		for ( var obj in chklayer) { 
		    var index = chklayer[obj];
		    createDTGeoLayerToOlLayer(dtGeoserver, layerList[index]);
    		}
    	    }
	    else{
		    alertPopup("Warning","Please select the Layer");
		}
		
    	}
    }

	
	//프록시서버로 요청했을시
	/*var url = CONTEXT + "/geoserver/proxy.ajax";
	
	// undo 위한 data 배열 -김호철-
	var layerArr = [];
	
	// layer를 추가하기전 숨겨진 layer와 feature를 삭제
	gitbuilder.variable.undoRedo.resetRedos();
	
	for (var i = 0; i < layerAddList.length; i++) {
		var layerVO = layerAddList[i];
		var curl = createURL(layerAddList[i]);
		var params = {
			url : curl
		};
		
		$.ajax({
			url : fixUrlPath(url),
			async : false,
			// type : "POST",
			type : "GET",
			contentType : "application/json; charset=UTF-8",
			dataType : "json",
			cache : false,
			data : params,
			beforeSend : function() { // 호출전실행

			},
			traditional : true,
			success : function(data, textStatus, jqXHR) {
	
				var layer = proxyDoneCallback2(data);

				var layerId = gitbuilder.method.layer.createLayerId();
				var obj = {
					layer : layer,
					name : layerVO.lName,
					id : layerVO.lName,
					type : layerVO.geomType,
					cat : 1,
					edit : true,
					attrType : layerVO.attInfo
				};
				// 배열에 layer 정보를 저장 -김호철-
				layerArr.push(layer);
				
				gitbuilder.method.layer.setLayerProperties(obj);

				gitbuilder.method.layer.addLayerOnList(layer);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log(textStatus)
			}
		});

	}
	// undo를 위한 data set -김호철-
	gitbuilder.method.SetUndoData('layerHide', layerArr, null);
	$('#geoserverLayerWindow').modal('hide');*/
}

function createDTGeoLayerToOlLayer(dtGeoserver, dtGeolayer){
    var olLayer;

        if (dtGeoserver != null && dtGeolayer != null) {
            var source = new ol.source.Vector(
		{
		    loader : function(extent, resolution, projection) {
			var url = dtGeoserver.url
				+ '/ows?service=WFS&version=1.0.0&request=GetFeature&typeName='
				+ dtGeolayer.wsName
				+ '%3A'
				+ dtGeolayer.lName
				+ '&outputformat=text%2Fjavascript&format_options=callback:??&srsname=EPSG:3857';
			$.ajax({
			    url : url,
			    dataType : 'jsonp',
			    beforeSend : function(){ //호출전실행
				loadImageShow();
			    }
			}).then(
				function(response, textStatus, jqXHR) {
				    var format = new ol.format.GeoJSON();
			            var features = format.readFeatures(response,
			                    {featureProjection: projection});
			            source.addFeatures(features);
			            loadImageHide();
				});
		    },
		});
	    
	    olLayer = gitbuilder.method.layer.createVectorLayer(source,null);

	    var layerArr = [];

	    

	    var layerId = gitbuilder.method.layer.createLayerId();

	    var obj = {
		layer : olLayer,
		name : dtGeolayer.lName,
		id : dtGeolayer.lName,
		type : dtGeolayer.geomType,
		cat : 1,
		edit : true,
		attrType : dtGeolayer.attInfo
	    };
	    // 배열에 layer 정보를 저장 -김호철-
	    layerArr.push(olLayer);

	    gitbuilder.method.layer.setLayerProperties(obj);

	    gitbuilder.method.layer.addLayerOnList(olLayer);

	    gitbuilder.method.layer.updateLayerList();

	    
	    
	    // undo를 위한 data set -김호철-
//	    gitbuilder.command.SetUndoData('layerHide',layerArr, null);
	    $('#geoserverLayerWindow').modal('hide');
    }
    else{
	alertPopup("Warning","Invalid request. Please try again.");
    }
}



function getLayerList(){
    return layerList;
}

/*
*//**
* 레이어 정보를 통해 요청 주소를 생성한다.
* @author seulgi.lee
* @date 2016. 02.
* @param result - 레이어 정보
* @returns 
*//*
function createURL(layerVO){
	var url = "";
	url = "http://175.116.181.39:9990/geoserver/wfs?service=WFS&version=2.0.0&request=GetFeature&typeName="+layerVO.lName+"&outputFormat=application/json&srsname=EPSG:3857";
//	url = "http://175.116.181.39:9990/geoserver/wfs?service=WFS&version=1.1.0&request=GetFeature&typename=clip_water_pipe&outputFormat=application/json&srsname=EPSG:3857";
	return url;
}
*/




/*
var geolayer;
var callNum;
function jsonpRequest(dtGeoserver, dtGeolayer){
    var requestURL = '';
    geolayer = dtGeolayer;
    callNum=0;
    
    // layer를 추가하기전 숨겨진 layer와 feature를 삭제
    gitbuilder.variable.undoRedo.resetRedos();
 
    if(dtGeoserver!=null&&dtGeolayer!=null){
	requestURL = dtGeoserver.url+'/ows?service=WFS&version=1.0.0&request=GetFeature&typeName='+dtGeolayer.wsName+'%3A'+dtGeolayer.lName+'&outputformat=text%2Fjavascript&format_options=callback:call&srsname=EPSG:3857';
	$.ajax({
		url : requestURL,
		async : false,
		cache : false,
		dataType : 'jsonp',
		beforeSend : function(){ //호출전실행
			loadImageShow();
		}
	});
    }
    else{
	alertPopup("Warning","Invalid request. Please try again.");
    }
} 

function convertGeojsonToLayer(result) {
	// console.log(JSON.stringify(result));
	var format = new ol.format.GeoJSON().readFeatures(result);

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
	var style = new ol.style.Style({
		image : new ol.style.Circle({
			fill : fill,
			stroke : stroke,
			radius : 5
		}),
		fill : fill,
		stroke : stroke,
		text : text
	});

	var layer = gitbuilder.method.layer.createVectorLayer(source, style);
	return layer;
}


function call(result){
	loadImageHide();
	// undo 위한 data 배열 -김호철-
	var layerArr = [];

	var layer = convertGeojsonToLayer(result);

	var layerId = gitbuilder.method.layer.createLayerId();
	
	
	var obj = {
		layer : layer,
		name : layerList[chklayer[callNum]].lName,
		id : layerId,
		type : layerList[chklayer[callNum]].geomType,
		cat : 1,
		edit : true,
		attrType : layerList[chklayer[callNum]].attInfo
	};
	// 배열에 layer 정보를 저장 -김호철-
	layerArr.push(layer);
	
	gitbuilder.method.layer.setLayerProperties(obj);

	gitbuilder.method.layer.addLayerOnList(layer);
	
	gitbuilder.method.layer.updateLayerList();
	
	// undo를 위한 data set -김호철-
	gitbuilder.method.SetUndoData('layerHide', layerArr, null);
	$('#geoserverLayerWindow').modal('hide');
	
	callNum = callNum+1;
}
*/