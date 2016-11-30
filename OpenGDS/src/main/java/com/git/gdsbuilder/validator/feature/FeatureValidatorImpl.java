package com.git.gdsbuilder.validator.feature;

import java.util.ArrayList;
<<<<<<< HEAD
import java.util.List;
=======
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.SchemaException;
<<<<<<< HEAD
import org.json.simple.JSONArray;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.operation.TransformException;

import com.git.gdsbuilder.type.feature.ErrorFeature;
import com.git.gdsbuilder.type.layer.ErrorLayer;
import com.git.gdsbuilder.type.result.DetailsValidatorResult;
import com.git.gdsbuilder.validator.factory.AttributeValidator;
import com.git.gdsbuilder.validator.factory.AttributeValidatorImpl;
import com.git.gdsbuilder.validator.factory.GeometryValidator;
import com.git.gdsbuilder.validator.factory.GeometryValidatorImpl;
=======
import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.type.result.DetailsValidatorReport;
import com.git.gdsbuilder.validator.factory.ValidatorFactory;
import com.git.gdsbuilder.validator.factory.ValidatorFactoryImpl;
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c

public class FeatureValidatorImpl implements FeatureValidator {

	@Override
<<<<<<< HEAD
	public ErrorLayer validateAttributeFix(SimpleFeatureCollection validatorLayer, JSONArray notNullAtt) throws SchemaException {

		ErrorLayer errLayer = new ErrorLayer();
		DefaultFeatureCollection errSFC = new DefaultFeatureCollection();
		List<DetailsValidatorResult> dtReports = new ArrayList<DetailsValidatorResult>();

		AttributeValidator attributeValidator = new AttributeValidatorImpl();
		SimpleFeatureIterator simpleFeatureIterator = validatorLayer.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			ErrorFeature errFeature = attributeValidator.attributeFix(simpleFeature, notNullAtt);
			if (errFeature != null) {
				errSFC.add(errFeature.getErrFeature());
				dtReports.add(errFeature.getDtReport());
			} else {
				continue;
			}
		}
		if (errSFC.size() > 0) {
			errLayer.setErrFeatureCollection(errSFC);
			errLayer.setDetailsValidatorReport(dtReports);
			return errLayer;
		} else {
			return null;
		}
	}

	@Override
	public ErrorLayer validateZvalueAmbiguous(SimpleFeatureCollection validatorLayer, JSONArray notNullAtt) throws SchemaException {

		ErrorLayer errLayer = new ErrorLayer();
		DefaultFeatureCollection errSFC = new DefaultFeatureCollection();
		List<DetailsValidatorResult> dtReports = new ArrayList<DetailsValidatorResult>();

		AttributeValidator attributeValidator = new AttributeValidatorImpl();
		SimpleFeatureIterator simpleFeatureIterator = validatorLayer.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			ErrorFeature errFeature = attributeValidator.z_valueAmbiguous(simpleFeature, notNullAtt);
			if (errFeature != null) {
				errSFC.add(errFeature.getErrFeature());
				dtReports.add(errFeature.getDtReport());
			} else {
				continue;
			}
		}
		if (errSFC.size() > 0 && dtReports.size() > 0) {
			errLayer.setErrFeatureCollection(errSFC);
			errLayer.setDetailsValidatorReport(dtReports);
			return errLayer;
=======
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
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
		} else {
			return null;
		}
	}

	@Override
<<<<<<< HEAD
	public ErrorLayer validateConBreak(SimpleFeatureCollection validatorLayer, SimpleFeatureCollection aop) throws SchemaException {

		ErrorLayer errLayer = new ErrorLayer();
		DefaultFeatureCollection errSFC = new DefaultFeatureCollection();
		List<DetailsValidatorResult> dtReports = new ArrayList<DetailsValidatorResult>();

		GeometryValidator geometryValidator = new GeometryValidatorImpl();
		SimpleFeatureIterator simpleFeatureIterator = validatorLayer.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			List<ErrorFeature> errFeatures = geometryValidator.conBreak(simpleFeature, aop);
			if (errFeatures != null) {
				for (ErrorFeature tmp : errFeatures) {
					errSFC.add(tmp.getErrFeature());
					dtReports.add(tmp.getDtReport());
				}
			} else {
				continue;
			}
		}
		if (errSFC.size() > 0 && dtReports.size() > 0) {
			errLayer.setErrFeatureCollection(errSFC);
			errLayer.setDetailsValidatorReport(dtReports);
			return errLayer;
=======
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
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
		} else {
			return null;
		}
	}

	@Override
<<<<<<< HEAD
	public ErrorLayer validateConIntersected(SimpleFeatureCollection validatorLayer) throws SchemaException {

		ErrorLayer errLayer = new ErrorLayer();
		DefaultFeatureCollection errSFC = new DefaultFeatureCollection();
		List<DetailsValidatorResult> dtReports = new ArrayList<DetailsValidatorResult>();

		List<SimpleFeature> tmpsSimpleFeatures = new ArrayList<SimpleFeature>();
		SimpleFeatureIterator simpleFeatureIterator = validatorLayer.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			tmpsSimpleFeatures.add(simpleFeature);
		}

		GeometryValidator geometryValidator = new GeometryValidatorImpl();
		int tmpsSimpleFeaturesSize = tmpsSimpleFeatures.size();
		for (int i = 0; i < tmpsSimpleFeaturesSize - 1; i++) {
			SimpleFeature tmpSimpleFeatureI = tmpsSimpleFeatures.get(i);
			for (int j = i + 1; j < tmpsSimpleFeaturesSize; j++) {
				SimpleFeature tmpSimpleFeatureJ = tmpsSimpleFeatures.get(j);
				List<ErrorFeature> errFeatures = geometryValidator.conIntersected(tmpSimpleFeatureI, tmpSimpleFeatureJ);
				if (errFeatures != null) {
					for (ErrorFeature tmp : errFeatures) {
						errSFC.add(tmp.getErrFeature());
						dtReports.add(tmp.getDtReport());
					}
				} else {
					continue;
				}
			}
		}
		if (errSFC.size() > 0 && dtReports.size() > 0) {
			errLayer.setErrFeatureCollection(errSFC);
			errLayer.setDetailsValidatorReport(dtReports);
			return errLayer;
=======
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
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
		} else {
			return null;
		}
	}

	@Override
<<<<<<< HEAD
	public ErrorLayer validateConOverDegree(SimpleFeatureCollection validatorLayer, double inputDegree) throws SchemaException {

		ErrorLayer errLayer = new ErrorLayer();
		DefaultFeatureCollection errSFC = new DefaultFeatureCollection();
		List<DetailsValidatorResult> dtReports = new ArrayList<DetailsValidatorResult>();

		GeometryValidator geometryValidator = new GeometryValidatorImpl();
		SimpleFeatureIterator simpleFeatureIterator = validatorLayer.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			List<ErrorFeature> errFeatures = geometryValidator.conOverDegree(simpleFeature, inputDegree);
			if (errFeatures != null) {
				for (ErrorFeature tmp : errFeatures) {
					errSFC.add(tmp.getErrFeature());
					dtReports.add(tmp.getDtReport());
				}
			} else {
				continue;
			}
		}
		if (errSFC.size() > 0 && dtReports.size() > 0) {
			errLayer.setErrFeatureCollection(errSFC);
			errLayer.setDetailsValidatorReport(dtReports);
			return errLayer;
=======
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
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
		} else {
			return null;
		}
	}

	@Override
<<<<<<< HEAD
	public ErrorLayer validateUselessPoint(SimpleFeatureCollection validatorLayer) throws SchemaException, NoSuchAuthorityCodeException, FactoryException,
			TransformException {

		ErrorLayer errLayer = new ErrorLayer();
		DefaultFeatureCollection errSFC = new DefaultFeatureCollection();
		List<DetailsValidatorResult> dtReports = new ArrayList<DetailsValidatorResult>();

		GeometryValidator geometryValidator = new GeometryValidatorImpl();
		SimpleFeatureIterator simpleFeatureIterator = validatorLayer.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			List<ErrorFeature> errFeatures = geometryValidator.uselessPoint(simpleFeature);
			if (errFeatures != null) {
				for (ErrorFeature tmp : errFeatures) {
					errSFC.add(tmp.getErrFeature());
					dtReports.add(tmp.getDtReport());
				}
			} else {
				continue;
			}
		}
		if (errSFC.size() > 0 && dtReports.size() > 0) {
			errLayer.setErrFeatureCollection(errSFC);
			errLayer.setDetailsValidatorReport(dtReports);
			return errLayer;
		} else {
			return null;
		}

	}

	@Override
	public ErrorLayer validateEntityDuplicated(SimpleFeatureCollection validatorLayer) throws SchemaException {

		ErrorLayer errLayer = new ErrorLayer();
		DefaultFeatureCollection errSFC = new DefaultFeatureCollection();
		List<DetailsValidatorResult> dtReports = new ArrayList<DetailsValidatorResult>();

		List<SimpleFeature> tmpsSimpleFeatures = new ArrayList<SimpleFeature>();
		SimpleFeatureIterator simpleFeatureIterator = validatorLayer.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			tmpsSimpleFeatures.add(simpleFeature);
		}

		GeometryValidator geometryValidator = new GeometryValidatorImpl();

		int tmpSize = tmpsSimpleFeatures.size();
		for (int i = 0; i < tmpSize - 1; i++) {
			SimpleFeature tmpSimpleFeatureI = tmpsSimpleFeatures.get(i);
			for (int j = i + 1; j < tmpSize; j++) {
				SimpleFeature tmpSimpleFeatureJ = tmpsSimpleFeatures.get(j);
				ErrorFeature errFeature = geometryValidator.entityDuplicated(tmpSimpleFeatureI, tmpSimpleFeatureJ);
				if (errFeature != null) {
					errSFC.add(errFeature.getErrFeature());
					dtReports.add(errFeature.getDtReport());
				} else {
					continue;
				}
			}
		}
		if (errSFC.size() > 0 && dtReports.size() > 0) {
			errLayer.setErrFeatureCollection(errSFC);
			errLayer.setDetailsValidatorReport(dtReports);
			return errLayer;
		} else {
			return null;
		}

	}

	@Override
	public ErrorLayer validatePointDuplicated(SimpleFeatureCollection validatorLayer) throws SchemaException {

		ErrorLayer errLayer = new ErrorLayer();
		DefaultFeatureCollection errSFC = new DefaultFeatureCollection();
		List<DetailsValidatorResult> dtReports = new ArrayList<DetailsValidatorResult>();

		GeometryValidator geometryValidator = new GeometryValidatorImpl();
		SimpleFeatureIterator simpleFeatureIterator = validatorLayer.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			List<ErrorFeature> errFeatures = geometryValidator.pointDuplicated(simpleFeature);
			if (errFeatures != null) {
				for (ErrorFeature tmp : errFeatures) {
					errSFC.add(tmp.getErrFeature());
					dtReports.add(tmp.getDtReport());
				}
			} else {
				continue;
			}
		}
		if (errSFC.size() > 0 && dtReports.size() > 0) {
			errLayer.setErrFeatureCollection(errSFC);
			errLayer.setDetailsValidatorReport(dtReports);
			return errLayer;
		} else {
			return null;
		}

	}

	@Override
	public ErrorLayer validateSelfEntity(SimpleFeatureCollection validatorLayer) throws SchemaException {

		ErrorLayer errLayer = new ErrorLayer();
		DefaultFeatureCollection errSFC = new DefaultFeatureCollection();
		List<DetailsValidatorResult> dtReports = new ArrayList<DetailsValidatorResult>();

		List<SimpleFeature> tmpsSimpleFeatures = new ArrayList<SimpleFeature>();
		SimpleFeatureIterator simpleFeatureIterator = validatorLayer.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			tmpsSimpleFeatures.add(simpleFeature);
		}

		GeometryValidator geometryValidator = new GeometryValidatorImpl();
		int tmpSize = tmpsSimpleFeatures.size();
		for (int i = 0; i < tmpSize - 1; i++) {
			SimpleFeature tmpSimpleFeatureI = tmpsSimpleFeatures.get(i);
			for (int j = i + 1; j < tmpSize; j++) {
				SimpleFeature tmpSimpleFeatureJ = tmpsSimpleFeatures.get(j);
				List<ErrorFeature> errFeatures = geometryValidator.selfEntity(tmpSimpleFeatureI, tmpSimpleFeatureJ);
				if (errFeatures != null) {
					for (ErrorFeature tmp : errFeatures) {
						errSFC.add(tmp.getErrFeature());
						dtReports.add(tmp.getDtReport());
					}
				} else {
					continue;
				}
			}
		}
		if (errSFC.size() > 0 && dtReports.size() > 0) {
			errLayer.setErrFeatureCollection(errSFC);
			errLayer.setDetailsValidatorReport(dtReports);
			return errLayer;
		} else {
			return null;
		}
	}

	@Override
	public ErrorLayer validateSelfEntity(SimpleFeatureCollection validatorLayer, SimpleFeatureCollection relationLayer) throws SchemaException {

		ErrorLayer errLayer = new ErrorLayer();
		DefaultFeatureCollection errSFC = new DefaultFeatureCollection();
		List<DetailsValidatorResult> dtReports = new ArrayList<DetailsValidatorResult>();

		List<SimpleFeature> tmpSimpleFeaturesI = new ArrayList<SimpleFeature>();
		SimpleFeatureIterator simpleFeatureIteratorI = validatorLayer.features();
		while (simpleFeatureIteratorI.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIteratorI.next();
			tmpSimpleFeaturesI.add(simpleFeature);
		}

		List<SimpleFeature> tmpSimpleFeaturesJ = new ArrayList<SimpleFeature>();
		SimpleFeatureIterator simpleFeatureIteratorJ = relationLayer.features();
		while (simpleFeatureIteratorJ.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIteratorJ.next();
			tmpSimpleFeaturesJ.add(simpleFeature);
		}

		GeometryValidator geometryValidator = new GeometryValidatorImpl();
		int tmpSizeI = tmpSimpleFeaturesI.size();
		int tmpSizeJ = tmpSimpleFeaturesJ.size();
		for (int i = 0; i < tmpSizeI; i++) {
			SimpleFeature simpleFeatureI = tmpSimpleFeaturesI.get(i);
			for (int j = 0; j < tmpSizeJ; j++) {
				SimpleFeature simpleFeatureJ = tmpSimpleFeaturesJ.get(j);
				List<ErrorFeature> errFeatures = geometryValidator.selfEntity(simpleFeatureI, simpleFeatureJ);
				if (errFeatures != null) {
					for (ErrorFeature tmp : errFeatures) {
						errSFC.add(tmp.getErrFeature());
						dtReports.add(tmp.getDtReport());
					}
				} else {
					continue;
				}
			}
		}
		if (errSFC.size() > 0 && dtReports.size() > 0) {
			errLayer.setErrFeatureCollection(errSFC);
			errLayer.setDetailsValidatorReport(dtReports);
			return errLayer;
		} else {
			return null;
		}

	}

	@Override
	public ErrorLayer validateSmallArea(SimpleFeatureCollection validatorLayer, double inputArea) throws SchemaException {

		ErrorLayer errLayer = new ErrorLayer();
		DefaultFeatureCollection errSFC = new DefaultFeatureCollection();
		List<DetailsValidatorResult> dtReports = new ArrayList<DetailsValidatorResult>();

		GeometryValidator geometryValidator = new GeometryValidatorImpl();
		SimpleFeatureIterator simpleFeatureIterator = validatorLayer.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			ErrorFeature errFeature = geometryValidator.smallArea(simpleFeature, inputArea);
			if (errFeature != null) {
				errSFC.add(errFeature.getErrFeature());
				dtReports.add(errFeature.getDtReport());
			} else {
				continue;
			}
		}
		if (errSFC.size() > 0 && dtReports.size() > 0) {
			errLayer.setErrFeatureCollection(errSFC);
			errLayer.setDetailsValidatorReport(dtReports);
			return errLayer;
=======
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
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
		} else {
			return null;
		}
	}

	@Override
<<<<<<< HEAD
	public ErrorLayer validateSmallLength(SimpleFeatureCollection validatorLayer, double inputLength) throws SchemaException {

		ErrorLayer errLayer = new ErrorLayer();
		DefaultFeatureCollection errSFC = new DefaultFeatureCollection();
		List<DetailsValidatorResult> dtReports = new ArrayList<DetailsValidatorResult>();

		GeometryValidator geometryValidator = new GeometryValidatorImpl();
		SimpleFeatureIterator simpleFeatureIterator = validatorLayer.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			ErrorFeature errFeature = geometryValidator.smallLength(simpleFeature, inputLength);
			if (errFeature != null) {
				errSFC.add(errFeature.getErrFeature());
				dtReports.add(errFeature.getDtReport());
			} else {
				continue;
			}
		}
		if (errSFC.size() > 0 && dtReports.size() > 0) {
			errLayer.setErrFeatureCollection(errSFC);
			errLayer.setDetailsValidatorReport(dtReports);
			return errLayer;
		} else {
			return null;
		}
	}

	@Override
	public ErrorLayer validateOutBoundary(SimpleFeatureCollection validatorLayer, SimpleFeatureCollection relationLayer) throws SchemaException {

		ErrorLayer errLayer = new ErrorLayer();
		DefaultFeatureCollection errSFC = new DefaultFeatureCollection();
		List<DetailsValidatorResult> dtReports = new ArrayList<DetailsValidatorResult>();

		List<SimpleFeature> tmpSimpleFeaturesI = new ArrayList<SimpleFeature>();
		SimpleFeatureIterator simpleFeatureIteratorI = validatorLayer.features();
		while (simpleFeatureIteratorI.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIteratorI.next();
			tmpSimpleFeaturesI.add(simpleFeature);
		}

		List<SimpleFeature> tmpSimpleFeaturesJ = new ArrayList<SimpleFeature>();
		SimpleFeatureIterator simpleFeatureIteratorJ = relationLayer.features();
		while (simpleFeatureIteratorJ.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIteratorJ.next();
			tmpSimpleFeaturesJ.add(simpleFeature);
		}

		GeometryValidator geometryValidator = new GeometryValidatorImpl();
		int tmpSizeI = tmpSimpleFeaturesI.size();
		int tmpSizeJ = tmpSimpleFeaturesJ.size();
		for (int i = 0; i < tmpSizeI; i++) {
			SimpleFeature simpleFeatureI = tmpSimpleFeaturesI.get(i);
			for (int j = 0; j < tmpSizeJ; j++) {
				SimpleFeature simpleFeatureJ = tmpSimpleFeaturesJ.get(j);
				ErrorFeature errFeature = geometryValidator.outBoundary(simpleFeatureI, simpleFeatureJ);
				if (errFeature != null) {
					errSFC.add(errFeature.getErrFeature());
					dtReports.add(errFeature.getDtReport());
				} else {
					continue;
				}
			}

		}
		if (errSFC.size() > 0 && dtReports.size() > 0) {
			errLayer.setErrFeatureCollection(errSFC);
			errLayer.setDetailsValidatorReport(dtReports);
			return errLayer;
=======
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
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
		} else {
			return null;
		}
	}
}
