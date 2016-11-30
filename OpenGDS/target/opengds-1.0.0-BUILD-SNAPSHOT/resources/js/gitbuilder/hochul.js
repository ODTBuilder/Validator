if (!gitbuilder.method.edit.RotateFeature) {
	
	/**
	 * @description 편집 기능 활성화
	 * 김호철
	 */
	gitbuilder.method.edit.EditToolTip = function EditToolTip() {
		if (gitbuilder.variable.intrctn.select === undefined || gitbuilder.variable.intrctn.select === null) {
			gitbuilder.method.edit.RemoveAllBuilderInteraction();
		} else if (gitbuilder.variable.intrctn.select instanceof ol.interaction.Select) {
			gitbuilder.variable.map.removeInteraction(gitbuilder.variable.intrctn.draw);
			gitbuilder.variable.map.removeInteraction(gitbuilder.variable.intrctn.dragBox);
			gitbuilder.variable.map.removeInteraction(gitbuilder.variable.intrctn.modify);
			gitbuilder.variable.map.removeInteraction(gitbuilder.variable.intrctn.snap);
		}
		gitbuilder.ui.CloseAttributeTable();

		var layers = gitbuilder.variable.selectedLayers;
		if (layers.getLength() !== 1) {
			console.error("Selected layer must be one, check [gitbuilder.variable.selectedLayers]");
			return;
		}
		
		if (gitbuilder.variable.intrctn.select === undefined || gitbuilder.variable.intrctn.select === null) {
			gitbuilder.method.edit.SelectFeature();
			return;
		}
		
		if (gitbuilder.variable.selectedFeatures.getLength() === 1) {
			
			var olFeatures = gitbuilder.variable.selectedFeatures.getArray();
			
			// 'EditToolTip'함수를 다시 호출하였을때 현재 선택된 피쳐가 전에 선택한 
			// 피쳐와 같은 피쳐이고 수정되지 않았다면 아래의 구문을 수행하지 않는다.
			if(gitbuilder.variable.selectedGeometry !== olFeatures[0].getGeometry() || 
					gitbuilder.variable.intrctn.translate !== undefined) {
				
				gitbuilder.variable.map.removeInteraction(gitbuilder.variable.intrctn.translate);
				gitbuilder.variable.intrctn.translate = undefined;
				
				gitbuilder.variable.selectedFeature = olFeatures[0];
				gitbuilder.variable.selectedGeometry = olFeatures[0].getGeometry();
				
				gitbuilder.method.edit.SetFlatPoint();
			}
			gitbuilder.ui.RemoveEditToolTip();
			gitbuilder.ui.DrawEditToolTip();
			gitbuilder.variable.intrctn.select.set('rotate', false);
			gitbuilder.variable.intrctn.select.set('scale', false);
			gitbuilder.variable.map.render();
		} else {
			console.error('Selected Feature must be one, check [gitbuilder.variable.selectedLayers]');
		}
	}
	
	/**
	 * @description 피처의 중심점(중점) 설정. 전역변수 'gitbuilder.variable.flatInteriorPoint'의 값을 
	 * 현재 선택된 피처의 중점으로 설정한다.
	 * 전역변수 'gitbuilder.variable.selectedGeometry'의 값이 존재할때만 사용할 수 있다.
	*/
	gitbuilder.method.edit.SetFlatPoint = function SetFlatPoint() {
		// 'gitbuilder.variable.selectedGeometry'가 빈 객체라면 시행하지 않음
		if (!($.isEmptyObject(gitbuilder.variable.selectedGeometry))) {
			if(gitbuilder.variable.selectedGeometry instanceof ol.geom.MultiLineString || 
					gitbuilder.variable.selectedGeometry instanceof ol.geom.LineString || 
					gitbuilder.variable.selectedGeometry instanceof ol.geom.Polygon) {
				var extent = gitbuilder.variable.selectedGeometry.getExtent();
				var X = extent[0] + (extent[2]-extent[0])/2;
				var Y = extent[1] + (extent[3]-extent[1])/2;
				gitbuilder.variable.flatInteriorPoint = [X, Y];
			} else if(gitbuilder.variable.selectedGeometry instanceof ol.geom.MultiPolygon) {
				gitbuilder.variable.flatInteriorPoint = gitbuilder.variable.selectedGeometry.getFlatInteriorPoints();
			} else {
				console.error('can not rotate this Feature!');
				return;
			}
		}
	}
	
	/**
	 * @description Feature를 Flip. Flip버튼을 클릭한 방향으로 feature의 좌표값을 재설정
	 * @param {String} flip을 실행할 방향
	 */
	gitbuilder.method.edit.FlipFeature = function FlipFeature(direction) {
		
		this.direction = direction;
		
		if (gitbuilder.variable.selectedFeatures.getLength() !== 0) {			
			if (gitbuilder.variable.selectedGeometry instanceof ol.geom.MultiPoint || 
					gitbuilder.variable.selectedGeometry instanceof ol.geom.Point) {
				console.error('cannot flip this Feature!');
				return;
			} else {
				gitbuilder.command.SetUndoData('edit', null, gitbuilder.variable.selectedFeature);
				gitbuilder.method.FlipAlgorithm(this.direction);
				gitbuilder.ui.RefreshEditToolTip();
				gitbuilder.variable.map.render();
			}
		}
	}
	
	gitbuilder.method.getContextPath = function getContextPath() {
		var offset = location.href.indexOf(location.host) + location.host.length;
		var ctxPath = location.href.substring(offset, location.href.indexOf('/',
				offset + 1));
		return ctxPath;
	}
	
	
	
	
	//========================================================================================================
	//================================================ Event =================================================

	gitbuilder.method.edit.RotateMouseDown = function RotateMouseDown() {
		gitbuilder.command.SetUndoData('edit', null, gitbuilder.variable.selectedFeature);
		
		// 4번(rotate) overlay를 제외한 나머지 EditToolTip을 삭제
		gitbuilder.ui.RemoveEditToolTip(4);
		
		// 'pointermove' 활성화
		gitbuilder.variable.intrctn.select.set('rotate', true);
		
		// mbr 숨김
		gitbuilder.variable.map.render();
	}
	
	gitbuilder.method.edit.RotateMouseUp = function RotateMouseUp() {
		gitbuilder.variable.intrctn.select.set('rotate', false);
		gitbuilder.variable.map.render();
		// crsrPosition 초기화
		gitbuilder.variable.crsrPosition = [];
		gitbuilder.variable.map.removeOverlay(gitbuilder.variable.map.getOverlayById('rotationing'));
		gitbuilder.variable.map.removeOverlay(gitbuilder.variable.map.getOverlayById('editTip4'));
		gitbuilder.ui.DrawEditToolTip();
		
	}
	
	gitbuilder.method.edit.ScaleMouseDown = function ScaleMouseDown(position) {
		this.position = position;
		gitbuilder.command.SetUndoData('edit', null, gitbuilder.variable.selectedFeature);
		
		switch (this.position) {
		case 'rd':
			gitbuilder.ui.RemoveEditToolTip(5);
			break;
		case 'ru':
			gitbuilder.ui.RemoveEditToolTip(6);
			break;
		case 'ld':
			gitbuilder.ui.RemoveEditToolTip(7);
			break;
		case 'lu':
			gitbuilder.ui.RemoveEditToolTip(8);
			break;
		}
		
		gitbuilder.variable.intrctn.select.set('scale', true);
		gitbuilder.variable.map.render();
	}
	
	gitbuilder.method.edit.ScaleMouseUp = function ScaleMouseUp(position) {
		gitbuilder.variable.intrctn.select.set('scale', false);
		gitbuilder.variable.map.render();
		switch (this.position) {
		case 'rd':
			gitbuilder.variable.map.removeOverlay(gitbuilder.variable.map.getOverlayById('editTip5'));
			break;
		case 'ru':
			gitbuilder.variable.map.removeOverlay(gitbuilder.variable.map.getOverlayById('editTip6'));
			break;
		case 'ld':
			gitbuilder.variable.map.removeOverlay(gitbuilder.variable.map.getOverlayById('editTip7'));
			break;
		case 'lu':
			gitbuilder.variable.map.removeOverlay(gitbuilder.variable.map.getOverlayById('editTip8'));
			break;
		}
		gitbuilder.method.edit.SetFlatPoint();
		gitbuilder.ui.DrawEditToolTip();
		gitbuilder.variable.crsrPosition = [];
	}
	
	//========================================================================================================
	//=================================================== Algorithm ==========================================
	
	/**
	 * @description 피처 회전 알고리즘. 'pointermove'이벤트 발생전의 커서 위치값과 이벤트 
	 * 발생시의 커서값을 인자로 받아 선택된 피쳐의 중점을 기준으로 두 점 사이의 각도를 반환
	 * @param {Array} 'pointermove'이벤트 발생전의 커서 위치값
	 * @param {Array} 'pointermove'이벤트 발생시의 커서 위치값
	 * @return {Float} 사이각을 radian 형식으로 반환
	 */
	gitbuilder.method.RotateAlgorithm = function RotateAlgorithm(prevCursorPosition, currentCursorPosition) {
		var result = 0;
		var extent = gitbuilder.variable.selectedGeometry.getExtent();
		var pivot = gitbuilder.variable.flatInteriorPoint;
		var prev = prevCursorPosition;
		if (prev.length === 0) {
			prev = [(extent[2]+extent[0])/2, extent[3]];
		}
		var current = currentCursorPosition;
		
		// 현재 feature의 중점 좌표에 Rotate Icon을 나타낸다.
		var rotationing = "<span class='rotationing glyphicon glyphicon-refresh' aria-hidden='false'></span>";
		var element = $(rotationing).get(0);
		if (gitbuilder.variable.map.getOverlayById('rotationing') === null) {
			var olOver = new ol.Overlay({
				id: 'rotationing',
				element: element
			});
			olOver.setElement(element);
			gitbuilder.variable.map.addOverlay(olOver);
			
			olOver.setPosition(gitbuilder.variable.flatInteriorPoint);
			olOver.setPositioning("center-center");
		}
		var currentRadi = Math.atan2(current[1]-pivot[1], current[0]-pivot[0]);
		var prevRadi = Math.atan2(prev[1]-pivot[1], prev[0]-pivot[0]);

		if (currentRadi > 0 && prevRadi < 0) {
			result = currentRadi - Math.abs(prevRadi);
		} else if (currentRadi < 0 && prevRadi > 0) {
			result = Math.abs(currentRadi) - prevRadi;
		} else {
			result = currentRadi - prevRadi;
		}
		return result;
	}
	
	/**
	 * @description 피처 플립 알고리즘. 선택한 방향으로 feature를 Flip
	 * @param {String} Flip을 할 방향
	 */
	gitbuilder.method.FlipAlgorithm = function FlipAlgorithm(direction) {
		this.direction = direction;
		var extentIndex = null;
		var extent = gitbuilder.variable.selectedGeometry.getExtent();
		var coordi = gitbuilder.variable.selectedGeometry.getFlatCoordinates();
		var newCoordi = [];
		var newGeometry = null;
		
		if (this.direction === "up") {
			extentIndex = 3;
		} else if (this.direction === "down") {
			extentIndex = 1;
		} else if (this.direction === "right") {
			extentIndex = 2;
		} else if (this.direction === "left") {
			extentIndex = 0;
		} else {
			console.error('direction error');
			return;
		}
		
		for (var i=0; i<coordi.length/2; i++) {
			if(extentIndex === 1 || extentIndex === 3) {
				if(coordi[2*i+1] !== extent[extentIndex]) {
					newCoordi.push([coordi[2*i], 2*extent[extentIndex] - coordi[2*i+1]]);
				} else {
					newCoordi.push([coordi[2*i], coordi[2*i+1]]);
				}
			} else {
				if(coordi[2*i] !== extent[extentIndex]) {
					newCoordi.push([2*extent[extentIndex] - coordi[2*i], coordi[2*i+1]]);
				} else {
					newCoordi.push([coordi[2*i], coordi[2*i+1]]);
				}
			}
		}
		
		if (gitbuilder.variable.selectedGeometry instanceof ol.geom.MultiPolygon) {
			newGeometry = new ol.geom.MultiPolygon([[newCoordi]]);
		} else if (gitbuilder.variable.selectedGeometry instanceof ol.geom.Polygon) {
			newGeometry = new ol.geom.Polygon([newCoordi]);
		} else if (gitbuilder.variable.selectedGeometry instanceof ol.geom.MultiLineString) {
			newGeometry = new ol.geom.MultiLineString([newCoordi]);
		} else if (gitbuilder.variable.selectedGeometry instanceof ol.geom.LineString) {
			newGeometry = new ol.geom.LineString(newCoordi);
		} else {
			console.error("feature type error");
			return;
		}
		gitbuilder.variable.selectedFeature.setGeometry(newGeometry);
		gitbuilder.variable.selectedGeometry = gitbuilder.variable.selectedFeature.getGeometry();
		gitbuilder.method.edit.SetFlatPoint();
	}
	
	/**
	 * @description 피처 확대, 축소 알고리즘. Scale버튼 마우스다운시의 좌표값과 마우스업시의 
	 * 좌표값을 인자로 받아 feature가 늘어난 배율을 계산하여 반환한다.
	 * @param {Array} 마우스다운시의 [extent[2],extent[3]]
	 * @param {Array} 마우스업시의 커서 위치
	 * @return {Array} feature가 scale된 x좌표, y좌표 비율의 절대값
	 */
	gitbuilder.method.ScaleAlgorithm = function ScaleAlgorithm(previousPoint, currentCursorPoint) {
		var result = [];
		var extent = previousPoint;
		var cursor = currentCursorPoint;
		var center = gitbuilder.variable.flatInteriorPoint;
		
		var cursorPixel = gitbuilder.variable.map.getPixelFromCoordinate(currentCursorPoint);
		var centerPixel = gitbuilder.variable.map.getPixelFromCoordinate(center);
		var subX = Math.abs(cursorPixel[0] - centerPixel[0]);
		var subY = Math.abs(cursorPixel[1] - centerPixel[1]);
		var magniX = 0;
		var magniY = 0;
		
		if (subX < 2 || subY < 2) {
			magniX = 1;
			magniY = 1;
		} else {
			magniX = (cursor[0]-center[0])/(extent[0]-center[0]);
			magniY = (cursor[1]-center[1])/(extent[1]-center[1]);
		}
		
		result.push(Math.abs(magniX), Math.abs(magniY));
		return result;
	}
	
	
	//========================================================================================================
	//=================================================== UI =================================================
	
	gitbuilder.ui.CursorPositionInfo = function CursorPositionInfo () {
		if (gitbuilder.variable.map.getOverlayById('hodraw') === null) {
			var hodraw = '<p id="hodraw" class="lead"><span class="label label-info">x</span><span class="label label-info">y</span></p>';
			
			var element = $(hodraw).get(0);
			var olOver = new ol.Overlay({
				id: 'hodraw',
				element: element,
				offset: [10, 10]
			});
			olOver.setElement(element);
			gitbuilder.variable.map.addOverlay(olOver);
			
			return;
		} else {
			return;
		}
	};
	
	gitbuilder.ui.SetCursorPositionInfo = function SetCursorPositionInfo (coordinate) {
		var olOver = gitbuilder.variable.map.getOverlayById('hodraw');
		var info = ['x: ', 'y: '];
		$('#hodraw').find('span').each(function (index, element) {
			$(this).text(info[index]+coordinate[index]);
		});
		olOver.setPosition(coordinate);
	}
	
	/**
	 * @description Icon 위치 설정. Icon의 좌표값을 설정한다.
	 * @param {String} element의 title 속성값
	 * @param {Array} 현재 커서의 위치
	 */
	gitbuilder.ui.SetIconPosition = function SetIconPosition(title, coordinate) {
		switch (title) {
		case 'Rotation':
			var olOver = gitbuilder.variable.map.getOverlayById('editTip4');
			olOver.setPosition(coordinate);
			break;
		case 'ScaleRightDown':
			var olOver = gitbuilder.variable.map.getOverlayById('editTip5');
			olOver.setPosition(coordinate);
			break;
		case 'ScaleRightUp':
			var olOver = gitbuilder.variable.map.getOverlayById('editTip6');
			olOver.setPosition(coordinate);
			break;
		case 'ScaleLeftDown':
			var olOver = gitbuilder.variable.map.getOverlayById('editTip7');
			olOver.setPosition(coordinate);
			break;
		case 'ScaleLeftUp':
			var olOver = gitbuilder.variable.map.getOverlayById('editTip8');
			olOver.setPosition(coordinate);
			break;
		}
		return;
	}
	
	/**
	 * @description EditToolTip 위치 재설정. 
	 * feature의 좌표가 변경되었을때 EditToolTip overlay들의 좌표값을 다시 설정
	 */
	gitbuilder.ui.RefreshEditToolTip = function RefreshEditToolTip() {
		
		var olOver = null;
		var extent = gitbuilder.variable.selectedGeometry.getExtent();
		var rotatePositionA = 0;
		var rotatePositionB = 0;
		
		for (var i=0; i<9; ++i) {
			olOver = gitbuilder.variable.map.getOverlayById('editTip'+i);
			
			switch (i) {
			case 0:
				olOver.setPosition([extent[i], (extent[1]+extent[3])/2]);
				break;
			case 1:
				olOver.setPosition([(extent[0]+extent[2])/2, extent[i]]);
				break;
			case 2:
				olOver.setPosition([extent[i], (extent[1]+extent[3])/2]);
				break;
			case 3:
				olOver.setPosition([(extent[0]+extent[2])/2, extent[i]]);
				break;
			case 4:
				rotatePositionA = (extent[2]-extent[0])/3;
				rotatePositionB = (extent[3]-extent[1])/3;
				if (rotatePositionA > rotatePositionB) {
					olOver.setPosition([(extent[0]+extent[2])/2, extent[3]+rotatePositionB]);
				} else {
					olOver.setPosition([(extent[0]+extent[2])/2, extent[3]+rotatePositionA]);
				}
				break;
			case 5:
				olOver.setPosition([extent[2], extent[1]]);
				break;
			case 6:
				olOver.setPosition([extent[2], extent[3]]);
				break;
			case 7:
				olOver.setPosition([extent[0], extent[1]]);
				break;
			case 8:
				olOver.setPosition([extent[0], extent[3]]);
				break;
			}
		}
	}
	
	/**
	 * @description EditToolTip 생성.
	 * 'flip', 'rotate', 'scale' 기능을 수행할 수 있는 icon들을 overlay객체로 그려낸다.
	 * 전역변수 'gitbuilder.variable.selectedGeometry'의 값이 설정되어 있어야만 사용할 수 있다.
	 */
	gitbuilder.ui.DrawEditToolTip = function DrawEditToolTip() {
		
		var flipLeft = "<span class='hoicon glyphicon glyphicon-menu-left' onclick='gitbuilder.method.edit.FlipFeature("+'"left"'+")' data-toggle='tooltip' title='FlipLeft' aria-hidden='false'></span>";
		var flipRight = "<span class='hoicon glyphicon glyphicon-menu-right' onclick='gitbuilder.method.edit.FlipFeature("+'"right"'+")' data-toggle='tooltip' title='FlipRight' aria-hidden='false'></span>";
		var flipUp = "<span class='hoicon glyphicon glyphicon-menu-up' onclick='gitbuilder.method.edit.FlipFeature("+'"up"'+")' data-toggle='tooltip' title='FlipUp' aria-hidden='false'></span>";
		var flipDown = "<span class='hoicon glyphicon glyphicon-menu-down' onclick='gitbuilder.method.edit.FlipFeature("+'"down"'+")' data-toggle='tooltip' title='FlipDown' aria-hidden='false'></span>";
		var rotate = "<span class='hoicon glyphicon glyphicon-refresh' onmousedown='gitbuilder.method.edit.RotateMouseDown()' onmouseup='gitbuilder.method.edit.RotateMouseUp()' data-toggle='tooltip' title='Rotation' aria-hidden='false'></span>";
		var scaleRightDown = "<span class='hoicon glyphicon glyphicon-unchecked' onmousedown='gitbuilder.method.edit.ScaleMouseDown("+'"rd"'+")' onmouseup='gitbuilder.method.edit.ScaleMouseUp("+'"rd"'+")' data-toggle='tooltip' title='ScaleRightDown' aria-hidden='false'></span>";
		var scaleRightUp = "<span class='hoicon glyphicon glyphicon-unchecked' onmousedown='gitbuilder.method.edit.ScaleMouseDown("+'"ru"'+")' onmouseup='gitbuilder.method.edit.ScaleMouseUp("+'"ru"'+")' data-toggle='tooltip' title='ScaleRightUp' aria-hidden='false'></span>";
		var scaleLeftDown = "<span class='hoicon glyphicon glyphicon-unchecked' onmousedown='gitbuilder.method.edit.ScaleMouseDown("+'"ld"'+")' onmouseup='gitbuilder.method.edit.ScaleMouseUp("+'"ld"'+")' data-toggle='tooltip' title='ScaleLeftDown' aria-hidden='false'></span>";
		var scaleLeftUp = "<span class='hoicon glyphicon glyphicon-unchecked' onmousedown='gitbuilder.method.edit.ScaleMouseDown("+'"lu"'+")' onmouseup='gitbuilder.method.edit.ScaleMouseUp("+'"lu"'+")' data-toggle='tooltip' title='ScaleLeftUp' aria-hidden='false'></span>";
		var icons = [flipLeft, flipDown, flipRight, flipUp, rotate, scaleRightDown, scaleRightUp, scaleLeftDown, scaleLeftUp];
		var element = null;
		var extent = gitbuilder.variable.selectedGeometry.getExtent();
		var olOver = null;
		var rotatePositionA = 0;
		var rotatePositionB = 0;
		
		for (var i=0; i<icons.length; ++i) {
			element = $(icons[i]).get(0);
			olOver = new ol.Overlay({
				id: 'editTip' + i,
				element: element
			});
			olOver.setElement(element);
			gitbuilder.variable.map.addOverlay(olOver);
			
			switch (i) {
			case 0:
				olOver.setPosition([extent[i], (extent[1]+extent[3])/2]);
				olOver.setPositioning("center-right");
				break;
			case 1:
				olOver.setPosition([(extent[0]+extent[2])/2, extent[i]]);
				olOver.setPositioning("top-center");
				break;
			case 2:
				olOver.setPosition([extent[i], (extent[1]+extent[3])/2]);
				olOver.setPositioning("center-left");
				break;
			case 3:
				olOver.setPosition([(extent[0]+extent[2])/2, extent[i]]);
				olOver.setPositioning("bottom-center");
				break;
			case 4:
				rotatePositionA = (extent[2]-extent[0])/3;
				rotatePositionB = (extent[3]-extent[1])/3;
				if (rotatePositionA > rotatePositionB) {
					olOver.setPosition([(extent[0]+extent[2])/2, extent[3]+rotatePositionB]);
				} else {
					olOver.setPosition([(extent[0]+extent[2])/2, extent[3]+rotatePositionA]);
				}
				olOver.setPositioning("bottom-center");
				break;
			case 5:
				olOver.setPosition([extent[2], extent[1]]);
				olOver.setPositioning("center-center");
				break;
			case 6:
				olOver.setPosition([extent[2], extent[3]]);
				olOver.setPositioning("center-center");
				break;
			case 7:
				olOver.setPosition([extent[0], extent[1]]);
				olOver.setPositioning("center-center");
				break;
			case 8:
				olOver.setPosition([extent[0], extent[3]]);
				olOver.setPositioning("center-center");
				break;
			}
		}
	}
	
	/**
	 * @description EditToolTip 제거, 삭제. 
	 * skip번호를 제외한 나머지 EditToolTip이 제거된다.
	 * EditToolTip의 각 버튼들은 서로다른 overlay객체 id를 가지고 있다.
	 * 'editTip1', 'editTip2'와 같이 번호만 다른 형식으로 저장되어 있다.
	 * @param {Numver} 지우지 않을 element의 id값 맨 뒤의 번호
	 */
	gitbuilder.ui.RemoveEditToolTip = function RemoveEditToolTip(skip) {
		var bool = true;
		var i = 0;
		while (bool) {
			if (gitbuilder.variable.map.getOverlayById('editTip'+i) !== null) {
				if (skip !== i) {
					gitbuilder.variable.map.removeOverlay(gitbuilder.variable.map.getOverlayById('editTip'+i));
				}
				++i;
			} else {
				bool = false;
			}
		}
		gitbuilder.variable.map.removeOverlay(gitbuilder.variable.map.getOverlayById('hodraw'));
		gitbuilder.variable.map.render();
		return;
	}
	
	gitbuilder.ui.DrawNbr = function DrawNbr(evt) {
		var nbr = null;
		var line = null;
		var point = [];
		var strokes = {
			'line': new ol.style.Stroke({
				color: 'rgba(152,152,152,0.9)',
				width: 3,
				lineDash: [1, 4]
			}),
			'point': new ol.style.Stroke({
				color: 'rgba(214,240,0,0.9)',
				width: 3
			}),
			'square': new ol.style.Stroke({
				color: 'rgba(214,240,0,0.9)',
				width: 2
			})
		};
		
		var styles = {
			'line': new ol.style.Style({
				stroke: strokes['line'],
				image: new ol.style.Circle({
					radius: 10,
					stroke: strokes['line']
				})
			}),
			'point': new ol.style.Style({
				stroke: strokes['point'],
				image: new ol.style.Circle({
					radius: 10,
					stroke: strokes['point']
				})
			}),
			'square': new ol.style.Style({
				image: new ol.style.RegularShape({
					stroke: strokes['square'],
					points: 4,
					radius: 8,
					angle: Math.PI / 4
				})
			})
		};
		
		var extent = gitbuilder.variable.selectedGeometry.getExtent();
		var coorX = (extent[0]+extent[2])/2;
		var rotatePositionA = 0;
		var rotatePositionB = 0;
		
		rotatePositionA = (extent[2]-extent[0])/3;
		rotatePositionB = (extent[3]-extent[1])/3;
		
		if (nbr === null) {
			nbr = new ol.geom.Polygon([[[extent[0],extent[3]], [extent[0],extent[1]], [extent[2],extent[1]],
			                            [extent[2], extent[3]], [extent[0],extent[3]]]]);
		} else {
			nbr.setCoordinates([[[extent[0],extent[3]], [extent[0],extent[1]], [extent[2],extent[1]],
		                            [extent[2], extent[3]], [extent[0],extent[3]]]]);
		}
		if (line === null) {
			if (rotatePositionA > rotatePositionB) {
				line = new ol.geom.LineString([[coorX,extent[3]], [coorX,extent[3]+rotatePositionB]]);
			} else {
				line = new ol.geom.LineString([[coorX,extent[3]], [coorX,extent[3]+rotatePositionA]]);
			}
			
		} else {
			if (rotatePositionA > rotatePositionB) {
				line.setCoordinates([[coorX,extent[3]], [coorX,extent[3]+rotatePositionB]]);
			} else {
				line.setCoordinates([[coorX,extent[3]], [coorX,extent[3]+rotatePositionA]]);
			}
			
		}
		
        var vectorContext = evt.vectorContext;
        vectorContext.setStyle(styles['line']);
        if (nbr !== null && line !== null) {
        	vectorContext.drawGeometry(nbr);
        }
        if (line !== null) {
        	vectorContext.drawGeometry(line);
        }
	}
}

if(!gitbuilder.command) {
	gitbuilder.command = {};
	gitbuilder.command.exe = {};
	gitbuilder.command.ui = {};
	
	//========================================================================================================
	//============================================= Undo Redo ================================================
	
	gitbuilder.command.UndoRedoCommand = function UndoRedoCommand (layer, feature, exe, value) {
		this.layer = layer;
		this.feature = feature;
		this.exe = exe; // undo 또는 redo를 실행할 함수 저장
		this.value = value; // undo 또는 redo 실행에 필요한 값 저장
	}
	
	gitbuilder.command.UndoRedoEdit = function UndoRedoEdit (olFeature, value) {
		return new gitbuilder.command.UndoRedoCommand(null, olFeature, gitbuilder.command.exe.UndoRedoEdit, value);
	};
	
	gitbuilder.command.UndoRedoFeature = function UndoRedoFeature (olLayer, olFeature, value) {
		return new gitbuilder.command.UndoRedoCommand(olLayer, olFeature, gitbuilder.command.exe.UndoRedoFeature, value);
	};
	
	gitbuilder.command.UndoRedoLayer = function UndoRedoLayer (olLayer, value) {
		return new gitbuilder.command.UndoRedoCommand(olLayer, null, gitbuilder.command.exe.UndoRedoLayer, value);
	};
	
	gitbuilder.command.UndoRedo = function UndoRedo () {
		var undos = [];
		var redos = [];
		
		return {
			setUndo: function (undo) {
				if (undos.length === (Math.pow(2,32)-1)) {
					undos.shift();
				}
				undos.push(undo);
			}, 
			setRedo: function (redo) {
				if (redos.length === (Math.pow(2,32)-1)) {
					redos.shift();
				}
				redos.push(redo);
			}, 
			exeUndoFeature: function () {
				var undo = undos.pop();
				undo.exe(undo.feature, undo.value);
			}, 
			exeUndoLayer: function () {
				var undo = undos.pop();
				undo.exe(undo.layer, undo.value);
			},
			exeRedoFeature: function () {
				var redo = redos.pop();
				redo.exe(redo.feature, redo.value);
			}, 
			exeRedoLayer: function () {
				var redo = redos.pop();
				redo.exe(redo.layer, redo.value);
			}, 
			getUndo: function (num) {
				return undos[num];
			},
			getRedo: function (num) {
				return redos[num];
			},
			lengthOfUndo: function () {
				return undos.length;
			}, 
			lengthOfRedo: function () {
				return redos.length;
			}, 
			removeUndo: function (num) {
				undos.splice(num, 1);
			}, 
			resetUndos: function () {
				undos = [];
			},
			resetRedos: function () {
				var removeLayer = function (layer) {
					var layerId = layer.get('id');
					var element = $('li[layerid="'+layerId+'"]');
					if (element.css('display') === 'none') {
						gitbuilder.variable.map.removeLayer(layer);
					}
				}
				
				while (redos.length !== 0) {
					var command = redos.pop();
					if (command.feature !== null) {
						for (var i=0; i<command.feature.length; ++i) {
							if (command.feature[i].getStyle() !== null) {
								if (command.feature[i].getStyle().image_ === '') {
									if (command.layer instanceof ol.layer.Vector) {
										var source = command.layer.getSource();
										source.removeFeature(command.feature[i]);
									} else if (command.layer instanceof ol.layer.Image && 
											command.layer.getSource() instanceof ol.source.ImageVector) {
										var source = command.layer.getSource().getSource();
										source.removeFeature(command.feature[i]);
									} else {
										console.error("The layer is not a vector layer.");
									}
								}
							}
						}
					}
					if (command.layer !== null) {
						if (command.layer instanceof Array) {
							for (var i=0; i<command.layer.length; ++i) {
								removeLayer(command.layer[i]);
							}
						} else {
							removeLayer(command.layer);
						}
					}
				}
				redos = [];
			}
		}
	}
	
	gitbuilder.variable.undoRedo = new gitbuilder.command.UndoRedo();
	
	/**
	 * @description undo에 필요한 값들을 저장한다.
	 * @param {String} 수행할 작업 ex) 'edit', 'featureHide', 'layerHide'
	 * @param {ol.Layer}
	 * @param {ol.Feature}
	 */
	gitbuilder.command.SetUndoData = function SetUndoData (functionName, newLayer, newFeature) {
		gitbuilder.variable.undoRedo.resetRedos();
		
		var layer = newLayer;
		var feature = [];
		var coordinates = [];
		var clone = null;
		var command = null;
		var history = '';
		if (newFeature instanceof Array) {
			for (var i=0; i<newFeature.length; ++i) {
				if (functionName === 'featureCreate') {
					clone = newFeature[i].clone();
					clone.setId(newFeature[i].getId());
					feature.push(clone);
					for (var j=0; j < gitbuilder.variable.undoRedo.lengthOfUndo(); ++j) {
						command = gitbuilder.variable.undoRedo.getUndo(j);
						if (command.feature !== null) {
							for (var temp in command.feature) {
								if (command.feature[temp].getId() === newFeature[i].getId()) {
									gitbuilder.variable.undoRedo.removeUndo(j);
								}
							}
						}
					}
				} else {
					feature.push(newFeature[i]);
				}
			}
		} else {
			feature.push(newFeature);
		}
		
		if (newFeature !== null) {
			for (var i=0; i<feature.length; ++i) {
				coordinates.push(feature[i].getGeometry().getCoordinates());
			}
		}
		
		// Feature, layer 생성 및 수정 전 정보 저장
		if (functionName === 'edit') {
			gitbuilder.variable.undoRedo.setUndo(new gitbuilder.command.UndoRedoEdit(feature, coordinates));
		} else if (functionName === 'featureHide') {
			gitbuilder.variable.undoRedo.setUndo(new gitbuilder.command.UndoRedoFeature(layer, feature, 'hide'));
		} else if (functionName === 'featureCreate') {
			history = 'function:removeFeature/Feature:';
			for (var i in feature) {
				history += feature[i].getId()+',';
			}
			history.slice(0, history.lastIndexOf('.'));
			gitbuilder.method.SetCommandHistory(history);
			for (var j=0; j < feature.length; ++j) {
				for (var i=0; i < gitbuilder.variable.undoRedo.lengthOfUndo; ++i) {
					
				}
			}
			gitbuilder.variable.undoRedo.setUndo(new gitbuilder.command.UndoRedoFeature(layer, feature, 'create'));
		} else if (functionName === 'layerHide') {
			gitbuilder.variable.undoRedo.setUndo(new gitbuilder.command.UndoRedoLayer(layer, 'hide'));
		} else {
			console.error('function name is not a function');
		}
	}
	
	/**
	 * @description 상태 되돌리기. 뒤로가기. 좌표값을 인자로 받아 feature를 이전 상태로 되돌린다
	 * @param {ol.feature} feature
	 * @param {Array} feature의 수정 전 좌표값
	 */
	gitbuilder.command.exe.UndoRedoEdit = function UndoRedoEdit (olFeature, coordinates) {
		var geometry = null;
		var newGeometry = null;
		var setGeometry = function (feature, coordinate) {
			geometry = feature.getGeometry();
			if (geometry instanceof ol.geom.MultiPolygon) {
				newGeometry = new ol.geom.MultiPolygon(coordinate);
			} else if (geometry instanceof ol.geom.Polygon) {
				newGeometry = new ol.geom.Polygon(coordinate);
			} else if (geometry instanceof ol.geom.MultiLineString) {
				newGeometry = new ol.geom.MultiLineString(coordinate);
			} else if (geometry instanceof ol.geom.LineString) {
				newGeometry = new ol.geom.LineString(coordinate);
			} else {
				console.error("feature type error");
				return;
			}
			feature.setGeometry(newGeometry);
		};
		
		for (var i=0; i<olFeature.length; ++i) {
			setGeometry(olFeature[i], coordinates[i]);
		}

		// 선택된 feature가 존재하고 현재 undo를 실행할 feature가 선택된 feature와 같은 것이라면 이 구문을 시행
		// mbr, 편집 버튼 위치, 전역변수를 새로 갱신
		if (gitbuilder.variable.selectedFeatures.getLength() === 1) {
			if (gitbuilder.variable.selectedFeature.getId() === olFeature[0].getId()) {
				gitbuilder.variable.selectedGeometry = olFeature[0].getGeometry();
				gitbuilder.method.edit.SetFlatPoint();
				gitbuilder.ui.RefreshEditToolTip();
				gitbuilder.variable.map.render();
			}
		}
	}
	
	gitbuilder.command.exe.UndoRedoFeature = function UndoRedoFeature (olFeature, value) {
		if (value === 'hide') {
			var style = new ol.style.Style({
				image: ''
			});
			for (var i=0; i<olFeature.length; ++i) {
				olFeature[i].setStyle(style);
			}
			
			gitbuilder.ui.RemoveEditToolTip();
			gitbuilder.variable.selectedFeatures.clear();
			gitbuilder.method.edit.RemoveAllBuilderInteraction();
			gitbuilder.variable.map.render();
		} else if (value === 'show') {
			for (var i=0; i<olFeature.length; ++i) {
				olFeature[i].setStyle(null);
			}
		} else if (value === 'create'){
			var layerId = null;
			var layer = null;
			var source = null;
			for (var i=0; i<olFeature.length; ++i) {
				layerId = olFeature[i].getId().split('.')[0];
				layer = gitbuilder.method.layer.getLayerById(layerId);
				source = layer.getSource();
				source.addFeature(olFeature[i]);
			}
		} else {
			console.error('"UndoRedoFeatureShow()" function command is "show" or "hide". check your command.');
		}
	}
	
	gitbuilder.command.exe.UndoRedoLayer = function UndoRedoLayer (olLayer, value) {
		var layerId = null;
		var element = null;
		var exeCommand = function (layer) {
			layerId = layer.get('id');
			element = $('li[layerid="'+layerId+'"]');
			
			if (value === 'hide') {
				layer.setVisible(false);
				element.hide();
				if (element.hasClass('ui-selected')) {
					element.removeClass('ui-selected');
					element.find(".GitBuilder-LayerList-Function").hide();
					gitbuilder.ui.CloseNavigatorWindow();
					gitbuilder.method.edit.RemoveAllBuilderInteraction();
					gitbuilder.variable.selectedLayers.clear();
				}
			} else if (value === 'show') {
				layer.setVisible(true);
				element.show();
			} else {
				console.error('UndoRedoLayer() function command is "show" or "hide". check your command.');
			}
		}
		
		if (olLayer instanceof Array) {
			for (var i=0; i<olLayer.length; ++i) {
				exeCommand(olLayer[i]);
			}
		} else {
			exeCommand(olLayer);
		}
	}
	
	//========================================================================================================
	//=============================================  ================================================
	
	gitbuilder.command.ui.AutoComplete = function AutoComplete () {
		var availableTags = [
		                     "LAYER", 
		                     "FEATURE"
		                     ];

		$('#commandKeyword').autocomplete({
			source: function (request, response) {
				var matcher = new RegExp('^'+$.ui.autocomplete.escapeRegex(request.term), 'i');
				response($.grep(availableTags, function (item) {
					return matcher.test(item);
				}));
			},
			position: { my: 'left bottom', at: 'left top'},
			open: function () {
				$('#ui-id-1').css('display', 'inline-block');
				$('.ui-helper-hidden-accessible').remove();
			}
		});
	}
	
	gitbuilder.command.SetCommandHistory = function SetCommandHistory () {
		
	}
	
	gitbuilder.command.CheckCommand = function CheckCommand () {
		var command = $('#commandKeyword').val();
		if (command.toUpperCase() === 'LAYER') {
			$('#commandKeyword').val('');
			gitbuilder.ui.NewVectorWindow();
		}
		
		if (command.match(/^feature/i) !== '') {
			var layers = gitbuilder.variable.selectedLayers;
			if (layers.getLength() !== 1) {
				$('#commandKeyword').val('FEATURE > Layer가 선택되지 않았거나 다중 선택이 되었습니다. 하나의 Layer를 선택해주세요.');
				return;
			}
			$('#commandKeyword').val('FEATURE > 지도상에서 좌표를 지정하세요.');
			gitbuilder.method.edit.DrawFeature();
		}
	}
	
	gitbuilder.command.ParseStringCommand = function ParseStringCommand (command) {
		var func = '';
		var key = [];
		var value = [];
		var temp = [];
		
		var cmd = command.toLowerCase().replace(/\s/gi, '');
		
		func = cmd.split('(')[0];
		
		
		if (cmd.indexof('[') === -1) {
			var a = cmd.slice(cmd.indexof('(')+1, cmd.indexof('[')-1).split(',');
			var b = cmd.slice(cmd.indexof('[')+1, cmd.indexof(']')).split(',');
			
			temp = a.concat(b);
		} else {
			temp = cmd.slice(cmd.indexof('(')+1, cmd.indexof(')')).split(',');
		}
		
		for (var i=0; i<temp.length; ++i) {
			key.push(temp[i].split('=')[0]);
			value.push(temp[i].split('=')[1]);
		}
		
		return {
			func: func,
			key: key,
			value: value
		}
	}
	
	//========================================================================================================
	//============================================ Command Event =============================================
	
	$(window).on('keydown', function (e) {
		// ctrl+z undo
		if (e.ctrlKey && e.which === 90) {
			e.preventDefault();
			// draw가 실행 중일 때 마지막 점을 하나 지움
			if (gitbuilder.variable.intrctn.draw !== undefined && 
					gitbuilder.variable.intrctn.draw.sketchFeature_ !== null) {
				gitbuilder.variable.intrctn.draw.removeLastPoint();
			} else {
				if (gitbuilder.variable.undoRedo.lengthOfUndo() !== 0) {
					// redo를 위한 feature의 현재 좌표값을 저장 (undo 함수 실행에 필요한 정보를 담은 객체를 반환)
					var command = gitbuilder.variable.undoRedo.getUndo(gitbuilder.variable.undoRedo.lengthOfUndo()-1);
					
					if (command.exe.name === 'UndoRedoFeature') {
						if (command.value === 'create') {
							gitbuilder.variable.undoRedo.setRedo(new gitbuilder.command.UndoRedoFeature(command.layer, command.feature, 'hide'));
						} else if (command.value === 'show') {
							gitbuilder.variable.undoRedo.setRedo(new gitbuilder.command.UndoRedoFeature(command.layer, command.feature, 'hide'));
						} else if (command.value === 'hide') {
							gitbuilder.variable.undoRedo.setRedo(new gitbuilder.command.UndoRedoFeature(command.layer, command.feature, 'show'));
						} else {
							console.error('Command Error: "create", "show", "hide"');
							return;
						}
						gitbuilder.variable.undoRedo.exeUndoFeature();
					} else if (command.exe.name === 'UndoRedoLayer') {
						gitbuilder.variable.undoRedo.setRedo(new gitbuilder.command.UndoRedoLayer(command.layer, 'show'));
						gitbuilder.variable.undoRedo.exeUndoLayer();
					} else {
						if (command.feature instanceof Array) {
							var coordinates = [];
							for (var i=0; i<command.feature.length; ++i) {
								coordinates.push(command.feature[i].getGeometry().getCoordinates());
							}
						} else {
							var coordinates = command.feature.getGeometry().getCoordinates();
						}
						gitbuilder.variable.undoRedo.setRedo(new gitbuilder.command.UndoRedoEdit(command.feature, coordinates));
						gitbuilder.variable.undoRedo.exeUndoFeature();
					}
				}
			}
		}
		
		// ctrl+y redo
		if (e.ctrlKey && e.which === 89) {
			e.preventDefault();
			if (gitbuilder.variable.undoRedo.lengthOfRedo() !== 0 && gitbuilder.variable.intrctn.draw === undefined) {
				var command = gitbuilder.variable.undoRedo.getRedo(gitbuilder.variable.undoRedo.lengthOfRedo()-1);
				
				if (command.exe.name === 'UndoRedoFeature') {
					if (command.value === 'show') {
						gitbuilder.variable.undoRedo.setUndo(new gitbuilder.command.UndoRedoFeature(command.layer, command.feature, 'hide'));
					} else if (command.value === 'hide') {
						gitbuilder.variable.undoRedo.setUndo(new gitbuilder.command.UndoRedoFeature(command.layer, command.feature, 'show'));
					} else {
						console.error('Command Error: "create", "show", "hide"');
						return;
					}
					gitbuilder.variable.undoRedo.exeRedoFeature();
				} else if (command.exe.name === 'UndoRedoLayer') {
					gitbuilder.variable.undoRedo.setUndo(new gitbuilder.command.UndoRedoLayer(command.layer, 'hide'));
					gitbuilder.variable.undoRedo.exeRedoLayer();
				} else {
					if (command.feature instanceof Array) {
						var coordinates = [];
						for (var i=0; i<command.feature.length; ++i) {
							coordinates.push(command.feature[i].getGeometry().getCoordinates());
						}
					} else {
						var coordinates = command.feature.getGeometry().getCoordinates();
					}
					gitbuilder.variable.undoRedo.setUndo(new gitbuilder.command.UndoRedoEdit(command.feature, coordinates));
					gitbuilder.variable.undoRedo.exeRedoFeature();
				}
			}
		}
		
		// command 입력창 생성
		if (e.which === 192) {
			e.preventDefault();
			if ($('#autocomplete').css('display') === 'none') {
				$('#autocomplete').show('slow');
				$('#commandKeyword').focus();
			} else {
				$('#commandKeyword').blur();
				$('#autocomplete').hide('slow');
			}
		}
		
		$('#commandKeyword').on('focusout', function () {
			$('#commandKeyword').focus();
		});
	});
}