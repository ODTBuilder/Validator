package com.git.gdsbuilder.geoserver.data;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

import com.git.gdsbuilder.geosolutions.geoserver.rest.GeoServerRESTManager;
import com.git.gdsbuilder.geosolutions.geoserver.rest.GeoServerRESTReader;

/**
 * DTGeoserver Object 
 * @author SG.Lee
 * @Date 2016.08.02
 * */
public class DTGeoserver implements Serializable{

	private static final long serialVersionUID = 1L;
	private String serverName; //서버이름
	
	//Geoserver
	private String url; //서버URL
	private String id; //서버ID
	private String pw; //서버PW
	
	public DTGeoserver(){
		
	}
	
	/**
	 * 생성자 생성
	 * @param name - 서버이름
	 * @param url - 서버URL
	 * @param id - 서버ID
	 * @param pw - 서버PW
	 * @throws IllegalArgumentException
	 * @throws MalformedURLException
	 */
	public DTGeoserver(String name, String url, String id, String pw) throws IllegalArgumentException, MalformedURLException{
		DTGeoserver geoserver = new DTGeoserver();
		geoserver =this.build(name, url, id, pw);
		if(geoserver!=null){
			this.serverName = geoserver.getServerName();
			this.url = geoserver.getUrl();
			this.id = geoserver.getId();
			this.pw = geoserver.getPw();
		}
	}
	
	
	/**
	 * Geoserver와 해당 정보를 통해 유효성 검사후 서버 생성
	 * @author SG.Lee
	 * @Date 2016.08.02
	 * @param 생성자와 동일
	 * @return DTGeoserver
	 * @throws
	 * */
	@SuppressWarnings("unused")
	public DTGeoserver build(String name, String url, String id, String pw){
		DTGeoserver buildServer = new DTGeoserver();
		GeoServerRESTManager manager;
		try {
			manager = new GeoServerRESTManager(new URL(url), id, pw);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			return null;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			return null;
		}
		
		
		if (manager == null) {
			buildServer = null;
		} else {
			GeoServerRESTReader reader = manager.getReader();
			boolean flag = reader.existGeoserver();
			if(flag){
				buildServer.setServerName(name);
				buildServer.setId(id);
				buildServer.setPw(pw);
				buildServer.setUrl(url);
			}
			else
				buildServer = null;
		}
		return buildServer;
	}
	
	
	
	/**
	 * DTGeoserver GET, SET
	 * @author SG.Lee
	 * @Date 2016.8.15
	 * */
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
}
