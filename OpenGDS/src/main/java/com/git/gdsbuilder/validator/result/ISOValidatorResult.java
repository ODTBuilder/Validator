package com.git.gdsbuilder.validator.result;

import com.git.gdsbuilder.type.result.ISOValidatorReport;

/**
 * 오류 리포트를 생성한다.
 * 
 * @author dayeon.oh
 * @data 2016.02
 */
public class ISOValidatorResult {

	String layerName;
	double numOfItems;
	double numOfErr;
	double weight;

	public ISOValidatorResult(String layerName, double numOfItems, double numOfErr, double weight) {
		this.layerName = layerName;
		this.numOfItems = numOfItems;
		this.numOfErr = numOfErr;
		this.weight = weight;
	}

	public ISOValidatorReport createISOValidatorReport() {

		ISOValidatorReport report = new ISOValidatorReport();

		String retioOfErr = getRatioOfErr();
		double accuracy = getAccuracy();
		double weigthedValue = getWeightedValue(accuracy, this.weight);

		report.setNumOfErr(numOfItems);
		report.setNumOfErr(numOfErr);
		report.setRatioOfErr(retioOfErr);
		report.setAccuracy(accuracy);
		report.setWeights(weight);
		report.setWeightedValue(weigthedValue);

		return report;
	}

	private String getRatioOfErr() {

		return (int) this.numOfErr + "/" + (int) this.numOfItems;
	}

	private double getAccuracy() {

		double accuracy = 1.0 - (this.numOfErr / this.numOfItems);

		return Double.parseDouble(String.format("%.2f", accuracy));
	}

	private double getWeightedValue(double accuracy, double weight) {

		double weightedValue = accuracy * weight;

		return Double.parseDouble(String.format("%.2f", weightedValue));
	}

}
