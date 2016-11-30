(function(a){"function"===typeof define&&define.amd?define(["jquery","./grid.base","./jqdnr","./jqmodal"],a):"object"===typeof exports?a(require("jquery")):a(jQuery)})(function(a){var n=a.jgrid,y=a.fn.jqGrid,r=y.getGuiStyles,z=y.getGridRes;n.jqModal=n.jqModal||{};a.extend(!0,n.jqModal,{toTop:!0});a.extend(n,{showModal:function(a){a.w.show()},closeModal:function(a){a.w.hide().attr("aria-hidden","true");a.o&&a.o.remove()},hideModal:function(e,b){b=a.extend({jqm:!0,gb:"",removemodal:!1},b||{});var c=
b.gb&&"string"===typeof b.gb&&"#gbox_"===b.gb.substr(0,6)?a("#"+b.gb.substr(6))[0]:!1,g=a(e);if(b.onClose&&(c=c?b.onClose.call(c,e):b.onClose(e),"boolean"===typeof c&&!c))return;if(a.fn.jqm&&!0===b.jqm)g.attr("aria-hidden","true").jqmHide();else{if(""!==b.gb)try{a(">.jqgrid-overlay",b.gb).first().hide()}catch(l){}g.hide().attr("aria-hidden","true")}b.removemodal&&g.remove()},findPos:function(a){var b=0,c=0;if(a.offsetParent){do b+=a.offsetLeft,c+=a.offsetTop,a=a.offsetParent;while(a)}return[b,c]},
createModal:function(e,b,c,g,l,k,d){var h=n.jqID,f=this.p,f=null!=f?f.jqModal||{}:{};c=a.extend(!0,{resizingRightBottomIcon:y.getIconRes.call(this,"form.resizableLtr")},n.jqModal||{},f,c);var f=document.createElement("div"),m="#"+h(e.themodal),v="rtl"===a(c.gbox).attr("dir")?!0:!1,w=e.resizeAlso?"#"+h(e.resizeAlso):!1;d=a.extend({},d||{});f.className=r.call(this,"dialog.window","ui-jqdialog");f.id=e.themodal;f.dir=v?"rtl":"ltr";var q=document.createElement("div");q.className=r.call(this,"dialog.document");
a(q).attr("role","document");var t=document.createElement("div");t.className=r.call(this,"dialog.subdocument");q.appendChild(t);f.appendChild(q);q=document.createElement("div");q.className=r.call(this,"dialog.header","ui-jqdialog-titlebar "+(v?"ui-jqdialog-titlebar-rtl":"ui-jqdialog-titlebar-ltr"));q.id=e.modalhead;a(q).append("<span class='ui-jqdialog-title'>"+c.caption+"</span>");var A=r.call(this,"states.hover"),p=a("<a aria-label='Close' class='"+r.call(this,"dialog.closeButton","ui-jqdialog-titlebar-close")+
"'></a>").hover(function(){p.addClass(A)},function(){p.removeClass(A)}).append("<span class='"+y.getIconRes.call(this,"form.close")+"'></span>");a(q).append(p);v=document.createElement("div");a(v).addClass(r.call(this,"dialog.content","ui-jqdialog-content")).attr("id",e.modalcontent);a(v).append(b);t.appendChild(v);a(t).prepend(q);!0===k?a("body").append(f):"string"===typeof k?a(k).append(f):a(f).insertBefore(g);a(f).css(d);void 0===c.jqModal&&(c.jqModal=!0);b={};if(a.fn.jqm&&!0===c.jqModal)0===c.left&&
0===c.top&&c.overlay&&(k=[],k=n.findPos(l),c.left=k[0]+4,c.top=k[1]+4),b.top=c.top+"px",b.left=c.left;else if(0!==c.left||0!==c.top)b.left=c.left,b.top=c.top+"px";a("a.ui-jqdialog-titlebar-close",q).click(function(){var b=a(m).data("onClose")||c.onClose,e=a(m).data("gbox")||c.gbox;n.hideModal(m,{gb:e,jqm:c.jqModal,onClose:b,removemodal:c.removemodal||!1});return!1});0!==c.width&&c.width||(c.width=300);0!==c.height&&c.height||(c.height=200);c.zIndex||((g=a(g).parents("*[role=dialog]").first().css("z-index"))?
(c.zIndex=parseInt(g,10)+2,c.toTop=!0):c.zIndex=950);b.left&&(b.left+="px");a(f).css(a.extend({width:isNaN(c.width)?"auto":c.width+"px",height:isNaN(c.height)?"auto":c.height+"px",zIndex:c.zIndex},b)).attr({tabIndex:"-1",role:"dialog","aria-labelledby":e.modalhead,"aria-hidden":"true"});void 0===c.drag&&(c.drag=!0);void 0===c.resize&&(c.resize=!0);if(c.drag)if(a.fn.jqDrag)a(q).css("cursor","move"),a(f).jqDrag(q);else try{a(f).draggable({handle:a("#"+h(q.id))})}catch(u){}if(c.resize)if(a.fn.jqResize)a(v).append("<div class='jqResize ui-resizable-handle ui-resizable-se "+
c.resizingRightBottomIcon+"'></div>"),a(m).jqResize(".jqResize",w);else try{a(f).resizable({handles:"se, sw",alsoResize:w})}catch(u){}!0===c.closeOnEscape&&a(f).keydown(function(b){27===b.which&&(b=a(m).data("onClose")||c.onClose,n.hideModal(m,{gb:c.gbox,jqm:c.jqModal,onClose:b,removemodal:c.removemodal||!1,formprop:!c.recreateForm||!1,form:c.form||""}))})},viewModal:function(e,b){b=a.extend(!0,{overlay:30,modal:!1,overlayClass:r.call(this,"overlay"),onShow:n.showModal,onHide:n.closeModal,gbox:"",
jqm:!0,jqM:!0},n.jqModal||{},b||{});if(a.fn.jqm&&!0===b.jqm)b.jqM?a(e).attr("aria-hidden","false").jqm(b).jqmShow():a(e).attr("aria-hidden","false").jqmShow();else{""!==b.gbox&&(a(">.jqgrid-overlay",b.gbox).first().show(),a(e).data("gbox",b.gbox));a(e).show().attr("aria-hidden","false");try{a(":input:visible",e)[0].focus()}catch(c){}}},info_dialog:function(e,b,c,g){var l=this,k=l.p,d=a.extend(!0,{width:290,height:"auto",dataheight:"auto",drag:!0,resize:!1,left:250,top:170,zIndex:1E3,jqModal:!0,modal:!1,
closeOnEscape:!0,align:"center",buttonalign:"center",buttons:[]},n.jqModal||{},null!=k?k.jqModal||{}:{},{caption:"<b>"+e+"</b>"},g||{}),h=d.jqModal;a.fn.jqm&&!h&&(h=!1);e="";var f=r.call(l,"states.hover");if(0<d.buttons.length)for(g=0;g<d.buttons.length;g++)void 0===d.buttons[g].id&&(d.buttons[g].id="info_button_"+g),e+=n.builderFmButon.call(l,d.buttons[g].id,d.buttons[g].text);b="<div id='info_id'>"+("<div id='infocnt' style='margin:0px;padding-bottom:1em;width:100%;overflow:auto;position:relative;height:"+
(isNaN(d.dataheight)?d.dataheight:d.dataheight+"px")+";"+("text-align:"+d.align+";")+"'>"+b+"</div>");if(c||""!==e)b+="<hr class='"+r.call(l,"dialog.hr")+"' style='margin:1px'/><div style='text-align:"+d.buttonalign+";padding:.8em 0 .5em 0;background-image:none;border-width: 1px 0 0 0;'>"+(c?n.builderFmButon.call(l,"closedialog",c):"")+e+"</div>";b+="</div>";try{"false"===a("#info_dialog").attr("aria-hidden")&&n.hideModal("#info_dialog",{jqm:h}),a("#info_dialog").remove()}catch(m){}n.createModal.call(l,
{themodal:"info_dialog",modalhead:"info_head",modalcontent:"info_content",resizeAlso:"infocnt"},b,d,"","",!0);e&&a.each(d.buttons,function(b){a("#"+n.jqID(l.id),"#info_id").bind("click",function(){d.buttons[b].onClick.call(a("#info_dialog"));return!1})});a("#closedialog","#info_id").click(function(){n.hideModal("#info_dialog",{jqm:h,onClose:a("#info_dialog").data("onClose")||d.onClose,gb:a("#info_dialog").data("gbox")||d.gbox});return!1});a(".fm-button","#info_dialog").hover(function(){a(this).addClass(f)},
function(){a(this).removeClass(f)});a.isFunction(d.beforeOpen)&&d.beforeOpen();n.viewModal.call(l,"#info_dialog",{onHide:function(a){a.w.hide().remove();a.o&&a.o.remove()},modal:d.modal,jqm:h});a.isFunction(d.afterOpen)&&d.afterOpen();try{a("#info_dialog").focus()}catch(m){}},bindEv:function(e,b){a.isFunction(b.dataInit)&&b.dataInit.call(this,e,b);b.dataEvents&&a.each(b.dataEvents,function(){void 0!==this.data?a(e).bind(this.type,this.data,this.fn):a(e).bind(this.type,this.fn)})},createEl:function(e,
b,c,g,l){function k(b,c,e){var d="dataInit dataEvents dataUrl buildSelect sopt searchhidden defaultValue attr custom_element custom_value selectFilled rowId mode cm iCol".split(" ");void 0!==e&&a.isArray(e)&&a.merge(d,e);a.each(c,function(c,e){-1===a.inArray(c,d)&&a(b).attr(c,e)});c.hasOwnProperty("id")||a(b).attr("id",n.randId())}var d="",h=this,f=h.p,m=n.info_dialog,v=z.call(a(h),"errors.errcap"),w=z.call(a(h),"edit"),q=w.msg,w=w.bClose;if(null==b)return"";switch(e){case "textarea":d=document.createElement("textarea");
g?b.cols||a(d).css({width:"100%","box-sizing":"border-box"}):b.cols||(b.cols=19);b.rows||(b.rows=2);if("&nbsp;"===c||"&#160;"===c||1===c.length&&160===c.charCodeAt(0))c="";d.value=c;k(d,b);a(d).attr({role:"textbox"});break;case "checkbox":d=document.createElement("input");d.type="checkbox";if(b.value){var t=b.value.split(":");c===t[0]&&(d.checked=!0,d.defaultChecked=!0);d.value=t[0];a(d).data("offval",t[1])}else t=String(c).toLowerCase(),0>t.search(/(false|f|0|no|n|off|undefined)/i)&&""!==t?(d.checked=
!0,d.defaultChecked=!0,d.value=c):d.value="on",a(d).data("offval","off");k(d,b,["value"]);a(d).attr({role:"checkbox","aria-checked":d.checked?"true":"false"});break;case "select":d=document.createElement("select");m=[];e=null;!0===b.multiple?(g=!0,d.multiple="multiple",a(d).attr("aria-multiselectable","true"),m=c.split(","),m=a.map(m,function(b){return a.trim(b)})):(g=!1,m[0]=a.trim(c));void 0===b.size&&(b.size=g?3:1);try{e=b.rowId}catch(x){}f&&f.idPrefix&&(e=n.stripPref(f.idPrefix,e));if(void 0!==
b.dataUrl){var r=b.postData||l.postData,t={elem:d,options:b,cm:b.cm,mode:b.mode,rowid:e,iCol:b.iCol,ovm:m};a.ajax(a.extend({url:a.isFunction(b.dataUrl)?b.dataUrl.call(h,e,c,String(b.name),t):b.dataUrl,type:"GET",dataType:"html",data:a.isFunction(r)?r.call(h,e,c,String(b.name)):r,context:t,success:function(b,c,e){var d=this.ovm,f=this.elem,g=this.cm,l=this.iCol,m=a.extend({},this.options),p=this.rowid,q=this.mode;b=a.isFunction(m.buildSelect)?m.buildSelect.call(h,b,e,g,l):b;"string"===typeof b&&(b=
a(a.trim(b)).html());b&&(a(f).append(b),k(f,m,r?["postData"]:void 0),setTimeout(function(){var b;a("option",f).each(function(c){0===c&&f.multiple&&(this.selected=!1);-1<a.inArray(a.trim(a(this).val()),d)&&(b=this.selected=!0)});b||a("option",f).each(function(){-1<a.inArray(a.trim(a(this).text()),d)&&(this.selected=!0)});a(f).change();n.fullBoolFeedback.call(h,m.selectFilled,"jqGridSelectFilled",{elem:f,options:m,cm:g,rowid:p,mode:q,cmName:null!=g?g.name:m.name,iCol:l})},0))}},l||{}))}else if(b.value){"function"===
typeof b.value&&(b.value=b.value());var p,u;l=[];f=void 0===b.separator?":":b.separator;q=void 0===b.delimiter?";":b.delimiter;v=function(b,a){if(0<a)return b};if("string"===typeof b.value)for(q=b.value.split(q),p=0;p<q.length;p++)u=q[p].split(f),2<u.length&&(u[1]=a.map(u,v).join(f)),l.push({value:u[0],innerHtml:u[1],selectValue:a.trim(u[0]),selectText:a.trim(u[1]),selected:!1});else if("object"===typeof b.value)for(p in f=b.value,f)f.hasOwnProperty(p)&&l.push({value:p,innerHtml:f[p],selectValue:a.trim(p),
selectText:a.trim(f[p]),selected:!1});for(p=0;p<l.length;p++)f=l[p],g||f.selectValue!==a.trim(c)||(t=f.selected=!0),g&&-1<a.inArray(f.selectValue,m)&&(t=f.selected=!0);if(!t)for(p=0;p<l.length;p++)f=l[p],g||f.selectText!==a.trim(c)||(f.selected=!0),g&&-1<a.inArray(f.selectText,m)&&(f.selected=!0);for(p=0;p<l.length;p++)f=l[p],c=document.createElement("option"),c.value=f.value,c.innerHTML=f.innerHtml,f.selected&&(c.selected=!0),d.appendChild(c);k(d,b,["value"]);n.fullBoolFeedback.call(h,b.selectFilled,
"jqGridSelectFilled",{elem:d,options:b,cm:b.cm,rowid:e,mode:b.mode,cmName:null!=b.cm?b.cm.name:b.name,iCol:b.iCol})}break;case "text":case "password":case "button":t="button"===e?"button":"textbox";d=document.createElement("input");d.type=e;k(d,b);d.value=c;"button"!==e&&(g?b.size||a(d).css({width:"100%","box-sizing":"border-box"}):b.size||(b.size=20));a(d).attr("role",t);break;case "image":case "file":d=document.createElement("input");d.type=e;k(d,b);break;case "custom":d=document.createElement("span");
try{if(a.isFunction(b.custom_element))if(u=b.custom_element.call(h,c,b),u instanceof jQuery||n.isHTMLElement(u)||"string"===typeof u)u=a(u).addClass("customelement").attr({id:b.id,name:b.name}),a(d).empty().append(u);else throw"editoptions.custom_element returns value of a wrong type";else throw"editoptions.custom_element is not a function";}catch(x){"e1"===x&&m.call(h,v,"function 'custom_element' "+q.nodefined,w),"e2"===x?m.call(h,v,"function 'custom_element' "+q.novalue,w):m.call(h,v,"string"===
typeof x?x:x.message,w)}}return d},checkDate:function(a,b){var c={},g;a=a.toLowerCase();g=-1!==a.indexOf("/")?"/":-1!==a.indexOf("-")?"-":-1!==a.indexOf(".")?".":"/";a=a.split(g);b=b.split(g);if(3!==b.length)return!1;var l=-1,k,d=g=-1,h;for(h=0;h<a.length;h++)k=isNaN(b[h])?0:parseInt(b[h],10),c[a[h]]=k,k=a[h],-1!==k.indexOf("y")&&(l=h),-1!==k.indexOf("m")&&(d=h),-1!==k.indexOf("d")&&(g=h);k="y"===a[l]||"yyyy"===a[l]?4:"yy"===a[l]?2:-1;var f;h=[0,31,29,31,30,31,30,31,31,30,31,30,31];if(-1===l)return!1;
f=c[a[l]].toString();2===k&&1===f.length&&(k=1);if(f.length!==k||0===c[a[l]]&&"00"!==b[l]||-1===d)return!1;f=c[a[d]].toString();if(1>f.length||1>c[a[d]]||12<c[a[d]]||-1===g)return!1;f=c[a[g]].toString();!(k=1>f.length||1>c[a[g]]||31<c[a[g]])&&(k=2===c[a[d]])&&(l=c[a[l]],k=c[a[g]]>(0!==l%4||0===l%100&&0!==l%400?28:29));return k||c[a[g]]>h[c[a[d]]]?!1:!0},isEmpty:function(a){return a.match(/^\s+$/)||""===a?!0:!1},checkTime:function(a){var b=/^(\d{1,2}):(\d{2})([apAP][Mm])?$/;if(!n.isEmpty(a))if(a=a.match(b)){if(a[3]){if(1>
a[1]||12<a[1])return!1}else if(23<a[1])return!1;if(59<a[2])return!1}else return!1;return!0},checkValues:function(e,b,c,g,l){var k,d,h=this.p;d=h.colModel;var f=n.isEmpty,m=z.call(a(this),"edit.msg"),v=z.call(a(this),"formatter.date.masks");if(void 0===c){"string"===typeof b&&(b=h.iColByName[b]);if(void 0===b||0>b)return[!0,"",""];g=d[b];c=g.editrules;null!=g.formoptions&&(k=g.formoptions.label)}else k=void 0===g?"_":g,g=d[b];if(c){k||(k=null!=h.colNames?h.colNames[b]:g.label);if(!0===c.required&&
f(e))return[!1,k+": "+m.required,""];h=!1===c.required?!1:!0;if(!0===c.number&&(!1!==h||!f(e))&&isNaN(e))return[!1,k+": "+m.number,""];if(void 0!==c.minValue&&!isNaN(c.minValue)&&parseFloat(e)<parseFloat(c.minValue))return[!1,k+": "+m.minValue+" "+c.minValue,""];if(void 0!==c.maxValue&&!isNaN(c.maxValue)&&parseFloat(e)>parseFloat(c.maxValue))return[!1,k+": "+m.maxValue+" "+c.maxValue,""];var r;if(!(!0!==c.email||!1===h&&f(e)||(r=/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i,
r.test(e))))return[!1,k+": "+m.email,""];if(!(!0!==c.integer||!1===h&&f(e)||!isNaN(e)&&0===e%1&&-1===e.indexOf(".")))return[!1,k+": "+m.integer,""];if(!(!0!==c.date||!1===h&&f(e)||(g.formatoptions&&g.formatoptions.newformat?(d=g.formatoptions.newformat,v.hasOwnProperty(d)&&(d=v[d])):d=d[b].datefmt||"Y-m-d",n.checkDate(d,e))))return[!1,k+": "+m.date+" - "+d,""];if(!0===c.time&&!(!1===h&&f(e)||n.checkTime(e)))return[!1,k+": "+m.date+" - hh:mm (am/pm)",""];if(!(!0!==c.url||!1===h&&f(e)||(r=/^(((https?)|(ftp)):\/\/([\-\w]+\.)+\w{2,3}(\/[%\-\w]+(\.\w{2,})?)*(([\w\-\.\?\\\/+@&#;`~=%!]*)(\.\w{2,})?)*\/?)/i,
r.test(e))))return[!1,k+": "+m.url,""];if(!0===c.custom){if(!1!==h||!f(e))return a.isFunction(c.custom_func)?(e=c.custom_func.call(this,e,k,b),a.isArray(e)?e:[!1,m.customarray,""]):[!1,m.customfcheck,""]}else if(a.isFunction(c.custom)&&(!1!==h||!f(e)))return e=c.custom.call(this,l),a.isArray(e)?e:[!1,m.customarray,""]}return[!0,"",""]}})});
//# sourceMappingURL=grid.common.map
