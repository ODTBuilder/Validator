package com.git.opengds.controller.builder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.geotools.feature.SchemaException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.git.opengds.service.builder.ValidatorService;
import com.vividsolutions.jts.io.ParseException;

/**
 * Handles requests for the application home page.
 */
@Controller
public class BuilderController {

	private static final Logger logger = LoggerFactory.getLogger(BuilderController.class);

	@Autowired
	ValidatorService validatorService;

	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @throws org.json.simple.parser.ParseException
	 * @throws SchemaException
	 */
	@RequestMapping(value = "/builder.do", method = RequestMethod.GET)
	public String builder(Model model) throws org.json.simple.parser.ParseException, SchemaException {
		logger.info("Opened page is {}.", "builder");

		// model.addAttribute("serverTime", formattedDate );

		JSONParser parser = new JSONParser();
		try {

			Object obj = parser.parse(new FileReader("c:\\test.txt"));
			JSONObject jsonObject = (JSONObject) obj;
			validatorService.autoValidation(jsonObject);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("돌아옴");
		
		return "builder";
	}
}
