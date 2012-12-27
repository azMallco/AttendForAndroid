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
	
	//��־��ǩ
	private static final String TAG = "WebDataGetAPI";
	
	//�ύ����
	protected String PostRequest( String url , JSONStringer json ) throws Exception{
		
		int statusCode = 0 ;
		String result = null ;
		
		Log.i(TAG,"url:"+url);
		
		// HttpClient��Ҫ����ִ�����󣬿��԰���������һ������� 
		HttpClient httpclient = new DefaultHttpClient ();
		
		//��������ʱ
		httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT , 10000);
		//���û�ȡ��ʱ
		httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT , 10000);
		
		// ����HTTP POST���������������
		HttpPost request = new HttpPost(url);
		
		try{
			//Head��Ϣ
			request.setHeader("Accept", "application/json");
			request.setHeader("Content-type", "application/json");
			
			//װ����Ϣ
			StringEntity entity = new StringEntity (json.toString() , "UTF-8");
			
			request.setEntity(entity);
			
			//��ȡ��������Ӧ����Ϣ
			HttpResponse httpresponse  = httpclient.execute(request);
			
			statusCode = httpresponse.getStatusLine().getStatusCode();
			
			Log.i(TAG, "statuscode = "+statusCode);
			
			//200 ����ͨ��
			if (statusCode == 200){
				//����String
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
		
		//��������
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
