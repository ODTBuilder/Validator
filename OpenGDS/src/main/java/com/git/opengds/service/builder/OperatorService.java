package com.git.opengds.service.builder;

import org.geotools.feature.SchemaException;
import org.json.simple.JSONObject;

public interface OperatorService {
	
	public JSONObject autoOperation(JSONObject featureJSON) throws SchemaException;
	
}
