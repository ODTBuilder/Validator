package com.git.opengds.operation.service;

import org.geotools.feature.SchemaException;
import org.json.simple.JSONObject;

public interface OperatorService {
	
	public JSONObject autoOperation(JSONObject featureJSON) throws SchemaException;
	
}
