package com.git.opengds.generalization.controller;

<<<<<<< HEAD
import javax.servlet.http.HttpServletRequest;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.SchemaException;
import org.geotools.validation.spatial.LineNoDanglesValidation;
import org.json.simple.JSONObject;
import org.opengis.feature.simple.SimpleFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.git.gdsbuilder.convertor.DataConvertor;
import com.git.gdsbuilder.generalization.data.DTGeneralAfLayer;
import com.git.gdsbuilder.generalization.data.TopologyTable;
import com.git.gdsbuilder.generalization.impl.Generalization.GeneralizationOrder;
import com.git.gdsbuilder.generalization.opt.DTGeneralOption;
import com.git.gdsbuilder.generalization.opt.DTGeneralOption.DTGeneralOptionType;
import com.git.gdsbuilder.generalization.rep.DTGeneralReport;
import com.git.opengds.generalization.service.GeneralizationService;
import com.vividsolutions.jts.geom.Geometry;


/**
 * 일반화와 관련된 요청을 수행한다.
 * @author SG.Lee
 * @Date 2016.09.05
 * */
=======
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
@Controller
@RequestMapping("/generalization")
public class GeneralizationController {

	private static final Logger logger = LoggerFactory.getLogger(GeneralizationController.class);

<<<<<<< HEAD
	
	DataConvertor convertor = new DataConvertor(); // 데이터 변환 객체 생성
	
	@Autowired
	GeneralizationService generalizationService; //일반화 Service 레이어 생성
	
	/**
	 * 일반화 지도로 이동한다.
	 * @author SG.Lee
	 * @Date 2016.09.05
	 * @param Model
	 * */
=======
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
	@RequestMapping(value = "/generalization.do", method = RequestMethod.GET)
	public String tempPage(Model model) {
		logger.info("Opened page is {}.", "temp");
		
<<<<<<< HEAD
		return "map/generalizationMap"; //페이지 호출
	}
	
	
	/**
	 * 일반화 수행을 요청한다. 
	 * @author SG.Lee
	 * @Date 2016.09.05
	 * @param JSONObject - 일반화 처리를 위한 파라미터를 포함한다. 
	 *         		     { json:
	 *      		        		{ option : { elival : elimination 수치
	 *      		                             simdistance : Simplication 수치}
	 *       		         		  layerName : 레이어 이름
	 *      		          		  type : 레이어 타입
	 *      		          		  geojson : ol.layer객체 -> json객체
	 *      		          		}
	 *                   }
	 * @return JSONObject
	 *         { layerName : 레이어 이름
	 *           geojson : 일반화 결과 레이어(JSONObject)
	 *           topologyTable : TopologyTable - 일반화 Topology Build 결과 객체
	 *           report : 레이어 결과 }
	 *         
	 * @throws
	 * */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/generalization.ajax")
	@ResponseBody
	public JSONObject generalizationAjax(HttpServletRequest request, @RequestBody JSONObject json){
		
		JSONObject genAftJSON = new JSONObject(); //일반화 결과 반환 JSON 생성
		
		JSONObject layerJson = convertor.stringToJSON(json.toString()); //Client에서 넘어온 String json -> Java JSONObect 변환
		
		genAftJSON = generalizationService.generalizationRequest(layerJson); //일반화 요청
		
		return genAftJSON;
=======
		return "map/generalizationMap";
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
	}
}
