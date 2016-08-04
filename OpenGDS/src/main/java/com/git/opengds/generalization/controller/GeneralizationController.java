package com.git.opengds.generalization.controller;

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
@RequestMapping("/generalization")
public class GeneralizationController {

	private static final Logger logger = LoggerFactory.getLogger(GeneralizationController.class);

	@RequestMapping(value = "/generalization.do", method = RequestMethod.GET)
	public String tempPage(Model model) {
		logger.info("Opened page is {}.", "temp");
		
		return "map/generalizationMap";
	}
}
