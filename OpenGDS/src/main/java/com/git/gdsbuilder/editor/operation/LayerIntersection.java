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
import com.vividsolutions.jts.geom.Polygon;

public class LayerIntersection implements Operatable {

	SimpleFeatureCollection simpleFeatureCollection1;
	SimpleFeatureCollection simpleFeatureCollection2;
	
	public enum Type {

		LAYERINTERSECTION("LayerIntersection");

		String typeName;

		Type(String typeName) {
			this.typeName = typeName;
		}

		public String getType() {
			return typeName;
		}
	};

	public LayerIntersection(SimpleFeatureCollection simpleFeatureCollection1, SimpleFeatureCollection simpleFeatureCollection2) {
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
		
		while(simpleFeatureIterator1.hasNext()){
			SimpleFeature simpleFeature = simpleFeatureIterator1.next();
			simpleFeatureList1.add(simpleFeature);
		}
		
		SimpleFeatureIterator simpleFeatureIterator2 = simpleFeatureCollection2.features();
		Vector<SimpleFeature> simpleFeatureList2 = new Vector<SimpleFeature>();
		
		while (simpleFeatureIterator2.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator2.next();
			simpleFeatureList2.add(simpleFeature);
		}
		
		DefaultFeatureCollection defaultFeatureCollection = new DefaultFeatureCollection();
		
		for (int i = 0; i < simpleFeatureList1.size(); i++) {
			for (int j = 0; j < simpleFeatureList2.size(); j++) {
				Geometry geometry1 = (Geometry) simpleFeatureList1.get(i).getDefaultGeometry();
				Geometry geometry2 = (Geometry) simpleFeatureList2.get(i).getDefaultGeometry();
				Geometry result = geometry1.intersection(geometry2);
				
				SimpleFeatureType simpleFeatureType = DataUtilities.createType("", "the_geom:" + result.getGeometryType());
				SimpleFeature simpleFeature = SimpleFeatureBuilder.build(simpleFeatureType, new Object[] {result}, null);
				defaultFeatureCollection.add(simpleFeature);
			}
		}
		
		return defaultFeatureCollection;
		
	}
}
