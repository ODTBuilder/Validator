package com.git.gdsbuilder.geoserver.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Geoserver List Object
 * @author SG.Lee
 * @Date 2016.08.05
 * */
public class DTGeoserverList extends ArrayList<DTGeoserver> implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	
	/**
	 * DTGeoserverList Enum
	 * @author SG.Lee
	 * @Date 2016.08.05
	 * */
	public enum Type {
		DTSERVERLIST("dtserverlist"),
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
	
	public DTGeoserverList(){
		
	}
	
	/**
	 * DTGeoserver List -> DTGeoserverList
	 * @param geoservers
	 */
	public DTGeoserverList(ArrayList<DTGeoserver> geoservers){
		if(geoservers.size()!=0){
			for(DTGeoserver server : geoservers){
				super.add(server);
			}
		}
	}
	
	/**
	 * DTGeoserverList에서 url로 해당 DTGeoserver
	 * @author SG.Lee
	 * @Date 2016.08.05
	 * @param url - Geoserver URL
	 * @return DTGeoserver - 조건에 맞는 객체 반환
	 * @throws
	 * */
	public DTGeoserver getDTGeoserver(String url){
		DTGeoserver geoserver = new DTGeoserver();
		for(int i=0; i<super.size();i++){
			if(super.get(i).getUrl().equals(url)){
				geoserver = super.get(i);
			}
		}
		if(geoserver.getUrl().equals("")||geoserver.getUrl()==null){
			geoserver = null;
		}
		return geoserver;
	}
	
	public boolean add(DTGeoserver geoserver){
		return super.add(geoserver);
	}
	
	
	public void remove(String url){
		for(int i=0; i<super.size();i++){
			if(super.get(i).getUrl().equals(url)){
				super.remove(i);
			}
		}
	}
	
	public void allRemove(){
		super.clear();
	}
}
