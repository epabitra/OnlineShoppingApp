package com.pabitra.online_shopping.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

public interface MyOTP {
	public static String generateOTP() {
		Random random = new Random();
		
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<6;i++) {
			sb.append(random.nextInt(10));
		}
		String otp = sb.toString();
		
		return otp;
	}
	public static void sendOTP(String otp, String phone) {
		String myUrl = "https://www.fast2sms.com/dev/bulkV2?authorization=<Authorization-Key>&variables_values="+otp+"&route=otp&numbers="+phone;
		
		try {
			URL url = new URL(myUrl);
			HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
			
			con.setRequestMethod("GET");
			con.setRequestProperty("cache-control", "no-cache");
			int responseCode = con.getResponseCode();
			System.out.println("Response code :: "+responseCode);
			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			while(true) {
				String line = br.readLine();
				if(line==null) break;
				response.append(line);
			}
			System.out.println(response);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
