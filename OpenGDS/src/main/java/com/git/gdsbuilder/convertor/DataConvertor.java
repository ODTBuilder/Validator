package com.git.gdsbuilder.convertor;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
<<<<<<< HEAD
=======
import java.util.LinkedList;
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
import java.util.List;

import org.geotools.data.DataUtilities;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

public class DataConvertor {

	/**
	 * SimpleFeatureCollection을 JSONObject로 변환하여 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.02
	 * @param simpleFeatureCollection
	 *            변환할 SimpleFeatureCollection
	 * @return JSONObject
	 */
	public JSONObject convertToGeoJSON(SimpleFeatureCollection simpleFeatureCollection) {

		return buildFeatureCollection(simpleFeatureCollection);

	}

	@SuppressWarnings("unchecked")
	private JSONObject buildFeatureCollection(SimpleFeatureCollection featureCollection) {

<<<<<<< HEAD
		JSONArray features = new JSONArray();
=======
		List<JSONObject> features = new LinkedList<JSONObject>();
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
		JSONObject obj = new JSONObject();
		obj.put("type", "FeatureCollection");
		obj.put("features", features);
		SimpleFeatureIterator simpleFeatureIterator = featureCollection.features();

<<<<<<< HEAD
		// int i = 0;
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			features.add(buildFeature(simpleFeature));
			/*
			 * if (i == 0) { obj.put("propertyType",
			 * buildPropertiesType(simpleFeature)); i++; }
			 */
=======
		int i = 0;
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			features.add(buildFeature(simpleFeature));
			if (i == 0) {
				obj.put("propertyType", buildPropertiesType(simpleFeature));
				i++;
			}
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
		}
		return obj;
	}

	@SuppressWarnings("unchecked")
	private JSONObject buildFeature(SimpleFeature simpleFeature) {

		JSONObject obj = new JSONObject();
		obj.put("type", "Feature");
		obj.put("id", simpleFeature.getID());
		obj.put("geometry", buildGeometry((Geometry) simpleFeature.getDefaultGeometry()));
		obj.put("properties", buildProperties(simpleFeature));
		return obj;

	}

	@SuppressWarnings("unchecked")
	private JSONObject buildProperties(SimpleFeature simpleFeature) {

		JSONObject obj = new JSONObject();
		Collection<Property> properties = simpleFeature.getProperties();

		for (Property property : properties) {
			obj.put(property.getName().toString(), property.getValue() == null ? "" : property.getValue().toString());
		}
		return obj;
	}

	private List<String> buildPropertiesType(SimpleFeature simpleFeature) {

		Collection<Property> properties = simpleFeature.getProperties();
		List<String> typeArray = new ArrayList<String>();
		for (Property property : properties) {
			String tempType = property.getType().toString();
			int firstIndex = tempType.indexOf("<");
			int lastIndex = tempType.lastIndexOf(">");
			String propertyType = tempType.substring(firstIndex + 1, lastIndex);
			typeArray.add(propertyType);
		}
		return typeArray;
	}

	private JSONObject buildGeometry(Geometry geometry) {

		GeometryJSON gjson = new GeometryJSON();
		Object obj = JSONValue.parse(gjson.toString(geometry));
		JSONObject jsonObj = (JSONObject) obj;
		return jsonObj;

	}

	/**
	 * 속성을 가진 JSONObject를 SimpleFeatureCollection으로 변환하여 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.02
	 * @param geo
	 *            변환할 JSONObject
	 * @param attribute
	 *            속성값
	 * @return JSONObject
	 * @throws SchemaException
	 */
	public SimpleFeatureCollection converToSimpleFeatureCollection(JSONObject geo, JSONObject attribute) throws SchemaException {

		return buildFeatureCollection(geo, attribute);

	}

	private SimpleFeatureCollection buildFeatureCollection(JSONObject geo, JSONObject attribute) throws SchemaException {

		DefaultFeatureCollection defaultFeatureCollection = new DefaultFeatureCollection();

		JSONArray features = (JSONArray) geo.get("features");
<<<<<<< HEAD
		int featureSize = features.size();
		for (int i = 0; i < featureSize; i++) {
=======
		for (int i = 0; i < features.size(); i++) {
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
			JSONObject feature = (JSONObject) features.get(i);
			if (feature.get("properties") != null) {
				defaultFeatureCollection.add(buildFeature(feature, attribute));
			} else {
				defaultFeatureCollection.add(buildFeature(feature));
			}
		}
		return defaultFeatureCollection;
	}

	@SuppressWarnings("rawtypes")
	private SimpleFeature buildFeature(JSONObject feature, JSONObject attribute) throws SchemaException {

		JSONObject property = (JSONObject) feature.get("properties");
<<<<<<< HEAD
		String featureID = feature.get("id").toString();
=======
		String featureID = (String) feature.get("id");
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
		Geometry geometry = buildGeometry(feature);

		String geometryType = geometry.getGeometryType();
		// SimpleFeature
		SimpleFeatureType simpleFeatureType = null;
		SimpleFeature simpleFeature = null;

		int size = attribute.values().size() + 1;
		Object[] objects;
		objects = new Object[size];
		objects[0] = geometry;

		int j = 1;
		String temp = "";
		Iterator iterator = attribute.keySet().iterator();
		while (iterator.hasNext()) {
<<<<<<< HEAD
=======

>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
			String key = (String) iterator.next();
			Object value = property.get(key);
			String valueType = (String) attribute.get(key);

<<<<<<< HEAD
			if (valueType.equals("Long")) {
				valueType = "String";
				objects[j] = value.toString();
				temp += key + ":" + valueType + ",";
				j++;
			} else {
				objects[j] = value;
				temp += key + ":" + valueType + ",";
				j++;
			}
		}
		simpleFeatureType = DataUtilities.createType(featureID.toString(), "the_geom:" + geometryType + "," + temp.substring(0, temp.length() - 1));
=======
			objects[j] = value;
			temp += key + ":" + valueType + ",";
			j++;
		}

		simpleFeatureType = DataUtilities.createType(featureID, "the_geom:" + geometryType + "," + temp.substring(0, temp.length() - 1));
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
		simpleFeature = SimpleFeatureBuilder.build(simpleFeatureType, objects, featureID);

		return simpleFeature;
	}

	/**
	 * 속성이 없는 JSONObject를 SimpleFeatureCollection으로 변환하여 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.02
	 * @param geo
	 *            변환할 JSONObject
	 * @return JSONObject
	 * @throws SchemaException
	 */
	public SimpleFeatureCollection converToSimpleFeatureCollection(JSONObject geo) throws SchemaException {

		return buildFeatureCollection(geo);

	}

	private SimpleFeatureCollection buildFeatureCollection(JSONObject geo) throws SchemaException {

		DefaultFeatureCollection defaultFeatureCollection = new DefaultFeatureCollection();

		JSONArray features = (JSONArray) geo.get("features");
<<<<<<< HEAD
		int featuresSize = features.size();
		for (int i = 0; i < featuresSize; i++) {
=======
		for (int i = 0; i < features.size(); i++) {
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
			JSONObject feature = (JSONObject) features.get(i);
			defaultFeatureCollection.add(buildFeature(feature));
		}
		return defaultFeatureCollection;
	}

	private SimpleFeature buildFeature(JSONObject feature) throws SchemaException {

		String featureID = (String) feature.get("id");
		Geometry geometry = buildGeometry(feature);
<<<<<<< HEAD
		String geometryType = geometry.getGeometryType();

		SimpleFeatureType simpleFeatureType = simpleFeatureType = DataUtilities.createType(featureID, "the_geom:" + geometryType);
		SimpleFeature simpleFeature = simpleFeature = SimpleFeatureBuilder.build(simpleFeatureType, new Object[] { geometry }, featureID);
=======

		String geometryType = geometry.getGeometryType();

		SimpleFeatureType simpleFeatureType = null;
		SimpleFeature simpleFeature = null;

		simpleFeatureType = DataUtilities.createType(featureID, "the_geom:" + geometryType);
		simpleFeature = SimpleFeatureBuilder.build(simpleFeatureType, new Object[] { geometry }, featureID);

>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
		return simpleFeature;

	}

	private Geometry buildGeometry(JSONObject feature) {

		GeometryFactory geometryFactory = new GeometryFactory();

		JSONObject jsonGeometry = (JSONObject) feature.get("geometry");
		String jsonGeometryType = (String) jsonGeometry.get("type");

		// SimpleFeature
		if (jsonGeometryType.equals("Point")) {
			// Geometry
			JSONArray coordinates = (JSONArray) jsonGeometry.get("coordinates");
			Double x = (Double) coordinates.get(0);
			Double y = (Double) coordinates.get(1);
			Point point = geometryFactory.createPoint(new Coordinate(x, y));
			return point;

		} else if (jsonGeometryType.equals("LineString")) {

			// Geometry
			JSONArray outerCoordinates = (JSONArray) jsonGeometry.get("coordinates");
			Coordinate[] coordinateArray;
			coordinateArray = new Coordinate[outerCoordinates.size()];

<<<<<<< HEAD
			int outerCoordSize = outerCoordinates.size();
			for (int k = 0; k < outerCoordSize; k++) {
=======
			for (int k = 0; k < outerCoordinates.size(); k++) {
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
				JSONArray innerCoordinates = (JSONArray) outerCoordinates.get(k);
				Double x = (Double) innerCoordinates.get(0);
				Double y = (Double) innerCoordinates.get(1);
				coordinateArray[k] = new Coordinate(x, y);
			}
			LineString lineString = geometryFactory.createLineString(coordinateArray);
			return lineString;

		} else if (jsonGeometryType.equals("Polygon")) {

			// Geometry
			JSONArray outerCoordinates = (JSONArray) jsonGeometry.get("coordinates");

			Coordinate[] coordinateArray;
			LinearRing linearRing = null;
			LinearRing holes[] = null;
			Polygon polygon = null;

<<<<<<< HEAD
			int outerCoordSize = outerCoordinates.size();
			for (int k = 0; k < outerCoordSize; k++) {
				JSONArray innerCoordinates = (JSONArray) outerCoordinates.get(k);
				coordinateArray = new Coordinate[innerCoordinates.size()];
				int innerCoorSize = innerCoordinates.size();
				for (int r = 0; r < innerCoorSize; r++) {
					JSONArray innerCoor = (JSONArray) innerCoordinates.get(r);
=======
			for (int k = 0; k < outerCoordinates.size(); k++) {

				JSONArray innerCoordinates = (JSONArray) outerCoordinates.get(k);
				coordinateArray = new Coordinate[innerCoordinates.size()];

				for (int r = 0; r < innerCoordinates.size(); r++) {
					JSONArray innerCoor = (JSONArray) innerCoordinates.get(r);

>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
					Double x = (Double) innerCoor.get(0);
					Double y = (Double) innerCoor.get(1);
					coordinateArray[r] = new Coordinate(x, y);
				}
				linearRing = geometryFactory.createLinearRing(coordinateArray);
			}
			polygon = geometryFactory.createPolygon(linearRing, holes);
			return polygon;

		} else if (jsonGeometryType.equals("MultiPoint")) {

			// Geometry
			JSONArray outerCoordinates = (JSONArray) jsonGeometry.get("coordinates");
			Coordinate[] coordinateArray;
			coordinateArray = new Coordinate[outerCoordinates.size()];
<<<<<<< HEAD
			int outerCoordSize = outerCoordinates.size();
			for (int k = 0; k < outerCoordSize; k++) {
				// Geometry
				JSONArray innerCoordinates = (JSONArray) outerCoordinates.get(k);
				Double x = (Double) innerCoordinates.get(0);
				Double y = (Double) innerCoordinates.get(1);
=======

			for (int k = 0; k < outerCoordinates.size(); k++) {
				// Geometry
				JSONArray coordinates = (JSONArray) jsonGeometry.get("coordinates");
				Double x = (Double) coordinates.get(0);
				Double y = (Double) coordinates.get(1);
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
				coordinateArray[k] = new Coordinate(x, y);
			}
			MultiPoint multiPoint = geometryFactory.createMultiPoint(coordinateArray);
			return multiPoint;

		} else if (jsonGeometryType.equals("MultiLineString")) {

			// Geometry
			JSONArray outerCoordinates = (JSONArray) jsonGeometry.get("coordinates");
			Coordinate[] coordinateArray;
			LineString lineStrings[] = new LineString[outerCoordinates.size()];

<<<<<<< HEAD
			int outerCoordSize = outerCoordinates.size();
			for (int k = 0; k < outerCoordSize; k++) {
				JSONArray innerCoordinates = (JSONArray) outerCoordinates.get(k);
				coordinateArray = new Coordinate[innerCoordinates.size()];

				int innerCoorSize = innerCoordinates.size();
				for (int r = 0; r < innerCoorSize; r++) {
=======
			for (int k = 0; k < outerCoordinates.size(); k++) {
				JSONArray innerCoordinates = (JSONArray) outerCoordinates.get(k);
				coordinateArray = new Coordinate[innerCoordinates.size()];
				for (int r = 0; r < innerCoordinates.size(); r++) {
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
					JSONArray innerCoor = (JSONArray) innerCoordinates.get(r);
					Double x = (Double) innerCoor.get(0);
					Double y = (Double) innerCoor.get(1);
					coordinateArray[r] = new Coordinate(x, y);
				}
				lineStrings[k] = geometryFactory.createLineString(coordinateArray);
			}
			MultiLineString multiLineString = geometryFactory.createMultiLineString(lineStrings);
			return multiLineString;

		} else if (jsonGeometryType.equals("MultiPolygon")) {

			// Geometry
			JSONArray firstOuter = (JSONArray) jsonGeometry.get("coordinates");
			Coordinate[] coordinateArray;
			LinearRing linearRing = null;
			LinearRing holes[] = null;
			Polygon[] polygons = new Polygon[firstOuter.size()];

<<<<<<< HEAD
			int firstOutSize = firstOuter.size();
			for (int a = 0; a < firstOutSize; a++) {
				JSONArray firstInnerCoor = (JSONArray) firstOuter.get(a);

				int firstInnerSize = firstInnerCoor.size();
				for (int k = 0; k < firstInnerSize; k++) {
					JSONArray secondInnerCoor = (JSONArray) firstInnerCoor.get(k);
					coordinateArray = new Coordinate[secondInnerCoor.size()];

					int secondInnerSize = secondInnerCoor.size();
					for (int r = 0; r < secondInnerSize; r++) {
=======
			for (int a = 0; a < firstOuter.size(); a++) {
				JSONArray firstInnerCoor = (JSONArray) firstOuter.get(a);
				for (int k = 0; k < firstInnerCoor.size(); k++) {
					JSONArray secondInnerCoor = (JSONArray) firstInnerCoor.get(k);
					coordinateArray = new Coordinate[secondInnerCoor.size()];
					for (int r = 0; r < secondInnerCoor.size(); r++) {
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
						JSONArray thirdInnerCoor = (JSONArray) secondInnerCoor.get(r);

						Double x = (Double) thirdInnerCoor.get(0);
						Double y = (Double) thirdInnerCoor.get(1);

						coordinateArray[r] = new Coordinate(x, y);
					}
					linearRing = geometryFactory.createLinearRing(coordinateArray);
				}
				polygons[a] = geometryFactory.createPolygon(linearRing, holes);
			}
			MultiPolygon multiPolygon = geometryFactory.createMultiPolygon(polygons);
			return multiPolygon;
		}
		return null;
	}

	/**
	 * 오류 정보를 담고 있는 SimpleFeature를 생성하여 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.02
	 * @param errfeatureID
	 *            오류가 있는 SimpleFeature의 ID
	 * @param centroid
	 *            오류가 있는 SimpleFeature의 중심점
<<<<<<< HEAD
	 * @param errName
=======
	 * @param errType
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
	 *            오류 종류
	 * @return JSONObject
	 * @throws SchemaException
	 */
<<<<<<< HEAD
	public SimpleFeature createErrSimpleFeature(String errfeatureID, Geometry centroid, String errName, String errType) throws SchemaException {

		SimpleFeatureType simpleFeatureType = DataUtilities.createType(errfeatureID, "the_geom:Point,featureID:String,errorName:String,errorType:String");
		SimpleFeature simpleFeature = SimpleFeatureBuilder.build(simpleFeatureType, new Object[] { centroid, errfeatureID, errName, errType }, errfeatureID
				+ "_err");
=======
	public SimpleFeature createErrFeature(String errfeatureID, Geometry centroid, String errType) throws SchemaException {

		SimpleFeatureType simpleFeatureType = null;
		SimpleFeature simpleFeature = null;

		simpleFeatureType = DataUtilities.createType("", "the_geom:Point,errfeatureID:String,errType:String");
		simpleFeature = SimpleFeatureBuilder.build(simpleFeatureType, new Object[] { centroid, errfeatureID, errType }, null);
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c

		return simpleFeature;
	}

	/**
	 * 문자열로 된 JSONObject를 JSONObject 객체로 변환하여 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.02
	 * @param jsonString
	 *            변환할 JSONObject 객체
	 * @return JSONObject
	 */
	public JSONObject stringToJSON(String jsonString) {
<<<<<<< HEAD

		// System.out.println(jsonString);
=======
		
		System.out.println(jsonString);
		
		
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c

		JSONParser parser = new JSONParser();
		StringReader in = new StringReader(jsonString);

		try {
			Object object = parser.parse(in);
			return (JSONObject) object;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 클라이언트에서 받은 JSONString을 List<JSONObject>로 변환시켜준다.
	 * 
	 * @author SG.LEE
	 * @data 2016.02
	 * @param jsonString
	 *            오류레이어 JSONString
	 * @return List<org.json.JSONObject>
	 */
	public List<org.json.JSONObject> errStringToList(String jsonString) {
		List<org.json.JSONObject> jsonObjects = new ArrayList<org.json.JSONObject>();

		org.json.JSONArray array = new org.json.JSONArray(jsonString);

<<<<<<< HEAD
		int arraySize = array.length();
		for (int i = 0; i < arraySize; i++) {
=======
		for (int i = 0; i < array.length(); i++) {
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
			org.json.JSONObject object = new org.json.JSONObject();
			object = (org.json.JSONObject) array.get(i);
			jsonObjects.add(object);
		}
		return jsonObjects;
	}
}
