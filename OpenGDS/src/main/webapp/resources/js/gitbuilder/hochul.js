if (!gitbuilder.method.edit.RotateFeature) {
	
	/**
	 * @description MultiTransform 기능 활성화
	 * 김호철
	 */
	gitbuilder.method.edit.EditToolTip = function EditToolTip() {
		
		gitbuilder.method.edit.RemoveAllBuilderInteraction();
		
		var inter = [];

		gitbuilder.variable.map.getInteractions().forEach(function (interaction) {
			if (interaction instanceof gitbuilder.interaction.MultiTransform || 
					interaction instanceof ol.interaction.Select) {
				inter.push(interaction);
			}
		});
		
		for (var i in inter) {
			gitbuilder.variable.map.removeInteraction(inter[i]);
		}
		
		gitbuilder.ui.CloseAttributeTable();

		var layers = gitbuilder.variable.selectedLayers;
		if (layers.getLength() !== 1) {
			console.error("Selected layer must be one, check [gitbuilder.variable.selectedLayers]");
			return;
		}

		var select = new ol.interaction.Select({
			layers: [layers.item(0)], 
			wrapX: false
		});
		
		gitbuilder.variable.map.addInteraction(select);
		gitbuilder.variable.map.addInteraction(new gitbuilder.interaction.MultiTransform({
			features: select.getFeatures()
		}));
	}
	
	
	
	//========================================================================================================
	//=================================================== UI =================================================
	
	/**
	 * @description EditToolTip 제거, 삭제. 
	 * skip번호를 제외한 나머지 EditToolTip이 제거된다.
	 * EditToolTip의 각 버튼들은 서로다른 overlay객체 id를 가지고 있다.
	 * 'editTip1', 'editTip2'와 같이 번호만 다른 형식으로 저장되어 있다.
	 * @param {Numver} 지우지 않을 element의 id값 맨 뒤의 번호
	 */
	gitbuilder.ui.RemoveEditToolTip = function RemoveEditToolTip(skip) {
		var inter = [];

		gitbuilder.variable.map.getInteractions().forEach(function (interaction) {
			if (interaction instanceof gitbuilder.interaction.MultiTransform || 
					interaction instanceof ol.interaction.Select) {
				inter.push(interaction);
				
			}
		});
		
		for (var i in inter) {
			gitbuilder.variable.map.removeInteraction(inter[i]);
		}
		gitbuilder.variable.map.render();
		return;
	}
};

//if (!gitbuilder.file) {
//	
//	gitbuilder.file = {
//			
//			fileSystem: null, 
//			
//			fileName: null,
//			
//			fileBlob: null,
//			
//			onInitFs: function (fs) {
//				gitbuilder.file.fileSystem = fs;
//				fs.root.getFile('history.txt', {create: false}, function (fileEntry) {
//					gitbuilder.file.fileName = fileEntry.name;
//					console.log('File created.');
//				}, gitbuilder.file.errorHandler);
//			}, 
//			
//			errorHandler: function(e) {
//				var msg = '';
//				
//				switch (e.code) {
//				case FileError.QUOTA_EXCEEDED_ERR:
//					msg = 'QUOTA_EXCEEDED_ERR';
//					break;
//				case FileError.NOT_FOUND_ERR:
//					msg = 'NOT_FOUND_ERR';
//					break;
//				case FileError.SECURITY_ERR:
//					msg = 'SECURITY_ERR';
//					break;
//				case FileError.INVALID_MODIFICATION_ERR:
//					msg = 'INVALID_MODIFICATION_ERR';
//					break;
//				case FileError.INVALID_STATE_ERR:
//					msg = 'INVALID_STATE_ERR';
//					break;
//				default:
//					msg = 'Unknown Error';
//					break;
//				};
//				
//				console.log('Error: ' + msg);
//			}, 
//			
//			readLocalFile: function () {
//				gitbuilder.file.fileSystem.root.getFile('history.txt', {}, function (fileEntry) {
//					
//					fileEntry.file( function (file) {
//						
//						var reader = new FileReader();
//						
//						reader.onloadend = function (e) {
//							return this.result;
//						};
//						
//						reader.readAsText(file, 'utf-8');
//						
//					}, gitbuilder.file.errorHandler);
//					
//				}, gitbuilder.file.errorHandler);
//			}, 
//			
//			writeLocalFile: function () {
//				gitbuilder.file.fileSystem.root.getFile('history.txt', {}, function (fileEntry) {
//					
//					fileEntry.createWriter( function (fileWriter) {
//						
//						fileWriter.onwriteend = function (e) {
//							console.log('Write complete.');
//						};
//						
//						fileWriter.onerror = function (e) {
//							console.log('Write failed: ' + e.toString());
//						};
//						
//						var blob = new Blob(gitbuilder.command.history, {type: 'text/plain;charset=utf-8'});
//						
//						gitbuilder.file.fileBlob = blob;
//						
//						fileWriter.write(blob)
//						
//					}, gitbuilder.file.errorHandler);
//					
//				}, gitbuilder.file.errorHandler);
//			}
//	}
//	
//	window.requestFileSystem = window.requestFileSystem || window.webkitRequestFileSystem;
//	
////	window.requestFileSystem(window.TEMPORARY, 5*1024*1024, function (fs) {
////		fs.root.getFile('history.txt', {create: false}, function (fileEntry) {
////			fileEntry.remove(function() {
////				console.log('File removed.');
////			}, gitbuilder.file.errorHandler);
////		}, gitbuilder.file.errorHandler);
////	}, gitbuilder.file.errorHandler);
//	
//	window.requestFileSystem(window.TEMPORARY, 5*512*2048, gitbuilder.file.onInitFs, gitbuilder.file.errorHandler);
//	
//	$(document).on('click', '#download-file', function () {
//		if (gitbuilder.file.fileBlob !== null) {
//			var element = document.createElement('a');
//			element.id = 'download-href';
//			element.download = gitbuilder.file.fileName;
//			element.href = window.URL.createObjectURL(gitbuilder.file.fileBlob);
//			element.style.display = 'none';
//			
//			element.click();
//		} else {
//			console.error('history is empty');
//		}
//	});
//};

gitbuilder.command = gitbuilder.command ? gitbuilder.command : {};


//================================= layer create command ===================================
gitbuilder.command.CreateLayer = function (opt_option) {
	var options = opt_option ? opt_option : {};
	
	this.map_ = options.map;

	this.getRandomColor_ = function () {
		var letters = '0123456789ABCDEF';
		var color = '#';
		for (var i = 0; i < 6; i++) {
			color += letters[Math.floor(Math.random() * 16)];
		}
		return color;
	}
	
	this.convertHex_ = function (hex, opacity) {
		hex = hex.replace('#', '');
		r = parseInt(hex.substring(0, 2), 16);
		g = parseInt(hex.substring(2, 4), 16);
		b = parseInt(hex.substring(4, 6), 16);

		// Add Opacity to RGB to obtain RGBA
		result = 'rgba(' + r + ',' + g + ',' + b + ',' + opacity / 100 + ')';
		return result;
	}
	
	this.param_ = null;
};

gitbuilder.command.CreateLayer.prototype.setAttribute = function (param) {
	this.param_ = param ? param : {};
	
	var temp = this.param_.type.toUpperCase();
	
	switch (temp) {
	case "POLYGON":
		this.param_.type = "Polygon";
		break;
	case "MULTIPOLYGON":
		this.param_.type = "MultiPolygon";
		break;
	case "POINT":
		this.param_.type = "Point";
		break;
	case "MULTIPOINT":
		this.param_.type = "MultiPoint";
		break;
	case "LINESTRING":
		this.param_.type = "LineString";
		break;
	case "MULTILINESTRING":
		this.param_.type = "MultiLineString";
		break;
	default:
		console.error('not supported type');
		return;
	}
	
	var layers = this.map_.getLayers();
	
	for (var i = 0; i < layers.getLength(); ++i) {
		if (layers.item(i).get("name") === param.name) {
			return false;
		}
	}
	return true;
};

gitbuilder.command.CreateLayer.prototype.execute = function () {
	if (!this.param_) {
		console.error('parameter is null');
		return;
	}
	
	var source = new ol.source.Vector({
		wrapX: true
	});
	var fill = new ol.style.Fill({
		color: this.convertHex_(this.getRandomColor_(), 50)
	});
	var stroke = new ol.style.Stroke({
		color: this.getRandomColor_(),
		width: 1.25
	});
	var text = new ol.style.Text({});
	var styles = new ol.style.Style({
		image: new ol.style.Circle({
			fill: fill,
			stroke: stroke,
			radius: 5
		}),
		fill: fill,
		stroke: stroke,
		text: text
	});
	
	var layer = new ol.layer.Vector({
		source : source,
		style : styles
	});
	
	layer.setZIndex(this.map_.getLayers().getLength());
	layer.set("name", this.param_.name);
	layer.set("type", this.param_.type);
	//layer.set("attribute", this.param.attribute);
	
	this.map_.addLayer(layer);
};
//================================================================================

//============================ feature create command ============================
gitbuilder.command.CreateFeature = function (opt_option) {
	var options = opt_option ? opt_option : {};
	
	this.map_ = options.map;
	
	this.param_ = null;
	
	// feature의 고유한 id를 생성한다
	this.setFeatureId_ = function (layer, feature) {		
		var source = null;
		
		if (layer instanceof ol.layer.Vector) {
			source = layer.getSource();
		} else if (layer instanceof ol.layer.Image) {
			if (layer.getSource() instanceof ol.source.ImageVector) {
				source = layer.getSource().getSource();
			} else {
				console.error('Image layer is not imagevector layer');
			}
		}
		
		var features = source.getFeatures();
		
		if (features.length > 0) {
			var max = 1;
			for (var i = 0; i < features.length; ++i) {
				var fid = features[i].getId();
				var num = parseInt(fid.substring(fid.indexOf('.') + 1));
				if (num > max) {
					max = num;
				} else {
					continue;
				}
			}
			max++;
			var feat = features[0].getId();
			var lid = feat.substring(0, feat.indexOf('.'));
			var fid = lid + '.' + max;
			feature.setId(fid);
			return feature;
		} else if (features.length === 0) {
			var fid = layer.get('id') + '.1';
			feature.setId(fid);
			return feature;
		}
	}
	
	this.getLayerById_ = function (_id) {
		var layers = this.map_.getLayers().getArray();
		for (var i = 0; i < layers.length; ++i) {
			if (layers[i].get('id') === _id) {
				return layers[i];
			}
		}
	}
	
	this.createGeometry_ = function (layer, coordinate) {
		var type = layer.get('type');
		var geometry = null;
		
		switch (type) {
		case 'Point':
			geometry = new ol.geom.Point(coordinate);
			break;
		case 'MultiPoint':
			geometry = new ol.geom.MultiPoint(coordinate);
			break;
		case 'LineString':
			geometry = new ol.geom.LineString(coordinate);
			break;
		case 'MultiLineString':
			geometry = new ol.geom.MultiLineString(coordinate);
			break;
		case 'Polygon':
			geometry = new ol.geom.Polygon(coordinate);
			break;
		case 'MultiPolygon':
			geometry = new ol.geom.MultiPolygon(coordinate);
			break;
		}
		
		return geometry;
	}
}

gitbuilder.command.CreateFeature.prototype.setAttribute = function (param) {
	this.param_ = param ? param : {};
	
	if (!param.layerid || !param.coordinate) {
		console.error('layer or coordinate is undefined');
		return false;
	}
	return true;
}

gitbuilder.command.CreateFeature.prototype.execute = function () {
	var layer = this.getLayerById_(this.param_.layerid);
	var source = layer.getSource();
	var geometry = this.createGeometry_(layer, this.param_.coordinate);
	var feature = new ol.Feature({
		geometry: geometry
	});
	if (geometry) {
		source.addFeature(feature)
	}
}
//================================================================================
gitbuilder.availableTags = [
                            "LAYER", 
                            "FEATURE", 
                            "REMOVE LAYER", 
                            "REMOVE FEATURE"
                            ];

$(document).on('keydown', function (e) {
	
	// command 입력창 숨김, 나타내기
	if (e.which === 192) {
		
		e.stopPropagation();
		e.preventDefault();
		
		if ($('#autocomplete').css('display') === 'none') {
			$('#autocomplete').show('slow');
			$('#commandKeyword').focus();
			
		} else {
			//입력창을 숨겼을때는 입력창을 비활성화 시킴
			$('#commandKeyword').blur();
			$('#autocomplete').hide('slow');
			gitbuilder.file.writeLocalFile();
		}
	}
});