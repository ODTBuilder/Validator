package com.git.gdsbuilder.validator.layer;

<<<<<<< HEAD
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.json.simple.JSONArray;

import com.git.gdsbuilder.type.layer.ErrorLayer;
import com.git.gdsbuilder.type.layer.ValidatorLayer;
import com.git.gdsbuilder.type.result.DetailsValidatorResult;
import com.git.gdsbuilder.type.result.ISOValidatorField;
=======
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
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
import com.git.gdsbuilder.type.validatorOption.AttributeFix;
import com.git.gdsbuilder.type.validatorOption.ConBreak;
import com.git.gdsbuilder.type.validatorOption.ConIntersected;
import com.git.gdsbuilder.type.validatorOption.ConOverDegree;
import com.git.gdsbuilder.type.validatorOption.EntityDuplicated;
<<<<<<< HEAD
import com.git.gdsbuilder.type.validatorOption.OutBoundary;
=======
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
import com.git.gdsbuilder.type.validatorOption.PointDuplicated;
import com.git.gdsbuilder.type.validatorOption.SelfEntity;
import com.git.gdsbuilder.type.validatorOption.SmallArea;
import com.git.gdsbuilder.type.validatorOption.SmallLength;
<<<<<<< HEAD
import com.git.gdsbuilder.type.validatorOption.UselessPoint;
import com.git.gdsbuilder.type.validatorOption.ValidatorOption;
import com.git.gdsbuilder.type.validatorOption.Z_ValueAmbiguous;
import com.git.gdsbuilder.validator.feature.FeatureValidator;
import com.git.gdsbuilder.validator.feature.FeatureValidatorImpl;
import com.git.gdsbuilder.validator.tmp.AttributeFixValidator;
import com.git.gdsbuilder.validator.tmp.EntityDuplicatedValidator;
import com.git.gdsbuilder.validator.tmp.SelfEntityMultiValidator;
import com.git.gdsbuilder.validator.tmp.SelfEntityValidator;
import com.git.gdsbuilder.validator.tmp.SmallAreaValidator;
import com.git.gdsbuilder.validator.tmp.SmallLengthValidator;

public class LayerValidatorImpl implements LayerValidator {

	@Override
	public ErrorLayer validateLayers(List<ValidatorLayer> validatorLayers,
			SimpleFeatureCollection aopSFC) throws Exception {

		System.gc();
=======
import com.git.gdsbuilder.type.validatorOption.ValidatorOption;
import com.git.gdsbuilder.validator.feature.FeatureValidator;
import com.git.gdsbuilder.validator.feature.FeatureValidatorImpl;
import com.git.gdsbuilder.validator.result.ISOValidatorResult;

public class LayerValidatorImpl implements LayerValidator {

	@SuppressWarnings("unchecked")
	@Override
	public ErrorLayer validateLayers(List<ValidatorLayer> qaLayers) throws SchemaException {
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c

		FeatureValidator featureValidator = new FeatureValidatorImpl();

		// errLayer
<<<<<<< HEAD
		ErrorLayer returnErrLayer = new ErrorLayer();
		int validatorLayersSize = validatorLayers.size();
		for (int i = 0; i < validatorLayersSize; i++) {
			ErrorLayer layerErrLayer = new ErrorLayer();
			// getLayer
			ValidatorLayer validatorLayer = validatorLayers.get(i);
			String vaLayerID = validatorLayer.getLayerID();
			double weight = validatorLayer.getWeigth();
			List<ValidatorOption> validatorOptions = validatorLayer
					.getValidatorOptions();
			if (validatorOptions != null) {
				SimpleFeatureCollection validatorSfc = validatorLayer
						.getSimpleFeatureCollection();
				// ISOresult info
				String layerID = validatorLayer.getLayerID();
				double numOfItems = validatorSfc.size();

				// Test
				ExecutorService executorService = Executors
						.newCachedThreadPool();

				int validatorOptionSize = validatorOptions.size();
				for (int j = 0; j < validatorOptionSize; j++) {
					boolean isRelations = false;
					ErrorLayer optionErrLayer = new ErrorLayer();
					ValidatorOption validatorOption = validatorOptions.get(j);
					if (validatorOption instanceof AttributeFix) {
						JSONArray notNullAtt = ((AttributeFix) validatorOption)
								.getAttributeKey();
						// optionErrLayer =
						// featureValidator.validateAttributeFix(validatorSfc,
						// notNullAtt);

						Callable<ErrorLayer> validate = new AttributeFixValidator(
								validatorSfc, notNullAtt);
						Future<ErrorLayer> future = executorService
								.submit(validate);
						optionErrLayer = future.get();
						System.gc();
					}
					if (validatorOption instanceof ConBreak) {
						optionErrLayer = featureValidator.validateConBreak(
								validatorSfc, aopSFC);
					}
					if (validatorOption instanceof ConIntersected) {
						optionErrLayer = featureValidator
								.validateConIntersected(validatorSfc);
					}
					if (validatorOption instanceof ConOverDegree) {
						double inputDegree = ((ConOverDegree) validatorOption)
								.getDegree();
						optionErrLayer = featureValidator
								.validateConOverDegree(validatorSfc,
										inputDegree);
					}
					if (validatorOption instanceof EntityDuplicated) {
						// optionErrLayer =
						// featureValidator.validateEntityDuplicated(validatorSfc);
						Callable<ErrorLayer> validate = new EntityDuplicatedValidator(
								validatorSfc);
						Future<ErrorLayer> future = executorService
								.submit(validate);
						optionErrLayer = future.get();
					}
					if (validatorOption instanceof PointDuplicated) {
						optionErrLayer = featureValidator
								.validatePointDuplicated(validatorSfc);
					}
					if (validatorOption instanceof SelfEntity) {
						isRelations = true;
						List<ValidatorLayer> relation = ((SelfEntity) validatorOption)
								.getRelation();
						int relationSize = relation.size();
						for (int k = 0; k < relationSize; k++) {
							ValidatorLayer relationLayer = relation.get(k);
							String reLayerID = relationLayer.getLayerID();
							if (vaLayerID.equals(reLayerID)) {
								// optionErrLayer =
								// featureValidator.validateSelfEntity(validatorSfc);

								Callable<ErrorLayer> validate = new SelfEntityValidator(
										validatorSfc);
								Future<ErrorLayer> future = executorService
										.submit(validate);
								optionErrLayer = future.get();
								System.gc();
								if (optionErrLayer != null) {
									List<DetailsValidatorResult> tempReports = optionErrLayer
											.getDetailsValidatorReport();
									for (DetailsValidatorResult temp : tempReports) {
										temp.setLayerID(layerID);
									}
									layerErrLayer
											.addErrorFeatureCollection(optionErrLayer
													.getErrFeatureCollection());
									layerErrLayer
											.addAllDetailsValidatorReport(tempReports);
								}
							} else {
								SimpleFeatureCollection relationSfc = relationLayer
										.getSimpleFeatureCollection();
								Callable<ErrorLayer> validate = new SelfEntityMultiValidator(
										validatorSfc, relationSfc);
								Future<ErrorLayer> future = executorService
										.submit(validate);
								optionErrLayer = future.get();
								System.gc();
								// optionErrLayer =
								// featureValidator.validateSelfEntity(validatorSfc,
								// relationSfc);
								if (optionErrLayer != null) {
									List<DetailsValidatorResult> tempReports = optionErrLayer
											.getDetailsValidatorReport();
									for (DetailsValidatorResult temp : tempReports) {
										temp.setLayerID(layerID);
									}
									layerErrLayer
											.addErrorFeatureCollection(optionErrLayer
													.getErrFeatureCollection());
									layerErrLayer
											.addAllDetailsValidatorReport(tempReports);
								}
							}
						}
					}
					if (validatorOption instanceof SmallArea) {
						double inputArea = ((SmallArea) validatorOption)
								.getArea();
						// optionErrLayer =
						// featureValidator.validateSmallArea(validatorSfc,
						// inputArea);

						Callable<ErrorLayer> validate = new SmallAreaValidator(
								validatorSfc, inputArea);
						Future<ErrorLayer> future = executorService
								.submit(validate);
						optionErrLayer = future.get();
						System.gc();
					}
					if (validatorOption instanceof SmallLength) {
						double inputLegth = ((SmallLength) validatorOption)
								.getLength();
						// optionErrLayer =
						// featureValidator.validateSmallLength(validatorSfc,
						// inputLegth);

						Callable<ErrorLayer> validate = new SmallLengthValidator(
								validatorSfc, inputLegth);
						Future<ErrorLayer> future = executorService
								.submit(validate);
						optionErrLayer = future.get();
						System.gc();
					}
					if (validatorOption instanceof Z_ValueAmbiguous) {
						JSONArray notNullAtt = ((Z_ValueAmbiguous) validatorOption)
								.getAttributeKey();
						optionErrLayer = featureValidator
								.validateZvalueAmbiguous(validatorSfc,
										notNullAtt);
					}
					if (validatorOption instanceof UselessPoint) {
						optionErrLayer = featureValidator
								.validateUselessPoint(validatorSfc);
					}
					if (validatorOption instanceof OutBoundary) {
						isRelations = true;
						List<ValidatorLayer> relation = ((OutBoundary) validatorOption)
								.getRelation();
						int relationSize = relation.size();
						for (int k = 0; k < relationSize; k++) {
							ValidatorLayer relationLayer = relation.get(k);
							String reLayerID = relationLayer.getLayerID();
							if (vaLayerID.equals(reLayerID)) {
							} else {
								SimpleFeatureCollection relationSfc = relationLayer
										.getSimpleFeatureCollection();
								optionErrLayer = featureValidator
										.validateOutBoundary(validatorSfc,
												relationSfc);
								if (optionErrLayer != null) {
									List<DetailsValidatorResult> tempReports = optionErrLayer
											.getDetailsValidatorReport();
									for (DetailsValidatorResult temp : tempReports) {
										temp.setLayerID(layerID);
									}
									layerErrLayer
											.addErrorFeatureCollection(optionErrLayer
													.getErrFeatureCollection());
									layerErrLayer
											.addAllDetailsValidatorReport(tempReports);
								}
							}
						}
					}
					if (isRelations == false && optionErrLayer != null) {
						List<DetailsValidatorResult> tempReports = optionErrLayer
								.getDetailsValidatorReport();
						for (DetailsValidatorResult temp : tempReports) {
							temp.setLayerID(layerID);
						}
						layerErrLayer.addErrorFeatureCollection(optionErrLayer
								.getErrFeatureCollection());
						layerErrLayer.addAllDetailsValidatorReport(tempReports);
					}
					// } catch (Exception e) {
					// continue;
					// }

					System.gc();
				}
				returnErrLayer.addErrorFeatureCollection(layerErrLayer
						.getErrFeatureCollection());
				returnErrLayer.addAllDetailsValidatorReport(layerErrLayer
						.getDetailsValidatorReport());
				ISOValidatorField isoValidatorField = new ISOValidatorField(
						layerID, numOfItems, weight);
				isoValidatorField.createISOField(layerErrLayer
						.getErrFeatureCollection());
				returnErrLayer.addISOValidatorField(isoValidatorField);

				executorService.shutdown();
			} else {
				continue;
			}
		}
		System.gc();
		return returnErrLayer;
=======
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
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
	}
}
