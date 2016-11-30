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

import com.git.gdsbuilder.type.layer.Layer;
import com.vividsolutions.jts.geom.Geometry;

public class LayerUnion implements Operatable {

	Layer layer1;
	Layer layer2;
	
	public enum Type {

		LAYERUNION("LayerUnion");

		String typeName;

		Type(String typeName) {
			this.typeName = typeName;
		}

		public String getType() {
			return typeName;
		}
	};
	
	public LayerUnion(Layer layer1, Layer layer2) {
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
	}

	@Override
	public SimpleFeatureCollection operateFeatures() throws SchemaException {
		
		SimpleFeatureIterator simpleFeatureIterator = layer1.getSimpleFeatureCollection().features();
		Vector<SimpleFeature> simpleFeatureList = new Vector<SimpleFeature>();
		
		while(simpleFeatureIterator.hasNext()){
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			simpleFeatureList.add(simpleFeature);	
		}
		
		SimpleFeatureIterator simpleFeatureIterator2 = layer2.getSimpleFeatureCollection().features();
		Vector<SimpleFeature> simpleFeatureList2 = new Vector<SimpleFeature>();
		 
		while(simpleFeatureIterator2.hasNext()){
			SimpleFeature simpleFeature = simpleFeatureIterator2.next();
			simpleFeatureList2.add(simpleFeature);                                                                                        
		}
		
		Geometry geometryList1 = (Geometry) simpleFeatureList.get(0).getDefaultGeometry();
		Geometry geometryList2 = (Geometry) simpleFeatureList2.get(0).getDefaultGeometry();
		
		for (int i = 1; i < simpleFeatureList.size(); i++) {
			Geometry geometry2 = (Geometry) simpleFeatureList.get(i).getDefaultGeometry();
			geometryList1 = geometryList1.union(geometry2);
		}
		
		for (int i = 1; i < simpleFeatureList2.size(); i++) {
			Geometry geometry2 = (Geometry) simpleFeatureList2.get(i).getDefaultGeometry();
			geometryList2 = geometryList2.union(geometry2);
		}
		
		Geometry result = geometryList1.union(geometryList2);
		SimpleFeatureType simpleFeatureType = DataUtilities.createType("", "the_geom:" + result.getGeometryType());
		SimpleFeature simpleFeature = SimpleFeatureBuilder.build(simpleFeatureType, new Object[] { result }, null);

		DefaultFeatureCollection sFeatureCollection = new DefaultFeatureCollection();
		sFeatureCollection.add(simpleFeature);
		
		return sFeatureCollection;
	}
}
