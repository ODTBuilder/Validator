package com.git.opengds.zipfile;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.SchemaException;
import org.json.simple.JSONObject;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;

import com.git.gdsbuilder.convertor.DataConvertor;

public class CreateFile {
	private static final String OUTPUT_DIR = "d:\\"; 
	
	@SuppressWarnings("unchecked")
	public void makeFile(JSONObject jsonObject) throws SchemaException, IOException{
		
		ConvertGeojson convertGeojson = new ConvertGeojson();
		DataConvertor dataConvertor =  new DataConvertor();
		
		String strJson = jsonObject.toString();
		JSONObject json = dataConvertor.stringToJSON(strJson);
		JSONObject layerJSON = dataConvertor.stringToJSON(json.get("layers").toString());
		Iterator<String> layerIter = layerJSON.keySet().iterator();
		
		while (layerIter.hasNext()) {
			String layerID = layerIter.next();
			File targetFile = new File(OUTPUT_DIR, layerID); // OUTPUT_DIR 경로에 fileName으로 파일만들기
			FileUtils.forceMkdir(targetFile);  // 파일 생성
			String targetFilePath = targetFile.getName();
			String path = OUTPUT_DIR + targetFilePath + "\\"+targetFilePath + ".shp";
			
			JSONObject layer = (JSONObject) layerJSON.get(layerID);
			String stringGeo = String.valueOf(layer.get("feature"));
			String stringAtt = String.valueOf(layer.get("attribute"));

			JSONObject feature = (JSONObject) dataConvertor.stringToJSON(stringGeo);
			JSONObject attribute = (JSONObject) dataConvertor.stringToJSON(stringAtt);
			SimpleFeatureCollection simpleFeatureCollection = dataConvertor.converToSimpleFeatureCollection(feature,attribute);
		
			JSONObject jsonObject2 = dataConvertor.convertToGeoJSON(simpleFeatureCollection);
			
			try {
				convertGeojson.createSHP(simpleFeatureCollection, path);
			} catch (NoSuchAuthorityCodeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FactoryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
}
