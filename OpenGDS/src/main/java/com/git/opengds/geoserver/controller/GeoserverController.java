package com.git.opengds.geoserver.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.git.gdsbuilder.convertor.DataConvertor;
import com.git.gdsbuilder.geoserver.data.DTGeoserver;
import com.git.gdsbuilder.geoserver.data.DTGeoserverList;
import com.git.opengds.common.AbstractController;
import com.git.opengds.geoserver.service.GeoserverService;


/**
 * Geoserver와 관련된 요청을 처리한다.
 * @author SG.Lee
 * @Date 2016.07.12
 * */
@Controller("geoserverController")
@RequestMapping("/geoserver")
public class GeoserverController extends AbstractController{
	
	@Autowired
	private GeoserverService geoService; //Geoserver Service 생성
	
	
	/**
	 * client에서 보낸 URL을 통한 서버 체크
	 * @author SG.Lee
	 * @Date 2016.07.12
	 * @param serverURL - Geoserver URL
	 * @return JSONObject { DTGeoserver : DTGeoserver - 지오서버 객체 } 
	 * @throws
	 * */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/serverChkAjax.ajax")
	@ResponseBody
	public JSONObject serverChkAjax(HttpServletRequest request,
			@RequestParam(value="serverURL", required=true) String serverURL){
		
		JSONObject object = new JSONObject();
		DTGeoserverList list = (DTGeoserverList) getSession(request, DTGeoserverList.Type.DTSERVERLIST.getTypeName());
		boolean flag = false;
		
		
		if(serverURL!=null && serverURL!=""){
			DTGeoserver server = list.getDTGeoserver(serverURL);
			if(server!=null){
				flag = true;
			}
			object.put("DTGeoserver", server);
		}
		object.put("flag", flag);
		return object;
	}
	
	/**
	 * 세션에 저장되어 있는 서버리스트 반환을 수행한다.
	 * @author SG.Lee
	 * @Date 216.07.12
	 * @param
	 * @return JSONObject { serverList : DTGeoserverList }
	 * @throws
	 * */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/loadingServerAjax.ajax")
	@ResponseBody
	public JSONObject geoserverLoadAjax(HttpServletRequest request){
		
		JSONObject object = new JSONObject();
		
		object.put("serverList", (DTGeoserverList) getSession(request, DTGeoserverList.Type.DTSERVERLIST.getTypeName()));
		
		return object;
	}
	
	
	/**
	 * Geoserver 추가요청을 수행한다.
	 * @author SG.Lee
	 * @Date 2016.07.12
	 * @param JSONObject - 서버 생성과 관련된 파라미터를 포함한다.
	 *					{ serverName : 서버이름
	 *					  serverURL : 서버URL
	 *					  serverID : 서버 ID
	 *					  serverPW : 서버 비밀번호 }
	 * @return JSONObejct
	 * 		   { flag : 서버 추가 결과
	 * 			 serverList : 서버 추가후 업데이트된 서버리스트 }
	 * @throws Exception
	 * */
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/geoserverAddLoadAjax.ajax")
	@ResponseBody
	public JSONObject geoserverAddLoadAjax(HttpServletRequest request,@RequestBody JSONObject json)
			throws Exception {
		JSONObject addReultJSON  = new JSONObject(); //서버추가 결과 JSONObject생성
		
		
		boolean flag = false;
		DataConvertor convertor = new DataConvertor();
		
		JSONObject jsonObj = convertor.stringToJSON(json.toString()); //String JSON -> JSONObject 변환    
		String name = (String) jsonObj.get("serverName");
		String url = (String) jsonObj.get("serverURL");
		String id = (String) jsonObj.get("serverID");
		String pw = (String) jsonObj.get("serverPW");
		
		//세션에 있는 GeoserverList 생성
		DTGeoserverList dtGeoserverList = new DTGeoserverList();
		dtGeoserverList = (DTGeoserverList) getSession(request, DTGeoserverList.Type.DTSERVERLIST.getTypeName());
		
		//geoservice에 Server생성 요청
		DTGeoserver geoserver = geoService.createDTGeoserver(name, id, pw, url);

		//생성 Ture -> DTGeoserverList에 서버 추가 -> 세션 저장
		if (geoserver != null) {
			if (dtGeoserverList == null) {
				dtGeoserverList = new DTGeoserverList();
				dtGeoserverList.add(geoserver);
				setSession(request,DTGeoserverList.Type.DTSERVERLIST.getTypeName(),dtGeoserverList);
			} else {
				dtGeoserverList.add(geoserver);
				updateSession(request, DTGeoserverList.Type.DTSERVERLIST.getTypeName(), dtGeoserverList);
			}
			flag = true;
		}
		else{
			if(dtGeoserverList == null){
				dtGeoserverList = new DTGeoserverList();
				setSession(request,DTGeoserverList.Type.DTSERVERLIST.getTypeName(),dtGeoserverList);
			}
			flag = false;
		}
		
		addReultJSON.put("flag", flag);
		addReultJSON.put("serverList", dtGeoserverList);
		return addReultJSON;
	}
	
	
	/**
	 * 서버 삭제 요청을 수행한다.
	 * @author SG.Lee
	 * @Date 2016.07.12
	 * @param JSONObject - 서버삭제와 관련된 파라미터를 포함한다.
	 * 					{ serverList : 삭제할 서버 리스트 }
	 * @return JSONObject
	 * 		   { flag : 삭제여부
	 *           serverList : 삭제후 업데이트된 서버리스트 }
	 * @throws
	 * */
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/geoserverRemoveLoadAjax.ajax")
	@ResponseBody
	public JSONObject geoserverRemoveLoadAjax(HttpServletRequest request,@RequestBody JSONObject json)
			throws Exception {
		JSONObject removeResultJSON = new JSONObject(); //삭제결과 JSONObject
		boolean flag = false; //결과 flag
		
		DTGeoserverList dtGeoserverList = new DTGeoserverList(); //DTGeoserverList 객체생성
		
		DataConvertor convertor = new DataConvertor(); // 데이터 변환 객체 생성
		
		JSONObject jsonObj = convertor.stringToJSON(json.toString()); // String JSON -> JSONObject
		JSONArray serverList = (JSONArray) jsonObj.get("serverList");
		
		//세션에 저장되어 있는 서버리스트 가져온다.
		List<DTGeoserver> sessionServerList = new ArrayList<DTGeoserver>();
		sessionServerList = (List<DTGeoserver>) getSession(request, DTGeoserverList.Type.DTSERVERLIST.getTypeName());
		
		for(int i=0;i<serverList.size();i++){
			JSONObject clGeoserver = new JSONObject();
			clGeoserver = (JSONObject) serverList.get(i);
			for(int j=0; j<sessionServerList.size();j++){
				//Client에서 넘어온 정보로 DTGeoserver 생성
				DTGeoserver geoserver = new DTGeoserver();
				geoserver = sessionServerList.get(j);
				String seName = "";
				String seURL = "";
				String clName = "";
				String clUrl = "";
				
				//요청온 내용과 비교하여 서버 삭제
				if(geoserver!=null){
					dtGeoserverList = new DTGeoserverList(); //객체 초기화
					seName = geoserver.getServerName();
					seURL = geoserver.getUrl();
					clName = (String) clGeoserver.get("serverName");
					clUrl = (String) clGeoserver.get("url");
					if(seName.equals(clName)&&seURL.equals(clUrl)){
						flag = true;
					}
					else{
						dtGeoserverList.add(geoserver);
					}
				}
			}
		}
		
		updateSession(request, DTGeoserverList.Type.DTSERVERLIST.getTypeName(), dtGeoserverList); //세션 업데이트
		removeResultJSON.put("flag", flag);
		removeResultJSON.put("serverList", dtGeoserverList);
		
		return removeResultJSON;
	}
}
