package com.git.opengds.controller.builder;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.git.opengds.service.builder.ValidatorService;

@Controller("qaController")
@RequestMapping("/qualityAssurance")
public class ValidatorController {
	
	@Autowired
	ValidatorService validatorService;
	
	@ResponseBody
	@RequestMapping(value = "/qa.ajax")
	public JSONObject qualityAssurance(HttpServletRequest request, @RequestBody JSONObject geo) throws Exception {

		return null;
	}
}
