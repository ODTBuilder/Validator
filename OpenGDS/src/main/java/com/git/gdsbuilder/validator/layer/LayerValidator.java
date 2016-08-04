package com.git.gdsbuilder.validator.layer;

import java.util.List;

import org.geotools.feature.SchemaException;

import com.git.gdsbuilder.type.layer.ErrorLayer;
import com.git.gdsbuilder.type.layer.ValidatorLayer;

public interface LayerValidator {

	public ErrorLayer validateLayers(List<ValidatorLayer> qaLayers) throws SchemaException;

}
