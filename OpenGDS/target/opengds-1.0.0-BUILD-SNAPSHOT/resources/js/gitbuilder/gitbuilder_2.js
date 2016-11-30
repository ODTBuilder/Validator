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
if (!gitbuilder.validation) {
	gitbuilder.validation = {};

	// 사용가능 검수항목
	gitbuilder.validation.validationOption = {
		point : [ "EntityDuplicated", "SelfEntity", "AttributeFix", "OutBoundary" ],
		linestring : [ "SmallLength", "EntityDuplicated", "SelfEntity", "PointDuplicated", "ConIntersected", "ConOverDegree", "ConBreak",
				"AttributeFix", "OutBoundary", "zValueAmbiguous", "UselessPoint" ],
		polygon : [ "SmallArea", "EntityDuplicated", "SelfEntity", "PointDuplicated", "AttributeFix", "OutBoundary" ]
	}

	gitbuilder.validation.option = {};

	// 검수 항목 객체(객체 중복)
	gitbuilder.validation.option.EntityDuplicated = {}

	// 검수 항목 객체(객체 겹침)
	gitbuilder.validation.option.SelfEntity = {
		relation : []
	}

	// 검수 항목 객체(속성 오류)
	gitbuilder.validation.option.AttributeFix = {
		attribute : {}
	}

	// 검수 항목 객체(최소 길이)
	gitbuilder.validation.option.SmallLength = {
		length : 3
	}

	// 검수 항목 객체(중복점)
	gitbuilder.validation.option.PointDuplicated = {}

	// 검수 항목 객체(등고선 교차)
	gitbuilder.validation.option.ConIntersected = {}

	// 검수 항목 객체(등고선 꺾임)
	gitbuilder.validation.option.ConOverDegree = {
		degree : 90
	}

	// 검수 항목 객체(등고선 끊김)
	gitbuilder.validation.option.ConBreak = {}

	// 검수 항목 객체(최소 넓이)
	gitbuilder.validation.option.SmallArea = {
		area : 3
	}

	// 검수 항목 객체(고도값유무)
	gitbuilder.validation.option.zValueAmbiguous = {
		attribute : {}
	}

	// 검수 항목 객체(경계)
	gitbuilder.validation.option.OutBoundary = {
		relation : []
	}

	// 검수 항목 객체(불필요점)
	gitbuilder.validation.option.UselessPoint = {}

}
if (!gitbuilder.variable) {

	// 변수객체
	gitbuilder.variable = {};

	// 커서 위치
	gitbuilder.variable.crsrPosition = [];

	// 커서 위치(좌표)
	gitbuilder.variable.crsrCoordinate = {};

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

	// 오픈 레이어스 인터랙션 객체
	gitbuilder.variable.intrctn = {
		draw : undefined,
		dragBox : undefined,
		select : undefined,
		modify : undefined,
		translate : undefined,
		snap : undefined,
		validationArea : undefined,
	};

	// 피처 속성 오버레이 객체
	gitbuilder.variable.overlay = {
		feature : new ol.Overlay({}),
		edit : new ol.Overlay({})
	};

	// 검수 전송 객체
	gitbuilder.variable.validationObj = {
		extent : undefined,
		layers : {}
	};

	// 공간 연산 전송 객체
	gitbuilder.variable.operationObj = {
		operator : {
			operationName : undefined,
			layers : {}
		}
	};

	// 현재 에러 네비게이팅 중인 객체
	gitbuilder.variable.navigatingFeature = {};

	// 현재 검수옵션 선택중인 객체
	gitbuilder.variable.validationLayer = {};

	// 검수영역 레이어
	gitbuilder.variable.validationArea = {};

	// 현재 선택한 검수옵션
	gitbuilder.variable.validationOption = {};

	// 선택한 레이어 배열
	gitbuilder.variable.selectedLayers = new ol.Collection();

	// 선택한 피쳐 배열
	gitbuilder.variable.selectedFeatures = new ol.Collection();

	// 선택한 피쳐 =김호철=
	gitbuilder.variable.selectedFeature = new ol.Feature();

	// 선택한 피쳐 Geometry =김호철=
	gitbuilder.variable.selectedGeometry = {};

	// 선택한 피쳐 중점 =김호철=
	gitbuilder.variable.flatInteriorPoint = [];

	// 선택한 에러피처
	gitbuilder.variable.errorFeatures = new ol.Collection();

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
	// strokeColor: 선색
	// strokeWidth: 선너비 인풋
	// fillColor: 면색
	// fillRadius: 면(점) 반지름 인풋
	// attrName: 속성명 텍스트인풋
	// attrType: 속성 데이터 타입 실렉트
	// attrList: 속성 리스트 테이블 바디
	// removeFeatureWindow: 피처 삭제 창
	// validatingWindow: 검수 설정 창
	gitbuilder.variable.elementid = {};

	// input name
	// layerType: 레이어 타입 인풋 네임
	gitbuilder.variable.inputname = {};

	// 메뉴 이름변경 또는 로컬라이징
	gitbuilder.variable.menuname = {};

	// 에러 리포트
	gitbuilder.variable.errReport = {};

	// 현재 편집중
	gitbuilder.variable.editingOpen = false;

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

		map.on('singleclick', function(evt) {
			gitbuilder.variable.crsrCoordinate = evt.coordinate;
			gitbuilder.ui.CloseAttributeTable();
		});

		map.on('click', function(evt) {
			gitbuilder.variable.selectedFeatures.clear();
			if (document.getElementsByClassName('hoicon').length !== 0) {
				// feature 편집 mbr을 삭제한다 -김호철-
				gitbuilder.ui.RemoveEditToolTip();
			}
		});

		map.on('pointerdrag', function(evt) {

		});

		map.on('pointermove', function(evt) {
			if (evt.dragging) {
				return;
			}

			if (gitbuilder.variable.intrctn.draw !== undefined) {
				var coordinate = gitbuilder.variable.map.getEventCoordinate(evt.originalEvent);
				gitbuilder.ui.SetCursorPositionInfo(coordinate);
			}

			if (gitbuilder.variable.selectedFeatures.getLength() === 1 && gitbuilder.variable.intrctn.select !== undefined) {
				if (gitbuilder.variable.intrctn.select.get('rotate') === true) {
					var elementTitle = document.getElementsByClassName('hoicon')[0].getAttribute('title');
					var prev = gitbuilder.variable.crsrPosition;
					var current = gitbuilder.variable.map.getEventCoordinate(evt.originalEvent);
					var rad = gitbuilder.method.RotateAlgorithm(prev, current);

					// shift키 누른채로 mouse move시 rotate 회전 속도가 절반으로 줄어듬
					if (evt.originalEvent.shiftKey === true) {
						gitbuilder.variable.selectedGeometry.rotate(rad / 2, gitbuilder.variable.flatInteriorPoint);
					} else {
						gitbuilder.variable.selectedGeometry.rotate(rad, gitbuilder.variable.flatInteriorPoint);
					}
					// 클릭한 버튼의 아이콘이 마우스를 따라 오도록함
					gitbuilder.ui.SetIconPosition(elementTitle, current);
					gitbuilder.variable.crsrPosition = current;
				}

				if (gitbuilder.variable.intrctn.select.get('scale') === true) {
					var cursorPoint = gitbuilder.variable.map.getEventCoordinate(evt.originalEvent);
					var elementTitle = document.getElementsByClassName('hoicon')[0].getAttribute('title');
					var extent = gitbuilder.variable.selectedGeometry.getExtent();
					var magni = gitbuilder.method.ScaleAlgorithm([ extent[2], extent[3] ], cursorPoint);
					var center = gitbuilder.variable.flatInteriorPoint;

					if (magni[0] > magni[1]) {
						gitbuilder.variable.selectedGeometry.scale(magni[0], magni[0], center);
					} else {
						gitbuilder.variable.selectedGeometry.scale(magni[1], magni[1], center);
					}
					gitbuilder.ui.SetIconPosition(elementTitle, cursorPoint);
					gitbuilder.variable.crsrPosition = cursorPoint;
				}
			}
		});

		map.on('postcompose', function(evt) {
			if (gitbuilder.variable.selectedFeatures.getLength() === 1) {
				if (gitbuilder.variable.map.getOverlayById('editTip0') !== null) {
					gitbuilder.ui.DrawNbr(evt);
				}
			}
		});

		// command 입력창 생성
		gitbuilder.command.ui.AutoComplete();
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
			layerKeysStr += "<li layerid='" + addedLayers[i].get("id") + "' class='GitBuilder-LayerList-Item'>";
			layerKeysStr += "<div>";

			layerKeysStr += addedLayers[i].get("name");

			var legend = gitbuilder.method.layer.CreateLegend(addedLayers[i]);
			if (legend) {
				layerKeysStr += "<div style='float:right;'>";
				layerKeysStr += legend;
				layerKeysStr += "</div>";
			}

			layerKeysStr += "</div>";
			layerKeysStr += '<div class="GitBuilder-LayerList-Function">';
			layerKeysStr += "<div class='text-center'>";
			layerKeysStr += '<button type="button" class="btn btn-default GitBuilder-LayerList-Function-Button GitBuilder-LayerList-Function-Show" layerid="'
					+ addedLayers[i].get("id") + '">';
			layerKeysStr += '<span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>';
			layerKeysStr += '<span class="glyphicon glyphicon-eye-close" aria-hidden="true"></span>';
			layerKeysStr += '</button>';
			layerKeysStr += '<button type="button" class="btn btn-default GitBuilder-LayerList-Function-Button GitBuilder-LayerList-Function-Fit" layerid="'
					+ addedLayers[i].get("id") + '">';
			layerKeysStr += '<span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>';
			layerKeysStr += '</button>';
			layerKeysStr += '<button type="button" class="btn btn-default GitBuilder-LayerList-Function-Button GitBuilder-LayerList-Function-Setting" layerid="'
					+ addedLayers[i].get("id") + '">';
			layerKeysStr += '<span class="glyphicon glyphicon-text-size" aria-hidden="true"></span>';
			layerKeysStr += '</button>';
			layerKeysStr += '<button type="button" class="btn btn-default GitBuilder-LayerList-Function-Button GitBuilder-LayerList-Function-Field" layerid="'
					+ addedLayers[i].get("id") + '">';
			layerKeysStr += '<span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>';
			layerKeysStr += '</button>';
			layerKeysStr += '<button type="button" class="btn btn-default GitBuilder-LayerList-Function-Button GitBuilder-LayerList-Function-Remove" layerid="'
					+ addedLayers[i].get("id") + '">';
			layerKeysStr += '<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>';
			layerKeysStr += '</button>';
			layerKeysStr += "</div>";
			layerKeysStr += "</div>";
			layerKeysStr += "</li>";

			$("#" + layerListId).prepend(layerKeysStr);

		}
		$(".glyphicon-eye-close").hide();
		$(".GitBuilder-LayerList-Function").hide();
		$("#" + layerListId).sortable("refresh").addClass("layerSelectable").selectable("refresh").find("li").addClass("ui-corner-all").prepend(
				"<span class='GitBuilder-LayerList-Item-Handle'><span class='glyphicon glyphicon-sort' aria-hidden='true'></span></span>");
	}
	// ==========================================================================================================
	/**
	 * @description 레이어 삭제
	 * @param {String}
	 *            레이어 아이디
	 */
	gitbuilder.method.layer.RemoveLayerOnList = function RemoveLayerOnList(layer) {
		var layer = gitbuilder.method.layer.getLayerById(layer);
		if (layer.get("id") === "Validation Result") {
			gitbuilder.ui.CloseNavigatorWindow();
		}
		gitbuilder.variable.map.removeLayer(layer);
		gitbuilder.variable.selectedLayers.clear();
		gitbuilder.method.layer.updateLayerList();
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
	 * @description 유일한 피처 아이디를 생성
	 * @param {ol.layer.Vector ||
	 *            ol.layer.Image} 피처를 생성할 레이어
	 * @param {ol.Feature}
	 *            아이디를 부여할 피처
	 * @returns {ol.Feature} 아이디를 가진 피처
	 */
	gitbuilder.method.layer.createFeatureId = function createFeatureId(layer, feature) {
		var lid = layer.get("id");

		var source;

		if (layer instanceof ol.layer.Vector) {
			source = layer.getSource();
		} else if (layer instanceof ol.layer.Image) {
			if (layer.getSource() instanceof ol.source.ImageVector) {
				source = layer.getSource().getSource();
			} else {
				console.error("Image layer is not imagevector layer");
			}
		}

		var features = source.getFeatures();

		if (features.length > 0) {
			var max = 1;
			for (var i = 0; i < features.length; i++) {
				var fid = features[i].getId();
				var num = parseInt(fid.substring(fid.indexOf(".") + 1));
				if (num > max) {
					max = num;
				} else {
					continue;
				}
			}
			max++;
			var feat = features[0].getId();
			var lid = feat.substring(0, feat.indexOf("."));
			var fid = lid + "." + max;
			feature.setId(fid);
			return feature;
		} else if (features.length === 0) {
			var fid = layer.get("id") + ".1";
			feature.setId(fid);
			return feature;
		}
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
	// ==========================================================================================================
	/**
	 * @description 레이어 영역으로 확대
	 * @returns {ol.layer.Vector} 확대할 벡터 레이어
	 */
	gitbuilder.method.layer.zoomToLayerExtent = function zoomToLayerExtent(layer) {

		if (!(layer instanceof ol.layer.Vector || (layer instanceof ol.layer.Image && layer.getSource() instanceof ol.source.ImageVector))) {
			console.error("not vector layer");
			return;
		}
		var source;
		if (layer instanceof ol.layer.Vector) {
			source = layer.getSource();
			gitbuilder.variable.view.fit(source.getExtent(), gitbuilder.variable.map.getSize());
		} else if (layer instanceof ol.layer.Image && layer.getSource() instanceof ol.source.ImageVector) {
			source = layer.getSource().getSource();
			gitbuilder.variable.view.fit(source.getExtent(), gitbuilder.variable.map.getSize());
		}

	}
	// ==========================================================================================================
	/**
	 * @description 벡터 레이어 생성시 사용자 선택 옵션을 가져온다.
	 * @returns {Object} 레이어 옵션
	 */
	gitbuilder.method.layer.getVectorOption = function getVectorOption() {
		var layerType = $(':radio[name="' + gitbuilder.variable.elementid.layerType + '"]:checked').val();
		var layerName = $("#" + gitbuilder.variable.elementid.layerName).val();
		var tBodyArr = $("#" + gitbuilder.variable.elementid.attrList).eq(0);
		var tBody = tBodyArr[0];
		var tr = $(tBody).children();

		var listObj = {};
		for (var i = 0; i < tr.length; i++) {
			listObj[$(tr[i]).find("td").eq(1).text()] = $(tr[i]).find("td").eq(2).text();
			// console.log($(tr[0]).find( "td" ).eq(1).text());
			// console.log($(tr[0]).find( "td" ).eq(2).text());
		}
		var result = {
			layerType : layerType,
			layerName : layerName,
			attributeList : listObj
		};
		// console.log(result);
		return result;
	}
	// ==========================================================================================================
	/**
	 * @description 레이어 범례 생성
	 * @param {ol.layer.Vector ||
	 *            ol.layer.Image} 범례를 표시할 백터 레이어
	 * @returns {String} 범례 div 태그 형태
	 */
	gitbuilder.method.layer.CreateLegend = function CreateLegend(layer) {

		if ((layer instanceof ol.layer.Vector) || (layer instanceof ol.layer.Image && layer.getSource() instanceof ol.source.ImageVector)) {
			var type = layer.get("type");
			var style;
			if (layer instanceof ol.layer.Vector) {
				style = layer.getStyle();
			} else if (layer instanceof ol.layer.Image) {
				if (layer.getSource() instanceof ol.source.ImageVector) {
					style = layer.getSource().getStyle();
				} else {
					console.error("The image layer is not image vector layer");
				}
			}
			var legend = "";
			legend += '<div class="GitBuilder-Layer-Legend" ';
			if (type === "Point" || type === "MultiPoint") {

				var image = style.getImage();
				if (image instanceof ol.style.Circle) {
					console.log("point - circle");
					var fill = image.getFill();
					var fillColor = fill.getColor();
					console.log(fillColor);
					var stroke = image.getStroke();
					var strokeColor = stroke.getColor();
					console.log(strokeColor);

					// $(legend).css();
					var strokeStyleCode = "2px solid " + strokeColor;

					legend += 'style="margin: 4px; width: 14px;height:14px; border-radius: 14px; border:' + strokeStyleCode + '; background-color:'
							+ fillColor + ';' + '"';
					legend += '>';
					legend += '</div>';
					// console.log(legend);

					// $(legend).css();
				} else if (image instanceof ol.style.Icon) {
					console.log("point - icon");
					var url = style.getSrc();
				} else if (image instanceof ol.style.RegularShape) {
					console.log("point - regular");
				}

			} else if (type === "LineString" || type === "MultiLineString") {

				var stroke = style.getStroke();
				if (stroke instanceof ol.style.Stroke) {
					console.log("line - stroke");
					var strokeColor = stroke.getColor();
					// console.log(strokeColor);

					legend += '>';

					var strokeStyleCode = "2px solid " + strokeColor;
					var strokeStyleCode2 = "1px solid " + strokeColor;
					legend += '<div style="display: block;width: 20px;height:8px;  border-top:' + strokeStyleCode + ';' + 'border-bottom:'
							+ strokeStyleCode2 + ';' + '">';
					legend += '</div>';

					legend += '<div style="display: block;width: 20px;height:8px;  border-top:' + strokeStyleCode2 + ';' + 'border-bottom:'
							+ strokeStyleCode + ';' + '">';
					legend += '</div>';

					legend += '</div>';
					// console.log(legend);
				} else {
					console.error("no style for linestring or multilinestring");
				}
			} else if (type === "Polygon" || type === "MultiPolygon") {

				var fill = style.getFill();
				var stroke = style.getStroke();
				if (fill instanceof ol.style.Fill && stroke instanceof ol.style.Stroke) {
					console.log("polygon - fill");
					var fillColor = fill.getColor();
					// console.log(fillColor);

					var strokeColor = stroke.getColor();
					console.log(strokeColor);

					// $(legend).css();
					var strokeStyleCode = "2px solid " + strokeColor;

					legend += 'style="width: 20px;height:20px; border:' + strokeStyleCode + '; background-color:' + fillColor + ';' + '"';
					legend += '>';
					legend += '</div>';
					// console.log(legend);

				} else {
					console.error("no style for polygon");
				}
			}
			return legend;
		} else {
			console.error("not ol.layer.Vector");
			return false;
		}
	}
	// ==========================================================================================================
	/**
	 * @description 벡터 레이어를 생성
	 * @param {ol.source.Vector}
	 *            벡터 소스
	 * @param {ol.style.Style}
	 *            벡터 스타일
	 * @returns {ol.layer.Vector} 벡터 레이어
	 */
	gitbuilder.method.layer.createVectorLayer = function createVectorLayer(originSource, originStyle) {
		var layer;

		var source = originSource;
		// 소스 파라미터가 비어있다면 빈소스로 초기화
		if (source === null || source === undefined) {
			source = new ol.source.Vector({
				wrapX : true
			});
		}
		var style = originStyle;
		if (style === null || style === undefined) {
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

			style = styles;
		}

		layer = new ol.layer.Vector({
			source : source,
			style : style
		});

		return layer;
	}
	// ==========================================================================================================
	/**
	 * @description 이미지 벡터 레이어를 생성
	 * @param {ol.source.Vector}
	 *            벡터 소스
	 * @param {ol.style.Style}
	 *            벡터 스타일
	 * @returns {ol.layer.Image} 이미지 벡터 레이어
	 */
	gitbuilder.method.layer.createImageVectorLayer = function createImageVectorLayer(originSource, originStyle) {
		var layer;

		var source = originSource;
		// 소스 파라미터가 비어있다면 빈소스로 초기화
		if (source === null || source === undefined) {
			source = new ol.source.Vector({
				wrapX : true
			});
		}
		var style = originStyle;
		if (style === null || style === undefined) {
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

			style = styles;
		}

		layer = new ol.layer.Image({
			source : new ol.source.ImageVector({
				source : source,
				style : style
			})
		});

		return layer;
	}
	// ==========================================================================================================
	/**
	 * @description 벡터 레이어 생성시 사용되는 함수 묶음 이벤트 연결용
	 */
	gitbuilder.method.layer.createVectorLayerBind = function createVectorLayerBind() {
		var layerOpt = gitbuilder.method.layer.getVectorOption();
		var layer = gitbuilder.method.layer.createVectorLayer();
		var layerId = gitbuilder.method.layer.createLayerId();
		/*
		 * { layer: 레이어 객체 name: 레이어 이름 id: 레이어 아이디 type: 레이어 타입 - 폴리곤 cat: 레이어
		 * 종류 - 0: 승인, 1: 비승인, 2: 오류 edit: 수정가능 - 불린 attrType: 피처 속성 {키: 데이터 타입,
		 * ...} }
		 */

		var obj = {
			layer : layer,
			name : layerOpt.layerName,
			id : layerId,
			type : layerOpt.layerType,
			cat : 1,
			edit : true,
			attrType : layerOpt.attributeList
		};

		gitbuilder.method.layer.setLayerProperties(obj);

		gitbuilder.method.layer.addLayerOnList(layer);

		// undo를 위한 data set -김호철-
		gitbuilder.command.SetUndoData('layerHide', layer, null);

		$('#' + gitbuilder.variable.elementid.vectorWindow).modal('hide');

	}
	// ==========================================================================================================
	/**
	 * @description 레이어 아이디를 통한 반환
	 */
	gitbuilder.method.layer.getLayerById = function getLayerById(_id) {
		var layers = gitbuilder.variable.map.getLayers().getArray();
		for (var i = 0; i < layers.length; i++) {
			if (layers[i].get("id") === _id) {
				return layers[i];
			}
		}
	}
	// ==========================================================================================================
	/**
	 * @description 랜덤 컬러 생성
	 * @returns {String} 색상코드
	 */
	gitbuilder.method.layer.getRandomColor = function getRandomColor() {
		var letters = '0123456789ABCDEF';
		var color = '#';
		for (var i = 0; i < 6; i++) {
			color += letters[Math.floor(Math.random() * 16)];
		}
		return color;
	}
	// ==========================================================================================================
	/**
	 * @description rgba 변환
	 * @param {String}
	 *            hex 코드
	 * @param {String}
	 *            투명도
	 * @returns {String} 색상코드
	 */
	gitbuilder.method.layer.convertHex = function convertHex(hex, opacity) {
		hex = hex.replace('#', '');
		r = parseInt(hex.substring(0, 2), 16);
		g = parseInt(hex.substring(2, 4), 16);
		b = parseInt(hex.substring(4, 6), 16);

		// Add Opacity to RGB to obtain RGBA
		result = 'rgba(' + r + ',' + g + ',' + b + ',' + opacity / 100 + ')';
		return result;
	}
	// ==========================================================================================================
	/**
	 * @description 모든 벡터 레이어 반환
	 * @returns {ol.Collection} 벡터 레이어 콜렉션
	 */
	gitbuilder.method.layer.getAllVectorLayer = function getAllVectorLayer() {
		var layers = gitbuilder.variable.map.getLayers();
		var vectorLayers = new ol.Collection();

		for (var i = 0; i < layers.getLength(); i++) {
			if ((layers.item(i) instanceof ol.layer.Vector || (layers.item(i) instanceof ol.layer.Image && layers.item(i).getSource() instanceof ol.source.ImageVector))
					&& (layers.item(i).get("type") === "Point" || layers.item(i).get("type") === "LineString"
							|| layers.item(i).get("type") === "Polygon" || layers.item(i).get("type") === "MultiPoint"
							|| layers.item(i).get("type") === "MultiLineString" || layers.item(i).get("type") === "MultiPolygon")) {
				vectorLayers.push(layers.item(i));
			}
		}

		return vectorLayers;
	}
	// ==========================================================================================================
	/**
	 * @description 에러 레이어 반환
	 * @returns {ol.Collection} 벡터 레이어 콜렉션
	 */
	gitbuilder.method.layer.getErrorLayer = function getErrorLayer() {
		var layers = gitbuilder.variable.map.getLayers();
		var vectorLayers = new ol.Collection();

		for (var i = 0; i < layers.getLength(); i++) {
			if ((layers.item(i) instanceof ol.layer.Vector || (layers.item(i) instanceof ol.layer.Image && layers.item(i).getSource() instanceof ol.source.ImageVector))
					&& (layers.item(i).get("type") === "Point" || layers.item(i).get("type") === "MultiPoint") && layers.item(i).get("cat") === 2) {
				vectorLayers.push(layers.item(i));
			}
		}

		return vectorLayers;
	}
	// ==========================================================================================================
	/**
	 * @description 선택 레이어 변경
	 * @param {ol.layer.Base}
	 *            createdLayer - 추가할 레이어
	 */
	gitbuilder.method.layer.SelectLayerSelectable = function SelectLayerSelectable(element) {

		var selectable = $("#" + gitbuilder.variable.elementid.layerList);

		$(".ui-selected", selectable).not(element).removeClass("ui-selected").addClass("ui-unselecting");

		$(element).not(".ui-selected").addClass("ui-selecting");

		selectable.data("ui-selectable")._mouseStop(null);
	}
	// ==========================================================================================================
	gitbuilder.method.edit = {};
	/**
	 * @description 피처를 생성한다.
	 */
	// gitbuilder.variable.intrctn.snap
	gitbuilder.method.edit.DrawFeature = function DrawFeature() {
		gitbuilder.method.edit.RemoveAllBuilderInteraction();

		gitbuilder.ui.CloseAttributeTable();
		// feature 편집 mbr을 삭제한다 -김호철-
		gitbuilder.ui.RemoveEditToolTip();
		// cursor position 정보 ui 생성 -김호철-
		gitbuilder.ui.CursorPositionInfo();

		var layers = gitbuilder.variable.selectedLayers;
		if (layers.getLength() !== 1) {
			console.error("There are no selected layers: variable - gitbuilder.variable.selectedLayers");
			return;
		}

		var selectedLayer = layers.item(0);
		var value = selectedLayer.get("type");
		var source;
		if (selectedLayer instanceof ol.layer.Vector) {
			source = selectedLayer.getSource();
		} else if (selectedLayer instanceof ol.layer.Image && selectedLayer.getSource() instanceof ol.source.ImageVector) {
			source = selectedLayer.getSource().getSource();
		}

		if (value !== "None") {
			var geometryFunction, maxPoints;
			if (value === "Square") {
				value = "Circle";
				geometryFunction = ol.interaction.Draw.createRegularPolygon(4);
			} else if (value === "Box") {
				value = "LineString";
				maxPoints = 2;
				geometryFunction = function(coordinates, geometry) {
					if (!geometry) {
						geometry = new ol.geom.Polygon(null);
					}
					var start = coordinates[0];
					var end = coordinates[1];
					geometry.setCoordinates([ [ start, [ start[0], end[1] ], end, [ end[0], start[1] ], start ] ]);
					return geometry;
				};
			}

			draw = new ol.interaction.Draw({
				source : source,
				type : /** @type {ol.geom.GeometryType} */
				(value),
				geometryFunction : geometryFunction,
				maxPoints : maxPoints
			});
			draw.set("name", "draw");

			draw.on('drawstart', function(e) {
				var feature = e.feature;
				// console.log(feature);
				var geom = feature.getGeometry();
				var point = new ol.geom.Point();
				point.setCoordinates(geom.getFirstCoordinate(), "XY");
				var featureAfter = new ol.Feature();
				featureAfter.setGeometry(point);

				gitbuilder.variable.intrctn.snap.addFeature(featureAfter);
				console.log("added point");
			});

			draw.on('drawend', function(e) {

				var layers = gitbuilder.variable.selectedLayers;
				if (layers.getLength() !== 1) {
					console.error("There are no selected layers: variable - gitbuilder.variable.selectedLayers");
					return;
				}

				var selectedLayer = layers.item(0);

				var feature = e.feature;
				// console.log(feature);
				gitbuilder.method.layer.createFeatureId(selectedLayer, feature);

				var keys = Object.keys(selectedLayer.get("attribute"));
				if (keys.length > 0) {
					gitbuilder.ui.OutputAttributeWindow(feature, selectedLayer.get("attribute"));
				}

				// undo를 위한 data set -김호철-
				gitbuilder.command.SetUndoData('featureHide', selectedLayer, feature);

				gitbuilder.variable.intrctn.snap.addFeature(feature);
			});

			// gitbuilder.variable.intrctn = {
			// draw : undefined,
			// dragBox : undefined,
			// select : undefined,
			// modify : undefined,
			// translate : undefined,
			// snap : undefined
			// };

			gitbuilder.variable.intrctn.draw = draw;
			gitbuilder.variable.map.addInteraction(draw);

			var featureCollection = new ol.Collection();
			var layers = gitbuilder.method.layer.getAllVectorLayer();
			var map = gitbuilder.variable.map;
			var extent = map.getView().calculateExtent(map.getSize());

			for (var i = 0; i < layers.getLength(); i++) {
				if (layers.item(i) instanceof ol.layer.Vector) {
					var source = layers.item(i).getSource();
					source.forEachFeatureIntersectingExtent(extent, function(feature) {
						featureCollection.push(feature);
					});
				} else if (layers.item(i) instanceof ol.layer.Image && layers[i].item(i).getSource() instanceof ol.source.ImageVector) {
					var source = layers.item(i).getSource().getSource();
					source.forEachFeatureIntersectingExtent(extent, function(feature) {
						featureCollection.push(feature);
					});
				}
			}

			gitbuilder.variable.intrctn.snap = new ol.interaction.Snap({
				features : featureCollection
			});
			gitbuilder.variable.map.addInteraction(gitbuilder.variable.intrctn.snap);

		} else if (value === null || value === undefined) {
			console.error("There is no type");
			return;
		}
	}
	// ==========================================================================================================
	/**
	 * @description 빌더에서 추가한 인터랙션을 삭제한다.
	 */
	gitbuilder.method.edit.RemoveAllBuilderInteraction = function RemoveAllBuilderInteraction() {
		var intrctn = gitbuilder.variable.intrctn;

		// gitbuilder.variable.intrctn = {
		// draw : undefined,
		// dragBox : undefined,
		// select : undefined,
		// modify : undefined,
		// translate : undefined,
		// snap : undefined,
		// validationArea : undefined
		// };

		var key = Object.keys(intrctn);
		for (var i = 0; i < key.length; i++) {
			// console.log(intrctn[key[i]]);
			gitbuilder.variable.map.removeInteraction(intrctn[key[i]]);
			intrctn[key[i]] = undefined;
		}
		// gitbuilder.variable.map.removeInteraction(gitbuilder.variable.intrctn.draw);
		// gitbuilder.variable.map.removeInteraction(gitbuilder.variable.intrctn.dragBox);
		// gitbuilder.variable.map.removeInteraction(gitbuilder.variable.intrctn.select);
		// gitbuilder.variable.map.removeInteraction(gitbuilder.variable.intrctn.modify);
		// gitbuilder.variable.map.removeInteraction(gitbuilder.variable.intrctn.translate);
		// gitbuilder.variable.map.removeInteraction(gitbuilder.variable.intrctn.snap);
		//
		// gitbuilder.variable.intrctn.draw = undefined;
		// gitbuilder.variable.intrctn.dragBox = undefined;
		// gitbuilder.variable.intrctn.select = undefined;
		// gitbuilder.variable.intrctn.modify = undefined;
		// gitbuilder.variable.intrctn.translate = undefined;
		// gitbuilder.variable.intrctn.snap = undefined;

	}
	// ==========================================================================================================
	gitbuilder.method.feature = {};
	// ==========================================================================================================
	/**
	 * @description 피처를 반환한다.
	 */
	gitbuilder.method.feature.getFeatureByIdAndLayer = function getFeatureByIdAndLayer(layer, id) {
		if (layer instanceof ol.layer.Vector || (layer instanceof ol.layer.Image && layer.getSource() instanceof ol.source.ImageVector)) {
			var features;
			if (layer instanceof ol.layer.Vector) {
				features = layer.getSource().getFeatures();
			} else if (layer instanceof ol.layer.Image && layer.getSource() instanceof ol.source.ImageVector) {
				features = layer.getSource().getSource().getFeatures();
			}

			for (var i = 0; i < features.length; i++) {
				if (features[i].getId() === id) {
					return features[i];
				}
			}
		}
	}
	// ==========================================================================================================
	/**
	 * @description 객체를 선택한다.
	 */
	gitbuilder.method.edit.SelectFeature = function SelectFeature(features) {
		gitbuilder.method.edit.RemoveAllBuilderInteraction();

		// feature 편집 mbr을 삭제한다 -김호철-
		gitbuilder.ui.RemoveEditToolTip();

		var layers = gitbuilder.variable.selectedLayers;
		if (layers.getLength() !== 1) {
			console.error("Selected layer must be one, check [gitbuilder.variable.selectedLayers]");
			return;
		}

		var selectedLayer = layers.item(0);

		var styles = [ new ol.style.Style({
			stroke : new ol.style.Stroke({
				color : 'rgba(0,153,255,1)',
				width : 2
			}),
			fill : new ol.style.Fill({
				color : 'rgba(255, 255, 255, 0.5)'
			})
		}), new ol.style.Style({
			image : new ol.style.Circle({
				radius : 10,
				fill : new ol.style.Fill({
					color : 'rgba(0,153,255,0.4)'
				})
			}),
			geometry : function(feature) {

				var coordinates;
				var geom;

				if (feature.getGeometry() instanceof ol.geom.MultiPolygon) {
					coordinates = feature.getGeometry().getCoordinates()[0][0];
					geom = new ol.geom.MultiPoint(coordinates);
				} else if (feature.getGeometry() instanceof ol.geom.Polygon) {
					coordinates = feature.getGeometry().getCoordinates()[0];
					geom = new ol.geom.MultiPoint(coordinates);
				} else if (feature.getGeometry() instanceof ol.geom.MultiLineString) {
					coordinates = feature.getGeometry().getCoordinates()[0];
					geom = new ol.geom.MultiPoint(coordinates);
				} else if (feature.getGeometry() instanceof ol.geom.LineString) {
					coordinates = feature.getGeometry().getCoordinates();
					geom = new ol.geom.MultiPoint(coordinates);
				} else if (feature.getGeometry() instanceof ol.geom.MultiPoint) {
					coordinates = feature.getGeometry().getCoordinates();
					geom = new ol.geom.MultiPoint(coordinates);
				} else if (feature.getGeometry() instanceof ol.geom.Point) {
					coordinates = [ feature.getGeometry().getCoordinates() ];
					geom = new ol.geom.MultiPoint(coordinates);
				}

				return geom;
			}
		}) ];

		select = new ol.interaction.Select({
			style : styles,
			layers : [ selectedLayer ],
			features : features,
			wrapX : false
		});

		select.set("name", "select");
		gitbuilder.variable.selectedFeatures = select.getFeatures();

		select.on("select", function(e) {

			var features = e.selected;
			if (features.length === 1) {
				if (selectedLayer.get("id") === "Validation Result") {
					gitbuilder.variable.errorFeatures.clear();
					gitbuilder.variable.errorFeatures.push(features[0]);
					console.log("선택한 에러 피처는 다음과 같습니다.");
					// console.log(gitbuilder.variable.errorFeatures);
				}

				var str = gitbuilder.ui.OutputAttributeTable(features[0]);
				// $("body").append(str);
				var popovr = $(str).get(0);
				var popup = gitbuilder.variable.overlay.feature;
				popup.setElement(popovr);
				// gitbuilder.variable.overlay.getElement().popover('hide');
				gitbuilder.variable.map.addOverlay(popup);
				var element = popup.getElement();

				$(element).popover('destroy');
				popup.setPosition(gitbuilder.variable.crsrCoordinate);
				// the keys are quoted to prevent renaming in ADVANCED mode.
				$(element).popover({
					'placement' : 'top',
					'animation' : false,
					'html' : true
				});
				$(element).popover('show');

			} else {
				gitbuilder.ui.CloseAttributeTable();
			}

		});

		gitbuilder.variable.intrctn.select = select;
		gitbuilder.variable.map.addInteraction(select);

		var dragBox = new ol.interaction.DragBox({
			condition : ol.events.condition.platformModifierKeyOnly
		});

		// clear selection when drawing a new box and when clicking on the map
		dragBox.on('boxstart', function() {
			gitbuilder.variable.selectedFeatures.clear();
		});

		dragBox.on('boxend', function() {
			// features that intersect the box are added to the collection of
			// selected features, and their names are displayed in the "info"
			// div
			var extent = dragBox.getGeometry().getExtent();
			if (selectedLayer instanceof ol.layer.Vector) {
				selectedLayer.getSource().forEachFeatureIntersectingExtent(extent, function(feature) {
					gitbuilder.variable.selectedFeatures.push(feature);
				});
			} else if (selectedLayer instanceof ol.layer.Image && selectedLayer.getSource() instanceof ol.source.ImageVector) {
				selectedLayer.getSource().getSource().forEachFeatureIntersectingExtent(extent, function(feature) {
					gitbuilder.variable.selectedFeatures.push(feature);
				});
			}

			gitbuilder.ui.CloseAttributeTable();
		});

		gitbuilder.variable.intrctn.dragBox = dragBox;
		gitbuilder.variable.map.addInteraction(dragBox);

	}
	// ==========================================================================================================
	/**
	 * @description 검수할 영역을 선택한다.
	 */
	gitbuilder.method.edit.SelectValidationArea = function SelectValidationArea() {
		gitbuilder.method.edit.RemoveAllBuilderInteraction();
		gitbuilder.ui.CloseAttributeTable();

		var dragBox = new ol.interaction.DragBox({});

		dragBox.on('boxstart', function() {
			// gitbuilder.variable.selectedFeatures.clear();
			gitbuilder.variable.validationObj = {
				extent : undefined,
				layers : {}
			};
		});

		dragBox.on('boxend', function() {

			var layers = gitbuilder.method.layer.getAllVectorLayer();
			var extent = dragBox.getGeometry().getExtent();

			for (var i = 0; i < layers.getLength(); i++) {
				var source = new ol.source.Vector();
				var arr = [];

				if (layers.item(i) instanceof ol.layer.Vector) {
					layers.item(i).getSource().forEachFeatureIntersectingExtent(extent, function(feature) {
						arr.push(feature);
					});
				} else if (layers.item(i) instanceof ol.layer.Image && layers.item(i).getSource() instanceof ol.source.ImageVector) {
					layers.item(i).getSource().getSource().forEachFeatureIntersectingExtent(extent, function(feature) {
						arr.push(feature);
					});
				}

				if ((layers.item(i) instanceof ol.layer.Vector)
						|| (layers.item(i) instanceof ol.layer.Image && layers.item(i).getSource() instanceof ol.source.ImageVector)) {

					gitbuilder.variable.validationObj.layers[layers.item(i).get("id")] = {
						feature : new ol.format.GeoJSON().writeFeatures(arr),
						attribute : layers.item(i).get("attribute"),
						qaOption : {},
						weight : 0
					};
				}

			}

			var polyExtent = {
				"type" : "FeatureCollection",
				"features" : [ {
					"type" : "Feature",
					"id" : "valiExtent",
					"geometry" : {
						"type" : "MultiPolygon",
						"coordinates" : [ [ [ [ extent[0], extent[1] ], [ extent[0], extent[3] ], [ extent[2], extent[3] ], [ extent[2], extent[1] ],
								[ extent[0], extent[1] ] ] ] ]
					},
					"properties" : {}
				} ]
			};
			gitbuilder.variable.validationObj.extent = JSON.stringify(polyExtent);

			gitbuilder.ui.ValidationOptionWindow();

			// console.log(gitbuilder.variable.validationObj);

		});

		gitbuilder.variable.intrctn.validationArea = dragBox;
		gitbuilder.variable.map.addInteraction(dragBox);

	}
	// ==========================================================================================================
	/**
	 * @description 선택한 레이어의 검수 영역을 생성한다.
	 * @param {ol.layer.Vector}
	 *            영역 레이어
	 * @param {ol.layer.Vector}
	 *            검수할 레이어 { area : ol.layer.Vector, validation :
	 *            ol.layer.Vector}
	 */
	gitbuilder.method.edit.CreateValidationArea = function CreateValidationArea(param) {

		var area = param.area;
		var validation = param.validation;
		if (area instanceof ol.layer.Vector) {
			var source = area.getSource();
			var features = source.getFeatures();
			if (area.get("type") !== "MultiLineString") {
				console.error("not multipolygon");
				return;
			}
			if (features.length > 1) {
				console.error("too many features");
				return;
			} else if (features.length === 0) {
				console.error("no features");
				return;
			}
			var geom = features[0].getGeometry();
			var coord = geom.getCoordinates();

			var extent = features[0].getGeometry().getExtent();
			var geomA = features[0].getGeometry();
			var coordinate = new Array(new Array(coord));

			var polyGeom = new ol.geom.MultiPolygon();

			polyGeom.setCoordinates([ coord ], "XY");

			var valiSource = {}
			var arr = [];

			if (validation instanceof ol.layer.Vector) {
				validation.getSource().forEachFeatureInExtent(extent, function(aa) {
					// console.log("forEachFeatureInExtent", aa.getGeometry());
					if (gitbuilder.method.edit.polyIntersectsPoly(polyGeom, aa.getGeometry()) === true) {
						arr.push(aa);
					}
				});
			} else if (validation instanceof ol.layer.Image && validation.getSource() instanceof ol.source.ImageVector) {
				validation.getSource().getSource().forEachFeatureInExtent(extent, function(aa) {
					// console.log("forEachFeatureInExtent", aa.getGeometry());
					if (gitbuilder.method.edit.polyIntersectsPoly(polyGeom, aa.getGeometry()) === true) {
						arr.push(aa);
					}
				});
			}

			valiSource = new ol.source.Vector({
				features : arr
			});

			if ((validation instanceof ol.layer.Vector)
					|| (validation instanceof ol.layer.Image && validation.getSource() instanceof ol.source.ImageVector)) {

				if (gitbuilder.variable.validationObj.layers.hasOwnProperty(validation.get("id"))) {
					if (gitbuilder.variable.validationObj.layers[validation.get("id")].hasOwnProperty("feature")) {
						// if
						// (gitbuilder.variable.validationObj.layers[validation.get("id")].feature
						// !== undefined) {
						gitbuilder.variable.validationObj.layers[validation.get("id")].feature = new ol.format.GeoJSON().writeFeatures(arr);
						// }
					} else {
						gitbuilder.variable.validationObj.layers[validation.get("id")].feature = {};
						gitbuilder.variable.validationObj.layers[validation.get("id")].feature = new ol.format.GeoJSON().writeFeatures(arr);
					}

					if (gitbuilder.variable.validationObj.layers[validation.get("id")].hasOwnProperty("attribute")) {
						// if
						// (gitbuilder.variable.validationObj.layers[validation.get("id")].attribute
						// !== undefined) {
						gitbuilder.variable.validationObj.layers[validation.get("id")].attribute = validation.get("attribute");
						// }
					} else {
						gitbuilder.variable.validationObj.layers[validation.get("id")].attribute = null;
					}

					if (gitbuilder.variable.validationObj.layers[validation.get("id")].hasOwnProperty("qaOption")) {
						// if
						// (gitbuilder.variable.validationObj.layers[validation.get("id")].qaOption
						// !== undefined) {
						// gitbuilder.variable.validationObj.layers[validation.get("id")].qaOption
						// = {};
						// }
					} else {
						gitbuilder.variable.validationObj.layers[validation.get("id")].qaOption = {};
					}

					if (gitbuilder.variable.validationObj.layers[validation.get("id")].hasOwnProperty("weight")) {
						// if
						// (gitbuilder.variable.validationObj.layers[validation.get("id")].weight
						// !== undefined) {
						// gitbuilder.variable.validationObj.layers[validation.get("id")].weight
						// = 0;
						// }
					} else {
						gitbuilder.variable.validationObj.layers[validation.get("id")].weight = 0;
					}

				} else {
					gitbuilder.variable.validationObj.layers[validation.get("id")] = {
						feature : new ol.format.GeoJSON().writeFeatures(arr),
						attribute : validation.get("attribute"),
						qaOption : {},
						weight : 0
					}
				}

			}

		}

		var polyExtent = {
			"type" : "FeatureCollection",
			"features" : [ {
				"type" : "Feature",
				"id" : "valiExtent",
				"geometry" : {
					"type" : "MultiPolygon",
					"coordinates" : [ coord ]
				},
				"properties" : {}
			} ]
		};

		gitbuilder.variable.validationObj.extent = JSON.stringify(polyExtent);

		// console.log(gitbuilder.variable.validationObj);

	}
	// ==========================================================================================================
	/**
	 * @description check whether the supplied polygons have any spatial
	 *              interaction
	 * @{ol.geometry.Polygon} polygeomA
	 * @{ol.geometry.Polygon} polygeomB
	 * @returns {Boolean} true||false
	 */
	gitbuilder.method.edit.polyIntersectsPoly = function polyIntersectsPoly(polygeomA, polygeomB) {

		var geomA = new jsts.io.GeoJSONReader().read(new ol.format.GeoJSON().writeFeatureObject(new ol.Feature({
			geometry : polygeomA
		}))).geometry;
		var geomB = new jsts.io.GeoJSONReader().read(new ol.format.GeoJSON().writeFeatureObject(new ol.Feature({
			geometry : polygeomB
		}))).geometry;
		return geomA.intersects(geomB);
	}
	// ==========================================================================================================
	/**
	 * @description 중복점을 제거한다.
	 */
	gitbuilder.method.edit.RemoveDuplicatedPoint = function RemoveDuplicatedPoint() {
		var features = gitbuilder.variable.selectedFeatures;
		if (features.getLength() > 1) {
			console.error("too many features");
			return;
		}
		var feature = features.item(0);
		var geom = feature.getGeometry();
		if (geom instanceof ol.geom.LineString) {
			var coord = geom.getCoordinates();
			for (var i = 0; i < coord.length; i++) {
				if (i == coord.length - 1) {
					break;
				}

				var x = coord[i][0];
				var y = coord[i][1];

				var nx = coord[i + 1][0];
				var ny = coord[i + 1][1];

				if (x === nx && y === ny) {
					coord.splice(i, 1);
				}
			}
		} else if (geom instanceof ol.geom.MultiLineString || geom instanceof ol.geom.Polygon) {
			var coord = geom.getCoordinates();
			for (var i = 0; i < coord[0].length; i++) {
				if (i == coord[0].length - 1) {
					break;
				}

				var x = coord[0][i][0];
				var y = coord[0][i][1];

				var nx = coord[0][i + 1][0];
				var ny = coord[0][i + 1][1];

				if (x === nx && y === ny) {
					coord[0].splice(i, 1);
				}
			}
			geom.setCoordinates(coord, "XY");
			feature.setGeometry(geom);
			// console.log(coord);
			// console.log(geom);
			// console.log(feature);
		} else if (geom instanceof ol.geom.MultiPolygon) {
			var coord = geom.getCoordinates();
			for (var i = 0; i < coord[0][0].length; i++) {
				if (i == coord[0][0].length - 1) {
					break;
				}

				var x = coord[0][0][i][0];
				var y = coord[0][0][i][1];

				var nx = coord[0][0][i + 1][0];
				var ny = coord[0][0][i + 1][1];

				if (x === nx && y === ny) {
					coord[0][0].splice(i, 1);
				}
			}
			geom.setCoordinates(coord, "XY");
			feature.setGeometry(geom);
			// console.log(coord);
			// console.log(geom);
			// console.log(feature);
		}
	}
	// ==========================================================================================================
	/**
	 * @description 객체를 이동한다.
	 */
	gitbuilder.method.edit.MoveFeature = function MoveFeature() {

		if (gitbuilder.variable.intrctn.select === undefined || gitbuilder.variable.intrctn.select === null) {
			gitbuilder.method.edit.RemoveAllBuilderInteraction();
		} else if (gitbuilder.variable.intrctn.select instanceof ol.interaction.Select) {
			gitbuilder.variable.map.removeInteraction(gitbuilder.variable.intrctn.draw);
			gitbuilder.variable.map.removeInteraction(gitbuilder.variable.intrctn.dragBox);
			gitbuilder.variable.map.removeInteraction(gitbuilder.variable.intrctn.modify);
			gitbuilder.variable.map.removeInteraction(gitbuilder.variable.intrctn.translate);
			gitbuilder.variable.map.removeInteraction(gitbuilder.variable.intrctn.snap);
		}
		gitbuilder.ui.CloseAttributeTable();
		// feature 편집 mbr을 삭제한다 -김호철-
		gitbuilder.ui.RemoveEditToolTip();

		var layers = gitbuilder.variable.selectedLayers;
		if (layers.getLength() !== 1) {
			console.error("Selected layer must be one, check [gitbuilder.variable.selectedLayers]");
			return;
		}

		var selectedLayer = layers.item(0);

		var select;

		var styles = [ new ol.style.Style({
			stroke : new ol.style.Stroke({
				color : 'rgba(0,153,255,1)',
				width : 2
			}),
			fill : new ol.style.Fill({
				color : 'rgba(255, 255, 255, 0.5)'
			})
		}), new ol.style.Style({
			image : new ol.style.Circle({
				radius : 10,
				fill : new ol.style.Fill({
					color : 'rgba(0,153,255,0.4)'
				})
			}),
			geometry : function(feature) {

				var coordinates;
				var geom;

				if (feature.getGeometry() instanceof ol.geom.MultiPolygon) {
					coordinates = feature.getGeometry().getCoordinates()[0][0];
					geom = new ol.geom.MultiPoint(coordinates);
				} else if (feature.getGeometry() instanceof ol.geom.Polygon) {
					coordinates = feature.getGeometry().getCoordinates()[0];
					geom = new ol.geom.MultiPoint(coordinates);
				} else if (feature.getGeometry() instanceof ol.geom.MultiLineString) {
					coordinates = feature.getGeometry().getCoordinates()[0];
					geom = new ol.geom.MultiPoint(coordinates);
				} else if (feature.getGeometry() instanceof ol.geom.LineString) {
					coordinates = feature.getGeometry().getCoordinates();
					geom = new ol.geom.MultiPoint(coordinates);
				} else if (feature.getGeometry() instanceof ol.geom.MultiPoint) {
					coordinates = feature.getGeometry().getCoordinates();
					geom = new ol.geom.MultiPoint(coordinates);
				} else if (feature.getGeometry() instanceof ol.geom.Point) {
					coordinates = [ feature.getGeometry().getCoordinates() ];
					geom = new ol.geom.MultiPoint(coordinates);
				}

				return geom;
			}
		}) ];

		if (gitbuilder.variable.intrctn.select === undefined || gitbuilder.variable.intrctn.select === null) {
			select = new ol.interaction.Select({
				layers : [ selectedLayer ],
				wrapX : false,
				style : styles
			});

			select.set("name", "select");
			gitbuilder.variable.selectedFeatures = select.getFeatures();

			gitbuilder.variable.intrctn.select = select;
			gitbuilder.variable.map.addInteraction(select);

		} else if (gitbuilder.variable.intrctn.select instanceof ol.interaction.Select) {
			select = gitbuilder.variable.intrctn.select;

			select.set("name", "select");
			gitbuilder.variable.selectedFeatures = select.getFeatures();

			var dragBox = new ol.interaction.DragBox({
				condition : ol.events.condition.platformModifierKeyOnly
			});

			gitbuilder.variable.intrctn.dragBox = dragBox;
			gitbuilder.variable.map.addInteraction(dragBox);

			dragBox.on('boxend', function() {
				// features that intersect the box are added to the collection
				// of
				// selected features, and their names are displayed in the
				// "info"
				// div
				var extent = dragBox.getGeometry().getExtent();
				selectedLayer.getSource().forEachFeatureIntersectingExtent(extent, function(feature) {
					gitbuilder.variable.selectedFeatures.push(feature);

				});
			});

			// clear selection when drawing a new box and when clicking on the
			// map
			dragBox.on('boxstart', function() {
				gitbuilder.variable.selectedFeatures.clear();
			});

		}

		// select.on("select", function() {
		//
		// });

		if (gitbuilder.variable.selectedFeatures instanceof ol.Collection) {
			var translate = new ol.interaction.Translate({
				features : gitbuilder.variable.intrctn.select.getFeatures()
			});

			gitbuilder.variable.intrctn.translate = translate;
			gitbuilder.variable.map.addInteraction(gitbuilder.variable.intrctn.translate);

			// 객체 이동시 undo data set -김호철-
			translate.on('translatestart', function(evt) {
				var features = evt.features;
				var featureArr = [];
				for (var i = 0; i < features.getLength(); ++i) {
					featureArr.push(features.item(i));
				}
				gitbuilder.command.SetUndoData('edit', null, featureArr)
			});
		}
	}
	// ==========================================================================================================
	/**
	 * @description 객체를 수정한다.
	 */
	gitbuilder.method.edit.ModifyFeature = function ModifyFeature() {
		if (gitbuilder.variable.intrctn.select === undefined || gitbuilder.variable.intrctn.select === null) {
			gitbuilder.method.edit.RemoveAllBuilderInteraction();
		} else if (gitbuilder.variable.intrctn.select instanceof ol.interaction.Select) {
			gitbuilder.variable.map.removeInteraction(gitbuilder.variable.intrctn.draw);
			gitbuilder.variable.map.removeInteraction(gitbuilder.variable.intrctn.dragBox);
			gitbuilder.variable.map.removeInteraction(gitbuilder.variable.intrctn.modify);
			gitbuilder.variable.map.removeInteraction(gitbuilder.variable.intrctn.translate);
			gitbuilder.variable.map.removeInteraction(gitbuilder.variable.intrctn.snap);
		}
		gitbuilder.ui.CloseAttributeTable();

		// feature 편집 mbr을 삭제한다 -김호철-
		gitbuilder.ui.RemoveEditToolTip();

		var layers = gitbuilder.variable.selectedLayers;
		if (layers.getLength() !== 1) {
			console.error("Selected layer must be one, check [gitbuilder.variable.selectedLayers]");
			return;
		}

		var selectedLayer = layers.item(0);

		var select;

		var styles = [ new ol.style.Style({
			stroke : new ol.style.Stroke({
				color : 'rgba(0,153,255,1)',
				width : 2
			}),
			fill : new ol.style.Fill({
				color : 'rgba(255, 255, 255, 0.5)'
			})
		}), new ol.style.Style({
			image : new ol.style.Circle({
				radius : 10,
				fill : new ol.style.Fill({
					color : 'rgba(0,153,255,0.4)'
				})
			}),
			geometry : function(feature) {

				var coordinates;
				var geom;

				if (feature.getGeometry() instanceof ol.geom.MultiPolygon) {
					coordinates = feature.getGeometry().getCoordinates()[0][0];
					geom = new ol.geom.MultiPoint(coordinates);
				} else if (feature.getGeometry() instanceof ol.geom.Polygon) {
					coordinates = feature.getGeometry().getCoordinates()[0];
					geom = new ol.geom.MultiPoint(coordinates);
				} else if (feature.getGeometry() instanceof ol.geom.MultiLineString) {
					coordinates = feature.getGeometry().getCoordinates()[0];
					geom = new ol.geom.MultiPoint(coordinates);
				} else if (feature.getGeometry() instanceof ol.geom.LineString) {
					coordinates = feature.getGeometry().getCoordinates();
					geom = new ol.geom.MultiPoint(coordinates);
				} else if (feature.getGeometry() instanceof ol.geom.MultiPoint) {
					coordinates = feature.getGeometry().getCoordinates();
					geom = new ol.geom.MultiPoint(coordinates);
				} else if (feature.getGeometry() instanceof ol.geom.Point) {
					coordinates = [ feature.getGeometry().getCoordinates() ];
					geom = new ol.geom.MultiPoint(coordinates);
				}

				return geom;
			}
		}) ];

		if (gitbuilder.variable.intrctn.select === undefined || gitbuilder.variable.intrctn.select === null) {
			select = new ol.interaction.Select({
				layers : [ selectedLayer ],
				wrapX : false,
				style : styles
			});

			select.set("name", "select");
			gitbuilder.variable.selectedFeatures = select.getFeatures();

			gitbuilder.variable.intrctn.select = select;
			gitbuilder.variable.map.addInteraction(select);

		} else if (gitbuilder.variable.intrctn.select instanceof ol.interaction.Select) {
			select = gitbuilder.variable.intrctn.select;

			select.set("name", "select");
			gitbuilder.variable.selectedFeatures = select.getFeatures();

			var dragBox = new ol.interaction.DragBox({
				condition : ol.events.condition.platformModifierKeyOnly
			});

			gitbuilder.variable.intrctn.dragBox = dragBox;
			gitbuilder.variable.map.addInteraction(dragBox);

			dragBox.on('boxend', function() {
				// features that intersect the box are added to the collection
				// of
				// selected features, and their names are displayed in the
				// "info"
				// div
				var extent = dragBox.getGeometry().getExtent();
				selectedLayer.getSource().forEachFeatureIntersectingExtent(extent, function(feature) {
					gitbuilder.variable.selectedFeatures.push(feature);

				});
			});

			// clear selection when drawing a new box and when clicking on the
			// map
			dragBox.on('boxstart', function() {
				gitbuilder.variable.selectedFeatures.clear();
			});

		}

		// select.on("select", function() {
		//
		// });

		if (gitbuilder.variable.selectedFeatures instanceof ol.Collection) {
			var modify = new ol.interaction.Modify({
				deleteCondition : ol.events.condition.shiftKeyOnly,
				features : gitbuilder.variable.intrctn.select.getFeatures()
			});

			gitbuilder.variable.intrctn.modify = modify;
			gitbuilder.variable.map.addInteraction(modify);

			// 객체 수정시 undo data set -김호철-
			modify.on('modifystart', function(evt) {
				var features = evt.features;
				var featureArr = [];
				for (var i = 0; i < features.getLength(); ++i) {
					featureArr.push(features.item(i));
				}
				gitbuilder.command.SetUndoData('edit', null, featureArr);
			});
		}

		var featureCollection = new ol.Collection();
		var layers = gitbuilder.method.layer.getAllVectorLayer();
		var map = gitbuilder.variable.map;
		var extent = map.getView().calculateExtent(map.getSize());

		for (var i = 0; i < layers.getLength(); i++) {
			if (layers.item(i) instanceof ol.layer.Vector) {
				var source = layers.item(i).getSource();
				source.forEachFeatureIntersectingExtent(extent, function(feature) {
					featureCollection.push(feature);
				});
			} else if (layers.item(i) instanceof ol.layer.Image && layers[i].item(i).getSource() instanceof ol.source.ImageVector) {
				var source = layers.item(i).getSource().getSource();
				source.forEachFeatureIntersectingExtent(extent, function(feature) {
					featureCollection.push(feature);
				});
			}
		}

		gitbuilder.variable.intrctn.snap = new ol.interaction.Snap({
			features : featureCollection
		});
		gitbuilder.variable.map.addInteraction(gitbuilder.variable.intrctn.snap);
	}
	// ==========================================================================================================
	/**
	 * @description 객체를 삭제하기 위한 실렉트 조작 및 확인창 조건설정
	 */
	gitbuilder.method.edit.RemoveFeature = function RemoveFeature() {
		if (gitbuilder.variable.intrctn.select === undefined || gitbuilder.variable.intrctn.select === null) {
			gitbuilder.method.edit.RemoveAllBuilderInteraction();
		} else if (gitbuilder.variable.intrctn.select instanceof ol.interaction.Select) {
			gitbuilder.variable.map.removeInteraction(gitbuilder.variable.intrctn.draw);
			gitbuilder.variable.map.removeInteraction(gitbuilder.variable.intrctn.dragBox);
			gitbuilder.variable.map.removeInteraction(gitbuilder.variable.intrctn.modify);
			gitbuilder.variable.map.removeInteraction(gitbuilder.variable.intrctn.translate);
			gitbuilder.variable.map.removeInteraction(gitbuilder.variable.intrctn.snap);
		}
		gitbuilder.ui.CloseAttributeTable();
		// feature 편집 mbr을 삭제한다 -김호철-
		gitbuilder.ui.RemoveEditToolTip();

		var layers = gitbuilder.variable.selectedLayers;
		if (layers.getLength() !== 1) {
			console.error("Selected layer must be one, check [gitbuilder.variable.selectedLayers]");
			return;
		}

		var selectedLayer = layers.item(0);

		var select;
		var styles = [ new ol.style.Style({
			stroke : new ol.style.Stroke({
				color : 'rgba(0,153,255,1)',
				width : 2
			}),
			fill : new ol.style.Fill({
				color : 'rgba(255, 255, 255, 0.5)'
			})
		}), new ol.style.Style({
			image : new ol.style.Circle({
				radius : 10,
				fill : new ol.style.Fill({
					color : 'rgba(0,153,255,0.4)'
				})
			}),
			geometry : function(feature) {

				var coordinates;
				var geom;

				if (feature.getGeometry() instanceof ol.geom.MultiPolygon) {
					coordinates = feature.getGeometry().getCoordinates()[0][0];
					geom = new ol.geom.MultiPoint(coordinates);
				} else if (feature.getGeometry() instanceof ol.geom.Polygon) {
					coordinates = feature.getGeometry().getCoordinates()[0];
					geom = new ol.geom.MultiPoint(coordinates);
				} else if (feature.getGeometry() instanceof ol.geom.MultiLineString) {
					coordinates = feature.getGeometry().getCoordinates()[0];
					geom = new ol.geom.MultiPoint(coordinates);
				} else if (feature.getGeometry() instanceof ol.geom.LineString) {
					coordinates = feature.getGeometry().getCoordinates();
					geom = new ol.geom.MultiPoint(coordinates);
				} else if (feature.getGeometry() instanceof ol.geom.MultiPoint) {
					coordinates = feature.getGeometry().getCoordinates();
					geom = new ol.geom.MultiPoint(coordinates);
				} else if (feature.getGeometry() instanceof ol.geom.Point) {
					coordinates = [ feature.getGeometry().getCoordinates() ];
					geom = new ol.geom.MultiPoint(coordinates);
				}

				return geom;
			}
		}) ];

		if (gitbuilder.variable.intrctn.select === undefined || gitbuilder.variable.intrctn.select === null) {
			select = new ol.interaction.Select({
				layers : [ selectedLayer ],
				wrapX : false,
				style : styles
			});

			select.set("name", "select");
			gitbuilder.variable.selectedFeatures = select.getFeatures();

			select
					.on(
							"select",
							function(e) {
								if (gitbuilder.variable.selectedFeatures instanceof ol.Collection
										&& (selectedLayer instanceof ol.layer.Vector || (layer instanceof ol.layer.Image && layer.getSource() instanceof ol.source.ImageVector))) {
									if (e.selected.length > 0) {
										gitbuilder.ui.RemoveFeatureWindow();
									}
								} else {
									console.error("Feature is not selected or Selected layers are too many");
								}
							});

			gitbuilder.variable.intrctn.select = select;
			gitbuilder.variable.map.addInteraction(select);

		} else if (gitbuilder.variable.intrctn.select instanceof ol.interaction.Select) {
			select = gitbuilder.variable.intrctn.select;

			if (select.getFeatures().getLength() > 0) {
				gitbuilder.ui.RemoveFeatureWindow();
			}
			select.set("name", "select");
			gitbuilder.variable.selectedFeatures = select.getFeatures();

			select
					.on(
							"select",
							function() {
								if (gitbuilder.variable.selectedFeatures instanceof ol.Collection
										&& (selectedLayer instanceof ol.layer.Vector || (layer instanceof ol.layer.Image && layer.getSource() instanceof ol.source.ImageVector))) {
									gitbuilder.ui.RemoveFeatureWindow();
								} else {
									console.error("Feature is not selected or Selected layers are too many");
								}
							});

			var dragBox = new ol.interaction.DragBox({
				condition : ol.events.condition.platformModifierKeyOnly
			});

			gitbuilder.variable.intrctn.dragBox = dragBox;
			gitbuilder.variable.map.addInteraction(dragBox);

			dragBox
					.on('boxend',
							function() {
								// features that intersect the box are added to
								// the collection
								// of
								// selected features, and their names are
								// displayed in the
								// "info"
								// div
								var extent = dragBox.getGeometry().getExtent();
								selectedLayer.getSource().forEachFeatureIntersectingExtent(extent, function(feature) {
									gitbuilder.variable.selectedFeatures.push(feature);

								});

								if (gitbuilder.variable.selectedFeatures instanceof ol.Collection
										&& (selectedLayer instanceof ol.layer.Vector || (selectedLayer instanceof ol.layer.Image && selectedLayer
												.getSource() instanceof ol.source.ImageVector))) {
									gitbuilder.ui.RemoveFeatureWindow();
								} else {
									console.error("Feature is not selected or Selected layers are too many");
								}
							});

			// clear selection when drawing a new box and when clicking on the
			// map
			dragBox.on('boxstart', function() {
				gitbuilder.variable.selectedFeatures.clear();
			});

		}

		// select.on("select", function() {
		//
		// });
	}
	// ==========================================================================================================
	/**
	 * @description 객체를 실제로 삭제한다.
	 */
	gitbuilder.method.edit.DeleteFeature = function DeleteFeature() {
		var layers = gitbuilder.variable.selectedLayers;
		if (layers.getLength() !== 1) {
			console.error("Selected layer must be one, check [gitbuilder.variable.selectedLayers]");
			return;
		}

		var selectedLayer = layers.item(0);
		var selectedFeatures = gitbuilder.variable.selectedFeatures;
		if (selectedLayer instanceof ol.layer.Vector
				|| (selectedLayer instanceof ol.layer.Image && selectedLayer.getSource() instanceof ol.source.ImageVector)) {
			// feature 삭제에 대한 redo data set -김호철-
			gitbuilder.command.SetUndoData('featureCreate', selectedLayer, selectedFeatures.getArray());

			if (selectedLayer instanceof ol.layer.Vector) {
				var source = selectedLayer.getSource();
				for (var i = 0; i < selectedFeatures.getLength(); i++) {
					source.removeFeature(selectedFeatures.item(i));
				}
			} else if (selectedLayer instanceof ol.layer.Image && selectedLayer.getSource() instanceof ol.source.ImageVector) {
				var source = selectedLayer.getSource().getSource();
				for (var i = 0; i < selectedFeatures.getLength(); i++) {
					source.removeFeature(selectedFeatures.item(i));
				}
			}

			selectedFeatures.clear();
			gitbuilder.ui.CloseAttributeTable();
			// feature 편집 mbr을 삭제한다 -김호철-
			gitbuilder.ui.RemoveEditToolTip();
			gitbuilder.ui.EditingWindow();
		} else {
			console.error("This layer is not a vector layer.");
		}
	}
	// ==========================================================================================================
	/**
	 * @description 에러 객체로 이동한다.
	 */
	gitbuilder.method.edit.BackToErrorFeature = function BackToErrorFeature() {
		var selector = "li[layerid='Validation Result']";
		var li = $(selector);
		// console.log(li);
		gitbuilder.method.layer.SelectLayerSelectable(li);
		gitbuilder.method.edit.SelectFeature(gitbuilder.variable.errorFeatures);

		gitbuilder.ui.EditingWindow();

		var str = gitbuilder.ui.OutputAttributeTable(gitbuilder.variable.errorFeatures.item(0));

		var popovr = $(str).get(0);
		var popup = gitbuilder.variable.overlay.feature;
		popup.setElement(popovr);

		gitbuilder.variable.map.addOverlay(popup);
		var element = popup.getElement();

		$(element).popover('destroy');
		gitbuilder.variable.crsrCoordinate = gitbuilder.variable.map.getView().getCenter();
		// gitbuilder.variable.crsrCoordinate =
		// feature.getGeometry().getFirstCoordinate();
		popup.setPosition(gitbuilder.variable.crsrCoordinate);
		// the keys are quoted to prevent renaming in ADVANCED
		// mode.
		$(element).popover({
			'placement' : 'top',
			'animation' : false,
			'html' : true
		});
		$(element).popover('show');
	}
	// ==========================================================================================================
	/**
	 * @description 입력한 애트리뷰트들을 피처에 저장한다.
	 * @param {ol.Feature}
	 *            속성이 저장될 피처
	 */
	gitbuilder.method.edit.InsertAttributeIntoFeature = function InsertAttributeIntoFeature(feature) {
		var keys = $(".GitBuilder-Edit-Attribute-Key");
		var values = $(".GitBuilder-Edit-Attribute-Value");

		for (var i = 0; i < keys.length; i++) {
			feature.set($(keys[i]).text(), $(values[i]).val());
		}
		// console.log(feature);

		// $("#" + gitbuilder.variable.elementid.featureListId).empty();
		// var layers = gitbuilder.variable.selectedLayers;
		// if (layers.getLength() === 1) {
		// var start = 0;
		// var count = 5;
		// var layer = gitbuilder.variable.selectedLayers.item(0);
		// $("#" +
		// gitbuilder.variable.elementid.featureListId).append(gitbuilder.ui.OutputFeatureList(layer,
		// start, count));
		// }
	}
	// ==========================================================================================================
	/**
	 * @description 애트리뷰트들을 저장하지 않고 피처를 삭제한다.
	 * @param {ol.Feature}
	 *            삭제될 피처
	 */
	gitbuilder.method.edit.NoAttributeAndDeleteFeature = function NoAttributeAndDeleteFeature(feature) {
		var layers = gitbuilder.variable.selectedLayers;
		// console.log(layers);
		var layer;
		if (layers !== undefined && layers !== null && layers instanceof ol.Collection && layers.getLength() === 1) {
			if (layers.item(0) instanceof ol.layer.Vector) {
				layer = layers.item(0);
				var source = layer.getSource();
				source.removeFeature(feature);
			} else if (layers.item(0) instanceof ol.layer.Image && layers.item(0).getSource() instanceof ol.source.ImageVector) {
				layer = layers.item(0);
				var source = layer.getSource().getSource();
				source.removeFeature(feature);
			} else {
				console.error("The layer is not a vector layer.");
			}
		} else {
			console.error("The selected layer must be one.");
		}
	}
	// ==========================================================================================================
	/**
	 * @description 파라미터의 오류 피처를 선택한다.
	 * @param {String}
	 *            피처 UFID
	 */
	gitbuilder.method.edit.LinkToErrorFeature = function LinkToErrorFeature(ufid) {
		console.log("link in");
		var layerid = ufid.substring(0, ufid.indexOf("."));
		var layer = gitbuilder.method.layer.getLayerById(layerid);
		var feature = gitbuilder.method.feature.getFeatureByIdAndLayer(layer, ufid);

		var selector = "li[layerid='" + layerid + "']";
		var li = $(selector);
		// console.log(li);
		gitbuilder.method.layer.SelectLayerSelectable(li);

		var featureColl = new ol.Collection();
		featureColl.push(feature);
		gitbuilder.method.edit.SelectFeature(featureColl);

		var source = new ol.source.Vector({
			features : featureColl
		});

		// try {
		// gitbuilder.variable.view.fit(source.getExtent(),
		// gitbuilder.variable.map.getSize());
		// } catch (e) {
		// alert("존재하지 않습니다. 이미 삭제 되었을수 있습니다.");
		// }

		gitbuilder.ui.EditingWindow();

		var str = gitbuilder.ui.OutputAttributeTable(feature);

		var popovr = $(str).get(0);
		var popup = gitbuilder.variable.overlay.feature;
		popup.setElement(popovr);

		gitbuilder.variable.map.addOverlay(popup);
		var element = popup.getElement();

		$(element).popover('destroy');
		gitbuilder.variable.crsrCoordinate = gitbuilder.variable.map.getView().getCenter();
		// gitbuilder.variable.crsrCoordinate =
		// feature.getGeometry().getFirstCoordinate();
		popup.setPosition(gitbuilder.variable.crsrCoordinate);
		// the keys are quoted to prevent renaming in ADVANCED
		// mode.
		$(element).popover({
			'placement' : 'top',
			'animation' : false,
			'html' : true
		});
		$(element).popover('show');

	}
	// ==========================================================================================================
	gitbuilder.method.operation = {};
	// ==========================================================================================================
	/**
	 * @description 연산 객체를 만든다
	 * @returns {gitbuilder.variable.operationObj} 연산객체
	 */
	gitbuilder.method.operation.CreateOperationObject = function CreateOperationObject() {
		var layers = gitbuilder.method.layer.getAllVectorLayer();

		var operator = $("#" + gitbuilder.variable.elementid.operator).val();
		var operand1 = $("input[type='radio'][name='GitBuilder-Operation-Radio1']:checked");
		var operand2 = $("input[type='radio'][name='GitBuilder-Operation-Radio2']:checked");

		// console.log(operator);
		// console.log($(operand1[0]).val());
		// console.log($(operand2[0]).val());

		gitbuilder.variable.operationObj = {
			operator : {
				operationName : undefined,
				layers : {}
			}
		};

		gitbuilder.variable.operationObj.operator.operationName = operator;

		for (var i = 0; i < layers.getLength(); i++) {
			// console.log(layers.item(i).get("id"));
			if (layers.item(i).get("id") === $(operand1[0]).val()) {
				if (layers.item(i) instanceof ol.layer.Vector) {
					gitbuilder.variable.operationObj.operator.layers[layers.item(i).get("id")] = {
						feature : new ol.format.GeoJSON().writeFeatures(layers.item(i).getSource().getFeatures()),
						operationOption : "operandLayerA",
						layerType : layers.item(i).get("type")
					};
				} else if (layers.item(i) instanceof ol.layer.Image && layers.item(i).getSource() instanceof ol.source.ImageVector) {
					gitbuilder.variable.operationObj.operator.layers[layers.item(i).get("id")] = {
						feature : new ol.format.GeoJSON().writeFeatures(layers.item(i).getSource().getSource().getFeatures()),
						operationOption : "operandLayerA",
						layerType : layers.item(i).get("type")
					};
				}

			} else if (layers.item(i).get("id") === $(operand2[0]).val()) {
				if (layers.item(i) instanceof ol.layer.Vector) {
					gitbuilder.variable.operationObj.operator.layers[layers.item(i).get("id")] = {
						feature : new ol.format.GeoJSON().writeFeatures(layers.item(i).getSource().getFeatures()),
						operationOption : "operandLayerB",
						layerType : layers.item(i).get("type")
					};
				} else if (layers.item(i) instanceof ol.layer.Image && layers.item(i).getSource() instanceof ol.source.ImageVector) {
					gitbuilder.variable.operationObj.operator.layers[layers.item(i).get("id")] = {
						feature : new ol.format.GeoJSON().writeFeatures(layers.item(i).getSource().getSource().getFeatures()),
						operationOption : "operandLayerB",
						layerType : layers.item(i).get("type")
					};
				}
			} else {
				continue;
			}

		}

		// console.log(gitbuilder.variable.operationObj);
		return gitbuilder.variable.operationObj
	}
	// ==========================================================================================================
	/**
	 * @description 공간연산을 요청한다.
	 */
	gitbuilder.method.operation.RequestOperation = function RequestOperation() {

		var obj = gitbuilder.method.operation.CreateOperationObject();
		// console.log(JSON.stringify(obj));
		var serverUrl = gitbuilder.method.getContextPath() + '/operator/operation.ajax';

		$.ajax({
			url : serverUrl,
			type : "POST",
			// type : "GET",
			// dataType : "json",
			contentType : "application/json; charset=UTF-8",
			cache : false,
			async : false,
			data : JSON.stringify(gitbuilder.variable.operationObj),
			beforeSend : function() { // 호출전실행
				// loadImageShow();
			},
			traditional : true,
			success : function(data, textStatus, jqXHR) {
				// console.log("data 구조:");
				// console.log(JSON.stringify(data));
				var keys = Object.keys(data.layers);
				for (var i = 0; i < keys.length; i++) {
					var layer = data.layers[keys[i]];
					var geojson = layer.feature;
					var format = new ol.format.GeoJSON().readFeatures(geojson);

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
					var layerId = gitbuilder.method.layer.createLayerId();

					var obj = {
						layer : layer,
						name : "Operation Result",
						id : layerId,
						type : data.layers[keys[i]].type,
						cat : 1,
						edit : true,
						attrType : null
					};
					gitbuilder.method.layer.setLayerProperties(obj);

					gitbuilder.method.layer.addLayerOnList(layer);
				}

				$('#' + gitbuilder.variable.elementid.operationWindow).modal('hide');
			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log(textStatus)
			}
		});
	}
	// ==========================================================================================================
	gitbuilder.method.validation = {};
	// ==========================================================================================================
	/**
	 * @description 서버에 검수를 요청
	 * @param {String}
	 *            요청주소
	 * @returns {Object} 검수결과
	 */
	gitbuilder.method.validation.RequestValidation = function RequestValidation() {
		$('#' + gitbuilder.variable.elementid.validatingWindow).modal('hide');
		gitbuilder.method.edit.RemoveAllBuilderInteraction();
		var obj = gitbuilder.variable.validationObj;
		console.log(JSON.stringify(obj));
		var serverUrl = gitbuilder.method.getContextPath() + '/validator/validate.ajax';

		$.ajax({
			url : serverUrl,
			type : "POST",
			// type : "GET",
			// dataType : "json",
			contentType : "application/json; charset=UTF-8",
			cache : false,
			async : true,
			data : JSON.stringify(gitbuilder.variable.validationObj),
			beforeSend : function() { // 호출전실행
				loadImageShow();
			},
			traditional : true,
			success : function(data, textStatus, jqXHR) {
				// gitbuilder.variable.errReport
				// console.log(JSON.stringify(data.DetailsReport));
				loadImageHide();

				if (!data.Error) {
					alertPopup("Message", "No error detected");
					return;
				}
				gitbuilder.variable.errReport = data.DetailsReport;
				// console.log(JSON.stringify(data.ErrorLayer));
				var format = new ol.format.GeoJSON().readFeatures(JSON.stringify(data.ErrorLayer));

				var source = new ol.source.Vector({
					features : format
				});

				var fill = new ol.style.Fill({
					color : "rgba(0,0,0,0)"
				});
				var stroke = new ol.style.Stroke({
					color : "rgba(255,0,0,1)",
					width : 2.7
				});
				var text = new ol.style.Text({});
				var styles = new ol.style.Style({
					image : new ol.style.Circle({
						fill : fill,
						stroke : stroke,
						radius : 40
					}),
					fill : fill,
					stroke : stroke,
					text : text
				});

				var layer = gitbuilder.method.layer.createVectorLayer(source, styles);
				var layerId = gitbuilder.method.layer.createLayerId();

				var obj = {
					layer : layer,
					name : "Validation Result",
					id : "Validation Result",
					type : "MultiPoint",
					cat : 2,
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

	}
	// ==========================================================================================================
	// ==========================================================================================================
	gitbuilder.method.coordinate = {};
	// ==========================================================================================================
	/**
	 * @description 좌표를 변환
	 * @param {Object} {
	 *            source : {ol.source.Vector} 좌표를 변환할 벡터 소스 required, epsg :
	 *            {String} EPSG 식별자 required, constructor : 좌표계 정의{String} }
	 * 
	 * @returns {Boolean} 변환 성공여부
	 */
	gitbuilder.method.coordinate.ConvertCoordinatesVectorSource = function ConvertCoordinatesVectorSource(param) {
		fetch('http://epsg.io/' + param.epsg + '.js').then(function(response) {
			return response.text();
		}).then(function(string) {

			eval(string);
			console.log(string);
		}).then(function() {
			var epsgid = ("EPSG:" + param.epsg).replace(/\s/gi, '');
			var features = param.source.getFeatures();
			for (var i = 0; i < features.length; i++) {
				if (features[i].getGeometry() instanceof ol.geom.Point) {
					var coord = features[i].getGeometry().getCoordinates();
					proj4(epsgid, 'EPSG:3857', coord);
					console.log(coord);
				} else if (features[i].getGeometry() instanceof ol.geom.LineString) {
					var coord = features[i].getGeometry().getCoordinates();
					for (var j = 0; j < coord.length; j++) {
						proj4(epsgid, 'EPSG:3857', coord[j]);
					}
					console.log(coord);
				} else if (features[i].getGeometry() instanceof ol.geom.Polygon) {
					var coord = features[i].getGeometry().getCoordinates();
					for (var j = 0; j < coord.length; j++) {
						for (var k = 0; k < coord[j].length; k++) {
							proj4(epsgid, 'EPSG:3857', coord[j][k]);
						}
					}
					console.log(coord);
				} else if (features[i].getGeometry() instanceof ol.geom.MultiPoint) {
					var coord = features[i].getGeometry().getCoordinates();
					for (var j = 0; j < coord.length; j++) {
						proj4(epsgid, 'EPSG:3857', coord[j]);
					}
					console.log(coord);
				} else if (features[i].getGeometry() instanceof ol.geom.MultiLineString) {
					var coord = features[i].getGeometry().getCoordinates();
					for (var j = 0; j < coord.length; j++) {
						for (var k = 0; k < coord[j].length; k++) {
							proj4(epsgid, 'EPSG:3857', coord[j][k]);
						}
					}
					console.log(coord);
				} else if (features[i].getGeometry() instanceof ol.geom.MultiPolygon) {
					var coord = features[i].getGeometry().getCoordinates();
					for (var j = 0; j < coord.length; j++) {
						for (var k = 0; k < coord[j].length; k++) {
							for (var l = 0; l < coord[j][k].length; l++) {
								console.log(coord[j][k][l]);
								coord[j][k][l] = proj4(epsgid, 'EPSG:3857', coord[j][k][l]);
								// console.log(proj4(epsgid, 'EPSG:3857',
								// coord[i][j][k]));
								console.log(coord[j][k][l]);
							}
						}
					}
					features[i].getGeometry().setCoordinates(coord);
					console.log(coord);
				}
			}
		});

	}
	// ==========================================================================================================
	/**
	 * @description 좌표변환 버튼 이벤트
	 */
	gitbuilder.method.coordinate.ConvertCoordinatesVectorSourceEvent = function ConvertCoordinatesVectorSourceEvent() {
		var layerid = $(".GitBuilder-QAList-AllVectorCRS").val();
		var layer = gitbuilder.method.layer.getLayerById(layerid);

		var epsg = $(".GitBuilder-CRS-FromCRSForm").val();
		console.log(epsg);

		var param = {
			source : layer.getSource(),
			epsg : epsg
		};
		gitbuilder.method.coordinate.ConvertCoordinatesVectorSource(param);
	}
	// ==========================================================================================================
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

		var list = $('<ul id="' + layerListId + '" class=".GitBuilder-LayerList"></ul>');
		$("#" + target).append(list);

		$("#" + layerListId).sortable({
			axis : "y",
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
			cancel : ".GitBuilder-LayerList-Item-Handle, .GitBuilder-LayerList-Function-Button",
			stop : function(event, ui) {

				// 선택한 li들의 객체배열을 획득
				var selected = $(".layerSelectable .ui-selected");

				gitbuilder.variable.selectedLayers = new ol.Collection();
				// 각각의 li마다
				selected.each(function() {
					var layer = gitbuilder.method.layer.getLayerById($(this).attr("layerid"));
					gitbuilder.variable.selectedLayers.push(layer);
					$(".GitBuilder-LayerList-Function").hide();
				});

				if (selected.length === 1) {
					$(selected[0]).find(".GitBuilder-LayerList-Function").show();
				} else if (selected.length > 1) {

				}

				gitbuilder.method.edit.RemoveAllBuilderInteraction();

				// feature 편집 mbr을 삭제한다 -김호철-
				gitbuilder.ui.RemoveEditToolTip();

				// gitbuilder.ui.CloseEditingWindow();
				if (gitbuilder.variable.editingOpen == true) {
					gitbuilder.ui.OpenEditingWindow();
				} else {
					gitbuilder.ui.CloseEditingWindow();
				}

			}
		}).find("li").addClass("ui-corner-all").prepend(
				"<span class='GitBuilder-LayerList-Item-Handle'><span class='glyphicon glyphicon-sort' aria-hidden='true'></span></span>");

		$(document).on("click", ".GitBuilder-LayerList-Function-Show", function() {
			console.log($(this).attr("layerid"));
			var layer = gitbuilder.method.layer.getLayerById($(this).attr("layerid"));
			var show = layer.getVisible();
			if (show) {
				layer.setVisible(false);
				$(this).find(".glyphicon-eye-open").hide();
				$(this).find(".glyphicon-eye-close").show();
			} else {
				layer.setVisible(true);
				$(this).find(".glyphicon-eye-close").hide();
				$(this).find(".glyphicon-eye-open").show();
			}
		});

		$(document).on("click", ".GitBuilder-LayerList-Function-Fit", function() {
			console.log($(this).attr("layerid"));
			var layer = gitbuilder.method.layer.getLayerById($(this).attr("layerid"));
			gitbuilder.method.layer.zoomToLayerExtent(layer);
		});

		$(document).on("click", ".GitBuilder-LayerList-Function-Remove", function() {
			console.log($(this).attr("layerid"));
			gitbuilder.method.layer.RemoveLayerOnList($(this).attr("layerid"));
		});
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
	} // ==========================================================================================================
	/**
	 * @description 레이어 스타일 창 출력
	 */
	gitbuilder.ui.LayerStyleWindow = function LayerStyleWindow(layer) {
		if (!gitbuilder.variable.elementid.removeFeatureWindow) {
			var styleWindowId = "styleWindow";
			var count = 0;
			while (gitbuilder.method.isDuplicatedId(styleWindowId)) {
				styleWindowId += count;
			}
			gitbuilder.variable.elementid.styleWindow = styleWindowId;

			var fillColor = "fillColor";
			count = 0;
			while (gitbuilder.method.isDuplicatedId(fillColor)) {
				fillColor += count;
			}
			gitbuilder.variable.elementid.fillColor = fillColor;

			var strokeColor = "strokeColor";
			count = 0;
			while (gitbuilder.method.isDuplicatedId(strokeColor)) {
				strokeColor += count;
			}
			gitbuilder.variable.elementid.strokeColor = strokeColor;

			var strokeWidth = "strokeWidth";
			count = 0;
			while (gitbuilder.method.isDuplicatedId(strokeWidth)) {
				strokeWidth += count;
			}
			gitbuilder.variable.elementid.strokeWidth = strokeWidth;

			var fillRadius = "fillRadius";
			count = 0;
			while (gitbuilder.method.isDuplicatedId(fillRadius)) {
				fillRadius += count;
			}
			gitbuilder.variable.elementid.fillRadius = fillRadius;

			var settingArea = "settingArea";
			count = 0;
			while (gitbuilder.method.isDuplicatedId(settingArea)) {
				settingArea += count;
			}
			gitbuilder.variable.elementid.settingArea = settingArea;

			var modal = "";
			modal += '<div id="' + styleWindowId
					+ '" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="Layer Style" aria-hidden="true">';
			modal += '<div class="modal-dialog">';
			modal += '<div class="modal-content">';
			modal += '<div class="modal-body">';

			modal += '<div id="' + gitbuilder.variable.elementid.settingArea + '">';

			var type = layer.get("type");
			if (type === "Point" || type === "MultiPoint") {
				modal += '<input type="text"	id="' + gitbuilder.variable.elementid.strokeColor + '" />';
				modal += '<input type="text"	id="' + gitbuilder.variable.elementid.fillColor + '" />';
			} else if (type === "LineString" || type === "MultiLineString") {
				modal += '<input type="text"	id="' + gitbuilder.variable.elementid.strokeColor + '" />';
			} else if (type === "Polygon" || type === "MultiPolygon") {
				modal += '<input type="text"	id="' + gitbuilder.variable.elementid.strokeColor + '" />';
				modal += '<input type="text"	id="' + gitbuilder.variable.elementid.fillColor + '" />';
			}

			modal += '</div>';

			modal += '</div>';
			modal += '<div class="modal-footer">';
			modal += ' <button type="button" class="btn btn-default" data-dismiss="modal">Cancek</button>';
			modal += '<button type="button" class="btn btn-primary" onclick="" data-dismiss="modal">OK</button>';
			modal += ' </div>';
			modal += '</div>';
			modal += '</div>';
			modal += '</div>';

			$("body").append(modal);

			$("#" + gitbuilder.variable.elementid.strokeColor)
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

			$("#" + gitbuilder.variable.elementid.fillColor)
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

		$('#' + gitbuilder.variable.elementid.styleWindow).modal('show');
	}
	// ==========================================================================================================
	/**
	 * @description 새로운 벡터 레이어를 생성
	 */
	gitbuilder.ui.NewVectorWindow = function NewVectorWindow() {
		if (!gitbuilder.variable.elementid.vectorWindow && !gitbuilder.variable.elementid.pointInput && !gitbuilder.variable.elementid.lineInput
				&& !gitbuilder.variable.elementid.polyInput && !gitbuilder.variable.elementid.layerType && !gitbuilder.variable.elementid.layerName
				&& !gitbuilder.variable.elementid.attrName && !gitbuilder.variable.elementid.attrType && !gitbuilder.variable.elementid.attrList) {
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

			var attrName = "attrName";
			count = 0;
			while (gitbuilder.method.isDuplicatedId(attrName)) {
				attrName += count;
			}
			gitbuilder.variable.elementid.attrName = attrName;

			var attrType = "attrType";
			count = 0;
			while (gitbuilder.method.isDuplicatedId(attrType)) {
				attrType += count;
			}
			gitbuilder.variable.elementid.attrType = attrType;

			var attrList = "attrList";
			count = 0;
			while (gitbuilder.method.isDuplicatedId(attrList)) {
				attrList += count;
			}
			gitbuilder.variable.elementid.attrList = attrList;

			var vector = "<div class='modal fade' id='" + vectorLayerWindowId + "' tabindex='-1' role='dialog'>";
			vector += '<div class="modal-dialog">';
			vector += '<div class="modal-content">';
			vector += '<div class="modal-header">';
			vector += '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
			vector += '<h4 class="modal-title">Vector</h4>';
			vector += '</div>';
			vector += '<div class="modal-body">';

			vector += '<form class="form-horizontal">';

			vector += '<div class="panel panel-default">';
			vector += '<div class="panel-heading">Layer</div>';
			vector += '<div class="panel-body">';

			vector += '<div class="form-group">';
			vector += '<label class="col-md-2 control-label">Type</label>';
			vector += '<div class="col-md-10">';
			vector += '<div class="btn-group col-md-12" data-toggle="buttons">';
			vector += '<label class="btn btn-default active col-md-4">';
			vector += '<input type="radio" name="' + layerType + '" id="' + pointId + '" value="MultiPoint" checked> Point';
			vector += '</label>';
			vector += '<label class="btn btn-default col-md-4">';
			vector += '<input type="radio" name="' + layerType + '" id="' + lineId + '" value="MultiLineString"> LineString';
			vector += '</label>';
			vector += '<label class="btn btn-default col-md-4">';
			vector += '<input type="radio" name="' + layerType + '" id="' + polygonId + '" value="MultiPolygon"> Polygon';
			vector += '</label>';
			vector += '</div>';

			vector += '</div>';
			vector += '</div>';

			vector += '<div class="form-group">';
			vector += '<label class="col-md-2 control-label">Name</label>';
			vector += '<div class="col-md-10">';
			vector += '<input type="text" class="form-control" id="' + layerNameId + '">';
			vector += '</div>';
			vector += '</div>';

			vector += '</div>';
			vector += '</div>';

			vector += '<div class="panel panel-default">';
			vector += '<div class="panel-heading">New Attribute</div>';
			vector += '<div class="panel-body">';

			vector += '<div class="form-group">';
			vector += '<label class="col-md-2 control-label">Name</label>';
			vector += '<div class="col-md-10">';
			vector += '<input type="text" class="form-control" id="' + attrName + '">';
			vector += '</div>';
			vector += '</div>';

			vector += '<div class="form-group">';
			vector += '<label class="col-md-2 control-label">Type</label>';
			vector += '<div class="col-md-10">';
			vector += '<select class="form-control" id="' + attrType + '">';
			vector += '<option value="String">String</option>';
			vector += '<option value="Integer">Integer</option>';
			vector += '<option value="Double">Double</option>';
			vector += '<option value="Date">Date</option>';
			vector += '</select>';
			vector += '</div>';
			vector += '</div>';

			vector += '<div class="form-group">';
			vector += '<div class="col-md-5">';
			vector += '</div>';
			vector += '<div class="col-md-2">';
			vector += '<button type="button" class="btn btn-default" onclick="gitbuilder.ui.AddAttributeIntoTable()">Add</button>';
			vector += '</div>';
			vector += '<div class="col-md-5">';
			vector += '</div>';
			vector += '</div>';

			vector += '</div>';
			vector += '</div>';

			vector += '<div class="panel panel-default">';
			vector += '<div class="panel-heading">Attribute List</div>';
			vector += '<div class="panel-body">';
			vector += '<table class="table table-bordered table-hover table-condensed">';
			vector += '<thead><tr><td><p class="text-center">#</p></td><td><p class="text-center">Name</p></td><td><p class="text-center">Type</p></td><td><p class="text-center">Delete</p></td></tr></thead>';
			vector += '<tbody id="' + attrList + '">';
			vector += '<tr><td><p class="text-center">1</p></td><td><p class="text-center">id</p></td><td><p class="text-center">Integer</p></td><td><p class="text-center"><button type="button" class="btn btn-default btn-xs DeleteAttr">Delete</button></p></td></tr>';
			vector += '</tbody>';
			vector += '</table>';
			vector += '</div>';
			vector += '</div>';

			vector += '</form>';

			vector += '</div>';
			vector += '<div class="modal-footer">';
			vector += '<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>';
			vector += '<button type="button" class="btn btn-primary" onclick="gitbuilder.method.layer.createVectorLayerBind()">Create</button>';
			vector += '</div>';
			vector += '</div>';
			vector += '</div>';
			vector += '</div>';

			$("body").append(vector);

			$(document).on("click", ".DeleteAttr", function() {
				var rowIdx = $(this).parent().parent().parent().index();
				gitbuilder.ui.RemoveAttributeFromTable(rowIdx);
			});

		} else {
			$("#" + gitbuilder.variable.elementid.layerName).val("");
			$("#" + gitbuilder.variable.elementid.attrName).val("");
			$("#" + gitbuilder.variable.elementid.attrType).val("String");
			$("#" + gitbuilder.variable.elementid.attrList).empty();
			var vector = '<tr><td><p class="text-center">1</p></td><td><p class="text-center">id</p></td><td><p class="text-center">Integer</p></td><td><p class="text-center"><button type="button" class="btn btn-default btn-xs DeleteAttr">Delete</button></p></td></tr>';
			// attrName: 속성명 텍스트인풋
			// attrType: 속성 데이터 타입 실렉트
			// attrList: 속성 리스트 테이블 바디
			$("#" + gitbuilder.variable.elementid.attrList).append(vector);
		}

		$('#' + gitbuilder.variable.elementid.vectorWindow).modal('show');
	}
	// ==========================================================================================================
	/**
	 * @description 네비게이터 창을 생성
	 */
	gitbuilder.ui.NavigatorWindow = function NavigatorWindow() {
		var layers = gitbuilder.method.layer.getErrorLayer();
		if (layers.getLength() > 1) {
			console.error("too many error layers");
			return;
		} else if (layers.getLength() === 0) {
			console.error("no error layer");
			return;
		}
		if (layers.item(0).get("cat") !== 2) {
			console.error("it is not error layer");
			return;
		}

		if (!gitbuilder.variable.elementid.navigatingWindow) {
			var naviWindowId = "navigatingWindow";
			var count = 0;
			while (gitbuilder.method.isDuplicatedId(naviWindowId)) {
				naviWindowId += count;
			}
			gitbuilder.variable.elementid.navigatingWindow = naviWindowId;

			var errFeature = "errFeatureSpan";
			var count = 0;
			while (gitbuilder.method.isDuplicatedId(errFeature)) {
				errFeature += count;
			}
			gitbuilder.variable.elementid.errorFeatureSpan = errFeature;

			var naviWin = '';

			naviWin += '<div id="' + naviWindowId + '" class="GitBuilder-Editing-Navigator">';

			naviWin += '<table class="table">';

			naviWin += '<tr>';

			naviWin += '<td>';
			naviWin += '<button type="button" class="btn btn-default" onClick="gitbuilder.ui.OutputErrorFeatureFromStartPreviousEventBind()">';
			naviWin += '<span class="glyphicon glyphicon-backward" aria-hidden="true"></span>';
			naviWin += '</button>';
			naviWin += '</td>';

			naviWin += '<td id="' + gitbuilder.variable.elementid.errorFeatureSpan + '">';
			naviWin += '</td>';

			naviWin += '<td>';
			naviWin += '<button type="button" class="btn btn-default" onClick="gitbuilder.ui.OutputErrorFeatureFromStartNextEventBind()">';
			naviWin += '<span class="glyphicon glyphicon-forward" aria-hidden="true"></span>';
			naviWin += '</button>';
			naviWin += '</td>';

			naviWin += '</tr>';

			naviWin += '<tr class="GitBuilder-Navigating-Feature">';
			naviWin += '</tr>';

			naviWin += '</table>';
			// naviWin += '<button class="btn btn-default
			// GitBuilder-Edit-Button"
			// onclick="gitbuilder.method.edit.RemoveDuplicatedPoint()"
			// title="Remove duplicated point"><span class="glyphicon
			// glyphicon-record" aria-hidden="true"></span></button>';
			naviWin += '</div>';

			var target = gitbuilder.variable.map.getTarget();
			console.log(target);

			$("#" + target).append(naviWin);

			$("#" + gitbuilder.variable.elementid.navigatingWindow).draggable({});

			var layer;

			if (layers.getLength() === 1) {
				layer = layers.item(0);
			}
			gitbuilder.ui.OutputErrorFeatureFromStart(layer, 0);
			$("#" + gitbuilder.variable.elementid.navigatingWindow).hide();
		} else {

		}
		if ($("#" + gitbuilder.variable.elementid.navigatingWindow + " :visible").length > 0) {
			gitbuilder.ui.CloseNavigatorWindow();
		} else {
			gitbuilder.ui.OpenNavigatorWindow();
		}

	}
	// ==========================================================================================================
	/**
	 * @description 네비게이터를 연다.
	 */
	gitbuilder.ui.OpenNavigatorWindow = function OpenNavigatorWindow() {
		$("#" + gitbuilder.variable.elementid.navigatingWindow).show();

	}
	// ==========================================================================================================
	/**
	 * @description 네비게이터를 닫는다.
	 */
	gitbuilder.ui.CloseNavigatorWindow = function CloseNavigatorWindow() {
		$("#" + gitbuilder.variable.elementid.navigatingWindow).hide();

	}
	// ==========================================================================================================
	/**
	 * @description 편집도구창을 생성
	 */
	gitbuilder.ui.EditingWindow = function EditingWindow() {
		var layers = gitbuilder.variable.selectedLayers;
		if (layers.getLength() !== 1) {
			console.error("gitbuilder.variable.selectedLayers doesn't have one layer");
			return;
		}
		if (!((layers.item(0) instanceof ol.layer.Vector) || (layers.item(0) instanceof ol.layer.Image && layers.item(0).getSource() instanceof ol.source.ImageVector))) {
			console.error("It is not vector layer");
			gitbuilder.ui.CloseEditingWindow();
			return;
		}
		if (!gitbuilder.variable.elementid.editingWindow) {
			var editingWindowId = "editingWindow";
			var count = 0;
			while (gitbuilder.method.isDuplicatedId(editingWindowId)) {
				editingWindowId += count;
			}
			gitbuilder.variable.elementid.editingWindow = editingWindowId;

			var featureListId = "featureListId";
			count = 0;
			while (gitbuilder.method.isDuplicatedId(featureListId)) {
				featureListId += count;
			}
			gitbuilder.variable.elementid.featureListId = featureListId;

			var editWin = '';

			editWin += '<div id="' + editingWindowId + '" class="GitBuilder-Editing-ToolBox">';

			editWin += '<div class="GitBuilder-Editing-ToolBox-Line GitBuilder-Editing-ToolBox-Line-FeatureList GitBuilder-Editing-ToolBox-Line-Collapsed" id="'
					+ gitbuilder.variable.elementid.featureListId + '">';
			editWin += '</div>';

			// editWin += '<div>';

			editWin += '<div class="btn-group-vertical GitBuilder-Editing-ToolBox-Line" role="group" aria-label="...">';
			editWin += '<button class="btn btn-default GitBuilder-Edit-Button" id="selectTool" onclick="gitbuilder.method.edit.SelectFeature()" title="Select features"><span class="glyphicon glyphicon-hand-up" aria-hidden="true"></span></button>';
			editWin += '<button class="btn btn-default GitBuilder-Edit-Button" id="drawTool" onclick="gitbuilder.method.edit.DrawFeature()" title="Draw features"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button>';
			editWin += '<button class="btn btn-default GitBuilder-Edit-Button" id="moveTool" onclick="gitbuilder.method.edit.MoveFeature()" title="Move features"><span class="glyphicon glyphicon-move" aria-hidden="true"></span></button>';
			editWin += '<button class="btn btn-default GitBuilder-Edit-Button" id="rotaTool" onclick="gitbuilder.method.edit.EditToolTip()" title="Rotate features"><span class="glyphicon glyphicon-repeat" aria-hidden="true"></span></button>';
			editWin += '<button class="btn btn-default GitBuilder-Edit-Button" id="modiTool" onclick="gitbuilder.method.edit.ModifyFeature()" title="Modify features"><span class="glyphicon glyphicon-wrench" aria-hidden="true"></span></button>';
			editWin += '<button class="btn btn-default GitBuilder-Edit-Button" id="remoTool" onclick="gitbuilder.method.edit.RemoveFeature()" title="Remove features"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></button>';
			editWin += '</div>';

			editWin += '<div class="btn-group-vertical GitBuilder-Editing-ToolBox-Line" role="group" aria-label="...">';
			editWin += '<button class="btn btn-default GitBuilder-Edit-Button GitBuilder-Editing-ToolBox-FeatureList" title="Feature list"><span class="glyphicon glyphicon-list" aria-hidden="true"></span></button>';
			editWin += '<button class="btn btn-default GitBuilder-Edit-Button" title="Attribute"><span class="glyphicon glyphicon-font" aria-hidden="true"></span></button>';
			editWin += '<button class="btn btn-default GitBuilder-Edit-Button" title="Copy feature"><span class="glyphicon glyphicon-copy" aria-hidden="true"></span></button>';
			editWin += '<button class="btn btn-default GitBuilder-Edit-Button" title="Paste feature"><span class="glyphicon glyphicon-paste" aria-hidden="true"></span></button>';
			editWin += '<button class="btn btn-default GitBuilder-Edit-Button" onclick="gitbuilder.method.edit.RemoveDuplicatedPoint()" title="Remove duplicated point"><span class="glyphicon glyphicon-record" aria-hidden="true"></span></button>';
			editWin += '</div>';

			// editWin += '<div id="' +
			// gitbuilder.variable.elementid.featureListId + '"
			// class="GitBuilder-Editing-FeatureList">';
			// var layers = gitbuilder.variable.selectedLayers;
			// if (layers.getLength() === 1) {
			// editWin += gitbuilder.ui.OutputFeatureList(layers.item(0));
			// }
			// editWin += '</div>';

			// editWin += '</div>';

			editWin += '</div>';

			var target = gitbuilder.variable.map.getTarget();
			console.log(target);

			$("#" + target).append(editWin);

			$("#" + gitbuilder.variable.elementid.editingWindow).draggable({});

			// $("#" + gitbuilder.variable.elementid.editingWindow).position({
			// my : "right top",
			// at : "right top",
			// of : "#" + target,
			// collision : "fit fit"
			// });

			// var map = gitbuilder.variable.map;
			// var extent = map.getView().calculateExtent(map.getSize());
			// console.log([ extent[1], extent[2] ]);

			$(document).on("click", ".GitBuilder-Editing-ToolBox-FeatureList", function() {
				if ($("#" + gitbuilder.variable.elementid.featureListId).hasClass("GitBuilder-Editing-ToolBox-Line-Collapsed")) {
					$("#" + gitbuilder.variable.elementid.featureListId).removeClass("GitBuilder-Editing-ToolBox-Line-Collapsed");
					$("#" + gitbuilder.variable.elementid.featureListId).addClass("GitBuilder-Editing-ToolBox-Line-NonCollapsed");
					$(".GitBuilder-Editing-ToolBox").removeClass("GitBuilder-Editing-ToolBox-Close");
					$(".GitBuilder-Editing-ToolBox").addClass("GitBuilder-Editing-ToolBox-Open");
				} else {
					$("#" + gitbuilder.variable.elementid.featureListId).removeClass("GitBuilder-Editing-ToolBox-Line-NonCollapsed");
					$("#" + gitbuilder.variable.elementid.featureListId).addClass("GitBuilder-Editing-ToolBox-Line-Collapsed");
					$(".GitBuilder-Editing-ToolBox").removeClass("GitBuilder-Editing-ToolBox-Open");
					$(".GitBuilder-Editing-ToolBox").addClass("GitBuilder-Editing-ToolBox-Close");
				}
				var well = $("#" + gitbuilder.variable.elementid.featureListId);
				$(well[0]).empty();
				var start = 0;
				var count = 5;
				var layer = gitbuilder.variable.selectedLayers.item(0);
				$(well[0]).append(gitbuilder.ui.OutputFeatureList(layer, start, count));
				console.log("event in");
			});

			$(document).on("click", ".GitBuilder-Edit-FeatureList-Feature", function() {
				var fid = $(this).find("td").eq(1).text();
				// console.log(fid);
				var layers = gitbuilder.variable.selectedLayers;
				if (layers.getLength() === 1) {
					var layer = layers.item(0);
					var feature = gitbuilder.method.feature.getFeatureByIdAndLayer(layer, fid);
					var source = new ol.source.Vector({
						features : [ feature ]
					});
					var features = source.getFeatures();
					if (features.length > 0) {
						gitbuilder.variable.view.fit(source.getExtent(), gitbuilder.variable.map.getSize());
					}
					gitbuilder.variable.selectedFeatures = new ol.Collection();
					gitbuilder.variable.selectedFeatures.push(feature);
					gitbuilder.method.edit.SelectFeature(gitbuilder.variable.selectedFeatures);

					if (gitbuilder.variable.selectedFeatures.getLength() === 1) {
						var str = gitbuilder.ui.OutputAttributeTable(features[0]);

						var popovr = $(str).get(0);
						var popup = gitbuilder.variable.overlay.feature;
						popup.setElement(popovr);

						gitbuilder.variable.map.addOverlay(popup);
						var element = popup.getElement();

						$(element).popover('destroy');
						// gitbuilder.variable.crsrCoordinate =
						// gitbuilder.variable.map.getView().getCenter();
						gitbuilder.variable.crsrCoordinate = feature.getGeometry().getFirstCoordinate();
						popup.setPosition(gitbuilder.variable.crsrCoordinate);
						// the keys are quoted to prevent renaming in ADVANCED
						// mode.
						$(element).popover({
							'placement' : 'top',
							'animation' : false,
							'html' : true
						});
						$(element).popover('show');

						if (layer.get("id") === "Validation Result") {
							gitbuilder.variable.errorFeatures.clear();
							gitbuilder.variable.errorFeatures.push(features[0]);
							console.log("선택한 에러 피처는 다음과 같습니다.");
							// console.log(gitbuilder.variable.errorFeatures);
						}

					} else {
						gitbuilder.ui.CloseAttributeTable();
					}
				}
			});
			$("#" + gitbuilder.variable.elementid.editingWindow).hide();
		} else {
			// $("#" + gitbuilder.variable.elementid.editingWindow).show();
			$("#" + gitbuilder.variable.elementid.featureListId).empty();
			var layers = gitbuilder.variable.selectedLayers;
			if (layers.getLength() === 1) {
				if ($("#" + gitbuilder.variable.elementid.featureListId).hasClass("GitBuilder-Editing-ToolBox-Line-NonCollapsed")) {
					var well = $("#" + gitbuilder.variable.elementid.featureListId);
					$(well[0]).empty();
					var start = 0;
					var count = 5;
					var layer = gitbuilder.variable.selectedLayers.item(0);
					$(well[0]).append(gitbuilder.ui.OutputFeatureList(layer, start, count));
					console.log("event in");
				}
			}
		}

		// $(document).on("click", ".GitBuilder-Edit-ErrorFeature-Link",
		// function() {
		// var ufid = $(this).attr("ufid");
		// gitbuilder.method.edit.LinkToErrorFeature(ufid);
		// console.log("error feature link");
		// });		

		$('[data-toggle="tooltip"]').tooltip();
		
		if ($("#" + gitbuilder.variable.elementid.editingWindow + " :visible").length > 0) {
			gitbuilder.ui.CloseEditingWindow();
		} else {
			gitbuilder.ui.OpenEditingWindow();
		}
		
		console.log("editing");		

	}
	// ==========================================================================================================
	/**
	 * @description 편집도구창을 연다.
	 */
	gitbuilder.ui.OpenEditingWindow = function OpenEditingWindow() {
//		gitbuilder.ui.EditingWindow();
		if (gitbuilder.variable.selectedLayers.getLength() === 1) {
			var layer = gitbuilder.variable.selectedLayers.item(0);
			if (layer instanceof ol.layer.Vector || (layer instanceof ol.layer.Image && layer.getSource() instanceof ol.source.ImageVector)) {
				$("#" + gitbuilder.variable.elementid.editingWindow).show();
				gitbuilder.variable.editingOpen = true;
			} else {
				console.error("can not edit. not vector");
			}
		} else {
			gitbuilder.ui.CloseAttributeTable();
		}

	}
	// ==========================================================================================================
	/**
	 * @description 편집도구창을 닫는다.
	 */
	gitbuilder.ui.CloseEditingWindow = function CloseEditingWindow() {

		$("#" + gitbuilder.variable.elementid.editingWindow).hide();
		gitbuilder.variable.editingOpen = false;

	}

	// ==========================================================================================================
	/**
	 * @description 피처 삭제 확인창 출력
	 */
	gitbuilder.ui.RemoveFeatureWindow = function RemoveFeatureWindow() {
		if (!gitbuilder.variable.elementid.removeFeatureWindow) {
			var removeWindowId = "removeFeatureWindow";
			var count = 0;
			while (gitbuilder.method.isDuplicatedId(removeWindowId)) {
				removeWindowId += count;
			}
			gitbuilder.variable.elementid.removeFeatureWindow = removeWindowId;

			var modal = "";
			modal += '<div id="' + removeWindowId
					+ '" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">';
			modal += '<div class="modal-dialog modal-sm">';
			modal += '<div class="modal-content">';
			modal += '<div class="modal-body">';
			modal += 'Features will be remove.';
			modal += '</div>';
			modal += '<div class="modal-footer">';
			modal += ' <button type="button" class="btn btn-default" data-dismiss="modal">No</button>';
			modal += '<button type="button" class="btn btn-primary" onclick="gitbuilder.method.edit.DeleteFeature()" data-dismiss="modal">Yes</button>';
			modal += '</div>';
			modal += '</div>';
			modal += '</div>';
			modal += '</div>';

			$("body").append(modal);
		}

		$('#' + gitbuilder.variable.elementid.removeFeatureWindow).modal('show');
	}
	// ==========================================================================================================
	/**
	 * @description 애트리뷰트 입력
	 */
	gitbuilder.ui.AddAttributeIntoTable = function AddAttributeIntoTable() {
		var keyName = $("#" + gitbuilder.variable.elementid.attrName).val();
		var keyType = $("#" + gitbuilder.variable.elementid.attrType).val();
		var trLength = $("#" + gitbuilder.variable.elementid.attrList).children().length + 1;
		var vector = '<tr><td><p class="text-center">' + trLength + '</p></td><td><p class="text-center">' + keyName
				+ '</p></td><td><p class="text-center">' + keyType
				+ '</p></td><td><p class="text-center"><button type="button" class="btn btn-default btn-xs DeleteAttr">Delete</button></p></td></tr>';
		// attrName: 속성명 텍스트인풋
		// attrType: 속성 데이터 타입 실렉트
		// attrList: 속성 리스트 테이블 바디
		$("#" + gitbuilder.variable.elementid.attrList).append(vector);

	}
	// ==========================================================================================================
	/**
	 * @description 애트리뷰트 삭제
	 */
	gitbuilder.ui.RemoveAttributeFromTable = function RemoveAttributeFromTable(idx) {
		console.log(idx);
		console.log("#" + gitbuilder.variable.elementid.attrList + " tr");
		$("#" + gitbuilder.variable.elementid.attrList + " tr").eq(idx).remove();
	}
	// ==========================================================================================================
	/**
	 * @description 검수 설정 창
	 */
	gitbuilder.ui.ValidationOptionWindow = function ValidationOptionWindow() {

		// feature 편집 mbr을 삭제한다 -김호철-
		gitbuilder.ui.RemoveEditToolTip();

		// 검수 실행 전 숨겨져있는 layer와 feature들을 삭제 -김호철-
		gitbuilder.variable.undoRedo.resetRedos();

		gitbuilder.variable.validationObj = {
			extent : undefined,
			layers : {}
		}

		if (!gitbuilder.variable.elementid.validatingWindow && !gitbuilder.variable.elementid.validationLayerList) {
			var validatingWindow = "validatingWindow";
			var count = 0;
			while (gitbuilder.method.isDuplicatedId(validatingWindow)) {
				validatingWindow += count;
			}
			gitbuilder.variable.elementid.validatingWindow = validatingWindow;

			var layerList = "QALayerList";
			count = 0;
			while (gitbuilder.method.isDuplicatedId(layerList)) {
				layerList += count;
			}
			gitbuilder.variable.elementid.validationLayerList = layerList;

			var valiOpt = "valiOption";
			count = 0;
			while (gitbuilder.method.isDuplicatedId(valiOpt)) {
				valiOpt += count;
			}
			gitbuilder.variable.elementid.validationOption = valiOpt;

			var relation = "layerRelation";
			count = 0;
			while (gitbuilder.method.isDuplicatedId(relation)) {
				relation += count;
			}
			gitbuilder.variable.elementid.layerRelation = relation;

			var valiWeight = "validationWeight";
			count = 0;
			while (gitbuilder.method.isDuplicatedId(valiWeight)) {
				valiWeight += count;
			}
			gitbuilder.variable.elementid.validationWeight = valiWeight;

			var info = "validationInfo";
			count = 0;
			while (gitbuilder.method.isDuplicatedId(info)) {
				info += count;
			}
			gitbuilder.variable.elementid.validationInfo = info;

			var varea = "validationAreaLayer";
			count = 0;
			while (gitbuilder.method.isDuplicatedId(varea)) {
				varea += count;
			}
			gitbuilder.variable.elementid.validationAreaLayer = varea;

			var modal = "";
			modal += '<div id="' + validatingWindow
					+ '" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">';
			modal += '<div class="modal-dialog modal-lg">';

			modal += '<div class="modal-content">';

			modal += '<div class="modal-header">';
			modal += ' <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
			modal += ' <h4 class="modal-title">Data Validation Setting</h4>';
			modal += ' </div>';

			modal += '<div class="modal-body">';
			modal += '<div class="row">';

			modal += '<div class="col-md-4">';
			modal += '<div class="panel panel-default">';
			modal += '<div class="panel-heading">Layer List</div>';
			modal += '<div class="panel-body">';
			modal += '<div style="margin:6px;">Validation Area</div>';
			modal += '<div id="' + gitbuilder.variable.elementid.validationAreaLayer + '">';
			modal += gitbuilder.ui.OutputVectorLayersSelect();
			modal += '</div>';
			modal += '<div style="margin:6px;">Validation Layers</div>';
			modal += '<div id="' + layerList + '">'
			modal += gitbuilder.ui.OutputVectorLayers();
			modal += '</div>'
			modal += '</div>';
			modal += '</div>';
			modal += '</div>';

			modal += '<div class="col-md-4">';
			modal += '<div class="panel panel-default">';
			modal += '<div class="panel-heading">Validation Option</div>';
			modal += '<div class="panel-body">';
			modal += '<div class="input-group">';
			modal += '<input type="number" class="form-control" min="0" max="100" id="' + valiWeight + '" placeholder="Weight">';
			modal += '<span class="input-group-addon">%</span>';
			modal += '</div>';
			modal += '<div id="' + valiOpt + '">';
			// modal += 'some option';
			modal += '</div>';
			modal += '</div>';
			modal += '</div>';
			modal += '</div>';

			modal += '<div class="col-md-4">';
			modal += '<div class="panel panel-default">';
			modal += '<div class="panel-heading">Detailed Option</div>';
			modal += '<div class="panel-body" id="' + relation + '">';
			modal += '-';
			modal += '</div>';
			modal += '</div>';
			modal += '</div>';

			modal += ' </div>';

			modal += '<div class="row">';
			modal += '<div class="col-md-12">';
			modal += '<div class="panel panel-default">';
			modal += '<div class="panel-heading">Information</div>';
			modal += '<div class="panel-body" id="' + info + '">';

			modal += '</div>';
			modal += '</div>';
			modal += '</div>';
			modal += '</div>';
			modal += '</div>';

			modal += '<div class="modal-footer">';
			modal += '<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>';
			modal += '<button type="button" class="btn btn-primary GitBuilder-Button-Validation" data-dismiss="modal" onclick="gitbuilder.method.validation.RequestValidation()">Validate</button>';
			modal += '</div>';

			modal += '</div>';

			modal += '</div>';
			modal += '</div>';

			$("body").append(modal);

			$(".GitBuilder-QAList-ValidationArea option:selected").each(function() {
				var layer = gitbuilder.method.layer.getLayerById($(this).val());
				gitbuilder.variable.validationArea = layer;
				$("#" + gitbuilder.variable.elementid.validationLayerList).empty();
				var list = gitbuilder.ui.OutputVectorLayers(layer);
				$("#" + gitbuilder.variable.elementid.validationLayerList).append(list);
				delete gitbuilder.variable.validationObj.layers[$(this).val()];
			});

			// 검수영역
			$(document).on("change", ".GitBuilder-QAList-ValidationArea", function() {
				$(".GitBuilder-QAList-ValidationArea option:selected").each(function() {
					var layer = gitbuilder.method.layer.getLayerById($(this).val());
					gitbuilder.variable.validationArea = layer;
					$("#" + gitbuilder.variable.elementid.validationLayerList).empty();
					var list = gitbuilder.ui.OutputVectorLayers(layer);
					$("#" + gitbuilder.variable.elementid.validationLayerList).append(list);
					delete gitbuilder.variable.validationObj.layers[$(this).val()];
				});
				// console.log(gitbuilder.variable.validationObj);
			});

			// 가중치
			$("#" + gitbuilder.variable.elementid.validationWeight).on(
					"input",
					function() {
						console.log($(this).val());
						gitbuilder.variable.validationObj.layers[gitbuilder.variable.validationLayer.after].weight = ($(this).val() === "") ? 0 : $(
								this).val();
						$("#" + gitbuilder.variable.elementid.validationInfo).empty();
						$("#" + gitbuilder.variable.elementid.validationInfo).append(gitbuilder.ui.OutputValidationOverview());

						// console.log(gitbuilder.variable.validationObj);
					});

			// gitbuilder.variable.validationLayer.after 현재 레이어
			$(document).on("click", ".GitBuilder-QAList-Item", function() {
				$(this).siblings(".GitBuilder-QAList-Item-active").removeClass("GitBuilder-QAList-Item-active");
				$(this).addClass("GitBuilder-QAList-Item-active");
				var nowId = $(this).attr("layerid");
				if (gitbuilder.variable.validationLayer.after === undefined) {
					gitbuilder.variable.validationLayer.after = nowId;
				} else {
					gitbuilder.variable.validationLayer.before = gitbuilder.variable.validationLayer.after;
					// var beforeId =
					// gitbuilder.variable.validationLayer.before;
					// if (!gitbuilder.variable.validationObj[beforeId]) {
					// gitbuilder.variable.validationObj[beforeId] = {
					// weight : $("#" +
					// gitbuilder.variable.elementid.validationWeight).val()
					// };
					// } else {
					// gitbuilder.variable.validationObj[beforeId].weight =
					// $("#" +
					// gitbuilder.variable.elementid.validationWeight).val();
					// }

					gitbuilder.variable.validationLayer.after = nowId;

					// if (gitbuilder.variable.validationLayer.before !==
					// gitbuilder.variable.elementid.validationWeight) {
					// $("#" +
					// gitbuilder.variable.elementid.validationWeight).val("");
					// }
				}

				gitbuilder.method.edit.CreateValidationArea({
					area : gitbuilder.variable.validationArea,
					validation : gitbuilder.method.layer.getLayerById(nowId)
				});

				// var retriveWeight = "";
				// if (gitbuilder.variable.validationObj[id]) {
				// retriveWeight = gitbuilder.variable.validationObj[id].weight;
				// }
				// console.log($(this).attr("layerid"));
				$("#" + gitbuilder.variable.elementid.validationOption).empty();
				// $("#" +
				// gitbuilder.variable.elementid.validationWeight).val(retriveWeight);
				var layer = gitbuilder.method.layer.getLayerById(nowId);
				var ul = gitbuilder.ui.OutputValidationOption(layer);
				$("#" + gitbuilder.variable.elementid.validationOption).append(ul);
				if (gitbuilder.variable.validationObj.layers.hasOwnProperty(nowId)) {
					if (gitbuilder.variable.validationObj.layers[nowId].hasOwnProperty("weight")) {
						$("#" + gitbuilder.variable.elementid.validationWeight).val(gitbuilder.variable.validationObj.layers[nowId].weight);
					}
				}
				$("#" + gitbuilder.variable.elementid.layerRelation).empty();
			});

			// gitbuilder.variable.validationOption.after 현재 선택한 옵션
			$(document)
					.on(
							"click",
							".GitBuilder-QAList-OptionName",
							function() {
								$(this).siblings(".GitBuilder-QAList-Item-active").removeClass("GitBuilder-QAList-Item-active");
								$(this).addClass("GitBuilder-QAList-Item-active");
								var checkBox = $(this).find("input:checkbox").val();
								// console.log(checkBox);
								if (gitbuilder.variable.validationOption.after === undefined) {
									gitbuilder.variable.validationOption.after = checkBox;
								} else {
									gitbuilder.variable.validationOption.before = gitbuilder.variable.validationOption.after;

									// gitbuilder.variable.validationObj[gitbuilder.variable.validationLayer.before]
									// = {
									// // 옵션저장
									// };

									gitbuilder.variable.validationOption.after = checkBox;
								}
								$("#" + gitbuilder.variable.elementid.layerRelation).empty();
								$("#" + gitbuilder.variable.elementid.layerRelation).append(
										gitbuilder.ui.OutputDetailedOption(gitbuilder.variable.validationOption.after));

								switch (gitbuilder.variable.validationOption.after) {
								case "SmallLength":
									// 길이
									$("#" + gitbuilder.variable.elementid.smallLength)
											.on(
													"input",
													function() {
														console.log($(this).val());
														gitbuilder.variable.validationObj.layers[gitbuilder.variable.validationLayer.after].qaOption.SmallLength = $(
																this).val();
														// console.log(gitbuilder.variable.validationObj);
													});
									break;

								case "SmallArea":
									// 넓이
									$("#" + gitbuilder.variable.elementid.smallArea)
											.on(
													"input",
													function() {
														console.log($(this).val());
														gitbuilder.variable.validationObj.layers[gitbuilder.variable.validationLayer.after].qaOption.SmallArea = $(
																this).val();
														// console.log(gitbuilder.variable.validationObj);
													});
									break;

								case "ConOverDegree":
									// 꺾임
									$("#" + gitbuilder.variable.elementid.conOverDegree)
											.on(
													"input",
													function() {
														console.log($(this).val());
														gitbuilder.variable.validationObj.layers[gitbuilder.variable.validationLayer.after].qaOption.ConOverDegree = $(
																this).val();
														// console.log(gitbuilder.variable.validationObj);
													});
									break;

								default:
									break;
								}
								$("#" + gitbuilder.variable.elementid.validationInfo).empty();
								$("#" + gitbuilder.variable.elementid.validationInfo).append(gitbuilder.ui.OutputValidationOverview());
							});

			$(document).on("change", ".GitBuilder-QAList-QAOption-CheckBox", function() {
				if ($(this).is(":checked")) {
					console.log($(this).val());
					var layerId = gitbuilder.variable.validationLayer.after;
					console.log("");
					if (!gitbuilder.variable.validationObj.layers[layerId]) {
						gitbuilder.variable.validationObj.layers[layerId] = {};
						var layer = gitbuilder.variable.validationObj.layers[layerId];
						if (!layer.qaOption) {
							layer.qaOption = {};
						}
					}
					var qaObj = gitbuilder.variable.validationObj.layers[layerId].qaOption;
					var qaName = $(this).val();
					if (!qaObj[qaName]) {
						qaObj[qaName] = "";
					}
					// console.log(gitbuilder.variable.validationObj);
				} else {
					var layerId = gitbuilder.variable.validationLayer.after;
					var obj = gitbuilder.variable.validationObj.layers[layerId].qaOption;
					var qaName = $(this).val();
					delete obj[qaName];
					var key = Object.keys(obj);
					if (key.length === 0) {
						gitbuilder.variable.validationObj.layers[layerId].qaOption = {};
					}
					// console.log(gitbuilder.variable.validationObj);
				}

			});

			$(document).on("change", ".GitBuilder-QAList-CheckBox-AllToggle", function() {
				if ($(this).is(":checked")) {
					$(".GitBuilder-QAList-Relation-CheckBox").prop("checked", true);

					var check = $(".GitBuilder-QAList-Relation-CheckBox");
					$(check).each(function() {
						if ($(this).is(":checked")) {
							console.log($(this).val());
							var layerId = gitbuilder.variable.validationLayer.after;

							var obj = gitbuilder.variable.validationObj.layers[layerId].qaOption;
							if (!Array.isArray(obj[gitbuilder.variable.validationOption.after])) {
								obj[gitbuilder.variable.validationOption.after] = [];
							}
							obj[gitbuilder.variable.validationOption.after].push($(this).val());

							// console.log(gitbuilder.variable.validationObj);
						} else {
							console.log($(this).val());
							var layerId = gitbuilder.variable.validationLayer.after;

							var obj = gitbuilder.variable.validationObj.layers[layerId].qaOption;
							if (!Array.isArray(obj[gitbuilder.variable.validationOption.after])) {
								obj[gitbuilder.variable.validationOption.after] = [];
							}
							var idx = obj[gitbuilder.variable.validationOption.after].indexOf($(this).val());
							obj[gitbuilder.variable.validationOption.after].splice(idx, 1);
							if (obj[gitbuilder.variable.validationOption.after].length === 0) {
								delete obj[gitbuilder.variable.validationOption.after];
							}
							// console.log(gitbuilder.variable.validationObj);
						}
					});

				} else {
					$(".GitBuilder-QAList-Relation-CheckBox").prop("checked", false);

					var check = $(".GitBuilder-QAList-Relation-CheckBox");
					$(check).each(function() {
						if ($(this).is(":checked")) {
							console.log($(this).val());
							var layerId = gitbuilder.variable.validationLayer.after;

							var obj = gitbuilder.variable.validationObj.layers[layerId].qaOption;
							if (!Array.isArray(obj[gitbuilder.variable.validationOption.after])) {
								obj[gitbuilder.variable.validationOption.after] = [];
							}
							obj[gitbuilder.variable.validationOption.after].push($(this).val());

							// console.log(gitbuilder.variable.validationObj);
						} else {
							// console.log($(this).val());
							var layerId = gitbuilder.variable.validationLayer.after;

							var obj = gitbuilder.variable.validationObj.layers[layerId].qaOption;
							if (!Array.isArray(obj[gitbuilder.variable.validationOption.after])) {
								obj[gitbuilder.variable.validationOption.after] = [];
							}
							var idx = obj[gitbuilder.variable.validationOption.after].indexOf($(this).val());
							obj[gitbuilder.variable.validationOption.after].splice(idx, 1);
							if (obj[gitbuilder.variable.validationOption.after].length === 0) {
								delete obj[gitbuilder.variable.validationOption.after];
							}
							// console.log(gitbuilder.variable.validationObj);
						}
					});
				}
			});

			$(document).on("change", ".GitBuilder-QAList-Relation-CheckBox", function() {
				if ($(this).is(":checked")) {
					// console.log($(this).val());
					var layerId = gitbuilder.variable.validationLayer.after;

					gitbuilder.method.edit.CreateValidationArea({
						area : gitbuilder.variable.validationArea,
						validation : gitbuilder.method.layer.getLayerById($(this).val())
					});

					var obj = gitbuilder.variable.validationObj.layers[layerId].qaOption;
					if (!Array.isArray(obj[gitbuilder.variable.validationOption.after])) {
						obj[gitbuilder.variable.validationOption.after] = [];
					}
					obj[gitbuilder.variable.validationOption.after].push($(this).val());

					// console.log(gitbuilder.variable.validationObj);
				} else {
					// console.log($(this).val());
					var layerId = gitbuilder.variable.validationLayer.after;

					var obj = gitbuilder.variable.validationObj.layers[layerId].qaOption;
					if (!Array.isArray(obj[gitbuilder.variable.validationOption.after])) {
						obj[gitbuilder.variable.validationOption.after] = [];
					}
					var idx = obj[gitbuilder.variable.validationOption.after].indexOf($(this).val());
					obj[gitbuilder.variable.validationOption.after].splice(idx, 1);
					if (obj[gitbuilder.variable.validationOption.after].length === 0) {
						delete obj[gitbuilder.variable.validationOption.after];
					}

					if (Object.keys(gitbuilder.variable.validationObj.layers[$(this).val()].qaOption).length === 0) {
						delete gitbuilder.variable.validationObj.layers[$(this).val()];
					}
					// console.log(gitbuilder.variable.validationObj);
				}
			});

			$(document).on("change", ".GitBuilder-QAList-Attribute-CheckBox", function() {
				if ($(this).is(":checked")) {
					// console.log($(this).val());
					var layerId = gitbuilder.variable.validationLayer.after;

					var obj = gitbuilder.variable.validationObj.layers[layerId].qaOption;
					if (!Array.isArray(obj[gitbuilder.variable.validationOption.after])) {
						obj[gitbuilder.variable.validationOption.after] = [];
					}
					obj[gitbuilder.variable.validationOption.after].push($(this).val());

					// console.log(gitbuilder.variable.validationObj);
				} else {
					// console.log($(this).val());
					var layerId = gitbuilder.variable.validationLayer.after;

					var obj = gitbuilder.variable.validationObj.layers[layerId].qaOption;
					if (!Array.isArray(obj[gitbuilder.variable.validationOption.after])) {
						obj[gitbuilder.variable.validationOption.after] = [];
					}
					var idx = obj[gitbuilder.variable.validationOption.after].indexOf($(this).val());
					obj[gitbuilder.variable.validationOption.after].splice(idx, 1);
					if (obj[gitbuilder.variable.validationOption.after].length === 0) {
						delete obj[gitbuilder.variable.validationOption.after];
					}
					// console.log(gitbuilder.variable.validationObj);
				}
			});

		} else {
			$("#" + gitbuilder.variable.elementid.validationLayerList).empty();
			var list = gitbuilder.ui.OutputVectorLayers();
			$("#" + gitbuilder.variable.elementid.validationLayerList).append(list);

			$(".GitBuilder-QAList-ValidationArea option:selected").each(function() {
				var layer = gitbuilder.method.layer.getLayerById($(this).val());
				gitbuilder.variable.validationArea = layer;
				$("#" + gitbuilder.variable.elementid.validationLayerList).empty();
				var list = gitbuilder.ui.OutputVectorLayers(layer);
				$("#" + gitbuilder.variable.elementid.validationLayerList).append(list);
				delete gitbuilder.variable.validationObj.layers[$(this).val()];
			});

		}

		$("#" + gitbuilder.variable.elementid.validationInfo).empty();
		$("#" + gitbuilder.variable.elementid.validationInfo).append(gitbuilder.ui.OutputValidationOverview());

		$('#' + gitbuilder.variable.elementid.validatingWindow).modal('show');
	}
	// ==========================================================================================================
	/**
	 * @description 벡터 레이어 목록 출력
	 * @param {ol.layer.Layer}
	 *            제외할 레이어
	 * @returns {String} 레이어 목록 리스트 태그형태
	 */
	gitbuilder.ui.OutputVectorLayers = function OutputVectorLayers(layer) {
		var vector = gitbuilder.method.layer.getAllVectorLayer();
		var isSkip = false;
		if (layer instanceof ol.layer.Vector || (layer instanceof ol.layer.Image && layer.getSource() instanceof ol.source.ImageVector)) {
			isSkip = true;
		}
		var str = '<ul class="list-group">';

		if (isSkip) {
			for (var i = 0; i < vector.getLength(); i++) {
				if (vector.item(i).get("id") !== layer.get("id")) {
					str += '<li class="list-group-item GitBuilder-QAList-Item" layerid="' + vector.item(i).get("id") + '">'
							+ vector.item(i).get("name") + '</li>';
				}
			}
		} else {
			for (var i = 0; i < vector.getLength(); i++) {
				str += '<li class="list-group-item GitBuilder-QAList-Item" layerid="' + vector.item(i).get("id") + '">' + vector.item(i).get("name")
						+ '</li>';
			}
		}

		str += '</ul>';
		return str;
	}
	// ==========================================================================================================
	/**
	 * @description 검수영역용 벡터 레이어 목록 출력
	 * @returns {String} 레이어 목록 실렉트 태그형태
	 */
	gitbuilder.ui.OutputVectorLayersSelect = function OutputVectorLayersSelect() {
		var vector = gitbuilder.method.layer.getAllVectorLayer();
		var str = '<select class="form-control GitBuilder-QAList-ValidationArea">';
		for (var i = 0; i < vector.getLength(); i++) {
			if (vector.item(i).get("type") === "MultiLineString") {
				str += '<option class="" value="' + vector.item(i).get("id") + '"';
				if (i === 0) {
					str += "selected";
				}
				str += '>' + vector.item(i).get("name") + '</option>';
			}
		}
		str += '</select>';
		return str;
	}
	// ==========================================================================================================
	/**
	 * @description 벡터 레이어 목록 출력
	 * @returns {String} 레이어 목록 실렉트 태그형태
	 */
	gitbuilder.ui.OutputAllVectorLayersSelect = function OutputAllVectorLayersSelect() {
		var vector = gitbuilder.method.layer.getAllVectorLayer();
		var str = '<select class="form-control GitBuilder-QAList-AllVectorCRS">';
		for (var i = 0; i < vector.getLength(); i++) {
			str += '<option class="" value="' + vector.item(i).get("id") + '"';
			if (i === 0) {
				str += "selected";
			}
			str += '>' + vector.item(i).get("name") + '</option>';
		}
		str += '</select>';
		return str;
	}
	// ==========================================================================================================
	/**
	 * @description 벡터 레이어 목록 출력(체크박스)
	 * @returns {String} 레이어 목록 리스트 태그형태
	 */
	gitbuilder.ui.OutputVectorLayersWithCheckBox = function OutputVectorLayersWithCheckBox(ids) {
		var layer = gitbuilder.variable.validationArea;
		var vector = gitbuilder.method.layer.getAllVectorLayer();
		var str = '<ul class="list-group">';
		str += '<li class="list-group-item active GitBuilder-QAList-DetailOption" >';
		str += '<label>';
		str += '<input type="checkbox" class="GitBuilder-QAList-CheckBox-AllToggle GitBuilder-Margin-CheckBoxOrRadio" >';
		str += 'Toggle all';
		str += '</label>';
		str += '</li>';
		for (var i = 0; i < vector.getLength(); i++) {
			if (vector.item(i) !== layer) {
				str += '<li class="list-group-item GitBuilder-QAList-DetailOption" >';
				str += '<label>';
				str += '<input type="checkbox" class="GitBuilder-QAList-CheckBox GitBuilder-QAList-Relation-CheckBox GitBuilder-Margin-CheckBoxOrRadio" value="'
						+ vector.item(i).get("id") + '"';
				if (Array.isArray(ids)) {
					for (var j = 0; j < ids.length; j++) {
						if (vector.item(i).get("id") === ids[j]) {
							str += 'checked';
							break;
						}
					}
				}
				str += '>';
				str += vector.item(i).get("name");
				str += '</label>';
				str += '</li>';
			}
		}
		str += '</ul>';
		return str;
	}
	// ==========================================================================================================
	/**
	 * @description 벡터 레이어 목록 출력(라디오버튼)
	 * @param {String}
	 *            라디오버튼 네임
	 * @returns {String} 레이어 목록 리스트 태그형태
	 */
	gitbuilder.ui.OutputVectorLayersWithRadio = function OutputVectorLayersWithRadio(name) {
		var vector = gitbuilder.method.layer.getAllVectorLayer();
		var str = '<ul class="list-group">';
		for (var i = 0; i < vector.getLength(); i++) {

			str += '<li class="list-group-item" >';
			str += '<label>';
			str += '<input type="radio" name="' + name + '" class="GitBuilder-Operation-Radio GitBuilder-Margin-CheckBoxOrRadio" value="'
					+ vector.item(i).get("id") + '">';
			str += vector.item(i).get("name");
			str += '</label>';
			str += '</li>';

		}
		str += '</ul>';
		return str;
	}
	// ==========================================================================================================
	/**
	 * @description 검수 옵션 오버뷰
	 * @returns {String} 오버뷰 태그형태
	 */
	gitbuilder.ui.OutputValidationOverview = function OutputValidationOverview() {
		var qaObj = gitbuilder.variable.validationObj.layers;
		var key = Object.keys(qaObj);
		var str = '';
		str += '<table class="table table-condensed">';
		str += '<thead>';
		str += '<tr>';
		str += '<td class="col-md-4"><p class="text-center">Name</p></td>';
		str += '<td class="col-md-4"><p class="text-center">Type</p></td>';
		str += '<td class="col-md-4"><p class="text-center">Weight</p></td>';
		str += '</tr>';
		str += '</thead>';
		str += '<tbody>';
		var total = 0;
		for (var i = 0; i < key.length; i++) {
			var layer = gitbuilder.method.layer.getLayerById(key[i]);
			str += '<tr>';
			str += '<td class="col-md-4"><p class="text-center">' + layer.get("name") + '</p></td>';
			str += '<td class="col-md-4"><p class="text-center">' + layer.get("type") + '</p></td>';
			str += '<td class="col-md-4"><p class="text-center">' + qaObj[key[i]].weight + '</p></td>';
			total += parseInt(qaObj[key[i]].weight);
			str += '</tr>';
		}
		str += '</tbody>';
		str += '<tfoot>';
		str += '<tr>';
		str += '<td class="col-md-4"></td>';
		str += '<td class="col-md-4"></td>';
		str += '<td class="col-md-4"><p class="text-center">' + total + '%</p></td>';
		str += '</tr>';
		str += '</tfoot>';
		str += '</table>';

		if (total > 100) {
			alertPopup("Warning", "Total weight can not exceed 100%.");
			// alert("Total weight can not exceed 100%.");
			$(".GitBuilder-Button-Validation").prop("disabled", true);
		} else {
			$(".GitBuilder-Button-Validation").prop("disabled", false);
		}
		return str;
	}
	// ==========================================================================================================
	/**
	 * @description 애트리뷰트 목록 출력(체크박스)
	 * @returns {String} 애트리뷰트 목록 리스트 태그형태
	 */
	gitbuilder.ui.OutputAttributeWithCheckBox = function OutputAttributeWithCheckBox(layer, attrKeys) {
		var attr = layer.get("attribute");
		var str = '<ul class="list-group">';
		var key = Object.keys(attr);
		for (var i = 0; i < key.length; i++) {
			str += '<li class="list-group-item GitBuilder-QAList-DetailOption" >';
			str += '<input type="checkbox" class="GitBuilder-QAList-CheckBox GitBuilder-QAList-Attribute-CheckBox GitBuilder-Margin-CheckBoxOrRadio" value="'
					+ key[i] + '"';
			if (Array.isArray(attrKeys)) {
				for (var j = 0; j < attrKeys.length; j++) {
					if (key[i] === attrKeys[j]) {
						str += "checked";
					}
				}
			}
			str += '>';
			str += key[i];
			str += '</li>';
		}
		str += '</ul>';
		return str;
	}
	// ==========================================================================================================
	/**
	 * @description 레이어별 검수옵션 목록 출력
	 * @param {ol.layer.Vector}
	 *            검수 옵션을 설정할 레이어
	 * @returns {String} 검수옵션 목록 리스트 태그 형태
	 */
	gitbuilder.ui.OutputValidationOption = function OutputValidationOption(layer) {
		// 사용가능 검수항목
		// gitbuilder.validation.validationOption = {
		// point: ["EntityDuplicated", "SelfEntity", "AttributeFix"],
		// linestring: ["SmallLength", "EntityDuplicated", "SelfEntity",
		// "PointDuplicated", "ConIntersected", "ConOverDegree", "ConBreak",
		// "AttributeFIx"],
		// polygon: ["SmallArea", "EntityDuplicated", "SelfEntity",
		// "PointDuplicated", "AttributeFix"]
		// }
		// gitbuilder.variable.validationObj.layers[gitbuilder.variable.validationLayer.after].qaOption.SmallLength

		var type = layer.get("type");
		var str = '<ul class="list-group">';
		if (type === "MultiPoint") {
			var ptOption = gitbuilder.validation.validationOption.point;
			for (var i = 0; i < ptOption.length; i++) {
				str += '<li class="list-group-item GitBuilder-QAList-OptionName">';
				str += '<input type="checkbox" class="GitBuilder-QAList-CheckBox GitBuilder-QAList-QAOption-CheckBox GitBuilder-Margin-CheckBoxOrRadio" value="'
						+ ptOption[i] + '"';

				var id = layer.get("id");
				var keys = Object.keys(gitbuilder.variable.validationObj.layers);

				if (keys.length > 0) {
					try {
						var optKeys = Object.keys(gitbuilder.variable.validationObj.layers[id].qaOption);
						for (var j = 0; j < optKeys.length; j++) {
							if (ptOption[i] === optKeys[j]) {
								str += "checked"
							}
						}
					} catch (err) {
						console.error(err);
					}
				}

				str += '>';
				str += ptOption[i];
				str += '</li>';
			}
		} else if (type === "MultiLineString") {
			var lsOption = gitbuilder.validation.validationOption.linestring;
			for (var i = 0; i < lsOption.length; i++) {
				str += '<li class="list-group-item GitBuilder-QAList-OptionName">';
				str += '<input type="checkbox" class="GitBuilder-QAList-CheckBox GitBuilder-QAList-QAOption-CheckBox GitBuilder-Margin-CheckBoxOrRadio" value="'
						+ lsOption[i] + '"';

				var id = layer.get("id");
				var keys = Object.keys(gitbuilder.variable.validationObj.layers);
				if (keys.length > 0) {
					try {
						var optKeys = Object.keys(gitbuilder.variable.validationObj.layers[id].qaOption);
						for (var j = 0; j < optKeys.length; j++) {
							if (lsOption[i] === optKeys[j]) {
								str += "checked"
							}
						}
					} catch (err) {
						console.error(err);
					}
				}

				str += '>';
				str += lsOption[i];
				str += '</li>';
			}
		} else if (type === "MultiPolygon") {
			var pgOption = gitbuilder.validation.validationOption.polygon;
			for (var i = 0; i < pgOption.length; i++) {
				str += '<li class="list-group-item GitBuilder-QAList-OptionName">';
				str += '<input type="checkbox" class="GitBuilder-QAList-CheckBox GitBuilder-QAList-QAOption-CheckBox GitBuilder-Margin-CheckBoxOrRadio" value="'
						+ pgOption[i] + '"';

				var id = layer.get("id");
				var keys = Object.keys(gitbuilder.variable.validationObj.layers);
				if (keys.length > 0 && gitbuilder.variable.validationObj.layers.hasOwnProperty(id)) {
					if (gitbuilder.variable.validationObj.layers[id].hasOwnProperty('qaOption')) {
						var optKeys = Object.keys(gitbuilder.variable.validationObj.layers[id].qaOption);
						for (var j = 0; j < optKeys.length; j++) {
							if (pgOption[i] === optKeys[j]) {
								str += "checked"
							}
						}
					}
				}

				str += '>';
				str += pgOption[i];
				str += '</li>';
			}
		} else if (type === "Point") {
			var ptOption = gitbuilder.validation.validationOption.point;
			for (var i = 0; i < ptOption.length; i++) {
				str += '<li class="list-group-item GitBuilder-QAList-OptionName">';
				str += '<input type="checkbox" class="GitBuilder-QAList-CheckBox GitBuilder-QAList-QAOption-CheckBox GitBuilder-Margin-CheckBoxOrRadio" value="'
						+ ptOption[i] + '"';

				var id = layer.get("id");
				var keys = Object.keys(gitbuilder.variable.validationObj.layers);
				if (keys.length > 0) {
					var optKeys = Object.keys(gitbuilder.variable.validationObj.layers[id].qaOption);
					for (var j = 0; j < optKeys.length; j++) {
						if (ptOption[i] === optKeys[j]) {
							str += "checked"
						}
					}
				}

				str += '>';
				str += ptOption[i];
				str += '</li>';
			}
		} else if (type === "LineString") {
			var lsOption = gitbuilder.validation.validationOption.linestring;
			for (var i = 0; i < lsOption.length; i++) {
				str += '<li class="list-group-item GitBuilder-QAList-OptionName">';
				str += '<input type="checkbox" class="GitBuilder-QAList-CheckBox GitBuilder-QAList-QAOption-CheckBox GitBuilder-Margin-CheckBoxOrRadio" value="'
						+ lsOption[i] + '"';

				var id = layer.get("id");
				var keys = Object.keys(gitbuilder.variable.validationObj.layers);
				if (keys.length > 0) {
					var optKeys = Object.keys(gitbuilder.variable.validationObj.layers[id].qaOption);
					for (var j = 0; j < optKeys.length; j++) {
						if (lsOption[i] === optKeys[j]) {
							str += "checked"
						}
					}
				}

				str += '>';
				str += lsOption[i];
				str += '</li>';
			}
		} else if (type === "Polygon") {
			var pgOption = gitbuilder.validation.validationOption.polygon;
			for (var i = 0; i < pgOption.length; i++) {
				str += '<li class="list-group-item GitBuilder-QAList-OptionName">';
				str += '<input type="checkbox" class="GitBuilder-QAList-CheckBox GitBuilder-QAList-QAOption-CheckBox GitBuilder-Margin-CheckBoxOrRadio" value="'
						+ pgOption[i] + '"';

				var id = layer.get("id");
				var keys = Object.keys(gitbuilder.variable.validationObj.layers);
				if (keys.length > 0) {
					var optKeys = Object.keys(gitbuilder.variable.validationObj.layers[id].qaOption);
					for (var j = 0; j < optKeys.length; j++) {
						if (pgOption[i] === optKeys[j]) {
							str += "checked"
						}
					}
				}

				str += '>';
				str += pgOption[i];
				str += '</li>';
			}
		}
		str += '</ul>';
		return str;
	}
	// ==========================================================================================================
	/**
	 * @description 검수옵션별 세부 기준값 목록 출력
	 * @param {String}
	 *            검수 옵션명
	 * @returns {String} 검수옵션 세부 기준값 목록 리스트 태그 형태
	 */
	gitbuilder.ui.OutputDetailedOption = function OutputDetailedOption(optName) {
		// 사용가능 검수항목
		// gitbuilder.validation.validationOption = {
		// point: ["EntityDuplicated", "SelfEntity", "AttributeFix"],
		// linestring: ["SmallLength", "EntityDuplicated", "SelfEntity",
		// "PointDuplicated", "ConIntersected", "ConOverDegree", "ConBreak",
		// "AttributeFIx"],
		// polygon: ["SmallArea", "EntityDuplicated", "SelfEntity",
		// "PointDuplicated", "AttributeFix"]
		// }

		var length = "smallLength";
		var count = 0;
		while (gitbuilder.method.isDuplicatedId(length)) {
			length += count;
		}
		gitbuilder.variable.elementid.smallLength = length;

		var area = "smallArea";
		count = 0;
		while (gitbuilder.method.isDuplicatedId(area)) {
			area += count;
		}
		gitbuilder.variable.elementid.smallArea = area;

		var degree = "overDegree";
		count = 0;
		while (gitbuilder.method.isDuplicatedId(degree)) {
			degree += count;
		}
		gitbuilder.variable.elementid.conOverDegree = degree;

		var str = '';

		switch (optName) {
		// case "EntityDuplicated":
		// str += gitbuilder.ui.OutputVectorLayersWithCheckBox();
		// break;
		case "SelfEntity":
			str += gitbuilder.ui
					.OutputVectorLayersWithCheckBox(gitbuilder.variable.validationObj.layers[gitbuilder.variable.validationLayer.after].qaOption.SelfEntity);
			break;
		case "AttributeFix":
			var layer = gitbuilder.method.layer.getLayerById(gitbuilder.variable.validationLayer.after);
			str += gitbuilder.ui.OutputAttributeWithCheckBox(layer,
					gitbuilder.variable.validationObj.layers[gitbuilder.variable.validationLayer.after].qaOption.AttributeFix);
			break;
		case "SmallLength":
			str += '<div class="GitBuilder-QAList-DetailOption">';
			str += '<span class="input-group">';
			str += '<input type="number" class="form-control GitBuilder-QAList-Value-Text" min="0" placeholder="Length" id="' + length + '"';
			if (gitbuilder.variable.validationObj.layers[gitbuilder.variable.validationLayer.after].qaOption.SmallLength !== undefined) {
				str += 'value="' + gitbuilder.variable.validationObj.layers[gitbuilder.variable.validationLayer.after].qaOption.SmallLength + '"';
			}
			str += '>';
			str += '<span class="input-group-addon">m</span>';
			str += '</span>';
			str += '</div>';
			break;
		case "ConOverDegree":
			str += '<div class="GitBuilder-QAList-DetailOption">';
			str += '<span class="input-group">';
			str += '<input type="number" class="form-control GitBuilder-QAList-Value-Text" min="0" placeholder="Degree" id="' + degree + '"';
			if (gitbuilder.variable.validationObj.layers[gitbuilder.variable.validationLayer.after].qaOption.ConOverDegree !== undefined) {
				str += 'value="' + gitbuilder.variable.validationObj.layers[gitbuilder.variable.validationLayer.after].qaOption.ConOverDegree + '"';
			}
			str += '>';
			str += '<span class="input-group-addon">Degree</span>';
			str += '</span>';
			str += '</div>';
			break;
		case "SmallArea":
			str += '<div class="GitBuilder-QAList-DetailOption">';
			str += '<span class="input-group">';
			str += '<input type="number" class="form-control GitBuilder-QAList-Value-Text" min="0" placeholder="Area" id="' + area + '" ';
			if (gitbuilder.variable.validationObj.layers[gitbuilder.variable.validationLayer.after].qaOption.SmallArea !== undefined) {
				str += 'value="' + gitbuilder.variable.validationObj.layers[gitbuilder.variable.validationLayer.after].qaOption.SmallArea + '"';
			}
			str += '>';
			str += '<span class="input-group-addon">m<sup>2</sup></span>';
			str += '</span>';
			str += '</div>';
			break;
		case "OutBoundary":
			str += gitbuilder.ui
					.OutputVectorLayersWithCheckBox(gitbuilder.variable.validationObj.layers[gitbuilder.variable.validationLayer.after].qaOption.OutBoundary);
			break;
		case "zValueAmbiguous":
			var layer = gitbuilder.method.layer.getLayerById(gitbuilder.variable.validationLayer.after);
			str += gitbuilder.ui.OutputAttributeWithCheckBox(layer,
					gitbuilder.variable.validationObj.layers[gitbuilder.variable.validationLayer.after].qaOption.zValueAmbiguous);
			break;
		default:
			str += '-';
			break;
		}

		return str;
	}
	// ==========================================================================================================
	/**
	 * @description 속성 입력창 출력
	 * @param {ol.Feature}
	 *            피처
	 * @param {Object}
	 *            피처 속성
	 */
	gitbuilder.ui.OutputAttributeWindow = function OutputAttributeWindow(feature, attr) {
		if (!gitbuilder.variable.elementid.attributeWindow) {
			var attrWindow = "attributeWindow";
			var count = 0;
			while (gitbuilder.method.isDuplicatedId(attrWindow)) {
				attrWindow += count;
			}
			gitbuilder.variable.elementid.attributeWindow = attrWindow;

			var attrTable = "attributeTable";
			var count = 0;
			while (gitbuilder.method.isDuplicatedId(attrTable)) {
				attrTable += count;
			}
			gitbuilder.variable.elementid.attributeTable = attrTable;

			var modal = "";
			modal += '<div id="' + attrWindow + '" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="my" aria-hidden="true">';
			modal += '<div class="modal-dialog">';
			modal += '<div class="modal-content">';
			modal += '<div class="modal-body" id="' + attrTable + '">';

			var key = Object.keys(attr);
			for (var i = 0; i < key.length; i++) {
				modal += '<div class="row GitBuilder-Edit-Attribute-Row">';
				modal += '<div class="col-md-3">';
				modal += '<p class="text-right GitBuilder-Edit-Attribute-Key">';
				modal += key[i];
				modal += '</p>';
				modal += '</div>';

				modal += '<div class="col-md-9">';
				modal += '<input type="text" class="form-control GitBuilder-Edit-Attribute-Value" value="">';
				modal += '</div>';
				modal += '</div>';
			}

			modal += '</div>';
			modal += '<div class="modal-footer">';
			modal += '<button type="button" class="btn btn-default GitBuilder-Edit-Attribute-Cancel-Button" data-dismiss="modal">Cancel</button>';
			modal += '<button type="button" class="btn btn-primary GitBuilder-Edit-Attribute-Save-Button" data-dismiss="modal">OK</button>';
			modal += ' </div>';
			modal += '</div>';
			modal += '</div>';
			modal += '</div>';

			$("body").append(modal);

			$(document).on("click", ".GitBuilder-Edit-Attribute-Save-Button", function() {
				gitbuilder.method.edit.InsertAttributeIntoFeature(feature);
			});

			$(document).on("click", ".GitBuilder-Edit-Attribute-Cancel-Button", function() {
				gitbuilder.method.edit.NoAttributeAndDeleteFeature(feature);
			});

		} else {
			$("#" + gitbuilder.variable.elementid.attributeTable).empty();

			var key = Object.keys(attr);
			modal = '';
			for (var i = 0; i < key.length; i++) {
				modal += '<div class="row GitBuilder-Edit-Attribute-Row">';
				modal += '<div class="col-md-2">';
				modal += '<p class="text-right GitBuilder-Edit-Attribute-Key">';
				modal += key[i];
				modal += '</p>';
				modal += '</div>';

				modal += '<div class="col-md-10">';
				modal += '<input type="text" class="form-control GitBuilder-Edit-Attribute-Value" value="">';
				modal += '</div>';
				modal += '</div>';
			}
			$("#" + gitbuilder.variable.elementid.attributeTable).append(modal);

			$(document).off("click", ".GitBuilder-Edit-Attribute-Save-Button");

			$(document).off("click", ".GitBuilder-Edit-Attribute-Cancel-Button");

			$(document).on("click", ".GitBuilder-Edit-Attribute-Save-Button", function() {
				gitbuilder.method.edit.InsertAttributeIntoFeature(feature);
			});

			$(document).on("click", ".GitBuilder-Edit-Attribute-Cancel-Button", function() {
				gitbuilder.method.edit.NoAttributeAndDeleteFeature(feature);
			});
		}
		$('#' + gitbuilder.variable.elementid.attributeWindow).modal({
			backdrop : "static",
			keyboard : false
		});
		$('#' + gitbuilder.variable.elementid.attributeWindow).modal('show');
	}
	// ==========================================================================================================
	/**
	 * @description 공간 연산창 출력
	 */
	gitbuilder.ui.OutputOperationWindow = function OutputOperationWindow() {
		if (!gitbuilder.variable.elementid.operationWindow) {
			var operWindow = "operationWindow";
			var count = 0;
			while (gitbuilder.method.isDuplicatedId(operWindow)) {
				operWindow += count;
			}
			gitbuilder.variable.elementid.operationWindow = operWindow;

			var operator = "operator";
			count = 0;
			while (gitbuilder.method.isDuplicatedId(operator)) {
				operator += count;
			}
			gitbuilder.variable.elementid.operator = operator;

			var operanda = "operandA";
			count = 0;
			while (gitbuilder.method.isDuplicatedId(operanda)) {
				operanda += count;
			}
			gitbuilder.variable.elementid.operandA = operanda;

			var operandb = "operandB";
			count = 0;
			while (gitbuilder.method.isDuplicatedId(operandb)) {
				operandb += count;
			}
			gitbuilder.variable.elementid.operandB = operandb;

			var modal = "";
			modal += '<div id="' + operWindow + '" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="my" aria-hidden="true">';
			modal += '<div class="modal-dialog">';
			modal += '<div class="modal-content">';
			modal += '<div class="modal-body">';

			modal += '<div>';
			modal += '<select class="form-control" id="' + gitbuilder.variable.elementid.operator + '">';
			modal += '<option value="LayerUnion">Union</option>';
			modal += '<option value="LayerIntersection">Intersect</option>';
			modal += '<option value="LayerDifference">Difference</option>';
			modal += '</select>';
			modal += '</div>';

			modal += '<div class="row">';

			modal += '<div class="col-md-6">';
			modal += '<div style="margin:6px;">Operand A</div>';
			modal += '<div id="' + gitbuilder.variable.elementid.operandA + '">';
			modal += gitbuilder.ui.OutputVectorLayersWithRadio("GitBuilder-Operation-Radio1");
			modal += '</div>';
			modal += '</div>';

			modal += '<div class="col-md-6">';
			modal += '<div style="margin:6px;">Operand B</div>';
			modal += '<div id="' + gitbuilder.variable.elementid.operandB + '">';
			modal += gitbuilder.ui.OutputVectorLayersWithRadio("GitBuilder-Operation-Radio2");
			modal += '</div>';
			modal += '</div>';

			modal += '</div>';

			modal += '</div>';
			modal += '<div class="modal-footer">';
			modal += '<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>';
			modal += '<button type="button" class="btn btn-primary GitBuilder-Calculation-Button" data-dismiss="modal">Calculate</button>';
			modal += ' </div>';
			modal += '</div>';
			modal += '</div>';
			modal += '</div>';

			$("body").append(modal);

			$(document).on("click", ".GitBuilder-Calculation-Button", function() {
				gitbuilder.method.operation.RequestOperation();
			});

		} else {

			$("#" + gitbuilder.variable.elementid.operandA).empty();
			$("#" + gitbuilder.variable.elementid.operandA).append(gitbuilder.ui.OutputVectorLayersWithRadio("GitBuilder-Operation-Radio1"));

			$("#" + gitbuilder.variable.elementid.operandB).empty();
			$("#" + gitbuilder.variable.elementid.operandB).append(gitbuilder.ui.OutputVectorLayersWithRadio("GitBuilder-Operation-Radio2"));

		}

		$('#' + gitbuilder.variable.elementid.operationWindow).modal('show');
	}
	// ==========================================================================================================
	/**
	 * @description 에러 피처 이름 및 선택
	 * @param {ol.layer.Vector ||
	 *            ol.layer.Image} 피처를 조회할 에러 레이어 (벡터)
	 * @returns {String} 피처 리스트 태그형태
	 */
	gitbuilder.ui.OutputErrorFeatureFromStart = function OutputErrorFeatureFromStart(layer, idx) {

		if (!((layer.get("cat") === 2) && (layer instanceof ol.layer.Vector || (layer instanceof ol.layer.Image && layer.getSource() instanceof ol.source.ImageVector)))) {
			console.error("no error layer");
			return;
		}

		var features;
		if (layer instanceof ol.layer.Vector) {
			features = layer.getSource().getFeatures();
		} else if (layer instanceof ol.layer.Image && layer.getSource() instanceof ol.source.ImageVector) {
			features = layer.getSource().getSource().getFeatures();
		}

		if (idx >= features.length || idx < 0) {
			console.error("out of bound");
			return;
		}

		gitbuilder.variable.navigatingFeature = {};
		gitbuilder.variable.navigatingFeature[layer.get("id")] = {
			features : features,
			idx : idx
		};

		var str = "<span>";
		str += "Error-" + (idx + 1);
		str += "</span>";

		var arr = new ol.Collection();
		arr.push(features[idx]);

		gitbuilder.method.edit.SelectFeature(arr);

		var source = new ol.source.Vector({
			features : arr
		});

		gitbuilder.variable.view.fit(source.getExtent(), gitbuilder.variable.map.getSize());

		var prop = features[idx].getProperties();
		var key = Object.keys(prop);
		var tb = "";
		for (var i = 0; i < key.length; i++) {
			if (key[i] === "geometry") {
				continue;
			} else if (key[i] === "errfeatureID") {
				if (layer.get("cat") === 2) {
					tb += '<tr class="GitBuilder-Navigating-Feature">';
					tb += '<td>';
					tb += key[i];
					tb += '</td>';
					tb += '<td  colspan="2">';
					tb += '<a href="#" onclick="gitbuilder.method.edit.LinkToErrorFeature(\'' + prop[key[i]] + '\');" ufid="' + prop[key[i]]
							+ '">Editing ' + prop[key[i]] + '</a>';
					tb += '</td>';
					tb += '</tr>';
					continue;
				}
			}
			tb += '<tr class="GitBuilder-Navigating-Feature">';
			tb += '<td>';
			tb += key[i];
			tb += '</td>';
			tb += '<td  colspan="2">';
			tb += prop[key[i]];
			tb += '</td>';
			tb += '</tr>';
		}

		var tr = $(".GitBuilder-Navigating-Feature");

		for (var i = 0; i < tr.length; i++) {
			if (i === 0) {
				continue;
			} else {
				$(tr[i]).remove();
			}
		}

		$(".GitBuilder-Navigating-Feature").replaceWith(tb);
		$("#" + gitbuilder.variable.elementid.errorFeatureSpan).empty();
		$("#" + gitbuilder.variable.elementid.errorFeatureSpan).append(str);
	}
	// ==========================================================================================================
	/**
	 * @description 에러 피처 이름 및 선택 이벤트 바인딩 다음
	 */
	gitbuilder.ui.OutputErrorFeatureFromStartNextEventBind = function OutputErrorFeatureFromStartEventBind() {

		var layers = gitbuilder.method.layer.getErrorLayer();
		var layer;

		if (layers.getLength() === 1) {
			layer = layers.item(0);
		}

		var errLayer = gitbuilder.variable.navigatingFeature[layer.get("id")];

		gitbuilder.ui.OutputErrorFeatureFromStart(layer, (errLayer.idx + 1));

	}
	// ==========================================================================================================
	/**
	 * @description 에러 피처 이름 및 선택 이벤트 바인딩 이전
	 */
	gitbuilder.ui.OutputErrorFeatureFromStartPreviousEventBind = function OutputErrorFeatureFromStartEventBind() {

		var layers = gitbuilder.method.layer.getErrorLayer();
		var layer;

		if (layers.getLength() === 1) {
			layer = layers.item(0);
		}

		var errLayer = gitbuilder.variable.navigatingFeature[layer.get("id")];

		gitbuilder.ui.OutputErrorFeatureFromStart(layer, (errLayer.idx - 1));

	}
	// ==========================================================================================================
	/**
	 * @description 피처 리스트
	 * @param {ol.layer.Vector ||
	 *            ol.layer.Image} 피처를 조회할 벡터 레이어
	 * @returns {String} 피처 리스트 태그형태
	 */
	gitbuilder.ui.OutputFeatureList = function OutputFeatureList(layer, start, count) {

		var ul = "";
		ul += '<table class="table table-hover table-condensed GitBuilder-Edit-FeatureList">';
		ul += '<thead>';
		ul += '<tr>';
		ul += '<td class="GitBuilder-Edit-FeatureList-Feature-Number">';
		ul += '#';
		ul += '</td>';
		ul += '<td class="GitBuilder-Edit-FeatureList-Feature-UFID">';
		ul += 'UFID';
		ul += '</td>';
		ul += '</tr>';
		ul += '</thead>';
		ul += '<tbody>';
		var features;
		if (layer instanceof ol.layer.Vector) {
			features = layer.getSource().getFeatures();
		} else if (layer instanceof ol.layer.Image && layer.getSource() instanceof ol.source.ImageVector) {
			features = layer.getSource().getSource().getFeatures();
		}

		var lastCount = 0;

		if (features.length < count) {
			lastCount = features.length;
		} else {
			lastCount = count;
		}

		for (var i = start; i < lastCount; i++) {
			ul += '<tr class="GitBuilder-Edit-FeatureList-Feature">';
			ul += '<td class="GitBuilder-Edit-FeatureList-Feature-Number">';
			ul += (i + 1);
			ul += '</td>';
			ul += '<td class="GitBuilder-Edit-FeatureList-Feature-UFID" style="text-overflow:ellipsis; overflow:hidden;" data-toggle="tooltip" data-placement="left" title="';
			ul += features[i].getId();
			ul += '">';
			ul += features[i].getId();
			ul += '</td>';
			ul += '</tr>';
		}
		ul += '</tbody>';
		ul += '</table>';
		return ul;
	}
	// ==========================================================================================================
	/**
	 * @description 피처 속성
	 * @param {ol.Feature}
	 *            속성을 조회할 피처
	 * @returns {String} 피처 속성 테이블 태그형태
	 */
	gitbuilder.ui.OutputAttributeTable = function OutputAttributeTable(feature) {

		var tb = "";
		tb += '<div class="GitBuilder-Edit-AttributePopup">';
		tb += '<button type="button" data-toggle="clickover" class="close GitBuilder-Edit-Attribute-Close" onclick="gitbuilder.ui.CloseAttributeTable();" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
		tb += '<table class="table table-hover table-condensed GitBuilder-Feature-Table">';
		tb += '<tbody>';
		var prop = feature.getProperties();
		var key = Object.keys(prop);
		for (var i = 0; i < key.length; i++) {
			if (key[i] === "geometry") {
				continue;
			} else if (key[i] === "errfeatureID") {
				var layers = gitbuilder.variable.selectedLayers;
				var layer;
				if (layers.getLength() === 1) {
					layer = layers.item(0);
				}
				if (layer.get("cat") === 2) {
					tb += '<tr>';
					tb += '<td>';
					tb += key[i];
					tb += '</td>';
					tb += '<td>';
					tb += '<button class="btn btn-link GitBuilder-Edit-ErrorFeature-Link" onclick="gitbuilder.method.edit.LinkToErrorFeature(\''
							+ prop[key[i]] + '\');" ufid="' + prop[key[i]] + '">Move to ' + prop[key[i]] + '</button>';
					tb += '</td>';
					tb += '</tr>';
					continue;
				}
			}
			tb += '<tr>';
			tb += '<td>';
			tb += key[i];
			tb += '</td>';
			tb += '<td>';
			tb += prop[key[i]];
			tb += '</td>';
			tb += '</tr>';
		}
		tb += '</tbody>';
		tb += '</table>';
		tb += '</div>';

		return tb;
	}
	// ==========================================================================================================
	/**
	 * @description 피처 속성창 닫기
	 */
	gitbuilder.ui.CloseAttributeTable = function CloseAttributeTable() {
		$(".GitBuilder-Edit-AttributePopup").hide();
		console.log("attribute table close");
	}
	// ==========================================================================================================
	/**
	 * @description 에러 리포트 출력
	 */
	gitbuilder.ui.OutputErrorReportWindow = function OutputErrorReportWindow() {
		if (!gitbuilder.variable.elementid.reportWindow) {
			var rptWindow = "errorReportWindow";
			var count = 0;
			while (gitbuilder.method.isDuplicatedId(rptWindow)) {
				rptWindow += count;
			}
			gitbuilder.variable.elementid.reportWindow = rptWindow;

			var jqTable = "jqErrorTable";
			var count = 0;
			while (gitbuilder.method.isDuplicatedId(jqTable)) {
				jqTable += count;
			}
			gitbuilder.variable.elementid.reportTable = jqTable;

			var errReport = gitbuilder.variable.errReport;
			var modal = "";
			modal += '<div id="' + rptWindow
					+ '" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="my" aria-hidden="true">';
			modal += '<div class="modal-dialog modal-lg">';
			modal += '<div class="modal-content">';
			modal += '<div class="modal-body">';

			modal += '<table id="' + gitbuilder.variable.elementid.reportTable + '">';
			modal += '</table>';

			modal += '</div>';
			modal += '<div class="modal-footer">';
			modal += '<button type="button" class="btn btn-primary" data-dismiss="modal">OK</button>';
			modal += ' </div>';
			modal += '</div>';
			modal += '</div>';
			modal += '</div>';

			$("body").append(modal);

		}

		// $("#" +
		// gitbuilder.variable.elementid.reportTable).jqGrid("clearGridData");

		$("#" + gitbuilder.variable.elementid.reportTable).jqGrid({
			caption : "Error Report",
			rownumbers : true,
			width : "865px",
			colNames : [ 'Error Type', 'Error Name', 'Layer ID', 'Feature ID', 'Error Coordinate X', 'Error Coordinate Y' ],
			colModel : [ {
				name : "errType",
				width : 150
			}, {
				name : "errName",
				width : 130
			}, {
				name : "layerID",
				width : 135
			}, {
				name : "featureID",
				width : 150
			}, {
				name : "errCoorX",
				width : 150
			}, {
				name : "errCoorY",
				width : 150
			} ],
			data : gitbuilder.variable.errReport
		});

		$('#' + gitbuilder.variable.elementid.reportWindow).modal('show');
	}
	// ==========================================================================================================
	/**
	 * @description 좌표계 변환 다이얼로그
	 */
	gitbuilder.ui.ConvertCoordinates = function ConvertCoordinates() {
		if (!gitbuilder.variable.elementid.crsWindow) {
			var crsWindow = "crsWindow";
			var count = 0;
			while (gitbuilder.method.isDuplicatedId(crsWindow)) {
				crsWindow += count;
			}
			gitbuilder.variable.elementid.crsWindow = crsWindow;

			var layerlist = "crsLayerList";
			var count = 0;
			while (gitbuilder.method.isDuplicatedId(layerlist)) {
				layerlist += count;
			}
			gitbuilder.variable.elementid.crsLayerList = layerlist;

			var modal = "";
			modal += '<div id="' + gitbuilder.variable.elementid.crsWindow
					+ '" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="my" aria-hidden="true">';
			modal += '<div class="modal-dialog">';
			modal += '<div class="modal-content">';

			modal += '<div class="modal-header">';
			modal += '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
			modal += '<h4 class="modal-title">Aligning CRS</h4>';
			modal += '</div>';

			modal += '<div class="modal-body">';

			modal += '<div>Layer</div>';
			modal += '<div id="' + gitbuilder.variable.elementid.crsLayerList + '">';
			modal += gitbuilder.ui.OutputAllVectorLayersSelect();
			modal += '</div>';

			modal += '<div>Original EPSG Code(to EPSG:3857)</div>';
			modal += '<div class="input-group">';
			modal += '<span class="input-group-addon">EPSG:</span>';
			modal += '<input type="number" class="form-control GitBuilder-CRS-FromCRSForm" placeholder="ex) 3857">';
			modal += '</div>';

			modal += '</div>';
			modal += '<div class="modal-footer">';
			modal += '<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>';
			modal += '<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="gitbuilder.method.coordinate.ConvertCoordinatesVectorSourceEvent()">OK</button>';
			modal += '</div>';
			modal += '</div>';
			modal += '</div>';
			modal += '</div>';

			$("body").append(modal);

		}
		$("#" + gitbuilder.variable.elementid.crsLayerList).empty();
		$("#" + gitbuilder.variable.elementid.crsLayerList).append(gitbuilder.ui.OutputAllVectorLayersSelect());
		$('#' + gitbuilder.variable.elementid.crsWindow).modal('show');
	}

}