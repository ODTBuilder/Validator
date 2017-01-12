/**
 * 
 */
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
gitbuilder.ui.NewVector = $
		.widget(
				"gitbuilder.newvector",
				{
					widnow : null,
					options : {
						map : null,
						appendTo : "body"
					},
					_create : function() {
						var xSpan = $("<span>").attr({
							"aria-hidden":true
						}).html("&times;");
						var xButton = $("<button>").attr({
							"type": "button",
							"data-dismiss": "modal",
							"aria-label":"Close"
						}).html(xSpan);
						this._addClass(xButton, "close");
						
						var htag = $("<h4>");
						htag.text("New Vector Layer");
						this._addClass(htag, "modal-title");
						
						var header = $("<div>").append(xButton).append(htag);
						this._addClass(header, "modal-header");
						/* 
						 * 
						 * 
						 * header end 
						 * 
						 * 
						 * */
						var pHead = $("<div>").text("Layer");
						this._addClass(pHead, "panel-heading");
						
						var tLabel = $("<label>").text("Type");
						this._addClass(tLabel, "col-md-2");
						this._addClass(tLabel, "control-label");
						
//						var  = $("<label>").text("Type");
//						this._addClass(tLabel, "col-md-2");
//						this._addClass(tLabel, "control-label");
						
						var btnGroup = $("<div>").attr({
							"data-toggle":"buttons"
						});
						this._addClass(btnGroup, "btn-group");
						this._addClass(btnGroup, "col-md-12");
						
						var tVal = $("<div>");
						this._addClass(tVal, "col-md-10");
						
						var fGroup = $("<div>");
						this._addClass(fGroup, "form-group");
						
						var pBody = $("<div>").append(fGroup);
						this._addClass(pBody, "panel-body");
						
						var panel = $("<div>").append(pHead).append(pBody);
						this._addClass(panel, "panel");
						this._addClass(panel, "panel-default");
						
						var form = $("<form>").append(panel);
						this._addClass(form, "form-horizontal");
						
						var body = $("<div>").append(form);
						this._addClass(body, "modal-body");
						/* 
						 * 
						 * 
						 * body end 
						 * 
						 * 
						 * */
						var footer = $("<div>");
						this._addClass(footer, "modal-footer");
						/* 
						 * 
						 * 
						 * footer end 
						 * 
						 * 
						 * */
						var content = $("<div>").append(header).append(body).append(footer);
						this._addClass(content, "modal-content");
						
						var dialog = $("<div>").html(content);
						this._addClass(dialog, "modal-dialog");
						
						this.window = $("<div>").hide().attr({
							// Setting tabIndex makes the div focusable
							tabIndex : -1,
							role : "dialog",
						}).html(dialog);
						this._addClass(this.window, "modal fade");
						
						this.window.appendTo(this._appendTo());
						this.window.modal({
							backdrop: true,
							keyboard: true,
							show: false,
						});
						this.window.modal('show');
//						var vector = "<div class='modal fade' tabindex='-1' role='dialog'>";
//						vector += '<div class="modal-dialog">';
//						vector += '<div class="modal-content">';
//						vector += '<div class="modal-header">';
//						vector += '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
//						vector += '<h4 class="modal-title">Vector</h4>';
//						vector += '</div>';
//						vector += '<div class="modal-body">';
//
//						vector += '<form class="form-horizontal">';
//
//						vector += '<div class="panel panel-default">';
//						vector += '<div class="panel-heading">Layer</div>';
//						vector += '<div class="panel-body">';
//
//						vector += '<div class="form-group">';
//						vector += '<label class="col-md-2 control-label">Type</label>';
//						vector += '<div class="col-md-10">';
//						vector += '<div class="btn-group col-md-12" data-toggle="buttons">';
//						vector += '<label class="btn btn-default active col-md-4">';
//						vector += '<input type="radio" value="MultiPoint" checked> Point';
//						vector += '</label>';
//						vector += '<label class="btn btn-default col-md-4">';
//						vector += '<input type="radio" value="MultiLineString"> LineString';
//						vector += '</label>';
//						vector += '<label class="btn btn-default col-md-4">';
//						vector += '<input type="radio" value="MultiPolygon"> Polygon';
//						vector += '</label>';
//						vector += '</div>';
//
//						vector += '</div>';
//						vector += '</div>';
//
//						vector += '<div class="form-group">';
//						vector += '<label class="col-md-2 control-label">Name</label>';
//						vector += '<div class="col-md-10">';
//						vector += '<input type="text" class="form-control">';
//						vector += '</div>';
//						vector += '</div>';
//
//						vector += '</div>';
//						vector += '</div>';
//
//						vector += '<div class="panel panel-default">';
//						vector += '<div class="panel-heading">New Attribute</div>';
//						vector += '<div class="panel-body">';
//
//						vector += '<div class="form-group">';
//						vector += '<label class="col-md-2 control-label">Name</label>';
//						vector += '<div class="col-md-10">';
//						vector += '<input type="text" class="form-control" >';
//						vector += '</div>';
//						vector += '</div>';
//
//						vector += '<div class="form-group">';
//						vector += '<label class="col-md-2 control-label">Type</label>';
//						vector += '<div class="col-md-10">';
//						vector += '<select class="form-control">';
//						vector += '<option value="String">String</option>';
//						vector += '<option value="Integer">Integer</option>';
//						vector += '<option value="Double">Double</option>';
//						vector += '<option value="Date">Date</option>';
//						vector += '</select>';
//						vector += '</div>';
//						vector += '</div>';
//
//						vector += '<div class="form-group">';
//						vector += '<div class="col-md-5">';
//						vector += '</div>';
//						vector += '<div class="col-md-2">';
//						vector += '<button type="button" class="btn btn-default">Add</button>';
//						vector += '</div>';
//						vector += '<div class="col-md-5">';
//						vector += '</div>';
//						vector += '</div>';
//
//						vector += '</div>';
//						vector += '</div>';
//
//						vector += '<div class="panel panel-default">';
//						vector += '<div class="panel-heading">Attribute List</div>';
//						vector += '<div class="panel-body">';
//						vector += '<table class="table table-bordered table-hover table-condensed">';
//						vector += '<thead><tr><td><p class="text-center">#</p></td><td><p class="text-center">Name</p></td><td><p class="text-center">Type</p></td><td><p class="text-center">Delete</p></td></tr></thead>';
//						vector += '<tbody>';
//						vector += '<tr><td><p class="text-center">1</p></td><td><p class="text-center">id</p></td><td><p class="text-center">Integer</p></td><td><p class="text-center"><button type="button" class="btn btn-default btn-xs DeleteAttr">Delete</button></p></td></tr>';
//						vector += '</tbody>';
//						vector += '</table>';
//						vector += '</div>';
//						vector += '</div>';
//
//						vector += '</form>';
//
//						vector += '</div>';
//						vector += '<div class="modal-footer">';
//						vector += '<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>';
//						vector += '<button type="button" class="btn btn-primary">Create</button>';
//						vector += '</div>';
//						vector += '</div>';
//						vector += '</div>';
//						vector += '</div>';
//						this.widnow = vector;
					},
					_appendTo : function() {
						var element = this.options.appendTo;
						if (element && (element.jquery || element.nodeType)) {
							return $(element);
						}
						return this.document.find(element || "body").eq(0);
					},
					_removeClass : function(element, keys, extra) {
						return this._toggleClass(element, keys, extra, false);
					},

					_addClass : function(element, keys, extra) {
						return this._toggleClass(element, keys, extra, true);
					},

					_toggleClass : function(element, keys, extra, add) {
						add = (typeof add === "boolean") ? add : extra;
						var shift = (typeof element === "string" || element === null), options = {
							extra : shift ? keys : extra,
							keys : shift ? element : keys,
							element : shift ? this.element : element,
							add : add
						};
						options.element.toggleClass(this._classes(options), add);
						return this;
					},

					_on : function(suppressDisabledCheck, element, handlers) {
						var delegateElement;
						var instance = this;

						// No suppressDisabledCheck flag, shuffle arguments
						if (typeof suppressDisabledCheck !== "boolean") {
							handlers = element;
							element = suppressDisabledCheck;
							suppressDisabledCheck = false;
						}

						// No element argument, shuffle and use this.element
						if (!handlers) {
							handlers = element;
							element = this.element;
							delegateElement = this.widget();
						} else {
							element = delegateElement = $(element);
							this.bindings = this.bindings.add(element);
						}

						$.each(handlers, function(event, handler) {
							function handlerProxy() {

								// Allow widgets to customize the disabled
								// handling
								// - disabled as an array instead of boolean
								// - disabled class as method for disabling
								// individual parts
								if (!suppressDisabledCheck && (instance.options.disabled === true || $(this).hasClass("ui-state-disabled"))) {
									return;
								}
								return (typeof handler === "string" ? instance[handler] : handler).apply(instance, arguments);
							}

							// Copy the guid so direct unbinding works
							if (typeof handler !== "string") {
								handlerProxy.guid = handler.guid = handler.guid || handlerProxy.guid || $.guid++;
							}

							var match = event.match(/^([\w:-]*)\s*(.*)$/);
							var eventName = match[1] + instance.eventNamespace;
							var selector = match[2];

							if (selector) {
								delegateElement.on(eventName, selector, handlerProxy);
							} else {
								element.on(eventName, handlerProxy);
							}
						});
					},

					_off : function(element, eventName) {
						eventName = (eventName || "").split(" ").join(this.eventNamespace + " ") + this.eventNamespace;
						element.off(eventName).off(eventName);

						// Clear the stack to avoid memory leaks (#10056)
						this.bindings = $(this.bindings.not(element).get());
						this.focusable = $(this.focusable.not(element).get());
						this.hoverable = $(this.hoverable.not(element).get());
					}
				});