package com.git.gdsbuilder.type.result;

public class DetailsValidatorResult {

	String layerID;
	String featureID;
	String errorType;
	String errorName;
	double errorCoordinateX;
	double errorCoordinateY;

	public DetailsValidatorResult(String featureID, String errType, String errName, double errCoorX, double errCoorY) {
		super();
		this.featureID = featureID;
		this.errorType = errType;
		this.errorName = errName;
		this.errorCoordinateX = errCoorX;
		this.errorCoordinateY = errCoorY;
	}

	public DetailsValidatorResult(String layerID, String featureID, String errType, String errName, double errCoorX, double errCoorY) {
		super();
		this.layerID = layerID;
		this.featureID = featureID;
		this.errorType = errType;
		this.errorName = errName;
		this.errorCoordinateX = errCoorX;
		this.errorCoordinateY = errCoorY;
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

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public String getErrorName() {
		return errorName;
	}

	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}

	public double getErrorCoordinateX() {
		return errorCoordinateX;
	}

	public void setErrorCoordinateX(double errorCoordinateX) {
		this.errorCoordinateX = errorCoordinateX;
	}

	public double getErrorCoordinateY() {
		return errorCoordinateY;
	}

	public void setErrorCoordinateY(double errorCoordinateY) {
		this.errorCoordinateY = errorCoordinateY;
	}


}
