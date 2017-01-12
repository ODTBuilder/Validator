if (!gitbuilder.method.edit.RotateFeature) {
	
	/**
	 * @description MultiTransform 기능 활성화
	 * 김호철
	 */
	gitbuilder.method.edit.EditToolTip = function EditToolTip() {
		if (gitbuilder.variable.intrctn.select === undefined || gitbuilder.variable.intrctn.select === null) {
			gitbuilder.method.edit.RemoveAllBuilderInteraction();
		} else if (gitbuilder.variable.intrctn.select instanceof ol.interaction.Select) {
			gitbuilder.method.edit.RemoveAllBuilderInteraction('select');
		}
		
		gitbuilder.ui.CloseAttributeTable();

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
		
		var select;
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

		var multiTransform = new gitbuilder.interaction.MultiTransform({
			features: select.getFeatures()
		});
		gitbuilder.variable.map.addInteraction(multiTransform);
		gitbuilder.variable.intrctn.multiTransform = multiTransform;
		gitbuilder.variable.map.render();
	};
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

gitbuilder.command.defaults = function (opt_options) {
	var options = opt_options ? opt_options : {};
	
	var command = [];
	
	var createLayer = options.createLayer !== undefined ? options.createLayer : true;
	if (createLayer) {
		command.push(new gitbuilder.command.Command({
			cmdName: 'LAYER',
			command: gitbuilder.command.createLayer,
			console: {
				success: 'Create Layer Success!',
				fail: 'Create Layer Fail! Hint: <LAYER name:__/type:__>'
			}
		}));
	}
	
	var createFeature = options.createFeature !== undefined ? options.createFeature : true;
	if (createFeature) {
		command.push(new gitbuilder.command.Command({
			cmdName: 'FEATURE',
			command: gitbuilder.command.createFeature,
			console: {
				success: 'Create Feature Success!',
				fail: 'Create Feature Fail! Hint: <FEATURE layername:__/coordinate:[x1,y1],[x2,y2],...>'
			}
		}));
	}
	
	var changeLayerName = options.changeLayerName !== undefined ? options.changeLayerName : true;
	if (changeLayerName) {
		command.push(new gitbuilder.command.Command({
			cmdName: 'LAYERNAME',
			command: gitbuilder.command.changeLayerName,
			console: {
				success: 'Change Layer Name Success!',
				fail: 'Change Layer Name Fail! Hint: <LAYERNAME target:__/changename:__>'
			}
		}));
	}
	
	var removeLayer = options.removeLayer !== undefined ? options.removeLayer : true;
	if (removeLayer) {
		command.push(new gitbuilder.command.Command({
			cmdName: 'REMOVELAYER',
			command: gitbuilder.command.removeLayer,
			console: {
				success: 'Remove Layer Success!',
				fail: 'Remove Layer Fail! Hint: <REMOVELAYER target:__>'
			}
		}));
	}
	
	var removeFeature = options.removeFeature !== undefined ? options.removeFeature : true;
	if (removeFeature) {
		command.push(new gitbuilder.command.Command({
			cmdName: 'REMOVEFEATURE',
			command: gitbuilder.command.removeFeature,
			console: {
				success: 'Remove Feature Success!',
				fail: 'Remove Feature Fail! Hint: <REMOVEFEATURE layer:__/id:__>'
			}
		}));
	}
	
	var layerStyle = options.setLayerStyle !== undefined ? options.setLayerStyle : true;
	if (layerStyle) {
		command.push(new gitbuilder.command.Command({
			cmdName: 'LAYERSTYLE',
			command: gitbuilder.command.layerStyle,
			console: {
				success: 'Set Layer Style Success!',
				fail: 'Set Layer Style Fail! Hint: <LAYERSTYLE target:__/[fillcolor:__/strokecolor:__/strokewidth:__]>'
			}
		}));
	}
	
	return command;
};

//================================= layer create command ===================================

gitbuilder.command.Command = function (options) {

	this.cmdName_ = options.cmdName;
	
	this.command_ = options.command;
	if (!this.command_) {
		console.error('Arribute Error: command must be setting');
		return;
	}
	
	this.console_ = options.console;
	
	this.setCommand = function (command) {
		this.command_ = command;
	};
	
	this.setConsole = function (console) {
		this.console_ = console;
	};
	
	this.getCmdName = function () {
		return this.cmdName_;
	};
	
	this.getCommand = function () {
		return this.command_;
	};
	
	this.getConsole = function () {
		return this.console_;
	};
};

gitbuilder.command.Command.prototype.execute = function (param) {
	if (this.command_(param)) {
		this.outputConsole(param, true);
		this.outputHistory(param, param.element.val());
		param.element.val('');
	} else {
		this.outputConsole(param, false);
	}
};

gitbuilder.command.Command.prototype.outputConsole = function (param, bool) {
	if (!!this.console_) {
		var text;
		if (bool) {
			text = this.console_.success;
		} else {
			text = this.console_.fail;
		}
		var tables = $(param.element).parent().parent().find('table');
		var tr = document.createElement('tr');
		var td = document.createElement('td');
		td.innerText = text;
		tr.appendChild(td);
		tables[0].appendChild(tr);
		tables[0].parentElement.scrollTop = tables[0].parentElement.scrollHeight;
	}
};

gitbuilder.command.Command.prototype.outputHistory = function (param, history) {
	var text = history;
	var tables = $(param.element).parent().parent().find('table');
	var tr = document.createElement('tr');
	var td = document.createElement('td');
	td.innerText = text;
	tr.appendChild(td);
	tables[1].appendChild(tr);
	tables[1].parentElement.scrollTop = tables[1].parentElement.scrollHeight;
};

// =======================================================================================

gitbuilder.command.createLayer = function (param) {
	if (!!param.name && !!param.type) {
		var getRandomColor = function () {
			var letters = '0123456789ABCDEF';
			var color = '#';
			for (var i = 0; i < 6; i++) {
				color += letters[Math.floor(Math.random() * 16)];
			}
			return color;
		};
		
		var convertHex = function (hex, opacity) {
			hex = hex.replace('#', '');
			var r = parseInt(hex.substring(0, 2), 16);
			var g = parseInt(hex.substring(2, 4), 16);
			var b = parseInt(hex.substring(4, 6), 16);

			// Add Opacity to RGB to obtain RGBA
			var result = 'rgba(' + r + ',' + g + ',' + b + ',' + opacity / 100 + ')';
			return result;
		};
		
		var fill = new ol.style.Fill({
			color: convertHex(getRandomColor(), 50)
		});
		
		var stroke = new ol.style.Stroke({
			color: getRandomColor(),
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
		
		var map = param.map;
		var layers = map.getLayers();
		var name = param.name;
		for (var i = 0; i < layers.getLength(); ++i) {
			if (layers.item(i).get("name") === name) {
				return false;
			}
		}
		var source = new ol.source.Vector({
			wrapX: true
		});
		
		var layer = new ol.layer.Vector({
			source : source,
			style : styles
		});
		var type;
		
		switch (param.type.toUpperCase()) {
		case "POLYGON":
			type = "Polygon";
			break;
		case "MULTIPOLYGON":
			type = "MultiPolygon";
			break;
		case "POINT":
			type = "Point";
			break;
		case "MULTIPOINT":
			type = "MultiPoint";
			break;
		case "LINESTRING":
			type = "LineString";
			break;
		case "MULTILINESTRING":
			type = "MultiLineString";
			break;
		default:
			console.error('not supported type');
			return false;
		}
		
		layer.setZIndex(map.getLayers().getLength());
		layer.set("name", name);
		layer.set("type", type);
		
		// 전역변수 없애야함!!
		layer.set("id", gitbuilder.method.layer.createLayerId());
		var layerOpt = gitbuilder.method.layer.getVectorOption();
		layer.set("attribute", layerOpt.attributeList);
		map.addLayer(layer);
		gitbuilder.method.layer.updateLayerList();
		return true;
	} else {
		return false;
	}
};

gitbuilder.command.createFeature = function (param) {
	if (!!param.layername && !!param.coordinate) {
		
		var layers = param.map.getLayers().getArray();
		var layer;
		
		for (var i = 0; i < layers.length; ++i) {
			if (layers[i].get('name') === param.layername) {
				layer = layers[i];
			}
		}
		
		if (!layer) {
			console.error('not find layer');
			return false;
		}
		
		var temp = param.coordinate.split(',');
		var coordinate = [];
		var x, y;
		for (var i = 0; i < temp.length/2; ++i) {
			x = temp[2*i].slice(temp[2*i].indexOf('[') + 1);
			y = temp[2*i + 1].slice(0, temp[2*i + 1].indexOf(']'));
			if (!parseFloat(x) || !parseFloat(y)) {
				console.error('wrong coordinate');
				return false;
			}
			coordinate.push([parseFloat(x), parseFloat(y)]);
		}
		
		var source = layer.getSource();
		var type = layer.get('type');
		var geometry;
		
		switch (type) {
		case 'Point':
			geometry = new ol.geom.Point(coordinate);
			break;
		case 'MultiPoint':
			geometry = new ol.geom.MultiPoint(coordinate);
			break;
		case 'LineString':
			geometry = new ol.geom.LineString([coordinate]);
			break;
		case 'MultiLineString':
			geometry = new ol.geom.MultiLineString([coordinate]);
			break;
		case 'Polygon':
			geometry = new ol.geom.Polygon([[coordinate]]);
			break;
		case 'MultiPolygon':
			geometry = new ol.geom.MultiPolygon([[coordinate]]);
			break;
		}
		
		var feature = new ol.Feature({
			geometry: geometry
		});
		
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
		} else if (features.length === 0) {
			var fid = layer.get('id') + '.1';
			feature.setId(fid);
		}
		
		if (geometry) {
			source.addFeature(feature)
			return true;
		} else {
			return false;
		}
	} else {
		return false;
	}
};

gitbuilder.command.changeLayerName = function (param) {
	if (!!param.target && !!param.changename) {
		var layers = param.map.getLayers().getArray();
		var layer;
		
		for (var i = 0; i < layers.length; ++i) {
			if (layers[i].get('name') === param.target) {
				layer = layers[i];
			}
		}
		
		if (!layer) {
			console.error('not find layer');
			return false;
		}
		
		layer.set('name', param.changename);
		gitbuilder.method.layer.updateLayerList();
		return true;
	}
};

gitbuilder.command.removeLayer = function (param) {
	if (!!param.target) {
		var layers = param.map.getLayers().getArray();
		var layer;
		
		for (var i = 0; i < layers.length; ++i) {
			if (layers[i].get('name') === param.target) {
				layer = layers[i];
			}
		}
		
		if (!layer) {
			console.error('not find layer');
			return false;
		}
		
		param.map.removeLayer(layer);
		gitbuilder.method.layer.updateLayerList();
		return true;
	}
};

gitbuilder.command.removeFeature = function (param) {
	if (!!param.layer && !!param.id) {
		var layers = param.map.getLayers().getArray();
		var layer;
		
		for (var i = 0; i < layers.length; ++i) {
			if (layers[i].get('name') === param.layer) {
				layer = layers[i];
			}
		}
		
		if (!layer) {
			console.error('not find layer');
			return false;
		}
		
		var source = layer.getSource();
		
		var feature = source.getFeatureById(param.id);
		
		source.removeFeature(feature);
		
		return true;
	}
};

gitbuilder.command.layerStyle = function (param) {
	if (!!param.target) {
		
		var layers = param.map.getLayers().getArray();
		var layer;
		
		for (var i = 0; i < layers.length; ++i) {
			if (layers[i].get('name') === param.target) {
				layer = layers[i];
			}
		}
		
		if (!layer) {
			console.error('not find layer');
			return false;
		}
		
		var fill = layer.getStyle().getFill();
		var stroke = layer.getStyle().getStroke();
		
		if (param.fillcolor) {
			fill.setColor(param.fillcolor);
		}
		
		if (param.strokecolor) {
			stroke.setColor(param.strokecolor);
		}
		
		if (param.strokewidth) {
			stroke.setWidth(parseInt(param.strokewidth));
		}
		
		return true;
	}
}
//================================================================================

$(document).on('keydown', function (e) {
	
	// command 입력창 숨김, 나타내기
	if (e.which === 192) {
		
		e.stopPropagation();
		e.preventDefault();
		
		if ($('#commandWindow').css('display') === 'none') {
			$('#commandWindow').show('slow');
			$('#commandKeyword').focus();
			
		} else {
			//입력창을 숨겼을때는 입력창을 비활성화 시킴
			$('#commandKeyword').blur();
			$('#commandWindow').hide('slow');
		}
	}
});