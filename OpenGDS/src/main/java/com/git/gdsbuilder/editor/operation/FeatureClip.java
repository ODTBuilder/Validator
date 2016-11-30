package com.git.gdsbuilder.editor.operation;

import org.geotools.data.simple.SimpleFeatureCollection;

public class FeatureClip implements Operatable {

	SimpleFeatureCollection simpleFeatureCollection;
	
	public enum Type {

		FEATURECLIP("FeatureClip");

		String typeName;

		Type(String typeName) {
			this.typeName = typeName;
		}

		public String getType() {
			return typeName;
		}
	};
	
	public FeatureClip(SimpleFeatureCollection simpleFeatureCollection) {
		super();
		this.simpleFeatureCollection = simpleFeatureCollection;
	}

	public SimpleFeatureCollection getSimpleFeatureCollection() {
		return simpleFeatureCollection;
	}

	public void setSimpleFeatureCollection(SimpleFeatureCollection simpleFeatureCollection) {
		this.simpleFeatureCollection = simpleFeatureCollection;
	}

	@Override
	public SimpleFeatureCollection operateFeatures() {
		// TODO Auto-generated method stub
		return null;
	}
}
