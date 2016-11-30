package com.git.gdsbuilder.validator.factory;

import java.util.ArrayList;
import java.util.List;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.SchemaException;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CRSAuthorityFactory;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.TransformException;

import com.git.gdsbuilder.convertor.DataConvertor;
import com.git.gdsbuilder.type.feature.ErrorFeature;
import com.git.gdsbuilder.type.result.DetailsValidatorResult;
import com.git.gdsbuilder.type.validatorOption.ConBreak;
import com.git.gdsbuilder.type.validatorOption.ConIntersected;
import com.git.gdsbuilder.type.validatorOption.ConOverDegree;
import com.git.gdsbuilder.type.validatorOption.EntityDuplicated;
import com.git.gdsbuilder.type.validatorOption.OutBoundary;
import com.git.gdsbuilder.type.validatorOption.PointDuplicated;
import com.git.gdsbuilder.type.validatorOption.SelfEntity;
import com.git.gdsbuilder.type.validatorOption.SmallArea;
import com.git.gdsbuilder.type.validatorOption.SmallLength;
import com.git.gdsbuilder.type.validatorOption.UselessPoint;
import com.vividsolutions.jts.algorithm.Angle;
import com.vividsolutions.jts.algorithm.CentroidPoint;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class GeometryValidatorImpl implements GeometryValidator {

	@Override
	public ErrorFeature smallArea(SimpleFeature simpleFeature, double defaultArea) throws SchemaException {

		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		double area = geometry.getArea();
		if (area < defaultArea) {
			DataConvertor convertService = new DataConvertor();
			String errFeatureID = simpleFeature.getID();

			// errSimpleFeature
			SimpleFeature errSimpleFeautre = convertService.createErrSimpleFeature(errFeatureID, geometry.getInteriorPoint(),
					SmallArea.Type.SMALLAREA.errName(), SmallArea.Type.SMALLAREA.errType());

			// DetailReport
			DetailsValidatorResult dtErrReport = new DetailsValidatorResult(errFeatureID, SmallArea.Type.SMALLAREA.errType(),
					SmallArea.Type.SMALLAREA.errName(), geometry.getCoordinate().x, geometry.getCoordinate().y);

			ErrorFeature errFeature = new ErrorFeature(errSimpleFeautre, dtErrReport);
			return errFeature;
		} else {
			return null;
		}
	}

	@Override
	public ErrorFeature smallLength(SimpleFeature simpleFeature, double defaultLength) throws SchemaException {

		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		double length = geometry.getLength();
		if (length < defaultLength) {
			DataConvertor convertService = new DataConvertor();
			String errFeatureID = simpleFeature.getID();

			// errSimpleFeature
			SimpleFeature errSimpleFeautre = convertService.createErrSimpleFeature(errFeatureID, geometry.getInteriorPoint(),
					SmallLength.Type.SMALLLENGTH.errName(), SmallLength.Type.SMALLLENGTH.errType());

			// DetailReport
			DetailsValidatorResult dtErrReport = new DetailsValidatorResult(errFeatureID, SmallLength.Type.SMALLLENGTH.errType(),
					SmallLength.Type.SMALLLENGTH.errName(), geometry.getCoordinate().x, geometry.getCoordinate().y);

			ErrorFeature errFeature = new ErrorFeature(errSimpleFeautre, dtErrReport);
			return errFeature;
		} else {
			return null;
		}
	}

	@Override
	public ErrorFeature entityDuplicated(SimpleFeature simpleFeatureI, SimpleFeature simpleFeatureJ) throws SchemaException {

		Geometry geometryI = (Geometry) simpleFeatureI.getDefaultGeometry();
		Geometry geometryJ = (Geometry) simpleFeatureJ.getDefaultGeometry();

		if (geometryI.equals(geometryJ)) {
			/*
			 * double geomIA = geometryI.getArea(); double geomJA =
			 * geometryJ.getArea(); if (geomIA == geomJA) { int geomIN =
			 * geometryI.getNumPoints(); int geomJN = geometryJ.getNumPoints();
			 * if (geomIN == geomJN) {
			 */
			DataConvertor convertService = new DataConvertor();
			SimpleFeature errSimpleFeautre = convertService.createErrSimpleFeature(simpleFeatureI.getID(), geometryI.getInteriorPoint(),
					EntityDuplicated.Type.ENTITYDUPLICATED.errName(), EntityDuplicated.Type.ENTITYDUPLICATED.errType());
			DetailsValidatorResult detailsReport = new DetailsValidatorResult(simpleFeatureI.getID(), EntityDuplicated.Type.ENTITYDUPLICATED.errType(),
					EntityDuplicated.Type.ENTITYDUPLICATED.errName(), geometryI.getInteriorPoint().getCoordinate().x, geometryI.getInteriorPoint()
							.getCoordinate().y);

			ErrorFeature errorFeature = new ErrorFeature(errSimpleFeautre, detailsReport);
			return errorFeature;
		} else {
			return null;
		}
	}

	@Override
	public List<ErrorFeature> pointDuplicated(SimpleFeature simpleFeature) throws SchemaException {

		List<ErrorFeature> errFeatures = new ArrayList<ErrorFeature>();

		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		Coordinate[] coordinates = geometry.getCoordinates();
		int coorSize = coordinates.length;

		for (int i = 0; i < coorSize - 1; i++) {
			if (coordinates[i].equals2D(coordinates[i + 1])) {
				Geometry returnGeom = new GeometryFactory().createPoint(coordinates[i + 1]);
				DataConvertor convertService = new DataConvertor();
				String errFeatureID = simpleFeature.getID();

				// SimpleFeature
				SimpleFeature errSimpleFeature = convertService.createErrSimpleFeature(errFeatureID, returnGeom,
						PointDuplicated.Type.POINTDUPLICATED.errName(), PointDuplicated.Type.POINTDUPLICATED.errType());

				// DetailReport
				DetailsValidatorResult detailsReport = new DetailsValidatorResult(errFeatureID, PointDuplicated.Type.POINTDUPLICATED.errType(),
						PointDuplicated.Type.POINTDUPLICATED.errName(), returnGeom.getCoordinate().x, returnGeom.getCoordinate().y);

				ErrorFeature errFeature = new ErrorFeature(errSimpleFeature, detailsReport);
				errFeatures.add(errFeature);
			}
		}
		if (errFeatures.size() != 0) {
			return errFeatures;
		} else {
			return null;
		}
	}

	@Override
	public List<ErrorFeature> selfEntity(SimpleFeature simpleFeatureI, SimpleFeature simpleFeatureJ) throws SchemaException {

		List<ErrorFeature> errFeatures = new ArrayList<ErrorFeature>();

		Geometry geometryI = (Geometry) simpleFeatureI.getDefaultGeometry();
		Geometry geometryJ = (Geometry) simpleFeatureJ.getDefaultGeometry();

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
				SimpleFeature errSimpleFeature = convertService.createErrSimpleFeature(simpleFeatureI.getID(), returnGeom.getGeometryN(i).getInteriorPoint(),
						SelfEntity.Type.SELFENTITY.errName(), SelfEntity.Type.SELFENTITY.errType());

				DetailsValidatorResult detailsReport = new DetailsValidatorResult(simpleFeatureI.getID(), SelfEntity.Type.SELFENTITY.errType(),
						SelfEntity.Type.SELFENTITY.errName(), returnGeom.getGeometryN(i).getInteriorPoint().getCoordinate().x, returnGeom.getGeometryN(i)
								.getInteriorPoint().getCoordinate().y);

				ErrorFeature errFeature = new ErrorFeature(errSimpleFeature, detailsReport);
				errFeatures.add(errFeature);
			}
			return errFeatures;
		} else {
			return null;
		}
	}

	private Geometry selfEntityPoint(Geometry geometryI, Geometry geometryJ) {

		String typeJ = geometryJ.getGeometryType();
		Geometry returnGeom = null;
		if (typeJ.equals("Point") || typeJ.equals("MultiPoint")) {
			if (geometryI.intersects(geometryJ)) {
				returnGeom = geometryI.intersection(geometryJ);
			}
		}
		if (typeJ.equals("LineString") || typeJ.equals("MultiLineString")) {
			if (geometryI.intersects(geometryJ) || geometryI.touches(geometryJ)) {
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
			if (geometryI.crosses(geometryJ) && !geometryI.equals(geometryJ)) {
				returnGeom = geometryI.intersection(geometryJ);
			}
		}
		if (typeJ.equals("Polygon") || typeJ.equals("MultiPolygon")) {
			if (geometryI.crosses(geometryJ.getBoundary()) || geometryI.within(geometryJ)) {
				returnGeom = geometryI.intersection(geometryJ.getBoundary());
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
			if (geometryI.getBoundary().crosses(geometryJ) || geometryI.contains(geometryJ)) {
				returnGeom = geometryI.getBoundary().intersection(geometryJ);
			}
		}
		if (typeJ.equals("Polygon") || typeJ.equals("MultiPolygon")) {
			if (!geometryI.equals(geometryJ)) {
				if (geometryI.overlaps(geometryJ) || geometryI.within(geometryJ) || geometryI.contains(geometryJ)) {
					returnGeom = geometryI.intersection(geometryJ);
				}
			}
		}
		return returnGeom;
	}

	@Override
	public List<ErrorFeature> conIntersected(SimpleFeature simpleFeatureI, SimpleFeature simpleFeatureJ) throws SchemaException {

		List<ErrorFeature> errFeatures = new ArrayList<ErrorFeature>();

		Geometry geometryI = (Geometry) simpleFeatureI.getDefaultGeometry();
		Geometry geometryJ = (Geometry) simpleFeatureJ.getDefaultGeometry();

		if (geometryI.intersects(geometryJ)) {
			Geometry returnGeom = geometryI.intersection(geometryJ);
			DataConvertor convertService = new DataConvertor();
			for (int i = 0; i < returnGeom.getNumGeometries(); i++) {

				// simpleFeatureI, SimpleFeatureJ
				SimpleFeature errSimpleFeature = convertService.createErrSimpleFeature(simpleFeatureI.getID(), returnGeom.getGeometryN(i),
						ConIntersected.Type.CONINTERSECTED.errName(), ConIntersected.Type.CONINTERSECTED.errType());

				// DetailReport simpleFeatureI, simpleFeatureJ
				DetailsValidatorResult detailsReport = new DetailsValidatorResult(simpleFeatureI.getID(), ConIntersected.Type.CONINTERSECTED.errType(),
						ConIntersected.Type.CONINTERSECTED.errName(), returnGeom.getGeometryN(i).getCoordinate().x, returnGeom.getInteriorPoint()
								.getCoordinate().y);

				ErrorFeature errFeature = new ErrorFeature(errSimpleFeature, detailsReport);
				errFeatures.add(errFeature);
			}
			return errFeatures;
		} else {
			return null;
		}
	}

	@Override
	public List<ErrorFeature> conOverDegree(SimpleFeature simpleFeature, double doubledfdegree) throws SchemaException {

		List<ErrorFeature> errFeatures = new ArrayList<ErrorFeature>();

		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		Coordinate[] coordinates = geometry.getCoordinates();
		int coorSize = coordinates.length;
		for (int i = 0; i < coorSize - 2; i++) {
			Coordinate a = coordinates[i];
			Coordinate b = coordinates[i + 1];
			Coordinate c = coordinates[i + 2];
			if (!a.equals2D(b) && !b.equals2D(c) && !c.equals2D(a)) {
				double angle = Angle.toDegrees(Angle.angleBetween(a, b, c));
				if (angle < doubledfdegree) {
					DataConvertor convertService = new DataConvertor();
					String errFeatureID = simpleFeature.getID();
					GeometryFactory geometryFactory = new GeometryFactory();
					Point errPoint = geometryFactory.createPoint(b);

					// SimpleFeature
					SimpleFeature errSimpleFeature = convertService.createErrSimpleFeature(errFeatureID, errPoint, ConOverDegree.Type.CONOVERDEGREE.errName(),
							ConOverDegree.Type.CONOVERDEGREE.errType());
					// DetailReport
					DetailsValidatorResult detailsReport = new DetailsValidatorResult(errFeatureID, ConOverDegree.Type.CONOVERDEGREE.errType(),
							ConOverDegree.Type.CONOVERDEGREE.errName(), errPoint.getCoordinate().x, errPoint.getCoordinate().y);

					ErrorFeature errFeature = new ErrorFeature(errSimpleFeature, detailsReport);
					errFeatures.add(errFeature);
				}
			}
		}
		if (errFeatures.size() != 0) {
			return errFeatures;
		} else {
			return null;
		}
	}

	@Override
	public List<ErrorFeature> conBreak(SimpleFeature simpleFeature, SimpleFeatureCollection aop) throws SchemaException {

		List<ErrorFeature> errFeatures = new ArrayList<ErrorFeature>();

		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		Coordinate[] coordinates = geometry.getCoordinates();
		Coordinate start = coordinates[0];
		Coordinate end = coordinates[coordinates.length - 1];
		GeometryFactory geometryFactory = new GeometryFactory();

		if (!start.equals2D(end)) {
			SimpleFeatureIterator iterator = aop.features();
			while (iterator.hasNext()) {
				SimpleFeature aopSimpleFeature = iterator.next();
				Geometry aopGeom = (Geometry) aopSimpleFeature.getDefaultGeometry();
				Geometry aopGeomBoundary = aopGeom.getBoundary();

				if (geometry.intersection(aopGeom) != null) {

					Coordinate[] temp = new Coordinate[] { start, end };
					DataConvertor convertService = new DataConvertor();
					String errFeatureID = simpleFeature.getID();
					int tempSize = temp.length;

					for (int i = 0; i < tempSize; i++) {
						Geometry returnGeom = geometryFactory.createPoint(temp[i]);
						if (Math.abs(returnGeom.distance(aopGeomBoundary)) > 0.2 || returnGeom.crosses(aopGeomBoundary)) {
							// SimpleFeature
							SimpleFeature errSimpleFeature = convertService.createErrSimpleFeature(errFeatureID, returnGeom, ConBreak.Type.CONBREAK.errName(),
									ConBreak.Type.CONBREAK.errType());
							// DetailReport
							DetailsValidatorResult detailsReport = new DetailsValidatorResult(errFeatureID, ConBreak.Type.CONBREAK.errType(),
									ConBreak.Type.CONBREAK.errName(), returnGeom.getCoordinate().x, returnGeom.getCoordinate().y);

							ErrorFeature errFeature = new ErrorFeature(errSimpleFeature, detailsReport);
							errFeatures.add(errFeature);
						}
					}
				}
			}
		}
		if (errFeatures.size() != 0) {
			return errFeatures;
		} else {
			return null;
		}
	}

	@Override
	public ErrorFeature outBoundary(SimpleFeature simpleFeatureI, SimpleFeature simpleFeatureJ) throws SchemaException {

		Geometry geometryI = (Geometry) simpleFeatureI.getDefaultGeometry();
		Geometry geometryJ = (Geometry) simpleFeatureJ.getDefaultGeometry();

		if (geometryI.overlaps(geometryJ)) {
			Geometry result = geometryI.intersection(geometryJ);
			if (!result.equals(geometryJ) || result == null || result.getNumPoints() == 0) {

				Geometry returnGome = geometryJ.getInteriorPoint();
				DataConvertor convertService = new DataConvertor();
				// simpleFeatureI, SimpleFeatureJ
				SimpleFeature errSimpleFeature = convertService.createErrSimpleFeature(simpleFeatureJ.getID(), returnGome,
						OutBoundary.Type.OUTBOUNDARY.errName(), OutBoundary.Type.OUTBOUNDARY.errType());
				// DetailReport simpleFeatureI, simpleFeatureJ
				DetailsValidatorResult detailsReport = new DetailsValidatorResult(simpleFeatureJ.getID(), OutBoundary.Type.OUTBOUNDARY.errType(),
						OutBoundary.Type.OUTBOUNDARY.errName(), returnGome.getCoordinate().x, returnGome.getCoordinate().y);
				ErrorFeature errFeature = new ErrorFeature(errSimpleFeature, detailsReport);
				return errFeature;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public List<ErrorFeature> uselessPoint(SimpleFeature simpleFeature) throws SchemaException, NoSuchAuthorityCodeException, FactoryException,
			TransformException {

		List<ErrorFeature> errFeatures = new ArrayList<ErrorFeature>();

		DataConvertor dConvertor = new DataConvertor();
		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		Coordinate[] coors = geometry.getCoordinates();
		int coorsSize = coors.length;

		CRSAuthorityFactory factory = CRS.getAuthorityFactory(true);
		CoordinateReferenceSystem crs = factory.createCoordinateReferenceSystem("EPSG:3857");
		for (int i = 0; i < coorsSize - 1; i++) {
			Coordinate a = coors[i];
			Coordinate b = coors[i + 1];

			// 길이 조건
			double tmpLength = a.distance(b);
			double distance = JTS.orthodromicDistance(a, b, crs);

			// double km = distance / 1000;
			// double meters = distance - (km * 1000);

			if (tmpLength == 0) {
				CentroidPoint cPoint = new CentroidPoint();
				cPoint.add(a);
				cPoint.add(b);

				Coordinate coordinate = cPoint.getCentroid();
				GeometryFactory gFactory = new GeometryFactory();
				Geometry returnGeom = gFactory.createPoint(coordinate);

				// errSimpleFeaeture
				SimpleFeature errSimpleFeature = dConvertor.createErrSimpleFeature(simpleFeature.getID(), returnGeom.getGeometryN(i),
						UselessPoint.Type.USELESSPOINT.errName(), UselessPoint.Type.USELESSPOINT.errType());

				// detailReports
				DetailsValidatorResult detailsReport = new DetailsValidatorResult(simpleFeature.getID(), UselessPoint.Type.USELESSPOINT.errType(),
						UselessPoint.Type.USELESSPOINT.errName(), returnGeom.getGeometryN(i).getCoordinate().x, returnGeom.getInteriorPoint().getCoordinate().y);

				ErrorFeature errFeature = new ErrorFeature(errSimpleFeature, detailsReport);
				errFeatures.add(errFeature);
			}
			if (i < coorsSize - 2) {
				// 각도 조건
				Coordinate c = coors[i + 2];
				if (!a.equals2D(b) && !b.equals2D(c) && !c.equals2D(a)) {
					double tmpAngle = Angle.toDegrees(Angle.angleBetween(a, b, c));
					if (tmpAngle < 6) {
						GeometryFactory gFactory = new GeometryFactory();
						Geometry returnGeom = gFactory.createPoint(b);

						// errSimpleFeaeture
						SimpleFeature errSimpleFeature = dConvertor.createErrSimpleFeature(simpleFeature.getID(), returnGeom.getGeometryN(i),
								UselessPoint.Type.USELESSPOINT.errName(), UselessPoint.Type.USELESSPOINT.errType());
						// detailReports
						DetailsValidatorResult detailsReport = new DetailsValidatorResult(simpleFeature.getID(), UselessPoint.Type.USELESSPOINT.errType(),
								UselessPoint.Type.USELESSPOINT.errName(), returnGeom.getGeometryN(i).getCoordinate().x, returnGeom.getInteriorPoint()
										.getCoordinate().y);

						ErrorFeature errFeature = new ErrorFeature(errSimpleFeature, detailsReport);
						errFeatures.add(errFeature);
					}
				}
			}
		}
		if (errFeatures.size() != 0) {
			return errFeatures;
		} else {
			return null;
		}
	}
}
