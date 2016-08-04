<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Open GDS Builder</title>

<script src="${pageContext.request.contextPath}/resources/js/jquery/jquery-2.2.2.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/ol3/ol-debug.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/ol3/ol.css">

<script src="${pageContext.request.contextPath}/resources/js/jqueryui/jquery-ui.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/jqueryui/jquery-ui.min.css">

<script src="${pageContext.request.contextPath}/resources/js/spectrum/spectrum.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/spectrum/spectrum.css">

<script>
	var gitbuilder;
	if (!gitbuilder) {
		gitbuilder = {};
	}
	if (!gitbuilder.variable) {
		gitbuilder.variable = {};
		gitbuilder.variable.crsrPosition = []; // 커서 위치
		gitbuilder.variable.prjGroup = {}; //프로젝트 객체
		gitbuilder.variable.clpboard = []; //피처 클립보드
		gitbuilder.variable.map; //오픈 레이어스 맵객체
		gitbuilder.variable.view; //오픈 레이어스 뷰객체
		gitbuilder.variable.ctrl = []; //오픈 레이어스 컨트롤 배열
		gitbuilder.variable.intrctn = []; //오픈 레이어스 인터랙션 배열
		gitbuilder.variable.elementid = {}; //DOM id
		gitbuilder.variable.menuname = {}; //메뉴 이름변경 또는 로컬라이징
	}
	if (!gitbuilder.method) {
		gitbuilder.method = {};
		//==========================================================================================================

		//==========================================================================================================
		gitbuilder.method.CheckId = function CheckId(_eid) {
			var idCheck = $('[id="' + _eid + '"]');
			if (idCheck.length === 0) {
				return true;
			} else {
				return false;
			}
		}
		//==========================================================================================================
	}
	if (!gitbuilder.ui) {
		//==========================================================================================================
		gitbuilder.ui = {};
		gitbuilder.ui.button = {};
		gitbuilder.ui.window = {};
		//==========================================================================================================
		gitbuilder.ui.window.NewVector = function NewVector(_obj) {
			
// 			<div class="">
				
// 			</div>
		}
		//==========================================================================================================
		
		//==========================================================================================================
		/** 
		 * @description 새로운 레이어를 만들기 위한 버튼
		 * @param {object} 버튼을 초기화할 생성자
		 */
		 
		 /*
		 {
			 target: id {string},
			 name: name {string},
			 layers: array <object>
		 }
		 */
		gitbuilder.ui.button.NewLayer = function NewLayer(_obj) {

		}
		//==========================================================================================================
		gitbuilder.ui.button.BaseMap = function BaseMap(_div) {

		}
		//==========================================================================================================
		gitbuilder.ui.window.Vector = function Vector(_obj) {

		}
		//==========================================================================================================
		gitbuilder.ui.MapArea = function MapArea(_div) {

		}
		//==========================================================================================================
		gitbuilder.ui.LayerList = function LayerList(_div) {

		}
	}
</script>

</head>
<body>
	<div style="width: 100%; height: 10%; background-color: #e3e3e3;">header</div>
	<div>
		<div
			style="float: left; width: 15%; height: 90%; background-color: #cccccc;">layer</div>
		<div
			style="float: left; width: 85%; height: 90%; background-color: #f1f1f1;">map</div>
	</div>
</body>
</html>
