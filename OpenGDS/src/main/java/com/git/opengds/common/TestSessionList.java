package com.git.opengds.common;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.git.gdsbuilder.geoserver.data.DTGeoserver;
import com.git.gdsbuilder.geoserver.data.DTGeoserverList.Type;

public class TestSessionList extends ArrayList<TestSession> implements Serializable{
	
	private static final long serialVersionUID = 1L;

	
	public enum Type {
		TESTSESSIONLIST("testSessionList"),
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
	
	public TestSessionList(){
		
	}
	
	public TestSessionList(ArrayList<TestSession> testSessions){
		if(testSessions.size()!=0){
			for(TestSession testSession : testSessions){
				super.add(testSession);
			}
		}
	}
	
	public TestSession getTestSession(String serverName){
		TestSession testSession = new TestSession();
		for(int i=0; i<super.size();i++){
			if(super.get(i).getServerName().equals(serverName)){
				testSession = super.get(i);
			}
		}
		if(testSession.getServerName().equals("")||testSession.getServerName()==null){
			testSession = null;
		}
		return testSession;
	}
	
	public void updateTestSession(TestSession testSession){
		int trueIndex = 0;
		for(int i=0; i<super.size();i++){
			if(super.get(i).getServerName().equals(testSession.getServerName())){
				trueIndex = i;
			}
		} 
		if(trueIndex!=0){
			super.set(trueIndex, testSession);
		}
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject checkTestSession(String serverName){
		JSONObject returnJSONObject = new JSONObject();
		boolean flag = false;
		int trueIndex = 0;
		
		for(int i=0; i<super.size();i++){
			if(super.get(i).getServerName().contains(serverName)){
				flag = true;
				trueIndex = trueIndex+1;
			}
		}
		returnJSONObject.put("flag", flag);
		returnJSONObject.put("index", trueIndex);
		
		return returnJSONObject;
	}
	
	public boolean add(TestSession testSession){
		return super.add(testSession);
	}
	
	
	public void remove(String serverName){
		for(int i=0; i<super.size();i++){
			if(super.get(i).getServerName().equals(serverName)){
				super.remove(i);
			}
		}
	}
	
	public void allRemove(){
		super.clear();
	}
}
