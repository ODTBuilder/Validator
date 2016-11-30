package com.git.gdsbuilder.type.layer;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.DefaultFeatureCollection;
import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.type.result.DetailsValidatorResult;
import com.git.gdsbuilder.type.result.ISOValidatorField;

public class ErrorLayer {

	DefaultFeatureCollection errFeatureCollection;
	List<DetailsValidatorResult> detailsValidatorReport;
	List<ISOValidatorField> isoValidatorReport;

	public ErrorLayer() {
		this.errFeatureCollection = new DefaultFeatureCollection();
		this.detailsValidatorReport = new ArrayList<DetailsValidatorResult>();
		this.isoValidatorReport = new ArrayList<ISOValidatorField>();
	}

	public SimpleFeatureCollection getErrFeatureCollection() {
		return errFeatureCollection;
	}

	public void setErrFeatureCollection(DefaultFeatureCollection errFeatureCollection) {
		this.errFeatureCollection = errFeatureCollection;
	}

	public List<DetailsValidatorResult> getDetailsValidatorReport() {
		return detailsValidatorReport;
	}

	public void setDetailsValidatorReport(List<DetailsValidatorResult> detailsValidatorReport) {
		this.detailsValidatorReport = detailsValidatorReport;
	}

	public List<ISOValidatorField> getISOValidatorReport() {
		return isoValidatorReport;
	}

	public ISOValidatorField getISOValidatorField(String layerID) {

		boolean isEmptied = true;
		ISOValidatorField returnField = new ISOValidatorField();
		for (int i = 0; i < isoValidatorReport.size(); i++) {
			ISOValidatorField tempField = isoValidatorReport.get(i);
			String id = tempField.getLayerID();
			if (layerID.equals(id)) {
				returnField = tempField;
				isEmptied = false;
			}
		}
		if (!isEmptied) {
			return returnField;
		} else {
			return null;
		}
	}

	public void setISOValidatorReport(List<ISOValidatorField> iSOValidatorReport) {
		isoValidatorReport = iSOValidatorReport;
	}

	public void setDetailsValidatorReport(DetailsValidatorResult detailsValidatorResult) {
		detailsValidatorReport.add(detailsValidatorResult);
	}

	public void setISOValidatorReport(ISOValidatorField isoValidatorFeild) {
		isoValidatorReport.add(isoValidatorFeild);
	}

	public void addErrorFeature(SimpleFeature errFeature) {
		this.errFeatureCollection.add(errFeature);
	}

	public void addErrorFeatureCollection(SimpleFeatureCollection errFeatureCollection) {
		this.errFeatureCollection.addAll(errFeatureCollection);
	}

	public void addDetailsValidatorReport(DetailsValidatorResult detailsValidatorReport) {
		this.detailsValidatorReport.add(detailsValidatorReport);
	}

	public void addAllDetailsValidatorReport(List<DetailsValidatorResult> detailsValidatorReports) {
		this.detailsValidatorReport.addAll(detailsValidatorReports);
	}

	public void addISOValidatorField(ISOValidatorField inputField) {

		ISOValidatorField thisField = this.getISOValidatorField(inputField.getLayerID());
		if (thisField != null) {
			this.getISOValidatorField(inputField.getLayerID()).setNumOfInvalidErr(thisField.getNumOfInvalidErr() + inputField.getNumOfInvalidErr());
			this.getISOValidatorField(inputField.getLayerID()).setNumOfStructureErr(thisField.getNumOfStructureErr() + inputField.getNumOfStructureErr());
			this.getISOValidatorField(inputField.getLayerID()).setNumOfErrItem(thisField.getNumOfErrItem() + inputField.getNumOfErrItem());
			this.getISOValidatorField(inputField.getLayerID()).createISOReport();
		} else {
			this.isoValidatorReport.add(inputField);
		}

	}

	public void addAllISOValidatorField(List<ISOValidatorField> inputISOResults) {

		// this 에 input을 더함
		int thisReportSize = this.isoValidatorReport.size();
		int inputReportSize = inputISOResults.size();
		if (thisReportSize == 0) {
			this.isoValidatorReport.add(inputISOResults.get(0));
			for (int i = 1; i < inputReportSize; i++) {
				ISOValidatorField inputField = inputISOResults.get(i);
				addISOValidatorField(inputField);
			}
		} else {
			for (int i = 0; i < inputReportSize; i++) {
				ISOValidatorField inputField = inputISOResults.get(i);
				addISOValidatorField(inputField);
			}
		}
	}
}
=======
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
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
