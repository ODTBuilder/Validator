package com.git.gdsbuilder.validator.feature;

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
}
