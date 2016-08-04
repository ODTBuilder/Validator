package com.git.gdsbuilder.editor.operation;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.opengis.feature.simple.SimpleFeature;

public interface OperatorFactory {

	SimpleFeature rotate(SimpleFeature simpleFeature1, double degree);

	SimpleFeature union(SimpleFeature simpleFeature1, SimpleFeature simpleFeature2);

}
