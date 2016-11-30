package com.git.opengds.geolayer.service;

import com.git.gdsbuilder.geolayer.data.DTGeoLayerList;
import com.git.gdsbuilder.geolayer.factory.DTGeoLayerPublisher;


/**
 * Geolayer와 관련된 요청을 처리한다.
 * @author SG.Lee
 * @Date 2016.08.16
 * */
public interface GeolayerService {

	/**
	 * 레이어 리스트를 
	 * @author SG.Lee
	 * @Date 2016.08.16
	 * @param publisher - DTGeoLayerPulisher
	 * @return DTGeoLayerList - 레이어 리스트
	 * @throws
	 * */
	public DTGeoLayerList getGeolayerList(DTGeoLayerPublisher publisher) throws IllegalArgumentException;
	
}
