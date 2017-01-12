package com.git.opengds.service.builder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.git.gdsbuilder.convertor.DataConvertor;
import com.git.gdsbuilder.type.dataCheck.DataValidCheck;
import com.git.gdsbuilder.type.layer.ErrorLayer;
import com.git.gdsbuilder.type.layer.ValidatorLayer;
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
import com.git.gdsbuilder.validator.json.DTGeoJSON;
import com.git.gdsbuilder.validator.layer.LayerValidator;
import com.git.gdsbuilder.validator.layer.LayerValidatorImpl;

@Service
public class ValidatorServiceImpl implements ValidatorService {

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject autoValidation(String stringJSON) throws Exception {

		DataConvertor dataConvertor = new DataConvertor();
		LayerValidator layerValidator = new LayerValidatorImpl();
		if (stringJSON.equals("") || stringJSON == null) {
			return null;
		} else {
			// String to JSONObject
			JSONObject jsonObject = dataConvertor.stringToJSON(stringJSON);
			// 검수 영역
			Object extentObj = jsonObject.get("extent");
			if (extentObj == null) {
				return null;
			} else {
				JSONObject extent = dataConvertor.stringToJSON(extentObj.toString());
				DTGeoJSON dtGeoJSONExtent = new DTGeoJSON();
				dtGeoJSONExtent.geoJsonValidate(extent);
				JSONObject trueExtent = dtGeoJSONExtent.getTrueJSON();
				SimpleFeatureCollection aopFeatureCollection = dataConvertor.converToSimpleFeatureCollection(trueExtent);

				// FeatureCollection Valid
				DataValidCheck dataValidCheck = new DataValidCheck();
				dataValidCheck.extentCheck("extent", aopFeatureCollection);

				List<ValidatorLayer> layers = new ArrayList<ValidatorLayer>();
				JSONArray errFeatures = new JSONArray();

				// 검수 레이어 생성 및 ValidData 검수
				ErrorLayer returnErrLayer = new ErrorLayer();

				// FeatureCollection Setting
				JSONObject layerJSON = (JSONObject) jsonObject.get("layers");
				ErrorLayer invalidErrLayer;
				Iterator<String> layerIter = layerJSON.keySet().iterator();
				while (layerIter.hasNext()) {
					// Get Layer
					String layerID = layerIter.next();
					JSONObject layer = (JSONObject) layerJSON.get(layerID);
					JSONObject feature = (JSONObject) dataConvertor.stringToJSON(layer.get("feature").toString());

					// geoJSON Valid
					DTGeoJSON dtGeoJSONLayer = new DTGeoJSON();
					dtGeoJSONLayer.geoJsonValidate(feature);
					JSONObject trueJSON = dtGeoJSONLayer.getTrueJSON();
					JSONObject falseJSON = dtGeoJSONLayer.getFalseJSON();
					JSONObject errJSON = dtGeoJSONLayer.createErrorJSON(falseJSON);
					errFeatures.addAll((JSONArray) errJSON.get("features"));

					// ISO Report 파라미터
					Object weigthObj = layer.get("weight");
					String weightStr = "";
					if (weigthObj.equals("") || weigthObj == null) {
						weightStr = "0";
					} else {
						weightStr = weigthObj.toString();
						double weight = Double.parseDouble(weightStr);

						// 속성오류 검수 유무
						boolean attValidator = false;
						Object valiOptionObj = layer.get("qaOption");
						if (valiOptionObj == null) {
							continue;
						} else {
							JSONObject valiOption = (JSONObject) valiOptionObj;
							Iterator optionIter = valiOption.keySet().iterator();
							while (optionIter.hasNext()) {
								String valiOptionStr = (String) optionIter.next();
								if (valiOptionStr.equals("AttributeFix") || valiOptionStr.equals("zValueAmbiguous")) {
									attValidator = true;
								}
							}

							// convert geojson
							if (!attValidator) {
								SimpleFeatureCollection featureCollection = dataConvertor.converToSimpleFeatureCollection(trueJSON);
								dataValidCheck.dataCheck(layerID, featureCollection, weight);
								if (dataValidCheck.isErrorLayer()) {
									invalidErrLayer = dataValidCheck.getErrorLayer();
									if (invalidErrLayer != null) {
										returnErrLayer.addErrorFeatureCollection(invalidErrLayer.getErrFeatureCollection());
										returnErrLayer.addAllDetailsValidatorReport(invalidErrLayer.getDetailsValidatorReport());
										returnErrLayer.addAllISOValidatorField(invalidErrLayer.getISOValidatorReport());
									}
								}
								ValidatorLayer validatorLayer = new ValidatorLayer("type", layerID, featureCollection, weight);
								layers.add(validatorLayer);
							} else {
								JSONObject attribute = (JSONObject) layer.get("attribute");
								SimpleFeatureCollection featureCollection = dataConvertor.converToSimpleFeatureCollection(trueJSON, attribute);
								dataValidCheck.dataCheck(layerID, featureCollection, weight);

								// 오류 레이어
								if (dataValidCheck.isErrorLayer()) {
									invalidErrLayer = dataValidCheck.getErrorLayer();
									if (invalidErrLayer != null) {
										returnErrLayer.addErrorFeatureCollection(invalidErrLayer.getErrFeatureCollection());
										returnErrLayer.addAllDetailsValidatorReport(invalidErrLayer.getDetailsValidatorReport());
										returnErrLayer.addAllISOValidatorField(invalidErrLayer.getISOValidatorReport());
									}
								}
								ValidatorLayer validatorLayer = new ValidatorLayer("type", layerID, featureCollection, weight);
								layers.add(validatorLayer);
							}
						}
					}
				}
				// 구조화 검증
				// ValidatorOption Setting
				int j = 0;
				Iterator<String> layerIter2 = layerJSON.keySet().iterator();
				while (layerIter2.hasNext()) {
					String layerID = layerIter2.next();
					JSONObject layer = (JSONObject) layerJSON.get(layerID);
					JSONObject valiOption = (JSONObject) layer.get("qaOption");
					if (!valiOption.isEmpty()) {
						List<ValidatorOption> optionList = new ArrayList<ValidatorOption>();
						Iterator optionIter = valiOption.keySet().iterator();
						while (optionIter.hasNext()) {
							String valiOptionStr = (String) optionIter.next();
							if (valiOptionStr.equals(SmallArea.Type.SMALLAREA.errName())) {
								Object getValue = valiOption.get(SmallArea.Type.SMALLAREA.errName());
								if (getValue == null || getValue.toString().equals("")) {
									continue;
								} else {
									String stringValue = getValue.toString();
									double value = Double.parseDouble(stringValue);
									ValidatorOption smallArea = new SmallArea(value);
									optionList.add(smallArea);
								}
							} else if (valiOptionStr.equals(SmallLength.Type.SMALLLENGTH.errName())) {
								Object getValue = valiOption.get(SmallLength.Type.SMALLLENGTH.errName());
								if (getValue == null) {
									continue;
								} else {
									String stringValue = getValue.toString();
									double value = Double.parseDouble(stringValue);
									ValidatorOption smallLength = new SmallLength(value);
									optionList.add(smallLength);
								}
							} else if (valiOptionStr.equals(SelfEntity.Type.SELFENTITY.errName())) {
								Object selfEntityObj = valiOption.get("SelfEntity");
								if (selfEntityObj == null || selfEntityObj.equals("")) {
									continue;
								} else {
									JSONArray selfEntityValue = (JSONArray) selfEntityObj;
									List<ValidatorLayer> relationLayers = new ArrayList<ValidatorLayer>();
									int valueSize = selfEntityValue.size();
									int layerSize = layers.size();
									for (int i = 0; i < valueSize; i++) {
										String relationID = (String) selfEntityValue.get(i);
										for (int k = 0; k < layerSize; k++) {
											ValidatorLayer tempLayer = layers.get(k);
											if (tempLayer.getLayerID().equals(relationID)) {
												relationLayers.add(tempLayer);
											}
										}
									}
									ValidatorOption selfEntity = new SelfEntity(relationLayers);
									optionList.add(selfEntity);
								}
							} else if (valiOptionStr.equals(PointDuplicated.Type.POINTDUPLICATED.errName())) {
								ValidatorOption smallLength = new PointDuplicated();
								optionList.add(smallLength);
							} else if (valiOptionStr.equals(EntityDuplicated.Type.ENTITYDUPLICATED.errName())) {
								EntityDuplicated entityDuplicated = new EntityDuplicated();
								optionList.add(entityDuplicated);
							} else if (valiOptionStr.equals(ConOverDegree.Type.CONOVERDEGREE.errName())) {
								Object getValue = valiOption.get("ConOverDegree");
								if (getValue == null) {
									continue;
								} else {
									String stringValue = getValue.toString();
									double value = Double.parseDouble(stringValue);
									ValidatorOption conOverDegree = new ConOverDegree(value);
									optionList.add(conOverDegree);
								}
							} else if (valiOptionStr.equals(ConBreak.Type.CONBREAK.errName())) {
								ValidatorOption conBreak = new ConBreak();
								optionList.add(conBreak);
							} else if (valiOptionStr.equals(ConIntersected.Type.CONINTERSECTED.errName())) {
								ValidatorOption conIntersected = new ConIntersected();
								optionList.add(conIntersected);
							} else if (valiOptionStr.equals(AttributeFix.Type.ATTRIBUTEFIX.errName())) {
								Object attributeFixObj = valiOption.get("AttributeFix");
								if (attributeFixObj == null || attributeFixObj.equals("")) {
									continue;
								} else {
									JSONArray attributeFixValue = (JSONArray) attributeFixObj;
									ValidatorOption attributeFix = new AttributeFix(attributeFixValue);
									optionList.add(attributeFix);
								}
							} else if (valiOptionStr.equals(OutBoundary.Type.OUTBOUNDARY.errName())) {
								Object outBoundaryObj = valiOption.get("OutBoundary");
								if (outBoundaryObj == null) {
									continue;
								} else {
									JSONArray outBoundaryValue = (JSONArray) outBoundaryObj;
									List<ValidatorLayer> relationLayers = new ArrayList<ValidatorLayer>();
									int valueSize = outBoundaryValue.size();
									int layerSize = layers.size();
									for (int i = 0; i < valueSize; i++) {
										String relationID = (String) outBoundaryValue.get(i);
										for (int k = 0; k < layerSize; k++) {
											ValidatorLayer tempLayer = layers.get(k);
											if (tempLayer.getLayerID().equals(relationID)) {
												relationLayers.add(tempLayer);
											}
										}
									}
									ValidatorOption outBoundary = new OutBoundary(relationLayers);
									optionList.add(outBoundary);
								}
							} else if (valiOptionStr.equals(Z_ValueAmbiguous.Type.Z_VALUEAMBIGUOUS.errName())) {
								Object z_Value = valiOption.get("zValueAmbiguous");
								if (z_Value == null) {
									continue;
								} else {
									JSONArray z_ValueArray = (JSONArray) z_Value;
									ValidatorOption Z_ValueAmbiguous = new Z_ValueAmbiguous(z_ValueArray);
									optionList.add(Z_ValueAmbiguous);
								}
							} else if (valiOptionStr.equals(UselessPoint.Type.USELESSPOINT.errName())) {
								ValidatorOption uselessPoint = new UselessPoint();
								optionList.add(uselessPoint);
							}
						}
						layers.get(j).setValidatorOptions(optionList);
						j++;
					} else {
 						j++;
						continue;
					}
				}
				// 구조화 검증 에러레이어 추가
				ErrorLayer qaErrLayer = layerValidator.validateLayers(layers, aopFeatureCollection);
				if (qaErrLayer != null) {
					returnErrLayer.addErrorFeatureCollection(qaErrLayer.getErrFeatureCollection());
					returnErrLayer.addAllDetailsValidatorReport(qaErrLayer.getDetailsValidatorReport());
					returnErrLayer.addAllISOValidatorField(qaErrLayer.getISOValidatorReport());
				}
				/*
				 * DTGeoJSON dtGeoJSON = new DTGeoJSON(); JSONObject temp1 =
				 * dtGeoJSON.createGeoJSON(errFeatures); JSONObject temp2 =
				 * dataConvertor.convertToGeoJSON(returnErrLayer.getErrSfc());
				 */
				if (returnErrLayer.getErrFeatureCollection().size() != 0 && returnErrLayer.getDetailsValidatorReport().size() != 0) {
					JSONObject returnJSON = new JSONObject();
					returnJSON.put("ErrorLayer", dataConvertor.convertToGeoJSON(returnErrLayer.getErrFeatureCollection()));
					returnJSON.put("ISOReport", returnErrLayer.getISOValidatorReport());
					returnJSON.put("DetailsReport", returnErrLayer.getDetailsValidatorReport());
					returnJSON.put("Error", "true");
					return returnJSON;
				} else {
					JSONObject returnJSON = new JSONObject();
					returnJSON.put("ErrorLayer", null);
					returnJSON.put("ISOReport", returnErrLayer.getISOValidatorReport());
					returnJSON.put("DetailsReport", null);
					returnJSON.put("Error", false);
					return returnJSON;
				}
			}
		}
	}
}
