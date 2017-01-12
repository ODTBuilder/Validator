package com.git.gdsbuilder.type.validatorOption;


public class ConIntersected extends ValidatorOption {

	public enum Type {

		CONINTERSECTED("ConIntersected", "GeometricError");

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
}
