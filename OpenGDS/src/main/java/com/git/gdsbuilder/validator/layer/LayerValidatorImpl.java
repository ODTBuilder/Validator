package com.git.gdsbuilder.validator.layer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.SchemaException;
import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.type.layer.ErrorLayer;
import com.git.gdsbuilder.type.layer.ValidatorLayer;
import com.git.gdsbuilder.type.result.DetailsValidatorReport;
import com.git.gdsbuilder.type.result.ISOValidatorReport;
import com.git.gdsbuilder.type.validatorOption.AttributeFix;
import com.git.gdsbuilder.type.validatorOption.ConBreak;
import com.git.gdsbuilder.type.validatorOption.ConIntersected;
import com.git.gdsbuilder.type.validatorOption.ConOverDegree;
import com.git.gdsbuilder.type.validatorOption.EntityDuplicated;
import com.git.gdsbuilder.type.validatorOption.PointDuplicated;
import com.git.gdsbuilder.type.validatorOption.SelfEntity;
import com.git.gdsbuilder.type.validatorOption.SmallArea;
import com.git.gdsbuilder.type.validatorOption.SmallLength;
import com.git.gdsbuilder.type.validatorOption.ValidatorOption;
import com.git.gdsbuilder.validator.feature.FeatureValidator;
import com.git.gdsbuilder.validator.feature.FeatureValidatorImpl;
import com.git.gdsbuilder.validator.result.ISOValidatorResult;

public class LayerValidatorImpl implements LayerValidator {

	@SuppressWarnings("unchecked")
	@Override
	public ErrorLayer validateLayers(List<ValidatorLayer> qaLayers) throws SchemaException {

		FeatureValidator featureValidator = new FeatureValidatorImpl();

		// errLayer
		DefaultFeatureCollection errDFC = new DefaultFeatureCollection();
		List<ISOValidatorReport> isoValidatorReports = new ArrayList<ISOValidatorReport>();
		List<DetailsValidatorReport> detailsValidatorReports = new ArrayList<DetailsValidatorReport>();

		for (int i = 0; i < qaLayers.size(); i++) {
			// getLayer
			ValidatorLayer validatorLayer = qaLayers.get(i);
			SimpleFeatureCollection validatorSfc = validatorLayer.getSimpleFeatureCollection();
			List<ValidatorOption> validatorOptions = validatorLayer.getValidatorOptions();

			// ISOresult info
			String layerName = validatorLayer.getLayerID();
			double numOfItems = validatorSfc.size();
			double weight = validatorLayer.getWeigth();
			double numOfErr = 0;
			boolean errLayer = false;

			for (int j = 0; j < validatorOptions.size(); j++) {
				ValidatorOption validatorOption = validatorOptions.get(j);
				if (validatorOption instanceof AttributeFix) {
				}
				if (validatorOption instanceof ConBreak) {
					Map<String, Object> temp = featureValidator.validateConBreak(validatorSfc);
					if (temp != null) {
						errDFC.add((SimpleFeature) temp.get("errSimpleFeatureCollection"));
						List<DetailsValidatorReport> errReps = (List<DetailsValidatorReport>) temp.get("detailReports");
						detailsValidatorReports.addAll(errReps);
						errLayer = true;
					}
				}
				if (validatorOption instanceof ConIntersected) {
					Map<String, Object> temp1 = featureValidator.validateConIntersected(validatorSfc);
					if (temp1 != null) {
						errDFC.add((SimpleFeature) temp1.get("errSimpleFeatureCollection"));
						List<DetailsValidatorReport> errReps = (List<DetailsValidatorReport>) temp1.get("detailReports");
						detailsValidatorReports.addAll(errReps);
						errLayer = true;
					}

					List<ValidatorLayer> relation = ((ConIntersected) validatorOption).getRelation();
					for (int k = 0; k < relation.size(); k++) {
						ValidatorLayer relationLayer = relation.get(k);
						SimpleFeatureCollection relationSfc = relationLayer.getSimpleFeatureCollection();
						Map<String, Object> temp2 = featureValidator.validateConIntersected(validatorSfc, relationSfc);
						if (temp2 != null) {
							errDFC.add((SimpleFeature) temp2.get("errSimpleFeatureCollection"));
							List<DetailsValidatorReport> errReps = (List<DetailsValidatorReport>) temp2.get("detailReports");
							detailsValidatorReports.addAll(errReps);
							errLayer = true;
						}
					}
				}
				if (validatorOption instanceof ConOverDegree) {
					double inputDegree = ((ConOverDegree) validatorOption).getDegree();
					Map<String, Object> temp = featureValidator.validateConOverDegree(validatorSfc, inputDegree);
					if (temp != null) {
						errDFC.add((SimpleFeature) temp.get("errSimpleFeatureCollection"));
						List<DetailsValidatorReport> errReps = (List<DetailsValidatorReport>) temp.get("detailReports");
						detailsValidatorReports.addAll(errReps);
						errLayer = true;
					}
				}
				if (validatorOption instanceof EntityDuplicated) {
					Map<String, Object> temp1 = featureValidator.validateEntityDuplicated(validatorSfc);
					if (temp1 != null) {
						errDFC.add((SimpleFeature) temp1.get("errSimpleFeatureCollection"));
						List<DetailsValidatorReport> errReps = (List<DetailsValidatorReport>) temp1.get("detailReports");
						detailsValidatorReports.addAll(errReps);
						errLayer = true;
					}
					List<ValidatorLayer> relation = ((EntityDuplicated) validatorOption).getRelation();
					for (int k = 0; k < relation.size(); k++) {
						ValidatorLayer relationLayer = relation.get(k);
						SimpleFeatureCollection relationSfc = relationLayer.getSimpleFeatureCollection();
						Map<String, Object> temp2 = featureValidator.validateEntityDuplicated(validatorSfc, relationSfc);
						if (temp2 != null) {
							errDFC.add((SimpleFeature) temp2.get("errSimpleFeatureCollection"));
							List<DetailsValidatorReport> errReps = (List<DetailsValidatorReport>) temp2.get("detailReports");
							detailsValidatorReports.addAll(errReps);
							errLayer = true;
						}
					}
				}
				if (validatorOption instanceof PointDuplicated) {
					Map<String, Object> temp = featureValidator.validatePointDuplicated(validatorSfc);
					if (temp != null) {
						errDFC.add((SimpleFeature) temp.get("errSimpleFeatureCollection"));
						List<DetailsValidatorReport> errReps = (List<DetailsValidatorReport>) temp.get("detailReports");
						detailsValidatorReports.addAll(errReps);
						errLayer = true;
					}
				}
				if (validatorOption instanceof SelfEntity) {
					SimpleFeatureCollection temp1 = featureValidator.validateSelfEntity(validatorSfc);
					if (temp1 != null) {
						errDFC.addAll(temp1);
						errLayer = true;
					}
					List<ValidatorLayer> relation = ((SelfEntity) validatorOption).getRelation();
					for (int k = 0; k < relation.size(); k++) {
						ValidatorLayer relationLayer = relation.get(k);
						SimpleFeatureCollection relationSfc = relationLayer.getSimpleFeatureCollection();
						SimpleFeatureCollection temp2 = featureValidator.validateSelfEntity(validatorSfc, relationSfc);
						if (temp2 != null) {
							errDFC.addAll(temp2);
							errLayer = true;
						}
					}
				}
				if (validatorOption instanceof SmallArea) {
					double inputArea = ((SmallArea) validatorOption).getArea();
					Map<String, Object> temp = featureValidator.validateSmallArea(validatorSfc, inputArea);
					if (temp != null) {
						errDFC.addAll((SimpleFeatureCollection) temp.get("errSimpleFeatureCollection"));
						List<DetailsValidatorReport> errReps = (List<DetailsValidatorReport>) temp.get("detailReports");
						detailsValidatorReports.addAll(errReps);
						errLayer = true;
					}
				}
				if (validatorOption instanceof SmallLength) {
					double inputLegth = ((SmallLength) validatorOption).getLength();
					Map<String, Object> temp = featureValidator.validateSmallLength(validatorSfc, inputLegth);
					if (temp != null) {
						errDFC.addAll((SimpleFeatureCollection) temp.get("errSimpleFeatureCollection"));
						List<DetailsValidatorReport> errReps = (List<DetailsValidatorReport>) temp.get("detailReports");
						detailsValidatorReports.addAll(errReps);
						errLayer = true;
					}
				}
				if (errLayer) {
					numOfErr++;
				}
			}
			// ISOValidatorReport
			ISOValidatorResult isoValidatorResult = new ISOValidatorResult(layerName, numOfItems, numOfErr, weight);
			ISOValidatorReport isoValidatorReport = isoValidatorResult.createISOValidatorReport();
			isoValidatorReports.add(isoValidatorReport);
		}
		if (detailsValidatorReports != null && isoValidatorReports != null && errDFC != null) {
			ErrorLayer errorLayer = new ErrorLayer();
			errorLayer.setDetailsValidatorReports(detailsValidatorReports);
			errorLayer.setIsoValidatorReports(isoValidatorReports);
			errorLayer.setErrSfc(errDFC);
			return errorLayer;
		} else {
			return null;
		}
	}
}
