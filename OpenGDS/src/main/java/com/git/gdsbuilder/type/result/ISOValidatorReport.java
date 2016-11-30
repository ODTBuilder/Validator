package com.git.gdsbuilder.type.result;

public class ISOValidatorReport {

<<<<<<< HEAD
=======
	String layerName;
	double numOfItems = 0;
	double numOfErr = 0;
	String ratioOfErr = "";
	double accuracy = 0;
	double weights = 0;
	double weightedValue;
	boolean checked;

	/**
	 * 오류 종류를 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.02
	 * @return String
	 */
	public String getLayerName() {
		return layerName;
	}

	/**
	 * 오류 종류를 설정한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.02
	 * @param layerName
	 *            오류 종류
	 */
	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}

	/**
	 * 검수를 수행한 객체의 수를 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.02
	 * @return double
	 */
	public double getNumOfItems() {
		return numOfItems;
	}

	/**
	 * 검수를 수행한 객체의 수를 설정한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.02
	 * @param numOfItems
	 *            검수를 수행한 객체의 수
	 */
	public void setNumOfItems(double numOfItems) {
		this.numOfItems = numOfItems;
	}

	/**
	 * 검수를 수행 후 오류가 있는 객체의 수를 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.02
	 * @return double
	 */
	public double getNumOfErr() {
		return numOfErr;
	}

	/**
	 * 검수를 수행 후 오류가 있는 객체의 수를 설정한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.02
	 * @param numOfErr
	 *            오류가 있는 객체의 수
	 */
	public void setNumOfErr(double numOfErr) {
		this.numOfErr = numOfErr;
	}

	/**
	 * 오류가 있는 객체의 수의 비율을 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.02
	 * @return String
	 */
	public String getRatioOfErr() {
		return ratioOfErr;
	}

	/**
	 * 검수를 수행 후 오류가 있는 객체의 수를 설정한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.02
	 * @param ratioOfErr
	 *            오류가 있는 객체의 수의 비율
	 */
	public void setRatioOfErr(String ratioOfErr) {
		this.ratioOfErr = ratioOfErr;
	}

	/**
	 * 오류 종류의 정확도를 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.02
	 * @return double
	 */
	public double getAccuracy() {
		return accuracy;
	}

	/**
	 * 오류 종류의 정확도를 설정한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.02
	 * @param accuracy
	 *            오류 종류의 정확도
	 */
	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	/**
	 * 오류 종류의 가중치를 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.02
	 * @return double
	 */
	public double getWeights() {
		return weights;
	}

	/**
	 * 오류 종류의 가중치를 설정한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.02
	 * @param weights
	 *            오류 종류의 가중치
	 */
	public void setWeights(double weights) {
		this.weights = weights;
	}

	/**
	 * 오류 종류의 정확도의 백분율을 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.02
	 * @return double
	 */
	public double getWeightedValue() {
		return weightedValue;
	}

	/**
	 * 오류 종류의 정확도의 백분율을 설정한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.02
	 * @param weightedValue
	 *            오류 종류의 정확도의 백분율
	 */
	public void setWeightedValue(double weightedValue) {
		this.weightedValue = weightedValue;
	}

	/**
	 * 오류 종류의 검수 수행 여부를 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.02
	 * @return boolean
	 */
	public boolean isChecked() {
		return checked;
	}

	/**
	 * 오류 종류의 검수 수행 여부를 설정한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.02
	 * @param checked
	 *            오류 종류의 검수 수행 여부
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public ISOValidatorReport createISOValidatorReport() {

		ISOValidatorReport report = new ISOValidatorReport();

		/*
		 * String retioOfErr = getRatioOfErr(); double accuracy = getAccuracy();
		 * double weigthedValue = getWeightedValue(accuracy, this.weight);
		 * 
		 * report.setType(type); report.setNumOfErr(numOfItems);
		 * report.setNumOfErr(numOfErr); report.setRatioOfErr(retioOfErr);
		 * report.setAccuracy(accuracy); report.setWeights(weight);
		 * report.setWeightedValue(weigthedValue);
		 */
		return report;
	}

	private String ratioOfErr() {

		return (int) this.numOfErr + "/" + (int) this.numOfItems;
	}

	private double accuracy() {

		double accuracy = 1.0 - (this.numOfErr / this.numOfItems);

		return Double.parseDouble(String.format("%.2f", accuracy));
	}

	private double weightedValue(double accuracy, double weight) {

		double weightedValue = accuracy * weight;

		return Double.parseDouble(String.format("%.2f", weightedValue));
	}
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c

}
