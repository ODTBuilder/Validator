$(document).ready(function() {
<<<<<<< HEAD
});
//==========================================================================================================
// ==========================================================================================================
// ==========================================================================================================
// ==========================================================================================================
// ==========================================================================================================
// ==========================================================================================================
// ==========================================================================================================
/*코딩시작*/
//check 관련 스크립트
$(document).on("change","#allSChkbox",function () {
    $(".chkserver").prop('checked', $(this).prop('checked'));
});



function loadingServerAjax(){
    
    var url=CONTEXT+"/geoserver/loadingServerAjax.ajax";
	var params={
	};
	sendObjectRequest(url, params, loadingServerAjaxCallback);
}

var geoserverList = null;
function loadingServerAjaxCallback(result){
    geoserverList = result.serverList;
    $('#addGeoTableBody > tr').remove();
    for(var obj in geoserverList){
	$('#addGeoTableBody').append(
		"<tr>" +
		"<td style='vertical-align: middle;'><div class='checkbox checkbox-primary checkbox-single' style='margin-top: 0px;margin-bottom: 0px;'><input class='chkserver' type='checkbox' id='singleCheckbox" + obj + "' name='chkserver'><label></label></div></td>" +
		"<td style='vertical-align: middle;'>" + geoserverList[obj].serverName + "</td>" +
		"<td><button type='button' class='btn btn-primary' id='detailserverbtn"+obj+"'>Detail Info</button></td>" +
		"</tr>");
    }
}


function addServerAjax(){
    var serverCase = {
	    serverName          : {type : "text", name : "서버명을", required : true},
	    serverURL         : {type : "text", name : "URL을", required : true},
	    serverID        : {type : "text", name : "아이디를", required : true},
	    serverPW        : {type : "text", name : "비밀번호를", required : true},
    };
    if( validation(serverCase) ){
	var url=CONTEXT+"/geoserver/geoserverAddLoadAjax.ajax";
	var params={
		serverName : $('#serverName').val(),
		serverURL : $('#serverURL').val(),
		serverID : $('#serverID').val(),
		serverPW : $('#serverPW').val()
	};
	sendObjectRequest(url, params, addServerAjaxCallback);
    }
}


function addServerAjaxCallback(result){
    geoserverList = result.serverList;
    var flag = result.flag;
    if(flag){
    $('#addGeoTableBody > tr').remove();
        for(var obj in geoserverList){
    	$('#addGeoTableBody').append(
    		"<tr>" +
    		"<td style='vertical-align: middle;'><div class='checkbox checkbox-primary checkbox-single' style='margin-top: 0px;margin-bottom: 0px;'><input type='checkbox' class='chkserver' id='singleCheckbox" + obj + "' name='chkserver'><label></label></div></td>" +
    		"<td style='vertical-align: middle;'>" + geoserverList[obj].serverName + "</td>" +
    		"<td><button type='button' class='btn btn-primary' id='detailserverbtn"+obj+"'>Detail Info</button></td>" +
    		"</tr>");
        }
    }
    else{
	alertPopup("Warning","The server information that is incorrect.");
    }
    closeModal('addServerDialog');
}

function removeServerAjax(){
    var chkServer = new Array();
	$(":checkbox[name='chkserver']:checked").each(function() {
		var checkboxValue = $(this).attr('id');
		chkServer.push(checkboxValue.substring(14));
	});
	var serverAddList = new Array();
	for ( var obj in chkServer) { 
		var index = chkServer[obj];
		serverAddList.push(geoserverList[index]);
	}
	
	var url = CONTEXT + "/geoserver/geoserverRemoveLoadAjax.ajax";
	var params={
		serverList : serverAddList,
	};
	sendObjectRequest(url, params, removeServerAjaxCallback);
}

function removeServerAjaxCallback(result){
    geoserverList = result.serverList;
    var flag = result.flag;
    if(flag){
	 $('#addGeoTableBody > tr').remove();
	        for(var obj in geoserverList){
	    	$('#addGeoTableBody').append(
	    		"<tr>" +
	    		"<td style='vertical-align: middle;'><div class='checkbox checkbox-primary checkbox-single' style='margin-top: 0px;margin-bottom: 0px;'><input type='checkbox' id='singleCheckbox" + obj + "' name='chkserver'><label></label></div></td>" +
	    		"<td style='vertical-align: middle;'>" + geoserverList[obj].serverName + "</td>" +
	    		"<td><button type='button' class='btn btn-primary' id='detailserverbtn"+obj+"'>Detail Info</button></td>" +
	    		"</tr>");
	        }
    }
    else{
	alertPopup("Warning","This is a system error. Please try again.");
    }
}


=======
	layerLoad();
});
var layerInfo;
var attInfo;
//var maxFeature;
var layerName;

/**
* 승인된 레이어를 불러온다.
* @author seulgi.lee
* @date 2016. 02.
*/
function layerLoad(){
	var rid = document.getElementById("rid").value;
	var tname = document.getElementById("tname").value;
	var url = CONTEXT +"/layer/layerLoadAjax.ajax";
	var params = {
			rid : rid,
			tname : tname
	};
	sendJsonRequest(url, params, layerLoadDonCallback);
}

/**
* 신청된 레이어를 불러온다.
* @author seulgi.lee
* @date 2016. 02.
* @param result - 레이어 정보
* @returns 
*/
function layerLoadDonCallback(result){
//	alert(JSON.stringify(result));
	layerInfo = result.layerVO;
	attInfo = layerInfo.attInfo;
//	alert(JSON.stringify(attInfo));
	var layerVO = result.layerVO;
	var geoJSON = result.geoJSON;
	
	var format = new ol.format.GeoJSON().readFeatures(geoJSON, {dataProjection:"EPSG:4326", featureProjection:"EPSG:3857"});
	
	var source = new ol.source.Vector({
		features : format
	});
	

	var newStyle = new ol.style.Style({
		fill : new ol.style.Fill({
			color : 'rgba(0, 255, 0, 0.7)'
		}),
		stroke : new ol.style.Stroke({
			color : 'rgba(0, 255, 0, 0.7)',
			width : 2
		}),
		image : new ol.style.Circle({
			radius : 7,
			fill : new ol.style.Fill({
				color : 'rgba(0, 255, 0, 0.7)',
			})
		})
	});
	
	layerName=layerInfo.lName;
	var newLayer = createVectorLayer(source, newStyle);
	newLayer = setLayerProperties(newLayer, layerInfo.lName+"_new", layerInfo.lName+"_new", layerInfo.geomType.toLowerCase(), "new", 1, true, attInfo);
	addLayerOnList(newLayer);

//	alert(JSON.stringify(geoJSON));
	var url = CONTEXT +"/geoserver/proxy.ajax";
	var curl = createURL(layerVO);
	var params ={
			url : curl
	};
	sendJsonRequest(url, params, proxyDoneCallback);
}


/**
* 프록시 서버를 통해 전달받은 레이어를 표시한다.
* @author seulgi.lee
* @date 2016. 02.
* @param result - 레이어 정보
* @returns 
*/
function proxyDoneCallback(result){
	loadImageHide();
	var format = new ol.format.GeoJSON().readFeatures(result);
	
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

	var url = 'http://175.116.181.39:9990/geoserver/wms';
	var nbBox = [layerInfo.nbBox.minx, layerInfo.nbBox.miny, layerInfo.nbBox.maxx, layerInfo.nbBox.maxy];
	var extnt = ol.proj.transformExtent(nbBox, "EPSG:4326", "EPSG:3857");
	var wms = createWmsTileLayer(url, layerInfo.lName, extnt);
//	wms = setLayerProperties(wms, layerInfo.lName, createLayerId(), "wms", "new", 1, false, null);
//	addLayerOnList(wms);

	var layer = createVectorLayer(new ol.source.Vector(), style);
	layer = setLayerProperties(layer, layerInfo.lName, layerInfo.lName, layerInfo.geomType.toLowerCase(), "new", 0,false, null);
//	addLayerOnList(layer);
	var layerArr = [wms, layer];
	var gLayer = createGroupLayer(layerArr);
	gLayer = setLayerProperties(gLayer, layerInfo.lName, createLayerId(), "group", "new", 0, false, null);
	addLayerOnList(gLayer);
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
	url = "http://175.116.181.39:9990/geoserver/wfs?service=WFS&version=1.1.0&request=GetFeature&typeName="+layerVO.lName+"&outputFormat=application/json&srsname=EPSG:3857";
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
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c

