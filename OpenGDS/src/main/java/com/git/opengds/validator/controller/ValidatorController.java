package com.git.opengds.validator.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.git.opengds.common.AbstractController;
import com.git.opengds.common.TestSession;
import com.git.opengds.common.TestSessionList;
import com.git.opengds.service.builder.ValidatorService;

@Controller("validatorController")
@RequestMapping("/validator")
public class ValidatorController extends AbstractController{

	@Autowired
	ValidatorService validatorService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/validate.ajax")
	@ResponseBody 
	public JSONObject geoserverAddLoadAjax(HttpServletRequest request, @RequestBody String geo) throws Exception {

		long timeDifference;
		
		System.out.println("들어왔당들어왔당들어왔당들어왔당들어왔당들어왔당들어왔당들어왔당들어왔당들어왔당들어왔당들어왔당들어왔당들어왔당들어왔당들어왔당들어왔당들어왔당들어왔당들어왔당");
		
		TestSession testSession = createTestSession(request, geo);
		
		JSONObject resultJSON = new JSONObject();
		resultJSON = validatorService.autoValidation(geo);
		
		long responseTime = System.currentTimeMillis();
		
		//응답시간
		testSession.setResponseTime(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(new Date(System.currentTimeMillis())));
		
		//요청시간-응답시간
		timeDifference = responseTime-Long.valueOf(testSession.getRequestTime());
		
		//요청시간 업데이트
		testSession.setRequestTime(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(new Date(Long.valueOf(testSession.getRequestTime()))));
		
		//시간차이 SET
		testSession.setTimeDifference(timeDifference/1000);
		
		
		TestSessionList sessionList = (TestSessionList) getSession(request, TestSessionList.Type.TESTSESSIONLIST.getTypeName());;
		
		//세션 업데이트 시키기
		sessionList.updateTestSession(testSession);
		updateSession(request, TestSessionList.Type.TESTSESSIONLIST.getTypeName(), sessionList);
		
		resultJSON.put("testSessionList", sessionList);
		
		// for (int j = 0; j < 100000; j++) {
		// System.out.println(j);
		// }

		// try {
		// Thread.sleep(10000);
		// } catch (Exception e) {
		// TODO: handle exception 

		// }
		// try {
		return resultJSON;
		// } catch (Exception e) {
		// System.out.println("터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐");
		// Thread.sleep(20000);
		// return null;
		// }

		// return null;
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/testSessionRemoveAjax.ajax")
	@ResponseBody
	public JSONObject testSessionRemoveAjax(HttpServletRequest request){
		JSONObject returnJSON = new JSONObject();
		
		removeSession(request, TestSessionList.Type.TESTSESSIONLIST.getTypeName());
		
		returnJSON.put("flag", true);
		
		return returnJSON;
	}
	
	/**
	 *
	 * @author SG.Lee
	 * @Date
	 * @param
	 * @return
	 * @throws
	 * */
	@SuppressWarnings("unused")
	private TestSession createTestSession(HttpServletRequest request, String geo){ 
		TestSession testSession;
		TestSessionList sessionList;
		String serverName;
		int requestSize;
		String requestTime;
		String responseTime="";
		long timeDifference=0;
		JSONObject validatorInfo;
		
		if(request!=null && geo.length()!=0){
//			serverName = request.getRemoteAddr() +"-0";
			serverName = request.getRemoteAddr();
			requestSize = geo.length();
			requestTime = String.valueOf(System.currentTimeMillis());
			validatorInfo = new JSONObject();
			testSession = new TestSession(serverName, requestSize, requestTime, responseTime, timeDifference, validatorInfo);
			
			if(testSessionListCheck(request)){
				sessionList = (TestSessionList) getSession(request, TestSessionList.Type.TESTSESSIONLIST.getTypeName());
				
				JSONObject resultCheck = testSessionCheck(request, serverName);
				
				if((Boolean) resultCheck.get("flag")){
//					String convertServerName = request.getRemoteAddr() +"-" + String.valueOf(Integer.valueOf(orderIndex)+1);
					String convertServerName = request.getRemoteAddr() +"-" + String.valueOf(resultCheck.get("index"));
					testSession.setServerName(convertServerName);
				}
					sessionList.add(testSession);
			}
			else{
				sessionList = new TestSessionList();
				setSession(request, TestSessionList.Type.TESTSESSIONLIST.getTypeName(), sessionList);
				sessionList.add(testSession);
			}
		}
		else
			testSession = null;
		
		
		return testSession;
	}
	
	private boolean testSessionListCheck(HttpServletRequest request){
		boolean checkFlag = false;
		
		TestSessionList sessionList = (TestSessionList)getSession(request, TestSessionList.Type.TESTSESSIONLIST.getTypeName());
		
		if(sessionList==null){
			checkFlag = false;
		}
		else
			checkFlag = true;
		
		return checkFlag;
	}
	
	private JSONObject testSessionCheck(HttpServletRequest request, String serverName){
		JSONObject returnJSONObject = new JSONObject();
		
		TestSessionList sessionList = (TestSessionList)getSession(request, TestSessionList.Type.TESTSESSIONLIST.getTypeName());
		
		if(serverName!=null && serverName!=""){
			returnJSONObject = sessionList.checkTestSession(serverName);
		}
		else
			returnJSONObject = null;
			
		return returnJSONObject;
	}
}
