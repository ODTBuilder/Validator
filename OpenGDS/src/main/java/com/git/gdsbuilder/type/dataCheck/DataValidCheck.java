package com.git.gdsbuilder.type.dataCheck;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.SchemaException;
import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.convertor.DataConvertor;
import com.git.gdsbuilder.type.feature.ErrorFeature;
import com.git.gdsbuilder.type.layer.ErrorLayer;
import com.git.gdsbuilder.type.result.DetailsValidatorResult;
import com.git.gdsbuilder.type.result.ISOValidatorField;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

public class DataValidCheck {

	ErrorLayer errorLayer;
	DefaultFeatureCollection trueFeatureCollection;
	boolean isErrorLayer;

	public ErrorLayer getErrorLayer() {
		return errorLayer;
	}

	public void setErrorLayer(ErrorLayer errorLayer) {
		this.errorLayer = errorLayer;
	}

	public DefaultFeatureCollection getTrueFeatureCollection() {
		return trueFeatureCollection;
	}

	public void setTrueFeatureCollection(DefaultFeatureCollection trueFeatureCollection) {
		this.trueFeatureCollection = trueFeatureCollection;
	}

	public boolean isErrorLayer() {
		return isErrorLayer;
	}

	public void setErrorLayer(boolean isErrorLayer) {
		this.isErrorLayer = isErrorLayer;
	}

	public void dataCheck(String layerID, SimpleFeatureCollection simpleFeatureCollection, double weight) throws SchemaException {

		SimpleFeatureIterator simpleFeatureIterator = simpleFeatureCollection.features();
		Vector<SimpleFeature> simpleFeatures = new Vector<SimpleFeature>();
		DefaultFeatureCollection errDeCollection = new DefaultFeatureCollection();
		DefaultFeatureCollection deCollection = new DefaultFeatureCollection();
		List<DetailsValidatorResult> detailsValidatorResults = new ArrayList<DetailsValidatorResult>();

		while (simpleFeatureIterator.hasNext()) { // simpleFeature들 하나씩 꺼내기
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			simpleFeatures.add(simpleFeature);
		}
		for (int i = 0; i < simpleFeatures.size(); i++) {
			SimpleFeature simpleFeature = simpleFeatures.get(i);
			ErrorFeature errorFeature = featureDataCheck(simpleFeature); // 데이터
																			// 검사
			if (errorFeature != null) {
				errDeCollection.add(errorFeature.getErrFeature());
				detailsValidatorResults.add(errorFeature.getDtReport());
			} else {
				deCollection.add(simpleFeature);
			}
		}

		trueFeatureCollection = deCollection;
		if (errDeCollection.size() > 0) {
			trueFeatureCollection = deCollection;
			isErrorLayer = true;
			errorLayer = new ErrorLayer();
			errorLayer.setErrFeatureCollection(errDeCollection);
			errorLayer.setDetailsValidatorReport(detailsValidatorResults);

			ISOValidatorField isoValidatorField = new ISOValidatorField(layerID, simpleFeatureCollection.size(), weight);
			isoValidatorField.createISOField(errDeCollection);
			errorLayer.setISOValidatorReport(isoValidatorField);
		}
	}

	// 객체 꼬임 여부 검사
	public ErrorFeature featureDataCheck(SimpleFeature simpleFeature) throws SchemaException {
		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		geometry.getCoordinate();
		DataConvertor dataConvertor = new DataConvertor();

		// 객체가 꼬여있으면
		if (!geometry.isValid()) {
			String simID = simpleFeature.getID();

			GeometryFactory f = new GeometryFactory();
			Coordinate[] coord = geometry.getCoordinates();
			Geometry errGeometry = f.createPoint(coord[0]);
			double coorX = geometry.getCoordinate().x;
			double coorY = geometry.getCoordinate().y;

			SimpleFeature errSimpleFeature = dataConvertor.createErrSimpleFeature(simID, errGeometry, "InvalidType", "TypeError");
			DetailsValidatorResult dtReport = new DetailsValidatorResult(simID, "TypeError", "invalidType", coorX, coorY);
			ErrorFeature errorFeature = new ErrorFeature(errSimpleFeature, dtReport); // 에러피처생성

			return errorFeature;
		} else { // 객체가 꼬여있지 않으면
			return null;
		}
	}
}
