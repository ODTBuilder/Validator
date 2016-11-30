package com.git.gdsbuilder.generalization.impl;

import java.io.IOException;

import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.DefaultFeatureCollection;
import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.generalization.data.DTGeneralAfLayer;
import com.git.gdsbuilder.generalization.data.DTGeneralPreLayer;
import com.git.gdsbuilder.generalization.opt.DTGeneralOption;
import com.git.gdsbuilder.generalization.rep.DTGeneralReport;
import com.git.gdsbuilder.generalization.rep.type.DTGeneralNums;
import com.vividsolutions.jts.geom.Geometry;

/**
 * 일반화 Type중 Elimination을 수행한다.
 * @author SG.Lee
 * @Date 2016.10
 * */
public class Elimination extends Generalization {

	public Elimination(DTGeneralPreLayer preLayer) {
		super(preLayer);
	}


	
	/**
	 * Elimination을 실행한다.
	 * @author SG.Lee
	 * @Date 2016.10
	 * @param 
	 * @return DTGeneralAfLayer
	 * @throws
	 * */
	public DTGeneralAfLayer getElimination() {
		DTGeneralAfLayer afLayer = new DTGeneralAfLayer(); // 일반화 후 레이어 생성
		Double value = new Double(0.0); // Double 객체생성

		// DTGeneralNums - 일반화 전후 생성
		DTGeneralNums preResultNum = new DTGeneralNums(DTGeneralNums.DTGeneralNumsType.ENTITYNUM, 0);
		DTGeneralNums afResultNum = new DTGeneralNums(DTGeneralNums.DTGeneralNumsType.ENTITYNUM, 0);

		// DefaultFeatureCollection 생성
		DefaultFeatureCollection collection = new DefaultFeatureCollection();

		value = super.option.get(DTGeneralOption.DTGeneralOptionType.LENGTH);

		// super.collection - 일반화 대상 레이어
		if (super.collection != null) {
			SimpleFeatureIterator iterator = super.collection.features();// collection iterator
			while (iterator.hasNext()) {
				SimpleFeature feature = iterator.next();
				Geometry geom = (Geometry) feature.getDefaultGeometry();
				
				//lineString일 경우에 
				if (geom.getGeometryType().equals("LineString") || geom.getGeometryType().equals("MultiLineString")) {
					if (value != null && value != 0.0) {
						if (geom.getLength() > value) {
							collection.add(feature);
						}
					} else
						collection = null;

				//polygon일 경우에
				} else if (geom.getGeometryType().equals("Polygon") || geom.getGeometryType().equals("MultiPolygon")) {
					if (value != null && value != 0.0) {
						if (geom.getArea() > value) {
							collection.add(feature);
						}
					} else
						collection = null;
				} else {
					collection = null;
				}
			}
			try {
				preResultNum.put(DTGeneralNums.DTGeneralNumsType.ENTITYNUM,((DefaultFeatureCollection) super.collection).getCount());
				afResultNum.put(DTGeneralNums.DTGeneralNumsType.ENTITYNUM, collection.getCount());
			} catch (IOException e) {
				return null;
			}
			
			DTGeneralReport report = new DTGeneralReport(preResultNum,afResultNum);
			afLayer.setCollection(collection);
			afLayer.setReport(report);
		}
		return afLayer;
	}
	
}
