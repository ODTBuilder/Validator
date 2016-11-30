package com.git.gdsbuilder.geolayer.factory;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.gdsbuilder.geolayer.data.DTGeoLayer;
import com.git.gdsbuilder.geolayer.data.DTGeoLayerList;
import com.git.gdsbuilder.geoserver.factory.DTGeoserverPublisher;
import com.git.gdsbuilder.geosolutions.geoserver.rest.GeoServerRESTManager;
import com.git.gdsbuilder.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import com.git.gdsbuilder.geosolutions.geoserver.rest.GeoServerRESTReader;
import com.git.gdsbuilder.geosolutions.geoserver.rest.decoder.RESTLayerList;

/**
 * DTGeoLayer 데이터 처리를 위한 Publisher 생성
 * @author SG.Lee
 * @Date 2016.08.17
 * */
public class DTGeoLayerPublisher {


	 private static final Logger LOGGER = LoggerFactory.getLogger(DTGeoserverPublisher.class);

	    private GeoServerRESTReader reader; // Geosolutions - GeoServerRESTReader
	    private GeoServerRESTPublisher publisher; // Geosolutions - GeoServerRESTPublisher
	    private GeoServerRESTManager manager; // Geosolutions - GeoServerRESTManager
	    

	    /** 생성자
	     * @param manager - GeoServerRESTManager 
	     */
	    public DTGeoLayerPublisher(GeoServerRESTManager manager){
	    	if(manager!=null){
	    		this.manager = manager;
	    		this.reader = manager.getReader();
	    		this.publisher = manager.getPublisher();
	    	}
	    }
	    
	    
		/**
		 * GeoServerRESTManager 통해 존재하는 레이어 정보를 가져온다. - Geoserver 통신
		 * @author SG.Lee
		 * @Date 2016.08.18
		 * @param
		 * @return DTGeoLayerList - 레이어 리스트
		 * @throws
		 * */
		public DTGeoLayerList getDTGeoLayerList() {
			boolean flag = reader.existGeoserver(); //서버 존재여부
			DTGeoLayerList layerList = new DTGeoLayerList();
			if (flag) { //서버가 존재할때
				RESTLayerList list = reader.getLayers(); //해당 서버에서 레이어 가져옴

				List<String> layerNames = new ArrayList<String>();

				layerNames = list.getNames(); //레이어 이름 리스트 가져옴

				if (layerNames.size() == 0)
					layerList = null;
				else {
					for (int i = 0; i < layerNames.size(); i++) {
						DTGeoLayer layer = new DTGeoLayer(manager,layerNames.get(i)); //DTGeoLayer 객체 생성
						layerList.add(layer);
					}
				}
			}  else
				layerList = null;

			return layerList;
		}
}
