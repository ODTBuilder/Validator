package com.git.opengds.validator.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.git.opengds.service.builder.ValidatorService;

@Controller("validatorController")
@RequestMapping("/validator")
public class ValidatorController {

	@Autowired
	ValidatorService validatorService;

	@RequestMapping(value = "/validate.ajax")
	@ResponseBody
	public JSONObject geoserverAddLoadAjax(HttpServletRequest request,
			@RequestBody String geo) throws Exception {

	
	return validatorService.autoValidation(geo);
	}
}
