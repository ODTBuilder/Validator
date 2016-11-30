<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
/* HTML5 display-role reset for older browsers */
article, aside, details, figcaption, figure, footer, header, hgroup,
	menu, nav, section {
	display: block;
}

body {
	line-height: 1;
}

ol, ul { 
	list-style: none;
}

blockquote, q {
	quotes: none;
}

blockquote:before, blockquote:after, q:before, q:after {
	content: '';
	content: none;
}

table {
	border-collapse: collapse;
	border-spacing: 0;
}

#mask{position:fixed; z-index:990; top:0; left:0; width:100%; height:100%; display:none; font-size:0; text-indent:-1900px; background:#000; -ms-filter:progid:DXImageTransform.Microsoft.alpha(opacity=50); /*IE8*/
filter:progid:DXImageTransform.Microsoft.alpha(opacity=50); opacity:0.5;}
</style>	
<div id="mask">dimmed</div> 

	<!-- loadimage추가 -->
	
	<img id="loadimage" src="${pageContext.request.contextPath}/resources/img/spin.gif" style="position: absolute; width: 150px; height: 150px; display: none;">
	<div id="builderInfo" style="width: 100%; height: 6%; background-color: #f8f8f8;">
		<div class="col-md-10">
			<a href="javascript:goMainPage()">
				<img src="${pageContext.request.contextPath}/resources/img/ci.png" style="width: auto; height: 25px; margin-top: 18px; margin-left: 6px;"/>
			</a>
		</div>
		<div class="col-md-2 text-right" style="margin-top: 14px;">
			<div class="input-group">
				<span class="input-group-btn">
					<button class="btn btn-default" type="button"  onclick="gitbuilder.ui.showQAPage()">Validation</button>
					<button class="btn btn-default" type="button" onclick="gitbuilder.ui.showGenPage()">Generalization</button>
				</span>
			</div>
		</div>
	</div>