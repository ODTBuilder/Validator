package com.git.gdsbuilder.validator.tmp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.DefaultFeatureCollection;
import org.json.simple.JSONArray;
import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.type.feature.ErrorFeature;
import com.git.gdsbuilder.type.layer.ErrorLayer;
import com.git.gdsbuilder.type.result.DetailsValidatorResult;
import com.git.gdsbuilder.validator.factory.AttributeValidator;
import com.git.gdsbuilder.validator.factory.AttributeValidatorImpl;

public class AttributeFixValidator implements Callable<ErrorLayer> {

	private SimpleFeatureCollection validatorSfc;
	private JSONArray notNullAtt;

	public AttributeFixValidator(SimpleFeatureCollection validatorSfc, JSONArray notNullAtt) {
		this.validatorSfc = validatorSfc;
		this.notNullAtt = notNullAtt;
	}

	@Override
	public ErrorLayer call() throws Exception {

		ErrorLayer errLayer = new ErrorLayer();
		DefaultFeatureCollection errSFC = new DefaultFeatureCollection();
		List<DetailsValidatorResult> dtReports = new ArrayList<DetailsValidatorResult>();

		AttributeValidator attributeValidator = new AttributeValidatorImpl();
		SimpleFeatureIterator simpleFeatureIterator = validatorSfc.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			ErrorFeature errFeature = attributeValidator.attributeFix(simpleFeature, notNullAtt);
			if (errFeature != null) {
				errSFC.add(errFeature.getErrFeature());
				dtReports.add(errFeature.getDtReport());
			} else {
				continue;
			}
		}
		if (errSFC.size() > 0) {
			errLayer.setErrFeatureCollection(errSFC);
			errLayer.setDetailsValidatorReport(dtReports);
			return errLayer;
		} else {
			return null;
		}
	}

}
