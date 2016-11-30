package com.git.opengds.service.builder;

<<<<<<< HEAD
=======
import org.geotools.feature.SchemaException;
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
import org.json.simple.JSONObject;

public interface ValidatorService {

<<<<<<< HEAD
	public JSONObject autoValidation(String layerJSON) throws Exception;
=======
	public JSONObject autoValidation(JSONObject layerJSON) throws SchemaException;
	
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
}
