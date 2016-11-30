package com.git.opengds.generalization.service;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.json.simple.JSONObject;

import com.git.gdsbuilder.generalization.data.DTGeneralAfLayer;
import com.git.gdsbuilder.generalization.impl.Generalization.GeneralizationOrder;
import com.git.gdsbuilder.generalization.opt.DTGeneralOption;





/**
 * 일반화와 관련된 데이터를 처리한다.
 * @author SG.Lee
 * @Date 2016.09.01
 * */
public interface GeneralizationService {
	
	
	/**
	 * simplification 일반화를 요청한다.
	 * @author SG.Lee
	 * @Date 2016.09.05
	 * @param collection - SimpleFeatureCollection
	 *        distance - Simplification 수직거리
	 * @return SimpleFeatureCollection - simplification 결과
	 * */
	public SimpleFeatureCollection simplification(SimpleFeatureCollection collection, double distance);
	
	
	/**
	 * 일반화를 요청한다.
	 * @author SG.Lee
	 * @Date 2016.09.05
	 * @param JSONObject - 일반화 처리를 위한 파라미터를 포함한다. 
	 *         		     { json:
	 *      		        		{ option : { elival : elimination 수치
	 *      		                             simdistance : Simplication 수치}
	 *       		         		  layerName : 레이어 이름
	 *      		          		  type : 레이어 타입
	 *      		          		  geojson : ol.layer객체 -> json객체
	 *      		          		}
	 *                   }
	 * @return JSONObject
	 *         { layerName : 레이어 이름
	 *           geojson : 일반화 결과 레이어(JSONObject)
	 *           topologyTable : TopologyTable객체
	 *           report : 레이어 결과
	 *          }
	 * @throws
	 * */
	public JSONObject generalizationRequest(JSONObject jsonObj);
	
	
	/**
	 * 일반화요청을 처리한다.
	 * @author SG.Lee
	 * @Date 2016.09.05
	 * @param collection - SimpleFeatureCollection 일반화 대상객체 
	 *        option - DTGeneralOption 일반화 옵션
	 *        order - GeneralizationOrder(Enum) 일반화 1순위
	 * @return DTGeneralAfLayer - 일반화후 레이어
	 * */
	public DTGeneralAfLayer generalization(SimpleFeatureCollection collection, DTGeneralOption option, GeneralizationOrder order);
}
 