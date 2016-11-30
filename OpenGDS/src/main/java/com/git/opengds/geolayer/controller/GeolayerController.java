package com.git.opengds.geolayer.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.git.gdsbuilder.convertor.DataConvertor;
import com.git.gdsbuilder.geolayer.data.DTGeoLayerList;
import com.git.gdsbuilder.geolayer.factory.DTGeoLayerPublisher;
import com.git.gdsbuilder.geoserver.data.DTGeoserver;
import com.git.gdsbuilder.geoserver.data.DTGeoserverList;
import com.git.gdsbuilder.geosolutions.geoserver.rest.GeoServerRESTManager;
import com.git.opengds.common.AbstractController;
import com.git.opengds.geoserver.service.GeoserverService;


/**
 * 레이어와 관련된 요청을 수행한다.
 * @author SG.Lee
 * @Date 2016.08.16
 * */
@Controller("geolayerController")
@RequestMapping("/geolayer")
public class GeolayerController extends AbstractController{
	
	@Autowired
	private GeoserverService geoService; //Geoserver Service 생성
	

	/** 
	 * 레이어 리스트 요청을 수행한다.
	 * @author SG.Lee
	 * @Date 2016.08.16
	 * @param JSONObject - 레이어 로드를 위한 파라미터를 포함한다.
	 *                   { serverName : 서버이름 }
	 * @return JSONObject
	 *         { layerList : 반환 레이어 리스트 }
	 * @throws
	 * */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/loadLayerAjax.ajax")
	@ResponseBody
	public JSONObject loadLayerAjax(HttpServletRequest request, @RequestBody JSONObject geo){
		DataConvertor convertor = new DataConvertor(); //데이터 변환 객체 생성
		JSONObject jsonObj = convertor.stringToJSON(geo.toString()); //String JSON -> JSONObject
		
		//반환객체 생성
		JSONObject layerListJSON = new JSONObject();
		DTGeoLayerList layerList = new DTGeoLayerList();
		
		String name = (String) jsonObj.get("serverName");
		
		//세션에 저장돼어 있는 GeoserverList 가져옴
		DTGeoserverList geoserverList = new DTGeoserverList();
		geoserverList = (DTGeoserverList) getSession(request, DTGeoserverList.Type.DTSERVERLIST.getTypeName());
		
		
		//서버리스트중에 Client에서 넘어온 이름과 동일한 서버객체 받음
		DTGeoserver geoserver= geoserverList.getDTGeoserver(name);
		GeoServerRESTManager manager = null;
		manager = geoService.getGeoserverRestManager(geoserver); //서버객체로 manager 생성
		
		if(manager!=null){
			DTGeoLayerPublisher publisher = new DTGeoLayerPublisher(manager);//DTGeoLayerPublisher
			layerList = publisher.getDTGeoLayerList();//publisher을 통해 해당 서버에 있는 레이어 리스트 가져옴
		}
		else
			layerList = null;
			
		layerListJSON.put("layerList", layerList);
		
		return layerListJSON;
	}
}
