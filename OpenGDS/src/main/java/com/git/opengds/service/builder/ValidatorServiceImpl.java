package com.git.opengds.service.builder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.geotools.data.simple.SimpleFeatureCollection;
<<<<<<< HEAD
=======
import org.geotools.feature.SchemaException;
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.git.gdsbuilder.convertor.DataConvertor;
<<<<<<< HEAD
import com.git.gdsbuilder.type.dataCheck.DataValidCheck;
=======
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
import com.git.gdsbuilder.type.layer.ErrorLayer;
import com.git.gdsbuilder.type.layer.ValidatorLayer;
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
import com.git.gdsbuilder.validator.json.DTGeoJSON;
=======
import com.git.gdsbuilder.type.validatorOption.ValidatorOption;
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
import com.git.gdsbuilder.validator.layer.LayerValidator;
import com.git.gdsbuilder.validator.layer.LayerValidatorImpl;

@Service
public class ValidatorServiceImpl implements ValidatorService {

	@SuppressWarnings("unchecked")
	@Override
<<<<<<< HEAD
	public JSONObject autoValidation(String stringJSON) throws Exception {
		
		System.gc();
=======
	public JSONObject autoValidation(JSONObject layerJSON) throws SchemaException {
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c

		DataConvertor dataConvertor = new DataConvertor();
		LayerValidator layerValidator = new LayerValidatorImpl();

<<<<<<< HEAD
		// String to JSONObject
		JSONObject jsonObject = dataConvertor.stringToJSON(stringJSON);

		// 검수 영역
		JSONObject extent = dataConvertor.stringToJSON(jsonObject.get("extent").toString());
		SimpleFeatureCollection aopFeatureCollection = dataConvertor.converToSimpleFeatureCollection(extent);

		List<ValidatorLayer> layers = new ArrayList<ValidatorLayer>();
		JSONArray errFeatures = new JSONArray();

		// 검수 레이어 생성 및 ValidData 검수
		ErrorLayer returnErrLayer = new ErrorLayer();

		// FeatureCollection Setting
		JSONObject layerJSON = (JSONObject) jsonObject.get("layers");
		ErrorLayer invalidErrLayer;
		Iterator<String> layerIter = layerJSON.keySet().iterator();
		
		System.gc();
		
		while (layerIter.hasNext()) {

			// Get Layer
			String layerID = layerIter.next();
			JSONObject layer = (JSONObject) layerJSON.get(layerID);
			// String stringGeo = String.valueOf(layer.get("feature"));
			JSONObject feature = (JSONObject) dataConvertor.stringToJSON(layer.get("feature").toString());

			// geoJSON Valid
			DTGeoJSON dtGeoJSON = new DTGeoJSON();
			dtGeoJSON.geoJsonValidate(feature);
			JSONObject trueJSON = dtGeoJSON.getTrueJSON();
			JSONObject falseJSON = dtGeoJSON.getFalseJSON();

			JSONObject errJSON = dtGeoJSON.createErrorJSON(falseJSON);
			errFeatures.addAll((JSONArray) errJSON.get("features"));

			// ISO Report 파라미터
			String weightStr = layer.get("weight").toString();
			double weight = Double.parseDouble(weightStr);

			// 속성오류 검수 유무
			boolean attValidator = false;
			JSONObject valiOption = (JSONObject) layer.get("qaOption");
			Iterator optionIter = valiOption.keySet().iterator();
			while (optionIter.hasNext()) {
				String valiOptionStr = (String) optionIter.next();
				if (valiOptionStr.equals("AttributeFix") || valiOptionStr.equals("zValueAmbiguous")) {
					attValidator = true;
				}
			}

			// convert geojson
			if (!attValidator) {
				// trueJSON -> SimpleFeatureCollection 변환

				SimpleFeatureCollection featureCollection = dataConvertor.converToSimpleFeatureCollection(trueJSON);

				// FeatureCollection Valid
				DataValidCheck dataValidCheck = new DataValidCheck();
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

				DataValidCheck dataValidCheck = new DataValidCheck();
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

		System.gc();
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
						String getValue = valiOption.get(SmallArea.Type.SMALLAREA.errName()).toString();
						double value = Double.parseDouble(getValue);
						ValidatorOption smallArea = new SmallArea(value);
						optionList.add(smallArea);
					} else if (valiOptionStr.equals(SmallLength.Type.SMALLLENGTH.errName())) {
						String getValue = valiOption.get(SmallLength.Type.SMALLLENGTH.errName()).toString();
						double value = Double.parseDouble(getValue);
						ValidatorOption smallLength = new SmallLength(value);
						optionList.add(smallLength);
					} else if (valiOptionStr.equals(SelfEntity.Type.SELFENTITY.errName())) {
						JSONArray selfEntityValue = (JSONArray) valiOption.get("SelfEntity");
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
					} else if (valiOptionStr.equals(PointDuplicated.Type.POINTDUPLICATED.errName())) {
						ValidatorOption smallLength = new PointDuplicated();
						optionList.add(smallLength);
					} else if (valiOptionStr.equals(EntityDuplicated.Type.ENTITYDUPLICATED.errName())) {
						EntityDuplicated entityDuplicated = new EntityDuplicated();
						optionList.add(entityDuplicated);
					} else if (valiOptionStr.equals(ConOverDegree.Type.CONOVERDEGREE.errName())) {
						String getValue = valiOption.get("ConOverDegree").toString();
						double value = Double.parseDouble(getValue);
						ValidatorOption conOverDegree = new ConOverDegree(value);
						optionList.add(conOverDegree);
					} else if (valiOptionStr.equals(ConBreak.Type.CONBREAK.errName())) {
						ValidatorOption conBreak = new ConBreak();
						optionList.add(conBreak);
					} else if (valiOptionStr.equals(ConIntersected.Type.CONINTERSECTED.errName())) {
						ValidatorOption conIntersected = new ConIntersected();
						optionList.add(conIntersected);
					} else if (valiOptionStr.equals(AttributeFix.Type.ATTRIBUTEFIX.errName())) {
						JSONArray attributeFixValue = (JSONArray) valiOption.get("AttributeFix");
						ValidatorOption attributeFix = new AttributeFix(attributeFixValue);
						optionList.add(attributeFix);
					} else if (valiOptionStr.equals(OutBoundary.Type.OUTBOUNDARY.errName())) {
						JSONArray outBoundaryValue = (JSONArray) valiOption.get("OutBoundary");
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
					} else if (valiOptionStr.equals(Z_ValueAmbiguous.Type.Z_VALUEAMBIGUOUS.errName())) {
						JSONArray z_Value = (JSONArray) valiOption.get("zValueAmbiguous");
						ValidatorOption Z_ValueAmbiguous = new Z_ValueAmbiguous(z_Value);
						optionList.add(Z_ValueAmbiguous);
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
		System.gc();
		// 구조화 검증 에러레이어 추가
		System.out.println("검수직전검수직전검수직전검수직전검수직전검수직전검수직전검수직전검수직전검수직전검수직전검수직전검수직전검수직전검수직전검수직전검수직전검수직전검수직전검수직전검수직전검수직전검수직전검수직전검수직전검수직전검수직전검수직전검수직전");
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
=======
		List<ValidatorLayer> layers = new ArrayList<ValidatorLayer>();
		JSONObject tmp = (JSONObject) layerJSON.get("layers");

		// FeatureCollection Setting
		Iterator<String> layerIter = tmp.keySet().iterator();
		while (layerIter.hasNext()) {
			String layerID = layerIter.next();
			JSONObject layer = (JSONObject) tmp.get(layerID);
			String stringGeo = String.valueOf(layer.get("feature"));
			JSONObject feature = (JSONObject) dataConvertor.stringToJSON(stringGeo);
			String weightStr = (String) layer.get("weight");
			double weight = Double.parseDouble(weightStr);
			try {
				SimpleFeatureCollection featureCollection = dataConvertor.converToSimpleFeatureCollection(feature);
				ValidatorLayer validatorLayer = new ValidatorLayer("type", layerID, featureCollection, weight);
				layers.add(validatorLayer);
			} catch (SchemaException e) {
				e.printStackTrace();
			}
		}

		// ValidatorOption Setting
		int j = 0;
		Iterator<String> layerIter2 = tmp.keySet().iterator();
		while (layerIter2.hasNext()) {
			String layerID = layerIter2.next();
			JSONObject layer = (JSONObject) tmp.get(layerID);
			JSONObject valiOption = (JSONObject) layer.get("qaOption");

			List<ValidatorOption> optionList = new ArrayList<ValidatorOption>();

			Iterator optionIter = valiOption.keySet().iterator();
			while (optionIter.hasNext()) {
				String valiOptionStr = (String) optionIter.next();

				if (valiOptionStr.equals(SmallArea.Type.SMALLAREA.errName())) {
					JSONObject smallAreaValue = (JSONObject) valiOption.get("SmallArea");
					String getValue = (String) smallAreaValue.get("value");
					double value = Double.parseDouble(getValue);

					ValidatorOption smallArea = new SmallArea(value);
					optionList.add(smallArea);

				} else if (valiOptionStr.equals(SmallLength.Type.SMALLLENGTH.errName())) {
					JSONObject smallLengthValue = (JSONObject) valiOption.get("SmallLength");
					String getValue = (String) smallLengthValue.get("value");
					double value = Double.parseDouble(getValue);

					ValidatorOption smallLength = new SmallLength(value);
					optionList.add(smallLength);

				} else if (valiOptionStr.equals(SelfEntity.Type.SELFENTITY.errName())) {
					JSONObject selfEntityValue = (JSONObject) valiOption.get("SelfEntity");
					JSONArray relationJSON = (JSONArray) selfEntityValue.get("relation");
					List<ValidatorLayer> relationLayers = new ArrayList<ValidatorLayer>();
					for (int i = 0; i < relationJSON.size(); i++) {
						String relationID = (String) relationJSON.get(i);
						for (int k = 0; k < layers.size(); k++) {
							ValidatorLayer tempLayer = layers.get(k);
							if (tempLayer.getLayerID().equals(relationID)) {
								relationLayers.add(tempLayer);
							}
						}
					}

					ValidatorOption selfEntity = new SelfEntity(relationLayers);
					optionList.add(selfEntity);

				} else if (valiOptionStr.equals(PointDuplicated.Type.POINTDUPLICATED.errName())) {
					ValidatorOption smallLength = new PointDuplicated();
					optionList.add(smallLength);

				} else if (valiOptionStr.equals(EntityDuplicated.Type.ENTITYDUPLICATED.errName())) {
					JSONObject entityDuplicatedValue = (JSONObject) valiOption.get("EntityDuplicated");

					JSONArray relationJSON = (JSONArray) entityDuplicatedValue.get("relation");
					List<ValidatorLayer> relationLayers = new ArrayList<ValidatorLayer>();
					for (int i = 0; i < relationJSON.size(); i++) {
						String relationID = (String) relationJSON.get(i);
						for (int k = 0; k < layers.size(); k++) {
							ValidatorLayer tempLayer = layers.get(k);
							if (tempLayer.getLayerID().equals(relationID)) {
								relationLayers.add(tempLayer);
							}
						}
					}

					ValidatorOption entityDuplicated = new EntityDuplicated(relationLayers);
					optionList.add(entityDuplicated);

				} else if (valiOptionStr.equals(ConOverDegree.Type.CONOVERDEGREE.errName())) {
					JSONObject conOverDegreeValue = (JSONObject) valiOption.get("ConOverDegree");
					String getValue = (String) conOverDegreeValue.get("value");
					double value = Double.parseDouble(getValue);

					ValidatorOption conOverDegree = new ConOverDegree(value);
					optionList.add(conOverDegree);

				} else if (valiOptionStr.equals(ConBreak.Type.CONBREAK.errName())) {
					ValidatorOption conBreak = new ConBreak();
					optionList.add(conBreak);

				} else if (valiOptionStr.equals(ConIntersected.Type.CONINTERSECTED.errName())) {
					JSONObject conIntersectedValue = (JSONObject) valiOption.get("ConIntersected");
					JSONArray relationJSON = (JSONArray) conIntersectedValue.get("relation");
					List<ValidatorLayer> relationLayers = new ArrayList<ValidatorLayer>();
					for (int i = 0; i < relationJSON.size(); i++) {
						String relationID = (String) relationJSON.get(i);
						for (int k = 0; k < layers.size(); k++) {
							ValidatorLayer tempLayer = layers.get(k);
							if (tempLayer.getLayerID().equals(relationID)) {
								relationLayers.add(tempLayer);
							}
						}
					}

					ValidatorOption conIntersected = new ConIntersected(relationLayers);
					optionList.add(conIntersected);

				} else if (valiOptionStr.equals(AttributeFix.Type.ATTRIBUTEFIX.errName())) {
					JSONObject attributeFixValue = (JSONObject) valiOption.get("AttributeFix");
					JSONObject attribute = (JSONObject) layer.get("attribute");
					String attributeType = (String) attribute.get("number");

					ValidatorOption attributeFix = new AttributeFix(attribute);
					optionList.add(attributeFix);
				}
			}
			layers.get(j).setValidatorOptions(optionList);
			j++;
		}
		ErrorLayer errorLayer = layerValidator.validateLayers(layers);
		System.out.println(dataConvertor.convertToGeoJSON(errorLayer.getErrSfc()));
		return null;
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
	}
}
