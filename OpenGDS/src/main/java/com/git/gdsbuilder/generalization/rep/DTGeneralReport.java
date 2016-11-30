package com.git.gdsbuilder.generalization.rep;

import java.io.Serializable;

import com.git.gdsbuilder.generalization.rep.type.DTGeneralNums;

/**
 * 일반화결과 레포트
 * @author SG.Lee
 * @Date 2016.10
 * */
public class DTGeneralReport implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private DTGeneralNums preResultNum; //일반화전 객체 및 포인트수
	private DTGeneralNums afResultNum; //일반화후 객체 및 포인트수 
	
	//일반화 결과 Enum
	public enum DTGeneralReportType {
		PREREPORT("prereport"), //일반화전
		AFREPORT("afreport"), //일반화후 
		UNKNOWN(null);

		private final String typeName;

		private DTGeneralReportType(String typeName) {
			this.typeName = typeName;
		}

		public static DTGeneralReportType get(String typeName) {
			for (DTGeneralReportType type : values()) {
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
	
	
	/**
	 * GET, SET
	 * @author SG.Lee
	 * @Date 2016.10
	 * */
	public DTGeneralNums getPreResultNum() {
		return preResultNum;
	}

	public void setPreResultNum(DTGeneralNums preResultNum) {
		this.preResultNum = preResultNum;
	}

	public DTGeneralNums getAfResultNum() {
		return afResultNum;
	}

	public void setAfResultNum(DTGeneralNums afResultNum) {
		this.afResultNum = afResultNum;
	}
	
	public DTGeneralReport(DTGeneralNums preResultNum, DTGeneralNums afResultNum){
		this.preResultNum=preResultNum;
		this.afResultNum=afResultNum;
	}
	
	
	/**
	 * 일반화 타입별 DTGeneralNums 
	 * @author SG.Lee
	 * @Date 2016.10
	 * @param type - 레포트 타입(일반화 전,후)
	 * @return DTGeneralNums
	 * @throws
	 * */
	public DTGeneralNums getNums(DTGeneralReportType type){
		if(type.getTypeName().equals(DTGeneralReportType.PREREPORT.getTypeName()))
			return preResultNum;
		else if(type.getTypeName().equals(DTGeneralReportType.AFREPORT.getTypeName()))
			return afResultNum;
		else
			return null;
	}
}
