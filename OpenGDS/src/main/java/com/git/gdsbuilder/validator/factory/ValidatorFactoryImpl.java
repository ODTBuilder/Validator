package com.git.gdsbuilder.validator.factory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

<<<<<<< HEAD
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.SchemaException;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.json.simple.JSONArray;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CRSAuthorityFactory;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.TransformException;
=======
import org.geotools.feature.SchemaException;
import org.json.simple.JSONArray;
import org.opengis.feature.simple.SimpleFeature;
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c

import com.git.gdsbuilder.convertor.DataConvertor;
import com.git.gdsbuilder.type.result.DetailsValidatorReport;
import com.git.gdsbuilder.type.validatorOption.AttributeFix;
import com.git.gdsbuilder.type.validatorOption.ConBreak;
import com.git.gdsbuilder.type.validatorOption.ConIntersected;
import com.git.gdsbuilder.type.validatorOption.ConOverDegree;
import com.git.gdsbuilder.type.validatorOption.EntityDuplicated;
<<<<<<< HEAD
import com.git.gdsbuilder.type.validatorOption.OutBoundary;
import com.git.gdsbuilder.type.validatorOption.PointDuplicated;
import com.git.gdsbuilder.type.validatorOption.SelfEntity;
import com.git.gdsbuilder.type.validatorOption.SmallArea;
import com.git.gdsbuilder.type.validatorOption.SmallLength;
import com.git.gdsbuilder.type.validatorOption.UselessPoint;
import com.git.gdsbuilder.type.validatorOption.Z_ValueAmbiguous;
import com.vividsolutions.jts.algorithm.Angle;
import com.vividsolutions.jts.algorithm.CentroidPoint;
=======
import com.git.gdsbuilder.type.validatorOption.PointDuplicated;
import com.git.gdsbuilder.type.validatorOption.SmallArea;
import com.git.gdsbuilder.type.validatorOption.SmallLength;
import com.vividsolutions.jts.algorithm.Angle;
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class ValidatorFactoryImpl implements ValidatorFactory {
<<<<<<< HEAD
	
=======
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
	// 허용범위이하 길이 오류
	@Override
	public Map<String, Object> smallLength(SimpleFeature simpleFeature, double defaultLength) throws SchemaException {

		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();

		double length = geometry.getLength();
		if (length < defaultLength) {
			DataConvertor convertService = new DataConvertor();
			String errFeatureID = simpleFeature.getID();

			Geometry returnGeom;

			if (geometry.getCoordinates().length > 2) {
				returnGeom = geometry.getInteriorPoint();
			} else {
				returnGeom = geometry.getInteriorPoint();
			}

			// SimpleFeature
			SimpleFeature returnfeature = convertService.createErrFeature(errFeatureID, returnGeom, "SmallLength");

			// DetailReport
			DetailsValidatorReport detailsReport = new DetailsValidatorReport(SmallLength.Type.SMALLLENGTH.errType(), SmallLength.Type.SMALLLENGTH.errName(),
					errFeatureID, returnGeom.getCoordinate().x, returnGeom.getCoordinate().y);

			// createReturnMap
			Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
			returnMap.put("simpleFeature", returnfeature);
			returnMap.put("detailsReport", detailsReport);

			return returnMap;
		} else {
			return null;
		}
	}

	// 허용범위이하 면적 오류
	@Override
	public Map<String, Object> smallArea(SimpleFeature simpleFeature, double defaultArea) throws SchemaException {

		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
<<<<<<< HEAD
=======

>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
		double area = geometry.getArea();
		if (area < defaultArea) {
			DataConvertor convertService = new DataConvertor();
			String errFeatureID = simpleFeature.getID();

			// SimpleFeature
<<<<<<< HEAD
			SimpleFeature returnfeature = convertService.createErrFeature(errFeatureID, geometry.getInteriorPoint(), "SmallArea");
=======
			SimpleFeature returnfeature = convertService.createErrFeature(errFeatureID, geometry, "SmallArea");
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c

			// DetailReport
			DetailsValidatorReport detailsReport = new DetailsValidatorReport(SmallArea.Type.SMALLAREA.errType(), SmallArea.Type.SMALLAREA.errName(),
					errFeatureID, geometry.getCoordinate().x, geometry.getCoordinate().y);

			// createReturnMap
			Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
			returnMap.put("simpleFeature", returnfeature);
			returnMap.put("detailsReport", detailsReport);

			return returnMap;
		} else {
			return null;
		}
	}

	// 요소중복 오류
	@Override
	public Map<String, Object> entityDuplicated(SimpleFeature simpleFeatureI, SimpleFeature simpleFeatureJ) throws SchemaException {

		Vector<SimpleFeature> simpleFeatures = new Vector<SimpleFeature>();
		List<DetailsValidatorReport> detailsReports = new ArrayList<DetailsValidatorReport>();

		Geometry geometryI = (Geometry) simpleFeatureI.getDefaultGeometry();
		Geometry geometryJ = (Geometry) simpleFeatureJ.getDefaultGeometry();

		if (geometryI.equals(geometryJ)) {
<<<<<<< HEAD
			double geomIA = geometryI.getArea();
			double geomJA = geometryJ.getArea();
			if (geomIA == geomJA) {
				int geomIN = geometryI.getNumPoints();
				int geomJN = geometryJ.getNumPoints();
				if (geomIN == geomJN) {
					DataConvertor convertService = new DataConvertor();
					SimpleFeature returnfeatureI = convertService.createErrFeature(simpleFeatureI.getID(), geometryI.getInteriorPoint(), "EntityDuplicated");
					SimpleFeature returnfeatureJ = convertService.createErrFeature(simpleFeatureJ.getID(), geometryJ.getInteriorPoint(), "EntityDuplicated");

					// SimpleFeatures
					simpleFeatures.add(returnfeatureI);
					simpleFeatures.add(returnfeatureJ);

					// DetailReport simpleFeatureI, simpleFeatureJ
					DetailsValidatorReport detailsReportI = new DetailsValidatorReport(EntityDuplicated.Type.ENTITYDUPLICATED.errType(),
							EntityDuplicated.Type.ENTITYDUPLICATED.errName(), simpleFeatureI.getID(), geometryI.getInteriorPoint().getCoordinate().x, geometryI
									.getInteriorPoint().getCoordinate().y);
					detailsReports.add(detailsReportI);

					DetailsValidatorReport detailsReportJ = new DetailsValidatorReport(EntityDuplicated.Type.ENTITYDUPLICATED.errType(),
							EntityDuplicated.Type.ENTITYDUPLICATED.errName(), simpleFeatureJ.getID(), geometryJ.getInteriorPoint().getCoordinate().x, geometryJ
									.getInteriorPoint().getCoordinate().y);
					detailsReports.add(detailsReportJ);

					// createReturnMap
					Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
					returnMap.put("simpleFeatures", simpleFeatures);
					returnMap.put("detailsReports", detailsReports);
					return returnMap;
				}
			}
		}
		return null;
	}

	// 단독 존재 오류
	@Override
	public Map<String, Object> selfEntity(SimpleFeature simpleFeatureI, SimpleFeature simpleFeatureJ) throws SchemaException {

		Vector<SimpleFeature> simpleFeatures = new Vector<SimpleFeature>();
		List<DetailsValidatorReport> detailsReports = new ArrayList<DetailsValidatorReport>();
=======
			DataConvertor convertService = new DataConvertor();
			SimpleFeature returnfeatureI = convertService.createErrFeature(simpleFeatureI.getID(), geometryI.getInteriorPoint(), "EntityDuplicated");
			SimpleFeature returnfeatureJ = convertService.createErrFeature(simpleFeatureJ.getID(), geometryJ.getInteriorPoint(), "EntityDuplicated");

			// SimpleFeatures
			simpleFeatures.add(returnfeatureI);
			simpleFeatures.add(returnfeatureJ);

			// DetailReport simpleFeatureI, simpleFeatureJ
			DetailsValidatorReport detailsReportI = new DetailsValidatorReport(EntityDuplicated.Type.ENTITYDUPLICATED.errType(),
					EntityDuplicated.Type.ENTITYDUPLICATED.errName(), simpleFeatureI.getID(), geometryI.getInteriorPoint().getCoordinate().x, geometryI
							.getInteriorPoint().getCoordinate().y);
			detailsReports.add(detailsReportI);

			DetailsValidatorReport detailsReportJ = new DetailsValidatorReport(EntityDuplicated.Type.ENTITYDUPLICATED.errType(),
					EntityDuplicated.Type.ENTITYDUPLICATED.errName(), simpleFeatureJ.getID(), geometryJ.getInteriorPoint().getCoordinate().x, geometryJ
							.getInteriorPoint().getCoordinate().y);
			detailsReports.add(detailsReportJ);

			// createReturnMap
			Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
			returnMap.put("simpleFeatures", simpleFeatures);
			returnMap.put("detailsReports", detailsReports);

			return returnMap;
		} else {
			return null;
		}
	}

	// 단독 존재 오류 - 포인트
	@Override
	public Vector<SimpleFeature> selfEntity4Point(SimpleFeature simpleFeatureI, SimpleFeature simpleFeatureJ) throws SchemaException {

		Vector<SimpleFeature> simpleFeatures = new Vector<SimpleFeature>();
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c

		Geometry geometryI = (Geometry) simpleFeatureI.getDefaultGeometry();
		Geometry geometryJ = (Geometry) simpleFeatureJ.getDefaultGeometry();

<<<<<<< HEAD
		String geomIType = geometryI.getGeometryType();
		String geomJType = geometryJ.getGeometryType();

		if (!geomIType.equals(geomJType)) {
			return null;
		} else {
			Geometry returnGeom = null;
			if (geomIType.equals("Point") || geomIType.equals("MultiPoint")) {
				if (geometryI.equals(geometryJ)) {
					returnGeom = geometryI.intersection(geometryJ);
				}
			}
			if (geomIType.equals("LineString") || geomIType.equals("MultiLineString")) {
				if (geometryI.crosses(geometryJ)) {
					returnGeom = geometryI.intersection(geometryJ);
				}
			}
			if (geomIType.equals("Polygon") || geomIType.equals("MultiPolygon")) {
				if (geometryI.overlaps(geometryJ)) {
					// if(geometryI.within(geometryJ) ||
					// geometryI.contains(geometryJ)) {
					returnGeom = geometryI.intersection(geometryJ);
					// }
				}
			}

			if (returnGeom != null) {
				DataConvertor convertService = new DataConvertor();
				for (int i = 0; i < returnGeom.getNumGeometries(); i++) {
					SimpleFeature returnfeatureI = convertService.createErrFeature(simpleFeatureI.getID(), returnGeom.getGeometryN(i).getInteriorPoint(),
							"SelfEntity");
					simpleFeatures.add(returnfeatureI);
					SimpleFeature returnfeatureJ = convertService.createErrFeature(simpleFeatureJ.getID(), returnGeom.getGeometryN(i).getInteriorPoint(),
							"SelfEntity");
					simpleFeatures.add(returnfeatureJ);

					DetailsValidatorReport detailsReportI = new DetailsValidatorReport(SelfEntity.Type.SELFENTITY.errType(),
							SelfEntity.Type.SELFENTITY.errName(), simpleFeatureI.getID(), returnGeom.getGeometryN(i).getInteriorPoint().getCoordinate().x,
							returnGeom.getGeometryN(i).getInteriorPoint().getCoordinate().y);
					detailsReports.add(detailsReportI);
					DetailsValidatorReport detailsReportJ = new DetailsValidatorReport(SelfEntity.Type.SELFENTITY.errType(),
							SelfEntity.Type.SELFENTITY.errName(), simpleFeatureJ.getID(), returnGeom.getGeometryN(i).getInteriorPoint().getCoordinate().x,
							returnGeom.getGeometryN(i).getInteriorPoint().getCoordinate().y);
					detailsReports.add(detailsReportJ);
				}
				// createReturnMap
				Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
				returnMap.put("simpleFeatures", simpleFeatures);
				returnMap.put("detailsReports", detailsReports);

				return returnMap;
			} else {
				return null;
			}
		}
	}

	@Override
	public Map<String, Object> selfEntityMTL(SimpleFeature simpleFeatureI, SimpleFeature simpleFeatureJ) throws SchemaException {

		Vector<SimpleFeature> simpleFeatures = new Vector<SimpleFeature>();
		List<DetailsValidatorReport> detailsReports = new ArrayList<DetailsValidatorReport>();
=======
		if (geometryI.equals(geometryJ)) {
			DataConvertor convertService = new DataConvertor();
			SimpleFeature returnfeatureI = convertService.createErrFeature(simpleFeatureI.getID(), geometryI.getInteriorPoint(), "SelfEntity");
			SimpleFeature returnfeatureJ = convertService.createErrFeature(simpleFeatureJ.getID(), geometryJ.getInteriorPoint(), "SelfEntity");

			simpleFeatures.add(returnfeatureI);
			simpleFeatures.add(returnfeatureJ);

			return simpleFeatures;
		} else {
			return null;
		}
	}

	// 단독 존재 오류 - 폴리곤
	@Override
	public Vector<SimpleFeature> selfEntity4Polygon(SimpleFeature simpleFeatureI, SimpleFeature simpleFeatureJ) throws SchemaException {

		Vector<SimpleFeature> simFeatures = new Vector<SimpleFeature>();
		DataConvertor convertService = new DataConvertor();
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c

		Geometry geometryI = (Geometry) simpleFeatureI.getDefaultGeometry();
		Geometry geometryJ = (Geometry) simpleFeatureJ.getDefaultGeometry();

<<<<<<< HEAD
		String geomIType = geometryI.getGeometryType();
		Geometry returnGeom = null;
		if (geomIType.equals("Point") || geomIType.equals("MultiPoint")) {
			returnGeom = selfEntityPoint(geometryI, geometryJ);
		}
		if (geomIType.equals("LineString") || geomIType.equals("MultiLineString")) {
			returnGeom = selfEntityLineString(geometryI, geometryJ);
		}
		if (geomIType.equals("Polygon") || geomIType.equals("MultiPolygon")) {
			returnGeom = selfEntityPolygon(geometryI, geometryJ);
		}

		if (returnGeom != null) {
			DataConvertor convertService = new DataConvertor();
			for (int i = 0; i < returnGeom.getNumGeometries(); i++) {
				SimpleFeature returnfeatureI = convertService.createErrFeature(simpleFeatureI.getID(), returnGeom.getGeometryN(i).getInteriorPoint(),
						"SelfEntity");
				simpleFeatures.add(returnfeatureI);
				DetailsValidatorReport detailsReportI = new DetailsValidatorReport(SelfEntity.Type.SELFENTITY.errType(), SelfEntity.Type.SELFENTITY.errName(),
						simpleFeatureI.getID(), returnGeom.getGeometryN(i).getInteriorPoint().getCoordinate().x, returnGeom.getGeometryN(i).getInteriorPoint()
								.getCoordinate().y);
				detailsReports.add(detailsReportI);
			}
			// createReturnMap
			Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
			returnMap.put("simpleFeatures", simpleFeatures);
			returnMap.put("detailsReports", detailsReports);

			return returnMap;
=======
		if (!geometryI.equals(geometryJ)) {

			if (geometryI.overlaps(geometryJ) || geometryI.within(geometryJ) || geometryI.contains(geometryJ)) {

				try {
					SimpleFeature returnfeatureI = convertService.createErrFeature(simpleFeatureI.getID(), geometryI.getInteriorPoint(), "SelfEntity");
					SimpleFeature returnfeatureJ = convertService.createErrFeature(simpleFeatureJ.getID(), geometryJ.getInteriorPoint(), "SelfEntity");
					simFeatures.add(returnfeatureI);
					simFeatures.add(returnfeatureJ);

					return simFeatures;
				} catch (Exception e) {
					return null;
				}
			} else {
				return null;
			}
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
		} else {
			return null;
		}
	}

<<<<<<< HEAD
	private Geometry selfEntityPoint(Geometry geometryI, Geometry geometryJ) {

		String typeJ = geometryJ.getGeometryType();
		Geometry returnGeom = null;
		if (typeJ.equals("Point") || typeJ.equals("MultiPoint")) {
			if (geometryI.equals(geometryJ)) {
				returnGeom = geometryI.intersection(geometryJ);
			}
		}
		if (typeJ.equals("LineString") || typeJ.equals("MultiLineString")) {
			if (geometryI.intersects(geometryJ)) {
				returnGeom = geometryI.intersection(geometryJ);
			}
		}
		if (typeJ.equals("Polygon") || typeJ.equals("MultiPolygon")) {
			if (geometryI.within(geometryJ)) {
				returnGeom = geometryI.intersection(geometryJ);
			}
		}
		return returnGeom;
	}

	private Geometry selfEntityLineString(Geometry geometryI, Geometry geometryJ) {

		String typeJ = geometryJ.getGeometryType();
		Geometry returnGeom = null;
		if (typeJ.equals("Point") || typeJ.equals("MultiPoint")) {
			if (geometryI.equals(geometryJ)) {
				returnGeom = geometryI.intersection(geometryJ);
			}
		}
		if (typeJ.equals("LineString") || typeJ.equals("MultiLineString")) {
			if (geometryI.crosses(geometryJ)) {
				returnGeom = geometryI.intersection(geometryJ);
			}
		}
		if (typeJ.equals("Polygon") || typeJ.equals("MultiPolygon")) {
			if (geometryI.crosses(geometryJ) || geometryI.within(geometryJ)) {
				returnGeom = geometryI.intersection(geometryJ);
			}
		}
		return returnGeom;
	}

	private Geometry selfEntityPolygon(Geometry geometryI, Geometry geometryJ) {

		String typeJ = geometryJ.getGeometryType();
		Geometry returnGeom = null;
		if (typeJ.equals("Point") || typeJ.equals("MultiPoint")) {
			if (geometryI.within(geometryJ)) {
				returnGeom = geometryI.intersection(geometryJ);
			}
		}
		if (typeJ.equals("LineString") || typeJ.equals("MultiLineString")) {
			if (geometryI.crosses(geometryJ) || geometryI.contains(geometryJ)) {
				returnGeom = geometryI.intersection(geometryJ);
			}
		}
		if (typeJ.equals("Polygon") || typeJ.equals("MultiPolygon")) {
			if (geometryI.overlaps(geometryJ) || geometryI.within(geometryJ) || geometryI.contains(geometryJ)) {
				returnGeom = geometryI.intersection(geometryJ);
			}
		}
		return returnGeom;
=======
	// 단독존재오류 - 라인
	@Override
	public Vector<SimpleFeature> selfEntity4Line(SimpleFeature simpleFeatureI, SimpleFeature simpleFeatureJ) throws SchemaException {

		Vector<SimpleFeature> simFeatures = new Vector<SimpleFeature>();

		Geometry geometryI = (Geometry) simpleFeatureI.getDefaultGeometry();
		Geometry geometryJ = (Geometry) simpleFeatureJ.getDefaultGeometry();

		if (geometryI.crosses(geometryJ)) {
			Geometry intersectionGeom = geometryI.intersection(geometryJ);
			Coordinate[] interCoor = intersectionGeom.getCoordinates();

			Coordinate[] geomeICoor = geometryI.getCoordinates();
			Coordinate[] geomeJCoor = geometryJ.getCoordinates();

			int intersectCount = 0;
			for (int b = 0; b < interCoor.length; b++) {
				for (int a = 0; a < geomeICoor.length; a++) {
					if (interCoor[b].equals2D(geomeICoor[a])) {
						intersectCount++;
					}
				}
				for (int a = 0; a < geomeJCoor.length; a++) {
					if (interCoor[b].equals2D(geomeJCoor[a])) {
						intersectCount++;
					}
				}
			}

			DataConvertor convertService = new DataConvertor();
			if (intersectCount == 0 || intersectCount % 2 != 0) {
				for (int i = 0; i < intersectionGeom.getNumGeometries(); i++) {

					SimpleFeature returnfeatureI = convertService.createErrFeature(simpleFeatureI.getID(), intersectionGeom.getGeometryN(i), "SelfEntity");
					SimpleFeature returnfeatureJ = convertService.createErrFeature(simpleFeatureJ.getID(), intersectionGeom.getGeometryN(i), "SelfEntity");

					simFeatures.add(returnfeatureI);
					simFeatures.add(returnfeatureJ);
				}
			}
		} else {
			return null;
		}
		return simFeatures;
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
	}

	// 중복점 오류
	@Override
	public Map<String, Object> pointDuplicated(SimpleFeature simpleFeature) throws SchemaException {

		Vector<SimpleFeature> simFeatures = new Vector<SimpleFeature>();
		List<DetailsValidatorReport> detailsReports = new ArrayList<DetailsValidatorReport>();
		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		Coordinate[] coordinates = geometry.getCoordinates();

		for (int i = 0; i < coordinates.length - 1; i++) {
			if (coordinates[i].equals2D(coordinates[i + 1])) {
				Geometry returnGeom = new GeometryFactory().createPoint(coordinates[i + 1]);
				DataConvertor convertService = new DataConvertor();
				String errFeatureID = simpleFeature.getID();

				// SimpleFeature
				simFeatures.add(convertService.createErrFeature(errFeatureID, returnGeom, "PointDuplicated"));

				// DetailReport
				DetailsValidatorReport detailsReport = new DetailsValidatorReport(PointDuplicated.Type.POINTDUPLICATED.errType(),
						PointDuplicated.Type.POINTDUPLICATED.errName(), errFeatureID, returnGeom.getCoordinate().x, returnGeom.getCoordinate().y);
				detailsReports.add(detailsReport);
			}
		}
		if (simFeatures.size() != 0) {
			// createReturnMap
			Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
			returnMap.put("simpleFeatures", simFeatures);
			returnMap.put("detailsReports", detailsReports);
			return returnMap;
		} else {
			return null;
		}
	}

	// 등고선 교차 오류
	@Override
	public Map<String, Object> conIntersected(SimpleFeature simpleFeatureI, SimpleFeature simpleFeatureJ) throws SchemaException {

		Vector<SimpleFeature> simFeatures = new Vector<SimpleFeature>();
		List<DetailsValidatorReport> detailsReports = new ArrayList<DetailsValidatorReport>();

		Geometry geometryI = (Geometry) simpleFeatureI.getDefaultGeometry();
		Geometry geometryJ = (Geometry) simpleFeatureJ.getDefaultGeometry();

		if (geometryI.intersects(geometryJ)) {
<<<<<<< HEAD
			Geometry returnGeom = geometryI.intersection(geometryJ);
=======
			Geometry returnGeom = geometryJ.intersection(geometryJ);
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
			DataConvertor convertService = new DataConvertor();
			for (int i = 0; i < returnGeom.getNumGeometries(); i++) {

				// simpleFeatureI, SimpleFeatureJ
				SimpleFeature returnfeatureI = convertService.createErrFeature(simpleFeatureI.getID(), returnGeom.getGeometryN(i), "ConIntersected");
				SimpleFeature returnfeatureJ = convertService.createErrFeature(simpleFeatureJ.getID(), returnGeom.getGeometryN(i), "ConIntersected");
				simFeatures.add(returnfeatureI);
				simFeatures.add(returnfeatureJ);

				// DetailReport simpleFeatureI, simpleFeatureJ
				DetailsValidatorReport detailsReportI = new DetailsValidatorReport(ConIntersected.Type.CONINTERSECTED.errType(),
						ConIntersected.Type.CONINTERSECTED.errName(), simpleFeatureI.getID(), returnGeom.getGeometryN(i).getCoordinate().x, returnGeom
								.getInteriorPoint().getCoordinate().y);
				detailsReports.add(detailsReportI);

				DetailsValidatorReport detailsReportJ = new DetailsValidatorReport(ConIntersected.Type.CONINTERSECTED.errType(),
						ConIntersected.Type.CONINTERSECTED.errName(), simpleFeatureJ.getID(), returnGeom.getGeometryN(i).getCoordinate().x, returnGeom
								.getInteriorPoint().getCoordinate().y);
				detailsReports.add(detailsReportJ);
			}
			// createReturnMap
			Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
<<<<<<< HEAD
			returnMap.put("simpleFeatures", simFeatures);
=======
			returnMap.put("SimpleFeatures", simFeatures);
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
			returnMap.put("detailsReports", detailsReports);

			return returnMap;
		} else {
			return null;
		}
	}

	// 등고선 꺾임 오류
	@Override
	public Map<String, Object> conOverDegree(SimpleFeature simpleFeature, double degree) throws SchemaException {

		Vector<SimpleFeature> simFeatures = new Vector<SimpleFeature>();
		List<DetailsValidatorReport> detailsReports = new ArrayList<DetailsValidatorReport>();

		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		Coordinate[] coordinates = geometry.getCoordinates();

		for (int i = 0; i < coordinates.length - 2; i++) {

			Coordinate a = coordinates[i];
			Coordinate b = coordinates[i + 1];
			Coordinate c = coordinates[i + 2];

			if (!a.equals2D(b) && !b.equals2D(c) && !c.equals2D(a)) {
				double angle = Angle.toDegrees(Angle.angleBetween(a, b, c));
				if (angle < degree) {
					DataConvertor convertService = new DataConvertor();
					String errFeatureID = simpleFeature.getID();
					GeometryFactory geometryFactory = new GeometryFactory();
					Point errPoint = geometryFactory.createPoint(b);

					// SimpleFeature
					SimpleFeature returnfeature = convertService.createErrFeature(errFeatureID, errPoint, "ConOverDegree");
					simFeatures.add(returnfeature);
					// DetailReport
					DetailsValidatorReport detailsReport = new DetailsValidatorReport(ConOverDegree.Type.CONOVERDEGREE.errType(),
							ConOverDegree.Type.CONOVERDEGREE.errName(), errFeatureID, errPoint.getCoordinate().x, errPoint.getCoordinate().y);
					detailsReports.add(detailsReport);
				}
			}
		}
		if (simFeatures.size() != 0) {
			// createReturnMap
			Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
			returnMap.put("simpleFeatures", simFeatures);
			returnMap.put("detailsReports", detailsReports);
			return returnMap;
		} else {
			return null;
		}
	}

	// 등고선 끊김 오류
	@Override
<<<<<<< HEAD
	public Map<String, Object> conBreak(SimpleFeature simpleFeature, SimpleFeatureCollection aop) throws SchemaException {
=======
	public Map<String, Object> conBreak(SimpleFeature simpleFeature) throws SchemaException {
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c

		Vector<SimpleFeature> simFeatures = new Vector<SimpleFeature>();
		List<DetailsValidatorReport> detailsReports = new ArrayList<DetailsValidatorReport>();
		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
<<<<<<< HEAD

		Coordinate[] coordinates = geometry.getCoordinates();
		Coordinate start = coordinates[0];
		Coordinate end = coordinates[coordinates.length - 1];
		GeometryFactory geometryFactory = new GeometryFactory();

		if (start.equals2D(end)) {
		} else {
			SimpleFeatureIterator iterator = aop.features();
			while (iterator.hasNext()) {
				SimpleFeature aopSimpleFeature = iterator.next();
				Geometry aopGeom = (Geometry) aopSimpleFeature.getDefaultGeometry();
				if (geometry.intersection(aopGeom) != null) {

					Coordinate[] temp = new Coordinate[] { start, end };
					DataConvertor convertService = new DataConvertor();
					String errFeatureID = simpleFeature.getID();

					for (int i = 0; i < temp.length; i++) {
						Geometry returnGeom = geometryFactory.createPoint(temp[i]);
						if (Math.abs(returnGeom.distance(aopGeom.getBoundary())) > 0.2 || returnGeom.crosses(aopGeom.getBoundary())) {
							// SimpleFeature
							SimpleFeature returnfeature = convertService.createErrFeature(errFeatureID, returnGeom, "ConBreak");
							simFeatures.add(returnfeature);
							// DetailReport
							DetailsValidatorReport detailsReport = new DetailsValidatorReport(ConBreak.Type.CONBREAK.errType(),
									ConBreak.Type.CONBREAK.errName(), errFeatureID, returnGeom.getCoordinate().x, returnGeom.getCoordinate().y);
							detailsReports.add(detailsReport);
						}
					}
				}
=======
		Coordinate[] coordinates = geometry.getCoordinates();

		Coordinate start = coordinates[0];
		Coordinate end = coordinates[coordinates.length - 1];

		if (start.equals2D(end)) {
		} else {
			Coordinate[] temp = new Coordinate[] { start, end };
			DataConvertor convertService = new DataConvertor();
			String errFeatureID = simpleFeature.getID();

			for (int i = 0; i < temp.length; i++) {
				GeometryFactory geometryFactory = new GeometryFactory();
				Geometry returnGeom = geometryFactory.createPoint(temp[i]);

				// SimpleFeature
				SimpleFeature returnfeature = convertService.createErrFeature(errFeatureID, returnGeom, "ConBreak");
				simFeatures.add(returnfeature);
				// DetailReport
				DetailsValidatorReport detailsReport = new DetailsValidatorReport(ConBreak.Type.CONBREAK.errType(), ConBreak.Type.CONBREAK.errName(),
						errFeatureID, returnGeom.getCoordinate().x, returnGeom.getCoordinate().y);
				detailsReports.add(detailsReport);
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
			}
		}
		if (simFeatures.size() != 0) {
			// createReturnMap
			Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
			returnMap.put("simpleFeatures", simFeatures);
			returnMap.put("detailsReports", detailsReports);
			return returnMap;
		} else {
			return null;
		}
	}

	// 속성 오류
<<<<<<< HEAD
	@SuppressWarnings("rawtypes")
	public Map<String, Object> attributeFix(SimpleFeature simpleFeature, JSONArray notNullAtt) throws SchemaException {
		boolean isError = false;
		if (notNullAtt != null) {
			Iterator iterator = notNullAtt.iterator();
=======
	public Map<String, Object> attributeFix(SimpleFeature simpleFeature, JSONArray attributes) throws SchemaException {

		boolean isError = false;
		if (attributes != null) {
			Iterator iterator = attributes.iterator();
>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
			if (simpleFeature.getAttributeCount() > 1) {
				while (iterator.hasNext()) {
					String attribute = (String) iterator.next();
					for (int i = 1; i < simpleFeature.getAttributeCount(); i++) {
						String key = simpleFeature.getFeatureType().getType(i).getName().toString();
						if (key.equals(attribute)) {
							Object value = simpleFeature.getAttribute(i);
							if (value != null) {
								if (value.toString().equals("")) {
									isError = true;
								}
							} else {
								isError = true;
							}
						}
					}
				}
			} else {
				isError = true;
			}
		}
		if (isError) {
			DataConvertor convertService = new DataConvertor();
			String errFeatureID = simpleFeature.getID();
			Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
			Geometry returnGeo = geometry.getInteriorPoint();

			// SimpleFeature
			SimpleFeature returnfeature = convertService.createErrFeature(errFeatureID, returnGeo, "AttributeFix");

			// DetailReport
			DetailsValidatorReport detailsReport = new DetailsValidatorReport(AttributeFix.Type.ATTRIBUTEFIX.errType(),
					AttributeFix.Type.ATTRIBUTEFIX.errName(), errFeatureID, geometry.getCoordinate().x, geometry.getCoordinate().y);
			Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
			returnMap.put("simpleFeature", returnfeature);
			returnMap.put("detailsReport", detailsReport);
			return returnMap;
		} else {
			return null;
		}
<<<<<<< HEAD

	}

	@Override
	public Map<String, Object> z_valueAmbiguous(SimpleFeature simpleFeature, JSONArray notNullAtt) throws SchemaException {
		boolean isError = false;
		if (notNullAtt != null) {
			Iterator iterator = notNullAtt.iterator();
			if (simpleFeature.getAttributeCount() > 1) {
				while (iterator.hasNext()) {
					String attribute = (String) iterator.next();
					for (int i = 1; i < simpleFeature.getAttributeCount(); i++) {
						String key = simpleFeature.getFeatureType().getType(i).getName().toString();
						if (key.equals(attribute)) {
							Object value = simpleFeature.getAttribute(i);
							if (value != null) {
								if (value.toString().equals("") || value.toString().equals("0.0")) {
									isError = true;
								}
							} else {
								isError = true;
							}
						}
					}
				}
			} else {
				isError = true;
			}
		}
		if (isError) {
			DataConvertor convertService = new DataConvertor();
			String errFeatureID = simpleFeature.getID();
			Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
			Geometry returnGeo = geometry.getInteriorPoint();

			// SimpleFeature
			SimpleFeature returnfeature = convertService.createErrFeature(errFeatureID, returnGeo, "Z_ValueAmbiguous");

			// DetailReport
			DetailsValidatorReport detailsReport = new DetailsValidatorReport(Z_ValueAmbiguous.Type.Z_VALUEAMBIGUOUS.errType(),
					Z_ValueAmbiguous.Type.Z_VALUEAMBIGUOUS.errName(), errFeatureID, geometry.getCoordinate().x, geometry.getCoordinate().y);
			Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
			returnMap.put("simpleFeature", returnfeature);
			returnMap.put("detailsReport", detailsReport);
			return returnMap;
		} else {
			return null;
		}
	}

	@Override
	public Map<String, Object> outBoundary(SimpleFeature simpleFeatureI, SimpleFeature simpleFeatureJ) throws SchemaException {

		Vector<SimpleFeature> simFeatures = new Vector<SimpleFeature>();
		List<DetailsValidatorReport> detailsReports = new ArrayList<DetailsValidatorReport>();

		Geometry geometryI = (Geometry) simpleFeatureI.getDefaultGeometry();
		Geometry geometryJ = (Geometry) simpleFeatureJ.getDefaultGeometry();

		if (geometryI.overlaps(geometryJ)) {
			/*
			 * System.out.println("intsersection : " +
			 * geometryJ.intersection(geometryI).toString());
			 * System.out.println("J : " + geometryJ.toString());
			 */
			Geometry result = geometryI.intersection(geometryJ);
			if (!result.equals(geometryJ)) {
				Geometry returnGeom = geometryI.intersection(geometryJ);
				DataConvertor convertService = new DataConvertor();
				for (int i = 0; i < returnGeom.getNumGeometries(); i++) {

					// simpleFeatureI, SimpleFeatureJ
					SimpleFeature returnfeatureI = convertService.createErrFeature(simpleFeatureJ.getID(), returnGeom.getGeometryN(i), "OutBoundary");
					simFeatures.add(returnfeatureI);

					// DetailReport simpleFeatureI, simpleFeatureJ
					DetailsValidatorReport detailsReportI = new DetailsValidatorReport(OutBoundary.Type.OUTBOUNDARY.errType(),
							OutBoundary.Type.OUTBOUNDARY.errName(), simpleFeatureJ.getID(), returnGeom.getGeometryN(i).getCoordinate().x, returnGeom
									.getInteriorPoint().getCoordinate().y);
					detailsReports.add(detailsReportI);
				}
			}

			// createReturnMap
			Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
			returnMap.put("simpleFeatures", simFeatures);
			returnMap.put("detailsReports", detailsReports);

			return returnMap;
		} else {
			return null;
		}
	}

	@Override
	public Map<String, Object> uselessPoint(SimpleFeature simpleFeature) throws SchemaException, NoSuchAuthorityCodeException, FactoryException,
			TransformException {

		Vector<SimpleFeature> simpleFeatures = new Vector<SimpleFeature>();
		List<DetailsValidatorReport> detailsReports = new ArrayList<DetailsValidatorReport>();
		DataConvertor dConvertor = new DataConvertor();

		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		Coordinate[] coors = geometry.getCoordinates();

		CRSAuthorityFactory factory = CRS.getAuthorityFactory(true);
		CoordinateReferenceSystem crs = factory.createCoordinateReferenceSystem("EPSG:3857");
		for (int i = 0; i < coors.length - 1; i++) {
			Coordinate a = coors[i];
			Coordinate b = coors[i + 1];

			// 길이 조건
			double tmpLength = a.distance(b);
			double distance = JTS.orthodromicDistance(a, b, crs);
			// System.out.println("distance : " + distance);

			// double km = distance / 1000;
			// double meters = distance - (km * 1000);

			/*
			 * System.out.println("Geom : " + geometry.toString());
			 * System.out.println("a : " + a); System.out.println("b : " + b);
			 * System.out.println("Distance1 : " + tmpLength);
			 * System.out.println("Distance2 : " + distance);
			 */

			if (tmpLength == 0) {

				CentroidPoint cPoint = new CentroidPoint();
				cPoint.add(a);
				cPoint.add(b);

				Coordinate coordinate = cPoint.getCentroid();
				GeometryFactory gFactory = new GeometryFactory();
				Geometry returnGeom = gFactory.createPoint(coordinate);

				SimpleFeature returnfeature = dConvertor.createErrFeature(simpleFeature.getID(), returnGeom.getGeometryN(i), "UselessPoint");
				simpleFeatures.add(returnfeature);
				// DetailReport simpleFeatureI, simpleFeatureJ
				DetailsValidatorReport detailsReportI = new DetailsValidatorReport(UselessPoint.Type.USELESSPOINT.errType(),
						UselessPoint.Type.USELESSPOINT.errName(), simpleFeature.getID(), returnGeom.getGeometryN(i).getCoordinate().x, returnGeom
								.getInteriorPoint().getCoordinate().y);
				detailsReports.add(detailsReportI);
			}
			if (i < coors.length - 2) {
				// 각도 조건
				Coordinate c = coors[i + 2];
				if (!a.equals2D(b) && !b.equals2D(c) && !c.equals2D(a)) {
					double tmpAngle = Angle.toDegrees(Angle.angleBetween(a, b, c));
					if (tmpAngle < 6) {
						GeometryFactory gFactory = new GeometryFactory();
						Geometry returnGeom = gFactory.createPoint(b);

						SimpleFeature returnfeature = dConvertor.createErrFeature(simpleFeature.getID(), returnGeom.getGeometryN(i), "UselessPoint");
						simpleFeatures.add(returnfeature);
						// DetailReport simpleFeatureI, simpleFeatureJ
						DetailsValidatorReport detailsReportI = new DetailsValidatorReport(UselessPoint.Type.USELESSPOINT.errType(),
								UselessPoint.Type.USELESSPOINT.errName(), simpleFeature.getID(), returnGeom.getGeometryN(i).getCoordinate().x, returnGeom
										.getInteriorPoint().getCoordinate().y);
						detailsReports.add(detailsReportI);
					}
				}
			}
		}
		Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("simpleFeatures", simpleFeatures);
		returnMap.put("detailsReports", detailsReports);

		return returnMap;
	}
=======
	}

>>>>>>> ecf4dc000dbc1e75e4bec2ccdd071366fc17030c
}
