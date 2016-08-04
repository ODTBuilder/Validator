package com.git.gdsbuilder.type.result;

public class DetailsValidatorReport {

	String errType;
	String errName;
	String layerID;
	String featureID;
	double errCoorX;
	double errCoorY;

	public DetailsValidatorReport(String errType, String errName, String featureID, double errCoorX, double errCoorY) {
		this.errType = errType;
		this.errName = errName;
		this.featureID = featureID;
		this.errCoorX = errCoorX;
		this.errCoorY = errCoorY;

	}

	public String getErrType() {
		return errType;
	}

	public void setErrType(String errType) {
		this.errType = errType;
	}

	public String getErrName() {
		return errName;
	}

	public void setErrName(String errName) {
		this.errName = errName;
	}

	public String getLayerID() {
		return layerID;
	}

	public void setLayerID(String layerID) {
		this.layerID = layerID;
	}

	public String getFeatureID() {
		return featureID;
	}

	public void setFeatureID(String featureID) {
		this.featureID = featureID;
	}

	public double getErrCoorX() {
		return errCoorX;
	}

	public void setErrCoorX(double errCoorX) {
		this.errCoorX = errCoorX;
	}

	public double getErrCoorY() {
		return errCoorY;
	}

	public void setErrCoorY(double errCoorY) {
		this.errCoorY = errCoorY;
	}

}
