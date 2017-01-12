gitbuilder = {};

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
	
	return command;
};

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
		
		map.addLayer(layer);
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
		return true;
	}
};