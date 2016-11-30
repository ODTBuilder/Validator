package com.git.gdsbuilder.type.validatorOption;

public class UselessPoint extends ValidatorOption {

	public enum Type {

		USELESSPOINT("UselessPoint", "GeometricError");

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
