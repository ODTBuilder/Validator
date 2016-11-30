package com.git.opengds.geoserver.service;

import java.net.MalformedURLException;

import com.git.gdsbuilder.geoserver.data.DTGeoserver;
import com.git.gdsbuilder.geoserver.data.DTGeoserverList;
import com.git.gdsbuilder.geosolutions.geoserver.rest.GeoServerRESTManager;


/**
 * Geoserver와 관련된 데이터를 처리한다.
 * @author SG.Lee
 * @Date 2016.08.30
 * */
public interface GeoserverService {

	/**
	 * 서버생성을 처리한다.
	 * @author SG.Lee
	 * @Date 2016.08.30
	 * @param name - 서버이름 
	 *        id - 서버id
	 *        pw - 서버pw
	 *        url -  서버url(Geoserver)
	 * @return DTGeoserver - 생선된 DTGeoserver 반환
	 * @throws
	 * */
	public DTGeoserver createDTGeoserver(String name, String id, String pw, String url) throws IllegalArgumentException, MalformedURLException;

	
	/**
	 * DTGeoserver를 가지고 GeoServerRESTManager 생성
	 * @author SG.Lee
	 * @Date 2016.08.30
	 * @param DTGeoserver
	 * @return 생성된 GeoServerRESTManager 객체
	 * @throws
	 * */
	public GeoServerRESTManager getGeoserverRestManager(DTGeoserver geoserver);
}
