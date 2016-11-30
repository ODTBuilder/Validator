package com.git.gdsbuilder.validator.factory;

import java.util.Iterator;

import org.geotools.feature.SchemaException;
import org.json.simple.JSONArray;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.convertor.DataConvertor;
import com.git.gdsbuilder.type.feature.ErrorFeature;
import com.git.gdsbuilder.type.result.DetailsValidatorResult;
import com.git.gdsbuilder.type.validatorOption.AttributeFix;
import com.git.gdsbuilder.type.validatorOption.Z_ValueAmbiguous;
import com.vividsolutions.jts.geom.Geometry;

public class AttributeValidatorImpl implements AttributeValidator {

	@Override
	public ErrorFeature attributeFix(SimpleFeature simpleFeature, JSONArray notNullAtt) throws SchemaException {

		boolean isError = false;
		if (notNullAtt != null) {
			Iterator iterator = notNullAtt.iterator();
			while (iterator.hasNext()) {
				String notNullKey = (String) iterator.next();
				Object value = simpleFeature.getAttribute(notNullKey);
				if (value != null) {
					if (value.toString().equals("")) {
						isError = true;
					} else {
						Property property = simpleFeature.getProperty(notNullKey);
						String type = property.getType().toString();
						int firstIndex = type.indexOf("<");
						int lastIndex = type.lastIndexOf(">");
						String propertyType = type.substring(firstIndex + 1, lastIndex);
						String valueType = value.getClass().getSimpleName();
						if (!propertyType.equals(valueType)) {
							isError = true;
						}
					}
				} else {
					isError = true;
				}
			}
		}
		if (isError) {
			DataConvertor convertService = new DataConvertor();
			String errFeatureID = simpleFeature.getID();
			Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
			Geometry returnGeo = geometry.getInteriorPoint();

			// SimpleFeature
			SimpleFeature errSimpleFeature = convertService.createErrSimpleFeature(errFeatureID, returnGeo, AttributeFix.Type.ATTRIBUTEFIX.errName(),
					AttributeFix.Type.ATTRIBUTEFIX.errType());

			// DetailReport
			DetailsValidatorResult detailReport = new DetailsValidatorResult(errFeatureID, AttributeFix.Type.ATTRIBUTEFIX.errType(),
					AttributeFix.Type.ATTRIBUTEFIX.errName(), geometry.getCoordinate().x, geometry.getCoordinate().y);

			ErrorFeature errorFeature = new ErrorFeature(errSimpleFeature, detailReport);
			return errorFeature;
		} else {
			return null;
		}
	}

	@Override
	public ErrorFeature z_valueAmbiguous(SimpleFeature simpleFeature, JSONArray notNullAtt) throws SchemaException {

		boolean isError = false;
		if (notNullAtt != null) {
			int attributeCount = simpleFeature.getAttributeCount();
			Iterator iterator = notNullAtt.iterator();
			if (attributeCount > 1) {
				while (iterator.hasNext()) {
					String attribute = (String) iterator.next();
					for (int i = 1; i < attributeCount; i++) {
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
			SimpleFeature errSimpleFeature = convertService.createErrSimpleFeature(errFeatureID, returnGeo, Z_ValueAmbiguous.Type.Z_VALUEAMBIGUOUS.errName(),
					Z_ValueAmbiguous.Type.Z_VALUEAMBIGUOUS.errType());

			// DetailReport
			DetailsValidatorResult detailReport = new DetailsValidatorResult(errFeatureID, Z_ValueAmbiguous.Type.Z_VALUEAMBIGUOUS.errType(),
					Z_ValueAmbiguous.Type.Z_VALUEAMBIGUOUS.errName(), geometry.getCoordinate().x, geometry.getCoordinate().y);

			ErrorFeature errorFeature = new ErrorFeature(errSimpleFeature, detailReport);
			return errorFeature;
		} else {
			return null;
		}
	}

}
