package com.git.gdsbuilder.geolayer.data;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.git.gdsbuilder.geoserver.data.DTGeoserver;
import com.git.gdsbuilder.geosolutions.geoserver.rest.GeoServerRESTManager;
import com.git.gdsbuilder.geosolutions.geoserver.rest.decoder.RESTLayer.Type;


/**
 * DTGeoLayer List Object
 * 
 * @author SG.Lee
 * @Date 2016.08
 * */
public class DTGeoLayerList extends ArrayList<DTGeoLayer> implements Serializable  {
	
	
	private static final long serialVersionUID = -4772221710449542370L;

	public enum Type {
		DTGEOLAYERLIST("dtgeolayerlist"),
		UNKNOWN(null);

		private final String typeName;

		private Type(String typeName) {
			this.typeName = typeName;
		}

		public static Type get(String typeName) {
			for (Type type : values()) {
				if(type == UNKNOWN)
					continue;
				if(type.typeName.equals(typeName))
					return type;
			}
			return UNKNOWN;
		}
		
		public String getTypeName(){
			return this.typeName;
		}
	};
	
	public DTGeoLayerList(){
		
	}
	
	public DTGeoLayerList(ArrayList<DTGeoLayer> geolayers){
		if(geolayers.size()!=0){
			for(DTGeoLayer layer : geolayers){
				super.add(layer);
			}
		}
	}
	
	public boolean add(DTGeoLayer layer){
		return super.add(layer);
	}
	
		
	public void allRemove(){
		super.clear();
	}
}
