package com.git.gdsbuilder.validator.factory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.geotools.feature.SchemaException;
import org.json.simple.JSONArray;
import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.convertor.DataConvertor;
import com.git.gdsbuilder.type.result.DetailsValidatorReport;
import com.git.gdsbuilder.type.validatorOption.AttributeFix;
import com.git.gdsbuilder.type.validatorOption.ConBreak;
import com.git.gdsbuilder.type.validatorOption.ConIntersected;
import com.git.gdsbuilder.type.validatorOption.ConOverDegree;
import com.git.gdsbuilder.type.validatorOption.EntityDuplicated;
import com.git.gdsbuilder.type.validatorOption.PointDuplicated;
import com.git.gdsbuilder.type.validatorOption.SmallArea;
import com.git.gdsbuilder.type.validatorOption.SmallLength;
import com.vividsolutions.jts.algorithm.Angle;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class ValidatorFactoryImpl implements ValidatorFactory {
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

		double area = geometry.getArea();
		if (area < defaultArea) {
			DataConvertor convertService = new DataConvertor();
			String errFeatureID = simpleFeature.getID();

			// SimpleFeature
			SimpleFeature returnfeature = convertService.createErrFeature(errFeatureID, geometry, "SmallArea");

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

		Geometry geometryI = (Geometry) simpleFeatureI.getDefaultGeometry();
		Geometry geometryJ = (Geometry) simpleFeatureJ.getDefaultGeometry();

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

		Geometry geometryI = (Geometry) simpleFeatureI.getDefaultGeometry();
		Geometry geometryJ = (Geometry) simpleFeatureJ.getDefaultGeometry();

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
		} else {
			return null;
		}
	}

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
			Geometry returnGeom = geometryJ.intersection(geometryJ);
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
			returnMap.put("SimpleFeatures", simFeatures);
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
	public Map<String, Object> conBreak(SimpleFeature simpleFeature) throws SchemaException {

		Vector<SimpleFeature> simFeatures = new Vector<SimpleFeature>();
		List<DetailsValidatorReport> detailsReports = new ArrayList<DetailsValidatorReport>();
		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
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
	public Map<String, Object> attributeFix(SimpleFeature simpleFeature, JSONArray attributes) throws SchemaException {

		boolean isError = false;
		if (attributes != null) {
			Iterator iterator = attributes.iterator();
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
	}

}
