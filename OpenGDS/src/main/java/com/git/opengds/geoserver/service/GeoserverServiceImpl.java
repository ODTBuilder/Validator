package com.git.opengds.geoserver.service;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.stereotype.Service;

import com.git.gdsbuilder.geoserver.data.DTGeoserver;
import com.git.gdsbuilder.geoserver.data.DTGeoserverList;
import com.git.gdsbuilder.geosolutions.geoserver.rest.GeoServerRESTManager;

@Service
public class GeoserverServiceImpl implements GeoserverService {

	public DTGeoserver createDTGeoserver(String name, String id, String pw, String url) throws IllegalArgumentException, MalformedURLException{
		DTGeoserver geoserver = new DTGeoserver();
		return geoserver.build(name, url, id, pw);
	}
	 
	public GeoServerRESTManager getGeoserverRestManager(DTGeoserver geoserver){
		GeoServerRESTManager geoServerRESTManager = null;
		if(geoserver!=null){
			String name = geoserver.getServerName();
			String id = geoserver.getId();
			String pw = geoserver.getPw();
			String url = geoserver.getUrl();
			
			try {
				geoServerRESTManager = new GeoServerRESTManager(new URL(url), id, pw);
			} catch (IllegalArgumentException e) {
				geoServerRESTManager = null;
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				geoServerRESTManager = null;
			}
		}
		
		
		return geoServerRESTManager;
	}
}
