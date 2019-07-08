package com.git.gdsbuilder.generalization;

import org.json.simple.JSONObject;

/**
 * 일반화 옵션 정의 클래스.
 * <p>
 * JSONObject 타입의 일반화 옵션을 {@link GeneralizationOption} 객체로 파싱함.
 * 
 * @author DY.Oh
 *
 */
public class GeneralizationOption {

	public static String FIR = "0";
	public static String SEC = "1";

	/**
	 * Elimination
	 */
	public static String ELIMINATION = "Elimination";
	/**
	 * Simplification
	 */
	public static String SIMPLIFICATION = "Simplification";

	private static String NAME = "name";
	private static String TOLERANCE = "tolerance";
	private static String REPEAT = "repeat";
	private static String MERGE = "merge";

	private String num;
	private String name;
	private double tolerance;
	private int repeat;
	private boolean merge;

	public void createGeneraliationOpt(String num, JSONObject opt) {

		this.num = num;
		this.name = (String) opt.get(NAME);
		this.tolerance = Double.parseDouble(opt.get(TOLERANCE).toString());
		if (name.equals(SIMPLIFICATION)) {
			this.repeat = Integer.parseInt(opt.get(REPEAT).toString());
			this.merge = (boolean) opt.get(MERGE);
		}
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getTolerance() {
		return tolerance;
	}

	public void setTolerance(double tolerance) {
		this.tolerance = tolerance;
	}

	public int getRepeat() {
		return repeat;
	}

	public void setRepeat(int repeat) {
		this.repeat = repeat;
	}

	public boolean isMerge() {
		return merge;
	}

	public void setMerge(boolean merge) {
		this.merge = merge;
	}

}
