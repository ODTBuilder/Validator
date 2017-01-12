/**
 * 레이어 리스트를 생성하는 객체를 정의한다.
 * 
 * @author yijun.so
 * @date 2016. 10
 * @version 0.03
 */
/*
 * ! OpenGDS/Builder 편집도구 레이어 메뉴 JQuery UI selectable, sortable 소스를 참조하여 제작함.
 * required : Widget, :data Selector, scrollParent, Mouse.
 */
var gitbuilder;
if (!gitbuilder)
	gitbuilder = {};
if (!gitbuilder.ui)
	gitbuilder.ui = {};
gitbuilder.ui.LayerListable = $
		.widget(
				"gitbuilder.layerlistable",
				$.ui.mouse,
				{
					version : "1.12.1",
					map : null,
					selectedLayers : new ol.Collection(),
					layers : null,
					layerLength : 0,
					widgetEventPrefix : "sort",
					ready : false,
					options : {
						map : null,
//						autoRefresh : true,
						appendToSort : "parent",
						appendToSelect : "body",
						axis : "y",
						connectWith : false,
						containment : false,
						cursor : "auto",
						cursorAt : false,
						dropOnEmpty : true,
						forcePlaceholderSize : false,
						forceHelperSize : false,
						grid : false,
						// handle : false,
						handle : ".GitBuilder-LayerSortable-Handle",
						helperToSort : "original",
						items : "> li",
						opacity : false,
						placeholder : false,
						revert : false,
						scroll : true,
						scrollSensitivity : 20,
						scrollSpeed : 20,
						scope : "default",
						toleranceToSort : "intersect",
						toleranceToSelect : "touch",
						zIndex : 1000,
						filterToSelect : ".Gitbuilder-LayerSelectable-Item",
						// cancel : ".GitBuilder-LayerSortable-Handle",

						// Callbacks
						activate : null,
						beforeStop : null,
						change : null,
						deactivate : null,
						out : null,
						over : null,
						receive : null,
						remove : null,
						sort : null,
						start : null,
						stop : null,
						update : null,

						// Callbacks
						selected : null,
						selecting : null,
						startSelect : null,
						stopSelect : null,
						unselected : null,
						unselecting : null
					},
					_destroyToSort : function() {
						this._mouseDestroy();

						for (var i = this.items.length - 1; i >= 0; i--) {
							this.items[i].item.removeData(this.widgetName + "-item");
						}

						return this;
					},
					_destroyToSelect : function() {
						this.selectees.removeData("selectable-item");
						this._mouseDestroy();
					},
					_initToSort : function() {
						this.containerCache = {};
						// this._addClass("ui-sortable");
						this._addClass("Gitbuilder-LayerSortable");
						// Get the items
						this.refreshToSort();
						// Let's determine the parent's offset
						this.offset = this.element.offset();

						// Initialize mouse events for interaction
						// this._mouseInit();

						this._setHandleClassName();

						// We're ready to go
						this.ready = true;
					},
					_initToSelect : function() {
						var that = this;

						// this._addClass("ui-selectable");
						this._addClass("Gitbuilder-LayerSelectable");

						this.dragged = false;

						// Cache selectee children based on filter
						this.refreshToSelect = function() {
							that.elementPos = $(that.element[0]).offset();
							that.selectees = $(that.options.filterToSelect, that.element[0]);
							that._addClass(that.selectees, "ui-selectee");
							that.selectees.each(function() {
								var $this = $(this), selecteeOffset = $this.offset(), pos = {
									left : selecteeOffset.left - that.elementPos.left,
									top : selecteeOffset.top - that.elementPos.top
								};
								$.data(this, "selectable-item", {
									element : this,
									$element : $this,
									left : pos.left,
									top : pos.top,
									right : pos.left + $this.outerWidth(),
									bottom : pos.top + $this.outerHeight(),
									startselected : false,
									selected : $this.hasClass("ui-selected"),
									selecting : $this.hasClass("ui-selecting"),
									unselecting : $this.hasClass("ui-unselecting")
								});
							});
						};
						this.refreshToSelect();

						// this._mouseInit();

						this.helperToSelect = $("<div>");
						this._addClass(this.helperToSelect, "ui-selectable-helper");

						var functionArea = $(".ui-selectee", this.element[0]).find(".Gitbuilder-LayerSelectable-Function-Area");

						$(functionArea, this.element[0]).each(function() {
							$(this).hide();
						});
					},
					_getLayerByName : function(name) {
						var layers = this.map.getLayers().getArray();
						for (var i = 0; i < layers.length; i++) {
							if (layers[i].get("name") === name) {
								return layers[i];
							}
						}
						return false;
					},

					_isOverAxis : function(x, reference, size) {
						return (x >= reference) && (x < (reference + size));
					},

					_isFloating : function(item) {
						return (/left|right/).test(item.css("float")) || (/inline|table-cell/).test(item.css("display"));
					},

					_isUniqueName : function(name) {
						var that = this;

						var layers = that.map.getLayers();

						var isUnique = true;
						for (var i = 0; i < layers.getLength(); i++) {
							if (layers.item(i).get("name") === name) {
								isUnique = false;
								break;
							}
						}
						return isUnique;
					},

					_getLegend : function(layer) {
						if ((layer instanceof ol.layer.Vector)
								|| (layer instanceof ol.layer.Image && layer.getSource() instanceof ol.source.ImageVector)) {
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
							legend += '<div class="GitBuilder-LayerSelectable-Legend" ';
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

									legend += 'style="margin: 4px; width: 14px;height:14px; border-radius: 14px; border:' + strokeStyleCode
											+ '; background-color:' + fillColor + ';' + '"';
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
									legend += '<div style="display: block;width: 20px;height:8px;  border-top:' + strokeStyleCode + ';'
											+ 'border-bottom:' + strokeStyleCode2 + ';' + '">';
									legend += '</div>';

									legend += '<div style="display: block;width: 20px;height:8px;  border-top:' + strokeStyleCode2 + ';'
											+ 'border-bottom:' + strokeStyleCode + ';' + '">';
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

									legend += 'style="width: 20px;height:20px; border:' + strokeStyleCode + '; background-color:' + fillColor + ';'
											+ '"';
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
					},

					_removeLayerOnList : function(layer) {
						var that = this;
						var removeLayer;
						if (typeof layer === 'string') {
							removeLayer = that._getLayerByName(layer);
							console.log('string');
							that.map.removeLayer(removeLayer);
						} else if (layer instanceof ol.layer.Base) {
							removeLayer = layer;
							console.log('ol');
							that.map.removeLayer(removeLayer);
						} else {
							console.error('no match type');
						}
					},

					_removeConfirm : function(layer) {

						var that = this;
						var dialog = '';

						$(document).off("click", ".GitBuilder-Event-RemoveLayer");
						
						$( ".GitBuilder-Temp-Dialog" ).detach();					

						dialog += '<div class="modal fade bs-example-modal-sm GitBuilder-Temp-Dialog">';

						dialog += '<div class="modal-dialog modal-sm">';

						dialog += '<div class="modal-content">';

						dialog += '<div class="modal-header">';

						dialog += '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>';

						dialog += '<h4 class="modal-title">Confirm</h4>';

						dialog += '</div>';

						dialog += '<div class="modal-body">';

						var name = layer.get('name');

						dialog += '<p>[' + name + '] layer will be deleted.</p>';

						dialog += '</div>';

						dialog += '<div class="modal-footer">';

						dialog += '<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>';

						dialog += '<button type="button" class="btn btn-primary GitBuilder-Event-RemoveLayer" data-dismiss="modal">OK</button>';

						dialog += '</div>';

						dialog += '</div>';

						dialog += '</div>';

						dialog += '</div>';

						// $('body').append(dialog);

						$(dialog).modal('show');

						$(document).on("click", ".GitBuilder-Event-RemoveLayer", function() {
							that._removeLayerOnList(name);
						});

					},

					_redrawList : function() {
						// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

						var that = this;

						var layers = that.map.getLayers();

						var addedLayers = layers.getArray();

						addedLayers.sort(function(a, b) {
							return a.getZIndex() < b.getZIndex() ? -1 : a.getZIndex() > b.getZIndex() ? 1 : 0;
						});

						$(that.element).empty();

						for (var i = 0; i < addedLayers.length; i++) {
							var str = '';
							str += '<li layerName="' + addedLayers[i].get('name')
									+ '" class="GitBuilder-LayerSelectable-Item GitBuilder-LayerSortable-Item">';
							str += '<span class="GitBuilder-LayerSortable-Handle"><span class="glyphicon glyphicon-sort" aria-hidden="true"></span></span>';

							str += '<div class="Gitbuilder-LayerSelectable-Filter">';

							str += '<div>';
							str += addedLayers[i].get('name');
							str += '</div>';
							
							var legend = that._getLegend(addedLayers[i]);
							if (legend) {
								str += "<div style='float:right;'>";
								str += legend;
								str += "</div>";
							}

							str += '<div class="Gitbuilder-LayerSelectable-Function-Area">';
							str += "<div class='text-center'>";
							str += '<button type="button" class="btn btn-default Gitbuilder-LayerSelectable-Function-Button Gitbuilder-LayerSelectable-Function-Button-Show" layerName="'
									+ addedLayers[i].get("name") + '">';
							str += '<span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>';
							str += '<span class="glyphicon glyphicon-eye-close" aria-hidden="true"></span>';
							str += '</button>';
							str += '<button type="button" class="btn btn-default Gitbuilder-LayerSelectable-Function-Button Gitbuilder-LayerSelectable-Function-Button-Fit" layerName="'
									+ addedLayers[i].get("name") + '">';
							str += '<span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>';
							str += '</button>';
							str += '<button type="button" class="btn btn-default Gitbuilder-LayerSelectable-Function-Button Gitbuilder-LayerSelectable-Function-Button-Setting" layerName="'
									+ addedLayers[i].get("name") + '">';
							str += '<span class="glyphicon glyphicon-text-size" aria-hidden="true"></span>';
							str += '</button>';
							str += '<button type="button" class="btn btn-default Gitbuilder-LayerSelectable-Function-Button Gitbuilder-LayerSelectable-Function-Button-Field" layerName="'
									+ addedLayers[i].get("name") + '">';
							str += '<span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>';
							str += '</button>';
							str += '<button type="button" class="btn btn-default Gitbuilder-LayerSelectable-Function-Button Gitbuilder-LayerSelectable-Function-Button-Remove" layerName="'
									+ addedLayers[i].get("name") + '">';
							str += '<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>';
							str += '</button>';
							str += "</div>";

							str += '</div>';

							str += '</li>';

							$(that.element).prepend(str);
							that.layerLength += 1;
						}

						var selectee = $(that.options.filterToSelect, this.element[0]).find(".Gitbuilder-LayerSelectable-Function-Area");

						$(selectee, this.element[0]).each(function() {
							$(this).hide();
						});
						// ////////////////////////////////////////////////////////////////////////////////////////////////
					},

					_create : function() {
						var that = this;
						if (this.options.map instanceof ol.Map) {
							that.map = this.options.map;
							
							that.map.set("layerlist", that.element.attr('id'));
							console.log(that.map);

							that.layers = that.map.getLayers();

							that.layers.on('add', function() {
								console.log('add');
								var last = that.layers.getLength() - 1;
								if (that.layers.item(last).get("name") === undefined || that.layers.item(last).get("name") === null
										|| that.layers.item(last).get("name") === "") {
									that.layers.pop();
									console.error("no layer name");
									return;
								} else {
									if (that._isUniqueName(that.layers.item(last).get("name"))) {
										that.layers.pop();
										console.error("duplicated layer name");
									} else {
										that.layerLength += 1;
										that._redrawList();
									}
								}
							});
							that.layers.on('change:length', function() {
								console.log('change:length');
								if (that.layerLength > that.layers.getLength()) {
									console.log('deleted');
									that._redrawList();
								}
							});
						} else {
							console.error("option: map is not ol.Map");
							return;
						}

						this._redrawList();

						$(this.element).on("click", ".Gitbuilder-LayerSelectable-Function-Button-Show", function() {
							console.log($(this).attr("layerName"));
							var layer = that._getLayerByName($(this).attr("layerName"));
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

						$(this.element).on("click", ".Gitbuilder-LayerSelectable-Function-Button-Fit", function() {
							console.log($(this).attr("layerName"));
							var layer = that._getLayerByName($(this).attr("layerName"));
							that._zoomToLayerExtent(layer);
						});

						$(this.element).on("click", ".Gitbuilder-LayerSelectable-Function-Button-Setting", function() {
							console.log($(this).attr("layerName"));
							var layer = that._getLayerByName($(this).attr("layerName"));

						});

						$(this.element).on("click", ".Gitbuilder-LayerSelectable-Function-Button-Field", function() {
							console.log($(this).attr("layerName"));
							var layer = that._getLayerByName($(this).attr("layerName"));

						});

						$(this.element).on("click", ".Gitbuilder-LayerSelectable-Function-Button-Remove", function() {
							console.log($(this).attr("layerName"));
							var layer = that._getLayerByName($(this).attr("layerName"));
							that._removeConfirm(layer);
						});

						this._mouseInit();

						this._initToSelect();

						this._initToSort();

					},

					_zoomToLayerExtent : function(layer) {
						console.log("fit");
						if (!(layer instanceof ol.layer.Vector || (layer instanceof ol.layer.Image && layer.getSource() instanceof ol.source.ImageVector))) {
							console.error("not vector layer");
							return;
						}
						var source;
						if (layer instanceof ol.layer.Vector) {
							source = layer.getSource();
							this.map.getView().fit(source.getExtent(), this.map.getSize());
						} else if (layer instanceof ol.layer.Image && layer.getSource() instanceof ol.source.ImageVector) {
							source = layer.getSource().getSource();
							this.map.getView().fit(source.getExtent(), this.map.getSize());
						}

					},

					_setOption : function(key, value) {
						this._super(key, value);

						if (key === "handle") {
							this._setHandleClassName();
						}
					},

					_setHandleClassName : function() {
						var that = this;
						this._removeClass(this.element.find(".ui-sortable-handle"), "ui-sortable-handle");
						$.each(this.items, function() {
							that._addClass(this.instance.options.handle ? this.item.find(this.instance.options.handle) : this.item,
									"ui-sortable-handle");
						});
					},

					_destroy : function() {

						this._destroyToSelect();
						this._destroyToSort();
					},

					_mouseCapture : function(event, overrideHandle) {

						if ($(event.target).hasClass("GitBuilder-LayerSortable-Handle")
								|| $(event.target).parent().hasClass("GitBuilder-LayerSortable-Handle")) {
							var currentItem = null, validHandle = false, that = this;

							if (this.reverting) {
								return false;
							}

							if (this.options.disabled || this.options.type === "static") {
								return false;
							}

							// We have to refresh the items data once first
							this._refreshItems(event);

							// Find out if the clicked node (or one of its
							// parents)
							// is a actual item in this.items
							$(event.target).parents().each(function() {
								if ($.data(this, that.widgetName + "-item") === that) {
									currentItem = $(this);
									return false;
								}
							});
							if ($.data(event.target, that.widgetName + "-item") === that) {
								currentItem = $(event.target);
							}

							if (!currentItem) {
								return false;
							}
							if (this.options.handle && !overrideHandle) {
								$(this.options.handle, currentItem).find("*").addBack().each(function() {
									if (this === event.target) {
										validHandle = true;
									}
								});
								if (!validHandle) {
									return false;
								}
							}

							this.currentItem = currentItem;
							this._removeCurrentsFromItems();
							return true;
						} else if ($(event.target).hasClass("Gitbuilder-LayerSelectable-Filter")
								|| $(event.target).parent().hasClass("Gitbuilder-LayerSelectable-Filter")) {
							this._mouseStartSelectable(event);
							return false;
						}

					},
					_mouseStartSelectable : function(event) {

						// //////////////////////////////////////////////////////////////////////////////////
						var that = this, options = this.options;

						this.opos = [ event.pageX, event.pageY ];

						this.elementPos = $(this.element[0]).offset();

						this.selectees = $(options.filterToSelect, this.element[0]);

						this._trigger("startSelect", event);

						$(options.appendToSelect).append(this.helperToSelect);

						// position helper (lasso)
						this.helperToSelect.css({
							"left" : event.pageX,
							"top" : event.pageY,
							"width" : 0,
							"height" : 0
						});

						this.refreshToSelect();

						this.selectees.filter(".ui-selected").each(function() {
							var selectee = $.data(this, "selectable-item");
							selectee.startselected = true;
							if (!event.metaKey && !event.ctrlKey) {
								that._removeClass(selectee.$element, "ui-selected");
								selectee.selected = false;
								that._addClass(selectee.$element, "ui-unselecting");
								selectee.unselecting = true;

								// selectable UNSELECTING callback
								that._trigger("unselecting", event, {
									unselecting : selectee.element
								});
							}
						});

						$(event.target).parents().addBack().each(
								function() {
									var doSelect, selectee = $.data(this, "selectable-item");
									if (selectee) {
										doSelect = (!event.metaKey && !event.ctrlKey) || !selectee.$element.hasClass("ui-selected");
										that._removeClass(selectee.$element, doSelect ? "ui-unselecting" : "ui-selected")._addClass(
												selectee.$element, doSelect ? "ui-selecting" : "ui-unselecting");
										selectee.unselecting = !doSelect;
										selectee.selecting = doSelect;
										selectee.selected = doSelect;

										// selectable (UN)SELECTING callback
										if (doSelect) {
											that._trigger("selecting", event, {
												selecting : selectee.element
											});
										} else {
											that._trigger("unselecting", event, {
												unselecting : selectee.element
											});
										}
										return false;
									}
								});
						that._mouseDragSelectable(event);
						// //////////////////////////////////////////////////////////////////////////////////
					},
					_mouseStart : function(event, overrideHandle, noActivation) {

						var i, body, o = this.options;

						this.currentContainer = this;

						// We only need to call refreshPositions, because
						// the
						// refreshItems call has been moved to
						// mouseCapture
						this.refreshPositions();

						// Create and append the visible helper
						this.helperToSort = this._createHelper(event);

						// Cache the helper size
						this._cacheHelperProportions();

						/*
						 * - Position generation - This block generates
						 * everything position related - it's the core of
						 * draggables.
						 */

						// Cache the margins of the original element
						this._cacheMargins();

						// Get the next scrolling parent
						this.scrollParent = this.helperToSort.scrollParent();

						// The element's absolute position on the page minus
						// margins
						this.offset = this.currentItem.offset();
						this.offset = {
							top : this.offset.top - this.margins.top,
							left : this.offset.left - this.margins.left
						};

						$.extend(this.offset, {
							click : { // Where the click happened,
								// relative to
								// the element
								left : event.pageX - this.offset.left,
								top : event.pageY - this.offset.top
							},
							parent : this._getParentOffset(),

							// This is a relative to absolute position minus
							// the
							// actual position calculation -
							// only used for relative positioned helper
							relative : this._getRelativeOffset()
						});

						// Only after we got the offset, we can change the
						// helper's position to absolute
						// TODO: Still need to figure out a way to make
						// relative
						// sorting possible
						this.helperToSort.css("position", "absolute");
						this.cssPosition = this.helperToSort.css("position");

						// Generate the original position
						this.originalPosition = this._generatePosition(event);
						this.originalPageX = event.pageX;
						this.originalPageY = event.pageY;

						// Adjust the mouse offset relative to the helper if
						// "cursorAt" is supplied
						(o.cursorAt && this._adjustOffsetFromHelper(o.cursorAt));

						// Cache the former DOM position
						this.domPosition = {
							prev : this.currentItem.prev()[0],
							parent : this.currentItem.parent()[0]
						};

						// If the helper is not the original, hide the
						// original
						// so it's not playing any role during
						// the drag, won't cause anything bad this way
						if (this.helperToSort[0] !== this.currentItem[0]) {
							this.currentItem.hide();
						}

						// Create the placeholder
						this._createPlaceholder();

						// Set a containment if given in the options
						if (o.containment) {
							this._setContainment();
						}

						if (o.cursor && o.cursor !== "auto") { // cursor
							// option
							body = this.document.find("body");

							// Support: IE
							this.storedCursor = body.css("cursor");
							body.css("cursor", o.cursor);

							this.storedStylesheet = $("<style>*{ cursor: " + o.cursor + " !important; }</style>").appendTo(body);
						}

						if (o.opacity) { // opacity option
							if (this.helperToSort.css("opacity")) {
								this._storedOpacity = this.helperToSort.css("opacity");
							}
							this.helperToSort.css("opacity", o.opacity);
						}

						if (o.zIndex) { // zIndex option
							if (this.helperToSort.css("zIndex")) {
								this._storedZIndex = this.helperToSort.css("zIndex");
							}
							this.helperToSort.css("zIndex", o.zIndex);
						}

						// Prepare scrolling
						if (this.scrollParent[0] !== this.document[0] && this.scrollParent[0].tagName !== "HTML") {
							this.overflowOffset = this.scrollParent.offset();
						}

						// Call callbacks
						this._trigger("start", event, this._uiHash());

						// Recache the helper size
						if (!this._preserveHelperProportions) {
							this._cacheHelperProportions();
						}

						// Post "activate" events to possible containers
						if (!noActivation) {
							for (i = this.containers.length - 1; i >= 0; i--) {
								this.containers[i]._trigger("activate", event, this._uiHash(this));
							}
						}

						// Prepare possible droppables
						if ($.ui.ddmanager) {
							$.ui.ddmanager.current = this;
						}

						if ($.ui.ddmanager && !o.dropBehaviour) {
							$.ui.ddmanager.prepareOffsets(this, event);
						}

						this.dragging = true;

						this._addClass(this.helperToSort, "ui-sortable-helper");

						// Execute the drag once - this causes the helper
						// not to
						// be visiblebefore getting its
						// correct position
						this._mouseDrag(event);

						return true;

					},

					_mouseDragSelectable : function(event) {
						// /////////////////////////////////////////////////////////////////////////////////////////////////////////
						this.dragged = true;

						if (this.options.disabled) {
							return;
						}

						var tmp, that = this, options = this.options, x1 = this.opos[0], y1 = this.opos[1], x2 = event.pageX, y2 = event.pageY;

						if (x1 > x2) {
							tmp = x2;
							x2 = x1;
							x1 = tmp;
						}
						if (y1 > y2) {
							tmp = y2;
							y2 = y1;
							y1 = tmp;
						}
						this.helperToSelect.css({
							left : x1,
							top : y1,
							width : x2 - x1,
							height : y2 - y1
						});

						this.selectees.each(function() {
							var selectee = $.data(this, "selectable-item"), hit = false, offset = {};

							// prevent helper from being selected if
							// appendTo:
							// selectable
							if (!selectee || selectee.element === that.element[0]) {
								return;
							}

							offset.left = selectee.left + that.elementPos.left;
							offset.right = selectee.right + that.elementPos.left;
							offset.top = selectee.top + that.elementPos.top;
							offset.bottom = selectee.bottom + that.elementPos.top;

							if (options.toleranceToSelect === "touch") {
								hit = (!(offset.left > x2 || offset.right < x1 || offset.top > y2 || offset.bottom < y1));
							} else if (options.toleranceToSelect === "fit") {
								hit = (offset.left > x1 && offset.right < x2 && offset.top > y1 && offset.bottom < y2);
							}

							if (hit) {

								// SELECT
								if (selectee.selected) {
									that._removeClass(selectee.$element, "ui-selected");
									selectee.selected = false;
								}
								if (selectee.unselecting) {
									that._removeClass(selectee.$element, "ui-unselecting");
									selectee.unselecting = false;
								}
								if (!selectee.selecting) {
									that._addClass(selectee.$element, "ui-selecting");
									selectee.selecting = true;

									// selectable SELECTING callback
									that._trigger("selecting", event, {
										selecting : selectee.element
									});
								}
							} else {

								// UNSELECT
								if (selectee.selecting) {
									if ((event.metaKey || event.ctrlKey) && selectee.startselected) {
										that._removeClass(selectee.$element, "ui-selecting");
										selectee.selecting = false;
										that._addClass(selectee.$element, "ui-selected");
										selectee.selected = true;
									} else {
										that._removeClass(selectee.$element, "ui-selecting");
										selectee.selecting = false;
										if (selectee.startselected) {
											that._addClass(selectee.$element, "ui-unselecting");
											selectee.unselecting = true;
										}

										// selectable UNSELECTING callback
										that._trigger("unselecting", event, {
											unselecting : selectee.element
										});
									}
								}
								if (selectee.selected) {
									if (!event.metaKey && !event.ctrlKey && !selectee.startselected) {
										that._removeClass(selectee.$element, "ui-selected");
										selectee.selected = false;

										that._addClass(selectee.$element, "ui-unselecting");
										selectee.unselecting = true;

										// selectable UNSELECTING callback
										that._trigger("unselecting", event, {
											unselecting : selectee.element
										});
									}
								}
							}
						});
						// /////////////////////////////////////////////////////////////////////////////////////////////////////////
						that._mouseStopSelectable(event);
						return false;
					},
					_mouseStopSelectable : function(event) {
						// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
						var that = this;

						this.dragged = false;

						$(".ui-unselecting", this.element[0]).each(function() {
							var selectee = $.data(this, "selectable-item");
							that._removeClass(selectee.$element, "ui-unselecting");
							selectee.unselecting = false;
							selectee.startselected = false;
							that._trigger("unselected", event, {
								unselected : selectee.element
							});
						});
						$(".ui-selecting", this.element[0]).each(function() {
							var selectee = $.data(this, "selectable-item");
							that._removeClass(selectee.$element, "ui-selecting")._addClass(selectee.$element, "ui-selected");
							selectee.selecting = false;
							selectee.selected = true;
							selectee.startselected = true;
							that._trigger("selected", event, {
								selected : selectee.element
							});
						});

						this.selectedLayers.clear();
						var selectee = $(".ui-selectee", this.element[0]).find(".Gitbuilder-LayerSelectable-Function-Area");

						$(selectee, this.element[0]).each(function() {
							$(this).hide();
						});

						$(".ui-selected", this.element[0]).each(function() {
							var selectee = $.data(this, "selectable-item");
							var name = $(selectee.element).attr("layerName");
							var layer = that._getLayerByName(name);

							if (layer) {
								that.selectedLayers.push(layer);
							}

							$(this).find(".Gitbuilder-LayerSelectable-Function-Area").show();

							var layer = that._getLayerByName($(this).attr("layerName"));
							var show = layer.getVisible();
							if (show) {
								$(this).find(".glyphicon-eye-open").show();
								$(this).find(".glyphicon-eye-close").hide();
							} else {
								$(this).find(".glyphicon-eye-close").show();
								$(this).find(".glyphicon-eye-open").hide();
							}

						});

						// console.log(this.selectedLayers);

						this._trigger("stopSelect", event, {
							selectedLayers : that.selectedLayers
						});

						this.helperToSelect.remove();

						// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

						return false;
					},
					_mouseDrag : function(event) {

						// if
						// ($(event.target).hasClass("GitBuilder-LayerSortable-Handle")
						// ||
						// $(event.target).parent().hasClass("GitBuilder-LayerSortable-Handle"))
						// {

						var i, item, itemElement, intersection, o = this.options, scrolled = false;

						// Compute the helpers position
						this.position = this._generatePosition(event);
						this.positionAbs = this._convertPositionTo("absolute");

						if (!this.lastPositionAbs) {
							this.lastPositionAbs = this.positionAbs;
						}

						// Do scrolling
						if (this.options.scroll) {
							if (this.scrollParent[0] !== this.document[0] && this.scrollParent[0].tagName !== "HTML") {

								if ((this.overflowOffset.top + this.scrollParent[0].offsetHeight) - event.pageY < o.scrollSensitivity) {
									this.scrollParent[0].scrollTop = scrolled = this.scrollParent[0].scrollTop + o.scrollSpeed;
								} else if (event.pageY - this.overflowOffset.top < o.scrollSensitivity) {
									this.scrollParent[0].scrollTop = scrolled = this.scrollParent[0].scrollTop - o.scrollSpeed;
								}

								if ((this.overflowOffset.left + this.scrollParent[0].offsetWidth) - event.pageX < o.scrollSensitivity) {
									this.scrollParent[0].scrollLeft = scrolled = this.scrollParent[0].scrollLeft + o.scrollSpeed;
								} else if (event.pageX - this.overflowOffset.left < o.scrollSensitivity) {
									this.scrollParent[0].scrollLeft = scrolled = this.scrollParent[0].scrollLeft - o.scrollSpeed;
								}

							} else {

								if (event.pageY - this.document.scrollTop() < o.scrollSensitivity) {
									scrolled = this.document.scrollTop(this.document.scrollTop() - o.scrollSpeed);
								} else if (this.window.height() - (event.pageY - this.document.scrollTop()) < o.scrollSensitivity) {
									scrolled = this.document.scrollTop(this.document.scrollTop() + o.scrollSpeed);
								}

								if (event.pageX - this.document.scrollLeft() < o.scrollSensitivity) {
									scrolled = this.document.scrollLeft(this.document.scrollLeft() - o.scrollSpeed);
								} else if (this.window.width() - (event.pageX - this.document.scrollLeft()) < o.scrollSensitivity) {
									scrolled = this.document.scrollLeft(this.document.scrollLeft() + o.scrollSpeed);
								}

							}

							if (scrolled !== false && $.ui.ddmanager && !o.dropBehaviour) {
								$.ui.ddmanager.prepareOffsets(this, event);
							}
						}

						// Regenerate the absolute position used for
						// position
						// checks
						this.positionAbs = this._convertPositionTo("absolute");

						// Set the helper position
						if (!this.options.axis || this.options.axis !== "y") {
							this.helperToSort[0].style.left = this.position.left + "px";
						}
						if (!this.options.axis || this.options.axis !== "x") {
							this.helperToSort[0].style.top = this.position.top + "px";
						}

						// Rearrange
						for (i = this.items.length - 1; i >= 0; i--) {

							// Cache variables and intersection, continue if
							// no
							// intersection
							item = this.items[i];
							itemElement = item.item[0];
							intersection = this._intersectsWithPointer(item);
							if (!intersection) {
								continue;
							}

							// Only put the placeholder inside the current
							// Container, skip all
							// items from other containers. This works
							// because
							// when moving
							// an item from one container to another the
							// currentContainer is switched before the
							// placeholder is moved.
							//
							// Without this, moving items in "sub-sortables"
							// can
							// cause
							// the placeholder to jitter between the outer
							// and
							// inner container.
							if (item.instance !== this.currentContainer) {
								continue;
							}

							// Cannot intersect with itself
							// no useless actions that have been done before
							// no action if the item moved is the parent of
							// the
							// item checked
							if (itemElement !== this.currentItem[0] && this.placeholder[intersection === 1 ? "next" : "prev"]()[0] !== itemElement
									&& !$.contains(this.placeholder[0], itemElement)
									&& (this.options.type === "semi-dynamic" ? !$.contains(this.element[0], itemElement) : true)) {

								this.direction = intersection === 1 ? "down" : "up";

								if (this.options.toleranceToSort === "pointer" || this._intersectsWithSides(item)) {
									this._rearrange(event, item);
								} else {
									break;
								}
								this._trigger("change", event, this._uiHash());
								break;
							}
						}

						// Post events to containers
						this._contactContainers(event);

						// Interconnect with droppables
						if ($.ui.ddmanager) {
							$.ui.ddmanager.drag(this, event);
						}

						// Call callbacks
						this._trigger("sort", event, this._uiHash());

						this.lastPositionAbs = this.positionAbs;

						return false;

					},

					_mouseStop : function(event, noPropagation) {

						if (!event) {
							return;
						}

						// If we are using droppables, inform the manager
						// about
						// the drop
						if ($.ui.ddmanager && !this.options.dropBehaviour) {
							$.ui.ddmanager.drop(this, event);
						}

						if (this.options.revert) {
							var that = this, cur = this.placeholder.offset(), axis = this.options.axis, animation = {};

							if (!axis || axis === "x") {
								animation.left = cur.left - this.offset.parent.left - this.margins.left
										+ (this.offsetParent[0] === this.document[0].body ? 0 : this.offsetParent[0].scrollLeft);
							}
							if (!axis || axis === "y") {
								animation.top = cur.top - this.offset.parent.top - this.margins.top
										+ (this.offsetParent[0] === this.document[0].body ? 0 : this.offsetParent[0].scrollTop);
							}
							this.reverting = true;
							$(this.helperToSort).animate(animation, parseInt(this.options.revert, 10) || 500, function() {
								that._clear(event);
							});
						} else {
							this._clear(event, noPropagation);
						}

						return false;

					},

					cancel : function() {

						if (this.dragging) {

							this._mouseUp(new $.Event("mouseup", {
								target : null
							}));

							if (this.options.helperToSort === "original") {
								this.currentItem.css(this._storedCSS);
								this._removeClass(this.currentItem, "ui-sortable-helper");
							} else {
								this.currentItem.show();
							}

							// Post deactivating events to containers
							for (var i = this.containers.length - 1; i >= 0; i--) {
								this.containers[i]._trigger("deactivate", null, this._uiHash(this));
								if (this.containers[i].containerCache.over) {
									this.containers[i]._trigger("out", null, this._uiHash(this));
									this.containers[i].containerCache.over = 0;
								}
							}

						}

						if (this.placeholder) {

							// $(this.placeholder[0]).remove(); would have been
							// the jQuery way - unfortunately,
							// it unbinds ALL events from the original node!
							if (this.placeholder[0].parentNode) {
								this.placeholder[0].parentNode.removeChild(this.placeholder[0]);
							}
							if (this.options.helperToSort !== "original" && this.helperToSort && this.helperToSort[0].parentNode) {
								this.helperToSort.remove();
							}

							$.extend(this, {
								helper : null,
								dragging : false,
								reverting : false,
								_noFinalSort : null
							});

							if (this.domPosition.prev) {
								$(this.domPosition.prev).after(this.currentItem);
							} else {
								$(this.domPosition.parent).prepend(this.currentItem);
							}
						}

						return this;

					},

					serialize : function(o) {

						var items = this._getItemsAsjQuery(o && o.connected), str = [];
						o = o || {};

						$(items).each(function() {
							var res = ($(o.item || this).attr(o.attribute || "id") || "").match(o.expression || (/(.+)[\-=_](.+)/));
							if (res) {
								str.push((o.key || res[1] + "[]") + "=" + (o.key && o.expression ? res[1] : res[2]));
							}
						});

						if (!str.length && o.key) {
							str.push(o.key + "=");
						}

						return str.join("&");

					},

					toArray : function(o) {

						var items = this._getItemsAsjQuery(o && o.connected), ret = [];

						o = o || {};

						items.each(function() {
							ret.push($(o.item || this).attr(o.attribute || "id") || "");
						});
						return ret;

					},

					/* Be careful with the following core functions */
					_intersectsWith : function(item) {

						var x1 = this.positionAbs.left, x2 = x1 + this.helperProportions.width, y1 = this.positionAbs.top, y2 = y1
								+ this.helperProportions.height, l = item.left, r = l + item.width, t = item.top, b = t + item.height, dyClick = this.offset.click.top, dxClick = this.offset.click.left, isOverElementHeight = (this.options.axis === "x")
								|| ((y1 + dyClick) > t && (y1 + dyClick) < b), isOverElementWidth = (this.options.axis === "y")
								|| ((x1 + dxClick) > l && (x1 + dxClick) < r), isOverElement = isOverElementHeight && isOverElementWidth;

						if (this.options.toleranceToSort === "pointer"
								|| this.options.forcePointerForContainers
								|| (this.options.toleranceToSort !== "pointer" && this.helperProportions[this.floating ? "width" : "height"] > item[this.floating ? "width"
										: "height"])) {
							return isOverElement;
						} else {

							return (l < x1 + (this.helperProportions.width / 2) && // Right
							// Half
							x2 - (this.helperProportions.width / 2) < r && // Left
							// Half
							t < y1 + (this.helperProportions.height / 2) && // Bottom
							// Half
							y2 - (this.helperProportions.height / 2) < b); // Top
							// Half

						}
					},

					_intersectsWithPointer : function(item) {
						var verticalDirection, horizontalDirection, isOverElementHeight = (this.options.axis === "x")
								|| this._isOverAxis(this.positionAbs.top + this.offset.click.top, item.top, item.height), isOverElementWidth = (this.options.axis === "y")
								|| this._isOverAxis(this.positionAbs.left + this.offset.click.left, item.left, item.width), isOverElement = isOverElementHeight
								&& isOverElementWidth;

						if (!isOverElement) {
							return false;
						}

						verticalDirection = this._getDragVerticalDirection();
						horizontalDirection = this._getDragHorizontalDirection();

						return this.floating ? ((horizontalDirection === "right" || verticalDirection === "down") ? 2 : 1)
								: (verticalDirection && (verticalDirection === "down" ? 2 : 1));

					},

					_intersectsWithSides : function(item) {

						var isOverBottomHalf = this._isOverAxis(this.positionAbs.top + this.offset.click.top, item.top + (item.height / 2),
								item.height), isOverRightHalf = this._isOverAxis(this.positionAbs.left + this.offset.click.left, item.left
								+ (item.width / 2), item.width), verticalDirection = this._getDragVerticalDirection(), horizontalDirection = this
								._getDragHorizontalDirection();

						if (this.floating && horizontalDirection) {
							return ((horizontalDirection === "right" && isOverRightHalf) || (horizontalDirection === "left" && !isOverRightHalf));
						} else {
							return verticalDirection
									&& ((verticalDirection === "down" && isOverBottomHalf) || (verticalDirection === "up" && !isOverBottomHalf));
						}

					},

					_getDragVerticalDirection : function() {
						var delta = this.positionAbs.top - this.lastPositionAbs.top;
						return delta !== 0 && (delta > 0 ? "down" : "up");
					},

					_getDragHorizontalDirection : function() {
						var delta = this.positionAbs.left - this.lastPositionAbs.left;
						return delta !== 0 && (delta > 0 ? "right" : "left");
					},

					refreshToSort : function(event) {
						this._refreshItems(event);
						this._setHandleClassName();
						this.refreshPositions();
						return this;
					},

					_connectWith : function() {
						var options = this.options;
						return options.connectWith.constructor === String ? [ options.connectWith ] : options.connectWith;
					},

					_getItemsAsjQuery : function(connected) {

						var i, j, cur, inst, items = [], queries = [], connectWith = this._connectWith();

						if (connectWith && connected) {
							for (i = connectWith.length - 1; i >= 0; i--) {
								cur = $(connectWith[i], this.document[0]);
								for (j = cur.length - 1; j >= 0; j--) {
									inst = $.data(cur[j], this.widgetFullName);
									if (inst && inst !== this && !inst.options.disabled) {
										queries.push([
												$.isFunction(inst.options.items) ? inst.options.items.call(inst.element) : $(inst.options.items,
														inst.element).not(".ui-sortable-helper").not(".ui-sortable-placeholder"), inst ]);
									}
								}
							}
						}

						queries.push([ $.isFunction(this.options.items) ? this.options.items.call(this.element, null, {
							options : this.options,
							item : this.currentItem
						}) : $(this.options.items, this.element).not(".ui-sortable-helper").not(".ui-sortable-placeholder"), this ]);

						function addItems() {
							items.push(this);
						}
						for (i = queries.length - 1; i >= 0; i--) {
							queries[i][0].each(addItems);
						}

						return $(items);

					},

					_removeCurrentsFromItems : function() {

						var list = this.currentItem.find(":data(" + this.widgetName + "-item)");

						this.items = $.grep(this.items, function(item) {
							for (var j = 0; j < list.length; j++) {
								if (list[j] === item.item[0]) {
									return false;
								}
							}
							return true;
						});

					},

					_refreshItems : function(event) {

						this.items = [];
						this.containers = [ this ];

						var i, j, cur, inst, targetData, _queries, item, queriesLength, items = this.items, queries = [ [
								$.isFunction(this.options.items) ? this.options.items.call(this.element[0], event, {
									item : this.currentItem
								}) : $(this.options.items, this.element), this ] ], connectWith = this._connectWith();

						// Shouldn't be run the first time through due to
						// massive slow-down
						if (connectWith && this.ready) {
							for (i = connectWith.length - 1; i >= 0; i--) {
								cur = $(connectWith[i], this.document[0]);
								for (j = cur.length - 1; j >= 0; j--) {
									inst = $.data(cur[j], this.widgetFullName);
									if (inst && inst !== this && !inst.options.disabled) {
										queries.push([ $.isFunction(inst.options.items) ? inst.options.items.call(inst.element[0], event, {
											item : this.currentItem
										}) : $(inst.options.items, inst.element), inst ]);
										this.containers.push(inst);
									}
								}
							}
						}

						for (i = queries.length - 1; i >= 0; i--) {
							targetData = queries[i][1];
							_queries = queries[i][0];

							for (j = 0, queriesLength = _queries.length; j < queriesLength; j++) {
								item = $(_queries[j]);

								// Data for target checking (mouse manager)
								item.data(this.widgetName + "-item", targetData);

								items.push({
									item : item,
									instance : targetData,
									width : 0,
									height : 0,
									left : 0,
									top : 0
								});
							}
						}

					},

					refreshPositions : function(fast) {

						// Determine whether items are being displayed
						// horizontally
						this.floating = this.items.length ? this.options.axis === "x" || this._isFloating(this.items[0].item) : false;

						// This has to be redone because due to the item being
						// moved out/into the offsetParent,
						// the offsetParent's position will change
						if (this.offsetParent && this.helperToSort) {
							this.offset.parent = this._getParentOffset();
						}

						var i, item, t, p;

						for (i = this.items.length - 1; i >= 0; i--) {
							item = this.items[i];

							// We ignore calculating positions of all connected
							// containers when we're not over them
							if (item.instance !== this.currentContainer && this.currentContainer && item.item[0] !== this.currentItem[0]) {
								continue;
							}

							t = this.options.toleranceElement ? $(this.options.toleranceElement, item.item) : item.item;

							if (!fast) {
								item.width = t.outerWidth();
								item.height = t.outerHeight();
							}

							p = t.offset();
							item.left = p.left;
							item.top = p.top;
						}

						if (this.options.custom && this.options.custom.refreshContainers) {
							this.options.custom.refreshContainers.call(this);
						} else {
							for (i = this.containers.length - 1; i >= 0; i--) {
								p = this.containers[i].element.offset();
								this.containers[i].containerCache.left = p.left;
								this.containers[i].containerCache.top = p.top;
								this.containers[i].containerCache.width = this.containers[i].element.outerWidth();
								this.containers[i].containerCache.height = this.containers[i].element.outerHeight();
							}
						}

						return this;
					},

					_createPlaceholder : function(that) {
						that = that || this;
						var className, o = that.options;

						if (!o.placeholder || o.placeholder.constructor === String) {
							className = o.placeholder;
							o.placeholder = {
								element : function() {

									var nodeName = that.currentItem[0].nodeName.toLowerCase(), element = $("<" + nodeName + ">", that.document[0]);

									that._addClass(element, "ui-sortable-placeholder", className || that.currentItem[0].className)._removeClass(
											element, "ui-sortable-helper");

									if (nodeName === "tbody") {
										that._createTrPlaceholder(that.currentItem.find("tr").eq(0), $("<tr>", that.document[0]).appendTo(element));
									} else if (nodeName === "tr") {
										that._createTrPlaceholder(that.currentItem, element);
									} else if (nodeName === "img") {
										element.attr("src", that.currentItem.attr("src"));
									}

									if (!className) {
										element.css("visibility", "hidden");
									}

									return element;
								},
								update : function(container, p) {

									// 1. If a className is set as 'placeholder
									// option, we don't force sizes -
									// the class is responsible for that
									// 2. The option 'forcePlaceholderSize can
									// be enabled to force it even if a
									// class name is specified
									if (className && !o.forcePlaceholderSize) {
										return;
									}

									// If the element doesn't have a actual
									// height by itself (without styles coming
									// from a stylesheet), it receives the
									// inline height from the dragged item
									if (!p.height()) {
										p.height(that.currentItem.innerHeight() - parseInt(that.currentItem.css("paddingTop") || 0, 10)
												- parseInt(that.currentItem.css("paddingBottom") || 0, 10));
									}
									if (!p.width()) {
										p.width(that.currentItem.innerWidth() - parseInt(that.currentItem.css("paddingLeft") || 0, 10)
												- parseInt(that.currentItem.css("paddingRight") || 0, 10));
									}
								}
							};
						}

						// Create the placeholder
						that.placeholder = $(o.placeholder.element.call(that.element, that.currentItem));

						// Append it after the actual current item
						that.currentItem.after(that.placeholder);

						// Update the size of the placeholder (TODO: Logic to
						// fuzzy, see line 316/317)
						o.placeholder.update(that, that.placeholder);

					},

					_createTrPlaceholder : function(sourceTr, targetTr) {
						var that = this;

						sourceTr.children().each(function() {
							$("<td>&#160;</td>", that.document[0]).attr("colspan", $(this).attr("colspan") || 1).appendTo(targetTr);
						});
					},

					_contactContainers : function(event) {
						var i, j, dist, itemWithLeastDistance, posProperty, sizeProperty, cur, nearBottom, floating, axis, innermostContainer = null, innermostIndex = null;

						// Get innermost container that intersects with item
						for (i = this.containers.length - 1; i >= 0; i--) {

							// Never consider a container that's located within
							// the item itself
							if ($.contains(this.currentItem[0], this.containers[i].element[0])) {
								continue;
							}

							if (this._intersectsWith(this.containers[i].containerCache)) {

								// If we've already found a container and it's
								// more "inner" than this, then continue
								if (innermostContainer && $.contains(this.containers[i].element[0], innermostContainer.element[0])) {
									continue;
								}

								innermostContainer = this.containers[i];
								innermostIndex = i;

							} else {

								// container doesn't intersect. trigger "out"
								// event if necessary
								if (this.containers[i].containerCache.over) {
									this.containers[i]._trigger("out", event, this._uiHash(this));
									this.containers[i].containerCache.over = 0;
								}
							}

						}

						// If no intersecting containers found, return
						if (!innermostContainer) {
							return;
						}

						// Move the item into the container if it's not there
						// already
						if (this.containers.length === 1) {
							if (!this.containers[innermostIndex].containerCache.over) {
								this.containers[innermostIndex]._trigger("over", event, this._uiHash(this));
								this.containers[innermostIndex].containerCache.over = 1;
							}
						} else {

							// When entering a new container, we will find the
							// item with the least distance and
							// append our item near it
							dist = 10000;
							itemWithLeastDistance = null;
							floating = innermostContainer.floating || this._isFloating(this.currentItem);
							posProperty = floating ? "left" : "top";
							sizeProperty = floating ? "width" : "height";
							axis = floating ? "pageX" : "pageY";

							for (j = this.items.length - 1; j >= 0; j--) {
								if (!$.contains(this.containers[innermostIndex].element[0], this.items[j].item[0])) {
									continue;
								}
								if (this.items[j].item[0] === this.currentItem[0]) {
									continue;
								}

								cur = this.items[j].item.offset()[posProperty];
								nearBottom = false;
								if (event[axis] - cur > this.items[j][sizeProperty] / 2) {
									nearBottom = true;
								}

								if (Math.abs(event[axis] - cur) < dist) {
									dist = Math.abs(event[axis] - cur);
									itemWithLeastDistance = this.items[j];
									this.direction = nearBottom ? "up" : "down";
								}
							}

							// Check if dropOnEmpty is enabled
							if (!itemWithLeastDistance && !this.options.dropOnEmpty) {
								return;
							}

							if (this.currentContainer === this.containers[innermostIndex]) {
								if (!this.currentContainer.containerCache.over) {
									this.containers[innermostIndex]._trigger("over", event, this._uiHash());
									this.currentContainer.containerCache.over = 1;
								}
								return;
							}

							itemWithLeastDistance ? this._rearrange(event, itemWithLeastDistance, null, true) : this._rearrange(event, null,
									this.containers[innermostIndex].element, true);
							this._trigger("change", event, this._uiHash());
							this.containers[innermostIndex]._trigger("change", event, this._uiHash(this));
							this.currentContainer = this.containers[innermostIndex];

							// Update the placeholder
							this.options.placeholder.update(this.currentContainer, this.placeholder);

							this.containers[innermostIndex]._trigger("over", event, this._uiHash(this));
							this.containers[innermostIndex].containerCache.over = 1;
						}

					},

					_createHelper : function(event) {

						var o = this.options, helper = $.isFunction(o.helper) ? $(o.helper.apply(this.element[0], [ event, this.currentItem ]))
								: (o.helper === "clone" ? this.currentItem.clone() : this.currentItem);

						// Add the helper to the DOM if that didn't happen
						// already
						if (!helper.parents("body").length) {
							$(o.appendToSort !== "parent" ? o.appendToSort : this.currentItem[0].parentNode)[0].appendChild(helper[0]);
						}

						if (helper[0] === this.currentItem[0]) {
							this._storedCSS = {
								width : this.currentItem[0].style.width,
								height : this.currentItem[0].style.height,
								position : this.currentItem.css("position"),
								top : this.currentItem.css("top"),
								left : this.currentItem.css("left")
							};
						}

						if (!helper[0].style.width || o.forceHelperSize) {
							helper.width(this.currentItem.width());
						}
						if (!helper[0].style.height || o.forceHelperSize) {
							helper.height(this.currentItem.height());
						}

						return helper;

					},

					_adjustOffsetFromHelper : function(obj) {
						if (typeof obj === "string") {
							obj = obj.split(" ");
						}
						if ($.isArray(obj)) {
							obj = {
								left : +obj[0],
								top : +obj[1] || 0
							};
						}
						if ("left" in obj) {
							this.offset.click.left = obj.left + this.margins.left;
						}
						if ("right" in obj) {
							this.offset.click.left = this.helperProportions.width - obj.right + this.margins.left;
						}
						if ("top" in obj) {
							this.offset.click.top = obj.top + this.margins.top;
						}
						if ("bottom" in obj) {
							this.offset.click.top = this.helperProportions.height - obj.bottom + this.margins.top;
						}
					},

					_getParentOffset : function() {

						// Get the offsetParent and cache its position
						this.offsetParent = this.helperToSort.offsetParent();
						var po = this.offsetParent.offset();

						// This is a special case where we need to modify a
						// offset calculated on start, since the
						// following happened:
						// 1. The position of the helper is absolute, so it's
						// position is calculated based on the
						// next positioned parent
						// 2. The actual offset parent is a child of the scroll
						// parent, and the scroll parent isn't
						// the document, which means that the scroll is included
						// in the initial calculation of the
						// offset of the parent, and never recalculated upon
						// drag
						if (this.cssPosition === "absolute" && this.scrollParent[0] !== this.document[0]
								&& $.contains(this.scrollParent[0], this.offsetParent[0])) {
							po.left += this.scrollParent.scrollLeft();
							po.top += this.scrollParent.scrollTop();
						}

						// This needs to be actually done for all browsers,
						// since pageX/pageY includes this
						// information with an ugly IE fix
						if (this.offsetParent[0] === this.document[0].body
								|| (this.offsetParent[0].tagName && this.offsetParent[0].tagName.toLowerCase() === "html" && $.ui.ie)) {
							po = {
								top : 0,
								left : 0
							};
						}

						return {
							top : po.top + (parseInt(this.offsetParent.css("borderTopWidth"), 10) || 0),
							left : po.left + (parseInt(this.offsetParent.css("borderLeftWidth"), 10) || 0)
						};

					},

					_getRelativeOffset : function() {

						if (this.cssPosition === "relative") {
							var p = this.currentItem.position();
							return {
								top : p.top - (parseInt(this.helperToSort.css("top"), 10) || 0) + this.scrollParent.scrollTop(),
								left : p.left - (parseInt(this.helperToSort.css("left"), 10) || 0) + this.scrollParent.scrollLeft()
							};
						} else {
							return {
								top : 0,
								left : 0
							};
						}

					},

					_cacheMargins : function() {
						this.margins = {
							left : (parseInt(this.currentItem.css("marginLeft"), 10) || 0),
							top : (parseInt(this.currentItem.css("marginTop"), 10) || 0)
						};
					},

					_cacheHelperProportions : function() {
						this.helperProportions = {
							width : this.helperToSort.outerWidth(),
							height : this.helperToSort.outerHeight()
						};
					},

					_setContainment : function() {

						var ce, co, over, o = this.options;
						if (o.containment === "parent") {
							o.containment = this.helperToSort[0].parentNode;
						}
						if (o.containment === "document" || o.containment === "window") {
							this.containment = [
									0 - this.offset.relative.left - this.offset.parent.left,
									0 - this.offset.relative.top - this.offset.parent.top,
									o.containment === "document" ? this.document.width() : this.window.width() - this.helperProportions.width
											- this.margins.left,
									(o.containment === "document" ? (this.document.height() || document.body.parentNode.scrollHeight) : this.window
											.height()
											|| this.document[0].body.parentNode.scrollHeight)
											- this.helperProportions.height - this.margins.top ];
						}

						if (!(/^(document|window|parent)$/).test(o.containment)) {
							ce = $(o.containment)[0];
							co = $(o.containment).offset();
							over = ($(ce).css("overflow") !== "hidden");

							this.containment = [
									co.left + (parseInt($(ce).css("borderLeftWidth"), 10) || 0) + (parseInt($(ce).css("paddingLeft"), 10) || 0)
											- this.margins.left,
									co.top + (parseInt($(ce).css("borderTopWidth"), 10) || 0) + (parseInt($(ce).css("paddingTop"), 10) || 0)
											- this.margins.top,
									co.left + (over ? Math.max(ce.scrollWidth, ce.offsetWidth) : ce.offsetWidth)
											- (parseInt($(ce).css("borderLeftWidth"), 10) || 0) - (parseInt($(ce).css("paddingRight"), 10) || 0)
											- this.helperProportions.width - this.margins.left,
									co.top + (over ? Math.max(ce.scrollHeight, ce.offsetHeight) : ce.offsetHeight)
											- (parseInt($(ce).css("borderTopWidth"), 10) || 0) - (parseInt($(ce).css("paddingBottom"), 10) || 0)
											- this.helperProportions.height - this.margins.top ];
						}

					},

					_convertPositionTo : function(d, pos) {

						if (!pos) {
							pos = this.position;
						}
						var mod = d === "absolute" ? 1 : -1, scroll = this.cssPosition === "absolute"
								&& !(this.scrollParent[0] !== this.document[0] && $.contains(this.scrollParent[0], this.offsetParent[0])) ? this.offsetParent
								: this.scrollParent, scrollIsRootNode = (/(html|body)/i).test(scroll[0].tagName);

						return {
							top : (

							// The absolute mouse position
							pos.top +

							// Only for relative positioned nodes: Relative
							// offset from element to offset parent
							this.offset.relative.top * mod +

							// The offsetParent's offset without borders (offset
							// + border)
							this.offset.parent.top * mod - ((this.cssPosition === "fixed" ? -this.scrollParent.scrollTop() : (scrollIsRootNode ? 0
									: scroll.scrollTop())) * mod)),
							left : (

							// The absolute mouse position
							pos.left +

							// Only for relative positioned nodes: Relative
							// offset from element to offset parent
							this.offset.relative.left * mod +

							// The offsetParent's offset without borders (offset
							// + border)
							this.offset.parent.left * mod - ((this.cssPosition === "fixed" ? -this.scrollParent.scrollLeft() : scrollIsRootNode ? 0
									: scroll.scrollLeft()) * mod))
						};

					},

					_generatePosition : function(event) {

						var top, left, o = this.options, pageX = event.pageX, pageY = event.pageY, scroll = this.cssPosition === "absolute"
								&& !(this.scrollParent[0] !== this.document[0] && $.contains(this.scrollParent[0], this.offsetParent[0])) ? this.offsetParent
								: this.scrollParent, scrollIsRootNode = (/(html|body)/i).test(scroll[0].tagName);

						// This is another very weird special case that only
						// happens for relative elements:
						// 1. If the css position is relative
						// 2. and the scroll parent is the document or similar
						// to the offset parent
						// we have to refresh the relative offset during the
						// scroll so there are no jumps
						if (this.cssPosition === "relative"
								&& !(this.scrollParent[0] !== this.document[0] && this.scrollParent[0] !== this.offsetParent[0])) {
							this.offset.relative = this._getRelativeOffset();
						}

						/*
						 * - Position constraining - Constrain the position to a
						 * mix of grid, containment.
						 */

						if (this.originalPosition) { // If we are not
							// dragging yet, we
							// won't check for
							// options

							if (this.containment) {
								if (event.pageX - this.offset.click.left < this.containment[0]) {
									pageX = this.containment[0] + this.offset.click.left;
								}
								if (event.pageY - this.offset.click.top < this.containment[1]) {
									pageY = this.containment[1] + this.offset.click.top;
								}
								if (event.pageX - this.offset.click.left > this.containment[2]) {
									pageX = this.containment[2] + this.offset.click.left;
								}
								if (event.pageY - this.offset.click.top > this.containment[3]) {
									pageY = this.containment[3] + this.offset.click.top;
								}
							}

							if (o.grid) {
								top = this.originalPageY + Math.round((pageY - this.originalPageY) / o.grid[1]) * o.grid[1];
								pageY = this.containment ? ((top - this.offset.click.top >= this.containment[1] && top - this.offset.click.top <= this.containment[3]) ? top
										: ((top - this.offset.click.top >= this.containment[1]) ? top - o.grid[1] : top + o.grid[1]))
										: top;

								left = this.originalPageX + Math.round((pageX - this.originalPageX) / o.grid[0]) * o.grid[0];
								pageX = this.containment ? ((left - this.offset.click.left >= this.containment[0] && left - this.offset.click.left <= this.containment[2]) ? left
										: ((left - this.offset.click.left >= this.containment[0]) ? left - o.grid[0] : left + o.grid[0]))
										: left;
							}

						}

						return {
							top : (

							// The absolute mouse position
							pageY -

							// Click offset (relative to the element)
							this.offset.click.top -

							// Only for relative positioned nodes: Relative
							// offset from element to offset parent
							this.offset.relative.top -

							// The offsetParent's offset without borders (offset
							// + border)
							this.offset.parent.top + ((this.cssPosition === "fixed" ? -this.scrollParent.scrollTop() : (scrollIsRootNode ? 0 : scroll
									.scrollTop())))),
							left : (

							// The absolute mouse position
							pageX -

							// Click offset (relative to the element)
							this.offset.click.left -

							// Only for relative positioned nodes: Relative
							// offset from element to offset parent
							this.offset.relative.left -

							// The offsetParent's offset without borders (offset
							// + border)
							this.offset.parent.left + ((this.cssPosition === "fixed" ? -this.scrollParent.scrollLeft() : scrollIsRootNode ? 0
									: scroll.scrollLeft())))
						};

					},

					_rearrange : function(event, i, a, hardRefresh) {

						a ? a[0].appendChild(this.placeholder[0]) : i.item[0].parentNode.insertBefore(this.placeholder[0],
								(this.direction === "down" ? i.item[0] : i.item[0].nextSibling));

						// Various things done here to improve the performance:
						// 1. we create a setTimeout, that calls
						// refreshPositions
						// 2. on the instance, we have a counter variable, that
						// get's higher after every append
						// 3. on the local scope, we copy the counter variable,
						// and check in the timeout,
						// if it's still the same
						// 4. this lets only the last addition to the timeout
						// stack through
						this.counter = this.counter ? ++this.counter : 1;
						var counter = this.counter;

						this._delay(function() {
							if (counter === this.counter) {

								// Precompute after each DOM insertion, NOT on
								// mousemove
								this.refreshPositions(!hardRefresh);
							}
						});

					},

					_clear : function(event, noPropagation) {

						this.reverting = false;

						// We delay all events that have to be triggered to
						// after the point where the placeholder
						// has been removed and everything else normalized again
						var i, delayedTriggers = [];

						// We first have to update the dom position of the
						// actual currentItem
						// Note: don't do it if the current item is already
						// removed (by a user), or it gets
						// reappended (see #4088)
						if (!this._noFinalSort && this.currentItem.parent().length) {
							this.placeholder.before(this.currentItem);
						}
						this._noFinalSort = null;

						if (this.helperToSort[0] === this.currentItem[0]) {
							for (i in this._storedCSS) {
								if (this._storedCSS[i] === "auto" || this._storedCSS[i] === "static") {
									this._storedCSS[i] = "";
								}
							}
							this.currentItem.css(this._storedCSS);
							this._removeClass(this.currentItem, "ui-sortable-helper");
						} else {
							this.currentItem.show();
						}

						if (this.fromOutside && !noPropagation) {
							delayedTriggers.push(function(event) {
								this._trigger("receive", event, this._uiHash(this.fromOutside));
							});
						}
						if ((this.fromOutside || this.domPosition.prev !== this.currentItem.prev().not(".ui-sortable-helper")[0] || this.domPosition.parent !== this.currentItem
								.parent()[0])
								&& !noPropagation) {

							// Trigger update callback if the DOM position has
							// changed
							delayedTriggers.push(function(event) {
								this._trigger("update", event, this._uiHash());
							});
						}

						// Check if the items Container has Changed and trigger
						// appropriate
						// events.
						if (this !== this.currentContainer) {
							if (!noPropagation) {
								delayedTriggers.push(function(event) {
									this._trigger("remove", event, this._uiHash());
								});
								delayedTriggers.push((function(c) {
									return function(event) {
										c._trigger("receive", event, this._uiHash(this));
									};
								}).call(this, this.currentContainer));
								delayedTriggers.push((function(c) {
									return function(event) {
										c._trigger("update", event, this._uiHash(this));
									};
								}).call(this, this.currentContainer));
							}
						}

						// Post events to containers
						function delayEvent(type, instance, container) {
							return function(event) {
								container._trigger(type, event, instance._uiHash(instance));
							};
						}
						for (i = this.containers.length - 1; i >= 0; i--) {
							if (!noPropagation) {
								delayedTriggers.push(delayEvent("deactivate", this, this.containers[i]));
							}
							if (this.containers[i].containerCache.over) {
								delayedTriggers.push(delayEvent("out", this, this.containers[i]));
								this.containers[i].containerCache.over = 0;
							}
						}

						// Do what was originally in plugins
						if (this.storedCursor) {
							this.document.find("body").css("cursor", this.storedCursor);
							this.storedStylesheet.remove();
						}
						if (this._storedOpacity) {
							this.helperToSort.css("opacity", this._storedOpacity);
						}
						if (this._storedZIndex) {
							this.helperToSort.css("zIndex", this._storedZIndex === "auto" ? "" : this._storedZIndex);
						}

						this.dragging = false;

						if (!noPropagation) {
							this._trigger("beforeStop", event, this._uiHash());
						}

						// $(this.placeholder[0]).remove(); would have been the
						// jQuery way - unfortunately,
						// it unbinds ALL events from the original node!
						this.placeholder[0].parentNode.removeChild(this.placeholder[0]);

						if (!this.cancelHelperRemoval) {
							if (this.helperToSort[0] !== this.currentItem[0]) {
								this.helperToSort.remove();
							}
							this.helperToSort = null;
						}

						if (!noPropagation) {
							for (i = 0; i < delayedTriggers.length; i++) {

								// Trigger all delayed events
								delayedTriggers[i].call(this, event);
							}
							// 선택한 li의 배열
							var lis = this._getItemsAsjQuery().toArray();
							// 배열을 뒤집는다(ZIndex는 높을수록 상위에 보이며, List index는 높을수록
							// 아래에 배치됨)
							lis.reverse();
							// 모든 레이어 획득
							var layers = this.map.getLayers();
							// 모든 레이어를 배열로 획득
							var addedLayers = layers.getArray();

							for (var i = 0; i < lis.length; i++) {
								// 레이어 아이디와 같은 레이어를 검색
								for (var j = 0; j < addedLayers.length; j++) {
									// li의 아이디가 레이어의 아이디와 같다면
									if (addedLayers[j].get("name") === $(lis[i]).attr("layername")) {
										if (addedLayers[j] instanceof ol.layer.Group) {
											addedLayers[j].setZIndex(i);
											var layers = addedLayers[j].getLayers().getArray();
											for (var k = 0; k < layers.length; k++) {
												layers[k].setZIndex(i + 1);
											}
										} else {
											// li의 인덱스를 레이어의 인덱스로 설정
											addedLayers[j].setZIndex(i + 1);
										}
										break;
									}
								}
							}
							// this.refreshToSelect();
							this._trigger("stop", event, this._uiHash());
						}

						this.fromOutside = false;
						return !this.cancelHelperRemoval;

					},

					_trigger : function() {
						if ($.Widget.prototype._trigger.apply(this, arguments) === false) {
							this.cancel();
						}
					},

					_uiHash : function(_inst) {
						var inst = _inst || this;
						return {
							helper : inst.helper,
							placeholder : inst.placeholder || $([]),
							position : inst.position,
							originalPosition : inst.originalPosition,
							offset : inst.positionAbs,
							item : inst.currentItem,
							sender : _inst ? _inst.element : null
						};
					}

				});
