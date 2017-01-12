package com.git.gdsbuilder.generalization.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.DefaultFeatureCollection;
import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.generalization.data.DTGeneralAfLayer;
import com.git.gdsbuilder.generalization.data.DTGeneralPreLayer;
import com.git.gdsbuilder.generalization.data.Topology;
import com.git.gdsbuilder.generalization.data.TopologyTable;
import com.git.gdsbuilder.generalization.opt.DTGeneralOption;
import com.git.gdsbuilder.generalization.rep.DTGeneralReport;
import com.git.gdsbuilder.generalization.rep.DTGeneralReport.DTGeneralReportType;
import com.git.gdsbuilder.generalization.rep.type.DTGeneralNums;
import com.git.gdsbuilder.generalization.rep.type.DTGeneralNums.DTGeneralNumsType;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

/**
 * 일반화를 처리한다.
 * @author SG.Lee
 * @Date 2016.10.24
 * */
public class Generalization {
	/**
	 * Generalization 순서 Enum
	 * @author SG.Lee
	 * @Date 2016.10.24
	 * */
	public enum GeneralizationOrder {
		SIMPLIFICATION("simplefication"),
		ELIMINATION("elimination"),
		UNKNOWN(null);

		private final String typeName;

		private GeneralizationOrder(String typeName) {
			this.typeName = typeName;
		}

		public static GeneralizationOrder get(String typeName) {
			for (GeneralizationOrder type : values()) {
				if(type == UNKNOWN)
					continue;
				if(type.typeName.equals(typeName))
					return type;
			}
			return UNKNOWN;
		}
		public String getTypeName(){
			return this.typeName;
		}
	};
	
	//일반화 대상
	protected SimpleFeatureCollection collection;
	
	//일반화 옵션 
	protected DTGeneralOption option;
	
	//일반화 순서
	private GeneralizationOrder order;
	
	
	public Generalization(SimpleFeatureCollection collection, DTGeneralOption option, GeneralizationOrder order){
		this.collection = collection;
		this.option = option;
		this.order = order;
	}
	
	public Generalization(DTGeneralPreLayer preLayer, GeneralizationOrder order){
		if(preLayer!=null){
			this.collection = preLayer.getCollection();
			this.option = preLayer.getOption();
			this.order = order;
		}
	}
	
	public Generalization(DTGeneralPreLayer preLayer){
		if(preLayer!=null){
			this.collection = preLayer.getCollection();
			this.option = preLayer.getOption();
		}
	}
	
	class ConnectObject{
		private DefaultFeatureCollection connectCollection = new DefaultFeatureCollection();
		private Simplification simplification;
		
		public ConnectObject() {
			// TODO Auto-generated constructor stub
		}
		
		public ConnectObject(SimpleFeatureCollection collection) {
			SimpleFeatureIterator iterator = collection.features();
			while (iterator.hasNext()) {
				SimpleFeature feature = iterator.next();
				connectCollection.add(feature);
			}
		}
		
		public void add(SimpleFeature feature){
			connectCollection.add(feature);
		}
		
		public DefaultFeatureCollection getConnectObject(){
			return connectCollection;
		}
		
		public DTGeneralAfLayer getGeneralzation(){
			DTGeneralAfLayer connectAFLayer = new DTGeneralAfLayer();

			simplification = (new Simplification(new DTGeneralPreLayer(connectCollection,option)));
			DTGeneralAfLayer dSimpleAfLayer = simplification.getSimplification();
			DTGeneralReport dSimpleReport = dSimpleAfLayer.getReport();
			
			
			
			return connectAFLayer;
		}
	}
	
	class DisConnectObject{
		private DefaultFeatureCollection disConnectCollection = new DefaultFeatureCollection();
		private Simplification simplification;
		private Elimination elimination;

		
		public DisConnectObject(){
			// TODO Auto-generated constructor stub
		}
		
		public DisConnectObject(SimpleFeatureCollection collection) {
			SimpleFeatureIterator iterator = collection.features();
			while (iterator.hasNext()) {
				SimpleFeature feature = iterator.next();
				disConnectCollection.add(feature);
			}
		}
		
		public void add(SimpleFeature feature){
			disConnectCollection.add(feature);
		}
		
		public DefaultFeatureCollection getDisConnectCollection(){
			return disConnectCollection;
		}
		
		public DTGeneralAfLayer getGeneralzation(){
			DTGeneralAfLayer disConnectAFLayer = new DTGeneralAfLayer();
			
			
			return disConnectAFLayer;
		}
		
	}
	
	
	@SuppressWarnings("static-access")
	public DTGeneralAfLayer getGeneralzation(){
		DTGeneralAfLayer returnLayer = new DTGeneralAfLayer();
		DefaultFeatureCollection dangleCollection = new DefaultFeatureCollection();
		DefaultFeatureCollection allCollection = new DefaultFeatureCollection();

		Vector<SimpleFeature> simpleFeatures = new Vector<SimpleFeature>();

		if (this.collection != null) {
			SimpleFeatureIterator iterator = this.collection.features();
			while (iterator.hasNext()) {
				SimpleFeature feature = iterator.next();
				simpleFeatures.add(feature);
			}
		} else
			returnLayer = null;

		
		
		
		//TopologyTable 생성
		if (simpleFeatures.size() != 0) {
			TopologyTable topologyTable = new TopologyTable();
			
			for (int i = 0; i < simpleFeatures.size(); i++) {
				//Feature간 Topology 생성
				Topology topology = new Topology();
				List<String> firstObjs = new ArrayList<String>();
				List<String> lastObjs = new ArrayList<String>();
				
				topology.setObjID(simpleFeatures.get(i).getID());
				
				///////////////////////////////////////////////////////////////////////////////////
				boolean startFlag = false;
				boolean endFlag = false;
				Geometry mainGeom = (Geometry) simpleFeatures.get(i).getDefaultGeometry();
				
				//길이, 영역
				///////////////////////////
				Double length = mainGeom.getLength()/1000;
				topology.setAlValue(length.valueOf(Math.round(length*100d)));
				 
				
				
				Coordinate[] mainCoord = mainGeom.getCoordinates();
				for (int j = 0; j < simpleFeatures.size(); j++) {
					if (i != j) {
						Geometry subGeom = (Geometry) simpleFeatures.get(j).getDefaultGeometry();
						/*if (mainCoord[0].equals2D(mainCoord[mainCoord.length - 1])) {
							allCollection.add(simpleFeatures.get(i));}*/ 
//						else {
							Geometry startGeom = new GeometryFactory().createPoint(mainCoord[0]);
							Geometry endGeom = new GeometryFactory().createPoint(mainCoord[mainCoord.length - 1]);
							if (endGeom.isWithinDistance(subGeom, 2000)) {
								endFlag = true;
								lastObjs.add(simpleFeatures.get(j).getID());
							} 
							if (startGeom.isWithinDistance(subGeom, 2000)) {
								startFlag = true;
								firstObjs.add(simpleFeatures.get(j).getID());
							}
//						}
					}
				}
				if(topology!=null){
					topology.setFirstObjs(firstObjs);
					topology.setLastObjs(lastObjs);
					topologyTable.add(topology);
				}
				
				if (startFlag == true && endFlag == true) {
					dangleCollection.add(simpleFeatures.get(i));
				} else
					allCollection.add(simpleFeatures.get(i));
			}
			
			returnLayer.setTopologyTable(topologyTable);
		} else
			returnLayer = null;
		
		
		
		
		if(this.order.equals("")||this.order==null){
			return null;
		}
		else{
			if(this.order.equals(GeneralizationOrder.SIMPLIFICATION)){
				if(this.order.equals("")||this.order==null){
					return null;
				}
				else{
					if(this.order.equals(GeneralizationOrder.SIMPLIFICATION)){
						
						
						Simplification dSimplification = (new Simplification(new DTGeneralPreLayer(dangleCollection,this.option)));
						DTGeneralAfLayer dSimpleAfLayer = dSimplification.getSimplification();
						DTGeneralReport dSimpleReport = dSimpleAfLayer.getReport();
						
						
						Simplification aSimplification = (new Simplification(new DTGeneralPreLayer(allCollection,this.option)));
						DTGeneralAfLayer aSimpleAfLayer = aSimplification.getSimplification();
						DTGeneralReport aSimpleReport = aSimpleAfLayer.getReport();
						SimpleFeatureCollection aSimpleFeatureCollection = aSimpleAfLayer.getCollection();
						
						Elimination elimination = (new Elimination(new DTGeneralPreLayer(aSimpleFeatureCollection, this.option)));
						DTGeneralAfLayer eliAfLayer = elimination.getElimination();
						DTGeneralReport eliReport = eliAfLayer.getReport();
						
						DTGeneralNums preNums = new DTGeneralNums();
						DTGeneralNums afNums = new DTGeneralNums();
						
						int dSimplePreNums = dSimpleReport.getPreResultNum().get(DTGeneralNumsType.POINTNUM);
						int aSimplePreNums = aSimpleReport.getPreResultNum().get(DTGeneralNumsType.POINTNUM);
						int dSimpleAfNums = dSimpleReport.getAfResultNum().get(DTGeneralNumsType.POINTNUM);
						int aSimpleAfNums = aSimpleReport.getAfResultNum().get(DTGeneralNumsType.POINTNUM);
						
					
						DefaultFeatureCollection returnCollection = new DefaultFeatureCollection();
						
						SimpleFeatureCollection dSimpleAfCollection = dSimpleAfLayer.getCollection();
						SimpleFeatureCollection aSimpleAfCollection = eliAfLayer.getCollection();
						
						if(dSimpleAfCollection!=null){
							SimpleFeatureIterator dIterator = dSimpleAfCollection.features();
							while(dIterator.hasNext()){
								returnCollection.add(dIterator.next());
							}
						}
						
						if(aSimpleAfCollection!=null){
							SimpleFeatureIterator aIterator = aSimpleAfCollection.features();
							while(aIterator.hasNext()){
								returnCollection.add(aIterator.next());
							}
						}
						
						
						
						preNums.put(DTGeneralNumsType.POINTNUM, dSimplePreNums+aSimplePreNums);
						preNums.put(DTGeneralNumsType.ENTITYNUM, simpleFeatures.size());
						afNums.put(DTGeneralNumsType.POINTNUM, dSimpleAfNums+aSimpleAfNums);
						try {
							afNums.put(DTGeneralNumsType.ENTITYNUM, returnCollection.getCount());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						DTGeneralReport returnReport = new DTGeneralReport(preNums, afNums);
						
						//ReturnLayer Create
						returnLayer.setCollection(returnCollection);
						returnLayer.setReport(returnReport);
					}
					else if(this.option.equals(GeneralizationOrder.ELIMINATION.getTypeName())){ 
						DTGeneralAfLayer eliAfLayer = getElimination();
						DTGeneralReport eliReport = eliAfLayer.getReport();
						SimpleFeatureCollection simpleFeatureCollection = eliAfLayer.getCollection();
						
						
						Simplification simplification = (new Simplification(new DTGeneralPreLayer(simpleFeatureCollection, this.option)));
						
						DTGeneralAfLayer simpleAfLayer = simplification.getSimplification();
						DTGeneralReport simpleReport = simpleAfLayer.getReport();
						
						DTGeneralNums preNums = new DTGeneralNums();
						DTGeneralNums afNums = new DTGeneralNums();
						
						preNums.put(DTGeneralNumsType.POINTNUM, simpleReport.getNums(DTGeneralReportType.PREREPORT).get(DTGeneralNumsType.POINTNUM));
						preNums.put(DTGeneralNumsType.ENTITYNUM, eliReport.getNums(DTGeneralReportType.PREREPORT).get(DTGeneralNumsType.ENTITYNUM));
						afNums.put(DTGeneralNumsType.POINTNUM, simpleReport.getNums(DTGeneralReportType.AFREPORT).get(DTGeneralNumsType.POINTNUM));
						afNums.put(DTGeneralNumsType.ENTITYNUM, eliReport.getNums(DTGeneralReportType.AFREPORT).get(DTGeneralNumsType.ENTITYNUM));
						
						DTGeneralReport returnReport = new DTGeneralReport(preNums, afNums);
						
						returnLayer.setCollection(simpleAfLayer.getCollection());
						returnLayer.setReport(returnReport);
					}
					else
						return null;
				}
			}
		}
		return returnLayer;
	}
	
	private DTGeneralAfLayer getSimplification(){
		return (new Simplification(new DTGeneralPreLayer(this.collection, this.option)).getSimplification());
	}
	
	private DTGeneralAfLayer getElimination(){
		return (new Elimination(new DTGeneralPreLayer(this.collection, this.option)).getElimination());
	}
}
