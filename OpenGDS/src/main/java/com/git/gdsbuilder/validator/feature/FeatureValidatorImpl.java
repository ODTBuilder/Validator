package com.git.gdsbuilder.validator.feature;

import java.util.ArrayList;
import java.util.List;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.SchemaException;
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

public class FeatureValidatorImpl implements FeatureValidator {

	@Override
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
		} else {
			return null;
		}
	}

	@Override
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
		} else {
			return null;
		}
	}

	@Override
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
		} else {
			return null;
		}
	}

	@Override
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
		} else {
			return null;
		}
	}

	@Override
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
		} else {
			return null;
		}
	}

	@Override
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
		} else {
			return null;
		}
	}
}
