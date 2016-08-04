package com.git.opengds.service.builder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.SchemaException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.git.gdsbuilder.convertor.DataConvertor;
import com.git.gdsbuilder.type.layer.ErrorLayer;
import com.git.gdsbuilder.type.layer.ValidatorLayer;
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
import com.git.gdsbuilder.validator.layer.LayerValidator;
import com.git.gdsbuilder.validator.layer.LayerValidatorImpl;

@Service
public class ValidatorServiceImpl implements ValidatorService {

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject autoValidation(JSONObject layerJSON) throws SchemaException {

		DataConvertor dataConvertor = new DataConvertor();
		LayerValidator layerValidator = new LayerValidatorImpl();

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
	}
}
