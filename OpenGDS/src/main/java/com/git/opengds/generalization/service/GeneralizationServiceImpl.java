package com.git.opengds.generalization.service;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.json.simple.JSONObject;
import org.opengis.feature.simple.SimpleFeature;
import org.springframework.stereotype.Service;

import com.git.gdsbuilder.convertor.DataConvertor;
import com.git.gdsbuilder.generalization.data.DTGeneralAfLayer;
import com.git.gdsbuilder.generalization.data.Topology;
import com.git.gdsbuilder.generalization.data.TopologyTable;
import com.git.gdsbuilder.generalization.impl.Generalization;
import com.git.gdsbuilder.generalization.impl.Generalization.GeneralizationOrder;
import com.git.gdsbuilder.generalization.opt.DTGeneralOption;
import com.git.gdsbuilder.generalization.opt.DTGeneralOption.DTGeneralOptionType;
import com.git.gdsbuilder.generalization.rep.DTGeneralReport;
import com.git.opengds.generalization.domain.TopologyTableVO;
import com.git.opengds.generalization.domain.TopologyVO;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.simplify.TopologyPreservingSimplifier;




/**
 * 일반화 요청처리
 * @author SG.Lee
 * @Date
 * */
@Service
public class GeneralizationServiceImpl implements GeneralizationService {
	
	DataConvertor dataConvertor = new DataConvertor(); //데이터 변환 객체 생성
	
	//test
	@SuppressWarnings("null")
	@Override
	public SimpleFeatureCollection simplification(SimpleFeatureCollection collection, double distance){
		DefaultFeatureCollection returnCollection = new DefaultFeatureCollection();
		SimpleFeatureIterator iterator = collection.features();
		while(iterator.hasNext()){
			SimpleFeature feature = iterator.next();
			Geometry geom = (Geometry) feature.getDefaultGeometry();
			Geometry newGeom = TopologyPreservingSimplifier.simplify(geom, distance);
			SimpleFeature addFeature = SimpleFeatureBuilder.deep(feature);
			addFeature.setDefaultGeometry(newGeom);
			returnCollection.add(addFeature);
		}
		returnCollection.validate();
		return returnCollection;
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.git.opengds.generalization.service.GeneralizationService#generalizationRequest(org.json.simple.JSONObject)
	 */
	@SuppressWarnings("unchecked")
	public JSONObject generalizationRequest(JSONObject jsonObj){
		JSONObject genAftJSON = new  JSONObject();

		JSONObject json = (JSONObject) jsonObj.get("json");

		JSONObject option = (JSONObject) json.get("option"); // 일반화 옵션 JSONObject 변환
		String layerName = json.get("layerName").toString(); // 레이어 이름
		String geomType = json.get("type").toString(); // Geometry Type

		JSONObject layerJSON = new JSONObject();

		layerJSON = dataConvertor.stringToJSON(json.get("geojson").toString());

		SimpleFeatureCollection collection = null; // 객체생성
		DTGeneralAfLayer afLayer = null;
		try {
			collection = dataConvertor.converToSimpleFeatureCollection(layerJSON);// Geojson 객체 -> Geotools SimpleFeature객체생성
		} catch (SchemaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 옵션관련 데이터 변환(JSONObject -> DTGeneralOption)
		//수정해야됨
		DTGeneralOption generalOption = new DTGeneralOption();
		if (geomType.equals("MultiPolygon") || geomType.equals("Polygon")) {
			Double area = new Double(Double.valueOf((String) option.get("elival")));
			generalOption.put(DTGeneralOptionType.AREA, area * 1000);
		} else if (geomType.equals("MultiLineString")|| geomType.equals("LineString")) {
			Double length = new Double(Double.valueOf(option.get("elival").toString()));
			Double distance = new Double(Double.valueOf((String) option.get("simval")));
			generalOption.put(DTGeneralOptionType.LENGTH, length * 1000);
			generalOption.put(DTGeneralOptionType.DISTANCE, distance * 1000);
		} else
			afLayer = null;

		// 일반화
		afLayer = this.generalization(collection, generalOption,GeneralizationOrder.SIMPLIFICATION);

		// 일반화 결과
		JSONObject afLayerGeojson = new JSONObject();
  		afLayerGeojson = dataConvertor.convertToGeoJSON(afLayer.getCollection()); // 일반화 결과(JSON 객체)

		DTGeneralReport generalReport = null;
		generalReport = afLayer.getReport(); // 일반화 결과 레포트

		TopologyTable topologyTable = new TopologyTable();
		topologyTable = afLayer.getTopologyTable(); // 일반화 Topology Build 결과

		@SuppressWarnings("unused")
		TopologyTableVO tableVO = TopologyTableToTopologyTableVO(topologyTable);
		
		
		// 결과 JSON객체에 PUT
		genAftJSON.put("layerName", layerName); 
		genAftJSON.put("geojson", afLayerGeojson);
		genAftJSON.put("topologyTable", tableVO);
		genAftJSON.put("report", generalReport);

		return genAftJSON;
	}
	
	/* (non-Javadoc)
	 * @see com.git.opengds.generalization.service.GeneralizationService#generalization(org.geotools.data.simple.SimpleFeatureCollection, com.git.gdsbuilder.generalization.opt.DTGeneralOption, com.git.gdsbuilder.generalization.impl.Generalization.GeneralizationOrder)
	 */
	public DTGeneralAfLayer generalization(SimpleFeatureCollection collection, DTGeneralOption option, GeneralizationOrder order){
		//일반화 객체 생성후 일반화 요청
		Generalization generalization = new Generalization(collection, option, order);
		return generalization.getGeneralzation();
	}
	
	@SuppressWarnings("unused")
	private TopologyTableVO TopologyTableToTopologyTableVO(TopologyTable topologyTable){
		TopologyTableVO topologyTableVO = new TopologyTableVO();
		
		for(Topology topology : topologyTable){
			TopologyVO topologyVO = new TopologyVO();
			
			topologyVO.setObjID(topology.getObjID());
			topologyVO.setAlValue(topology.getAlValue());
			topologyVO.setFirstObjs(topology.getFirstObjs().toString());
			topologyVO.setLastObjs(topology.getLastObjs().toString());
			
			topologyTableVO.add(topologyVO);
		}
		return topologyTableVO;
	}
}
