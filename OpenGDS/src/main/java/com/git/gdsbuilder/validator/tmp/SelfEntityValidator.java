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

public class SelfEntityValidator implements Callable<ErrorLayer> {

	private SimpleFeatureCollection validatorSfc;
//	private HttpServletRequest request;

	public SelfEntityValidator(SimpleFeatureCollection validatorSfc) {
		this.validatorSfc = validatorSfc;
		//this.request = request;
	}

	@Override
	public ErrorLayer call() throws Exception {

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
				List<ErrorFeature> errFeatures = geometryValidator.selfEntity(tmpSimpleFeatureI, tmpSimpleFeatureJ);
				if (errFeatures != null) {
					for (ErrorFeature tmp : errFeatures) {
						errSFC.add(tmp.getErrFeature());
						dtReports.add(tmp.getDtReport());
					}
				} else {
					continue;
				}
			}
		}
		if (errSFC.size() > 0 && dtReports.size() > 0) {
			ErrorLayer errLayer = new ErrorLayer(errSFC, dtReports);
			errLayer.setErrFeatureCollection(errSFC);
			errLayer.setDetailsValidatorReport(dtReports);
			return errLayer;
		} else {
			return null;
		}
	}
}
