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

		System.out
				.println("들어왔당들어왔당들어왔당들어왔당들어왔당들어왔당들어왔당들어왔당들어왔당들어왔당들어왔당들어왔당들어왔당들어왔당들어왔당들어왔당들어왔당들어왔당들어왔당들어왔당");
//		// for (int j = 0; j < 100000; j++) {
//		// System.out.println(j);
//		// }
//
//		try {
//			Thread.sleep(20000);
//		} catch (Exception e) {
//		}

		// }
		// try {
	
		return validatorService.autoValidation(geo);
		// } catch (Exception e) {
		// System.out.println("터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐터짐");
		// Thread.sleep(20000);
		// return null;
		// }

		// return null;
	}
}
