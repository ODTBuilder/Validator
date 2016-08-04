package com.git.gdsbuilder.editor.operation;

import java.util.Vector;

import org.geotools.data.DataUtilities;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Polygon;

public class LayerDifference implements Operatable {

	SimpleFeatureCollection simpleFeatureCollection1;
	SimpleFeatureCollection simpleFeatureCollection2;
	
	public enum Type {

		LAYERDIFFERENCE("LayerDifference");

		String typeName;

		Type(String typeName) {
			this.typeName = typeName;
		}

		public String getType() {
			return typeName;
		}
	};
	
	public LayerDifference(SimpleFeatureCollection simpleFeatureCollection1, SimpleFeatureCollection simpleFeatureCollection2) {
		super();
		this.simpleFeatureCollection1 = simpleFeatureCollection1;
		this.simpleFeatureCollection2 = simpleFeatureCollection2;
	}

	public SimpleFeatureCollection getSimpleFeatureCollection1() {
		return simpleFeatureCollection1;
	}

	public void setSimpleFeatureCollection1(SimpleFeatureCollection simpleFeatureCollection1) {
		this.simpleFeatureCollection1 = simpleFeatureCollection1;
	}

	public SimpleFeatureCollection getSimpleFeatureCollection2() {
		return simpleFeatureCollection2;
	}

	public void setSimpleFeatureCollection2(SimpleFeatureCollection simpleFeatureCollection2) {
		this.simpleFeatureCollection2 = simpleFeatureCollection2;
	}

	@Override
	public SimpleFeatureCollection operateFeatures() throws SchemaException {
		
		SimpleFeatureIterator simpleFeatureIterator1 = simpleFeatureCollection1.features();
		Vector<SimpleFeature> simpleFeatureList1 = new Vector<SimpleFeature>();
		
		while (simpleFeatureIterator1.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator1.next();
			simpleFeatureList1.add(simpleFeature);
		}
		
		SimpleFeatureIterator simpleFeatureIterator2 = simpleFeatureCollection2.features();
		Vector<SimpleFeature> simpleFeatureList2 = new Vector<SimpleFeature>();
		
		while(simpleFeatureIterator2.hasNext()){
			SimpleFeature simpleFeature = simpleFeatureIterator2.next();
			simpleFeatureList2.add(simpleFeature);
		}
	
		Polygon[] polygons = new Polygon[simpleFeatureList2.size()]; 
		LineString[] lineStrings = new LineString[simpleFeatureList2.size()];
		
		//List<Object> list = new ArrayList<Object>();
		
		for (int i = 0; i < simpleFeatureList2.size(); i++) {
			Geometry geometry = (Geometry) simpleFeatureList2.get(i).getDefaultGeometry(); // simplefeature geometry 값
			String geoType = geometry.getGeometryType().toString();
			if(geoType.equals("Polygon")){
				Polygon polygon = (Polygon) geometry;
				polygons[i] = polygon;
				//System.out.println("폴리곤");
				
			}else if(geoType.equals("LineString")){
				LineString lineString = (LineString) geometry;
				lineStrings[i] = lineString;
				System.out.println("라인");
				System.out.println();
				//list.add(lineStrings);
			}
		} 
		
		//System.out.println(list.getClass().toString());
		GeometryFactory geometryFactory = new GeometryFactory();
		
		Geometry multiPolygon = geometryFactory.createMultiPolygon(polygons);
		//Geometry multiLineString = geometryFactory.createMultiLineString(lineStrings);
		
		DefaultFeatureCollection defaultFeatureCollection = new DefaultFeatureCollection();
		for (int i = 0; i < simpleFeatureList1.size(); i++) { 
			Geometry geometry = (Geometry) simpleFeatureList1.get(i).getDefaultGeometry();
			String geometryType = geometry.getClass().getSimpleName().toString();
			
			if(geometryType.equals("Polygon")){
				Geometry result = geometry.difference(multiPolygon);
				SimpleFeatureType simpleFeatureType = DataUtilities.createType("", "the_geom:" + result.getGeometryType());
				SimpleFeature simpleFeature = SimpleFeatureBuilder.build(simpleFeatureType, new Object[] { result }, null);
				defaultFeatureCollection.add(simpleFeature);
				//System.out.println("폴리곤");
			}/*else if(geometryType.equals("LineString")){
				Geometry result = geometry.difference(multiLineString);
				SimpleFeatureType simpleFeatureType = DataUtilities.createType("", "the_geom:" + result.getGeometryType());
				SimpleFeature simpleFeature = SimpleFeatureBuilder.build(simpleFeatureType, new Object[] { result } , null);
				defaultFeatureCollection.add(simpleFeature);
				System.out.println("라인마지막");
				System.out.println();
			}*/
		}
		return defaultFeatureCollection;
	}
}
