package com.git.gdsbuilder.generalization.rep.type;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 일반화 객체수와 포인트수를 포함한 객체
 * @author SG.Lee
 * @Date
 * */
public class DTGeneralNums extends HashMap<Object, Integer> implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	//Enum
	public enum DTGeneralNumsType {
		ENTITYNUM("entitynum"),
		POINTNUM("pointnum"),
		UNKNOWN(null);

		private final String typeName;

		private DTGeneralNumsType(String typeName) {
			this.typeName = typeName;
		}

		public static DTGeneralNumsType get(String typeName) {
			for (DTGeneralNumsType type : values()) {
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
	
	public DTGeneralNums(){
		
	}
	
	public DTGeneralNums(DTGeneralNumsType type, Integer value) {
		this.put(type, value);
	}
	
	public Integer put(DTGeneralNumsType type, Integer value){
		return super.put(type, value);
	}
	
	public Integer get(DTGeneralNumsType type){
		return super.get(type);
	}
	
	public void addNum(DTGeneralNumsType key ,Integer num){
		Integer preNum = super.get(key);
		
		if(preNum!=null&&num!=null){
			this.put(key, num+preNum);
		}
		
	}
}
