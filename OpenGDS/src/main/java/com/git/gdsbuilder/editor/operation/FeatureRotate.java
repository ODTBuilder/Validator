package com.git.gdsbuilder.editor.operation;

import java.util.Vector;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.DefaultFeatureCollection;
import org.opengis.feature.simple.SimpleFeature;

public class FeatureRotate implements Operatable {

	SimpleFeatureCollection simpleFeatureCollection;
	double degree;
	
	public enum Type {

		FEATUREROTATE("FeatureRotate");

		String typeName;

		Type(String typeName) {
			this.typeName = typeName;
		}

		public String getType() {
			return typeName;
		}
	};
	
	public FeatureRotate(SimpleFeatureCollection simpleFeatureCollection, double degree) {
		super();
		this.simpleFeatureCollection = simpleFeatureCollection;
		this.degree = degree;
	}

	public SimpleFeatureCollection getSimpleFeatureCollection() {
		return simpleFeatureCollection;
	}

	public void setSimpleFeatureCollection(SimpleFeatureCollection simpleFeatureCollection) {
		this.simpleFeatureCollection = simpleFeatureCollection;
	}
	public double getDegree() {
		return degree;
	}

	public void setDegree(double degree) {
		this.degree = degree;
	}

	@Override
	public SimpleFeatureCollection operateFeatures() {
		
		OperatorFactory operatorFactory = new OperatorFactoryImpl();
		
		SimpleFeatureCollection featureCollection = simpleFeatureCollection;
		SimpleFeatureIterator featureIterator = featureCollection.features();
		SimpleFeatureCollection returnFeatureCollection = new DefaultFeatureCollection();
		
		int a = featureCollection.size();
		
		/*if(a==1){
			Vector<SimpleFeature> simpleFeatureList = new Vector<SimpleFeature>();
			
			while(featureIterator.hasNext()){
				SimpleFeature simpleFeature = featureIterator.next();
				simpleFeatureList.add(simpleFeature);
			}
		
			SimpleFeature simpleFeature1 = simpleFeatureList.get(0);
			returnFeatureCollection = operatorFactory.rotate(simpleFeature1, degree);
		}
		else
			return null;*/
		
		Vector<SimpleFeature> simpleFeatureList = new Vector<SimpleFeature>();
		
		while(featureIterator.hasNext()){
			SimpleFeature simpleFeature = featureIterator.next();
			simpleFeatureList.add(simpleFeature);
		}
		
		
		return returnFeatureCollection;
	}
}
