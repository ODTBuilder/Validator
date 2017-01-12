package com.git.opengds.common;

import org.json.simple.JSONObject;

public class TestSession {

	private String serverName; //서버이름
	private int requestSize; //요청사이즈
	private String requestTime; //요청시간
	private String responseTime; //응답시간
	private long timeDifference; //응답시간-요청시간
	private JSONObject validatorInfo; //요청 클래스별 처리결과
	
	public TestSession() {
		// TODO Auto-generated constructor stub
	}
	
	public TestSession(String serverName, int requestSize, String requestTime, String responseTime, long timeDifference, JSONObject validatorInfo) {
		// TODO Auto-generated constructor stub
		this.serverName = serverName;
		this.requestSize = requestSize;
		this.requestTime = requestTime;
		this.responseTime = responseTime;
		this.timeDifference = timeDifference;
		this.validatorInfo = validatorInfo;
	}
	
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public int getRequestSize() {
		return requestSize;
	}
	public void setRequestSize(int requestSize) {
		this.requestSize = requestSize;
	}
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
	public String getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}
	public long getTimeDifference() {
		return timeDifference;
	}
	public void setTimeDifference(long timeDifference) {
		this.timeDifference = timeDifference;
	}
	public JSONObject getValidatorInfo() {
		return validatorInfo;
	}
	public void setValidatorInfo(JSONObject validatorInfo) {
		this.validatorInfo = validatorInfo;
	}
}
