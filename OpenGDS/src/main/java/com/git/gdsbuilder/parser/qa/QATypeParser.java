/*
 *    OpenGDS/Builder
 *    http://git.co.kr
 *
 *    (C) 2014-2017, GeoSpatial Information Technology(GIT)
 *    
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 3 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package com.git.gdsbuilder.parser.qa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.git.gdsbuilder.type.validate.layer.QALayerType;
import com.git.gdsbuilder.type.validate.layer.QALayerTypeList;
import com.git.gdsbuilder.type.validate.option.AttributeFigure;
import com.git.gdsbuilder.type.validate.option.AttributeFilter;
import com.git.gdsbuilder.type.validate.option.AttributeMiss;
import com.git.gdsbuilder.type.validate.option.CloseMiss;
import com.git.gdsbuilder.type.validate.option.FixedValue;
import com.git.gdsbuilder.type.validate.option.GraphicMiss;
import com.git.gdsbuilder.type.validate.option.LayerFixMiss;
import com.git.gdsbuilder.type.validate.option.OptionFigure;
import com.git.gdsbuilder.type.validate.option.OptionFilter;
import com.git.gdsbuilder.type.validate.option.OptionRelation;
import com.git.gdsbuilder.type.validate.option.OptionTolerance;
import com.git.gdsbuilder.type.validate.option.QAOption;

/**
 * JSONArray 형태의 검수 옵션(검수 항목 및 검수 항목별 세부 설정)정보를
 * {@link com.git.gdsbuilder.parser.file.writer.SHPFileWriter} 형태로 변환하는 클래스
 * 
 * @author DY.Oh
 */
public class QATypeParser {

	/**
	 * 검수 옵션(검수 항목 및 검수 항목별 세부 설정)정보
	 */
	JSONArray validateTypeArray;
	/**
	 * {@link com.git.gdsbuilder.parser.file.writer.SHPFileWriter}형태의 검수 옵션 정보
	 */
	QALayerTypeList validateLayerTypeList;
	/**
	 * 변환 시 발생하는 오류에 대한 설명
	 */
	String comment;

	/**
	 * JSONArray 형태의 검수 옵션(검수 항목 및 검수 항목별 세부 설정)정보를
	 * {@link com.git.gdsbuilder.parser.file.writer.SHPFileWriter} 형태로 변환을 위한 생성자
	 * 
	 * @param validateTypeArray 검수 옵션(검수 항목 및 검수 항목별 세부 설정)정보
	 */
	public QATypeParser(JSONArray validateTypeArray) {
		this.validateTypeArray = validateTypeArray;
		typeListParser();
	}

	public QALayerTypeList getValidateLayerTypeList() {
		return validateLayerTypeList;
	}

	public void setValidateLayerTypeList(QALayerTypeList validateLayerTypeList) {
		this.validateLayerTypeList = validateLayerTypeList;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * JSONArray 형태의 검수 옵션(검수 항목 및 검수 항목별 세부 설정)정보를
	 * {@link com.git.gdsbuilder.parser.file.writer.SHPFileWriter} 형태로 변환
	 * 
	 * @author DY.Oh
	 */
	public void typeListParser() {

		if (validateTypeArray == null || validateTypeArray.size() == 0) {
			this.comment += "옵션 미존재" + "<br>";
			validateLayerTypeList = null;
		}
		if (validateTypeArray.size() != 0) {
			try {
				this.validateLayerTypeList = new QALayerTypeList();
				for (int j = 0; j < validateTypeArray.size(); j++) {
					JSONObject layerType = (JSONObject) validateTypeArray.get(j);
					QALayerType type = typeOptionParserT(layerType);
					validateLayerTypeList.add(type);
					validateLayerTypeList.addAllLayerIdList(type.getLayerIDList());
				}
				if (validateLayerTypeList.size() < 0) {
					this.comment += "옵션 미존재" + "<br>";
				}
			} catch (Exception e) {
				this.comment += "옵션 설정 오류" + "<br>";
				validateLayerTypeList = null;
			}
		}
	}

	private QALayerType typeOptionParserT(JSONObject layerType) {

		QALayerType type = new QALayerType();
		QAOption qaOption = new QAOption();

		// name
		String name = (String) layerType.get("name");
		type.setName(name);
		qaOption.setName(name);

		// layers
		JSONArray typeLayers = (JSONArray) layerType.get("layers");
		Map<String, Object> layerFixMap = parseLayerFix(typeLayers);
		qaOption.setLayerMissOptions((List<LayerFixMiss>) layerFixMap.get("layerFix"));
		type.setLayerIDList((List<String>) layerFixMap.get("layerCodes"));

		// option
		Object optionsObj = layerType.get("options");
		if (optionsObj != null) {
			JSONObject option = (JSONObject) optionsObj;
			Object attrOption = option.get("attribute");
			if (attrOption != null) {
				qaOption.setAttributeMissOptions(parseAttributeOption((JSONObject) attrOption));
			} else {
				qaOption.setAttributeMissOptions(null);
			}
			Object grapOption = option.get("graphic");
			if (grapOption != null) {
				qaOption.setGraphicMissOptions(parseGraphicOption((JSONObject) grapOption));
			} else {
				qaOption.setGraphicMissOptions(null);
			}
			Object closeOption = option.get("adjacent");
			if (closeOption != null) {
				qaOption.setCloseMissOptions(parseCloseOption((JSONObject) closeOption));
			} else {
				qaOption.setCloseMissOptions(null);
			}
		} else {
			qaOption.setAttributeMissOptions(null);
			qaOption.setGraphicMissOptions(null);
			qaOption.setCloseMissOptions(null);
		}
		type.setOption(qaOption);
		return type;
	}

	private List<CloseMiss> parseCloseOption(JSONObject closeOption) {

		List<CloseMiss> closeMisses = new ArrayList<>();
		Iterator optionIter = closeOption.keySet().iterator();
		while (optionIter.hasNext()) {
			CloseMiss closeMiss = new CloseMiss();
			String optionName = (String) optionIter.next();
			closeMiss.setOption(optionName);
			JSONObject optionValue = (JSONObject) closeOption.get(optionName);
			Object filterObj = optionValue.get("filter");
			Object relationObj = optionValue.get("relation");
			Object toleranceObj = optionValue.get("tolerance");
			Object figureObj = optionValue.get("figure");
			// filter
			if (filterObj == null) {
				closeMiss.setFilter(null);
			} else {
				JSONArray filter = (JSONArray) filterObj;
				List<OptionFilter> optionConditions = parseFilter(filter);
				closeMiss.setFilter(optionConditions);
			}
			// relation
			if (relationObj == null) {
				closeMiss.setRetaion(null);
			} else {
				JSONArray relation = (JSONArray) relationObj;
				List<OptionRelation> optionRelations = parseRelation(relation);
				closeMiss.setRetaion(optionRelations);
			}
			// tolerance
			if (toleranceObj == null) {
				closeMiss.setTolerance(null);
			} else {
				JSONArray tolerances = (JSONArray) toleranceObj;
				List<OptionTolerance> optionsTolerances = parseTolerance(tolerances);
				closeMiss.setTolerance(optionsTolerances);
			}
			// figure
			if (figureObj == null) {
				closeMiss.setFigure(null);
			} else {
				JSONArray figures = (JSONArray) figureObj;
				List<OptionFigure> optionFigureList = parseFigure(figures);
				closeMiss.setFigure(optionFigureList);
			}
			closeMisses.add(closeMiss);
		}
		return closeMisses;
	}

	private List<GraphicMiss> parseGraphicOption(JSONObject grapOption) {

		List<GraphicMiss> graphicMisses = new ArrayList<>();

		Iterator optionIter = grapOption.keySet().iterator();
		while (optionIter.hasNext()) {
			GraphicMiss graphicMiss = new GraphicMiss();
			String optionName = (String) optionIter.next();
			graphicMiss.setOption(optionName);
			JSONObject optionValue = (JSONObject) grapOption.get(optionName);
			Object filterObj = optionValue.get("filter");
			Object figureObj = optionValue.get("figure");
			Object relationObj = optionValue.get("relation");
			Object toleranceObj = optionValue.get("tolerance"); // 그래픽검수
			// filter
			if (filterObj == null) {
				graphicMiss.setFilter(null);
			} else {
				JSONArray filter = (JSONArray) filterObj;
				List<OptionFilter> optionConditions = parseFilter(filter);
				graphicMiss.setFilter(optionConditions);
			}
			// figure
			if (figureObj == null) {
				graphicMiss.setFigure(null);
			} else {
				JSONArray figure = (JSONArray) figureObj;
				List<OptionFigure> optionConditions = parseFigure(figure);
				graphicMiss.setFigure(optionConditions);
			}

			// relation
			if (relationObj == null) {
				graphicMiss.setRetaion(null);
			} else {
				JSONArray relation = (JSONArray) relationObj;
				List<OptionRelation> optionRelations = parseRelation(relation);
				graphicMiss.setRetaion(optionRelations);
			}
			// tolerance
			if (toleranceObj == null) {
				graphicMiss.setTolerance(null);
			} else {
				JSONArray tolerances = (JSONArray) toleranceObj;
				List<OptionTolerance> optionsTolerances = parseTolerance(tolerances);
				graphicMiss.setTolerance(optionsTolerances);
			}
			graphicMisses.add(graphicMiss);
		}
		return graphicMisses;
	}

	private List<AttributeMiss> parseAttributeOption(JSONObject attrOption) {

		List<AttributeMiss> attributeMisses = new ArrayList<>();

		Iterator optionIter = attrOption.keySet().iterator();
		while (optionIter.hasNext()) {
			AttributeMiss attributeMiss = new AttributeMiss();
			String optionName = (String) optionIter.next();
			attributeMiss.setOption(optionName);
			JSONObject optionValue = (JSONObject) attrOption.get(optionName);
			Object filterObj = optionValue.get("filter");
			Object relationObj = optionValue.get("relation");
			Object figureObj = optionValue.get("figure"); // 속성검수
			Object toleranceObj = optionValue.get("tolerance");

			// filter
			if (filterObj == null) {
				attributeMiss.setFilter(null);
			} else {
				JSONArray filter = (JSONArray) filterObj;
				List<OptionFilter> optionConditions = parseFilter(filter);
				attributeMiss.setFilter(optionConditions);
			}
			// relation
			if (relationObj == null) {
				attributeMiss.setRetaion(null);
			} else {
				JSONArray relation = (JSONArray) relationObj;
				List<OptionRelation> optionRelations = parseRelation(relation);
				attributeMiss.setRetaion(optionRelations);
			}
			// figure
			if (figureObj == null) {
				attributeMiss.setFigure(null);
			} else {
				JSONArray figures = (JSONArray) figureObj;
				List<OptionFigure> optionFigureList = parseFigure(figures);
				attributeMiss.setFigure(optionFigureList);
			}
			// tolerance
			if (toleranceObj == null) {
				attributeMiss.setTolerance(null);
			} else {
				JSONArray tolerances = (JSONArray) toleranceObj;
				List<OptionTolerance> optionsTolerances = parseTolerance(tolerances);
				attributeMiss.setTolerance(optionsTolerances);
			}
			attributeMisses.add(attributeMiss);
		}
		return attributeMisses;
	}

	private List<OptionTolerance> parseTolerance(JSONArray tolerances) {

		List<OptionTolerance> optionsTolerances = new ArrayList<>();
		for (int j = 0; j < tolerances.size(); j++) {
			OptionTolerance optionToleracne = new OptionTolerance();
			JSONObject tolerance = (JSONObject) tolerances.get(j);
			String code = (String) tolerance.get("code");
			optionToleracne.setLayerID(code);

			Double value;
			if (tolerance.get("value") == null) {
				value = null;
			} else {
				value = Double.valueOf(tolerance.get("value").toString());
			}
			optionToleracne.setValue(value);
			String condition = (String) tolerance.get("condition");
			optionToleracne.setCondition(condition);
			Object intervalObj = tolerance.get("interval");
			if (intervalObj == null) {
				// null
				optionToleracne.setInterval(null);
			} else {
				Double interval = Double.valueOf(intervalObj.toString());
				optionToleracne.setInterval(interval);
			}
			optionsTolerances.add(optionToleracne);
		}
		return optionsTolerances;
	}

	private List<OptionRelation> parseRelation(JSONArray relation) {

		List<OptionRelation> optionRelations = new ArrayList<>();
		for (int i = 0; i < relation.size(); i++) {
			OptionRelation optionRelation = new OptionRelation();
			JSONObject relationJson = (JSONObject) relation.get(i);
			String name = (String) relationJson.get("name");
			optionRelation.setName(name);
			Object relationFilterObj = relationJson.get("filter");
			Object relationFigureObj = relationJson.get("figure");
			Object relationToleranceObj = relationJson.get("tolerance");
			// relationFilter
			if (relationFilterObj != null) {
				JSONArray filter = (JSONArray) relationFilterObj;
				List<OptionFilter> optionConditions = parseFilter(filter);
				optionRelation.setFilters(optionConditions);
			} else {
				optionRelation.setFilters(null);
			}
			// relationFigure
			if (relationFigureObj != null) {
				JSONArray relationFigures = (JSONArray) relationFigureObj;
				List<OptionFigure> optionFigureList = parseFigure(relationFigures);
				optionRelation.setFigures(optionFigureList);
			} else {
				optionRelation.setFigures(null);
			}
			// relationTolerance
			if (relationToleranceObj != null) {
				JSONArray relationTolerance = (JSONArray) relationToleranceObj;
				List<OptionTolerance> optionToleraceList = parseTolerance(relationTolerance);
				optionRelation.setTolerances(optionToleraceList);
			} else {
				optionRelation.setTolerances(null);
			}
			optionRelations.add(optionRelation);
		}
		return optionRelations;

	}

	private List<OptionFilter> parseFilter(JSONArray filter) {

		List<OptionFilter> optionConditions = new ArrayList<>();
		for (int i = 0; i < filter.size(); i++) {
			JSONObject attributeJson = (JSONObject) filter.get(i);
			OptionFilter optionConditon = new OptionFilter();
			// code
			String code = (String) attributeJson.get("code");
			optionConditon.setLayerID(code);
			// attribute
			Object attrObj = attributeJson.get("attribute");
			if (attrObj == null) {
				// null
				optionConditon.setFilter(null);
			} else {
				JSONArray attribute = (JSONArray) attrObj;
				List<AttributeFilter> attributeConditions = parseAttribute(attribute);
				optionConditon.setFilter(attributeConditions);
			}
			optionConditions.add(optionConditon);
		}
		return optionConditions;
	}

	private List<AttributeFilter> parseAttribute(JSONArray attribute) {

		List<AttributeFilter> filters = new ArrayList<>();
		for (int j = 0; j < attribute.size(); j++) {
			JSONObject attrJson = (JSONObject) attribute.get(j);
			AttributeFilter filter = new AttributeFilter();
			// key
			String key = (String) attrJson.get("key");
			filter.setKey(key);
			// values
			Object valuesObj = attrJson.get("values");
			if (valuesObj == null) {
				// null
				filter.setValues(null);
			} else {
				filter.setValues((List<Object>) attrJson.get("values"));
			}
			filters.add(filter);
		}
		return filters;
	}

	private List<OptionFigure> parseFigure(JSONArray figures) {

		List<OptionFigure> optionFigureList = new ArrayList<>();

		for (int f = 0; f < figures.size(); f++) {
			OptionFigure optionFigure = new OptionFigure();
			JSONObject figure = (JSONObject) figures.get(f);
			// code
			String code = (String) figure.get("code");
			optionFigure.setLayerID(code);
			// attribute
			List<AttributeFigure> attributeConditions = new ArrayList<>();
			Object attrObj = figure.get("attribute");
			if (attrObj == null) {
				// null
				optionFigure.setFigure(null);
			} else {
				JSONArray attribute = (JSONArray) attrObj;
				for (int a = 0; a < attribute.size(); a++) {
					JSONObject attrJson = (JSONObject) attribute.get(a);
					AttributeFigure attributeCondition = new AttributeFigure();
					// filter index
					Object fIdxObj = attrJson.get("fidx");
					if (fIdxObj != null) {
						Long fIdx = (Long) fIdxObj;
						attributeCondition.setFIdx(fIdx);
					} else {
						attributeCondition.setFIdx(null);
					}
					// key
					String key = (String) attrJson.get("key");
					Object valuesObj = attrJson.get("values");
					if (valuesObj != null) {
						List<Object> values = (List<Object>) valuesObj;
						attributeCondition.setValues(values);
					} else {
						attributeCondition.setValues(null);
					}
					Object numberObj = attrJson.get("number");
					if (numberObj != null) {
						Double number = Double.valueOf(numberObj.toString());
						attributeCondition.setNumber(number);
						Object conditionObj = attrJson.get("condition");
						if (conditionObj != null) {
							attributeCondition.setCondition(conditionObj.toString());
						} else {
							attributeCondition.setCondition(null);
						}
						Object intervalObj = attrJson.get("interval");
						if (intervalObj != null) {
							attributeCondition.setInterval(Double.valueOf(intervalObj.toString()));
						} else {
							attributeCondition.setInterval(null);
						}
					} else {
						attributeCondition.setNumber(null);
					}
					attributeCondition.setKey(key);
					attributeConditions.add(attributeCondition);
				}
				optionFigure.setFigure(attributeConditions);
			}
			optionFigureList.add(optionFigure);
		}
		return optionFigureList;
	}

	private Map<String, Object> parseLayerFix(JSONArray typeLayers) {

		try {
			Map<String, Object> returnMap = new HashMap<>();

			List<String> layerIDList = new ArrayList<>();
			List<LayerFixMiss> layerFixList = new ArrayList<>();
			for (int i = 0; i < typeLayers.size(); i++) {
				JSONObject layer = (JSONObject) typeLayers.get(i);
				String code = (String) layer.get("code");
				layerIDList.add(code);

				LayerFixMiss fix = new LayerFixMiss();
				fix.setOption("LayerFixMiss");
				fix.setLayerID(code);
				fix.setGeometry((String) layer.get("geometry"));

				// attrFix
				Object fixObj = layer.get("fix");
				if (fixObj != null) {
					List<FixedValue> fixedValues = new ArrayList<>();
					JSONArray fixArr = (JSONArray) layer.get("fix");
					for (int j = 0; j < fixArr.size(); j++) {
						FixedValue fixedValue = new FixedValue();
						JSONObject fixJson = (JSONObject) fixArr.get(j);
						fixedValue.setKey((String) fixJson.get("name"));
						fixedValue.setType((String) fixJson.get("type"));
						fixedValue.setIsnull((Boolean) fixJson.get("isnull"));
						fixedValue.setLength((Long) fixJson.get("length"));

						Object valueObj = fixJson.get("values");
						if (valueObj != null) {
							List<Object> values = new ArrayList<>();
							JSONArray valueArr = (JSONArray) valueObj;
							for (int v = 0; v < valueArr.size(); v++) {
								values.add(valueArr.get(v));
							}
							fixedValue.setValues(values);
							fixedValues.add(fixedValue);
						} else {
							fixedValues.add(fixedValue);
						}
					}
					fix.setFix(fixedValues);
				}
				layerFixList.add(fix);
			}
			returnMap.put("layerFix", layerFixList);
			returnMap.put("layerCodes", layerIDList);
			return returnMap;
		} catch (Exception e) {
			return null;
		}
	}
}
