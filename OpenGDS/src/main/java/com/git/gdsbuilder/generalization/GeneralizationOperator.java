package com.git.gdsbuilder.generalization;

import java.util.List;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.DefaultFeatureCollection;
import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.type.dt.layer.DTLayer;
import com.vividsolutions.jts.geom.Geometry;

/**
 * 일반화 수행 클래스.
 *
 * @author DY.Oh
 *
 */
public class GeneralizationOperator {

	private DTLayer dtLayer;
	private List<GeneralizationOption> genalOpts;

	private int beforeFetureNum;
	private int beforePointNum;

	private int afterFetureNum;
	private int afterPointNum;

	public GeneralizationOperator(DTLayer dtLayer, List<GeneralizationOption> genalOpts) {
		this.dtLayer = dtLayer;
		this.genalOpts = genalOpts;
	}

	/**
	 * {@link DTLayer}를 일반화.
	 * 
	 * @return {@link SimpleFeatureCollection} 일반화 결과.
	 * 
	 * @author DY.Oh
	 */
	public SimpleFeatureCollection generalizate() {

		SimpleFeatureCollection inputSfc = dtLayer.getSimpleFeatureCollection();
		this.beforeFetureNum = inputSfc.size();
		this.beforePointNum = getNumPoints(inputSfc);

		SimpleFeatureCollection sfc = new DefaultFeatureCollection();
		sfc = inputSfc;

		for (GeneralizationOption genalOpt : genalOpts) {
			String name = genalOpt.getName();
			double tolerance = genalOpt.getTolerance();
			if (name.equals(GeneralizationOption.SIMPLIFICATION)) {
				DTSimplifier simplifier = new DTSimplifier();
				sfc = simplifier.simplify(sfc, tolerance, genalOpt.getRepeat(), genalOpt.isMerge());
			}
			if (name.equals(GeneralizationOption.ELIMINATION)) {
				DTEliminater eliminater = new DTEliminater();
				sfc = eliminater.eliminate(sfc, tolerance);
			}
		}
		this.afterFetureNum = sfc.size();
		this.afterPointNum = getNumPoints(sfc);

		return sfc;
	}

	/**
	 * {@link SimpleFeatureCollection}의 모든 객체의 포인트 수를 계산함.
	 * 
	 * @param sfc {@link SimpleFeatureCollection}
	 * @return int 모든 객체의 포인트 수
	 * 
	 * @author DY.Oh
	 */
	public int getNumPoints(SimpleFeatureCollection sfc) {

		int ptNum = 0;
		SimpleFeatureIterator sfIter = sfc.features();
		while (sfIter.hasNext()) {
			SimpleFeature feature = sfIter.next();
			Geometry geom = (Geometry) feature.getDefaultGeometry();
			ptNum += geom.getNumPoints();
		}
		return ptNum;
	}
}
