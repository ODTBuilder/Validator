package com.git.gdsbuilder.type.validatorOption;

<<<<<<< HEAD

public class ConIntersected extends ValidatorOption {

=======
import java.util.List;

import com.git.gdsbuilder.type.layer.ValidatorLayer;

public class ConIntersected extends ValidatorOption {

	List<ValidatorLayer> relation;

>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
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
<<<<<<< HEAD
=======

	public ConIntersected(List<ValidatorLayer> relation) {
		super();
		this.relation = relation;
	}

	public List<ValidatorLayer> getRelation() {
		return relation;
	}

	public void setRelation(List<ValidatorLayer> relation) {
		this.relation = relation;
	}

>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
}
