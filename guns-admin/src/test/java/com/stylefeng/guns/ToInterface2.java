package com.stylefeng.guns;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author 包福平
 * @QQ:1140913970
 */
public class ToInterface2 {
    /**
     * 调用对方接口方法
     * @param path 对方或第三方提供的路径
     * @param data 向对方或第三方发送的数据，大多数情况下给对方发送JSON数据让对方解析
     * @throws Exception 
     */
    public static void interfaceUtil(String path,String data) throws Exception {
        	URL url = new URL(path);  
        	HttpURLConnection httpConn = (HttpURLConnection)  
        	        url.openConnection();  
        	httpConn.setRequestMethod("GET");  
        	httpConn.connect();  
        	  
        	BufferedReader reader = new BufferedReader(new InputStreamReader(  
        	        httpConn.getInputStream()));  
        	String line;  
        	StringBuffer buffer = new StringBuffer();  
        	while ((line = reader.readLine()) != null) {  
        	    buffer.append(line);  
        	}  
        	reader.close();  
        	httpConn.disconnect();
        	  
        	System.out.println(buffer.toString()); 
        	Map<String, Object> map = (Map<String, Object>) JSON.parse(buffer.toString());
        	System.out.println(map);
        	System.out.println(map.get("object"));
        	List<Map<String, Object>> list = (List<Map<String, Object>>) JSON.parse(map.get("object").toString());
        	list.forEach(System.out::println);
        }
    //http://localhost:8080/fido-rest/wayfidoapi/v1/system/systems
    //http://localhost:8080/guns-admin/system/delete
    public static void main(String[] args) throws Exception {
        interfaceUtil("http://localhost:8080/fido-rest/wayfidoapi/v1/system/systems", "");
    }
}