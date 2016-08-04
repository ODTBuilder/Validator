package com.git.gdsbuilder.type.validatorOption;

import java.util.Map;

public class AttributeFix extends ValidatorOption {

	Map<Object, Object> attribute;

	public enum Type {

		ATTRIBUTEFIX("AttributeFix", "AttributeError");

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

	public AttributeFix(Map<Object, Object> attribute) {
		super();
		this.attribute = attribute;
	}

	public Map<Object, Object> getSimpleFeatureType() {
		return attribute;
	}

	public void setSimpleFeatureType(Map<Object, Object> attribute) {
		this.attribute = attribute;
	}
}
