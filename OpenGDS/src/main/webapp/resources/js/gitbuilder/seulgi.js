// 슬기
	// ==========================================================================================================
	/*코딩시작*/

	/**
	 * @description 검수, 일반화 페이지 이동
	 */
	gitbuilder.ui.showQAPage = function showQAPage(){
	    var params = {
		};
		getToUrl(CONTEXT + "/temp.do", params, "_self");
	}
	
	gitbuilder.ui.showGenPage = function showGenPage(){
	    var params = {
		};
		getToUrl(CONTEXT + "/generalization/generalization.do", params, "_self");
	}
	
	/**
	 * @description GeoServer 추가
	 */
	gitbuilder.ui.NewAddGeoserverWindow = function NewAddGeoserverWindow(){
	    if (!gitbuilder.variable.elementid.geoserverWindow) {
		var geoserverWindowId = "geoserverWindow";
		var addServerDialogId = "addServerDialog";
		gitbuilder.variable.elementid.geoserverWindow = geoserverWindowId;
		
		var geoserver = "<div class='modal fade' id='" + geoserverWindowId + "' tabindex='-1' role='dialog'>";
		geoserver += '<div class="modal-dialog">';
		geoserver += '<div class="modal-content">';
		geoserver += '<div class="modal-header">';
		geoserver += '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
		geoserver += '<h4 class="modal-title">GeoServer Management</h4>';
		geoserver += '</div>';
		geoserver += '<div class="modal-body">';
		geoserver += '<button type="button" style="margin: 5px 0px 5px 0px;" class="btn btn-primary btn-lg btn-block" data-toggle="modal" href="#'+addServerDialogId+'"">Add Server</button>';
		geoserver += '<div class="tbList">';
		geoserver += '<table class="table table-hover table-bordered text-center">';
		geoserver += '<colgroup><col width="20%"/><col width="50%"/><col width="30%"/>';
		geoserver += '<thead>';
		geoserver += '<tr>';
		geoserver += '<th style="text-align: center;"><div class="checkbox checkbox-primary checkbox-single " style="margin-top: 0px;margin-bottom: 0px;"><input type="checkbox" id="allSChkbox" name="allCheckbox"><label></label></div></th>';
		geoserver += '<th style="text-align: center;"><strong>Server Name</strong></th>';
		geoserver += '<th style="text-align: center;"><strong>Detail Information</strong></th>';
		geoserver += '</tr>';
		geoserver += '</thead>';
		geoserver += '<tbody id="addGeoTableBody">';
		geoserver += '</tbody>';
		geoserver += '</table>';
		geoserver += '</div>';
		geoserver += '<div class="text-center">';
		geoserver += '<button type="button" style="width: 330px;" class="btn btn-lg btn-danger" onclick="removeServerAjax()">Delete Selected</button>';
		geoserver += '</div>';
		geoserver += '</div>';
		geoserver += '<div class="modal-footer">';
		geoserver += '<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>';
		geoserver += '</div>';
		geoserver += '</div>';
		geoserver += '</div>';
		geoserver += '</div>';

		$("body").append(geoserver);
		
		var serverDiaContent = "<div class='modal fade' id='"+addServerDialogId+"' tabindex='-1' data-focus-on='input:first' role='dialog'>";
		serverDiaContent += '<div class="modal-dialog">';
		serverDiaContent += '<div class="modal-content">';
		serverDiaContent += '<div class="modal-header">';
		serverDiaContent += '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
		serverDiaContent += '<h4 class="modal-title">Add Server</h4>';
		serverDiaContent += '</div>';
		serverDiaContent += '<div class="modal-body form-horizontal">';
		serverDiaContent += '<div class="form-group">';
		serverDiaContent += '<label class="col-sm-2 control-label">Server Name</label>';
		serverDiaContent += '<div class="col-sm-10">';
		serverDiaContent += '<input type="text" class="form-control" id="serverName" placeholder="Server Name">';
		serverDiaContent += '</div>';
		serverDiaContent += '</div>';
		serverDiaContent += '<div class="form-group">';
		serverDiaContent += '<label class="col-sm-2 control-label">URL</label>';
		serverDiaContent += '<div class="col-sm-10">';
		serverDiaContent += '<input type="text" class="form-control" id="serverURL" placeholder="URL">';
		serverDiaContent += '</div>';
		serverDiaContent += '</div>';
		serverDiaContent += '<div class="form-group">';
		serverDiaContent += '<label class="col-sm-2 control-label">ID</label>';
		serverDiaContent += '<div class="col-sm-10">';
		serverDiaContent += '<input type="text" class="form-control" id="serverID" placeholder="ID">';
		serverDiaContent += '</div>';
		serverDiaContent += '</div>';
		serverDiaContent += '<div class="form-group">';
		serverDiaContent += '<label class="col-sm-2 control-label">Password</label>';
		serverDiaContent += '<div class="col-sm-10">';
		serverDiaContent += '<input type="password" class="form-control" id="serverPW" placeholder="Password">';
		serverDiaContent += '</div>';
		serverDiaContent += '</div>';
		serverDiaContent += '<div class="modal-footer">';
		serverDiaContent += '<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>';
		serverDiaContent += '<button type="button" class="btn btn-primary" onclick="addServerAjax()">Add</button>';
		serverDiaContent += '</div>';
		serverDiaContent += '</div>';
		serverDiaContent += '</div>';
		serverDiaContent += '</div>';
		
		$("body").append(serverDiaContent);
		
	    }
	    loadingServerAjax();
	    $('#' + gitbuilder.variable.elementid.geoserverWindow).modal('show');
	}
	
	gitbuilder.ui.AddServerDialog = function AddServerDialog(){
	  /*  var title = "서버추가";
	    var dialogContent = "test";
	    var dlg = new BootstrapDialog({
	        title: title,
	        message: dialogContent,
	        draggable: true,
	        onshown: function(dialog) {
	            var tier = $('.bootstrap-dialog').length - 1;
	            dialog.$modal.prev(".modal-backdrop")
	                .css("z-index", 1030 + tier * 30);
	            dialog.$modal
	                .css("z-index", 1040 + tier * 30);
	        },
	        buttons: [{
	            label: 'OK',
	            cssClass: 'btn-primary',
	            action: function (dialog) {
	                if (callback !== "") { callback(); }
	                dialog.close();
	            }
	        },{
	            label: 'Close',
	            cssClass: 'btn',
	            action: function (dialog) {
	                dialog.close();
	            }
	        }]
	    }).open();*/
	    /*if (!gitbuilder.variable.elementid.addServerDialog) {
		var addServerDialogId = "addServerDialog";
		gitbuilder.variable.elementid.addServerDialog = addServerDialogId;
		
		var serverDiaContent = "<div class='modal fade' id='" + addServerDialogId + "' tabindex='-1' role='dialog'>";
		serverDiaContent += '<div class="modal-dialog ">';
		serverDiaContent += '<div class="modal-content">';
		serverDiaContent += '<div class="modal-header">';
		serverDiaContent += '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
		serverDiaContent += '<h4 class="modal-title">서버추가</h4>';
		serverDiaContent += '</div>';
		serverDiaContent += '<div class="modal-body form-horizontal">';
		serverDiaContent += '<div class="form-group">';
		serverDiaContent += '<label class="col-sm-2 control-label">서버명</label>';
		serverDiaContent += '<div class="col-sm-10">';
		serverDiaContent += '<input type="text" class="form-control" id="serverName" placeholder="서버명">';
		serverDiaContent += '</div>';
		serverDiaContent += '</div>';
		serverDiaContent += '<div class="form-group">';
		serverDiaContent += '<label class="col-sm-2 control-label">URL</label>';
		serverDiaContent += '<div class="col-sm-10">';
		serverDiaContent += '<input type="text" class="form-control" id="serverURL" placeholder="URL">';
		serverDiaContent += '</div>';
		serverDiaContent += '</div>';
		serverDiaContent += '<div class="form-group">';
		serverDiaContent += '<label class="col-sm-2 control-label">아이디</label>';
		serverDiaContent += '<div class="col-sm-10">';
		serverDiaContent += '<input type="text" class="form-control" id="serverID" placeholder="아이디">';
		serverDiaContent += '</div>';
		serverDiaContent += '</div>';
		serverDiaContent += '<div class="form-group">';
		serverDiaContent += '<label class="col-sm-2 control-label">비밀번호</label>';
		serverDiaContent += '<div class="col-sm-10">';
		serverDiaContent += '<input type="text" class="form-control" id="serverPW" placeholder="비밀번호">';
		serverDiaContent += '</div>';
		serverDiaContent += '</div>';
		serverDiaContent += '<div class="modal-footer">';
		serverDiaContent += '<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>';
		serverDiaContent += '<button type="button" class="btn btn-primary">추가</button>';
		serverDiaContent += '</div>';
		serverDiaContent += '</div>';
		serverDiaContent += '</div>';
		serverDiaContent += '</div>';
		
		$("body").append(serverDiaContent);
	    }*/
	}
	
	/**
	 * @description GeoServer Layer 추가
	 */
	gitbuilder.ui.NewGeoserverLayerWindow = function NewGeoserverLayerWindow(flag){
	    if (!gitbuilder.variable.elementid.geoserverLayerWindow) {
		gitbuilder.variable.elementid.geoserverLayerWindow = 'geoserverLayerWindow';
		var geoserverLayerWindowId = "geoserverLayerWindow";
		var geoserverLayer = "<div class='modal fade' id='" + geoserverLayerWindowId + "' tabindex='-1' role='dialog'>";
		geoserverLayer += '<div class="modal-dialog">';
		geoserverLayer += '<div class="modal-content">';
		geoserverLayer += '<div class="modal-header">';
		geoserverLayer += '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
		geoserverLayer += '<h4 class="modal-title">Additional Server Layer</h4>';
		geoserverLayer += '</div>';
		geoserverLayer += '<div class="modal-body" style="height: 37%;overflow-y: scroll;display: block;">';
		geoserverLayer += '<div style="padding-bottom: 1%;">';
		geoserverLayer += '<select class="form-control input-lg" style="padding-bottom: 1%;" id="seserverlayer"></select>';
		geoserverLayer += '</div>';
		geoserverLayer += '<div class="tbList">';
		geoserverLayer += '<table class="table table-hover table-bordered text-center">';
		geoserverLayer += '<colgroup><col width="15%"/><col width="25%"/><col width="30%"/><col width="30%"/>';
		geoserverLayer += '<thead>';
		geoserverLayer += '<tr>';
		geoserverLayer += '<th style="text-align: center;"><div class="checkbox checkbox-primary checkbox-single " style="margin-top: 0px;margin-bottom: 0px;"><input type="checkbox" id="allLChkbox"><label></label></div></th>';
		geoserverLayer += '<th style="text-align: center;"><strong>Layer Type</strong></th>';
		geoserverLayer += '<th style="text-align: center;"><strong>Layer Name</strong></th>';
		geoserverLayer += '<th style="text-align: center;"><strong>Detail Information</strong></th>';
		geoserverLayer += '</tr>';
		geoserverLayer += '</thead>';
		geoserverLayer += '<tbody id="addGeoLayerTableBody">';
		geoserverLayer += '</tbody>';
		geoserverLayer += '</table>';
		geoserverLayer += '</div>';
		geoserverLayer += '<img id="layerloadimage" src="'+CONTEXT+'/resources/img/spin.gif" style="position: absolute; margin-left:40%; margin-top:20%; width: 100px; height: 90px; display: none;">';
		geoserverLayer += '</div>';
		geoserverLayer += '<div class="modal-footer">';
		geoserverLayer += '<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>';
		if(flag=='Q'){
		    geoserverLayer += '<button type="button" class="btn btn-primary" onclick="geoLayerAdd()">Add</button>';
		}
		else if(flag=='G'){
		    geoserverLayer += '<button type="button" class="btn btn-primary" onclick="geoGenLayerAdd()">Add</button>';
		}
		else{
		    geoserverLayer += '<button type="button" class="btn btn-primary" onclick="geoLayerAdd()">Add</button>';
		}
		geoserverLayer += '</div>';
		geoserverLayer += '</div>';
		geoserverLayer += '</div>';
		geoserverLayer += '</div>';
		$("body").append(geoserverLayer);
	    }
	    
	    
	    loadingLayerServerAjax();
	    $('#defaultserver').attr('disabled',false);
	    $('#addGeoLayerTableBody > tr').remove();
	    $('#' + gitbuilder.variable.elementid.geoserverLayerWindow).modal('show');
	    
	    $('#seserverlayer').on('change', function(){
		$('#defaultserver').attr('disabled',true);
		var url = $("#seserverlayer option:selected").val();
			loadLayerAjax(url);
		});
	}
	
	
	gitbuilder.ui.NewGenLayerManageWindow = function NewGenLayerManageWindow(flag){
	    if (!gitbuilder.variable.elementid.NewGenLayerManageWindow) {
		gitbuilder.variable.elementid.genLayerManageWindow = 'genLayerManageWindow';
		var genLayerManageWindowId = "genLayerManageWindow";
		var geoserverLayer = "<div class='modal fade' id='" + genLayerManageWindowId + "' tabindex='-1' role='dialog'>";
		geoserverLayer += '<div class="modal-dialog">';
		geoserverLayer += '<div class="modal-content">';
		geoserverLayer += '<div class="modal-header">';
		geoserverLayer += '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
		geoserverLayer += '<h4 class="modal-title">Generalization Layer Management</h4>';
		geoserverLayer += '</div>';
		geoserverLayer += '<div class="modal-body" style="height: 37%;overflow-y: scroll;display: block;">';
		geoserverLayer += '<div class="tbList">';
		geoserverLayer += '<table class="table table-hover table-bordered text-center">';
		geoserverLayer += '<colgroup><col width="15%"/><col width="25%"/><col width="30%"/><col width="30%"/>';
		geoserverLayer += '<thead>';
		geoserverLayer += '<tr>';
		geoserverLayer += '<th style="text-align: center;"><strong>Select</strong></th>';
		geoserverLayer += '<th style="text-align: center;"><strong>Layer Type</strong></th>';
		geoserverLayer += '<th style="text-align: center;"><strong>Layer Name</strong></th>';
		geoserverLayer += '<th style="text-align: center;"><strong>Detail Information</strong></th>';
		geoserverLayer += '</tr>';
		geoserverLayer += '</thead>';
		geoserverLayer += '<tbody id="genLayerManageTableBody">';
		geoserverLayer += '</tbody>';
		geoserverLayer += '</table>';
		geoserverLayer += '</div>';
		geoserverLayer += '</div>';
		geoserverLayer += '<div class="modal-footer">';
		geoserverLayer += '<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>';
		geoserverLayer += '<button type="button" class="btn btn-danger" data-dismiss="modal">Delete</button>';
		geoserverLayer += '</div>';
		geoserverLayer += '</div>';
		geoserverLayer += '</div>';
		geoserverLayer += '</div>';
		$("body").append(geoserverLayer);
	    }
	    
	    
	    loadingLayerServerAjax();
	    if(flag!="A"){
		$('#' + gitbuilder.variable.elementid.genLayerManageWindow).modal('show');
	    }
	    $('#seserverlayer').on('change', function(){
		loadLayerAjax();
		});
	}
	
	gitbuilder.ui.NewGeneralizationWindow = function NewGeneralizationWindow(flag){
	    if (!gitbuilder.variable.elementid.generalizationWindow) {
		var generalizationWindowId = "generalizationWindow";
		gitbuilder.variable.elementid.generalizationWindow = generalizationWindowId;
		
		
		var generDiaContent = "<div class='modal fade' id='"+generalizationWindowId+"' tabindex='-1' data-focus-on='input:first' role='dialog'>";
		generDiaContent += '<div class="modal-dialog modal-lg">';
		generDiaContent += '<div class="modal-content">';
		generDiaContent += '<div class="modal-header">';
		generDiaContent += '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
		generDiaContent += '<h4 class="modal-title">Generalization</h4>';
		generDiaContent += '</div>';
		generDiaContent += '<div class="modal-body form-horizontal" id="genOption">';
		generDiaContent += '</div>';
		generDiaContent += '<div class="modal-footer">';
		generDiaContent += '<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>';
		generDiaContent += '<button type="button" class="btn btn-primary" onclick="generalizationRun()" data-dismiss="modal">Run</button>';
		generDiaContent += '</div>';
		generDiaContent += '</div>';
		generDiaContent += '</div>';
		generDiaContent += '</div>';
		
		$("body").append(generDiaContent);
		
	    }
	    if(flag!="A"){
		$('#' + gitbuilder.variable.elementid.generalizationWindow).modal('show');
	    }
	}
	
	gitbuilder.ui.NewGeneralizationResultWindow = function NewGeneralizationResultWindow(flag){
	    if (!gitbuilder.variable.elementid.generalizationResultWindow) {
		var generalizationResultWindowId = "generalizationResultWindow";
		gitbuilder.variable.elementid.generalizationResultWindow = generalizationResultWindowId;
		
		
		var generResultDiaContent = "<div class='modal fade' id='"+generalizationResultWindowId+"' tabindex='-1' data-focus-on='input:first' role='dialog'>";
		generResultDiaContent += '<div class="modal-dialog">';
		generResultDiaContent += '<div class="modal-content">';
		generResultDiaContent += '<div class="modal-header">';
		generResultDiaContent += '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
		generResultDiaContent += '<h4 class="modal-title">Generalization Result</h4>';
		generResultDiaContent += '</div>';
		generResultDiaContent += '<div class="modal-body" id="genResult">';
		generResultDiaContent += '</div>';
		generResultDiaContent += '<div class="modal-footer">';
		generResultDiaContent += '<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>';
		generResultDiaContent += '</div>';
		generResultDiaContent += '</div>';
		generResultDiaContent += '</div>';
		generResultDiaContent += '</div>';
		
		$("body").append(generResultDiaContent);
		
	    }
	    if(flag!="A"){
		$('#' + gitbuilder.variable.elementid.generalizationResultWindow).modal('show');
	    }
	}
	
	/**
	 * @description TopologyTable 추가
	 */
	gitbuilder.ui.NewTopologyTableWindow = function NewTopologyTableWindow(flag){
	    if (!gitbuilder.variable.elementid.topologyTableWindow) {
		gitbuilder.variable.elementid.topologyTableWindow = 'topologyTableWindow';
		var topologyTableWindowId = "topologyTableWindow";
		var topologyTable = "<div class='modal fade' id='" + topologyTableWindowId + "' tabindex='-1' role='dialog'>";
		topologyTable += '<div class="modal-dialog" style="width: 1000px;">';
		topologyTable += '<div class="modal-content">';
		topologyTable += '<div class="modal-header">';
		topologyTable += '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
		topologyTable += '<h4 class="modal-title">TopologyTable</h4>';
		topologyTable += '</div>';
		topologyTable += '<div class="modal-body" style="height: 37%;overflow-y: scroll;display: block;">';
		topologyTable += '<div class="tbList">';
		topologyTable += '<table class="table table-hover table-bordered text-center">';
		topologyTable += '<colgroup><col width="15%"/><col width="40%"/><col width="40%"/><col width="10%"/>';
		topologyTable += '<thead>';
		topologyTable += '<tr>';
		topologyTable += '<th style="text-align: center;"><strong>Object ID</strong></th>';
		topologyTable += '<th style="text-align: center;"><strong>First Point</strong></th>';
		topologyTable += '<th style="text-align: center;"><strong>Last Point</strong></th>';
		topologyTable += '<th style="text-align: center;"><strong>Length(km)/area(m2)</strong></th>';
		topologyTable += '</tr>';
		topologyTable += '</thead>';
		topologyTable += '<tbody id="topologyTableBody">';
		topologyTable += '</tbody>';
		topologyTable += '</table>';
		topologyTable += '</div>';
		topologyTable += '</div>';
		topologyTable += '<div class="modal-footer">';
		topologyTable += '<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>';
		topologyTable += '</div>';
		topologyTable += '</div>';
		topologyTable += '</div>';
		topologyTable += '</div>';
		$("body").append(topologyTable);
	    }
	    if(flag!="A"){
	    $('#' + gitbuilder.variable.elementid.topologyTableWindow).modal('show');
	    }
	}
	