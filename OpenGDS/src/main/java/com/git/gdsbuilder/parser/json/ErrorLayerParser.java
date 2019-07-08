/*
 *    OpenGDS/Builder
 *    http://git.co.kr
 *
 *    (C) 2014-2017, GeoSpatial Information Technology(GIT)
 *    
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 3 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package com.git.gdsbuilder.parser.json;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.geotools.data.DataUtilities;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.git.gdsbuilder.type.validate.error.ErrorFeature;
import com.git.gdsbuilder.type.validate.error.ErrorLayer;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;

/**
 * 오류 레이어를 GeoJSON 타입으로 변환하는 클래스
 * 
 * @author DY.OH
 *
 */
public class ErrorLayerParser {

	/**
	 * 오류 레이어를 GeoJSON 타입으로 변환
	 * 
	 * <p>
	 * <em>GeoJSON Example :</em>
	 * <pre>
	 * <code>
	 * {
	 *  "type": "FeatureCollection",
	 *  "features": [
	 * 	 {
	 * 	 "type": "Feature",
	 * 	 "geometry": {
	 * 	  "type": "Point",
	 * 	  "coordinates": [102.0, 0.5]
	 * 	  },
	 * 	  "properties": {
	 * 	   "layerID": "layerID",
	 *	   "featureID": "featureID",
	 * 	   "errType": "Graphic",
	 * 	   "errName": "EntityDuplicated",
	 * 	   "comment": null
	 * 	  }
	 * 	 }
	 *  ]
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param errorLayer 오류 레이어
	 * @return JSONObject 변환된 GeoJSON
	 * @author DY.OH
	 */
	public JSONObject parseGeoJSON(ErrorLayer errorLayer) {

		DefaultFeatureCollection collection = new DefaultFeatureCollection();

		if (errorLayer != null) {
			List<ErrorFeature> errList = errorLayer.getErrFeatureList();
			if (errList.size() > 0) {
				for (int i = 0; i < errList.size(); i++) {
					ErrorFeature err = errList.get(i);
					String layerID = err.getLayerID();
					String featureID = err.getFeatureID();
					String errType = err.getErrType();
					String errName = err.getErrName();
					Geometry errPoint = err.getErrPoint();
					String featureIdx = "f_" + i;
					String geomType = errPoint.getGeometryType();
					String comment = err.getComment();

					SimpleFeatureType sfType;
					try {
						sfType = DataUtilities.createType(featureIdx,
								"layerID:String,featureID:String,errType:String,errName:String,comment:String,the_geom:"
										+ geomType);
						SimpleFeature newSimpleFeature = SimpleFeatureBuilder.build(sfType,
								new Object[] { layerID, featureID, errType, errName, comment, errPoint }, featureIdx);

						collection.add(newSimpleFeature);
					} catch (SchemaException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		JSONObject json = buildFeatureCollection(collection);
		return json;
	}

	private JSONObject buildFeatureCollection(SimpleFeatureCollection featureCollection) {
		List<JSONObject> features = new LinkedList<JSONObject>();
		SimpleFeatureIterator simpleFeatureIterator = featureCollection.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			features.add(buildFeature(simpleFeature));
		}

		JSONObject obj = new JSONObject();
		obj.put("type", "FeatureCollection");
		obj.put("features", features);
		return obj;
	}

	private JSONObject buildFeature(SimpleFeature simpleFeature) {
		JSONObject obj = new JSONObject();
		obj.put("type", "Feature");
		obj.put("geometry", buildGeometry((Geometry) simpleFeature.getDefaultGeometry()));
		obj.put("properties", buildProperties(simpleFeature));
		return obj;

	}

	private JSONObject buildProperties(SimpleFeature simpleFeature) {
		JSONObject obj = new JSONObject();
		Collection<Property> properties = simpleFeature.getProperties();
		for (Property property : properties) {
			obj.put(property.getName().toString(), property.getValue() == null ? "" : property.getValue().toString());
		}
		return obj;
	}

	private JSONObject buildGeometry(Geometry geometry) {

		JSONObject jsonObj = new JSONObject();

		// coordinates
		Coordinate coor = geometry.getCoordinate();
		double x = coor.x;
		double y = coor.y;
		JSONArray coors = new JSONArray();
		coors.add(x);
		coors.add(y);

		jsonObj.put("coordinates", coors);

		// type
		String type = geometry.getGeometryType();
		jsonObj.put("type", type);

		return jsonObj;
	}
}
