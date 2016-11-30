package com.git.opengds.service.builder;

import java.util.Iterator;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.SchemaException;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.git.gdsbuilder.convertor.DataConvertor;
import com.git.gdsbuilder.editor.operation.LayerDifference;
import com.git.gdsbuilder.editor.operation.LayerIntersection;
import com.git.gdsbuilder.editor.operation.LayerUnion;

@Service
public class OperatorServiceImpl implements OperatorService {

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject autoOperation(JSONObject featureJSON) throws SchemaException {
		
		System.out.println("연산전 : " + featureJSON.toString());

		JSONObject operation = (JSONObject) featureJSON.get("operator");
		String operationName = (String) operation.get("operationName");
		JSONObject layers = (JSONObject) operation.get("layers");
		
		Iterator<String> iterator = layers.keySet().iterator();// layer name 값
		JSONObject object = new JSONObject();
		
		while(iterator.hasNext()){
			String id = iterator.next(); 
			JSONObject layer = (JSONObject) layers.get(id);
			String featureStr = String.valueOf(layer.get("feature")); // feature : value값
			String operationOption = (String) layer.get("operationOption");
			
			DataConvertor dataConvertor = new DataConvertor();
			JSONObject feature = dataConvertor.stringToJSON(featureStr);
			
			SimpleFeatureCollection simpleFeatureCollection = dataConvertor.converToSimpleFeatureCollection(feature);
			object.put(operationOption, simpleFeatureCollection);
		}
		
		SimpleFeatureCollection simpleFeatureCollection1 = (SimpleFeatureCollection) object.get("operandLayerA");
		SimpleFeatureCollection simpleFeatureCollection2 = (SimpleFeatureCollection) object.get("operandLayerB");
		
		SimpleFeatureCollection returnSF = null;
		
		if(operationName.equals(LayerUnion.Type.LAYERUNION.getType())){
			LayerUnion layerUnion = new LayerUnion(simpleFeatureCollection1, simpleFeatureCollection2);
			returnSF = layerUnion.operateFeatures();
		}else if(operationName.equals(LayerDifference.Type.LAYERDIFFERENCE.getType())){
			LayerDifference layerDifference = new LayerDifference(simpleFeatureCollection1, simpleFeatureCollection2);
			returnSF = layerDifference.operateFeatures();
		}else if(operationName.equals(LayerIntersection.Type.LAYERINTERSECTION.getType())){
			LayerIntersection layerIntersection = new LayerIntersection(simpleFeatureCollection1, simpleFeatureCollection2);
			returnSF = layerIntersection.operateFeatures();
		}
		
		DataConvertor dataConvertor = new DataConvertor();
		JSONObject resultJSONObject = dataConvertor.convertToGeoJSON(returnSF);
		
		System.out.println("연산후 : " + resultJSONObject.toString());
		return resultJSONObject;
	}

}
