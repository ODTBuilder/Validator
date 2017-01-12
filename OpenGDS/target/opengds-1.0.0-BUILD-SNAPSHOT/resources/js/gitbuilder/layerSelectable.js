/**
 * 레이어 리스트를 생성하는 객체를 정의한다.
 * 
 * @author yijun.so
 * @date 2016. 10
 * @version 0.03
 */

var gitbuilder;
if (!gitbuilder)
	gitbuilder = {};
if (!gitbuilder.ui)
	gitbuilder.ui = {};
gitbuilder.ui.LayerListSelectable = $
		.widget(
				"ui.layerselectable",
				$.ui.mouse,
				{
					version : "1.12.1",
					map : null,
					selectedLayers : new ol.Collection(),
					options : {
						map : null,
						appendTo : "body",
						autoRefresh : true,
						distance : 0,
						filter : ".GitBuilder-LayerSelectable-Item",
						tolerance : "touch",
						cancel: ".GitBuilder-LayerSortable-Handle",

						// Callbacks
						selected : null,
						selecting : null,
						start : null,
						stop : null,
						unselected : null,
						unselecting : null
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
					_create : function() {
						if (this.options.map instanceof ol.Map) {
							this.map = this.options.map;
							console.log("ol.Map");
						} else {
							console.error("option: map is not ol.Map");
							return;
						}

						var that = this;

						this._addClass("ui-selectable");
						this._addClass("Gitbuilder-LayerSelectable");

						var layers = that.map.getLayers();

						var addedLayers = layers.getArray();

						addedLayers.sort(function(a, b) {
							return a.getZIndex() < b.getZIndex() ? -1 : a.getZIndex() > b.getZIndex() ? 1 : 0;
						});

						for (var i = 0; i < addedLayers.length; i++) {
							var str = '';
							str += '<li layerName="' + addedLayers[i].get('name') + '" class="GitBuilder-LayerSelectable-Item">';
							str += '<span class="GitBuilder-LayerSortable-Handle"><span class="glyphicon glyphicon-sort" aria-hidden="true"></span></span>';
							str += '<div>';
							str += addedLayers[i].get('name');
							str += '</div>';

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

							str += '</li>';

							$(that.element).prepend(str);
						}

						// $(".glyphicon-eye-close").hide();

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

						this.dragged = false;

						// Cache selectee children based on filter
						this.refresh = function() {
							that.elementPos = $(that.element[0]).offset();
							that.selectees = $(that.options.filter, that.element[0]);
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
						this.refresh();

						this._mouseInit();

						this.helper = $("<div>");
						this._addClass(this.helper, "ui-selectable-helper");

						var selectee = $(".ui-selectee", this.element[0]).find(".Gitbuilder-LayerSelectable-Function-Area");

						$(selectee, this.element[0]).each(function() {
							$(this).hide();
						});
					},

					_destroy : function() {
						this.selectees.removeData("selectable-item");
						this._mouseDestroy();
					},

					_mouseStart : function(event) {
						var that = this, options = this.options;

						this.opos = [ event.pageX, event.pageY ];
						this.elementPos = $(this.element[0]).offset();

						if (this.options.disabled) {
							return;
						}

						this.selectees = $(options.filter, this.element[0]);

						this._trigger("start", event);

						$(options.appendTo).append(this.helper);

						// position helper (lasso)
						this.helper.css({
							"left" : event.pageX,
							"top" : event.pageY,
							"width" : 0,
							"height" : 0
						});

						if (options.autoRefresh) {
							this.refresh();
						}

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

					},

					_mouseDrag : function(event) {

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
						this.helper.css({
							left : x1,
							top : y1,
							width : x2 - x1,
							height : y2 - y1
						});

						this.selectees.each(function() {
							var selectee = $.data(this, "selectable-item"), hit = false, offset = {};

							// prevent helper from being selected if appendTo:
							// selectable
							if (!selectee || selectee.element === that.element[0]) {
								return;
							}

							offset.left = selectee.left + that.elementPos.left;
							offset.right = selectee.right + that.elementPos.left;
							offset.top = selectee.top + that.elementPos.top;
							offset.bottom = selectee.bottom + that.elementPos.top;

							if (options.tolerance === "touch") {
								hit = (!(offset.left > x2 || offset.right < x1 || offset.top > y2 || offset.bottom < y1));
							} else if (options.tolerance === "fit") {
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

						return false;
					},

					_mouseStop : function(event) {
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

						this._trigger("stop", event, {
							selectedLayers : that.selectedLayers
						});

						this.helper.remove();

						return false;
					}

				});
