package com.git.opengds.service.builder;

import org.geotools.feature.SchemaException;
import org.json.simple.JSONObject;

public interface ValidatorService {

	public JSONObject autoValidation(JSONObject layerJSON) throws SchemaException;
	
}
