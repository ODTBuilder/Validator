package com.git.opengds.service.builder;

import org.json.simple.JSONObject;

public interface ValidatorService {

	public JSONObject autoValidation(String layerJSON) throws Exception;
}
