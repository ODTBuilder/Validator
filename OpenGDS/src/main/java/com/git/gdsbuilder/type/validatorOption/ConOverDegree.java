package com.git.gdsbuilder.type.validatorOption;

public class ConOverDegree extends ValidatorOption {

	double degree;

	public enum Type {

		CONOVERDEGREE("ConOverDegree", "GeometricError");

		String errName;
		String errType;

		Type(String errName, String errType) {
			this.errName = errName;
			this.errType = errType;
		}

		public String errName() {
			return errName;
		}

		public String errType() {
			return errType;
		}
	};

	public ConOverDegree(double degree) {
		super();
		this.degree = degree;
	}

	public double getDegree() {
		return degree;
	}

	public void setDegree(double degree) {
		this.degree = degree;
	}

}
