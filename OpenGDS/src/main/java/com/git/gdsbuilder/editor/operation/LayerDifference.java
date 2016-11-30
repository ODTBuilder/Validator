package com.git.gdsbuilder.editor.operation;

import java.util.Vector;

import org.geotools.data.DataUtilities;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.DefaultFeatureCollection;
<<<<<<< HEAD
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.geojson.geom.MultiPolygonHandler;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.git.gdsbuilder.type.layer.Layer;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
=======
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
import com.vividsolutions.jts.geom.Polygon;

public class LayerDifference implements Operatable {

<<<<<<< HEAD
	Layer layer1;
	Layer layer2;

=======
	SimpleFeatureCollection simpleFeatureCollection1;
	SimpleFeatureCollection simpleFeatureCollection2;
	
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
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
<<<<<<< HEAD

	public LayerDifference(Layer layer1, Layer layer2) {
		super();
		this.layer1 = layer1;
		this.layer2 = layer2;
	}

	public Layer getLayer1() {
		return layer1;
	}

	public void setLayer1(Layer layer1) {
		this.layer1 = layer1;
	}

	public Layer getLayer2() {
		return layer2;
	}

	public void setLayer2(Layer layer2) {
		this.layer2 = layer2;
=======
	
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
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
	}

	@Override
	public SimpleFeatureCollection operateFeatures() throws SchemaException {
<<<<<<< HEAD

		// Layer1의 feature
		SimpleFeatureCollection simpleFeatureCollection1 =  layer1.getSimpleFeatureCollection();
		SimpleFeatureIterator simpleFeatureIterator1 = simpleFeatureCollection1.features();
		Vector<SimpleFeature> simpleFeatureList1 = new Vector<SimpleFeature>();

		while(simpleFeatureIterator1.hasNext()){
			SimpleFeature simpleFeature = simpleFeatureIterator1.next();
			simpleFeatureList1.add(simpleFeature);
		}

		SimpleFeatureCollection simpleFeatureCollection2 = layer2.getSimpleFeatureCollection();
		SimpleFeatureIterator simpleFeatureIterator2 = simpleFeatureCollection2.features();
		Vector<SimpleFeature> simpleFeatureList2 = new Vector<SimpleFeature>();

		// Layer2의 feature
=======
		
		SimpleFeatureIterator simpleFeatureIterator1 = simpleFeatureCollection1.features();
		Vector<SimpleFeature> simpleFeatureList1 = new Vector<SimpleFeature>();
		
		while (simpleFeatureIterator1.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator1.next();
			simpleFeatureList1.add(simpleFeature);
		}
		
		SimpleFeatureIterator simpleFeatureIterator2 = simpleFeatureCollection2.features();
		Vector<SimpleFeature> simpleFeatureList2 = new Vector<SimpleFeature>();
		
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
		while(simpleFeatureIterator2.hasNext()){
			SimpleFeature simpleFeature = simpleFeatureIterator2.next();
			simpleFeatureList2.add(simpleFeature);
		}
<<<<<<< HEAD
		
		DefaultFeatureCollection defaultFeatureCollection = new DefaultFeatureCollection();
		for (int i = 0; i < simpleFeatureList1.size(); i++) {
			for (int j = 0; j < simpleFeatureList2.size(); j++) {
				Geometry geometry = (Geometry) simpleFeatureList1.get(i).getDefaultGeometry();
				Geometry geometry2 = (Geometry) simpleFeatureList2.get(j).getDefaultGeometry();
				if(geometry.crosses(geometry2) || geometry.overlaps(geometry2) || geometry.contains(geometry2) || geometry.within(geometry2) || geometry.equals(geometry2)){
					Geometry result = geometry.difference(geometry2);
					SimpleFeatureType simpleFeatureType = DataUtilities.createType("", "the_geom:" + result.getGeometryType());
					SimpleFeature simpleFeature = SimpleFeatureBuilder.build(simpleFeatureType, new Object[] {result}, null);
					defaultFeatureCollection.add(simpleFeature);
				}
			}
		}
		return defaultFeatureCollection;
	}
	
=======
	
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
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
}
