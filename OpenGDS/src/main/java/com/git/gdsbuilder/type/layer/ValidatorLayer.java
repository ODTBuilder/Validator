package com.git.gdsbuilder.type.layer;

import java.util.List;

import org.geotools.data.simple.SimpleFeatureCollection;

import com.git.gdsbuilder.type.validatorOption.ValidatorOption;

public class ValidatorLayer {

	String layerType;
	String layerID;
	SimpleFeatureCollection simpleFeatureCollection;
	List<ValidatorOption> validatorOptions;
	double weigth;

	public ValidatorLayer() {

	}

	public ValidatorLayer(String layerType, String layerID, SimpleFeatureCollection simpleFeatureCollection, double weight) {
		super();
		this.layerType = layerType;
		this.layerID = layerID;
		this.simpleFeatureCollection = simpleFeatureCollection;
		this.weigth = weight;
	}

	public ValidatorLayer(String layerType, String layerID, SimpleFeatureCollection simpleFeatureCollection, List<ValidatorOption> validatorOptions) {
		super();
		this.layerType = layerType;
		this.layerID = layerID;
		this.simpleFeatureCollection = simpleFeatureCollection;
		this.validatorOptions = validatorOptions;
	}

	public String getLayerType() {
		return layerType;
	}

	public void setLayerType(String layerType) {
		this.layerType = layerType;
	}

	public String getLayerID() {
		return layerID;
	}

	public void setLayerID(String layerID) {
		this.layerID = layerID;
	}

	public SimpleFeatureCollection getSimpleFeatureCollection() {
		return simpleFeatureCollection;
	}

	public void setSimpleFeatureCollection(SimpleFeatureCollection simpleFeatureCollection) {
		this.simpleFeatureCollection = simpleFeatureCollection;
	}

	public List<ValidatorOption> getValidatorOptions() {
		return validatorOptions;
	}

	public void setValidatorOptions(List<ValidatorOption> validatorOptions) {
		this.validatorOptions = validatorOptions;
	}

	public double getWeigth() {
		return weigth;
	}

	public void setWeigth(double weigth) {
		this.weigth = weigth;
	}

}
