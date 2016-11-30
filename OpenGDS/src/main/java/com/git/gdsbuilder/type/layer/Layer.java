package com.git.gdsbuilder.type.layer;

import org.geotools.data.simple.SimpleFeatureCollection;

public class Layer {
	
	String type;
	SimpleFeatureCollection simpleFeatureCollection;
	
	public Layer(String type, SimpleFeatureCollection simpleFeatureCollection) {
		super();
		this.type = type;
		this.simpleFeatureCollection = simpleFeatureCollection;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public SimpleFeatureCollection getSimpleFeatureCollection() {
		return simpleFeatureCollection;
	}
	
	public void setSimpleFeatureCollection(SimpleFeatureCollection simpleFeatureCollection) {
		this.simpleFeatureCollection = simpleFeatureCollection;
	}
	

}
