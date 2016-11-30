package com.git.gdsbuilder.generalization.impl;

import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.validation.spatial.LineNoDanglesValidation;
import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.generalization.data.DTGeneralAfLayer;
import com.git.gdsbuilder.generalization.data.DTGeneralPreLayer;
import com.git.gdsbuilder.generalization.opt.DTGeneralOption;
import com.git.gdsbuilder.generalization.rep.DTGeneralReport;
import com.git.gdsbuilder.generalization.rep.type.DTGeneralNums;
import com.git.gdsbuilder.generalization.rep.type.DTGeneralNums.DTGeneralNumsType;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.simplify.TopologyPreservingSimplifier;

public class Simplification extends Generalization {

	public Simplification(DTGeneralPreLayer preLayer) {
		super(preLayer);
	}

	
	public DTGeneralAfLayer getSimplification(){
		DTGeneralAfLayer afLayer = new DTGeneralAfLayer(); 
		Double distance = super.option.get(DTGeneralOption.DTGeneralOptionType.DISTANCE);
		DTGeneralNums preResultNum = new DTGeneralNums(DTGeneralNums.DTGeneralNumsType.POINTNUM, 0);
		DTGeneralNums afResultNum = new DTGeneralNums(DTGeneralNums.DTGeneralNumsType.POINTNUM, 0);
		DefaultFeatureCollection collection = new DefaultFeatureCollection();
		
		
		if (super.collection != null) {
			if (distance != 0.0 && distance != null) {
				SimpleFeatureIterator iterator = super.collection.features();
				while (iterator.hasNext()) {
					SimpleFeature feature = iterator.next();
					Geometry geom = (Geometry) feature.getDefaultGeometry();
					int geomNums = 0;
					geomNums = geom.getNumPoints();
					Geometry newGeom = TopologyPreservingSimplifier.simplify(geom, distance);
					int newGeomNums = 0;
					newGeomNums = newGeom.getNumPoints();
					preResultNum.addNum(DTGeneralNumsType.POINTNUM, geomNums);
					afResultNum.addNum(DTGeneralNumsType.POINTNUM, newGeomNums);
					feature.setDefaultGeometry(newGeom);
					collection.add(feature);
				}
				DTGeneralReport report = new DTGeneralReport(preResultNum,afResultNum);
				afLayer.setCollection(collection);
				afLayer.setReport(report);
			}
		}
		return afLayer;
	}
}
