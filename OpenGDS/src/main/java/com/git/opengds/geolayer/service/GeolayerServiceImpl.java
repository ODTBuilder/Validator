 package com.git.opengds.geolayer.service;

import org.springframework.stereotype.Service;

import com.git.gdsbuilder.geolayer.data.DTGeoLayerList;
import com.git.gdsbuilder.geolayer.factory.DTGeoLayerPublisher;

@Service
public class GeolayerServiceImpl implements GeolayerService {

	public DTGeoLayerList getGeolayerList(DTGeoLayerPublisher publisher){
		DTGeoLayerList layerList = new DTGeoLayerList();
		layerList = publisher.getDTGeoLayerList();
		return layerList;
	}
}
