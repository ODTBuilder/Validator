package com.git.opengds.zipfile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.FileUtils;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.json.simple.JSONObject;

import com.git.gdsbuilder.convertor.DataConvertor;
//import java.util.zip.ZipFile;

public class UnzipFile {

	private static final String OUTPUT_DIR = "d:\\"; 
	private static final int BUFFER_SIZE = 8192;

	@SuppressWarnings("unchecked")
	public JSONObject readFile(File file) throws IOException{

		DataConvertor dataConvertor = new DataConvertor();
		JSONObject jsonObject = new JSONObject();
		JSONObject subJsonObject = new JSONObject();

		String name = file.getName();  // 파일 이름.확장자 
		int comma = name.lastIndexOf(".");
		String fileName = name.substring(0,comma);  // 파일 이름
		Charset euc_kr = Charset.forName("EUC-KR");
		ZipFile zipFile = new ZipFile(file,euc_kr); 

		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		List<ZipEntry> zipEntryName = new Vector<ZipEntry>(); //파일명 리스트
		List<String> entryName = new ArrayList<String>(); // 확장자 제거한 리스트
		ArrayList<String> arrayList = new ArrayList<String>();  // 중복 제거한 arrayList
		int count = 0;

		try {
			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();
				InputStream stream = zipFile.getInputStream(entry);
				File targetFile = new File(OUTPUT_DIR, fileName);// OUTPUT_DIR 경로에 fileName으로 파일만들기
				FileUtils.forceMkdir(targetFile);  // 파일 생성
				extractEntry(entry, stream, targetFile);  // 한개 엔트리의 압축 풀기
				zipEntryName.add(entry);
			}

			// 확장자 제거 후 배열에 저장
			for (int i = 0; i < zipEntryName.size(); i++) {
				ZipEntry fullName = zipEntryName.get(i);
				String stringName = fullName.getName();
				int intComma = stringName.lastIndexOf(".");
				String shortName = stringName.substring(0,intComma);
				entryName.add(shortName);
			}

			// 배열 중복 제거
			for (int i = 0; i < entryName.size(); i++) {
				if(! arrayList.contains(entryName.get(i))){
					arrayList.add(entryName.get(i));
				}
			}

			// shp, dbf, shx파일 여부 검사
			for (int i = 0; i < arrayList.size(); i++) {
				Boolean flagShp = false;
				Boolean flagShx = false;
				Boolean flagDbf = false;
				for (int j = 0; j < zipEntryName.size(); j++) {
					String arrayListName = arrayList.get(i);
					ZipEntry zipEntry = zipEntryName.get(j);
					String strZipEntry = zipEntry.getName();

					if(strZipEntry.equals(arrayListName+".shp")){
						flagShp = true;
					}
					if(strZipEntry.equals(arrayListName+".shx")){
						flagShx = true;
					}
					if(strZipEntry.equals(arrayListName+".dbf")){
						flagDbf = true;
					}
					if(flagShp == true && flagShx == true && flagDbf==true){
						flagShp = false;
						flagShx = false;
						flagDbf = false;
						try {
							count++;
							ConvertGeojson geojson = new  ConvertGeojson();
							String shpFile = arrayListName+".shp";
							String entryString =  OUTPUT_DIR + fileName + "\\" + shpFile;
							SimpleFeatureCollection featureCollection = geojson.readShp(entryString); // shp 파일 읽기
							JSONObject jsonObject1 = dataConvertor.convertToGeoJSON(featureCollection);  // jsonobject변환
							subJsonObject.put(arrayListName, jsonObject1); // {파일 이름 : feature}
						} catch (Exception e) {
							System.out.println("test");
							continue;
						}
					}
				}
			}
		}finally{
			zipFile.close();		
		}
		int total = arrayList.size();
		int result = total - count;
		System.out.println("실패한 shp 파일 수 : " + result);
		jsonObject.put("layers", subJsonObject);
		//System.out.println("결과: " + jsonObject);
		return jsonObject;
	}

	// ZIP 파일의 한개 엔트리의 압축을 푼다.
	// targetFile : 압축 풀린 파일 경로
	@SuppressWarnings("unused")
	private static FileOutputStream extractEntry(final ZipEntry entry, InputStream inputStream, File targetFile) throws IOException{
		String filePath = entry.getName();  
		String targetFilePath = targetFile.getAbsolutePath();
		String exractedFile = targetFilePath + "\\" + filePath;
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(exractedFile);  //  해제할 각각 파일의 outputstream을 생성
			final byte[] buf = new byte[BUFFER_SIZE];
			int read = 0;
			int length;
			while((length = inputStream.read(buf, 0, buf.length))>=0){
				fos.write(buf, 0, length);
			} 
		}catch (IOException ioex) {
			fos.close();  // 하나
		}
		return fos;
	}
}
