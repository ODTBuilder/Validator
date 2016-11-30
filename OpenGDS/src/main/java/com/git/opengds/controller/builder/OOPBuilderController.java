package com.git.opengds.controller.builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class OOPBuilderController {
	
	private static final Logger logger = LoggerFactory.getLogger(OOPBuilderController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/oop.do", method = RequestMethod.GET)
	public String tempPage(Model model) {
		logger.info("{} page is opened.", "OOP");
		
		return "map/oop_builder";
	}
	
}
