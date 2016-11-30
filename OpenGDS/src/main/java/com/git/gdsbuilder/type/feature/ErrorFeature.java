package com.git.gdsbuilder.type.feature;

import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.type.result.DetailsValidatorResult;

public class ErrorFeature {

	SimpleFeature errFeature;
	DetailsValidatorResult dtReport;

	public ErrorFeature(SimpleFeature errFeature, DetailsValidatorResult dtReport) {
		this.errFeature = errFeature;
		this.dtReport = dtReport;
	}

	public SimpleFeature getErrFeature() {
		return errFeature;
	}

	public void setErrFeature(SimpleFeature errFeature) {
		this.errFeature = errFeature;
	}

	public DetailsValidatorResult getDtReport() {
		return dtReport;
	}

	public void setDtReport(DetailsValidatorResult dtReport) {
		this.dtReport = dtReport;
	}

}
