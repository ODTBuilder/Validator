package com.git.gdsbuilder.type.validatorOption;


public class EntityDuplicated extends ValidatorOption {

	public enum Type {

		ENTITYDUPLICATED("EntityDuplicated", "GeometricError");

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
