package com.git.gdsbuilder.generalization.opt;

import java.util.HashMap;

/**
 * 일반화 옵션 설정
 * @author SG.Lee
 * @Date 2016.10
 * */
public class DTGeneralOption extends HashMap<Object,Double> {

	private static final long serialVersionUID = 1L;

	
	/**
	 * 일반화 옵션 타입 Enum
	 * @author SG.Lee
	 * @Date
	 * */
	public enum DTGeneralOptionType {
		DISTANCE("distance"), //Simplification - 수직거리
		LENGTH("length"), //Elimination - Line일 경우 
		AREA("area"), //Elimination - Polygon일 경우
		UNKNOWN(null);

		private final String typeName;

		private DTGeneralOptionType(String typeName) {
			this.typeName = typeName;
		}

		public static DTGeneralOptionType get(String typeName) {
			for (DTGeneralOptionType type : values()) {
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
	
	public Double put(DTGeneralOptionType type, Double value){
		return super.put(type, value);
	}
	
	public Double get(DTGeneralOptionType type){
		return super.get(type);
	}
}
