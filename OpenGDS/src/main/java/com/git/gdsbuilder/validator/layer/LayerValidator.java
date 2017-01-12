package com.git.gdsbuilder.validator.layer;

import java.util.List;

import org.geotools.data.simple.SimpleFeatureCollection;

import com.git.gdsbuilder.type.layer.ErrorLayer;
import com.git.gdsbuilder.type.layer.ValidatorLayer;

/**
 * 다중 레이어의 검수를 수행한다.
 * 
 * @author dayeon.oh
 * @data 2016.10
 */
public interface LayerValidator {

	/**
	 * 다중 레이어의 검수를 수행하여 오류 정보를 포함한 ErrorLayer를 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.10
	 * @param qaLayers
	 *            검수를 수행할 레이어 리스트
	 * @param aop
	 *            검수 영역
	 * @return ErrorLayer
	 * @throws Exception
	 */
	public ErrorLayer validateLayers(List<ValidatorLayer> qaLayers, SimpleFeatureCollection aop) throws Exception;

}
