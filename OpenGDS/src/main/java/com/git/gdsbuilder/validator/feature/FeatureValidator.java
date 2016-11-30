package com.git.gdsbuilder.validator.feature;

<<<<<<< HEAD
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.SchemaException;
import org.json.simple.JSONArray;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.operation.TransformException;

import com.git.gdsbuilder.type.layer.ErrorLayer;

/**
 * 검수 항목에 따라 단일 또는 다중 레이어(SimpleFeatureCollection)의 검수를 수행한다.
 * @author dayeon.oh
 * @data 2016.10
 */
public interface FeatureValidator {

	/**
	 * 검수 항목 중 “필수 속성 오류(Attribute Fix)” 검수를 수행하여 오류 정보를 포함한 ErrorLayer를 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.10
	 * @param validatorLayer
	 *            검수를 수행할 단일 레이어
	 * @param notNullAtt
	 *            필수 속성의 key 값과 value Type을 담고 있는 JSONArray
	 * @return ErrorLayer
	 * @throws SchemaException
	 */
	public ErrorLayer validateAttributeFix(SimpleFeatureCollection validatorLayer, JSONArray notNullAtt) throws SchemaException;

	/**
	 * 검수 항목 중 “등고선 교차 오류(ConIntersected)” 검수를 수행하여 오류 정보를 포함한 ErrorLayer를 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.10
	 * @param validatorLayer
	 *            검수를 수행할 단일 레이어
	 * @return ErrorLayer
	 * @throws SchemaException
	 */
	public ErrorLayer validateConIntersected(SimpleFeatureCollection validatorLayer) throws SchemaException;

	/**
	 * 검수 항목 중 “등고선 꺾임 오류(ConOverDegree)” 검수를 수행하여 오류 정보를 포함한 ErrorLayer를 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.10
	 * @param validatorLayer
	 *            검수를 수행할 단일 레이어
	 * @param inputDegree
	 *            입력 각도
	 * @return ErrorLayer
	 * @throws SchemaException
	 */
	public ErrorLayer validateConOverDegree(SimpleFeatureCollection validatorLayer, double inputDegree) throws SchemaException;

	/**
	 * 검수 항목 중 “요소 중복 오류(EntityDuplicaated)” 검수를 수행하여 오류 정보를 포함한 ErrorLayer를 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.10
	 * @param validatorLayer
	 *            검수를 수행할 단일 레이어
	 * @return ErrorLayer
	 * @throws SchemaException
	 */
	public ErrorLayer validateEntityDuplicated(SimpleFeatureCollection validatorLayer) throws SchemaException;

	/**
	 * 검수 항목 중 “중복점오류(Duplicated Point)” 검수를 수행하여 오류 정보를 포함한 ErrorLayer를 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.10
	 * @param validatorLayer
	 *            검수를 수행할 단일 레이어
	 * @return ErrorLayer
	 * @throws SchemaException
	 */
	public ErrorLayer validatePointDuplicated(SimpleFeatureCollection validatorLayer) throws SchemaException;

	/**
	 * 검수 항목 중 “단독 존재 오류 (Self Entity)” 검수를 수행하여 오류 정보를 포함한 ErrorLayer를 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.10
	 * @param validatorLayer
	 *            검수를 수행할 단일 레이어
	 * @return ErrorLayer
	 * @throws SchemaException
	 */
	public ErrorLayer validateSelfEntity(SimpleFeatureCollection validatorLayer) throws SchemaException;

	/**
	 * 검수 항목 중 두 레이어의 “단독 존재 오류 (Self Entity)” 검수를 수행하여 오류 정보를 포함한 ErrorLayer를 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.10
	 * @param validatorLayer
	 *            검수를 수행할 단일 레이어(기준 레이어)
	 * @param relationLayer
	 *            검수를 수행할 단일 레이어(비교 레이어)
	 * @return ErrorLayer
	 * @throws SchemaException
	 */
	public ErrorLayer validateSelfEntity(SimpleFeatureCollection validatorLayer, SimpleFeatureCollection relationLayer) throws SchemaException;

	/**
	 * 검수 항목 중 “허용 범위 이하 면적 (Small Area)” 검수 결과를 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.10
	 * @param validatorLayer
	 *            검수를 수행할 단일 레이어
	 * @param inputArea
	 *            입력 면적
	 * @return ErrorLayer
	 * @throws SchemaException
	 */
	public ErrorLayer validateSmallArea(SimpleFeatureCollection validatorLayer, double inputArea) throws SchemaException;

	/**
	 * 검수 항목 중 “허용 범위 이하 길이 (Small Length)” 검수 결과를 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.10
	 * @param validatorLayer
	 *            검수를 수행할 단일 레이어
	 * @param inputArea
	 *            입력 길이
	 * @return ErrorLayer
	 * @throws SchemaException
	 */
	public ErrorLayer validateSmallLength(SimpleFeatureCollection validatorLayer, double inputLength) throws SchemaException;

	/**
	 * 검수 항목 중 “등고선 끊김 오류(ConBreak)” 검수 결과를 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.10
	 * @param validatorLayer
	 *            검수를 수행할 단일 레이어
	 * @param aop
	 *            검수 영역
	 * @return ErrorLayer
	 * @throws SchemaException
	 */
	public ErrorLayer validateConBreak(SimpleFeatureCollection validatorLayer, SimpleFeatureCollection aop) throws SchemaException;

	/**
	 * 검수 항목 중 “등고선 직선화 미처리 오류 (Useless Point)” 검수 결과를 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.10
	 * @param validatorLayer
	 *            검수를 수행할 단일 레이어
	 * @param aop
	 *            검수 영역
	 * @return ErrorLayer
	 * @throws SchemaException
	 */
	public ErrorLayer validateUselessPoint(SimpleFeatureCollection validatorLayer) throws SchemaException, NoSuchAuthorityCodeException, FactoryException,
			TransformException;

	/**
	 * 검수 항목 중 두 레이어의 “경계초과오류 (Out Boundary)” 검수 결과를 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.10
	 * @param validatorLayer
	 *            검수를 수행할 단일 레이어(기준 레이어)
	 * @param relationLayer
	 *            검수를 수행할 단일 레이어(비교 레이어)
	 * @return ErrorLayer
	 * @throws SchemaException
	 */
	public ErrorLayer validateOutBoundary(SimpleFeatureCollection validatorLayer, SimpleFeatureCollection relationLayer) throws SchemaException;

	/**
	 * 검수 항목 중 “고도값 오류 (Z-Value Abmiguous)” 검수 결과를 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.10
	 * @param validatorLayer
	 *            검수를 수행할 단일 레이어
	 * @param notNullAtt
	 *            등고선 고도값 속성의 key 값과 value Type을 담고 있는 JSONArray
	 * @return ErrorLayer
	 * @throws SchemaException
	 */
	public ErrorLayer validateZvalueAmbiguous(SimpleFeatureCollection validatorLayer, JSONArray notNullAtt) throws SchemaException;

=======
import java.util.Map;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.SchemaException;

/**
 * SimpleFeature의 검수를 수행한다.
 * 
 * @author dayeon.oh
 * @data 2016.02
 */
public interface FeatureValidator {

	public SimpleFeatureCollection validateAttributeFix(SimpleFeatureCollection validatorLayer) throws SchemaException;

	public Map<String, Object> validateConBreak(SimpleFeatureCollection validatorLayer) throws SchemaException;

	public Map<String, Object> validateConIntersected(SimpleFeatureCollection validatorLayer) throws SchemaException;

	public Map<String, Object> validateConIntersected(SimpleFeatureCollection validatorLayer, SimpleFeatureCollection relationLayer) throws SchemaException;

	public Map<String, Object> validateConOverDegree(SimpleFeatureCollection validatorLayer, double inputDegree) throws SchemaException;

	public Map<String, Object> validateEntityDuplicated(SimpleFeatureCollection validatorLayer) throws SchemaException;

	public Map<String, Object> validateEntityDuplicated(SimpleFeatureCollection validatorLayer, SimpleFeatureCollection relationLayer) throws SchemaException;

	public Map<String, Object> validatePointDuplicated(SimpleFeatureCollection validatorLayer) throws SchemaException;

	public SimpleFeatureCollection validateSelfEntity(SimpleFeatureCollection validatorLayer) throws SchemaException;

	public SimpleFeatureCollection validateSelfEntity(SimpleFeatureCollection validatorLayer, SimpleFeatureCollection relationLayer) throws SchemaException;

	public Map<String, Object> validateSmallArea(SimpleFeatureCollection validatorLayer, double inputArea) throws SchemaException;

	public Map<String, Object> validateSmallLength(SimpleFeatureCollection validatorLayer, double inputLength) throws SchemaException;
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
}
