package com.git.gdsbuilder.type.validatorOption;

public class ConBreak extends ValidatorOption {

	public enum Type {

		CONBREAK("ConBreak", "GeometricError");

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
