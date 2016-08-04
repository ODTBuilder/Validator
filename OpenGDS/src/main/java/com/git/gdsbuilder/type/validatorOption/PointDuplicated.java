package com.git.gdsbuilder.type.validatorOption;

public class PointDuplicated extends ValidatorOption {

	public enum Type {

		POINTDUPLICATED("PointDuplicated", "GeometricError");

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
