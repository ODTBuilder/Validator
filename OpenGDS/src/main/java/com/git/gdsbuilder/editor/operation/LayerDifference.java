package com.git.gdsbuilder.editor.operation;

import java.util.Vector;

import org.geotools.data.DataUtilities;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.DefaultFeatureCollection;
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
import com.vividsolutions.jts.geom.Polygon;

public class LayerDifference implements Operatable {

	Layer layer1;
	Layer layer2;

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
	}

	@Override
	public SimpleFeatureCollection operateFeatures() throws SchemaException {

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
		while(simpleFeatureIterator2.hasNext()){
			SimpleFeature simpleFeature = simpleFeatureIterator2.next();
			simpleFeatureList2.add(simpleFeature);
		}
		
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
	
}
