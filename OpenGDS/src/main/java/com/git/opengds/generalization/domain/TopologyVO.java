package com.git.opengds.generalization.domain;

public class TopologyVO {
	private String objID;
	private String firstObjs;
	private String lastObjs;
	private Double alValue;
	
	public String getObjID() {
		return objID;
	}
	public void setObjID(String objID) {
		this.objID = objID;
	}
	public String getFirstObjs() {
		return firstObjs;
	}
	public void setFirstObjs(String firstObjs) {
		this.firstObjs = firstObjs;
	}
	public String getLastObjs() {
		return lastObjs;
	}
	public void setLastObjs(String lastObjs) {
		this.lastObjs = lastObjs;
	}
	public Double getAlValue() {
		return alValue;
	}
	public void setAlValue(Double alValue) {
		this.alValue = alValue;
	}
}
