$(document).ready(function() {
});

// ==========================================================================================================
// ==========================================================================================================
// ==========================================================================================================
// ==========================================================================================================
// ==========================================================================================================
// ==========================================================================================================
// ==========================================================================================================
/* 코딩시작 */
$(document).on("change","#allLChkbox",function () {
    $(".chklayer").prop('checked', $(this).prop('checked'));
});


function loadingLayerServerAjax() {
	var url = CONTEXT + "/geolayer/loadingServerAjax.ajax";
	var params = {};
	sendJsonRequest(url, params, loadingLayerServerAjaxCallback);
}

function loadingLayerServerAjaxCallback(result) {
	var geoserverList = result.serverList;
	$('#seserverlayer > option').remove();
//	if (geoserverList === null || geoserverList.length === 0) {
		$('#seserverlayer').append("<option id='defaultserver' value='Please select your server.'>Please select your server.</option>");
//	} else {
		for ( var obj in geoserverList) {
			$('#seserverlayer').append("<option value='" + geoserverList[obj].url + "'>" + geoserverList[obj].serverName + "</option>");
		}
//	}
}

function loadLayerAjax(serverUrl) {
	var url = CONTEXT + "/geolayer/loadLayerAjax.ajax";
	var params = {
		serverUrl : serverUrl
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

function geoLayerAdd() {
	var chklayer = new Array();
	$(":checkbox[name='chklayer']:checked").each(function() {
		var checkboxValue = $(this).attr('id');
		chklayer.push(checkboxValue.substring(14));
	});
	var layerAddList = new Array();
	for ( var obj in chklayer) { 
		var index = chklayer[obj];
		layerAddList.push(layerList[index]);
	}

	var url = CONTEXT + "/geoserver/proxy.ajax";
	
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
	
	$('#geoserverLayerWindow').modal('hide');
}

function proxyDoneCallback2(result) {
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

function getLayerList(){
    return layerList;
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
	url = "http://175.116.181.39:9990/geoserver/wfs?service=WFS&version=2.0.0&request=GetFeature&typeName="+layerVO.lName+"&outputFormat=application/json&srsname=EPSG:3857";
//	url = "http://175.116.181.39:9990/geoserver/wfs?service=WFS&version=1.1.0&request=GetFeature&typename=clip_water_pipe&outputFormat=application/json&srsname=EPSG:3857";
	return url;
}
