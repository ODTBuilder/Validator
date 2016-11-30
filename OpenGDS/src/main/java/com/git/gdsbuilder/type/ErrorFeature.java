package com.git.gdsbuilder.type;

import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.type.result.DetailsValidatorReport;

public class ErrorFeature {

	SimpleFeature errFeature;
	DetailsValidatorReport dtReport;

	public ErrorFeature(SimpleFeature errFeature, DetailsValidatorReport dtReport) {
		this.errFeature = errFeature;
		this.dtReport = dtReport;
	}

	public SimpleFeature getErrFeature() {
		return errFeature;
	}

	public void setErrFeature(SimpleFeature errFeature) {
		this.errFeature = errFeature;
	}

	public DetailsValidatorReport getDtReport() {
		return dtReport;
	}

	public void setDtReport(DetailsValidatorReport dtReport) {
		this.dtReport = dtReport;
	}
}
