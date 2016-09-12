package ow.client;

import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class DataUtils {
	public static String doGet(String urlStr) throws Exception {
		StringBuffer stringBuffer=new StringBuffer();
		URL url;
		try {
			url = new URL(urlStr);
			HttpURLConnection connection=(HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");  
			connection.setConnectTimeout(5000);  
			connection.setDoInput(true);  
			connection.setDoOutput(true);  
			if (connection.getResponseCode()==200) {
				InputStream inputStream=connection.getInputStream();
				int len=0;
				byte[] buf=new byte[1024];
				while ((len=inputStream.read(buf))!=-1) {
					stringBuffer.append(new String(buf, 0, len, "UTF-8")); 
					
				}
				inputStream.close();
			}else {

				throw new Exception();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringBuffer.toString();
	}
}
