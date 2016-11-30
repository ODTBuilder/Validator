$(document).ready(function() {
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



