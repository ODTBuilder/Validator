package com.git.gdsbuilder.generalization;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.DefaultFeatureCollection;
import org.opengis.feature.simple.SimpleFeature;

import com.vividsolutions.jts.geom.Geometry;

/**
 * {@link SimpleFeatureCollection}을 Elimination 하는 클래스.
 * 
 * @author DY.Oh
 *
 */
public class DTEliminater {

	/**
	 * {@link SimpleFeatureCollection}을 Elimination 함.
	 * <p>
	 * {@link SimpleFeatureCollection}의 Geometry 타입이 LineString인 경우 객체의 길이가
	 * tolerance 미만인 객체를 삭제함.
	 * <p>
	 * {@link SimpleFeatureCollection}의 Geometry 타입이 Polygon인 경우 객체의 넓이가 tolerance
	 * 미만인 객체를 삭제함.
	 * 
	 * @param inputSfc  Elimination를 할 {@link SimpleFeatureCollection}
	 * @param tolerance 길이 또는 넓이
	 * @return Elimination 결과 {@link SimpleFeatureCollection}
	 * 
	 * @author DY.Oh
	 */
	public SimpleFeatureCollection eliminate(SimpleFeatureCollection inputSfc, double tolerance) {

		DefaultFeatureCollection dfc = new DefaultFeatureCollection();
		SimpleFeatureIterator sfIter = inputSfc.features();

		while (sfIter.hasNext()) {
			SimpleFeature feature = sfIter.next();
			Geometry geom = (Geometry) feature.getDefaultGeometry();
			if (geom.isValid() && geom.isSimple()) {
				// linestring
				if (geom.getGeometryType().equals("LineString") || geom.getGeometryType().equals("MultiLineString")) {
					if (geom.getLength() > tolerance) {
						dfc.add(feature);
					}
				}
				// polygon
				else if (geom.getGeometryType().equals("Polygon") || geom.getGeometryType().equals("MultiPolygon")) {
					if (geom.getArea() > tolerance) {
						dfc.add(feature);
					}
				}
			}
		}
		return dfc;
	}

}
