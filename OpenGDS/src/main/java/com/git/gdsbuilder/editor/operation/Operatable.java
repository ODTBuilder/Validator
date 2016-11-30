package com.git.gdsbuilder.editor.operation;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.SchemaException;

public interface Operatable {

	public SimpleFeatureCollection operateFeatures() throws SchemaException;
}
