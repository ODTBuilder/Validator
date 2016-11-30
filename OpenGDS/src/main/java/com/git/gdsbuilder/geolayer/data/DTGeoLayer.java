package com.git.gdsbuilder.geolayer.data;

import org.json.simple.JSONObject;

import com.git.gdsbuilder.geosolutions.geoserver.rest.GeoServerRESTManager;
import com.git.gdsbuilder.geosolutions.geoserver.rest.GeoServerRESTReader;
import com.git.gdsbuilder.geosolutions.geoserver.rest.decoder.RESTDataStore;
import com.git.gdsbuilder.geosolutions.geoserver.rest.decoder.RESTFeatureType;
import com.git.gdsbuilder.geosolutions.geoserver.rest.decoder.RESTLayer;


/**
 * 지오서버 레이어 
 * @author SG.Lee
 * @Date 2016.05
 * */
public class DTGeoLayer {
	/*public enum Type {
		SHP("SHP"),
		DB("DB"),
		UNKNOWN(null);

		private final String layerType;

		private Type(String layerType) {
			this.layerType = layerType;
		}

		public static Type get(String layerType) {
			for (Type type : values()) {
				if(type == UNKNOWN)
					continue;
				if(type.layerType.equals(layerType))
					return type;
			}
			return UNKNOWN;
		}
	};*/
	
	private String wsName; //작업공간
	private String dsName; //저장소
	private String lName; //레이어이름
	private String srs; //좌표체계
	private JSONObject nbBox = new JSONObject();  //바운더리
	private String dsType; //저장소타입
	private String geomType; //공간정보타입
	private JSONObject attInfo = new JSONObject(); // 속성정보
	
	
	/**
	 * 생성자 생성
	 */
	public DTGeoLayer(GeoServerRESTManager manager, String layerName){
		GeoServerRESTReader reader = manager.getReader();
		build(reader,layerName);
	}
	
	public DTGeoLayer(GeoServerRESTReader reader, String layerName){
		build(reader,layerName);
	}
	
	
	/**
	 * Geoserver 유효성 검사후 layer 생성
	 * @author SG.Lee
	 * @Date 2016.09.15
	 * @param 생성자와 동일
	 * @return DTGeoLayer
	 * @throws
	 * */
	public DTGeoLayer build(GeoServerRESTReader reader, String layerName){
		if(reader ==null){
			return null;
		}
		else{
			boolean flag = reader.existGeoserver(); // Geoserver 존재여부확인
			if(flag){
				RESTLayer layer = reader.getLayer(layerName);
				RESTFeatureType featureType = reader.getFeatureType(layer);
				RESTDataStore dataStore = reader.getDatastore(featureType);
				createLayer(dataStore, featureType);
				return this;
			}
			else
				return null;
		}
	}
	
	
	/**
	 * Geoserver 존재시 DTGeoLayer 생성
	 * @author SG.Lee
	 * @Date 2016.09.15
	 * @param dataStore - Geosolutions Format
	 *        featureType - Geosolutions Format
	 * @return
	 * @throws
	 * */
	@SuppressWarnings("unchecked")
	private void createLayer(RESTDataStore dataStore, RESTFeatureType featureType){
		this.lName = featureType.getName();
		this.srs = featureType.getSRS();
		this.nbBox.put("minx", featureType.getMinX());
		this.nbBox.put("miny", featureType.getMinY());
		this.nbBox.put("maxx", featureType.getMaxX());
		this.nbBox.put("maxy", featureType.getMaxY());
		this.dsName = featureType.getStoreName();
		this.geomType = featureType.getGeomType();
		this.attInfo = featureType.getAttType();
		this.wsName = dataStore.getWorkspaceName();
		this.dsType = dataStore.getStoreType();
	}
	
	
	/**
	 * DTGeolayer GET, SET
	 * @author SG.Lee
	 * @Date 2016.8.15
	 * */
	public String getWsName() {
		return wsName;
	}
	public void setWsName(String wsName) {
		this.wsName = wsName;
	}
	public String getDsName() {
		return dsName;
	}
	public void setDsName(String dsName) {
		this.dsName = dsName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getSrs() {
		return srs;
	}
	public void setSrs(String srs) {
		this.srs = srs;
	}
	public JSONObject getNbBox() {
		return nbBox;
	}
	public void setNbBox(JSONObject nbBox) {
		this.nbBox = nbBox;
	}
	public String getDsType() {
		return dsType;
	}
	public void setDsType(String dsType) {
		this.dsType = dsType;
	}
	public String getGeomType() {
		return geomType;
	}
	public void setGeomType(String geomType) {
		this.geomType = geomType;
	}
	public JSONObject getAttInfo() {
		return attInfo;
	}
	public void setAttInfo(JSONObject attInfo) {
		this.attInfo = attInfo;
	}
}
