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
import com.vividsolutions.jts.geom.Polygon;

public class LayerIntersection implements Operatable {

	Layer layer1;
	Layer layer2;
	
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

	public LayerIntersection(Layer layer1, Layer layer2) {
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
		
		SimpleFeatureIterator simpleFeatureIterator1 = layer1.getSimpleFeatureCollection().features();
		Vector<SimpleFeature> simpleFeatureList1 = new Vector<SimpleFeature>();
		
		while(simpleFeatureIterator1.hasNext()){
			SimpleFeature simpleFeature = simpleFeatureIterator1.next();
			simpleFeatureList1.add(simpleFeature);
		}
		
		SimpleFeatureIterator simpleFeatureIterator2 = layer2.getSimpleFeatureCollection().features();
		Vector<SimpleFeature> simpleFeatureList2 = new Vector<SimpleFeature>();
		
		while (simpleFeatureIterator2.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator2.next();
			simpleFeatureList2.add(simpleFeature);
		}
		
		DefaultFeatureCollection defaultFeatureCollection = new DefaultFeatureCollection();
		
		for (int i = 0; i < simpleFeatureList1.size(); i++) {
			for (int j = 0; j < simpleFeatureList2.size(); j++) {
				Geometry geometry1 = (Geometry) simpleFeatureList1.get(i).getDefaultGeometry();
				Geometry geometry2 = (Geometry) simpleFeatureList2.get(j).getDefaultGeometry();
				Geometry result = geometry1.intersection(geometry2);
				System.out.println(result);
				System.out.println();
				
				SimpleFeatureType simpleFeatureType = DataUtilities.createType("", "the_geom:" + result.getGeometryType());
				SimpleFeature simpleFeature = SimpleFeatureBuilder.build(simpleFeatureType, new Object[] {result}, null);
				defaultFeatureCollection.add(simpleFeature);
			}
		}
		return defaultFeatureCollection;
	}
}
