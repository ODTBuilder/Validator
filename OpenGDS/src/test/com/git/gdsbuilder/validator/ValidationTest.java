package com.git.gdsbuilder.validator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.geotools.data.DataUtilities;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;

import com.git.gdsbuilder.file.writer.SHPFileWriter;
import com.git.gdsbuilder.parser.file.QAFileParser;
import com.git.gdsbuilder.type.dt.collection.DTLayerCollection;
import com.git.gdsbuilder.type.dt.collection.DTLayerCollectionList;
import com.git.gdsbuilder.type.dt.feature.DTFeature;
import com.git.gdsbuilder.type.dt.layer.DTLayer;
import com.git.gdsbuilder.type.validate.error.ErrorFeature;
import com.git.gdsbuilder.type.validate.error.ErrorLayer;
import com.git.gdsbuilder.type.validate.layer.QALayerType;
import com.git.gdsbuilder.type.validate.layer.QALayerTypeList;
import com.git.gdsbuilder.type.validate.option.QAOption;
import com.git.gdsbuilder.type.validate.option.specific.GraphicMiss;
import com.git.gdsbuilder.type.validate.option.type.DMQAOptions;
import com.git.gdsbuilder.validator.collection.CollectionValidator;
import com.git.gdsbuilder.validator.feature.FeatureGraphicValidator;
import com.git.gdsbuilder.validator.feature.FeatureGraphicValidatorImpl;
import com.git.gdsbuilder.validator.fileReader.UnZipFile;
import com.git.gdsbuilder.validator.fileReader.shp.parser.SHPFileLayerParser;
import com.git.gdsbuilder.validator.layer.LayerValidator;
import com.git.gdsbuilder.validator.layer.LayerValidatorImpl;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class ValidationTest {

	public static void main(String[] args) {

		// 1. DTLayerCollection Validation
		layerCollectionValidation();
		// 2. DTLayer Validation
		layerValidation();
		// 3. DTFeature Validation
		featureValidation();
	}

	// 1. DTLayerCollection Validation
	private static void layerCollectionValidation() {

		// read zip file
		File zipFile = new File("D:\\digitalmap20.zip");
		UnZipFile unZipFile = new UnZipFile("D:\\upzip");
		try {
			unZipFile.decompress(zipFile, (long) 2); // cidx 2 : 수치지도 구조화 shp 파일
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// create DTLayerCollection
		String epsg = "EPSG:4326";
		QAFileParser parser = new QAFileParser(epsg, 2, "shp", unZipFile, null); // cidx 2 : 수치지도 구조화 shp 파일
		DTLayerCollectionList collectionList = parser.getCollectionList();

		// create QALayerType
		String typeName = "건물";

		GraphicMiss selfentity = new GraphicMiss();
		selfentity.setOption(DMQAOptions.Type.SELFENTITY.getErrCode());

		GraphicMiss entityduplicated = new GraphicMiss();
		entityduplicated.setOption(DMQAOptions.Type.ENTITYDUPLICATED.getErrCode());

		List<GraphicMiss> graphicMissOptions = new ArrayList<>();
		graphicMissOptions.add(selfentity);
		graphicMissOptions.add(entityduplicated);

		QAOption option = new QAOption();
		option.setName(typeName);
		option.setGraphicMissOptions(graphicMissOptions);

		QALayerType layerType = new QALayerType();
		layerType.setName(typeName);

		List<String> layerIdList = new ArrayList<>();
		layerIdList.add("B0010000");
		layerIdList.add("B0020000");
		layerType.setLayerIDList(layerIdList);

		layerType.setOption(option);

		QALayerTypeList qaLayerTypeList = new QALayerTypeList();
		qaLayerTypeList.add(layerType);

		for (DTLayerCollection collection : collectionList) {
			CollectionValidator validator = new CollectionValidator(collection, null, qaLayerTypeList);
			ErrorLayer errLayer = validator.getErrLayer();
			try {
				SHPFileWriter.writeSHP(epsg, errLayer, "D:\\collectionErr_" + collection.getCollectionName() + ".shp");
			} catch (IOException | SchemaException | FactoryException e) {
				e.printStackTrace();
			}
		}
	}

	// 2. DTLayer Validation
	private static void layerValidation() {

		// read shp file
		String epsg = "EPSG:4326";
		SHPFileLayerParser parser = new SHPFileLayerParser();
		SimpleFeatureCollection sfc = parser.getShpObject(epsg, new File("D:\\gis_osm_buildings.shp"));

		// create DTLayer
		String layerId = "gis_osm_buildings";
		DTLayer layer = new DTLayer();
		layer.setLayerID(layerId);
		layer.setSimpleFeatureCollection(sfc);

		// validation
		LayerValidator validator = new LayerValidatorImpl(layer);
		try {
			ErrorLayer errLayer = validator.validateSelfEntity(null);
			// write error shp file
			SHPFileWriter.writeSHP(epsg, errLayer, "D:\\layerErr.shp");
		} catch (SchemaException | IOException | FactoryException e) {
			e.printStackTrace();
		}
	}

	// 3. DTFeature Validation
	private static void featureValidation() {

		// create DTFeature
		GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);
		WKTReader reader = new WKTReader(geometryFactory);
		try {
			Geometry geom1 = geometryFactory.createGeometry(reader.read("POLYGON((10 10, 30 0, 40 10, 30 20, 10 10))"));
			Geometry geom2 = geometryFactory.createGeometry(reader.read("POLYGON((20 10, 20 40, 30 40, 30 0, 20 10))"));

			SimpleFeatureType sfType1 = DataUtilities.createType("DTFeature1", "the_geom:Polygon");
			SimpleFeature sf1 = SimpleFeatureBuilder.build(sfType1, new Object[] { geom1 }, "DTFeature1");

			SimpleFeatureType sfType2 = DataUtilities.createType("DTFeature2", "the_geom:Polygon");
			SimpleFeature sf2 = SimpleFeatureBuilder.build(sfType2, new Object[] { geom2 }, "DTFeature2");

			DTFeature feature1 = new DTFeature();
			feature1.setSimefeature(sf1);

			DTFeature feature2 = new DTFeature();
			feature2.setSimefeature(sf2);

			// validation
			FeatureGraphicValidator validator = new FeatureGraphicValidatorImpl();
			List<ErrorFeature> errFeatures = validator.validateSelfEntity(feature1, feature2, null);

			ErrorLayer errLayer = new ErrorLayer();
			errLayer.addErrorFeatureList(errFeatures);

			// write error shp file
			String epsg = "EPSG:4326";
			SHPFileWriter.writeSHP(epsg, errLayer, "D:\\featureErr.shp");

		} catch (ParseException | SchemaException e) {
			e.printStackTrace();
		} catch (NoSuchAuthorityCodeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FactoryException e) {
			e.printStackTrace();
		}
	}
}
