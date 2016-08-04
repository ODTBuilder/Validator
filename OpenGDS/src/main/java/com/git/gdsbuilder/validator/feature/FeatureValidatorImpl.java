package com.git.gdsbuilder.validator.feature;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.SchemaException;
import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.type.result.DetailsValidatorReport;
import com.git.gdsbuilder.validator.factory.ValidatorFactory;
import com.git.gdsbuilder.validator.factory.ValidatorFactoryImpl;

public class FeatureValidatorImpl implements FeatureValidator {

	@Override
	public SimpleFeatureCollection validateAttributeFix(SimpleFeatureCollection validatorLayer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> validateConBreak(SimpleFeatureCollection validatorLayer) throws SchemaException {

		DefaultFeatureCollection errDfc = new DefaultFeatureCollection();
		List<DetailsValidatorReport> errReps = new ArrayList<DetailsValidatorReport>();

		ValidatorFactory validatorFactory = new ValidatorFactoryImpl();
		SimpleFeatureIterator simpleFeatureIterator = validatorLayer.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			Map<String, Object> errMap = validatorFactory.conBreak(simpleFeature);
			if (errMap != null) {
				errDfc.add((SimpleFeature) errMap.get("simpleFeature"));
				DetailsValidatorReport errRep = (DetailsValidatorReport) errMap.get("detailsReport");
				errRep.setLayerID(validatorLayer.getID());
				errReps.add(errRep);
			}
		}
		if (errDfc.size() != 0) {
			Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
			returnMap.put("errSimpleFeatureCollection", errDfc);
			returnMap.put("detailReports", errReps);
			return returnMap;
		} else {
			return null;
		}
	}

	@Override
	public Map<String, Object> validateConIntersected(SimpleFeatureCollection validatorLayer) throws SchemaException {

		DefaultFeatureCollection errDfc = new DefaultFeatureCollection();
		List<DetailsValidatorReport> errReps = new ArrayList<DetailsValidatorReport>();

		ValidatorFactory validatorFactory = new ValidatorFactoryImpl();
		Vector<SimpleFeature> simpleFeatureV = new Vector<SimpleFeature>();
		SimpleFeatureIterator simpleFeatureIterator = validatorLayer.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			simpleFeatureV.add(simpleFeature);
		}
		for (int i = 0; i < simpleFeatureV.size() - 1; i++) {
			SimpleFeature simpleFeatureI = simpleFeatureV.get(i);
			SimpleFeature simpleFeatureJ = simpleFeatureV.get(i + 1);
			Map<String, Object> errMap = validatorFactory.conIntersected(simpleFeatureI, simpleFeatureJ);
			if (errMap != null) {
				errDfc.add((SimpleFeature) errMap.get("simpleFeature"));
				DetailsValidatorReport errRep = (DetailsValidatorReport) errMap.get("detailsReport");
				errRep.setLayerID(validatorLayer.getID());
				errReps.add(errRep);
			}
		}
		if (errDfc.size() != 0) {
			Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
			returnMap.put("errSimpleFeatureCollection", errDfc);
			returnMap.put("detailReports", errReps);
			return returnMap;
		} else {
			return null;
		}
	}

	@Override
	public Map<String, Object> validateConIntersected(SimpleFeatureCollection validatorLayer, SimpleFeatureCollection relationLayer) throws SchemaException {

		DefaultFeatureCollection errDfc = new DefaultFeatureCollection();
		List<DetailsValidatorReport> errReps = new ArrayList<DetailsValidatorReport>();
		ValidatorFactory validatorFactory = new ValidatorFactoryImpl();

		Vector<SimpleFeature> simpleFeaturesV = new Vector<SimpleFeature>();
		SimpleFeatureIterator simpleFeatureIteratorV = validatorLayer.features();
		while (simpleFeatureIteratorV.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIteratorV.next();
			simpleFeaturesV.add(simpleFeature);
		}

		Vector<SimpleFeature> simpleFeaturesR = new Vector<SimpleFeature>();
		SimpleFeatureIterator simpleFeatureIteratorR = relationLayer.features();
		while (simpleFeatureIteratorR.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIteratorR.next();
			simpleFeaturesR.add(simpleFeature);
		}

		for (int i = 0; i < simpleFeaturesV.size(); i++) {
			SimpleFeature simpleFeatureI = simpleFeaturesV.get(i);
			for (int j = 0; j < simpleFeaturesR.size(); j++) {
				SimpleFeature simpleFeatureJ = simpleFeaturesV.get(j);
				Map<String, Object> errMap = validatorFactory.conIntersected(simpleFeatureI, simpleFeatureJ);
				if (errMap != null) {
					errDfc.add((SimpleFeature) errMap.get("simpleFeature"));
					DetailsValidatorReport errRep = (DetailsValidatorReport) errMap.get("detailsReport");
					errRep.setLayerID(validatorLayer.getID());
					errReps.add(errRep);
				}
			}
		}
		if (errDfc.size() != 0) {
			Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
			returnMap.put("errSimpleFeatureCollection", errDfc);
			returnMap.put("detailReports", errReps);
			return returnMap;
		} else {
			return null;
		}
	}

	@Override
	public Map<String, Object> validateConOverDegree(SimpleFeatureCollection validatorLayer, double inputDegree) throws SchemaException {

		DefaultFeatureCollection errDfc = new DefaultFeatureCollection();
		List<DetailsValidatorReport> errReps = new ArrayList<DetailsValidatorReport>();
		ValidatorFactory validatorFactory = new ValidatorFactoryImpl();

		SimpleFeatureIterator simpleFeatureIterator = validatorLayer.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			Map<String, Object> errMap = validatorFactory.conOverDegree(simpleFeature, inputDegree);
			if (errMap != null) {
				errDfc.add((SimpleFeature) errMap.get("simpleFeature"));
				DetailsValidatorReport errRep = (DetailsValidatorReport) errMap.get("detailsReport");
				errRep.setLayerID(validatorLayer.getID());
				errReps.add(errRep);
			}
		}
		if (errDfc.size() != 0) {
			Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
			returnMap.put("errSimpleFeatureCollection", errDfc);
			returnMap.put("detailReports", errReps);
			return returnMap;
		} else {
			return null;
		}
	}

	@Override
	public Map<String, Object> validateEntityDuplicated(SimpleFeatureCollection validatorLayer) throws SchemaException {

		DefaultFeatureCollection errDfc = new DefaultFeatureCollection();
		List<DetailsValidatorReport> errReps = new ArrayList<DetailsValidatorReport>();
		ValidatorFactory validatorFactory = new ValidatorFactoryImpl();

		Vector<SimpleFeature> simpleFeatureV = new Vector<SimpleFeature>();
		SimpleFeatureIterator simpleFeatureIterator = validatorLayer.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			simpleFeatureV.add(simpleFeature);
		}

		for (int i = 0; i < simpleFeatureV.size() - 1; i++) {
			SimpleFeature simpleFeatureI = simpleFeatureV.get(i);
			SimpleFeature simpleFeatureJ = simpleFeatureV.get(i + 1);
			Map<String, Object> errMap = validatorFactory.entityDuplicated(simpleFeatureI, simpleFeatureJ);
			if (errMap != null) {
				errDfc.add((SimpleFeature) errMap.get("simpleFeature"));
				DetailsValidatorReport errRep = (DetailsValidatorReport) errMap.get("detailsReport");
				errRep.setLayerID(validatorLayer.getID());
				errReps.add(errRep);
			}
		}
		if (errDfc.size() != 0) {
			Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
			returnMap.put("errSimpleFeatureCollection", errDfc);
			returnMap.put("detailReports", errReps);
			return returnMap;
		} else {
			return null;
		}
	}

	@Override
	public Map<String, Object> validateEntityDuplicated(SimpleFeatureCollection validatorLayer, SimpleFeatureCollection relationLayer) throws SchemaException {

		DefaultFeatureCollection errDfc = new DefaultFeatureCollection();
		List<DetailsValidatorReport> errReps = new ArrayList<DetailsValidatorReport>();
		ValidatorFactory validatorFactory = new ValidatorFactoryImpl();

		Vector<SimpleFeature> simpleFeaturesV = new Vector<SimpleFeature>();
		SimpleFeatureIterator simpleFeatureIteratorV = validatorLayer.features();
		while (simpleFeatureIteratorV.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIteratorV.next();
			simpleFeaturesV.add(simpleFeature);
		}

		Vector<SimpleFeature> simpleFeaturesR = new Vector<SimpleFeature>();
		SimpleFeatureIterator simpleFeatureIteratorR = relationLayer.features();
		while (simpleFeatureIteratorR.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIteratorR.next();
			simpleFeaturesR.add(simpleFeature);
		}

		for (int i = 0; i < simpleFeaturesV.size(); i++) {
			SimpleFeature simpleFeatureI = simpleFeaturesV.get(i);
			for (int j = 0; j < simpleFeaturesR.size(); j++) {
				SimpleFeature simpleFeatureJ = simpleFeaturesR.get(j);
				Map<String, Object> errMap = validatorFactory.entityDuplicated(simpleFeatureI, simpleFeatureJ);
				if (errMap != null) {
					errDfc.add((SimpleFeature) errMap.get("simpleFeature"));
					DetailsValidatorReport errRep = (DetailsValidatorReport) errMap.get("detailsReport");
					errRep.setLayerID(validatorLayer.getID());
					errReps.add(errRep);
				}
			}
		}
		if (errDfc.size() != 0) {
			Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
			returnMap.put("errSimpleFeatureCollection", errDfc);
			returnMap.put("detailReports", errReps);
			return returnMap;
		} else {
			return null;
		}
	}

	@Override
	public Map<String, Object> validatePointDuplicated(SimpleFeatureCollection validatorLayer) throws SchemaException {

		DefaultFeatureCollection errDfc = new DefaultFeatureCollection();
		List<DetailsValidatorReport> errReps = new ArrayList<DetailsValidatorReport>();
		ValidatorFactory validatorFactory = new ValidatorFactoryImpl();

		SimpleFeatureIterator simpleFeatureIterator = validatorLayer.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			Map<String, Object> errMap = validatorFactory.pointDuplicated(simpleFeature);
			if (errMap != null) {
				errDfc.add((SimpleFeature) errMap.get("simpleFeature"));
				DetailsValidatorReport errRep = (DetailsValidatorReport) errMap.get("detailsReport");
				errRep.setLayerID(validatorLayer.getID());
				errReps.add(errRep);
			}
		}
		if (errDfc.size() != 0) {
			Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
			returnMap.put("errSimpleFeatureCollection", errDfc);
			returnMap.put("detailReports", errReps);
			return returnMap;
		} else {
			return null;
		}
	}

	@Override
	public SimpleFeatureCollection validateSelfEntity(SimpleFeatureCollection validatorLayer) throws SchemaException {

		return null;
	}

	@Override
	public SimpleFeatureCollection validateSelfEntity(SimpleFeatureCollection validatorLayer, SimpleFeatureCollection relationLayer) throws SchemaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> validateSmallArea(SimpleFeatureCollection validatorLayer, double inputArea) throws SchemaException {

		DefaultFeatureCollection errDfc = new DefaultFeatureCollection();
		List<DetailsValidatorReport> errReps = new ArrayList<DetailsValidatorReport>();

		ValidatorFactory validatorFactory = new ValidatorFactoryImpl();
		SimpleFeatureIterator simpleFeatureIterator = validatorLayer.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			Map<String, Object> errMap = validatorFactory.smallArea(simpleFeature, inputArea);
			if (errMap != null) {
				errDfc.add((SimpleFeature) errMap.get("simpleFeature"));
				DetailsValidatorReport errRep = (DetailsValidatorReport) errMap.get("detailsReport");
				errRep.setLayerID(validatorLayer.getID());
				errReps.add(errRep);
			}
		}
		if (errDfc.size() != 0) {
			Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
			returnMap.put("errSimpleFeatureCollection", errDfc);
			returnMap.put("detailReports", errReps);
			return returnMap;
		} else {
			return null;
		}
	}

	@Override
	public Map<String, Object> validateSmallLength(SimpleFeatureCollection validatorLayer, double inputLength) throws SchemaException {

		DefaultFeatureCollection errDfc = new DefaultFeatureCollection();
		List<DetailsValidatorReport> errReps = new ArrayList<DetailsValidatorReport>();

		ValidatorFactory validatorFactory = new ValidatorFactoryImpl();
		SimpleFeatureIterator simpleFeatureIterator = validatorLayer.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			Map<String, Object> errMap = validatorFactory.smallLength(simpleFeature, inputLength);
			if (errMap != null) {
				errDfc.add((SimpleFeature) errMap.get("simpleFeature"));
				DetailsValidatorReport errRep = (DetailsValidatorReport) errMap.get("detailsReport");
				errRep.setLayerID(validatorLayer.getID());
				errReps.add(errRep);
			}
		}
		if (errDfc.size() != 0) {
			Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
			returnMap.put("errSimpleFeatureCollection", errDfc);
			returnMap.put("detailReports", errReps);
			return returnMap;
		} else {
			return null;
		}
	}
}
