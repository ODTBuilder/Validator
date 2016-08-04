package com.git.gdsbuilder.type.validatorOption;

import java.util.List;

import com.git.gdsbuilder.type.layer.ValidatorLayer;

public class EntityDuplicated extends ValidatorOption {

	List<ValidatorLayer> relation;

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

	public EntityDuplicated(List<ValidatorLayer> relation) {
		super();
		this.relation = relation;
	}

	public List<ValidatorLayer> getRelation() {
		return relation;
	}

	public void setRelation(List<ValidatorLayer> relation) {
		this.relation = relation;
	}

}
