package com.example.attendencesystem;

import java.io.*;
import org.apache.*;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.json.JSONStringer;

import android.util.Log;

public class webDataApi {
	
	//日志标签
	private static final String TAG = "WebDataGetAPI";
	
	//提交数据
	protected String PostRequest( String url , JSONStringer json ) throws Exception{
		
		int statusCode = 0 ;
		String result = null ;
		
		Log.i(TAG,"url:"+url);
		
		// HttpClient主要负责执行请求，可以把它看做是一个浏览器 
		HttpClient httpclient = new DefaultHttpClient ();
		
		//设置请求超时
		httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT , 10000);
		//设置获取超时
		httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT , 10000);
		
		// 利用HTTP POST向服务器发起请求
		HttpPost request = new HttpPost(url);
		
		try{
			//Head信息
			request.setHeader("Accept", "application/json");
			request.setHeader("Content-type", "application/json");
			
			//装入信息
			StringEntity entity = new StringEntity (json.toString() , "UTF-8");
			
			request.setEntity(entity);
			
			//获取服务器回应的消息
			HttpResponse httpresponse  = httpclient.execute(request);
			
			statusCode = httpresponse.getStatusLine().getStatusCode();
			
			Log.i(TAG, "statuscode = "+statusCode);
			
			//200 正常通信
			if (statusCode == 200){
				//返回String
				result = retrieveInputStream(httpresponse.getEntity());
			}

		}catch(Exception e){
			
			Log.e(TAG, e.getMessage());
			
		}
		
		
		return result;
		
	}

	private String retrieveInputStream(HttpEntity entity) {
		
		int length = (int) entity.getContentLength();
		
		if( length < 0 ){
			length = 10000;
		}
		
		//缓存容器
		StringBuffer sb = new StringBuffer(length);
		
		try{
			
			InputStreamReader inputstreamreader = new InputStreamReader (entity.getContent());
			
			char buffer[] = new char[length];
			int count;
			
			while ((count =  inputstreamreader.read( buffer , 0 , length)) > 0){
				
				sb.append( buffer , 0 , count);
				
			}
	
		}catch (UnsupportedEncodingException e){
			
			Log.e(TAG, e.getMessage());
			
		}catch (IOException e){
			
			Log.e(TAG, e.getMessage());
			
		}catch (IllegalStateException e){
			
			Log.e(TAG, e.getMessage());
			
		}
		
		return sb.toString();
	}
	
	
	
}
