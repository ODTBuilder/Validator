$(document).ready(function() {
    setGenBodyDivSize();
    setSeparateMap();
});

var map1;
var map2;
var view;




function setGenBodyDivSize(){
    var bodyDiv = document.getElementById('bodyDiv');
    
    var width;
    var height;
    
    width = bodyDiv.offsetWidth;
    height = bodyDiv.offsetHeight;
    
    document.getElementById('bfLayer').style.height = height/100*76;
    document.getElementById('afLayer').style.height = height/100*76;
}


/**
 * 검수전, 검수후 맵에 대한 정보를 입력한다.
 * @author seulgi.lee
 * @date 2016. 02.
 * @param trgt1 - 검수전 지도 div ID
 * @param trgt2 - 검수후 지도 div ID
 * @returns 
 */
function setSeparateMap() {
	var tile = new ol.layer.Tile({
	    source : new ol.source.BingMaps({
        		key : 'AtZS5HHiM9JIaF1cUX-x-zQT_97S8YkWkjxDowNNUGD1D8jWUtgVmdaxsiitNQbo',
        		imagerySet : "Road"
	          })
	});

	view = new ol.View({
		center : [ 0, 0 ],
		zoom : 1,
		projection : "EPSG:3857"
	});

	map1 = new ol.Map({
		target : "bfLayer",
		layers : [ tile ],
		view : view
	});

	map2 = new ol.Map({
		target : "afLayer",
		layers : [ tile ],
		view : map1.getView()
	});
}



