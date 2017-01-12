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

public class SelfEntityMultiValidator implements Callable<ErrorLayer> {

	SimpleFeatureCollection validatorSfc;
	SimpleFeatureCollection relationSfc;
//	private HttpServletRequest request;

	public SelfEntityMultiValidator(SimpleFeatureCollection validatorSfc, SimpleFeatureCollection relationSfc) {
		this.validatorSfc = validatorSfc;
		this.relationSfc = relationSfc;
		//this.request = request;
	}

	@Override
	public ErrorLayer call() throws Exception {

		ErrorLayer errLayer = new ErrorLayer();
		DefaultFeatureCollection errSFC = new DefaultFeatureCollection();
		List<DetailsValidatorResult> dtReports = new ArrayList<DetailsValidatorResult>();

		List<SimpleFeature> tmpSimpleFeaturesI = new ArrayList<SimpleFeature>();
		SimpleFeatureIterator simpleFeatureIteratorI = validatorSfc.features();
		while (simpleFeatureIteratorI.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIteratorI.next();
			tmpSimpleFeaturesI.add(simpleFeature);
		}

		List<SimpleFeature> tmpSimpleFeaturesJ = new ArrayList<SimpleFeature>();
		SimpleFeatureIterator simpleFeatureIteratorJ = relationSfc.features();
		while (simpleFeatureIteratorJ.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIteratorJ.next();
			tmpSimpleFeaturesJ.add(simpleFeature);
		}

		GeometryValidator geometryValidator = new GeometryValidatorImpl();
		int tmpSizeI = tmpSimpleFeaturesI.size();
		int tmpSizeJ = tmpSimpleFeaturesJ.size();
		for (int i = 0; i < tmpSizeI; i++) {
			SimpleFeature simpleFeatureI = tmpSimpleFeaturesI.get(i);
			for (int j = 0; j < tmpSizeJ; j++) {
				SimpleFeature simpleFeatureJ = tmpSimpleFeaturesJ.get(j);
				List<ErrorFeature> errFeatures = geometryValidator.selfEntity(simpleFeatureI, simpleFeatureJ);
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
			errLayer.setErrFeatureCollection(errSFC);
			errLayer.setDetailsValidatorReport(dtReports);
			return errLayer;
		} else {
			return null;
		}
	}

}
