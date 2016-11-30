package com.git.opengds.zipfile.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.git.gdsbuilder.convertor.DataConvertor;
import com.git.opengds.zipfile.CreateFile;
import com.git.opengds.zipfile.UnzipFile;

@Controller("UploadController")
@RequestMapping("/upload")
public class UploadController{
	
	UnzipFile unzipFile = new UnzipFile();
	CreateFile createFile = new CreateFile();
	
	@RequestMapping(value = "/upload.ajax")
	public String geoserverAddLoadAjax(HttpServletRequest request,@RequestBody JSONObject geo) throws Throwable {
		
		JSONParser parser = new JSONParser();
		
//		Object obj = parser.parse(new FileReader("d:\\makeshphwp.txt","UTF8"));
//		Object obj = parser.parse(new BufferedReader(new InputStreamReader(new FileInputStream("d:\\논.txt"),"EUC-KR")));
//		Object obj = parser.parse(new BufferedReader(new InputStreamReader(new FileInputStream("d:\\makeshphwp.txt"))));
		
		String obj = "{\"layers\":{\"layer3\":{\"feature\":{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"id\":\"layer3.1\",\"geometry\":{\"type\":\"MultiPolygon\",\"coordinates\":[[[[4074939.676811924,8168834.896024106],[4251050.58998097,6662108.194466712],[6266542.151804497,6495781.220918168],[5943672.144327913,8041643.680957573],[4074939.676811924,8168834.896024106]]]]},\"properties\":{\"id\":\"01\",\"이름\":\"빌딩01\"}},{\"type\":\"Feature\",\"id\":\"layer3.2\",\"geometry\":{\"type\":\"MultiPolygon\",\"coordinates\":[[[[8272249.774007522,8188402.775265112],[7558022.181710836,6603404.556743696],[9436538.588847328,6975194.262322794],[8272249.774007522,8188402.775265112]]]]},\"properties\":{\"id\":\"02\",\"이름\":\"빌딩02\"}}]},\"attribute\":{\"id\":\"Integer\",\"이름\":\"String\"}}}}";
		
		DataConvertor dataConvertor = new DataConvertor();
		JSONObject jsonObject2 = dataConvertor.stringToJSON(obj);
		System.out.println();
		System.out.println("obj.toString() : " + obj.toString());
		//JSONObject jsonObject = (JSONObject) obj;
		
		createFile.makeFile(jsonObject2);
		
	/*	File file = new File("D:\\2개.zip");
		
		try {
			unzipFile.readFile(file);
			System.out.println();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block			e.printStackTrace();
		}
		
		System.out.println("마지막");
		//System.out.println("json: " + geo.toString());
*/		
		return null;
	}
}