package com.git.gdsbuilder.generalization;

import java.util.ArrayList;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.simplify.TopologyPreservingSimplifier;

/**
 * {@link SimpleFeatureCollection}을 Simplification 하는 클래스.
 * 
 * @author DY.Oh
 *
 */
public class DTSimplifier {

	/**
	 * {@link SimpleFeatureCollection}을 Simplification 함.
	 * <p>
	 * Douglas-Peucker 알고리즘을 적용한 LineString 타입 또는 Polygon 타입의 객체를 Simplication 함.
	 * 
	 * 
	 * @param inputSfc  Simplification을 진행할 {@link SimpleFeatureCollection}
	 * @param tolerance distanceTolerance
	 * @param repeat    반복횟수
	 * @param merge     병합 여부
	 *                  <p>
	 *                  {@code true} : {@link SimpleFeatureCollection}의 모든 객체를 1개의
	 *                  객체로 병합 한 후 Simplification 진행
	 *                  <p>
	 *                  {@code false} : {@link SimpleFeatureCollection}의 모든 객체를 각각
	 *                  Simplification 진행
	 * @return Simplification 결과 {@link SimpleFeatureCollection}
	 * 
	 * @author DY.Oh
	 */
	public SimpleFeatureCollection simplify(SimpleFeatureCollection inputSfc, double tolerance, int repeat,
			boolean merge) {

		SimpleFeatureCollection sfc = new DefaultFeatureCollection();
		sfc = inputSfc;
		for (int r = 0; r < repeat; r++) {
			if (merge) {
				SimpleFeatureIterator sfIter = sfc.features();
				ArrayList<Geometry> geometries = new ArrayList<>();
				// 1. union
				while (sfIter.hasNext()) {
					SimpleFeature feature = sfIter.next();
					Geometry geom = (Geometry) feature.getDefaultGeometry();
					if (geom.isValid() && geom.isSimple()) {
						geom.setUserData(feature);
						geometries.add(geom);
					}
				}
				GeometryFactory factory = new GeometryFactory();
				GeometryCollection geometryCollection = (GeometryCollection) factory.buildGeometry(geometries);
				Geometry union = geometryCollection.union();
				// 2. simplify
				Geometry result = TopologyPreservingSimplifier.simplify(union, tolerance);
				// 3. multi to single
				DefaultFeatureCollection dfc = new DefaultFeatureCollection();
				SimpleFeatureType sfType = sfc.getSchema();
				int num = result.getNumGeometries();
				for (int n = 0; n < num; n++) {
					Geometry single = result.getGeometryN(n);
					SimpleFeature sf = SimpleFeatureBuilder.build(sfType, new Object[] {}, String.valueOf(n));
					sf.setDefaultGeometry(single);
					dfc.add(sf);
				}
				sfc = dfc;
			} else {
				DefaultFeatureCollection dfc = new DefaultFeatureCollection();
				SimpleFeatureIterator sfIter = sfc.features();
				SimpleFeatureType sfType = sfc.getSchema();

				int afGeomNum = 0;
				while (sfIter.hasNext()) {
					SimpleFeature feature = sfIter.next();
					Geometry geom = (Geometry) feature.getDefaultGeometry();
					if (geom.isValid() && geom.isSimple()) {
						Geometry result = TopologyPreservingSimplifier.simplify(geom, tolerance);
						if (!result.isEmpty()) {
							SimpleFeature sf = SimpleFeatureBuilder.build(sfType, new Object[] {},
									String.valueOf(afGeomNum));
							sf.setDefaultGeometry(result);
							dfc.add(sf);
						}
					}
				}
				sfc = dfc;
			}
		}
		return sfc;
	}

}
