package com.git.gdsbuilder.type.result;

import java.util.HashMap;
import java.util.Map;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.opengis.feature.simple.SimpleFeature;

public class ISOValidatorField {

	String layerID;
	double numOfItem;
	double numOfInvalidErr;
	double numOfStructureErr;
	double numOfErrItem;
	String ratioOferrItem;
	double accuracyValue;
	double weight;
	double weightedValue;

	public ISOValidatorField() {

	}

	public ISOValidatorField(String layerID, double numOfFeature, double weight) {
		super();
		this.layerID = layerID;
		this.numOfItem = numOfFeature;
		this.weight = weight;
		this.numOfInvalidErr = 0;
		this.numOfStructureErr = 0;
		this.numOfErrItem = 0;
		this.ratioOferrItem = "";
		this.accuracyValue = 0;
	}

	public String getLayerID() {
		return layerID;
	}

	public void setLayerID(String layerID) {
		this.layerID = layerID;
	}

	public double getNumOfItem() {
		return numOfItem;
	}

	public void setNumOfItem(double numOfItem) {
		this.numOfItem = numOfItem;
	}

	public double getNumOfInvalidErr() {
		return numOfInvalidErr;
	}

	public void setNumOfInvalidErr(double numOfInvalidErr) {
		this.numOfInvalidErr = numOfInvalidErr;
	}

	public double getNumOfStructureErr() {
		return numOfStructureErr;
	}

	public void setNumOfStructureErr(double numOfStructureErr) {
		this.numOfStructureErr = numOfStructureErr;
	}

	public double getNumOfErrItem() {
		return numOfErrItem;
	}

	public void setNumOfErrItem(double numOfErrItem) {
		this.numOfErrItem = numOfErrItem;
	}

	public String getRatioOferrItem() {
		return ratioOferrItem;
	}

	public void setRatioOferrItem(String ratioOferrItem) {
		this.ratioOferrItem = ratioOferrItem;
	}

	public double getAccuracyValue() {
		return accuracyValue;
	}

	public void setAccuracyValue(double accuracyValue) {
		this.accuracyValue = accuracyValue;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getWeightedValue() {
		return weightedValue;
	}

	public void setWeightedValue(double weightedValue) {
		this.weightedValue = weightedValue;
	}

	public void createISOReport() {

		this.ratioOferrItem = ratioOfErr();
		this.accuracyValue = accuracy();
		this.weightedValue = weightedValue();

	}

	private String ratioOfErr() {

		return (int) this.numOfErrItem + "/" + (int) this.numOfItem;
	}

	private double accuracy() {

		double accuracy = 1.0 - (this.numOfErrItem / this.numOfItem);

		return Double.parseDouble(String.format("%.3f", accuracy));
	}

	private double weightedValue() {

		double weightedValue = this.accuracyValue * (this.weight / 100);

		return Double.parseDouble(String.format("%.3f", weightedValue));
	}

	public void createISOField(SimpleFeatureCollection errFeatureCollection) {

		Map<String, String> checkedID = new HashMap<String, String>();

		boolean firstIn = true;
		SimpleFeatureIterator iterator = errFeatureCollection.features();
		while (iterator.hasNext()) {
			SimpleFeature errFeature = iterator.next();
			String errFeatureID = errFeature.getID();
			String errType = (String) errFeature.getAttribute("errorType");
			if (firstIn) {
				firstIn = false;
				if (errType.equals("AttributeError") || errType.equals("GeometricError")) {
					this.numOfStructureErr++;
					checkedID.put(errFeatureID, "checked");
				} else if (errType.equals("TypeError")) {
					this.numOfInvalidErr++;
					checkedID.put(errFeatureID, "checked");
				}
			} else {
				if (checkedID.get(errFeatureID) != null) {
					continue;
				} else {
					if (errType.equals("AttributeError") || errType.equals("GeometricError")) {
						this.numOfStructureErr++;
						checkedID.put(errFeatureID, "checked");
					} else if (errType.equals("TypeError")) {
						this.numOfInvalidErr++;
						checkedID.put(errFeatureID, "checked");
					}
				}
			}
		}
		this.numOfErrItem = this.numOfInvalidErr + this.numOfStructureErr;
		this.ratioOferrItem = ratioOfErr();
		this.accuracyValue = accuracy();
		this.weightedValue = weightedValue();
	}
}
