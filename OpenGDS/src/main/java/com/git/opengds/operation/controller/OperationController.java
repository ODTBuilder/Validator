package com.git.opengds.operation.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.git.opengds.operation.service.OperatorService;

@Controller("operationController")
@RequestMapping(value="/operator")
public class OperationController {

	@Autowired
	OperatorService operatorService;
	
	@RequestMapping(value = "/operation.ajax")
	@ResponseBody
	public JSONObject geoserverAddLoadAjax(HttpServletRequest request,@RequestBody JSONObject geo)
			throws Exception {
		
		System.out.println(geo);
		
		return operatorService.autoOperation(geo);
	}
}
