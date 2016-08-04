package com.git.gdsbuilder.type.layer;

import java.util.List;

import org.geotools.data.simple.SimpleFeatureCollection;

import com.git.gdsbuilder.type.result.DetailsValidatorReport;
import com.git.gdsbuilder.type.result.ISOValidatorReport;

public class ErrorLayer {

	SimpleFeatureCollection errSfc;
	List<DetailsValidatorReport> detailsValidatorReports;
	List<ISOValidatorReport> isoValidatorReports;

	public SimpleFeatureCollection getErrSfc() {
		return errSfc;
	}

	public void setErrSfc(SimpleFeatureCollection errSfc) {
		this.errSfc = errSfc;
	}

	public List<DetailsValidatorReport> getDetailsValidatorReports() {
		return detailsValidatorReports;
	}

	public void setDetailsValidatorReports(List<DetailsValidatorReport> detailsValidatorReports) {
		this.detailsValidatorReports = detailsValidatorReports;
	}

	public List<ISOValidatorReport> getIsoValidatorReports() {
		return isoValidatorReports;
	}

	public void setIsoValidatorReports(List<ISOValidatorReport> isoValidatorReports) {
		this.isoValidatorReports = isoValidatorReports;
	}

	public void addDetailsValidatorReport(DetailsValidatorReport detailsValidatorReport) {
		this.detailsValidatorReports.add(detailsValidatorReport);
	}

	public void addISOValidatorReport(ISOValidatorReport isoValidatorReport) {
		this.isoValidatorReports.add(isoValidatorReport);
	}

	public void addAllDetailsValidatorReport(List<DetailsValidatorReport> detailsValidatorReports) {
		this.detailsValidatorReports.addAll(detailsValidatorReports);
	}

	public void addAllIsoValidatorResult(List<ISOValidatorReport> isoValidatorReports) {
		this.isoValidatorReports.addAll(isoValidatorReports);
	}

}
