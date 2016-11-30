package com.git.gdsbuilder.type.validatorOption;

<<<<<<< HEAD
import org.json.simple.JSONArray;

public class AttributeFix extends ValidatorOption {

	JSONArray attributeKey;
=======
import java.util.Map;

public class AttributeFix extends ValidatorOption {

	Map<Object, Object> attribute;
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c

	public enum Type {

		ATTRIBUTEFIX("AttributeFix", "AttributeError");
<<<<<<< HEAD
=======

>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
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

<<<<<<< HEAD
	public AttributeFix(JSONArray attributeKey) {
		super();
		this.attributeKey = attributeKey;
	}

	public JSONArray getAttributeKey() {
		return attributeKey;
	}

	public void setAttributeKey(JSONArray attributeKey) {
		this.attributeKey = attributeKey;
	}

=======
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
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
}
