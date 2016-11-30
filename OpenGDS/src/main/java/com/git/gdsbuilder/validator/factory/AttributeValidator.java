package com.git.gdsbuilder.validator.factory;

import org.geotools.feature.SchemaException;
import org.json.simple.JSONArray;
import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.type.feature.ErrorFeature;

/**
 * 피처 단위(SimpleFeature)의 속성 검수를 수행하여
 * 
 * @author dayeon.oh
 * @data 2016.10
 */
public interface AttributeValidator {

	/**
	 * 검수 항목 중 “필수 속성 오류(Attribute Fix)” 검수를 수행하여 오류 정보를 포함한 ErrorFeature를 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.10
	 * @param simpleFeature
	 *            검수를 수행할 SimpleFeature
	 * @param notNullAtt
	 *            필수 속성의 key 값과 value Type을 담고 있는 JSONArray
	 * @return ErrorFeature
	 * @throws SchemaException
	 */
	ErrorFeature attributeFix(SimpleFeature simpleFeature, JSONArray notNullAtt) throws SchemaException;

	/**
	 * 검수 항목 중 “고도값 오류(Z_ValueAmbiguous)” 검수를 수행하여 오류 정보를 포함한 ErrorFeature를
	 * 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.10
	 * @param simpleFeature
	 *            검수를 수행할 SimpleFeature
	 * @param notNullAtt
	 *            등고선 고도값 속성의 key 값과 value Type을 담고 있는 JSONArray
	 * @return ErrorFeature
	 * @throws SchemaException
	 */
	ErrorFeature z_valueAmbiguous(SimpleFeature simpleFeature, JSONArray notNullAtt) throws SchemaException;

}
