package com.git.opengds.generalization.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class TopologyTableVO extends ArrayList<TopologyVO> implements Serializable{
	
	private static final long serialVersionUID = 1L;


	public TopologyTableVO(){}
	
	
	/** 
	 * Topolgy List -> TopologyTable
	 * @param topologys 
	 */
	public TopologyTableVO(ArrayList<TopologyVO> topologys){
		if(topologys.size()!=0){
			for(TopologyVO topology : topologys){
				super.add(topology);
			}
		}
	}
	public boolean add(TopologyVO topology){
		return super.add(topology);
	}
	
		
	public void allRemove(){
		super.clear();
	}

}
