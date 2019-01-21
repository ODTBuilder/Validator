/**
 * 
 */
package com.git.gdsbuilder.parser.geoserver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;

import com.git.gdsbuilder.type.dt.layer.DTLayer;

import lombok.Data;

/**
 * @author GIT
 *
 */
@Data
public class QALayerParser {

	private String baseUrl;
	private String user;
	private String pw;

	private String getCapabilities;
	private String layerName;
	private DataStore dataStore;

	/**
	 * @param baseUrl
	 * @param user
	 * @param pw
	 * @param workspace
	 */
	public QALayerParser(String baseUrl, String user, String pw, String layerName) {
		super();
		this.baseUrl = baseUrl;
		this.user = user;
		this.pw = pw;
		this.layerName = layerName;
	}

	public void init() {
		String getCapabilities = baseUrl + "/wfs?REQUEST=GetCapabilities&version=1.0.0";
		Map connectionParameters = new HashMap();
		connectionParameters.put("WFSDataStoreFactory:GET_CAPABILITIES_URL", getCapabilities);
		// connectionParameters.put("WFSDataStoreFactory.TIMEOUT.key", 999999999);
		// connectionParameters.put("WFSDataStoreFactory:BUFFER_SIZE", 999999999);
		try {
			this.dataStore = DataStoreFinder.getDataStore(connectionParameters);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public DTLayer layerParse() {
		SimpleFeatureCollection sfc = null;
		DTLayer dtLayer = null;
		try {
			SimpleFeatureSource source = this.dataStore.getFeatureSource(this.layerName);
			sfc = source.getFeatures();
			dtLayer = new DTLayer();
			String layrName = this.layerName.substring(this.layerName.indexOf(":") + 1, this.layerName.length());
			dtLayer.setLayerID(layrName);
			dtLayer.setSimpleFeatureCollection(sfc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dtLayer;
	}
}
