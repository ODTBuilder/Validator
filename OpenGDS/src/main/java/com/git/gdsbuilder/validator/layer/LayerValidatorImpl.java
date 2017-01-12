package com.git.gdsbuilder.validator.layer;

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
import com.git.gdsbuilder.type.validatorOption.AttributeFix;
import com.git.gdsbuilder.type.validatorOption.ConBreak;
import com.git.gdsbuilder.type.validatorOption.ConIntersected;
import com.git.gdsbuilder.type.validatorOption.ConOverDegree;
import com.git.gdsbuilder.type.validatorOption.EntityDuplicated;
import com.git.gdsbuilder.type.validatorOption.OutBoundary;
import com.git.gdsbuilder.type.validatorOption.PointDuplicated;
import com.git.gdsbuilder.type.validatorOption.SelfEntity;
import com.git.gdsbuilder.type.validatorOption.SmallArea;
import com.git.gdsbuilder.type.validatorOption.SmallLength;
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
	public ErrorLayer validateLayers(List<ValidatorLayer> validatorLayers, SimpleFeatureCollection aopSFC) throws Exception {

		FeatureValidator featureValidator = new FeatureValidatorImpl();

		// errLayer
		ErrorLayer returnErrLayer = new ErrorLayer();
		int validatorLayersSize = validatorLayers.size();
		for (int i = 0; i < validatorLayersSize; i++) {
			ErrorLayer layerErrLayer = new ErrorLayer();
			// getLayer
			ValidatorLayer validatorLayer = validatorLayers.get(i);
			String vaLayerID = validatorLayer.getLayerID();
			double weight = validatorLayer.getWeigth();
			List<ValidatorOption> validatorOptions = validatorLayer.getValidatorOptions();
			if (validatorOptions != null) {
				SimpleFeatureCollection validatorSfc = validatorLayer.getSimpleFeatureCollection();
				// ISOresult info
				String layerID = validatorLayer.getLayerID();
				double numOfItems = validatorSfc.size();

				// Test
				ExecutorService executorService = Executors.newCachedThreadPool();

				int validatorOptionSize = validatorOptions.size();
				for (int j = 0; j < validatorOptionSize; j++) {
					boolean isRelations = false;
					ErrorLayer optionErrLayer = new ErrorLayer();
					ValidatorOption validatorOption = validatorOptions.get(j);
					if (validatorOption instanceof AttributeFix) {
						JSONArray notNullAtt = ((AttributeFix) validatorOption).getAttributeKey();
						// optionErrLayer =
						// featureValidator.validateAttributeFix(validatorSfc,
						// notNullAtt);

						Callable<ErrorLayer> validate = new AttributeFixValidator(validatorSfc, notNullAtt);
						Future<ErrorLayer> future = executorService.submit(validate);
						optionErrLayer = future.get();
					}
					if (validatorOption instanceof ConBreak) {
						optionErrLayer = featureValidator.validateConBreak(validatorSfc, aopSFC);
					}
					if (validatorOption instanceof ConIntersected) {
						optionErrLayer = featureValidator.validateConIntersected(validatorSfc);
					}
					if (validatorOption instanceof ConOverDegree) {
						double inputDegree = ((ConOverDegree) validatorOption).getDegree();
						optionErrLayer = featureValidator.validateConOverDegree(validatorSfc, inputDegree);
					}
					if (validatorOption instanceof EntityDuplicated) {
						// optionErrLayer =
						// featureValidator.validateEntityDuplicated(validatorSfc);
						Callable<ErrorLayer> validate = new EntityDuplicatedValidator(validatorSfc);
						Future<ErrorLayer> future = executorService.submit(validate);
						optionErrLayer = future.get();
					}
					if (validatorOption instanceof PointDuplicated) {
						optionErrLayer = featureValidator.validatePointDuplicated(validatorSfc);
					}
					if (validatorOption instanceof SelfEntity) {
						isRelations = true;
						List<ValidatorLayer> relation = ((SelfEntity) validatorOption).getRelation();
						int relationSize = relation.size();
						for (int k = 0; k < relationSize; k++) {
							ValidatorLayer relationLayer = relation.get(k);
							String reLayerID = relationLayer.getLayerID();
							if (vaLayerID.equals(reLayerID)) {
								// optionErrLayer =
								// featureValidator.validateSelfEntity(validatorSfc);

								Callable<ErrorLayer> validate = new SelfEntityValidator(validatorSfc);
								Future<ErrorLayer> future = executorService.submit(validate);
								optionErrLayer = future.get();

								if (optionErrLayer != null) {
									List<DetailsValidatorResult> tempReports = optionErrLayer.getDetailsValidatorReport();
									for (DetailsValidatorResult temp : tempReports) {
										temp.setLayerID(layerID);
									}
									layerErrLayer.addErrorFeatureCollection(optionErrLayer.getErrFeatureCollection());
									layerErrLayer.addAllDetailsValidatorReport(tempReports);
								}
							} else {
								SimpleFeatureCollection relationSfc = relationLayer.getSimpleFeatureCollection();
								Callable<ErrorLayer> validate = new SelfEntityMultiValidator(validatorSfc, relationSfc);
								Future<ErrorLayer> future = executorService.submit(validate);
								optionErrLayer = future.get();

								// optionErrLayer =
								// featureValidator.validateSelfEntity(validatorSfc,
								// relationSfc);
								if (optionErrLayer != null) {
									List<DetailsValidatorResult> tempReports = optionErrLayer.getDetailsValidatorReport();
									for (DetailsValidatorResult temp : tempReports) {
										temp.setLayerID(layerID);
									}
									layerErrLayer.addErrorFeatureCollection(optionErrLayer.getErrFeatureCollection());
									layerErrLayer.addAllDetailsValidatorReport(tempReports);
								}
							}
						}
					}
					if (validatorOption instanceof SmallArea) {
						double inputArea = ((SmallArea) validatorOption).getArea();
						// optionErrLayer =
						// featureValidator.validateSmallArea(validatorSfc,
						// inputArea);

						Callable<ErrorLayer> validate = new SmallAreaValidator(validatorSfc, inputArea);
						Future<ErrorLayer> future = executorService.submit(validate);
						optionErrLayer = future.get();
					}
					if (validatorOption instanceof SmallLength) {
						double inputLegth = ((SmallLength) validatorOption).getLength();
						// optionErrLayer =
						// featureValidator.validateSmallLength(validatorSfc,
						// inputLegth);

						Callable<ErrorLayer> validate = new SmallLengthValidator(validatorSfc, inputLegth);
						Future<ErrorLayer> future = executorService.submit(validate);
						optionErrLayer = future.get();
					}
					if (validatorOption instanceof Z_ValueAmbiguous) {
						JSONArray notNullAtt = ((Z_ValueAmbiguous) validatorOption).getAttributeKey();
						optionErrLayer = featureValidator.validateZvalueAmbiguous(validatorSfc, notNullAtt);
					}
					if (validatorOption instanceof UselessPoint) {
						optionErrLayer = featureValidator.validateUselessPoint(validatorSfc);
					}
					if (validatorOption instanceof OutBoundary) {
						isRelations = true;
						List<ValidatorLayer> relation = ((OutBoundary) validatorOption).getRelation();
						int relationSize = relation.size();
						for (int k = 0; k < relationSize; k++) {
							ValidatorLayer relationLayer = relation.get(k);
							String reLayerID = relationLayer.getLayerID();
							if (vaLayerID.equals(reLayerID)) {
							} else {
								SimpleFeatureCollection relationSfc = relationLayer.getSimpleFeatureCollection();
								optionErrLayer = featureValidator.validateOutBoundary(validatorSfc, relationSfc);
								if (optionErrLayer != null) {
									List<DetailsValidatorResult> tempReports = optionErrLayer.getDetailsValidatorReport();
									for (DetailsValidatorResult temp : tempReports) {
										temp.setLayerID(layerID);
									}
									layerErrLayer.addErrorFeatureCollection(optionErrLayer.getErrFeatureCollection());
									layerErrLayer.addAllDetailsValidatorReport(tempReports);
								}
							}
						}
					}
					if (isRelations == false && optionErrLayer != null) {
						List<DetailsValidatorResult> tempReports = optionErrLayer.getDetailsValidatorReport();
						for (DetailsValidatorResult temp : tempReports) {
							temp.setLayerID(layerID);
						}
						layerErrLayer.addErrorFeatureCollection(optionErrLayer.getErrFeatureCollection());
						layerErrLayer.addAllDetailsValidatorReport(tempReports);
					}
					// } catch (Exception e) {
					// continue;
					// }
				}
				returnErrLayer.addErrorFeatureCollection(layerErrLayer.getErrFeatureCollection());
				returnErrLayer.addAllDetailsValidatorReport(layerErrLayer.getDetailsValidatorReport());
				ISOValidatorField isoValidatorField = new ISOValidatorField(layerID, numOfItems, weight);
				isoValidatorField.createISOField(layerErrLayer.getErrFeatureCollection());
				returnErrLayer.addISOValidatorField(isoValidatorField);

				executorService.shutdown();
			} else {
				continue;
			}
		}
		if (returnErrLayer.getDetailsValidatorReport().size() == 0 || returnErrLayer.getErrFeatureCollection().size() == 0
				|| returnErrLayer.getISOValidatorReport().size()== 0) {
			return null;
		} else {
			return returnErrLayer;
		}
	}
}
