package com.git.gdsbuilder.editor.operation;

import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.convertor.DataConvertor;
import com.vividsolutions.jts.geom.Geometry;

public class OperatorFactoryImpl implements OperatorFactory {

	@Override
	public SimpleFeature rotate(SimpleFeature simpleFeature1, double degree) {
		
		System.out.println("etest");
		
		return null;
	}

	@Override
	public SimpleFeature union(SimpleFeature simpleFeature1, SimpleFeature simpleFeature2) {
		
		System.out.println("union시작");
		System.out.println();
		//Geometry geometry1 = (Geometry) simpleFeature1.getDefaultGeometry();
		//Geometry geometry2 = (Geometry) simpleFeature2.getDefaultGeometry();
		
		/*Geometry geometry = geometry1.union(geometry2);
		
		DataConvertor dataConvertor = new DataConvertor();
		
		SimpleFeature returnSimpleFeature = (SimpleFeature) geometry;*/
		//System.out.println(returnSimpleFeature.toString());
		
		//return returnSimpleFeature;
		// TODO Auto-generated method stub
		
		return null;
		
		
	}	

}
