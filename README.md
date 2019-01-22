OpenGDS / Validator (공간정보 검수 자바 라이브러리)
=======

OpenGDS/Validator는 국토공간정보연구사업 중, [공간정보 SW 활용을 위한 오픈소스 가공기술 개발]과제의 3차년도 연구성과입니다. 
2차원 공간정보 검수를 위한 오픈소스 자바 라이브러리이며 Geotools, GeoserverManager 등의 오픈소스 라이브러리를 사용하여 개발되었습니다.
또한 OpenGDS / Builder (공간자료 편집도구)와 연동하여 검수 요청 및 검수 결과 확인, 오류 데이터 편집이 가능합니다. 

감사합니다.<br>
공간정보기술(주) 연구소 <link>http://www.git.co.kr/<br>
OpenGeoDT 팀

특징
=====
- OpenGDS/Validator는 자사 GIS 통합 솔루션인 GeoDT 2.2 기술을 기반으로한 공간정보 검수 자바 라이브러리입니다.
- 웹 페이지상에서 공간정보의 기하학적/논리적 구조와 속성값에 대한 검수 기능을 제공합니다.
- OGC 표준을 준수하고 있으며 국가기본도, 지하시설물도, 임상도 작성 작업규정을 따르는 총 53여종의 검수기능을 제공합니다.
- 라이브러리 형태로 개발되어 사용자 요구사항에 따라 커스터 마이징 및 확장이 가능합니다.
- shp, ngi, dxf 파일을 업로드 후 검수하며 검수 결과는 shp 파일로 다운로드 받을 수 있습니다.

연구기관
=====
세부 책임 : 부산대학교 <link>http://www.pusan.ac.kr/

연구 책임 : 국토연구원 <link>http://www.krihs.re.kr/

Getting Started
=====
### 1. 환경 ###
- Java – OpenJDK 1.8.0.111 64 bit
- eclipse neon 
- Tomcat8.0.43 64bit

### 2. Clone or Download ###
- https://github.com/ODTBuilder/Validator 접속 후 Git 또는 SVN을 통해 Clone 하거나 zip 파일 형태로 소스코드 다운로드 

### 3. 소스코드 설치 및 프로젝트 실행 ###
- eclipse 실행 후 Project Import

### 4. Test 코드 작성 ###
- src/test/com/git/gdsbuilder/validator/ValidationTest.java 클래스 생성
- DTLayerCollection Validation : zip 형태의 다수 shp layer 파일 검수
<pre><code>// 1. read zip file
File zipFile = new File("D:\\digitalmap20.zip");
UnZipFile unZipFile = new UnZipFile("D:\\upzip");

try {
    unZipFile.decompress(zipFile, (long) 2); // cidx 2 : 국가기본도 구조화 shp 파일
} catch (Throwable e) {
    e.printStackTrace();
}

// 2. create DTLayerCollection
String epsg = "EPSG:4326";
QAFileParser parser = new QAFileParser(epsg, 2, "shp", unZipFile, null); // cidx 2 : 국가기본도 구조화 shp 파일
DTLayerCollectionList collectionList = parser.getCollectionList();

// 3. create QALayerType
String typeName = "건물";

// 4. set SelfEntity Option
GraphicMiss selfentity = new GraphicMiss();
selfentity.setOption(DMQAOptions.Type.SELFENTITY.getErrCode());

// 5. set Entityduplicated Option
GraphicMiss entityduplicated = new GraphicMiss();
entityduplicated.setOption(DMQAOptions.Type.ENTITYDUPLICATED.getErrCode());

List<GraphicMiss> graphicMissOptions = new ArrayList<>();
graphicMissOptions.add(selfentity);
graphicMissOptions.add(entityduplicated);

// 6. create QAOption
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

// 7. validation 
for (DTLayerCollection collection : collectionList) {
    CollectionValidator validator = new CollectionValidator(collection, null, qaLayerTypeList);
    ErrorLayer errLayer = validator.getErrLayer();
    try {
        // 8. Write error shp file
	SHPFileWriter.writeSHP(epsg, errLayer, "D:\\collectionErr_" + collection.getCollectionName() + ".shp");
    } catch (IOException | SchemaException | FactoryException e) {
	e.printStackTrace();
    }
}
</code></pre>
- DTLayer Validation : 단일 shp layer 파일 검수
<pre><code>// 1. read shp file
String epsg = "EPSG:4326";
SHPFileLayerParser parser = new SHPFileLayerParser();
SimpleFeatureCollection sfc = parser.getShpObject(epsg, new File("D:\\gis_osm_buildings.shp"));

// 2. create DTLayer
String layerId = "gis_osm_buildings";
DTLayer layer = new DTLayer();
layer.setLayerID(layerId);
layer.setSimpleFeatureCollection(sfc);

// 3. validation
LayerValidator validator = new LayerValidatorImpl(layer);
try {
    ErrorLayer errLayer = validator.validateSelfEntity(null);
    // 4. write error shp file
    SHPFileWriter.writeSHP(epsg, errLayer, "D:\\layerErr.shp");
    } catch (SchemaException | IOException | FactoryException e) {
        e.printStackTrace();
}
</code></pre>
- DTFeature Validation : 단일 feature 검수
- 파일 검수가 아닌 SimpleFeature 검수
<pre><code>// 1. create Geometry
GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);
WKTReader reader = new WKTReader(geometryFactory);
try {
    Geometry geom1 = geometryFactory.createGeometry(reader.read("POLYGON((10 10, 30 0, 40 10, 30 20, 10 10))"));
    Geometry geom2 = geometryFactory.createGeometry(reader.read("POLYGON((20 10, 20 40, 30 40, 30 0, 20 10))"));

    // 2. create SimpleFeature
    SimpleFeatureType sfType1 = DataUtilities.createType("DTFeature1", "the_geom:Polygon");
    SimpleFeature sf1 = SimpleFeatureBuilder.build(sfType1, new Object[] { geom1 }, "DTFeature1");

    SimpleFeatureType sfType2 = DataUtilities.createType("DTFeature2", "the_geom:Polygon");
    SimpleFeature sf2 = SimpleFeatureBuilder.build(sfType2, new Object[] { geom2 }, "DTFeature2");

    // 3. create DTFeature
    DTFeature feature1 = new DTFeature();
    feature1.setSimefeature(sf1);

    DTFeature feature2 = new DTFeature();
    feature2.setSimefeature(sf2);

    // 4. validation
    FeatureGraphicValidator validator = new FeatureGraphicValidatorImpl();
    List<ErrorFeature> errFeatures = validator.validateSelfEntity(feature1, feature2, null);

    ErrorLayer errLayer = new ErrorLayer();
    errLayer.addErrorFeatureList(errFeatures);

    // 5. write error shp file
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
</code></pre>
- 테스트 코드 작성 후 각각의 error shp file 생성 여부 확인 
- error shp file은 OpenGDS / Builder (공간자료 편집도구) 또는 타 GIS 편집 툴에서 확인 가능

사용 라이브러리
=====
1. GeoTools 16.5 (LGPL) http://www.geotools.org/
2. JTS 1.12 (Eclipse Public License 1.0, Eclipse Distribution License 1.0 (a BSD Style License)) https://www.locationtech.org/
2. ApachePOI 3.14 (Apache License 2.0) http://poi.apache.org
3. ApacheCommons 1.3.3 (Apache License 2.0) commons.apache.org/proper/commons-logging/
4. JACKSON 1.9.7 (Apache License (AL) 2.0, LGPL 2.1)
5. JSON 20160212 (MIT License)
6. kabeja 0.4 (Apache Software License 2.0) http://kabeja.sourceforge.net/index.html
7. kabeja-svg 0.4 (Apache Software License 2.0) http://kabeja.sourceforge.net/index.html
8. gt-datastore-ngi 1.0.0 (GNU Library or Lesser General Public License version 2.0 (LGPLv2)) http://www.mangosystem.com/

Mail
====
Developer : SG.LEE
ghre55@git.co.kr
