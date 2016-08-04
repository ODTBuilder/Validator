/**
 * 공간정보 편집도구를 구성하는 함수를 정의한다.
 * 
 * @author yijun.so
 * @date 2016. 02
 * @version 0.02
 */

var gitbuilder;
if (!gitbuilder) {
	gitbuilder = {};
}
if (!gitbuilder.variable) {

	// 변수객체
	gitbuilder.variable = {};

	// 커서 위치
	gitbuilder.variable.crsrPosition = [];

	// 프로젝트 객체
	gitbuilder.variable.prjGroup = {};

	// 피처 클립보드
	gitbuilder.variable.clpboard = [];

	// 오픈 레이어스 뷰객체
	gitbuilder.variable.view = {};

	// 오픈 레이어스 맵객체
	gitbuilder.variable.map = {};

	// 오픈 레이어스 컨트롤 배열
	gitbuilder.variable.ctrl = [];

	// 오픈 레이어스 인터랙션 배열
	gitbuilder.variable.intrctn = [];

	// 선택한 레이어 배열
	gitbuilder.variable.selectedLayers = [];

	// DOM id
	// layerList: 레이어 리스트
	// shpWindow : 쉐잎 윈도우
	// shpInput: shp 인풋
	// shxInput: shx 인풋
	// dbfInput: dbf 인풋
	// vectorWindow: 벡터 윈도우
	// pointInput: 포인트 인풋 라디오버튼 아이디
	// lineInput: 라인 인풋 라디오버튼 아이디
	// polyInput: 폴리곤 인풋 라디오버튼 아이디
	// layerName: 레이어 이름 인풋 텍스트
	gitbuilder.variable.elementid = {};

	// input name
	// layerType: 레이어 타입 인풋 네임
	gitbuilder.variable.inputname = {};

	// 메뉴 이름변경 또는 로컬라이징
	gitbuilder.variable.menuname = {};

	// 에러 리포트
	gitbuilder.variable.errReport = {};

}
if (!gitbuilder.method) {
	gitbuilder.method = {};
	// ==========================================================================================================
	/**
	 * @description 엘리먼트 아이디 중복을 검사한다.
	 * @return {boolean} 중복여부
	 */
	gitbuilder.method.isDuplicatedId = function isDuplicatedId(_eid) {
		var idCheck = $('[id="' + _eid + '"]');
		if (idCheck.length === 0) {
			return false;
		} else {
			return true;
		}
	}
	// ==========================================================================================================
	/**
	 * @description 인풋 네임 중복을 검사한다.
	 * @return {boolean} 중복여부
	 */
	gitbuilder.method.isDuplicatedName = function isDuplicatedName(_name) {
		var nameCheck = $('input[name="' + _name + '"]');
		if (nameCheck.length === 0) {
			return false;
		} else {
			return true;
		}
	}
	// ==========================================================================================================
	/**
	 * @description 컨텍스트 패스를 구한다.
	 * @return {String} 컨텍스트 패스
	 */
	gitbuilder.method.getContextPath = function getContextPath() {
		var offset = location.href.indexOf(location.host) + location.host.length;
		var ctxPath = location.href.substring(offset, location.href.indexOf('/', offset + 1));
		return ctxPath;
	}
	// ==========================================================================================================
	gitbuilder.method.map = {};
	/**
	 * @description 뷰 객체를 설정
	 * @param {ol.View}
	 *            view - 뷰 객체
	 */
	gitbuilder.method.map.setMapView = function setMapView(view) {
		gitbuilder.variable.view = view;
	}
	// ==========================================================================================================
	/**
	 * @description 뷰 객체를 반환
	 * @returns {ol.View} 뷰 객체
	 */
	gitbuilder.method.map.getMapView = function getMapView() {
		return gitbuilder.variable.view;
	}
	// ==========================================================================================================
	/**
	 * @description 지도 영역을 설정
	 * @param {ol.Map}
	 *            map - 지도 객체
	 */
	gitbuilder.method.map.setBuilderMap = function setBuilderMap(map) {
		gitbuilder.variable.map = map;
	}
	// ==========================================================================================================
	/**
	 * @description 지도 영역을 반환
	 * @returns {ol.Map} 맵 객체
	 */
	gitbuilder.method.map.getBuilderMap = function getBuilderMap() {
		return gitbuilder.variable.map;
	}
	// ==========================================================================================================
	/**
	 * @description 지도 컨트롤을 설정
	 * @param {ol.control[]}
	 *            control - 컨트롤 배열
	 */
	gitbuilder.method.map.setMapControl = function setMapControl(control) {
		gitbuilder.variable.ctrl = control;
	}
	// ==========================================================================================================
	/**
	 * @description 지도 컨트롤을 반환
	 * @returns {ol.control[]}
	 */
	gitbuilder.method.map.getMapControl = function getMapControl() {
		return gitbuilder.variable.ctrl;
	}
	// ==========================================================================================================
	gitbuilder.method.layer = {};
	/**
	 * @description 레이어 목록을 업데이트
	 */
	gitbuilder.method.layer.updateLayerList = function updateLayerList() {
		var layers = gitbuilder.method.map.getBuilderMap().getLayers();
		var layerListId = gitbuilder.variable.elementid.layerList;
		var addedLayers = layers.getArray();

		addedLayers.sort(function(a, b) {
			return a.getZIndex() < b.getZIndex() ? -1 : a.getZIndex() > b.getZIndex() ? 1 : 0;
		});

		$("#" + layerListId).empty();

		for (var i = 0; i < addedLayers.length; i++) {

			var layerKeysStr = "";
			layerKeysStr += "<li layerid='" + addedLayers[i].get("id") + "' class='GitBuilder-LayerList-Item'><p class='ListedLayer'>"
					+ addedLayers[i].get("name"); /*
													 * + "</p><button
													 * class='layerPropBtn'>Properties</button></li>";
													 */

			$("#" + layerListId).prepend(layerKeysStr);

		}
		$(".ListedLayer").css("display", "inline-block").css("width", 160).css("height", 16).css("text-overflow", "ellipsis");

		$("#" + layerListId).sortable({
			handle : ".GitBuilder-LayerList-Item-Handle",
			stop : function(event, ui) {
				// 선택한 li의 배열
				var layerLiArr = $("#" + layerListId + " li").toArray();
				// 배열을 뒤집는다
				layerLiArr.reverse();
				// 모든 레이어 획득
				var layers = gitbuilder.method.map.getBuilderMap().getLayers();
				// 모든 레이어를 배열로 획득
				var addedLayers = layers.getArray();

				for (var i = 0; i < layerLiArr.length; i++) {
					// 레이어 아이디와 같은 레이어를 검색
					for (var j = 0; j < addedLayers.length; j++) {
						// li의 아이디가 레이어의 아이디와 같다면
						if (addedLayers[j].get("id") === $(layerLiArr[i]).attr("layerid")) {
							if (addedLayers[j] instanceof ol.layer.Group) {
								addedLayers[j].setZIndex(i);
								var layers = addedLayers[j].getLayers().getArray();
								for (var k = 0; k < layers.length; k++) {
									layers[k].setZIndex(i);
								}
							} else {
								// li의 인덱스를 레이어의 인덱스로 설정
								addedLayers[j].setZIndex(i);
								break;
							}
						}
					}
				}
			}
		}).addClass("layerSelectable").selectable({
			filter : "li",
			cancel : ".GitBuilder-LayerList-Item-Handle, .layerPropBtn",
			stop : function(event, ui) {

			}
		}).find("li").addClass("ui-corner-all").prepend(
				"<span class='GitBuilder-LayerList-Item-Handle'><span class='glyphicon glyphicon-sort' aria-hidden='true'></span></span>");
	}
	// ==========================================================================================================
	/**
	 * @description 지도와 목록에 레이어를 추가
	 * @param {ol.layer.Base}
	 *            createdLayer - 추가할 레이어
	 */
	gitbuilder.method.layer.addLayerOnList = function addLayerOnList(createdLayer) {
		if (createdLayer !== null || createdLayer !== undefined) {
			gitbuilder.method.map.getBuilderMap().addLayer(createdLayer);
		} else {
			console.error("Layer is not created");
		}

		gitbuilder.method.layer.updateLayerList();
	}
	// ==========================================================================================================
	/**
	 * @description 유일한 레이어 아이디를 생성
	 * @returns {string} 레이어 아이디
	 */
	gitbuilder.method.layer.createLayerId = function createLayerId() {
		var layers = gitbuilder.method.map.getBuilderMap().getLayers().getArray();
		var layerId;
		if (layers.length > 0) {
			var max = 1;
			for (var i = 0; i < layers.length; i++) {
				var lid = layers[i].get("id");
				var num = parseInt(lid.substring(lid.indexOf("layer") + 5));
				if (num > max) {
					max = num;
				} else {
					continue;
				}
			}
			max++;
			layerId = "layer" + max;
		} else if (layers.length === 0) {
			layerId = "layer1";
		}
		return layerId;
	}
	// ==========================================================================================================
	/**
	 * @description 레이어에 부가 속성을 부여
	 * @param {object}
	 *            레이어에 부여될 속성
	 */
	/*
	 * { layer: 레이어 객체 name: 레이어 이름 id: 레이어 아이디 type: 레이어 타입 - 폴리곤 cat: 레이어 종류 -
	 * 0: 승인, 1: 비승인, 2: 오류 edit: 수정가능 - 불린 attrType: 피처 속성 {키: 데이터 타입, ...} }
	 */
	gitbuilder.method.layer.setLayerProperties = function setLayerProperties(obj) {
		// function setLayerProperties(layer, name, id, type, from, cat, edit,
		// attrType) {

		// 레이어의 갯수만큼의 인덱스를 부여(새로만든 레이어는 최상위로 보여진다)
		obj.layer.setZIndex(gitbuilder.method.map.getBuilderMap().getLayers().getLength());
		// 레이어 이름 부여
		obj.layer.set("name", obj.name);
		// 레이어 아이디 부여
		obj.layer.set("id", obj.id);
		// 선택한 타입을 부여
		obj.layer.set("type", obj.type);
		// 검수결과 레이어인지
		obj.layer.set("cat", obj.cat);
		// 수정 가능한 레이어인지
		obj.layer.set("editable", obj.edit);

		// 애트리뷰트 설정
		if (obj.attrType !== null && obj.attrType !== undefined) {
			// 레이어 애트리뷰트 타입 부여
			obj.layer.set("attribute", obj.attrType);
		}
	}
}

if (!gitbuilder.ui) {
	// ==========================================================================================================
	gitbuilder.ui = {};
	gitbuilder.ui.button = {};
	gitbuilder.ui.window = {};

	// ==========================================================================================================
	/**
	 * @description 레이어 리스트를 설정
	 * @param {ol.control[]}
	 *            control - 컨트롤 배열
	 */
	gitbuilder.ui.LayerList = function setLayerList(target) {
		var layerListId = "layerList";
		var count = 0;
		while (gitbuilder.method.isDuplicatedId(layerListId)) {
			layerListId += count;
		}

		gitbuilder.variable.elementid.layerList = layerListId;
		// div.css("overflow", "hidden");
		var list = $('<ul id="' + layerListId + '" class=".GitBuilder-LayerList"></ul>');
		$("#" + target).append(list);

		$("#" + layerListId).sortable({
			handle : ".GitBuilder-LayerList-Item-Handle",
			stop : function(event, ui) {
				// 선택한 li의 배열
				var layerLiArr = $("#" + layerListId + " li").toArray();
				// 배열을 뒤집는다
				layerLiArr.reverse();
				// 모든 레이어 획득
				var layers = gitbuilder.method.map.getBuilderMap().getLayers();
				// 모든 레이어를 배열로 획득
				var addedLayers = layers.getArray();

				for (var i = 0; i < layerLiArr.length; i++) {
					// 레이어 아이디와 같은 레이어를 검색
					for (var j = 0; j < addedLayers.length; j++) {
						// li의 아이디가 레이어의 아이디와 같다면
						if (addedLayers[j].get("id") === $(layerLiArr[i]).attr("layerId")) {
							if (addedLayers[j] instanceof ol.layer.Group) {
								addedLayers[j].setZIndex(i);
								var layers = addedLayers[j].getLayers().getArray();
								for (var k = 0; k < layers.length; k++) {
									layers[k].setZIndex(i);
								}
							} else {
								// li의 인덱스를 레이어의 인덱스로 설정
								addedLayers[j].setZIndex(i);
								break;
							}
						}
					}
				}
			}
		}).addClass("layerSelectable").selectable({
			filter : "li",
			cancel : ".GitBuilder-LayerList-Item-Handle, .layerPropBtn",
			stop : function(event, ui) {

				// // ul 객체를 저장
				// var p1 = $(this);
				//
				// // 선택한 li들의 객체배열을 획득
				// var selected = $(".layerSelectable .ui-selected");
				//
				// // 모든 레이어 획득
				// var allLayers =
				// gitbuilder.method.getBuilderMap().getLayers().getArray();
				//
				// // // 모든 레이어를 비선택으로 설정
				// // for (var i = 0; i < allLayers.length; i++) {
				// // allLayers[i].set("selected", 0);
				// // }
				//
				// // 각각의 li마다
				// selected.each(function() {
				// // li의 부모를 획득
				// var p2 = $(this).parent();
				// // 같은 부모의 자식이라면
				// if (p1.get(0) === p2.get(0)) {
				// // selectable에서 선택한 아이디를 가진 레이어 객체획득
				// var selectedLayer = getLayerById($(this).attr("id"));
				// // 선택여부를 1로 설정
				// selectedLayer.set("selected", 1);
				// } else {
				// // 아니라면 메시지 출력
				// console.log("it isn't a descendant");
				// }
				// });
				// // 선택한 레이어 객체들 획득
				// var selected = getSelectedLayers();
				//
				// // 선택한 레이어가 2개 이상이면
				// if (selected.length > 1) {
				// // 레이어 이름 배열로 초기화
				// var layerNames = [];
				// // 선택된 레이어들의 이름을 배열에 추가
				// for (var i = 0; i < selected.length; i++) {
				// layerNames.push(selected[i].get("name"));
				// }
				// // 레이어 이름들을 다이얼로그에 출력
				// $("#drawTool").dialog("option", "title", "Tool - " +
				// layerNames);
				// // 다중레이어 선택으로 도구모음을 연다
				// openToolBox("multiplex");
				//
				// $("#featureList").empty();
				//
				// // 선택한 레이어가 1개라면
				// } else if (selected.length === 1) {
				//
				// var layer = selected[0];
				// if (layer.get("editable") === false) {
				// disabledTool();
				// } else {
				// enabledTool();
				// }
				//
				// var view = gitbuilder.method.getBuilderMap().getView();
				//
				// if (layer instanceof ol.layer.Image) {
				// view.fit(layer.getExtent(),
				// gitbuilder.method.getBuilderMap().getSize());
				// } else if (layer instanceof ol.layer.Vector) {
				// var source = layer.getSource();
				// var features = source.getFeatures();
				// if (features.length > 0) {
				// view.fit(source.getExtent(),
				// gitbuilder.method.getBuilderMap().getSize());
				// }
				// } else if (layer instanceof ol.layer.Group) {
				// var layers = layer.getLayers().getArray();
				// for (var i = 0; i < layers.length; i++) {
				// if (layers[i] instanceof ol.layer.Tile) {
				// view.fit(layers[i].getExtent(),
				// gitbuilder.method.getBuilderMap().getSize());
				// }
				// }
				// }
				// // 레이어 이름을 저장
				// var layerNames = selected[0].get("name");
				//
				// // 선택한 레이어가 베이스맵이 아니라면
				// if (selected[0].get("type") === "point" ||
				// selected[0].get("type") === "linestring"
				// || selected[0].get("type") === "polygon" ||
				// selected[0].get("type") === "multipoint"
				// || selected[0].get("type") === "multilinestring" ||
				// selected[0].get("type") === "multipolygon") {
				//
				// // 레이어 이름 출력
				// $("#drawTool").dialog("option", "title", "Tool - " +
				// layerNames);
				// // 레이어 타입에 따른 도구모음을 연다
				// openToolBox(selected[0].get("type"));
				// if (selected[0].get("cat") === 2) {
				// openToolBox("error");
				// }
				// var layers = getSelectedLayers();
				// if (layers.length === 1 && layers[0] instanceof
				// ol.layer.Vector) {
				// $("#featureList").empty();
				// var features = layers[0].getSource().getFeatures();
				// for (var i = 0; i < features.length; i++) {
				// var str = '<li class="ui-widget-content" id="' +
				// features[i].getId() + '" style="padding: 3px;">'
				// + features[i].getId() + '</li>';
				// $("#featureList").append(str);
				// }
				//
				// $("#featureList").selectable({
				// start : function(event, ui) {
				// selectedFeatures = new ol.Collection();
				// },
				// selected : function(event, ui) {
				//
				// var id = ui.selected.id;
				// var feature = getFeatureById(id);
				// selectedFeatures.push(feature);
				//
				// },
				// stop : function(event, ui) {
				//
				// var view = gitbuilder.method.getBuilderMap().getView();
				// var source = new ol.source.Vector();
				// source.addFeatures(selectedFeatures.getArray());
				// view.fit(source.getExtent(),
				// gitbuilder.method.getBuilderMap().getSize());
				//
				// removeMyInteraction(gitbuilder.method.getBuilderMap());
				//
				// if (selectedFeatures.getLength() > 0) {
				//
				// popFeatureDialog(selectedFeatures.getArray());
				// $("#selectPopUp").dialog("option", "position", {
				// my : "right top",
				// at : "right-310px top",
				// of : $("#map")
				// });
				//
				// var selectLayers = getSelectedLayers();
				// if (selectedFeatures.getLength() > 0 && selectLayers.length >
				// 1) {
				// $("#dlet").button("disable");
				// }
				//
				// if (selectedFeatures.getLength() === 1) {
				// var layers = getSelectedLayers();
				// if (layers.length === 1) {
				// if (layers[0].get("cat") === 2) {
				// $("#cpy").button("disable");
				// $("#attr").button("disable");
				// $("#dlet").button("enable");
				// $("#mdfy").button("enable");
				// $("#move").button("enable");
				// } else {
				// $("#cpy").button("enable");
				// $("#attr").button("enable");
				// $("#dlet").button("enable");
				// $("#mdfy").button("enable");
				// $("#move").button("enable");
				// }
				// }
				//
				// var feature = selectedFeatures.item(0);
				// var str = getSimpleProperties(feature);
				// $("#viewAttr").empty();
				// $("#viewAttr").append(str);
				//
				// var keys = feature.getKeys();
				// var features1 = selected[0].getSource().getFeatures();
				// var keys = features1[0].getKeys();
				// var flag = true;
				// if (selectLayers.length === 1) {
				// if (selectLayers[0].get("cat") === 2) {
				// flag = false;
				// }
				// } else {
				//
				// }
				// if (flag) {
				//
				// var featureId = feature.getId();
				// var layerId = featureId.substring(0, featureId.indexOf("."));
				// // console.log(layerId);
				// layerId = layerId.trim();
				// var layer = getLayerById(layerId);
				// var layers = [ layer ];
				// var collFeatures = new ol.Collection();
				// var theFeature = getFeatureById2(layerId, featureId);
				// collFeatures.push(theFeature);
				// updateSelectInteraction2(layers, collFeatures,
				// gitbuilder.method.getBuilderMap());
				//
				// $("#deleteConfirmFeature").dialog({
				// autoOpen : false,
				// modal : true,
				// buttons : {
				// "확인" : function() {
				// removeSelectedFeature(layer, collFeatures);
				// $(this).dialog("close");
				// },
				// "취소" : function() {
				// $(this).dialog("close");
				// }
				// }
				// });
				//
				// } else if (!flag) {
				// var featureId = feature.get("errfeatureID");
				// // var
				// // featureId
				// // =
				// // "layer1.1";
				// var layerId = featureId.substring(0, featureId.indexOf("."));
				// // console.log(layerId);
				// layerId = layerId.trim();
				// var layer = getLayerById(layerId);
				// var layers = [ layer ];
				// var collFeatures = new ol.Collection();
				// var theFeature = getFeatureById2(layerId, featureId);
				// collFeatures.push(theFeature);
				// // updateSelectInteraction2(null,
				// // null,
				// // map);
				// updateSelectInteraction2(layers, collFeatures,
				// gitbuilder.method.getBuilderMap());
				//
				// $("#deleteConfirmFeature").dialog({
				// autoOpen : false,
				// modal : true,
				// buttons : {
				// "확인" : function() {
				// removeSelectedFeature(layer, collFeatures);
				// $(this).dialog("close");
				// },
				// "취소" : function() {
				// $(this).dialog("close");
				// }
				// }
				// });
				//
				// }
				//
				// } else if (selectedFeatures.getLength() > 1) {
				//
				// // //////////////////////////////
				// var flag = true;
				// if (selectLayers.length === 1) {
				// if (selectLayers[0].get("cat") === 2) {
				// flag = false;
				// }
				// } else {
				//
				// }
				// if (flag) {
				//
				// var collFeatures = new ol.Collection();
				// var layers;
				//
				// for (var i = 0; i < selectedFeatures.getLength(); i++) {
				// var featureId = selectedFeatures.item(i).getId();
				// var layerId = featureId.substring(0, featureId.indexOf("."));
				// // console.log(layerId);
				// layerId = layerId.trim();
				// var layer = getLayerById(layerId);
				// layers = [ layer ];
				// var theFeature = getFeatureById2(layerId, featureId);
				// collFeatures.push(theFeature);
				// }
				//
				// updateSelectInteraction2(layers, collFeatures,
				// gitbuilder.method.getBuilderMap());
				//
				// $("#deleteConfirmFeature").dialog({
				// autoOpen : false,
				// modal : true,
				// buttons : {
				// "확인" : function() {
				// removeSelectedFeature(layer, collFeatures);
				// $(this).dialog("close");
				// },
				// "취소" : function() {
				// $(this).dialog("close");
				// }
				// }
				// });
				//
				// } else if (!flag) {
				//
				// var collFeatures = new ol.Collection();
				// var layers;
				//
				// for (var i = 0; i < selectedFeatures.getLength(); i++) {
				// var featureId = selectedFeatures.item(i).get("errfeatureID");
				// var layerId = featureId.substring(0, featureId.indexOf("."));
				// // console.log(layerId);
				// layerId = layerId.trim();
				// var layer = getLayerById(layerId);
				// layers = [ layer ];
				// var theFeature = getFeatureById2(layerId, featureId);
				// collFeatures.push(theFeature);
				// }
				//
				// updateSelectInteraction2(layers, collFeatures,
				// gitbuilder.method.getBuilderMap());
				//
				// $("#deleteConfirmFeature").dialog({
				// autoOpen : false,
				// modal : true,
				// buttons : {
				// "확인" : function() {
				// removeSelectedFeature(layer, collFeatures);
				// $(this).dialog("close");
				// },
				// "취소" : function() {
				// $(this).dialog("close");
				// }
				// }
				// });
				//
				// }
				//
				// // /////////////////////////////
				//
				// var layers = getSelectedLayers();
				// if (layers.length === 1) {
				// if (layers[0].get("cat") === 2) {
				// $("#cpy").button("disable");
				// $("#attr").button("disable");
				// $("#dlet").button("disable");
				// $("#mdfy").button("disable");
				// $("#move").button("disable");
				// } else {
				// $("#cpy").button("enable");
				// $("#attr").button("disable");
				// $("#dlet").button("enable");
				// $("#mdfy").button("enable");
				// $("#move").button("enable");
				// }
				// }
				// }
				//
				// $("#selectPopUp").dialog("open");
				// }
				// }
				// });
				//
				// }
				// } else {
				// $("#drawTool").dialog("close");
				// }
				//
				// } else {
				// // 도구모음창을 닫는다
				// $("#drawTool").dialog("close");
				// }
				// // 편집도구모음창을 닫는다
				// $("#selectPopUp").dialog("close");
				// // 인터랙션을 삭제한다
				// removeMyInteraction(gitbuilder.method.getBuilderMap());
			}
		}).find("li").addClass("ui-corner-all").prepend(
				"<span class='GitBuilder-LayerList-Item-Handle'><span class='glyphicon glyphicon-sort' aria-hidden='true'></span></span>");
	}
	// ==========================================================================================================
	/**
	 * @description 새로운 SHP 레이어를 생성
	 */
	gitbuilder.ui.NewSHPWindow = function NewSHPWindow() {

		if (!gitbuilder.variable.elementid.shpWindow) {
			var shpLayerWindowId = "shpWindow";
			var count = 0;
			while (gitbuilder.method.isDuplicatedId(shpLayerWindowId)) {
				shpLayerWindowId += count;
			}
			gitbuilder.variable.elementid.shpWindow = shpLayerWindowId;

			var upload1 = "shpfile";
			count = 0;
			while (gitbuilder.method.isDuplicatedId(upload1)) {
				upload1 += count;
			}
			gitbuilder.variable.elementid.shpInput = upload1;

			var upload2 = "shxfile";
			count = 0;
			while (gitbuilder.method.isDuplicatedId(upload2)) {
				upload2 += count;
			}
			gitbuilder.variable.elementid.shxInput = upload2;

			var upload3 = "dbffile";
			count = 0;
			while (gitbuilder.method.isDuplicatedId(upload3)) {
				upload3 += count;
			}
			gitbuilder.variable.elementid.dbfInput = upload3;

			var shpWin = "<div class='modal fade' id='" + shpLayerWindowId + "' tabindex='-1' role='dialog'>";
			shpWin += '<div class="modal-dialog">';
			shpWin += '<div class="modal-content">';
			shpWin += '<div class="modal-header">';
			shpWin += '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
			shpWin += '<h4 class="modal-title">SHP Upload</h4>';
			shpWin += '</div>';
			shpWin += '<div class="modal-body">';

			shpWin += '<div class="form-group">';

			shpWin += '<label for="exampleInputFile1">SHP File Upload</label>';
			shpWin += '<input type="file" id="' + upload1 + '">';
			shpWin += '<p class="help-block">Upload your SHP file</p>';

			shpWin += '<label for="exampleInputFile2">SHX File Upload</label>';
			shpWin += '<input type="file" id="' + upload2 + '">';
			shpWin += '<p class="help-block">Upload your SHX file</p>';

			shpWin += '<label for="exampleInputFile3">DBF File Upload</label>';
			shpWin += '<input type="file" id="' + upload3 + '">';
			shpWin += '<p class="help-block">Upload your DBF file</p>';

			shpWin += '</div>';

			shpWin += '</div>';
			shpWin += '<div class="modal-footer">';
			shpWin += '<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>';
			shpWin += '<button type="button" class="btn btn-primary">Upload</button>';
			shpWin += '</div>';
			shpWin += '</div>';
			shpWin += '</div>';
			shpWin += '</div>';

			$("body").append(shpWin);
		}

		$('#' + gitbuilder.variable.elementid.shpWindow).modal('show');
	}
	// ==========================================================================================================
	/**
	 * @description 새로운 벡터 레이어를 생성
	 */
	gitbuilder.ui.NewVectorWindow = function NewVectorWindow() {
		if (!gitbuilder.variable.elementid.vectorWindow) {
			var vectorLayerWindowId = "vectorWindow";
			var count = 0;
			while (gitbuilder.method.isDuplicatedId(vectorLayerWindowId)) {
				vectorLayerWindowId += count;
			}
			gitbuilder.variable.elementid.vectorWindow = vectorLayerWindowId;

			var pointId = "ptType";
			count = 0;
			while (gitbuilder.method.isDuplicatedId(pointId)) {
				pointId += count;
			}
			gitbuilder.variable.elementid.pointInput = pointId;

			var lineId = "lsType";
			count = 0;
			while (gitbuilder.method.isDuplicatedId(lineId)) {
				lineId += count;
			}
			gitbuilder.variable.elementid.lineInput = lineId;

			var polygonId = "pgType";
			count = 0;
			while (gitbuilder.method.isDuplicatedId(polygonId)) {
				polygonId += count;
			}
			gitbuilder.variable.elementid.polyInput = polygonId;

			var layerType = "lType";
			count = 0;
			while (gitbuilder.method.isDuplicatedName(layerType)) {
				layerType += count;
			}
			gitbuilder.variable.elementid.layerType = layerType;

			var layerNameId = "layerName";
			count = 0;
			while (gitbuilder.method.isDuplicatedId(layerNameId)) {
				layerNameId += count;
			}
			gitbuilder.variable.elementid.layerName = layerNameId;

			var vector = "<div class='modal fade' id='" + vectorLayerWindowId + "' tabindex='-1' role='dialog'>";
			vector += '<div class="modal-dialog">';
			vector += '<div class="modal-content">';
			vector += '<div class="modal-header">';
			vector += '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
			vector += '<h4 class="modal-title">Vector</h4>';
			vector += '</div>';
			vector += '<div class="modal-body">';

			vector += '<form class="form-horizontal">';

			vector += '<div class="form-group">';

			vector += '<label class="col-md-2 control-label">Type</label>';

			vector += '<div class="col-md-3">';
			vector += '<label class="radio-inline">';
			vector += '<input type="radio" name="' + layerType + '" id="' + pointId + '" value="point"> Point';
			vector += '</label>';
			vector += '</div>';
			vector += '<div class="col-md-3">';
			vector += '<label class="radio-inline">';
			vector += '<input type="radio" name="' + layerType + '" id="' + lineId + '" value="linestring"> LineString';
			vector += '</label>';
			vector += '</div>';
			vector += '<div class="col-md-3">';
			vector += '<label class="radio-inline">';
			vector += '<input type="radio" name="' + layerType + '" id="' + polygonId + '" value="polygon"> Polygon';
			vector += '</label>';
			vector += '</div>';
			vector += '<div class="col-md-1">';
			vector += '</div>';
			vector += '</div>';

			vector += '<div class="form-group">';
			vector += '<label for="' + layerNameId + '" class="col-md-2 control-label">Name</label>';
			vector += '<div class="col-md-9">';
			vector += '<input type="text" class="form-control" placeholder="Layer name" id="' + layerNameId + '">';
			vector += '</div>';
			vector += '</div>';

			vector += '<div class="form-group">';
			vector += '<label class="col-md-2 control-label">Stroke</label>';
			vector += '<div class="col-md-2">';
			vector += '<input type="text"	id="strk" />';
			vector += '</div>';
			vector += '<label class="col-md-2 control-label">Width</label>';
			vector += '<div class="col-md-2">';
			vector += '<input type="number" min="0" class="form-control"	id="width" />';
			vector += '</div>';
			vector += '</div>';
			
			vector += '<div class="form-group">';
			vector += '<label class="col-md-2 control-label">Fill</label>';
			vector += '<div class="col-md-2">';
			vector += '<input type="text"	id="fll" />';
			vector += '</div>';
			vector += '<label class="col-md-2 control-label">Radius</label>';
			vector += '<div class="col-md-2">';
			vector += '<input type="number" min="0" class="form-control"	id="radius" />';
			vector += '</div>';
			vector += '</div>';

			vector += '</form>';

			vector += '</div>';
			vector += '<div class="modal-footer">';
			vector += '<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>';
			vector += '<button type="button" class="btn btn-primary">Create</button>';
			vector += '</div>';
			vector += '</div>';
			vector += '</div>';
			vector += '</div>';

			$("body").append(vector);

			$("#strk")
					.spectrum(
							{
								showAlpha : true,
								showInput : true,
								preferredFormat : "rgb",
								showPalette : true,
								palette : [
										[ "rgb(0, 0, 0)", "rgb(67, 67, 67)", "rgb(102, 102, 102)", "rgb(153, 153, 153)", "rgb(183, 183, 183)",
												"rgb(204, 204, 204)", "rgb(217, 217, 217)", "rgb(239, 239, 239)", "rgb(243, 243, 243)",
												"rgb(255, 255, 255)" ],
										[ "rgb(152, 0, 0)", "rgb(255, 0, 0)", "rgb(255, 153, 0)", "rgb(255, 255, 0)", "rgb(0, 255, 0)",
												"rgb(0, 255, 255)", "rgb(74, 134, 232)", "rgb(0, 0, 255)", "rgb(153, 0, 255)", "rgb(255, 0, 255)" ],
										[ "rgb(230, 184, 175)", "rgb(244, 204, 204)", "rgb(252, 229, 205)", "rgb(255, 242, 204)",
												"rgb(217, 234, 211)", "rgb(208, 224, 227)", "rgb(201, 218, 248)", "rgb(207, 226, 243)",
												"rgb(217, 210, 233)", "rgb(234, 209, 220)", "rgb(221, 126, 107)", "rgb(234, 153, 153)",
												"rgb(249, 203, 156)", "rgb(255, 229, 153)", "rgb(182, 215, 168)", "rgb(162, 196, 201)",
												"rgb(164, 194, 244)", "rgb(159, 197, 232)", "rgb(180, 167, 214)", "rgb(213, 166, 189)",
												"rgb(204, 65, 37)", "rgb(224, 102, 102)", "rgb(246, 178, 107)", "rgb(255, 217, 102)",
												"rgb(147, 196, 125)", "rgb(118, 165, 175)", "rgb(109, 158, 235)", "rgb(111, 168, 220)",
												"rgb(142, 124, 195)", "rgb(194, 123, 160)", "rgb(166, 28, 0)", "rgb(204, 0, 0)", "rgb(230, 145, 56)",
												"rgb(241, 194, 50)", "rgb(106, 168, 79)", "rgb(69, 129, 142)", "rgb(60, 120, 216)",
												"rgb(61, 133, 198)", "rgb(103, 78, 167)", "rgb(166, 77, 121)", "rgb(133, 32, 12)", "rgb(153, 0, 0)",
												"rgb(180, 95, 6)", "rgb(191, 144, 0)", "rgb(56, 118, 29)", "rgb(19, 79, 92)", "rgb(17, 85, 204)",
												"rgb(11, 83, 148)", "rgb(53, 28, 117)", "rgb(116, 27, 71)", "rgb(91, 15, 0)", "rgb(102, 0, 0)",
												"rgb(120, 63, 4)", "rgb(127, 96, 0)", "rgb(39, 78, 19)", "rgb(12, 52, 61)", "rgb(28, 69, 135)",
												"rgb(7, 55, 99)", "rgb(32, 18, 77)", "rgb(76, 17, 48)" ] ]
							});

			$("#fll")
					.spectrum(
							{
								showAlpha : true,
								showInput : true,
								preferredFormat : "rgb",
								showPalette : true,
								palette : [
										[ "rgb(0, 0, 0)", "rgb(67, 67, 67)", "rgb(102, 102, 102)", "rgb(153, 153, 153)", "rgb(183, 183, 183)",
												"rgb(204, 204, 204)", "rgb(217, 217, 217)", "rgb(239, 239, 239)", "rgb(243, 243, 243)",
												"rgb(255, 255, 255)" ],
										[ "rgb(152, 0, 0)", "rgb(255, 0, 0)", "rgb(255, 153, 0)", "rgb(255, 255, 0)", "rgb(0, 255, 0)",
												"rgb(0, 255, 255)", "rgb(74, 134, 232)", "rgb(0, 0, 255)", "rgb(153, 0, 255)", "rgb(255, 0, 255)" ],
										[ "rgb(230, 184, 175)", "rgb(244, 204, 204)", "rgb(252, 229, 205)", "rgb(255, 242, 204)",
												"rgb(217, 234, 211)", "rgb(208, 224, 227)", "rgb(201, 218, 248)", "rgb(207, 226, 243)",
												"rgb(217, 210, 233)", "rgb(234, 209, 220)", "rgb(221, 126, 107)", "rgb(234, 153, 153)",
												"rgb(249, 203, 156)", "rgb(255, 229, 153)", "rgb(182, 215, 168)", "rgb(162, 196, 201)",
												"rgb(164, 194, 244)", "rgb(159, 197, 232)", "rgb(180, 167, 214)", "rgb(213, 166, 189)",
												"rgb(204, 65, 37)", "rgb(224, 102, 102)", "rgb(246, 178, 107)", "rgb(255, 217, 102)",
												"rgb(147, 196, 125)", "rgb(118, 165, 175)", "rgb(109, 158, 235)", "rgb(111, 168, 220)",
												"rgb(142, 124, 195)", "rgb(194, 123, 160)", "rgb(166, 28, 0)", "rgb(204, 0, 0)", "rgb(230, 145, 56)",
												"rgb(241, 194, 50)", "rgb(106, 168, 79)", "rgb(69, 129, 142)", "rgb(60, 120, 216)",
												"rgb(61, 133, 198)", "rgb(103, 78, 167)", "rgb(166, 77, 121)", "rgb(133, 32, 12)", "rgb(153, 0, 0)",
												"rgb(180, 95, 6)", "rgb(191, 144, 0)", "rgb(56, 118, 29)", "rgb(19, 79, 92)", "rgb(17, 85, 204)",
												"rgb(11, 83, 148)", "rgb(53, 28, 117)", "rgb(116, 27, 71)", "rgb(91, 15, 0)", "rgb(102, 0, 0)",
												"rgb(120, 63, 4)", "rgb(127, 96, 0)", "rgb(39, 78, 19)", "rgb(12, 52, 61)", "rgb(28, 69, 135)",
												"rgb(7, 55, 99)", "rgb(32, 18, 77)", "rgb(76, 17, 48)" ] ]
							});
		}

		$('#' + gitbuilder.variable.elementid.vectorWindow).modal('show');
	}
	// ==========================================================================================================
}