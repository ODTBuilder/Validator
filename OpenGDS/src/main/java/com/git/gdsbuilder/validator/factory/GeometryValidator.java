package com.git.gdsbuilder.validator.factory;

import java.util.List;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.SchemaException;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.operation.TransformException;

import com.git.gdsbuilder.type.feature.ErrorFeature;

public interface GeometryValidator {

	/**
	 * 검수 항목 중 “허용 범위 이하 면적 (Small Area)” 검수를 수행하여 오류 정보를 포함한 ErrorFeature를
	 * 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.10
	 * @param simpleFeature
	 *            검수를 수행할 SimpleFeature
	 * @param defaultArea
	 *            입력 넓이
	 * @return ErrorFeature
	 * @throws SchemaException
	 */
	ErrorFeature smallArea(SimpleFeature simpleFeature, double defaultArea) throws SchemaException;

	/**
	 * 검수 항목 중 “허용 범위 이하 길이 (Small Length)” 검수를 수행하여 오류 정보를 포함한 ErrorFeature를
	 * 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.10
	 * @param simpleFeature
	 *            검수를 수행할 SimpleFeature
	 * @param defaultLength
	 *            입력 길이
	 * @return ErrorFeature
	 * @throws SchemaException
	 */
	ErrorFeature smallLength(SimpleFeature simpleFeature, double defaultLength) throws SchemaException;

	/**
	 * 검수 항목 중 “요소 중복 오류(EntityDuplicaated)” 검수를 수행하여 오류 정보를 포함한 ErrorFeature
	 * 리스트를 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.10
	 * @param simpleFeatureI
	 *            검수를 수행할 SimpleFeature(기준 SimpleFeature)
	 * @param simpleFeatureJ
	 *            검수를 수행할 SimpleFeature(비교 SimpleFeature)
	 * @return List<ErrorFeature>
	 * @throws SchemaException
	 */
	ErrorFeature entityDuplicated(SimpleFeature simpleFeatureI, SimpleFeature simpleFeatureJ) throws SchemaException;

	/**
	 * 검수 항목 중 “중복점오류(Duplicated Point)” 검수를 수행하여 오류 정보를 포함한 ErrorFeature 리스트를
	 * 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.10
	 * @param simpleFeatureI
	 *            검수를 수행할 SimpleFeature
	 * @return List<ErrorFeature>
	 * @throws SchemaException
	 */
	List<ErrorFeature> pointDuplicated(SimpleFeature simpleFeature) throws SchemaException;

	/**
	 * 검수 항목 중 “단독 존재 오류 (Self Entity)” 검수를 수행하여 오류 정보를 포함한 ErrorFeature 리스트를
	 * 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.10
	 * @param simpleFeatureI
	 *            검수를 수행할 SimpleFeature(기준 SimpleFeature)
	 * @param simpleFeatureJ
	 *            검수를 수행할 SimpleFeature(비교 SimpleFeature)
	 * @return List<ErrorFeature>
	 * @throws SchemaException
	 */
	List<ErrorFeature> selfEntity(SimpleFeature simpleFeatureI, SimpleFeature simpleFeatureJ) throws SchemaException;

	/**
	 * 검수 항목 중 “등고선 교차 오류(ConIntersected)” 검수를 수행하여 오류 정보를 포함한 ErrorFeature 리스트를
	 * 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.10
	 * @param simpleFeatureI
	 *            검수를 수행할 SimpleFeature(기준 SimpleFeature)
	 * @param simpleFeatureJ
	 *            검수를 수행할 SimpleFeature(비교 SimpleFeature)
	 * @return List<ErrorFeature>
	 * @throws SchemaException
	 */
	List<ErrorFeature> conIntersected(SimpleFeature simpleFeatureI, SimpleFeature simpleFeatureJ) throws SchemaException;

	/**
	 * 검수 항목 중 “등고선 꺾임 오류(ConOverDegree)” 검수를 수행하여 오류 정보를 포함한 ErrorFeature 리스트를
	 * 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.10
	 * @param simpleFeature
	 *            검수를 수행할 SimpleFeature
	 * @param doubledfdegree
	 *            입력 각도
	 * @return List<ErrorFeature>
	 * @throws SchemaException
	 */
	List<ErrorFeature> conOverDegree(SimpleFeature simpleFeature, double doubledfdegree) throws SchemaException;

	/**
	 * 검수 항목 중 “등고선 끊김 오류(ConBreak)” 검수를 수행하여 오류 정보를 포함한 ErrorFeature 리스트를 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.10
	 * @param simpleFeature
	 *            검수를 수행할 SimpleFeature
	 * @param aop
	 *            검수 영역
	 * @return List<ErrorFeature>
	 * @throws SchemaException
	 */
	List<ErrorFeature> conBreak(SimpleFeature simpleFeature, SimpleFeatureCollection aop) throws SchemaException;

	/**
	 * 검수 항목 중 “경계초과오류 (Out Boundary)” 검수를 수행하여 오류 정보를 포함한 ErrorFeature 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.10
	 * @param simpleFeatureI
	 *            검수를 수행할 단일 레이어(기준 레이어)
	 * @param simpleFeatureJ
	 *            검수를 수행할 단일 레이어(비교 레이어)
	 * @return ErrorFeature
	 * @throws SchemaException
	 */
	ErrorFeature outBoundary(SimpleFeature simpleFeatureI, SimpleFeature simpleFeatureJ) throws SchemaException;

	/**
	 * 검수 항목 중 “등고선 직선화 미처리 오류 (Useless Point)” 검수를 수행하여 오류 정보를 포함한 ErrorFeature
	 * 리스트를 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.10
	 * @param simpleFeature
	 *            검수를 수행할 SimpleFeature
	 * @return List<ErrorFeature>
	 * @throws SchemaException
	 */
	List<ErrorFeature> uselessPoint(SimpleFeature simpleFeature) throws SchemaException, NoSuchAuthorityCodeException, FactoryException, TransformException;

}
