package com.git.gdsbuilder.validator.tmp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.DefaultFeatureCollection;
import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.type.feature.ErrorFeature;
import com.git.gdsbuilder.type.layer.ErrorLayer;
import com.git.gdsbuilder.type.result.DetailsValidatorResult;
import com.git.gdsbuilder.validator.factory.GeometryValidator;
import com.git.gdsbuilder.validator.factory.GeometryValidatorImpl;

public class EntityDuplicatedValidator implements Callable<ErrorLayer> {

	private SimpleFeatureCollection validatorSfc;

	public EntityDuplicatedValidator(SimpleFeatureCollection validatorSfc) {
		this.validatorSfc = validatorSfc;
	}

	@Override
	public ErrorLayer call() throws Exception {

		ErrorLayer errLayer = new ErrorLayer();
		DefaultFeatureCollection errSFC = new DefaultFeatureCollection();
		List<DetailsValidatorResult> dtReports = new ArrayList<DetailsValidatorResult>();

		List<SimpleFeature> tmpsSimpleFeatures = new ArrayList<SimpleFeature>();
		SimpleFeatureIterator simpleFeatureIterator = validatorSfc.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			tmpsSimpleFeatures.add(simpleFeature);
		}

		GeometryValidator geometryValidator = new GeometryValidatorImpl();

		int tmpSize = tmpsSimpleFeatures.size();
		for (int i = 0; i < tmpSize - 1; i++) {
			SimpleFeature tmpSimpleFeatureI = tmpsSimpleFeatures.get(i);
			for (int j = i + 1; j < tmpSize; j++) {
				SimpleFeature tmpSimpleFeatureJ = tmpsSimpleFeatures.get(j);
				ErrorFeature errFeature = geometryValidator.entityDuplicated(tmpSimpleFeatureI, tmpSimpleFeatureJ);
				if (errFeature != null) {
					errSFC.add(errFeature.getErrFeature());
					dtReports.add(errFeature.getDtReport());
				} else {
					continue;
				}
			}
		}
		if (errSFC.size() > 0 && dtReports.size() > 0) {
			errLayer.setErrFeatureCollection(errSFC);
			errLayer.setDetailsValidatorReport(dtReports);
			return errLayer;
		} else {
			return null;
		}
	}
}
