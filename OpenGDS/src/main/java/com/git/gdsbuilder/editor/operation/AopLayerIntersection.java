package com.git.gdsbuilder.editor.operation;

import java.util.Vector;

import org.geotools.data.DataUtilities;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.vividsolutions.jts.geom.Geometry;

public class AopLayerIntersection implements AopOperatable {
	
	SimpleFeatureCollection simpleFeatureCollection;
	SimpleFeatureCollection aopsimpleFeatureCollection;
	
	public AopLayerIntersection(SimpleFeatureCollection simpleFeatureCollection, SimpleFeatureCollection aopsimpleFeatureCollection) {
		this.simpleFeatureCollection = simpleFeatureCollection;
		this.aopsimpleFeatureCollection = aopsimpleFeatureCollection;
	}
	
	public SimpleFeatureCollection getSimpleFeatureCollection() {
		return simpleFeatureCollection;
	}
	public void setSimpleFeatureCollection(SimpleFeatureCollection simpleFeatureCollection) {
		this.simpleFeatureCollection = simpleFeatureCollection;
	}
	public SimpleFeatureCollection getAopsimpleFeatureCollection() {
		return aopsimpleFeatureCollection;
	}
	public void setAopsimpleFeatureCollection(SimpleFeatureCollection aopsimpleFeatureCollection) {
		this.aopsimpleFeatureCollection = aopsimpleFeatureCollection;
	}

	@Override
	public SimpleFeatureCollection operateFeatures() throws SchemaException {
		
		// 검수 데이터 레이어
		SimpleFeatureIterator featureIterator = simpleFeatureCollection.features();
		Vector<SimpleFeature> simpleFeatureList1 = new Vector<SimpleFeature>();
		
		while(featureIterator.hasNext()){
			SimpleFeature simpleFeature = featureIterator.next();
			simpleFeatureList1.add(simpleFeature);
		}
		
		// 영역 레이어
		SimpleFeatureIterator aopFeatureIterator = aopsimpleFeatureCollection.features();
		Vector<SimpleFeature> simpleFeatureList2 = new Vector<SimpleFeature>();
		
		while(aopFeatureIterator.hasNext()){
			SimpleFeature simpleFeature = aopFeatureIterator.next();
			simpleFeatureList2.add(simpleFeature);
		}
		
		DefaultFeatureCollection defaultFeatureCollection = new DefaultFeatureCollection();
		for (int i = 0; i < simpleFeatureList1.size(); i++) {
			for (int j = 0; j < simpleFeatureList2.size(); j++) {
				SimpleFeature simpleFeature = simpleFeatureList1.get(i);
				
				Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
				Geometry exGeometry = (Geometry) simpleFeatureList2.get(j).getDefaultGeometry();
				
				if(geometry.within(exGeometry) == true){
					defaultFeatureCollection.add(simpleFeature);
				}else if(geometry.crosses(exGeometry) == true || geometry.overlaps(exGeometry)){
					Geometry result = geometry.intersection(exGeometry);
					simpleFeature.setDefaultGeometry(result);
					/*SimpleFeatureType simpleFeatureType = DataUtilities.createType("", "the_geom:" + result.getGeometryType());
					SimpleFeature simFeature = SimpleFeatureBuilder.build(simpleFeatureType, new Object[] {result}, null);*/
					defaultFeatureCollection.add(simpleFeature);
				}
			}
		}
		return defaultFeatureCollection;
	}
}
