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

#mask {
	position: fixed;
	z-index: 990;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	display: none;
	font-size: 0;
	text-indent: -1900px;
	background: #000;
	-ms-filter: progid:DXImageTransform.Microsoft.alpha(opacity=50);
	/*IE8*/
	filter: progid:DXImageTransform.Microsoft.alpha(opacity=50);
	opacity: 0.5;
}
</style>
<div id="mask">dimmed</div>

<!-- loadimage추가 -->

<img id="loadimage" src="${pageContext.request.contextPath}/resources/img/spin.gif" style="position: absolute; width: 150px; height: 150px; display: none;">

<div class="row panel panel-default" style="margin-bottom: 0;">

	<div class="navbar-header">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="javascript:goMainPage()">OpenGDS</a>
		</div>
	</div>
	<div class="collapse navbar-collapse">
		<ul class="nav navbar-nav navbar-right">
			<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Quick menu<span class="caret"></span></a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="#" onclick="gitbuilder.ui.showQAPage()">Validation</a></li>
					<li><a href="#" onclick="gitbuilder.ui.showGenPage()">Generalization</a></li>
				</ul></li>
		</ul>
	</div>

</div>
