package com.git.opengds.operation.service;

import java.util.Iterator;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.SchemaException;
import org.json.simple.JSONObject;
import org.opengis.feature.simple.SimpleFeature;
import org.springframework.stereotype.Service;

import com.git.gdsbuilder.convertor.DataConvertor;
import com.git.gdsbuilder.editor.operation.LayerDifference;
import com.git.gdsbuilder.editor.operation.LayerIntersection;
import com.git.gdsbuilder.editor.operation.LayerUnion;
import com.git.gdsbuilder.type.layer.Layer;

@Service
public class OperatorServiceImpl implements OperatorService {

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject autoOperation(JSONObject featureJSON) throws SchemaException {
		System.out.println("연산전 : " + featureJSON.toString()); //
		System.out.println();
		
		DataConvertor convertor = new DataConvertor();
		// String으로 들어온 JSONObejct를 JSONObejct객체로 변환해준다.
		JSONObject inputObject = new JSONObject();
		inputObject = convertor.stringToJSON(featureJSON.toString());
		
		// JSONObject객체에서 필요한 value값을 가져온다.
		JSONObject operation =  convertor.stringToJSON(inputObject.get("operator").toString());
		String operationName = (String) operation.get("operationName");
		JSONObject layers = convertor.stringToJSON(operation.get("layers").toString());
		
		Iterator<String> iterator = layers.keySet().iterator();// layer name 값
		JSONObject object = new JSONObject();
		
		while(iterator.hasNext()){
			String id = iterator.next(); 
			JSONObject layerName = (JSONObject) layers.get(id);
			
			String featureStr = String.valueOf(layerName.get("feature")); // feature : value값
			String operationOption = (String) layerName.get("operationOption");
			String layerType = (String) layerName.get("layerType");
			
			DataConvertor dataConvertor = new DataConvertor();
			JSONObject feature = dataConvertor.stringToJSON(featureStr);
			
			SimpleFeatureCollection simpleFeatureCollection = dataConvertor.converToSimpleFeatureCollection(feature);
			Layer layer = new Layer(layerType, simpleFeatureCollection);
			object.put(operationOption, layer);
		}
		
		Layer layer1 = (Layer) object.get("operandLayerA"); // 연산할 레이어
		Layer layer2 = (Layer) object.get("operandLayerB");
		
		SimpleFeatureCollection simpleFeatureCollection1 = (SimpleFeatureCollection) layer1.getSimpleFeatureCollection();
		SimpleFeatureCollection simpleFeatureCollection2 = (SimpleFeatureCollection) layer2.getSimpleFeatureCollection();
		
		String type1 = layer1.getType();
		String type2 = layer2.getType();
		
		Layer layerA = new Layer(type1, simpleFeatureCollection1);
		Layer layerB = new Layer(type2, simpleFeatureCollection2);
		
		SimpleFeatureCollection returnSF = null;
		
		if(operationName.equals(LayerUnion.Type.LAYERUNION.getType())){
			LayerUnion layerUnion = new LayerUnion(layerA, layerB);
			returnSF = layerUnion.operateFeatures();
		}else if(operationName.equals(LayerDifference.Type.LAYERDIFFERENCE.getType())){
			LayerDifference layerDifference = new LayerDifference(layerA, layerB);
			returnSF = layerDifference.operateFeatures();
		}else if(operationName.equals(LayerIntersection.Type.LAYERINTERSECTION.getType())){
			LayerIntersection layerIntersection = new LayerIntersection(layerA, layerB);
			returnSF = layerIntersection.operateFeatures();
			System.out.println(returnSF.toString());
			System.out.println();
		}
		
		String featureType = "";
		if(returnSF!=null){
			SimpleFeatureIterator featureIterator = returnSF.features();
			if(featureIterator.hasNext()){
				while(featureIterator.hasNext()){
					SimpleFeature simpleFeature = featureIterator.next();
					featureType = simpleFeature.getDefaultGeometry().getClass().getSimpleName();
					break;
				}
			}
			else
				return null;
		}
		
		DataConvertor dataConvertor = new DataConvertor();
		JSONObject jsonFeatureCollection = dataConvertor.convertToGeoJSON(returnSF);
		
		JSONObject oplayer = new JSONObject();
		oplayer.put("feature", jsonFeatureCollection);
		oplayer.put("type", featureType);
		
		JSONObject jsonLayers = new JSONObject();
		jsonLayers.put("oplayer", oplayer);
		
		JSONObject resultJSONObject = new JSONObject();
		resultJSONObject.put("layers", jsonLayers);
		
		System.out.println("연산후 : " + resultJSONObject.toString());
		
		return resultJSONObject;
	}
}
