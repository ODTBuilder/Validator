package com.git.gdsbuilder.generalization.data;

import org.geotools.data.simple.SimpleFeatureCollection;

import com.git.gdsbuilder.generalization.opt.DTGeneralOption;

/**
 * 일반화하기전 레이어 정보객체
 * @author SG.Lee
 * @Date 2016.09
 * */
public class DTGeneralPreLayer {
	private SimpleFeatureCollection collection; //일반화 대상 레이어
	private DTGeneralOption option; //일반화 조건
	

	//생성자
	public DTGeneralPreLayer(SimpleFeatureCollection collection, DTGeneralOption option){
		this.collection=collection;
		this.option=option;
	}
	
	/**
	 * GET, SET
	 * @author SG.Lee
	 * @Date 2016.8.15
	 * */
	public SimpleFeatureCollection getCollection() {
		return collection;
	}
	public void setCollection(SimpleFeatureCollection collection) {
		this.collection = collection;
	}
	public DTGeneralOption getOption() {
		return option;
	}
	public void setOption(DTGeneralOption option) {
		this.option = option;
	}
}
