package com.git.opengds.zipfile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.geotools.data.DataStoreFinder;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.FileDataStoreFactorySpi;
import org.geotools.data.Transaction;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.feature.SchemaException;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;

import com.git.gdsbuilder.convertor.DataConvertor;

public class ConvertGeojson 
{
	public SimpleFeatureCollection readShp(String filePath){

		File file = new File(filePath);
		System.out.println(file.getName().toString());
		System.out.println();
		Map<String, Object> map = new HashMap<String, Object>();
		ShapefileDataStore dataStore;
		String typeName;
		SimpleFeatureCollection collection = null;

		try {
			map.put("url", file.toURI().toURL());
			dataStore = (ShapefileDataStore) DataStoreFinder.getDataStore(map);

			typeName = dataStore.getTypeNames()[0];
			Charset UTF_8 = Charset.forName("EUC-KR");
			dataStore.setCharset(UTF_8);
			SimpleFeatureSource source = dataStore.getFeatureSource(typeName);
			Filter filter = Filter.INCLUDE;
			collection = source.getFeatures(filter);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return collection;
	}

	// create , Write SHP File
	public void createSHP(SimpleFeatureCollection simpleFeatureCollection, String filePath) throws IOException, SchemaException, NoSuchAuthorityCodeException, FactoryException {

		DataConvertor dataConvertor = new DataConvertor();
		
		FileDataStoreFactorySpi factory = new ShapefileDataStoreFactory();
		File file = new File(filePath);
		Map map = Collections.singletonMap("url", file.toURI().toURL());
		ShapefileDataStore myData = (ShapefileDataStore) factory.createNewDataStore(map);
		SimpleFeatureType featureType = simpleFeatureCollection.getSchema();
		Charset charset = Charset.forName("EUC-KR");

		myData.setCharset(charset);
		myData.createSchema(featureType);

		Transaction transaction = new DefaultTransaction("create");
		String typeName = myData.getTypeNames()[0];
		SimpleFeatureSource featureSource = myData.getFeatureSource(typeName);

		if (featureSource instanceof SimpleFeatureStore) {
			SimpleFeatureStore featureStore = (SimpleFeatureStore) featureSource;
			featureStore.setTransaction(transaction);
			try {
				featureStore.addFeatures(simpleFeatureCollection);
				transaction.commit();
			} catch (Exception e) {
				transaction.rollback();
			} finally {
				transaction.close();
			}
			System.out.println("Success!");
			System.exit(0);
		} else {
			System.out.println(typeName + " does not support read/write access");
			System.exit(1);
		}
	}
}